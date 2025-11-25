
public class Player{
    private int ChipsCollected;
    private boolean isAlive;
    private int Ypos;
    private int Xpos;
    private InventoryManagement Inventory;
	private char sign;


    public Player(int ChipsCollected, boolean isAlive, int StartposX, int StartposY, InventoryManagement Inventory){
        this.ChipsCollected = ChipsCollected;
        this.isAlive = isAlive;
        this.Ypos = StartposY;
        this.Xpos = StartposX;
        this.Inventory = Inventory;
		sign = 'c';
    }

    public boolean getLife(){
        return this.isAlive;
    }
    public int getChipsCollected(){
        return this.ChipsCollected;
    }
    public int getXpos(){
        return this.Xpos;
    }
    public int getYpos(){
        return this.Ypos;
    }
    public InventoryManagement getInventory(){
        return this.Inventory;
    }
    public void collectChip(){
        this.ChipsCollected++;
    }
    public void collectItem(Item item){
        Inventory.addItem(item);
    }

    public void nextPos(int nextX, int nextY){
        this.Xpos = nextX;
        this.Ypos = nextY;
    }

    public void die(){
        this.isAlive = false;
    }
    public void respawn(int StartingX, int StartingY){
        this.isAlive = true;
        this.Xpos = StartingX;
        this.Ypos = StartingY;
        this.ChipsCollected = 0;
        this.Inventory = new InventoryManagement();
    }
}
