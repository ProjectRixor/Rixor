package com.projectrixor.rixor.scrimmage.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class UpdateUtil {

	private static String version;
	private static String plugin_name;

	public static boolean checkForUpdate(String new_version_adress, String new_plugin_adress, PluginDescriptionFile pdFile, Player p)
	{
		plugin_name = pdFile.getName();

		p.sendMessage(ChatColor.GREEN + "["+plugin_name+"]" + ChatColor.YELLOW + "Searching for update...");

		try
		{
			URL url1 = new URL(new_version_adress);
			URLConnection connection = url1.openConnection();

			ByteArrayOutputStream result1 = new ByteArrayOutputStream();
			java.io.InputStream input1 = connection.getInputStream();
			byte[] buffer = new byte[1000];
			int amount = 0;

			while(amount != -1)
			{

				result1.write(buffer, 0, amount);
				amount = input1.read(buffer);

				version = result1.toString();
			}

			String Version_String_Temp = version;
			Double version_double = Double.parseDouble(Version_String_Temp);

			String version_now = pdFile.getVersion();
			Double version_now_double = Double.parseDouble(version_now);

			if(version_now_double < version_double && version_double > version_now_double)
			{
				p.sendMessage(ChatColor.GREEN + "["+plugin_name+"] " + ChatColor.GREEN + "Update found!");
				p.sendMessage(ChatColor.GREEN + "["+plugin_name+"] "  + ChatColor.YELLOW + "Updating...");

				URL url2 = new URL(new_plugin_adress);
				ReadableByteChannel rbc1 = Channels.newChannel(url2.openStream());
				//File updateFolder = new File("plugins/update/");
				//updateFolder.mkdir();
				FileOutputStream fos1 = new FileOutputStream("plugins/" + plugin_name + ".jar");
				fos1.getChannel().transferFrom(rbc1, 0, 1 << 24);

				p.sendMessage(ChatColor.GREEN + "["+plugin_name+"] " + ChatColor.GREEN + "Updated successfully!" + ChatColor.AQUA + " Restart the server for changes to take affect");
				return true;
			}
			else {
				p.sendMessage(ChatColor.GREEN + "["+plugin_name+"] " + ChatColor.YELLOW + "No update found!");
				return false;
			}
		}
		catch (MalformedURLException | FileNotFoundException e)
		{
			//e.printStackTrace();
		}catch (IOException e)
		{
			p.sendMessage(ChatColor.GREEN + "["+plugin_name+"] " + ChatColor.RED + "Unable to find Version-File!");
			p.sendMessage(ChatColor.GREEN + "["+plugin_name+"] " + ChatColor.RED + "Unable to load update!");
			return false;
			//e.printStackTrace();
		}
		return false;
	}


	public static boolean isUpdateAvailable(String new_version_adress, String new_plugin_adress, PluginDescriptionFile pdFile, Player p){
		plugin_name = pdFile.getName();


		try
		{
			URL url1 = new URL(new_version_adress);
			URLConnection connection = url1.openConnection();

			ByteArrayOutputStream result1 = new ByteArrayOutputStream();
			java.io.InputStream input1 = connection.getInputStream();
			byte[] buffer = new byte[1000];
			int amount = 0;

			while(amount != -1)
			{

				result1.write(buffer, 0, amount);
				amount = input1.read(buffer);

				version = result1.toString();
			}

			String Version_String_Temp = version;
			Float version_double = Float.parseFloat(Version_String_Temp);

			String version_now = pdFile.getVersion();
			Float version_now_double = Float.parseFloat(version_now);

			if(version_now_double < version_double && version_double > version_now_double)
			{
				return true;
			}
			else {
				//p.sendMessage("["+plugin_name+"]" + ChatColor.YELLOW + "No update found!");
				return false;
			}
		}
		catch (MalformedURLException | FileNotFoundException e)
		{
			//e.printStackTrace();
		}catch (IOException e)
		{
			p.sendMessage(ChatColor.GREEN + "["+plugin_name+"] " + ChatColor.RED + "Unable to find Version-File!");
			p.sendMessage(ChatColor.GREEN + "["+plugin_name+"] " + ChatColor.RED + "Unable to load update!");
			return false;
			//e.printStackTrace();
		}
		return false;
	}
}
