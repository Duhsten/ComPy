package com.trikon.compy.os;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.trikon.compy.screen.ComputerScreen;
import com.trikon.compy.util.Device;

public class Computer {

	private int powerState = 0;
	private FileSystem fileSystem;
	private Terminal shell;
	private ComputerScreen screen;
	private boolean pythonConsole;
	private boolean colour;
	PythonInterpreter python = new PythonInterpreter(this);
	public Computer(Device d, ComputerScreen screen) {
		this.screen = screen;
		fileSystem = new FileSystem(d.id);
		shell = new Terminal(ComputerScreen.xSize, ComputerScreen.ySize, null);
		
	    File f = new File(fileSystem.getEnvRootDir() + "\\.output");
	    try {
			f.createNewFile();
		} catch (IOException e) {
		}
		
	}
	
	 public boolean isColour()
	    {
	        return colour;
	    }
	public boolean isPythonConsole( ) {
		return pythonConsole;
	}
	public void setPythonConsole(boolean b) {
		pythonConsole = b;
	}
	public void clearOutput() {
		 screen.clearOutput();
		 try {
			Files.newBufferedWriter(new File(fileSystem.getEnvRootDir() + "\\.output").toPath() , StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	public Terminal getTerminal() {
		return shell;
	}
	public boolean shutdown() {
		File outputFile = new File(this.fileSystem.getEnvRootDir() + "\\.output");
		File currentPathFile = new File(this.fileSystem.getEnvRootDir() + "\\.currentpath");
		if(outputFile.exists()) {
			outputFile.delete();
		}
		if (currentPathFile.exists()) {
			currentPathFile.delete();
		}
		return true;
	}
	public String commandLinePrefix() {
		if(isPythonConsole()) {return ">>> ";}
		else {return fileSystem.printCurDir() + "> ";}
		
	}
	public FileSystem getFileSystem() {
		return fileSystem;
	}
	public void runCommand(String msg, ComputerScreen screen) {
		System.out.println("Running command " + msg);
		//String response = shell.runCommand(msg,this).response;
		//if(!response.equals("")) {
		//	screen.updateOutput(response);
		//}
		
	}

	public void runPythonCommand(String cmd, ComputerScreen screen) {
		
		screen.updateOutput(python.run(cmd));
	}
	public void setPower(int i) {
		powerState = i;
	}
	
}
