package com.trikon.compy.os.core;

public class FileSystem {

	private String envRootDir;
	public FileSystem(String envRootDir)
	{
		this.envRootDir = envRootDir;
	}
	
	public String getEnvRootDir() {
		return envRootDir;
	}
}



class DirStack {
	
	private String[] stack = new String[5];
	private int index = 0;
	public DirStack() {
		
	}
	
	public void pushDir(String s)
	{
		if(index > stack.length -1)
		{
			String[] newStack = new String[stack.length * 2];
			
			for(int i = 0; i < stack.length; i++)
			{
				newStack[i] = stack[i];
			}
			stack = newStack;
		}
		
		stack[index] = s;
		index++;
	}
	public String popDir() {
		
		if(index < 0) {

			index = 0;
			return null;
		}
		String result = stack[index];
		stack[index] = null;
		index--;
		return result;
		
	}
	
	public void printStack() {
		
		for(int i = 0; i < stack.length; i++)
		{
			System.out.print(stack[i] + "/");
		}
	}
	
}
