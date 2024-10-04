import javax.swing.*;
import java.awt.*;

public class GameIntroScreen extends JPanel {

    public GameIntroScreen(String username, JFrame frame, String selectedCatColor) {
        // Set up the panel
        setLayout(new BorderLayout());

        // Create and set up the title label
        JLabel titleLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        // Create and set up the story text
        JTextArea storyText = new JTextArea(
                "The Great Cat Adventure: Hunt for the Rat \n\n" +
                        "Welcome to The Great Cat Adventure! You are a fearless feline on a mission to catch the sneaky rat. " +
                        "But beware—it's not just a walk in the park! Along your journey, you’ll encounter danger, mystery, and tough decisions. " +
                        "Will you have the courage, skill, and luck to catch your prey? Let’s find out!"
        );
        storyText.setFont(new Font("Arial", Font.PLAIN, 18));
        storyText.setEditable(false);  // Prevent editing
        storyText.setLineWrap(true);   // Enable text wrapping
        storyText.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(storyText);
        add(scrollPane, BorderLayout.CENTER);

        // Add a "Start Game" button to proceed to the game
        JButton startGameButton = new JButton("Start Game");
        add(startGameButton, BorderLayout.SOUTH);

        // Action listener for the Start Game button
        startGameButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                frame.getContentPane().removeAll();
                CatChasingGame gamePanel = new CatChasingGame(username, frame, selectedCatColor);
                frame.getContentPane().add(gamePanel.getGamePanel(), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            });
        });
    }
}
