public class Tiles {
  
    private String tileName;
    private boolean canWalk;
    protected char sign;
    private boolean occupied;
    private boolean isForceFloor;
    private String forceDirection;
    private boolean hasChip;
    private Item item;
    
    public Tiles(String tileName, boolean canWalk, char sign) {
        this.tileName = tileName;
        this.canWalk = canWalk;
        this.sign = sign;
        this.occupied = false;
        this.isForceFloor = false;
        this.forceDirection = null;
        this.hasChip = false;
        this.item = null;
    }
    
    //methods to create different tile types
    public static Tiles createFloor() {
        return new NormalTile("Floor");
    }
    
    public static Tiles createFloorWithItem(Item item) {
        return new NormalTile("Floor", item);
    }
    
    public static Tiles createFloorWithChip() {
        return new NormalTile("Floor", true);
    }
    public static Tiles createWall() {
        return new WallTile("Wall");
    }
    
    public static Tiles createWater() {
        return new WaterTile("Water");
    }
    
    public static Tiles createFire() {
        return new FireTile("Fire");
    }

  	public static Tiles createIce() {
		return new IceTile("Ice");
	}
  
    public static Tiles createRedDoor() {
        return new RedDoor("RedDoor");
    }
    
    public static Tiles createBlueDoor() {
        return new BlueDoor("BlueDoor");
    }
    
    public static Tiles createExit() {
        return new ExitTile("Exit");
    }
    
    public static Tiles createForceFloor(String direction) {
        return new ForceFloor("Force", direction);
    }
    public char getSign() {
        return sign;
    }
    
    public void setSign(char sign) {
        this.sign = sign;
    }
    
    public void occupy() {
        occupied = true;
        sign = 'c';
    }
    
    public boolean isOccupied() {
        return occupied;
    }
    
    public boolean isForceFloor() {
        return isForceFloor;
    }
    
    public String getForceDirection() {
        return forceDirection;
    }
    
    public String getTileName() {
        return tileName;
    }
    
    public boolean isCanWalk() {
        return canWalk;
    }
    
    public void setCanWalk(boolean canWalk) {
        this.canWalk = canWalk;
    }
    
    public boolean hasChip() {
        return hasChip;
    }
    
    public void setHasChip(boolean hasChip) {
        this.hasChip = hasChip;
    }
    
    public boolean checkHasItem() {
        return item != null;
    }
    
    public boolean collectChip() {
        if (hasChip) {
            hasChip = false;
            if (item == null) {
                sign = ' ';
            }
            return true;
        }
        return false;
    }
    
    public Item collectItem() {
        Item collectedItem = item;
        item = null;
        sign = ' ';
        return collectedItem;
    }
}

