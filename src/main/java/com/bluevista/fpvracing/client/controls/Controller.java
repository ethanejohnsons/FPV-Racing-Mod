package com.bluevista.fpvracing.client.controls;

import com.bluevista.fpvracing.client.utils.BetaflightHelper;

import static org.lwjgl.glfw.GLFW.*;

public class Controller {

	public static int CONTROLLER_ID = 0; // TODO read in from config later???

	public static float getAxis(int axis) {
		return glfwGetJoystickAxes(CONTROLLER_ID).get(axis);
	}

	public float getBetaflightAxis(int axis, float rate, float expo, float superRate) { // logistic yo
		return (float) BetaflightHelper.calculateRates(getAxis(axis), rate, expo, superRate) / 10;
	}
}