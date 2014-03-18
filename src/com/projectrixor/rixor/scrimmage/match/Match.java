package com.projectrixor.rixor.scrimmage.match;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.projectrixor.rixor.scrimmage.Scrimmage;
import com.projectrixor.rixor.scrimmage.Var;
import com.projectrixor.rixor.scrimmage.map.MapTeam;
import com.projectrixor.rixor.scrimmage.map.extras.SidebarType;
import com.projectrixor.rixor.scrimmage.map.objective.CoreObjective;
import com.projectrixor.rixor.scrimmage.player.Client;
import com.projectrixor.rixor.scrimmage.rotation.RotationSlot;
import com.projectrixor.rixor.scrimmage.utils.ConversionUtil;
import com.projectrixor.rixor.scrimmage.utils.SchedulerUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;
import me.confuser.barapi.BarAPI;

import com.projectrixor.rixor.scrimmage.map.Map;
import org.bukkit.inventory.ItemStack;

public class Match {

	@Getter Map map;
	@Getter
	RotationSlot slot;

	@Getter @Setter boolean hasEnded = false;

	@Getter
	SchedulerUtil schedule;
	
	@Getter SchedulerUtil startingTask;
	@Getter @Setter int starting = 30;
	@Getter @Setter boolean currentlyStarting = false;

	@Getter SchedulerUtil restartingTask;
	@Getter @Setter int restarting = 30;
	@Getter @Setter boolean currentlyRestarting = false;

	@Getter SchedulerUtil timingTask;
	@Getter int timing = 0;
	@Getter double doubleTiming = 0;
	@Getter @Setter int length;
	@Getter @Setter boolean currentlyRunning = false;

	@Getter SchedulerUtil cyclingTask;
	@Getter @Setter int cycling = 30;
	@Getter @Setter boolean loaded = false;
	@Getter @Setter boolean currentlyCycling = false;
	
	public Match(RotationSlot slot, int length) {
		this.slot = slot;
		this.length = length;
		this.map = slot.getMap();
		
		this.startingTask = new SchedulerUtil() {
			
			@Override
			public void runnable() {
				starting();
			}
			
		};
		
		this.timingTask = new SchedulerUtil() {
			
			@Override
			public void runnable() {
				timing();
			}
			
		};

		this.restartingTask = new SchedulerUtil() {

			@Override
			public void runnable() {
				restarting();
			}

		};
		
		this.cyclingTask = new SchedulerUtil() {
			
			@Override
			public void runnable() {
				cycling(Scrimmage.getRotation().getNext());
			}
			
		};
		
		map.update(true);
		setCurrentlyStarting(true);
		setCurrentlyRunning(false);
		setCurrentlyCycling(false);
	}
	
	public void start() {
		//start(9001);
	}

	int startingTime;
	public void start(int time) {
		startingTime = time;
		if(time == -2)
			time = 30;
		
		try {
			stop();
		} catch(Exception e) {
			// meh
		}
		
		this.doubleTiming = 0;
		this.timing = 0;
		this.cycling = 30;
		this.starting = time;
		this.startingTask.repeat(20, 0);
	}
	
	public void cycle() {
		cycle(30);
	}

	int cycleTime;

	public void cycle(int time) {
		cycleTime = time;
		if(time == 0)
			time = 30;
		
		try {
			stop();
		} catch(Exception e) {
			// meh
		}
		
		this.timing = 0;
		this.cycling = time;
		this.starting = 30;
		this.cyclingTask.repeat(20, 0);
	}


	int originalRestart;
	public void restart(int time){
		restarting = time;
		originalRestart = time;
		if(time == 0)
			time = 30;

		try {
			stop();
		} catch(Exception e) {
			// meh
		}
		this.timing = 0;
		this.restarting = time;
		this.starting = 30;
		this.restartingTask.repeat(20, 0);
	}
	
	public void stop() throws NullPointerException {
		if(this.startingTask.getTask() != null) this.startingTask.getTask().cancel();
		if(this.cyclingTask.getTask() != null) this.cyclingTask.getTask().cancel();
		if(this.timingTask.getTask() != null) this.timingTask.getTask().cancel();
		if(this.restartingTask.getTask() != null) this.restartingTask.getTask().cancel();
		
		setCurrentlyStarting(true);
		setCurrentlyRunning(false);
		setCurrentlyCycling(false);
		setCurrentlyRestarting(false);
	}
	
