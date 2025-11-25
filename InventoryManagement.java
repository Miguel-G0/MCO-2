public class InventoryManagement{

private int redKeys;
private int blueKeys;
private boolean hasFlipper;
private boolean hasFireboots;



public InventoryManagement(){
    this.redKeys = 0;
    this.blueKeys = 0;
    this.hasFlipper = false;
    this.hasFireboots = false;
}

public void addItem(Item item){
    if(item.getName().equals("RedKey")){
        redKeys++;
    }else if(item.getName().equals("BlueKey")){
        blueKeys++;
    }else if(item.getName().equals("Flippers")){
        hasFlipper = true;
    }else if(item.getName().equals("FireBoots")){
        hasFireboots = true;
    }
}

public boolean checkFlipper(){
    return hasFlipper;
}

public boolean checkFireBoot(){
    return hasFireboots;
}

public int getRedKeys(){
    return redKeys;
}
public int getBlueKeys(){
    return blueKeys;
}

public boolean useRedKey(){
    if(redKeys>0){
        redKeys--;
        return true;
    }
    return false;
}

public boolean useBlueKey(){
    if(blueKeys>0){
        blueKeys--;
        return true;
    }
    return false;
}
}
