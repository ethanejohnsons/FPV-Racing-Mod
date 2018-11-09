package com.bluevista.fpvracing.controls;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import com.bluevista.fpvracing.OSValidator;

public class GenericTransmitter {
	
	private Controller controller;
	
	public GenericTransmitter() {
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		Controllers.poll();		
		
		System.out.println("Controllers: ");
		for(int i = 0; i < Controllers.getControllerCount(); i++) {
//			if(Controllers.getController(i).getAxisCount() == 8) {
//				controller = Controllers.getController(i);
//			}
			System.out.println(Controllers.getController(i).getName() + ", " + Controllers.getController(i).getAxisCount());
		}
		
		if(OSValidator.isWindows()) {
			controller = Controllers.getController(3);
		} else {
			controller = Controllers.getController(0);
		}
	}
	
	public float getRawAxis(int axis) {
		if(OSValidator.isWindows()) {
			return controller.getAxisValue(axis+4);
		} else {
			return controller.getAxisValue(axis);
		}
	}
	
	public float getFilteredAxis(int axis, float rate, float expo, float superRate) { // logistic yo
		return (float) bf_calc(getRawAxis(axis), rate, expo, superRate) / 10;
	}
	
	// BF rate calculation function
	private double bf_calc(double rcCommand, double rcRate, double expo, double superRate) {
	    double absRcCommand = Math.abs(rcCommand);
		
	    if(rcRate > 2.0)
	        rcRate = rcRate + (14.54 * (rcRate - 2.0));

	    if(expo != 0)
	        rcCommand = rcCommand * Math.pow(Math.abs(rcCommand), 3) * expo + rcCommand * (1.0 - expo);

	    double angleRate = 200.0 * rcRate * rcCommand;
	    if(superRate != 0){
	        double rcSuperFactor = 1.0 / (clamp(1.0 - absRcCommand * (superRate), 0.01, 1.00));
	        angleRate *= rcSuperFactor;
	    }

	    return angleRate;
	}
	
    private double clamp(double n, double minn, double maxn) {
    	return Math.max(Math.min(maxn, n), minn);
    }
}