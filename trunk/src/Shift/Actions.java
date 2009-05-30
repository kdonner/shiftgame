package Shift;

public enum Actions {
	RUN("Run"),
	JUMP_TAKEOFF("JumpTakeOff"),
	JUMP_APEX("JumpApex"),
	JUMP_FALL("JumpFall"),
	FALL_FAST("FallFast"),
	STAND("Stand"),
	PUSH("Push");
	
	public String name;
	Actions(String name)
	{
		this.name = name;
	}
}
