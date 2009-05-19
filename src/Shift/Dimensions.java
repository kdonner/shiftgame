package Shift;

public enum Dimensions {

	//TODO Adjust Terminal Velocities to actual values
	DIM0 (0, true, 10), // This is just for the zeroth dimension which the player should never enter
	DIM1 ((Constants.GRAVITY / Shift.FRAME_RATE), true, 10),
	DIM2 ((Constants.GRAVITY / Shift.FRAME_RATE) / 4, true, 10),
	DIM3 ((Constants.GRAVITY / Shift.FRAME_RATE) * 4, true, 10),
	DIM4 ((Constants.GRAVITY / Shift.FRAME_RATE), false, 10),
	DIM5 ((Constants.GRAVITY / Shift.FRAME_RATE), true, 10),
	DIM6 ((Constants.GRAVITY / Shift.FRAME_RATE) / 2, true, 10),
	DIM7 ((Constants.GRAVITY / Shift.FRAME_RATE) / 4, false, 10),
	DIM8 ((Constants.GRAVITY / Shift.FRAME_RATE) * 4, false, 10);
	
	private final double gravity;
	private final boolean gravIsDown;
	final double terminalVelocity;
	
	Dimensions(double grav, boolean isDown, double term)
	{
		gravity = grav;
		gravIsDown = isDown;
		terminalVelocity = term;
	}
	
	public double getGravity()
	{
		if(gravIsDown)
			return gravity;
		else
			return -gravity;
	}
}
