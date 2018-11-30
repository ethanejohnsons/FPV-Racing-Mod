package com.bluevista.fpvracing.controls;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import com.bluevista.fpvracing.OSValidator;
import com.bluevista.fpvracing.math.BetaflightHelper;

public class ControllerTaranis  implements IController {
	
	private Controller controller;
	
	public ControllerTaranis() {
		try {
			Controllers.create();
			Controllers.poll();		
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Controllers boi:");
		for(int i = 0; i < Controllers.getControllerCount(); i++) {
			System.out.println(Controllers.getController(i).getName());
		}
		
		if(OSValidator.isWindows()) {
			controller = Controllers.getController(5);
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
		return (float) BetaflightHelper.calculateRates(getRawAxis(axis), rate, expo, superRate) / 10;
	}
}