public class Item{
   private String itemName;
    protected char sign;
    private String itemType;
    
    public Item(String itemType) {
        this.itemType = itemType;
        
        switch(itemType) {
            case "RedKey":
                this.itemName = "RedKey";
                this.sign = 'r';
                break;
            case "BlueKey":
                this.itemName = "BlueKey";
                this.sign = 'b';
                break;
            case "Flippers":
                this.itemName = "Flippers";
                this.sign = 'f';
                break;
            case "FireBoots":
                this.itemName = "FireBoots";
                this.sign = 'J';
                break;
            default:
                this.itemName = "UnknownItem";
                this.sign = '?';
        }
    }
    
    public static Item createRedKey() {
        return new Item("RedKey");
    }
    
    public static Item createBlueKey() {
        return new Item("BlueKey");
    }
    
    public static Item createFlippers() {
        return new Item("Flippers");
    }
    
    public static Item createFireBoots() {
        return new Item("FireBoots");
    }

    public String getName() {
        return itemName;
    }
    
    public String getType() {
        return itemType;
    }
    
    public char getSign() {
        return sign;
    }
    
    public boolean isRedKey() {
        return "RedKey".equals(itemType);
    }
    
    public boolean isBlueKey() {
        return "BlueKey".equals(itemType);
    }
    
    public boolean isFlippers() {
        return "Flippers".equals(itemType);
    }
    
    public boolean isFireBoots() {
        return "FireBoots".equals(itemType);
    }
}