	private boolean doubleStarting() {
		setDoubleCycle(true);
		return false;
		
	}
	
	private void setDoubleCycle(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private boolean starting() {
		String p = "s";
		if(starting == 1) p = "";
		for (Player Online : Bukkit.getOnlinePlayers()) {
			if(starting > 0) {
			BarAPI.setMessage(Online, ChatColor.GREEN + "Match starting in " + ChatColor.DARK_RED + starting + ChatColor.GREEN + " second" + p + "!", (float) starting / startingTime * 100);
			}
		}
		setCurrentlyStarting(true);
		if(starting == 0) {
			Scrimmage.broadcast(ChatColor.DARK_PURPLE + "" + ChatColor.STRIKETHROUGH + "-------------------------");
			Scrimmage.broadcast(ChatColor.GOLD + "The match has started!");
			List<String> allteams = new ArrayList<String>();
			String todo = "";
			String finalteam = "";
			for(MapTeam team : getMap().getTeams()) {
				allteams.add("" + getMap().getTeams());
				if(allteams.size() == 1) {
					todo = todo + team.getColor() + "" + team.getDisplayName() + ChatColor.RED + ChatColor.BOLD + " VS ";
					allteams.remove(team.getName());
				} else {
					todo = todo + team.getColor() + "" + team.getDisplayName();
					allteams.remove(team.getName());
					finalteam = team.getColor() + "" + team.getName();
					
				}
			}
			if (getMap().getTeams().size() == 1) {
			Scrimmage.broadcast(finalteam + "" + ChatColor.RED + "" + ChatColor.BOLD + " VS " + ChatColor.GRAY + "themselves...?");
			} else {
			Scrimmage.broadcast(todo);
			}
			Scrimmage.broadcast(ChatColor.DARK_PURPLE + "" + ChatColor.STRIKETHROUGH + "-------------------------");
			for(MapTeam team : getMap().getTeams()) {
				team.setScore(0);
			}
			Scrimmage.getMap().reloadSidebar(true,SidebarType.OBJECTIVES);
			for (Player Online : Bukkit.getOnlinePlayers()) {
				Online.playSound(Online.getLocation(), Sound.NOTE_PIANO, 100, 1);
				}
			startingTask.getTask().cancel();
			setCurrentlyStarting(false);
			timingTask.repeat(20, 0);
			
			for(MapTeam team : getMap().getTeams())
				for(Client client : team.getPlayers()) {
					client.setTeam(team, true, true, true);
					client.getPlayer().setFoodLevel(20);
				}


			
			return true;
		}
		
		
		
		if (starting <= 3 && starting > 0)
			for (Player Online : Bukkit.getOnlinePlayers()) {
				Online.playSound(Online.getLocation(), Sound.NOTE_PIANO, 100, -1);
				}
		
		starting--;
		return false;
	}
	
	
	
	private boolean timing() {
		setCurrentlyRunning(true);
		if((timing >= length && length != -1) || end(false) || (getMap().getTimeLimit() > 0 && getMap().getTimeLimit() <= timing)) {
			end(true);
			setCurrentlyRunning(false);
			return true;
		}
		
	/*	if(timing % (5*60) == 0) {
			String playing = ChatColor.DARK_PURPLE + "Currently playing " + ChatColor.GOLD + getMap().getName();
			String by = ChatColor.DARK_PURPLE + " by ";
			
			List<String> authors = new ArrayList<String>();
			for(Contributor author : getMap().getAuthors())
				authors.add(ChatColor.RED + author.getName());
			String creators = ConversionUtil.commaList(authors, ChatColor.DARK_PURPLE);
			String broadcast = playing + by + creators;
			Scrimmage.broadcast(broadcast);
		}*/
		
		for(CoreObjective core : getMap().getCores()) {
			if(core.getStage().getNext() != null && core.getStage().getNext().getTime() == timing) {
				String prefix = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "> > > ";
				String suffix = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " < < <";
				Scrimmage.broadcast(prefix + ChatColor.RED + (core.getStage().name() + " CORE MODE") + suffix);
				core.setStage(core.getStage().getNext());
			}
		}
		
		if(getMap().getSidebar() == SidebarType.SCORE) {
			boolean timer = false;
			double preMapTime = (getMap().getTimeLimit()/100);
			Var.oldMapTime = (preMapTime);
			
			
			if(timing % 60 == 0) timer = true;
			else if(getMap().getTimeLimit() > 0) {
				if(getMap().getTimeLimit() - timing <= 60) {
					if(getMap().getTimeLimit() - timing % 15 == 0) timer = true;
					else if(getMap().getTimeLimit() - timing <= 5) timer = true;
					
				}
			}
			
			if(timing % Var.oldMapTime == 0) {
				Var.edHealth = Var.edHealth - 1;
			}
			if (timing % 1 == 0) {
				String score = ChatColor.AQUA + "Score: ";
				for(MapTeam team : getMap().getTeams())
					score += team.getColor() + "" + team.getScore() + " ";
				if(getMap().getTimeLimit() > 0)
					score += ChatColor.RED + ConversionUtil.formatTime(getMap().getTimeLimit()-getTiming());
				int limit = getMap().getTimeLimit();
				int percentage = (int)((limit - getTiming()) * 100.0 / limit + 0.5);
				float realPercentage = (float) percentage;
				if (realPercentage < 0) {
					realPercentage = 0;
				}
				for (Player Online : Bukkit.getOnlinePlayers()) {
				BarAPI.setMessage(Online, score, realPercentage);
				}
				/*for (Player Online : Bukkit.getOnlinePlayers()) {
					if ((getMap().getTimeLimit() - getTiming())/100 > 1) {
						BarAPI.setMessage(Online, score, Var.edHealth);
						//BarAPI.setMessage(Online, score, (float) timing / (float) getMap().getTimeLimit()));
						Bukkit.broadcastMessage("" + (timing / getMap().getTimeLimit()));
						//BarAPI.setMessage(Online, score, 50f);
					} else {
						BarAPI.setMessage(Online, score, Var.edHealth);
						//BarAPI.setMessage(Online, score, (float) timing / (float) getMap().getTimeLimit()));
						Bukkit.broadcastMessage("" + (timing / getMap().getTimeLimit()));
						Bukkit.broadcastMessage("" + Var.edHealth + " " + Var.oldMapTime);
					}
				}*/
			}
			
			if(timer) {
				String score = ChatColor.AQUA + "Score: ";
				for(MapTeam team : getMap().getTeams())
					score += team.getColor() + "" + team.getScore() + " ";
				if(getMap().getTimeLimit() > 0)
					score += ChatColor.RED + ConversionUtil.formatTime(getMap().getTimeLimit() - getTiming());
				Scrimmage.broadcast(score);
			}
		} else {
			if(timing % 1 == 0) {
				String time = ChatColor.AQUA + "Time Elapsed: " + ChatColor.RED + "";
				time += ConversionUtil.formatTime(getTiming());
				for (Player Online : Bukkit.getOnlinePlayers()) {
					BarAPI.setMessage(Online, time);
					}
			}
		}
		
		timing++;
		
		return false;
	}
	
	public boolean end(boolean force) {
		List<MapTeam> teams = getMap().getWinners();
		MapTeam winner = null;
		
		if(teams.size() == 1)
			winner = teams.get(0);
		
		if(!force) return false;
		end(winner);
		return true;
	}
	
	public boolean end(List<MapTeam> winners) {
		end();
		if (winners.size() == 0) {
			for (Player Online : Bukkit.getOnlinePlayers()) {
				BarAPI.setMessage(Online, ChatColor.RED + "Tie!", 100f);
				}
		}
		else if(winners.size() > 0) {
			List<String> teams = new ArrayList<String>();
			for(MapTeam team : winners) {
				Var.teams.add(team.getColor() + team.getDisplayName());
			}
			
			for(MapTeam team : winners) {
				teams.add(team.getColor() + team.getDisplayName());
			}
				
			String teamString = teams + "";
			String text = ConversionUtil.commaList(teams, ChatColor.GRAY);
			for (Player Online : Bukkit.getOnlinePlayers()) {
				BarAPI.setMessage(Online, text + ChatColor.RED + " wins!", 100f);
				}
			Scrimmage.broadcast(ChatColor.BOLD + text + ChatColor.GRAY + " wins!");

			for (Player p : Bukkit.getOnlinePlayers()){
				if (text.equals(Client.getClient(p).getTeam().getDisplayName()) || text.equalsIgnoreCase("tie!")){
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
				}
				else {
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 5, 1);
				}
			  hasEnded = true;
			}
		}
		return true;
	}
	
