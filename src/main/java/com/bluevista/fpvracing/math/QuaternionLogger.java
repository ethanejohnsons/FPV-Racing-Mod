package com.bluevista.fpvracing.math;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class QuaternionLogger {

	private String filename;
	
	public QuaternionLogger(String filename) {
		this.filename = filename;
        this.logHeaderToFile();
	}
	
	public void logHeaderToFile() {
	    try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));

		    writer.append("Keys:" + "\n" + 
		    			  "I = pitch up" + "\n" +
		    			  "K = pitch down" + "\n" +
		    			  "J = roll left" + "\n" +
		    			  "L = roll right" + "\n" +
		    			  "U = yaw left" + "\n" +
		    			  "O = yaw right" + "\n" +
		    			  "Y = throttle up" + "\n" +
		    			  "H = throttle down" + "\n" +
		    			  "\n\n");
		    writer.close();
	    } catch (IOException e) {
	    	System.err.println("QuaternionLogger failed to write to file");
	    }
	}
	
	public void logBlankLineToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
			writer.append("\n");
			writer.close();
		} catch (IOException e) {
			System.err.println("QuaternionLogger failed to write to file");
		}
	}
	
	public void logToFile(String name, String key, float x, float y, float z, float w) {
	    try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
		    writer.append(name + 
		    		      "Key: " + key + 
		    			  ", x: " + x +
		    			  ", y: " + y +
		    			  ", z: " + z +
		    		 	  ", w: " + w +
		    		      "\n");
		    writer.close();
	    } catch (IOException e) {
	    	System.err.println("QuaternionLogger failed to write to file");
	    }
	}
	
}
