import java.util.Scanner;

public class MainGame {
	private InventoryManagement inventory;
	private Levels level;
	private Player Chip;
	private MainEngine engine;
    private int currentLevel;
    private int allLevels = 2;

	public MainGame(){
		this.inventory = new InventoryManagement();
        this.currentLevel = 1;
		initializeLevel(currentLevel);
	}

    public Player getChip() {
        return Chip;
    }
    
    public Levels getLevel() {
        return level;
    }
    
    public MainEngine getEngine() {
        return engine;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getAllLevels() {
        return allLevels;
    }

    public void nextLevel() {
        if (currentLevel < allLevels) {
            currentLevel++;
            initializeLevel(currentLevel);
        }
    }

    //used to load the next level
    private void initializeLevel(int levelNum){

        int width = 21;
        int height = 11;
        int neededChips;
        switch(levelNum){
            case 1:
                neededChips = 3;
                break;
            case 2:
                neededChips = 3;
                break;
            default:
                neededChips = 0;
                break;
        }
        
        this.inventory = new InventoryManagement();
        this.level = new Levels(false, width, height, neededChips, levelNum);
        
        // Get starting position from level map
        int startX = level.getPlayerStartX();
        int startY = level.getPlayerStartY();
        
        this.Chip = new Player(0, true, startX, startY, inventory);
        this.engine = new MainEngine(Chip, level);
    }

    public void play(){
        Scanner sc = new Scanner(System.in);
        currentLevel = 1; // Reset to level 1 when starting a new game
        initializeLevel(currentLevel);
        
        while(currentLevel <= allLevels){
            while(!level.isComplete() && Chip.getLife()){
                printMap();
                System.out.println("Chips: "+Chip.getChipsCollected()+" / "+level.getRequiredChips());
                System.out.print("Move (WASD): ");
                char input = sc.next().charAt(0);
                engine.Movement(input);
            }
            
            if(!Chip.getLife()){
                System.out.println("You died!");
                break;
            }
            
            if(level.isComplete()){
                if(currentLevel < allLevels){
                    System.out.println("Level "+currentLevel+" Complete!");
                    System.out.println("Proceeding to Level "+(currentLevel+1));
                    currentLevel++;
                    initializeLevel(currentLevel);
                } else {
                    System.out.println("All levels completed!");
                    break;
                }
            }
        }
        endgame();
        askPlayAgain(sc);
    }

    public void endgame(){
        if (level.isComplete()&& currentLevel==allLevels){
            System.out.println("You Win!");
        }
        else if (Chip.getLife()==false){

            System.out.println("Game Over!");
        }
    }

    private void askPlayAgain(Scanner sc) {
    System.out.println("Would you like to play again? (y/n)");
    char response = sc.next().toLowerCase().charAt(0);
    if (response == 'y') {
        play(); // Start over
    } else {
        System.out.println("Thanks for playing!");
    }
}

	public void printMap(){
	
		for(int i = 0; i < level.getHeight(); i++) {
			String row = level.getRow(i);
			if(i == Chip.getYpos()) {
				StringBuilder sb = new StringBuilder(row);
				sb.setCharAt(Chip.getXpos(), 'c');
				System.out.println(sb.toString());
			} else {
				System.out.println(row);
			}
		}
	}
}
