import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessGameGUI extends JFrame {
    private JTextField guessInputField;
    private JLabel resultLabel, titleLabel, attemptLabel;
    private JButton guessButton, resetButton;
    private int randomNumber;
    private int maxAttempts;
    private int attemptsMade;

    public NumberGuessGameGUI(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        this.randomNumber = generateRandomNumber();
        this.attemptsMade = 0;

        setTitle("Number Guessing Game");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        getContentPane().setBackground(new Color(70, 130, 180));

        titleLabel = new JLabel("Welcome to the Number Guessing Game!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        JLabel promptLabel = new JLabel("Guess a number between 1 and 50:");
        promptLabel.setForeground(new Color(255, 140, 0));

        guessInputField = new JTextField(10);
        guessButton = new JButton("Guess");
        resetButton = new JButton("Reset Game");
        resultLabel = new JLabel("");
        attemptLabel = new JLabel("Attempts left: " + (maxAttempts - attemptsMade));

        guessButton.setBackground(new Color(255, 165, 0));
        guessButton.setForeground(Color.WHITE);
        resetButton.setBackground(new Color(220, 20, 60)); // Crimson color
        resetButton.setForeground(Color.WHITE);
        resultLabel.setForeground(Color.WHITE);
        attemptLabel.setForeground(Color.WHITE);

        add(titleLabel);
        add(promptLabel);
        add(guessInputField);
        add(guessButton);
        add(resetButton);
        add(resultLabel);
        add(attemptLabel);

        guessButton.addActionListener(new GuessButtonListener());
        resetButton.addActionListener(e -> resetGame());

        setVisible(true);
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(50) + 1;
    }

    private void checkGuess(int guess) {
        attemptsMade++;
        attemptLabel.setText("Attempts left: " + (maxAttempts - attemptsMade));

        if (guess < randomNumber) {
            resultLabel.setText("Too low! Try again.");
            resultLabel.setForeground(new Color(255, 69, 0)); // Red color for wrong guess
        } else if (guess > randomNumber) {
            resultLabel.setText("Too high! Try again.");
            resultLabel.setForeground(new Color(255, 69, 0)); // Red color for wrong guess
        } else {
            resultLabel.setText("Correct! You guessed the number in " + attemptsMade + " attempts.");
            resultLabel.setForeground(new Color(34, 139, 34)); // Green color for success
            guessButton.setEnabled(false);
        }

        if (attemptsMade >= maxAttempts && guess != randomNumber) {
            resultLabel.setText("No more attempts! The number was " + randomNumber);
            resultLabel.setForeground(new Color(255, 105, 180)); // Pink color for game over
            guessButton.setEnabled(false);
        }
    }

    private void resetGame() {
        this.randomNumber = generateRandomNumber();
        this.attemptsMade = 0;
        guessButton.setEnabled(true);
        resultLabel.setText("");
        attemptLabel.setText("Attempts left: " + maxAttempts);
        guessInputField.setText("");
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userInput = guessInputField.getText();
            try {
                int userGuess = Integer.parseInt(userInput);
                if (userGuess < 1 || userGuess > 50) {
                    resultLabel.setText("Please enter a number between 1 and 50.");
                    resultLabel.setForeground(Color.WHITE);
                } else {
                    new Thread(() -> checkGuess(userGuess)).start();
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input. Please enter a number.");
                resultLabel.setForeground(Color.WHITE);
            }
        }
    }

    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog(null, "Enter the maximum number of attempts:");
        try {
            int maxAttempts = Integer.parseInt(input);
            SwingUtilities.invokeLater(() -> new NumberGuessGameGUI(maxAttempts));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
        }
    }
}