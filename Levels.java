import java.util.*;

public class Levels{

    private ArrayList<ArrayList<Tiles>> Map;
    private Player Chip;
    private int RequiredChips;
    private boolean Complete;
    private int Width;
    private int Height;
    private int level;
    private int levelNum;

    public Levels(boolean isComplete, int Width, int Height, int RequiredChips,int level){
        this.Width = Width;
        this.Height = Height;
        this.RequiredChips = RequiredChips;
        this.Complete = isComplete;
        this.level = level;
        this.Map = new ArrayList<>();
        loadLevel(level);
    }
    private void loadLevel(int levelNum) {
            switch(levelNum) {
                case 1:
                    loadLevel1();
                    break;
                case 2:
                    loadLevel2();
                    break;
            }
        }

    //These are methods used for the levels layout
    private void loadLevel1() {
        CreateMap("#####################");
        CreateMap("#c>>>>>>> *         #");
        CreateMap("##########V         #");
        CreateMap("#E  #     V         #");
        CreateMap("#  ^#     V         #");
        CreateMap("#  ^#       >>>>  * #");
        CreateMap("###^#           #   #");
        CreateMap("#  *   <<<<<<<<<    #");
        CreateMap("#                   #");
        CreateMap("#                   #");
        CreateMap("#####################");
    }
    private void loadLevel2() {
        CreateMap("#####################");
        CreateMap("#c           *   #f #");
        CreateMap("####             #  #");
        CreateMap("#Jb#                #");
        CreateMap("#V^#####~~~~~~~~#####");
        CreateMap("#V^#             #* #");
        CreateMap("#V^#             #  #");
        CreateMap("#                  r#");
        CreateMap("#######&&&&&&########");
        CreateMap("#*R              B E#");
        CreateMap("#####################");
    }

    //getter methods of Levels
    public int getRequiredChips(){
        return RequiredChips;
    }
    public boolean isComplete(){
        return Complete;
    }
    public void setComplete (boolean complete){
        this.Complete = complete;
    }
    public  int getWidth(){
        return Width;
    }
    public int getHeight(){
        return Height;
    }
    public int getLevel(){
        return level;
    }
     public int getPlayerStartX() {
        for (int y = 0; y < Height; y++) {
            for (int x = 0; x < Width; x++) {
                if (Map.get(y).get(x).getSign() == 'c') {
                    return x;
                }
            }
        }
        return 1; // Default fallback
    }
    
    public int getPlayerStartY() {
        for (int y = 0; y < Height; y++) {
            for (int x = 0; x < Width; x++) {
                if (Map.get(y).get(x).getSign() == 'c') {
                    return y;
                }
            }
        }
        return 1; // Default fallback
    }

    //This resets the level
    public void resetLevel(){
        this.Complete = false;
        this.Map.clear();
        loadLevel(level);
    }

    public String getRow(int rowIndex){ //fix this later
        if (rowIndex >= 0 && rowIndex < Map.size()) {
            StringBuilder rowString = new StringBuilder();
            for(Tiles tile : Map.get(rowIndex)) {
                rowString.append(tile.getSign());
            }
            return rowString.toString();
        } else {
            System.out.println("Invalid row index!");
            return "";
        }
        
        
    }

//this is used to fill the map with tiles, and items
    public void CreateMap(String line) {
        ArrayList<Tiles> Row = new ArrayList<>();

        for (int j = 0; j < line.length(); j++) {
            char c = line.charAt(j);

            switch (c) {
                case ' ':
                    Row.add(Tiles.createFloor());
                    break;
                case 'c':
                    Row.add(Tiles.createFloor());
                    break;
                case '~':
                    Row.add(Tiles.createWater());
                    break;
                case '&':
                    Row.add(Tiles.createFire());
                    break;
                case '#':
                    Row.add(Tiles.createWall());
                    break;
                case '>':
                    Row.add(Tiles.createForceFloor("right"));
                    break;
                case '<':
                    Row.add(Tiles.createForceFloor("left"));
                    break;
                case '^':
                    Row.add(Tiles.createForceFloor("up"));
                    break;
                case 'V':
                    Row.add(Tiles.createForceFloor("down"));
                    break;
				case 'R':
				    Row.add(Tiles.createRedDoor());
					break;
				case 'B':
				    Row.add(Tiles.createBlueDoor());
					break;
				case 'r':
                    Row.add(Tiles.createFloorWithItem(Item.createRedKey()));
                    break;
                case 'b':
                    Row.add(Tiles.createFloorWithItem(Item.createBlueKey()));
                    break;
				case 'f':
                    Row.add(Tiles.createFloorWithItem(Item.createFlippers()));
                    break;
				case 'J':
                    Row.add(Tiles.createFloorWithItem(Item.createFireBoots()));
                    break;
				case '*':
                    Row.add(Tiles.createFloorWithChip());
                    break;
                case 'E':
                    Row.add(Tiles.createExit());
                    break;
                default:
                    System.out.println("Invalid tile code: " + c);
                    break;
            }
        }
        Map.add(Row);
    }

    public boolean inBoundary(int x, int y){
        if(x >= 0 && x < Width && y>=0  && y< Height){
            return true;
        }
        return false;
    }

    public void PlaceTile(int x, int y, Tiles tile){
        if(inBoundary(x,y)==true)
        {
            Map.get(y).set(x, tile);
        }
    }

    public Tiles getTile(int x, int y){
        if(inBoundary(x,y)==true)
        {
            return Map.get(y).get(x);
        }
        else{
            return Tiles.createWall();
        }
    }

    public String[] getMapDisplay(int playerX, int playerY) {
            String[] display = new String[Height];
            for (int i = 0; i < Height; i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < Width; j++) {
                    if (i == playerY && j == playerX) {
                        row.append('c');
                    } else {
                        row.append(Map.get(i).get(j).getSign());
                    }
                }
                display[i] = row.toString();
            }
            return display;
        }

}
