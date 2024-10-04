import javax.swing.*;
import java.awt.*;

public class GameIntroScreen extends JPanel {

    public GameIntroScreen(String username, JFrame frame, String selectedCatColor) {
        // Create a label to hold the background image
        JLabel backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("/mapBg.jpg")));
        backgroundLabel.setLayout(new GridBagLayout());  // Use GridBagLayout for more control

        // Create and set up the title label
        JLabel titleLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Daydream.ttf")).deriveFont(Font.BOLD, 32);
            titleLabel.setFont(customFont);
        } catch (Exception e) {
            titleLabel.setFont(new Font("Arial", Font.BOLD, 32));  // Fallback font if loading fails
        }

        titleLabel.setForeground(Color.WHITE);  // Make text visible on the background

        // Use GridBagConstraints to position components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);  // Add some padding
        backgroundLabel.add(titleLabel, gbc);

        // Determine the perk explanation based on the selected cat color
        String perkExplanation;
        switch (selectedCatColor.toLowerCase()) {
            case "black":
                perkExplanation = "your life is lower than other cats, and it is 85.";
                break;
            case "white":
                perkExplanation = "your life is higher than other cats, and it is 125.";
                break;
            case "orange":
                perkExplanation = "you recover more health when eating than other cats.";
                break;
            default:
                perkExplanation = "you have unique abilities.";  // Default case for other colors
        }

        // Create and set up the story text with the cat color and perk
        JTextArea storyText = new JTextArea(
                "Along your journey, you'll encounter various scenarios and enemies. " +
                        "As a " + selectedCatColor + " cat, " + perkExplanation + " Good luck!"
        );
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Daydream.ttf")).deriveFont(Font.PLAIN, 18);
            storyText.setFont(customFont);
        } catch (Exception e) {
            storyText.setFont(new Font("Arial", Font.PLAIN, 18));  // Fallback font if loading fails
        }
        storyText.setOpaque(false);  // Make the text area transparent
        storyText.setForeground(Color.WHITE);  // Make text visible on the background
        storyText.setEditable(false);  // Prevent editing
        storyText.setLineWrap(true);   // Enable text wrapping
        storyText.setWrapStyleWord(true);
        storyText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);  // Center text horizontally
        JScrollPane scrollPane = new JScrollPane(storyText);
        scrollPane.setOpaque(false);  // Make scroll pane transparent
        scrollPane.getViewport().setOpaque(false);

        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        backgroundLabel.add(scrollPane, gbc);

        // Add a "Start Game" button to proceed to the game
        JButton startGameButton = new JButton("Start Game");
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Daydream.ttf")).deriveFont(Font.PLAIN, 16);
            startGameButton.setFont(customFont);
        } catch (Exception e) {
            startGameButton.setFont(new Font("Arial", Font.PLAIN, 16));  // Fallback font
        }

        startGameButton.setContentAreaFilled(false);  // Remove button fill
        startGameButton.setBorderPainted(false);      // Remove button border
        startGameButton.setOpaque(false);             // Make the button fully transparent
        startGameButton.setForeground(Color.WHITE);

        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        backgroundLabel.add(startGameButton, gbc);

        // Set up the panel with the background
        setLayout(new BorderLayout());
        add(backgroundLabel);

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