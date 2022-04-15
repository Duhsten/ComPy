package com.trikon.compy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import net.minecraft.client.Minecraft;
import net.minecraft.world.storage.FolderName;

public class CompyLib {

	
	public static void init() {
		initDir(SaveFilePath.COMPY);
	}

	private static void initDir(SaveFilePath file) {
		if (!Files.isDirectory(Paths.get(file.getPath()))) {
			System.out.println("Initializing ComPy");
			new File(file.toString()).mkdirs();
		} else {
			System.out.println("Loaded ComPy");
			System.out.println(computerList());
		}
	}

	public static class SaveFilePath {
		
		private final static String ROOT = trimEnd(Minecraft.getInstance().getSingleplayerServer().getWorldPath(FolderName.ROOT).toAbsolutePath().toString().replace("\\.\\", "\\"));
		public static final SaveFilePath SAVE_ROOT = new SaveFilePath(ROOT);
		public static final SaveFilePath COMPY = new SaveFilePath(ROOT + "\\compy");
		public static final SaveFilePath DEVICES = new SaveFilePath(ROOT + "\\compy\\devices");
		public static final SaveFilePath DEVICES_LIST = new SaveFilePath(ROOT + "\\compy\\devices.json");
		private final String p;

		public SaveFilePath(String p) {
			this.p = p;
		}

		public String getPath() {
			return this.p;
		}

		public String toString() {
			return this.p.toString();
		}
	}
	
	public static String trimEnd(String value) {
	    int len = value.length();
	    int st = 0;
	    while ((st < len) && value.charAt(len - 1) == '.') {
	      len--;
	    }
	    return value.substring(0, len);
	}
	public static boolean computerList() {
		
		
		if(!Files.exists(Paths.get(SaveFilePath.DEVICES_LIST.toString()), LinkOption.NOFOLLOW_LINKS))
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	public static boolean checkID(String id) throws IOException {
		List<Device> d = CompyLib.getDevices();
		
		if(d ==  null ) {
			return false;
		}
		else {
			for(Device de : d) {
				if(de.id == id) {
					return true;
				}
			}
		}
		return false;
	}
	public static boolean checkForDeviceAt(int x, int y, int z) {
		List<Device> d = null;
		try {d = CompyLib.getDevices();}
		catch(Exception ex) {};
		
		if(d ==  null ) {
			return false;
		}
		else {
			for(Device de : d) {
				if(de.x == x && de.y == y && de.z == z) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static Device getDeviceAt(int x, int y, int z) {
		List<Device> d = null;
		try {d = CompyLib.getDevices();}
		catch(Exception ex) {};
		
		if(d ==  null ) {
			return null;
		}
		else {
			for(Device de : d) {
				if(de.x == x && de.y == y && de.z == z) {
					return de;
				}
			}
		}
		return null;
	}
	
	public static boolean addDevice(int x, int y, int z) {
		String filesys = getRandomString(x, y, z);
		Device newDevice = new Device(filesys, x, y, z);
		Gson gson = new Gson();
		List<Device> devices = new ArrayList<>();
		initDir(SaveFilePath.DEVICES);
		if(!computerList()) {
			
			devices.add(newDevice);
			new File(SaveFilePath.DEVICES.toString() + "\\" + filesys).mkdir();
		}
		else {
			try {
				
				BufferedReader reader = new BufferedReader(new FileReader(SaveFilePath.DEVICES_LIST.toString()));
				StringBuilder stringBuilder = new StringBuilder();
				String line = null;
				String ls = System.getProperty("line.separator");
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
					stringBuilder.append(ls);
				}
				// delete the last new line separator
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
				reader.close();

				String content = stringBuilder.toString();
				devices = gson.fromJson(content, ArrayList.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			devices.add(newDevice);
			new File(SaveFilePath.DEVICES.toString() + "\\" + filesys).mkdir();
		}
		String json = gson.toJson(devices);
		try {
			  FileWriter writer = new FileWriter(SaveFilePath.DEVICES_LIST.toString(), false);
			  writer.write(json);
			  writer.close();
			} catch (IOException e) {
			  e.printStackTrace();
			  return false;
			}  
		return true;
	}
	public static List<Device> getDevices() throws IOException {
		
		if(!computerList()) {
			return null;
		}
		else {
			BufferedReader reader = new BufferedReader(new FileReader(SaveFilePath.DEVICES_LIST.toString()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			// delete the last new line separator
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			reader.close();

			String content = stringBuilder.toString();
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<Device>>(){}.getType();
			
			return new Gson().fromJson(content, listType);
		}
	}
	
	
	private static String getRandomString(int x, int y, int z) {
		String i = String.valueOf(x) + String.valueOf(y) + String.valueOf(z);
		Long j = Long.parseLong(i);
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        rnd.setSeed(j);
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	

}

