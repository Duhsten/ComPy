package com.trikon.compy.os;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import com.trikon.compy.util.CompyLib.SaveFilePath;

public class FileSystem {

	private String envRootDir;
	private String filesystemID;
	private DirStack directoryStack;

	public FileSystem(String filesystemID) {
		this.filesystemID = filesystemID;
		this.envRootDir = SaveFilePath.DEVICES + "\\" + filesystemID;
		directoryStack = new DirStack(this);
		System.out.println("filesystem-" + FileSystem.getRandomString());
	}

	public String getCurEnvDir() {
		return envRootDir + "\\" + directoryStack.currentDir();
	}

	public String getCurDir() {
		return directoryStack.currentDir();
	}

	public String printCurDir() {
		return directoryStack.printCurrentDir();
	}

	public String getEnvRootDir() {
		return envRootDir;
	}

	public boolean makeDirectory(String dir) {
		return new File(getCurEnvDir() + "/" + dir).mkdirs();
	}

	public boolean changeDirectory(String dir) {
		if (dir == null) {
			directoryStack = new DirStack(this);
			dir = getCurDir();
		} else if (dir == "" || dir.equals("")) {
			directoryStack = new DirStack(this);
			dir = getCurDir();
		}
		String[] dirs = dir.split("/");
		for (int i = 0; i < dirs.length; i++) {
			if (dirs[i].contains("..")) { 
				System.out.println("Going back a directory: " + directoryStack.popDir());
				
			} else {
				File f = new File(getCurEnvDir() + "\\" + dirs[i]);
				if (f.exists()) {
					directoryStack.pushDir(dirs[i]);
				} else {
				}
			}
		}

		return false;
	}

	public String getDirectory(String dir) {
		if (dir == null) {
			dir = getCurEnvDir();
		} else if (dir == "" || dir.equals("")) {
			dir = getCurEnvDir();
		}
		String content = "";
		File directoryPath = new File(dir);
		// List of all files and directories
		String contents[] = directoryPath.list();
		System.out.println("List of files and directories in the specified directory:");
		for (int i = 0; i < contents.length; i++) {
			if (!contents[i].startsWith(".")) {
				content = content + " " + contents[i];
			}
		}
		return content;
	}

	public static String getRandomString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	
}

class DirStack {

	private String[] stack = new String[20];
	private int index = 0;
	private FileSystem fileSys;

	public DirStack(FileSystem fileSys) {
		this.fileSys = fileSys;
		loadCurrentPath();
	}

	public void pushDir(String s) {
		if (index > stack.length - 1) {
			String[] newStack = new String[stack.length * 2];

			for (int i = 0; i < stack.length; i++) {
				newStack[i] = stack[i];
			}
			stack = newStack;
		}

		stack[index] = s;
		index++;
		saveCurrentPath();
	}

	public String popDir() {
		index--;
		if (index < 0) {

			index = 0;
			return null;
		}
		String result = stack[index];
		stack[index] = null;
		
		saveCurrentPath();
		return result;

	}

	public String printCurrentDir() {
		String dir = "";
		for (int i = 0; i < stack.length; i++) {
			if (stack[i] != null)
				dir = dir + stack[i] + "/";
		}
		return dir;
	}

	public String currentDir() {
		String dir = "";
		for (int i = 0; i < stack.length; i++) {
			if (stack[i] != null)
				dir = dir + stack[i] + "\\";
		}
		return dir;
	}

	private void saveCurrentPath() {
		File f = new File(fileSys.getEnvRootDir() + "\\.currentpath");
		String path = "";
		if (f.exists()) {
			
			try {
				f.delete();
				FileOutputStream fos = null;
				fos = new FileOutputStream(f, true);
				fos.write(printCurrentDir().getBytes());
				fos.close();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else {
			try {
				f.createNewFile();
				FileOutputStream fos = null;
				fos = new FileOutputStream(f, false);
				fos.write(printCurrentDir().getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	private void loadCurrentPath() {
		File f = new File(fileSys.getEnvRootDir() + "\\.currentpath");
		String path = "";
		if (f.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fileSys.getEnvRootDir() + "\\.currentpath"));
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
				path = Files.readString(f.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (path != "" && !path.equals("")) {
			String[] paths = path.split("/");

			for (int i = 0; i < paths.length; i++) {
				pushDir(paths[i]);
			}
		}
	}

}
