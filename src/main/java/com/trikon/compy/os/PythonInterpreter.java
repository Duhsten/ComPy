package com.trikon.compy.os;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.python.core.PyObject;

public class PythonInterpreter {

	private org.python.util.PythonInterpreter pyInterp = new org.python.util.PythonInterpreter();
	private org.python.util.InteractiveConsole console = new org.python.util.InteractiveConsole();
	
	//Input Stream
	private PipedInputStream input = new PipedInputStream();
	private PipedOutputStream output = new PipedOutputStream();
	
	
	//Output Stream
	private PipedInputStream input2 = new PipedInputStream();
	private PipedOutputStream output2 = new PipedOutputStream();
	
	private Computer computer;
	public PythonInterpreter(Computer c) {
		this.computer = c;
		try {
			input.connect(output);
			input2.connect(output2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String run(String s) {
				System.out.println("Recieved Python: " + s);
		      try {
		    	 
		          
		             
		    	  Writer w = new FileWriter(this.computer.getFileSystem().getEnvRootDir() + "\\.output", true);
		    	  StringWriter out = new StringWriter();
		    	  console.setOut(w);
		    	  console.exec(s);
		    	  w.close();
		    	

		    	  
		    	   return "";
		      }
		      catch(Exception ex) {
		    	
		    	  return ex.toString();
		      }
		    
	}
}
