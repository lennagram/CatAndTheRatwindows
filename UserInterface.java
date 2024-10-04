import javax.swing.*;
import java.awt.*;

public class UserInterface extends JPanel {
    private JProgressBar healthBar;
    private JProgressBar hungerBar;
    private JProgressBar thirstBar;
    private DefaultListModel<String> inventoryModel;
    private JList<String> inventoryList;

    private int health = 100;
    private int hunger = 100;
    private int thirst = 100;

    public UserInterface() {
        setLayout(new BorderLayout(10, 10));  // Adjusted layout manager

        // Create and set up panels
        JPanel statusPanel = new JPanel(new GridLayout(3, 2));
        JPanel inventoryPanel = new JPanel(new BorderLayout());

        // Health bar setup
        healthBar = new JProgressBar(0, 5);
        healthBar.setValue(health);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.RED);
        statusPanel.add(new JLabel("Health:"));
        statusPanel.add(healthBar);

        // Hunger bar setup
        hungerBar = new JProgressBar(0, 5);
        hungerBar.setValue(hunger);
        hungerBar.setStringPainted(true);
        hungerBar.setForeground(Color.ORANGE);
        statusPanel.add(new JLabel("Hunger:"));
        statusPanel.add(hungerBar);

        // Thirst bar setup
        thirstBar = new JProgressBar(0, 5);
        thirstBar.setValue(thirst);
        thirstBar.setStringPainted(true);
        thirstBar.setForeground(Color.BLUE);
        statusPanel.add(new JLabel("Thirst:"));
        statusPanel.add(thirstBar);

        // Add statusPanel to the JPanel
        add(statusPanel, BorderLayout.NORTH);

        // Inventory setup
        inventoryModel = new DefaultListModel<>();
        inventoryList = new JList<>(inventoryModel);
        JScrollPane inventoryScrollPane = new JScrollPane(inventoryList);
        inventoryScrollPane.setPreferredSize(new Dimension(200, 80));

        inventoryModel.addElement("Food");
        inventoryModel.addElement("Water");
        inventoryModel.addElement("Poisoned");

        inventoryPanel.add(new JLabel("Inventory:"), BorderLayout.NORTH);
        inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);

        // Add inventoryPanel to the JPanel
        add(inventoryPanel, BorderLayout.CENTER);
    }

    // Existing methods (e.g., collectItem, updateHealthBar, gameLogic) remain unchanged

    // Update methods
    public void collectItem(String item) {
        inventoryModel.addElement(item);
    }

    public void updateHealth(int newHealth) {
        this.health = newHealth;
        healthBar.setValue(health);
        checkGameOver();
    }

    public void updateHunger(int newHunger) {
        this.hunger = newHunger;
        hungerBar.setValue(hunger);
    }

    public void updateThirst(int newThirst) {
        this.thirst = newThirst;
        thirstBar.setValue(thirst);
    }

    private void checkGameOver() {
        if (health <= 0) {
            JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}
