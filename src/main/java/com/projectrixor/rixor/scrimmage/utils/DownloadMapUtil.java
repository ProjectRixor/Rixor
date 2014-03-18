package com.projectrixor.rixor.scrimmage.utils;


import com.projectrixor.rixor.scrimmage.Scrimmage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class DownloadMapUtil {

	public static boolean checkForMap(String mapname){
		String parsedMapName = mapname.replace(" ", "%20").replace("/", "");
		String returnValue;
		try
		{
			URL url1 = new URL("http://update.masterejay.us/maps/" + parsedMapName + ".zip");
			Scrimmage.debug(parsedMapName, ".");
			URLConnection connection = url1.openConnection();

			ByteArrayOutputStream result1 = new ByteArrayOutputStream();
			java.io.InputStream input1 = connection.getInputStream();
			byte[] buffer = new byte[1000];
			int amount = 0;

			while(amount != -1)
			{

				result1.write(buffer, 0, amount);
				amount = input1.read(buffer);

				returnValue = result1.toString();
				Scrimmage.debug(returnValue, ".");
				if (returnValue.contains("Not Found")){
				   return false;
				}
				else {
					return true;
				}
			}
		}
		catch (MalformedURLException|FileNotFoundException e)
		{
			//e.printStackTrace();
			return false;
		}catch (IOException e)
		{
			return false;
			//e.printStackTrace();
		}
		return false;
	}

	public static void downloadMap(String mapName){
		String parsedMapName = mapName.replace(" ", "%20").replace("/", "");
		try
		{

				URL url2 = new URL("http://update.masterejay.us/maps/" + parsedMapName + ".zip");
				ReadableByteChannel rbc1 = Channels.newChannel(url2.openStream());
				File updateFolder = new File("temp/");
				updateFolder.mkdir();
				FileOutputStream fos1 = new FileOutputStream("temp/" + mapName + ".zip");
				fos1.getChannel().transferFrom(rbc1, 0, 1 << 24);
				fos1.close();
				ZipUtil.unZip("temp/" + mapName + ".zip", "maps/");


		}catch (IOException e)
		{
			return;
			//e.printStackTrace();
		}
	}
}
