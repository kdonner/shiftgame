package Shift;

public class PickupItem extends LevelObject 
{
	Pickups itemType;
	
	public PickupItem(Shift parent, Pickups type)
	{
		super(parent, ObjectType.ITEM, parent.getImage(type.img));
		itemType = type;
	}
}
