import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame {


    private JButton startGame;
    private JButton exitGame;
    
    public MainMenuGUI() {
        setTitle("Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null); // Center the window

        startGame = new JButton("Start Game");
        exitGame = new JButton("Exit Game");

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // Actually start the game
            }
        });

        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(startGame);
        add(exitGame);
    }

    private void startGame() {
        // Close the menu and start the actual game
        this.dispose(); // Close the menu window
        
        // Start your main game GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameGUI(); // This starts your actual game
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainMenuGUI gui = new MainMenuGUI();
                gui.setVisible(true);
            }
        });
    }




















}