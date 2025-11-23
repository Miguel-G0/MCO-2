import java.util.Scanner;
import javax.swing.*;

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
		nextLevel(currentLevel);
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

    //used to load the next level
    private void nextLevel(int levelNum){
        int neededChips = 3;
        int width = 21;
        int height = 11;
        
        switch(levelNum){
            case 1:
                neededChips = 3;
                break;
            case 2:
                neededChips = 3;
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

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> new GUI());
    }

    public void displayMainMenu(){
	System.out.println("===CHIP'S CHALLENGE===");
        System.out.println("	1. Start Game");
        System.out.println("	2. Exit");
        Scanner choice = new Scanner(System.in);
        switch(choice.nextInt()){
            case 1:
                play();
                break;
            case 2:
                System.out.println("Game shutting down...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
                displayMainMenu();
                break;
        }	
        choice.close();
    }


    public void play(){
         Scanner sc = new Scanner(System.in);
    currentLevel = 1; // Reset to level 1 when starting a new game
    nextLevel(currentLevel);
    
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
                nextLevel(currentLevel);
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







