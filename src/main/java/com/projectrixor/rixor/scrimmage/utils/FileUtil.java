package com.projectrixor.rixor.scrimmage.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public static List<File> getFiles(File folder) {
		if (!folder.exists())
			folder.mkdirs();
		List<File> list = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				getFiles(fileEntry);
			}
			else {
				list.add(fileEntry);
			}
		}
		return list;
	}
	
	public static void delete(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				delete(new File(dir, children[i]));
			}
		}
		dir.delete();
	}
	
	public static void move(File from, File to) throws IOException {
		copyFolder(from, to);
		delete(from);
	}
	
	public static void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copy(File src, File dest) throws IOException {
		if (!src.exists()) {
			throw new IOException("Can not find source: " + src.getAbsolutePath()+".");
		} else if (!src.canRead()) {
			throw new IOException("No right to source: " + src.getAbsolutePath()+".");
		}
		if (src.isDirectory()) 	{
			if (!dest.exists()) { 
				if (!dest.mkdirs()) {
					throw new IOException("Could not create direcotry: " + dest.getAbsolutePath() + ".");
				}
			}
			String list[] = src.list();
			for (int i = 0; i < list.length; i++) {
				File dest1 = new File(dest, list[i]);
				File src1 = new File(src, list[i]);
				copy(src1 , dest1);
			}
		} else {
			FileInputStream fin = null;
			FileOutputStream fout = null;
			byte[] buffer = new byte[4096]; 
			int bytesRead;
			try {
				fin =  new FileInputStream(src);
				fout = new FileOutputStream (dest);
				while ((bytesRead = fin.read(buffer)) >= 0) {
					fout.write(buffer,0,bytesRead);
				}
			} catch (IOException e) {
				IOException wrapper = new IOException("Unable to copy file: " + src.getAbsolutePath() + "to" + dest.getAbsolutePath()+".");
				wrapper.initCause(e);
				wrapper.setStackTrace(e.getStackTrace());
				throw wrapper;
			} finally {
				if (fin != null) { fin.close(); }
				if (fout != null) { fout.close(); }
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else {
			OutputStream out;
			try {
				InputStream in = new FileInputStream(src);
				out = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
						out.write(buffer, 0, length);
				}
				out.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}