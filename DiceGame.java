import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import java.util.Random;

public class DiceGame extends JFrame implements ActionListener {
    private JLabel diceLabel1, diceLabel2, resultLabel;
    private JButton rollButton, resetButton;
    private Random random;
    private String resourcePath = "resources/";
    private Timer player2Timer;

    public DiceGame() {
        setTitle("Dice Game");
        setSize(500, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize components
        diceLabel1 = new JLabel(new ImageIcon(resourcePath + "dice1.jpg"));
        diceLabel2 = new JLabel(new ImageIcon(resourcePath + "dice1.jpg"));
        resultLabel = new JLabel("Click ROLL to start!", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel dicePanel = new JPanel();
        dicePanel.add(diceLabel1);
        dicePanel.add(diceLabel2);

        rollButton = new JButton("Roll Dice");
        resetButton = new JButton("Reset");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(rollButton);
        buttonPanel.add(resetButton);

        add(resultLabel, BorderLayout.NORTH);
        add(dicePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        rollButton.addActionListener(this);
        resetButton.addActionListener(this);

        random = new Random();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rollButton) {
            rollButton.setEnabled(false);
            resultLabel.setText("Player 1 is rolling...");
            playSound("roll.wav");

            // Roll for player 1
            int dice1 = random.nextInt(6) + 1;
            diceLabel1.setIcon(new ImageIcon(resourcePath + "dice" + dice1 + ".jpg"));

            // Delay for player 2 roll using Timer
            player2Timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    playSound("roll.wav");
                    int dice2 = random.nextInt(6) + 1;
                    diceLabel2.setIcon(new ImageIcon(resourcePath + "dice" + dice2 + ".jpg"));

                    String result;
                    if (dice1 > dice2) {
                        result = "Player 1 wins!";
                    } else if (dice2 > dice1) {
                        result = "Player 2 wins!";
                    } else {
                        result = "It's a tie!";
                    }

                    resultLabel.setText(result);
                    playSound("win.wav");
                    rollButton.setEnabled(true);
                    player2Timer.stop();
                }
            });
            player2Timer.setRepeats(false);
            player2Timer.start();
        }

        if (e.getSource() == resetButton) {
            diceLabel1.setIcon(new ImageIcon(resourcePath + "dice1.jpg"));
            diceLabel2.setIcon(new ImageIcon(resourcePath + "dice1.jpg"));
            resultLabel.setText("Click ROLL to start!");
            playSound("reset.wav");
        }
    }

    private void playSound(String soundFile) {
        try {
            File file = new File(resourcePath + soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Sound error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new DiceGame();
    }
}
