import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.*;

public class DiceGameGUI extends JFrame implements ActionListener {
    private JLabel diceLabel1, diceLabel2, resultLabel;
    private JButton rollButton, resetButton;
    private Random random;

    public DiceGameGUI() {
        setTitle("Dice Game");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        random = new Random();

        // Panel for dice
        JPanel dicePanel = new JPanel();
        diceLabel1 = new JLabel(new ImageIcon("resources/dice1.jpg"));
        diceLabel2 = new JLabel(new ImageIcon("resources/dice1.jpg"));
        dicePanel.add(diceLabel1);
        dicePanel.add(diceLabel2);

        // Result Label
        resultLabel = new JLabel("Click 'Roll' to play!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Buttons
        rollButton = new JButton("Roll Dice");
        resetButton = new JButton("Reset");
        rollButton.addActionListener(this);
        resetButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(rollButton);
        buttonPanel.add(resetButton);

        // Layout
        setLayout(new BorderLayout());
        add(dicePanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void playSound(String fileName) {
        try {
            File file = new File("resources/" + fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        }
    }

    private ImageIcon getDiceImage(int value) {
        return new ImageIcon("resources/dice" + value + ".jpg");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rollButton) {
            int dice1 = random.nextInt(6) + 1;
            int dice2 = random.nextInt(6) + 1;

            diceLabel1.setIcon(getDiceImage(dice1));
            diceLabel2.setIcon(getDiceImage(dice2));

            if (dice1 > dice2) {
                resultLabel.setText("🎉 Player 1 Wins!");
                playSound("win.wav");
            } else if (dice2 > dice1) {
                resultLabel.setText("🎉 Player 2 Wins!");
                playSound("win.wav");
            } else {
                resultLabel.setText("😮 It's a Draw!");
                playSound("roll.wav");
            }
        } else if (e.getSource() == resetButton) {
            diceLabel1.setIcon(getDiceImage(1));
            diceLabel2.setIcon(getDiceImage(1));
            resultLabel.setText("Click 'Roll' to play!");
            playSound("reset.wav");
        }
    }
}
