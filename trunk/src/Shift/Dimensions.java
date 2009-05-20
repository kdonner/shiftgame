package Shift;

public enum Dimensions {

	//TODO Adjust Terminal Velocities to actual values
	DIM0 (0, true, 10, 0), // This is just for the zeroth dimension which the player should never enter
	DIM1 ((Constants.GRAVITY / Shift.FRAME_RATE), true, 10, 1),
	DIM2 ((Constants.GRAVITY / Shift.FRAME_RATE) / 4, true, 10, 2),
	DIM3 ((Constants.GRAVITY / Shift.FRAME_RATE) * 4, true, 10, 3),
	DIM4 ((Constants.GRAVITY / Shift.FRAME_RATE), false, 10, 4),
	DIM5 ((Constants.GRAVITY / Shift.FRAME_RATE), true, 10, 5),
	DIM6 ((Constants.GRAVITY / Shift.FRAME_RATE) / 2, true, 10, 6),
	DIM7 ((Constants.GRAVITY / Shift.FRAME_RATE) / 4, false, 10, 7),
	DIM8 ((Constants.GRAVITY / Shift.FRAME_RATE) * 4, false, 10, 8);
	
	private final double gravity;
	private final boolean gravIsDown;
	int dimNum;
	final double terminalVelocity;
	
	Dimensions(double grav, boolean isDown, double term, int num)
	{
		gravity = grav;
		gravIsDown = isDown;
		terminalVelocity = term;
		dimNum = num;
	}
	
	public double getGravity()
	{
		if(gravIsDown)
			return gravity;
		else
			return -gravity;
	}
}
