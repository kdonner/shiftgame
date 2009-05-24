package Shift;

public enum Dimensions {

	//TODO Adjust Terminal Velocities to actual values
	DIM0 (0, true, 10, 0, new float[]{0f, 0f, 0f, 1f}), // This is just for the zeroth dimension which the player should never enter
	DIM1 ((Constants.GRAVITY / Shift.FRAME_RATE), true, 20, 1, new float[]{0.9f, 0.9f, 0.9f, 1f}),
	DIM2 ((Constants.GRAVITY / Shift.FRAME_RATE) / 4, true, 20, 2, new float[]{0.5f, 0.5f, 1f, 1f}),
	DIM3 ((Constants.GRAVITY / Shift.FRAME_RATE) * 4, true, 20, 3, new float[]{1f, 0.5f, 0.5f, 1f}),
	DIM4 ((Constants.GRAVITY / Shift.FRAME_RATE), false, 20, 4, new float[]{0.0f, 0.9f, 0.7f, 1f}),
	DIM5 ((Constants.GRAVITY / Shift.FRAME_RATE), true, 20, 5, new float[]{0f, 0f, 0f, 1f}),
	DIM6 ((Constants.GRAVITY / Shift.FRAME_RATE) / 2, true, 20, 6, new float[]{0f, 0f, 0f, 1f}),
	DIM7 ((Constants.GRAVITY / Shift.FRAME_RATE) / 4, false, 20, 7, new float[]{0f, 0f, 0f, 1f}),
	DIM8 ((Constants.GRAVITY / Shift.FRAME_RATE) * 4, false, 20, 8, new float[]{0f, 0f, 0f, 1f});
	
	private final double gravity;
	private final boolean gravIsDown;
	int dimNum;
	float[] filters;
	final double terminalVelocity;
	
	Dimensions(double grav, boolean isDown, double term, int num, float[] filter)
	{
		gravity = grav;
		gravIsDown = isDown;
		terminalVelocity = term;
		dimNum = num;
		filters = filter;
	}
	
	public double getGravity()
	{
		if(gravIsDown)
			return gravity;
		else
			return -gravity;
	}
}
