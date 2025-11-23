public class MainEngine {
    
    private Levels level;
    private Player Chip;

    public MainEngine(Player Chip, Levels level){
        this.level = level;
        this.Chip = Chip;
    }

    public boolean canMove(Tiles tileMove){
        if (tileMove.isCanWalk()==false){
            return false;
        }
        return true;
    }
    public boolean Movement(char WASD){
    int X = Chip.getXpos();
    int Y = Chip.getYpos();
    int nextX = X;
    int nextY = Y;
        switch(WASD){
            case 'W':
                nextY--;
                break;
            case 'w':
                nextY--;
                break;
            case 'S':
                nextY++;
                break;
            case 's':
                nextY++;
                break;
            case 'A':
                nextX--;
                break;
            case 'a':
                nextX--;
                break;
            case 'D':
                nextX++;
                break;
            case 'd':
                nextX++;
                break;
            default:
                return false;
        }
        Tiles nextTile = level.getTile(nextX, nextY);
        if (level.inBoundary(nextX, nextY)==false){
            return false;
        }
        if(nextTile.getTileName().equals("Exit")){
            if(Chip.getChipsCollected()>=level.getRequiredChips()){
                level.setComplete(true);
                Chip.nextPos(nextX, nextY);
                return true;
            }
            else{
                return false;
            }
        }
        if(nextTile.isCanWalk()==false){

            if(nextTile.getTileName().equals("RedDoor")){
                if(Chip.getInventory().useRedKey()==true){
                    level.PlaceTile(nextX, nextY, Tiles.createFloor());
                    Chip.nextPos(nextX, nextY);
                    Tiles newTile = level.getTile(nextX, nextY);
                    if (newTile.isForceFloor()) {
                        slideOnForceFloor(newTile.getForceDirection());
                    }
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            if(nextTile.getTileName().equals("BlueDoor")){
                if(Chip.getInventory().useBlueKey()==true){
                   level.PlaceTile(nextX, nextY, Tiles.createFloor());
                    Chip.nextPos(nextX, nextY);
                    Tiles newTile = level.getTile(nextX, nextY);
                    if (newTile.isForceFloor()) {
                        slideOnForceFloor(newTile.getForceDirection());
                    }
                    return true;
                }
                else{
                    return false;
                }
            }
        }

        if(canMove(nextTile)==false){
            return false;
        }
        boolean collected= false;
        if (nextTile.hasChip()) {
            if (nextTile.collectChip()) {
                Chip.collectChip();
                collected= true;
            }
        }
        if (nextTile.checkHasItem()) {
            Item item = nextTile.collectItem();
            Chip.collectItem(item);
            collected= true;
       }
        if (collected==true) {
        Chip.nextPos(nextX, nextY);

        if (nextTile.isForceFloor()) {
            slideOnForceFloor(nextTile.getForceDirection());
        }   
            return true;
        }
        if(nextTile.getTileName().equals("Water")){
            if(Chip.getInventory().checkFlipper()==false){
                Chip.nextPos(nextX, nextY);
                Chip.die();
                return true;
            }
            else{
                Chip.nextPos(nextX, nextY);
                return true;
            }
        }
        if(nextTile.getTileName().equals("Fire")){
            if(Chip.getInventory().checkFireBoot()==false){
                Chip.nextPos(nextX, nextY);
                Chip.die();
                return true;
                }
            else{
                Chip.nextPos(nextX, nextY);
                return true;
            }
            
        }
        Chip.nextPos(nextX, nextY);
            if (nextTile.isForceFloor()) {
                slideOnForceFloor(nextTile.getForceDirection());
            }
            return true;
    }
    private void slideOnForceFloor(String direction) {


            boolean keepSliding = true;
            
            while (keepSliding) {
                int currentX = Chip.getXpos();
                int currentY = Chip.getYpos();
                int nextX = currentX;
                int nextY = currentY;
                
                switch (direction) {
                    case "up":
                        nextY--;
                        break;
                    case "down":
                        nextY++;
                        break;
                    case "left":
                        nextX--;
                        break;
                    case "right":
                        nextX++;
                        break;
                }
                
                if (!level.inBoundary(nextX, nextY)) {
                    break;
                }
                
                Tiles nextTile = level.getTile(nextX, nextY);
                
                if (nextTile.getTileName().equals("Exit")) {
                    if (Chip.getChipsCollected() >= level.getRequiredChips()) {
                        level.setComplete(true);
                        Chip.nextPos(nextX, nextY);
                    }
                    break;
                }
                if (!canMove(nextTile)) {
                    break;
                }
                if (nextTile.hasChip()) {
                    if (nextTile.collectChip()) {
                        Chip.collectChip();
                    }
                }
                if (nextTile.checkHasItem()) {
                    Item item = nextTile.collectItem();
                    Chip.collectItem(item);
                }
                
                if (nextTile.getTileName().equals("Water")) {
                    if (!Chip.getInventory().checkFlipper()) {
                        Chip.nextPos(nextX, nextY);
                        Chip.die();
                        break;
                    }
                }
                if (nextTile.getTileName().equals("Fire")) {
                    if (!Chip.getInventory().checkFireBoot()) {
                        Chip.nextPos(nextX, nextY);
                        Chip.die();
                        break;
                    }
                }
                
            Chip.nextPos(nextX, nextY);
            if (!nextTile.isForceFloor()) {
                keepSliding = false;
            } 
            else{
                direction = nextTile.getForceDirection();
            }
        }
    }
}