	public boolean end(MapTeam winner) {
		List<MapTeam> winners = new ArrayList<MapTeam>();
		if(winner != null) winners.add(winner);
		return end(winners);
	}
	
	private void end() {
		String text = ConversionUtil.commaList(Var.teams, ChatColor.GRAY);
		for (Player Online : Bukkit.getOnlinePlayers()) {
			BarAPI.setMessage(Online, text + ChatColor.RED + " Wins!", 100f);
			}
		Var.teams.remove(Var.teams);
		Scrimmage.broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # # # # # # ");
		Scrimmage.broadcast(ChatColor.DARK_PURPLE + "# # " + ChatColor.GOLD + "The match has ended!" + ChatColor.DARK_PURPLE + " # #");
		Scrimmage.broadcast(ChatColor.DARK_PURPLE + "# # # # # # # # # # # # # # # #");
		timingTask.getTask().cancel();
		/* cyclingTask.repeatAsync(20, 20);
		Auto Cycle*/
		for(MapTeam team : getMap().getTeams())
			for(Client client : team.getPlayers()) {
				//client.setTeam(getMap().getObservers(), true, false, false);
				client.getPlayer().setGameMode(GameMode.CREATIVE);
				client.getPlayer().getInventory().setItem(0, new ItemStack(Material.COMPASS));
			}


		
		setCurrentlyRunning(false);
		setCurrentlyCycling(true);
	}


