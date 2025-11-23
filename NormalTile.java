public class NormalTile extends Tiles {
	

    private Item hasItem;
    private boolean hasChips;

    public NormalTile(String tileName, boolean hasChips){
        super(tileName, true,'*');
        this.hasItem = null;
        this.hasChips = hasChips;
		sign = '*';
    }

    public NormalTile(String tileName, Item hasItem) {
        super(tileName, true,' ');   //i have questions is this really false
        this.hasItem = hasItem;
		sign = hasItem.getSign();
        this.hasChips = false;
    }
    public NormalTile(String tileName){
        super(tileName, true,' ');
        this.hasItem = null;
        this.hasChips = false;
		sign = ' ';
    }

    @Override
    public boolean checkHasItem(){
        return this.hasItem !=null;
    }
    @Override
    public boolean hasChip(){
        return this.hasChips;
    }

    public Tiles getNormalTile() {
        return this;
    }
    @Override
    public Item collectItem(){
        Item item = this.hasItem;
        this.hasItem = null;
        this.sign = ' ';
        return item;
    }
    @Override
    public boolean collectChip(){
        if(this.hasChips){
            this.hasChips = false;
            this.sign = ' ';
            return true;
        }
        return false;
    }
}