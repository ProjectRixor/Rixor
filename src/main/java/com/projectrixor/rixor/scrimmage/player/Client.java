package com.projectrixor.rixor.scrimmage.player;

import com.projectrixor.rixor.scrimmage.Rixor;
import com.projectrixor.rixor.scrimmage.map.Map;
import com.projectrixor.rixor.scrimmage.map.MapLoader;
import com.projectrixor.rixor.scrimmage.map.MapTeam;
import com.projectrixor.rixor.scrimmage.map.extras.Contributor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

// import ServerLog;

public class Client {
	
	static List<Client> clients = new ArrayList<Client>();
	
	public static Client getClient(Player player) {
		for(Client client : clients)
			if(client.getPlayer() == player)
				return client;
		
		return null;
	}
	List<Contributor> authors;
	Player player;
	MapTeam team;
	PermissionAttachment perms;
	MapLoader loader;
	
	public Client(Player player) {
		this.player = player;
		this.perms = player.addAttachment(Rixor.getInstance());
	}

	public static List<Client> getClients(){
		return Client.clients;
	}

	public boolean hasTouched() {
		return false;
	}
	
	public boolean isRanked() {
		return (getStars().length() !=0 && (!(getStars().equals(ChatColor.BLUE + "*"))));
	}
	
	public String getStars() {
	
		//String[] devs = new String[]{"ParaPenguin", "Haribo98", "MasterEjzz", "ShinyDialga45"};
		List<String> authors = new ArrayList<String>();
		for(Contributor author : Rixor.getMap().getAuthors()) {
			authors.add(author.getName());
		}
		
		String op = ChatColor.GOLD + "❖";
		
		String author = ChatColor.BLUE + "*";
		
		String stars = "";
		
		if(getPlayer().isOp())
			stars += op;
		
		
		for(String string : authors) {
			if(string.contains(getPlayer().getName())) {
				stars += author;
				break;
			}
		}
		

		
		return stars;
	}
	
	public void setTeam(MapTeam team) {
		/*
		ServerLog.info("Starting: " + Rixor.getRotation().getSlot().getMatch().isCurrentlyStarting());
		ServerLog.info("Running: " + Rixor.getRotation().getSlot().getMatch().isCurrentlyRunning());
		ServerLog.info("Cycling: " + Rixor.getRotation().getSlot().getMatch().isCurrentlyCycling());
		*/
		
		if(Rixor.getRotation().getSlot().getMatch().isCurrentlyRunning()) {
			setTeam(team, true, true, true);
			return;
		}
		
		setTeam(team, false, true, false);
	}
	
	public void setTeam(MapTeam team, boolean load, boolean clear, boolean teleport) {
		this.team = team;
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				try {
					updateVision();
				} catch(NullPointerException e) {
					// meh
				}
			}
			
		}.runTaskLaterAsynchronously(Rixor.getInstance(), 1);
		
		for(PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
		player.setScoreboard(team.getMap().getBoard());
		if(load) team.loadout(this, teleport, clear);
		
		//if(team.getTeam() == null) ServerLog.info("Scoreboard Team for '"+team.getName()+"' is null");
		if(clear) team.getTeam().addPlayer(getPlayer());
		
		getPlayer().setDisplayName(getStars() + getTeam().getColor() + getPlayer().getName());
	}
	
	public boolean isObserver() {
		return team.isObserver();
	}
	
	public static void updateVision() {
		for(Client client : getClients()) {
			Map map = client.getTeam().getMap();
			List<Client> observers = map.getObservers().getPlayers();
			List<Client> players = new ArrayList<Client>();
			for(MapTeam team : map.getTeams())
				players.addAll(team.getPlayers());
			
			if(Rixor.getRotation().getSlot().getMatch().isCurrentlyRunning()) {
				for(Client observer : observers)
					for(Client update : Client.getClients())
						observer.getPlayer().showPlayer(update.getPlayer());
				for(Client player : players) {
					for(Client update : observers)
						player.getPlayer().hidePlayer(update.getPlayer());
					for(Client update : players)
						player.getPlayer().showPlayer(update.getPlayer());
				}
			} else {
				for(Client observer : Client.getClients())
					for(Client update : Client.getClients())
						observer.getPlayer().showPlayer(update.getPlayer());
			}
		}
	}

	public List<Contributor> getAuthors(){
		return this.authors;
	}

	public Player getPlayer(){
		return this.player;
	}

	public MapTeam getTeam(){
		return this.team;
	}

	public PermissionAttachment getPerms(){
		return this.perms;
	}

	public MapLoader getLoader(){
		return this.loader;
	}
}
