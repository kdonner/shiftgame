package Shift;

public enum Actions {
	RUN("Run"),
	JUMP("Jump"),
	STAND("Stand"),
	PUSH("Push");
	
	public String name;
	Actions(String name)
	{
		this.name = name;
	}
}
