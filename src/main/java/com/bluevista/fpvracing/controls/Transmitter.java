package com.bluevista.fpvracing.controls;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import com.bluevista.fpvracing.math.BetaflightHelper;
import com.bluevista.fpvracing.util.OSValidator;

public class Transmitter {
	
	private Controller controller;
	
	public Transmitter() {
		try {
			Controllers.create();
			Controllers.poll();		
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		//if(OSValidator.isWindows()) {
		//	controller = Controllers.getController(1);
		//}
		
		controller = Controllers.getController(1); // hard coded : will fix later
	}
	
	public float getRawAxis(int axis) {
		if(OSValidator.isWindows()) {
			return controller.getAxisValue(axis+4); // + 4
		} else {
			return controller.getAxisValue(axis);
		}
	}
	
	public float getFilteredAxis(int axis, float rate, float expo, float superRate) { // logistic yo
		return (float) BetaflightHelper.calculateRates(getRawAxis(axis), rate, expo, superRate) / 10;
	}
}