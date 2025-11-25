import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GameGUI extends JFrame{
    private static final int TILE_SIZE=40;
    private Levels level;
    private MainGame game;
    private GamePanel gamePanel;

    public GameGUI(){
        game = new MainGame();
        createGUI();
    }

    private void createGUI(){
        setTitle("Chip's Challenge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public class GamePanel extends JPanel{

        private ArrayList<Image> sprites;
        private ArrayList<Character> spriteChars;
        private MainEngine engine;

        public GamePanel(){
            sprites = new ArrayList<>();
            spriteChars = new ArrayList<>();
            this.engine = game.getEngine();
            setPreferredSize(new Dimension(21 * TILE_SIZE, 11 * TILE_SIZE + 80));
            loadSprites();
            setKeyInputs();
            setFocusable(true);
            requestFocusInWindow();
        }

         private void setKeyInputs() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    handleKeyPress(e);
                }
            });
        }

        
        private void handleKeyPress(KeyEvent e) {
            char keyChar = e.getKeyChar();
            if (keyChar == 'w' || keyChar == 'a' || keyChar == 's' || keyChar == 'd' || keyChar == 'W' || keyChar == 'A' || keyChar == 'S' || keyChar == 'D') {
        
                boolean moved = engine.Movement(keyChar);
                
                if (moved) {
                    repaint();
                    if(game.getLevel().isComplete()) {
                        if (game.getCurrentLevel() < game.getAllLevels()) {
                            game.nextLevel();
                            this.engine = game.getEngine();
                            repaint();
                        }
                        else{
                            showGameOverDialog();
                        }
                    }
                    else if (!game.getChip().getLife()) {
                        resetLevelDisplay();
                    }
                }
            }
        }

        //This function resets the entire game
        private void resetGame() {
        game = new MainGame();
        this.engine = game.getEngine();
        repaint();
        }

        private void resetLevelDisplay() {
            int currentLevel = game.getCurrentLevel();
            game.getLevel().resetLevel();
            repaint();

            int startingX = game.getLevel().getPlayerStartX();
            int startingY = game.getLevel().getPlayerStartY();   
            game.getChip().respawn(startingX, startingY);

            this.engine = new MainEngine(game.getChip(), game.getLevel());
            repaint();
        }
         
        private Image loadImage(String filename) {
            try {
                BufferedImage img = ImageIO.read(new File(filename));
                return img.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
            } catch (Exception e) {
                System.err.println("Failed to load: " + filename);
                return null;
            }
        }

        public void addSprite(char spriteChar, String spriteName){
            spriteChars.add(spriteChar);
            sprites.add(loadImage(spriteName));
        }

        //This function assigns the sprite images to their respective char symbol
        public void loadSprites(){
            addSprite(' ', "sprites/floor.png");
            addSprite('#', "sprites/wall.png");
            addSprite('~', "sprites/water.png");
            addSprite('&', "sprites/fire.png");
            addSprite('E', "sprites/exit.png");
            addSprite('R', "sprites/red_door.png");
            addSprite('B', "sprites/blue_door.png");
            addSprite('*', "sprites/chip_item.png");
            addSprite('c', "sprites/player.png");
            addSprite('r', "sprites/red_key.png");
            addSprite('b', "sprites/blue_key.png");
            addSprite('f', "sprites/flippers.png");
            addSprite('J', "sprites/fire_boots.png");
            addSprite('<', "sprites/force_left.png");
            addSprite('>', "sprites/force_right.png");
            addSprite('^', "sprites/force_up.png");
            addSprite('V', "sprites/force_down.png");
        }

        private Image getSprite(char sign) {
            for (int i = 0; i < spriteChars.size(); i++) {
                if (spriteChars.get(i) == sign) {
                    return sprites.get(i);
                }
            }
            return null;
        }
         protected void paintComponent(Graphics toPaint){
            super.paintComponent(toPaint);
            renderGame(toPaint);
        }

        //fix later
        private void renderGame(Graphics g) {
            Levels level = game.getLevel();
            Player chip = game.getChip();
                
            for (int y = 0; y < level.getHeight(); y++) {
                for (int x = 0; x < level.getWidth(); x++) {
                    Tiles tile = level.getTile(x, y);
                    char sign = tile.getSign();
                        
                    if (x == chip.getXpos() && y == chip.getYpos()) {
                            generateTiles(g, x, y, 'c');
                    } else {
                            generateTiles(g, x, y, sign);
                    }
                }
            }
            displayHUD(g);
        }

        private void generateTiles(Graphics pic, int x, int y, char sign){
            int xPixel= x*TILE_SIZE;
            int yPixel= y*TILE_SIZE;

            Image sprite = getSprite(sign);
            if (sprite != null) {
                    pic.drawImage(sprite, xPixel, yPixel, this);
            } 
            else {
                pic.setColor(getColorForTile(sign));
                pic.fillRect(xPixel, yPixel, TILE_SIZE, TILE_SIZE);
                pic.setColor(Color.BLACK);
                pic.drawRect(xPixel, yPixel, TILE_SIZE, TILE_SIZE);
            }
        }

        private Color getColorForTile(char sign) {
            switch (sign) {
                case ' ': return Color.LIGHT_GRAY;
                case '#': return Color.DARK_GRAY;
                case '~': return Color.BLUE;
                case '&': return Color.RED;
                case 'E': return Color.GREEN;
                case 'R': return Color.RED;
                case 'B': return Color.BLUE;
                case '*': return Color.YELLOW;
                case 'r': return Color.RED;
                case 'b': return Color.BLUE;
                case 'f': return Color.CYAN;
                case 'J': return Color.ORANGE;
                case '<': case '>': case '^': case 'V': return Color.WHITE;
                case 'c': return Color.ORANGE;
                default: return Color.WHITE;
            }
        }

        private void displayHUD(Graphics display) {
            int hudY = 11 * TILE_SIZE + 10;
            display.setColor(Color.BLACK);
            display.drawString("Chips: " + game.getChip().getChipsCollected() + " / " + game.getLevel().getRequiredChips(), 10, hudY);

            displayInventoryIcons(display, hudY + 40);
        }

    private void displayInventoryIcons(Graphics g, int startY) {
        InventoryManagement inventory = game.getChip().getInventory();
        int xPos = 10;
        int iconSize = 20;
        
        g.drawString("Inventory: ", xPos, startY);
        xPos += 80;
        
        //Display the red keys in the inventory
        if (inventory.getRedKeys() > 0) {
            g.setColor(Color.RED);
            g.fillRect(xPos, startY - 15, iconSize, iconSize);
            g.setColor(Color.BLACK);
            g.drawString("x" + inventory.getRedKeys(), xPos + 25, startY);
            xPos += 60;
        }
        
        //Display the blue keys in the inventory
        if (inventory.getBlueKeys() > 0) {
            g.setColor(Color.BLUE);
            g.fillRect(xPos, startY - 15, iconSize, iconSize);
            g.setColor(Color.BLACK);
            g.drawString("x" + inventory.getBlueKeys(), xPos + 25, startY);
            xPos += 60;
        }
        
        //Display the flippers in the inventory
        if (inventory.checkFlipper()) {
            g.setColor(Color.CYAN);
            g.fillRect(xPos, startY - 15, iconSize, iconSize);
            g.setColor(Color.BLACK);
            g.drawString("F", xPos + 8, startY - 3);
            xPos += 40;
        }

        //Display the fire boots in the inventory
        if (inventory.checkFireBoot()) {
            g.setColor(Color.ORANGE);
            g.fillRect(xPos, startY - 15, iconSize, iconSize);
            g.setColor(Color.BLACK);
            g.drawString("B", xPos + 8, startY - 3);
            xPos += 40;
        }
    }

        private void showGameOverDialog() {
            String message = game.getLevel().isComplete() ? 
                "Congratulations! You completed the level!" : "Game Over!";
            
            JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            
            int response = JOptionPane.showConfirmDialog(this, "Play again?", "Restart", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
                repaint();
            } 
            else {
                System.exit(0);
            }
        }

    }

}