	public void restarting(){
		String p = "s";

		if(restarting == 1) p = "";
		for (Player Online : Bukkit.getOnlinePlayers()) {
				BarAPI.setMessage(Online, ChatColor.DARK_AQUA + "Restarting "
						+ "in " + ChatColor.DARK_RED + restarting + ChatColor.DARK_AQUA + " second" + p + "!", (float) restarting / originalRestart * 100);

		}
		setCurrentlyRestarting(true);
		if (restarting == 0){
			this.restartingTask.getTask().cancel();
			for (Player p1 : Bukkit.getOnlinePlayers()){
				p1.kickPlayer(ChatColor.GREEN + "Server has shutdown! " + ChatColor.GOLD + "Rejoin!");
			}

			for (Map m : Scrimmage.getMapsPlayed()){
				Scrimmage.getInstance().getServer().unloadWorld(m.getWorld(), false);
				//Scrimmage.getInstance().getLogger().info(m.getName() + " " + m.getFolder().getAbsolutePath());
				//for (File file : m.getFolder().listFiles()) {
				//	try{
				//		FileDeleteStrategy.FORCE.delete(file);
				//	}catch(IOException e){
			//			Scrimmage.getInstance().getLogger().info(e.getMessage());
			//		}
			//	}
		//	}
			Scrimmage.getInstance().getServer().shutdown();
		}
		restarting--;
		}
	}

	public static boolean delete(File path) {
		if( path.exists() ) {
			File files[] = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					delete(files[i]);
				}
				else {
					files[i].delete();
				} //end else
			}
		}
		return( path.delete() );
	}
	
	public boolean cycling(RotationSlot next) {
		String p = "s";
		if (next == null){
			restart(30);
			return true;
		}
		if(cycling == 1) p = "";
		for (Player Online : Bukkit.getOnlinePlayers()) {
			BarAPI.setMessage(Online, ChatColor.DARK_AQUA + "Cycling to " + ChatColor.AQUA + next.getLoader().getName() + ChatColor.DARK_AQUA
					+ " in " + ChatColor.DARK_RED + cycling + ChatColor.DARK_AQUA + " second" + p + "!", (float) cycling / cycleTime * 100);
		}
		
		setCurrentlyCycling(true);
		if(cycling == 0) {
			Var.canSetNext = 0;
			cyclingTask.getTask().cancel();
			setCurrentlyCycling(false);
			Scrimmage.getRotation().setSlot(next);
			for(Client client : Client.getClients())
				client.setTeam(next.getMap().getObservers(), true, true, true);
			next.getMatch().start();
			Scrimmage.addMapToMapsPlayed(next.getMap());
			hasEnded = false;
			for (Player Online : Bukkit.getOnlinePlayers()) {
				BarAPI.setMessage(Online, ChatColor.GRAY + "Welcome to " + ChatColor.GOLD + Scrimmage.getTeam() + ChatColor.GRAY + "'s Server!");
			}
			return true;
		}
		
		if(cycling == 1 && !loaded && next != null) {
			setLoaded(true);
			next.load();
		}
		
		cycling--;
		return false;
	}
	
}
