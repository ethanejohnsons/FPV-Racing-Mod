package com.bluevista.fpvracing.controls;

public interface IController {
	public float getRawAxis(int axis);
	public float getFilteredAxis(int axis, float rate, float expo, float superRate);
}
