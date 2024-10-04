import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CatChasingGame extends JPanel {
    private final String username; // Added username variable
    private final String currentCat; // Added currentCat variable

    private final List<String> scenarios;
    private final List<String> enemies;
    private final String[] catOptions = {"White Cat", "Orange Cat", "Black Cat"};
    private int catLife = 100;
    private int maxLife = 100;
    private int currentScenarioIndex = 0;
    private final Random random = new Random();
    private JLabel healthLabel;
    private JTextArea scenarioDescription;
    private JButton eatDrinkButton;
    private JButton rollDiceButton;
    private boolean ateOrDrank = false; // To track if player has eaten or drunk in the current scenario
    private int currentWinCondition; // Store current odds of winning
    private int hungerLevel = 50; // Hunger level starts at 50
    private JLabel background; // Declare background JLabel as an instance variable
    private Font customFont; // Declare font variable
    private JLayeredPane layeredPane;
    private String enemy;

    public CatChasingGame(String username, JFrame frame, String selectedCatColor) { // Constructor takes username and cat color
        this.username = username;
        this.currentCat = selectedCatColor;

        // Set max health based on cat color
        if ("White".equals(currentCat)) {
            maxLife = 1225;
            catLife = 1225;
        } else {
            maxLife = 100;  // Max health for orange and black cat
            catLife = 100;
        }

        scenarios = new ArrayList<>();
        loadScenarioData();

        // Load the custom font
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/Daydream.ttf")).deriveFont(24f); // Change size as needed
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        enemies = new ArrayList<>(List.of("Dog", "Snake", "Coyote", "Eagle", "Fox", "Hawk", "Bear","Wolf"));

        // Layout and component setup
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel(new GridLayout(4, 1));
        gamePanel.setPreferredSize(new Dimension(600, 120));


        background = new JLabel();
        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setVerticalAlignment(SwingConstants.CENTER);

        gamePanel.add(background);

        // Load and scale images
        loadScenarioData();

        JLabel titleLabel = new JLabel("Welcome to Cat Chasing the Rat, " + username + "!", SwingConstants.CENTER);
        titleLabel.setFont(customFont.deriveFont(24f));
        gamePanel.add(titleLabel);

        healthLabel = new JLabel("Health: " + catLife + " | Hunger: " + hungerLevel, SwingConstants.CENTER);
        healthLabel.setFont(customFont.deriveFont(18f));
        gamePanel.add(healthLabel);

        JButton nextButton = new JButton("Next");
        gamePanel.add(nextButton);

        add(gamePanel, BorderLayout.NORTH);
        layeredPane.add(background, BorderLayout.CENTER);
        add(layeredPane, BorderLayout.CENTER);

        // Description area
        scenarioDescription = new JTextArea();
        scenarioDescription.setEditable(false);
        scenarioDescription.setWrapStyleWord(true);
        scenarioDescription.setLineWrap(true);
        JScrollPane descriptionScrollPane = new JScrollPane(scenarioDescription);
        descriptionScrollPane.setPreferredSize(new Dimension(600, 100)); // Set preferred size for the description box
        // Create a new panel for description and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        // Add description to the bottom panel
        bottomPanel.add(descriptionScrollPane, BorderLayout.NORTH);

        // Action buttons
        JPanel actionPanel = new JPanel(new GridLayout(1, 2));
        eatDrinkButton = new JButton("Eat/Drink");
        rollDiceButton = new JButton("Roll Dice");
        eatDrinkButton.setVisible(false);
        rollDiceButton.setVisible(false);
        actionPanel.add(eatDrinkButton);
        actionPanel.add(rollDiceButton);
        // Add action panel to the bottom
        bottomPanel.add(actionPanel, BorderLayout.SOUTH);

        // Add the bottom panel to the main panel in the south
        add(bottomPanel, BorderLayout.SOUTH);

        // Start the game
        nextButton.addActionListener(e -> {

            Collections.shuffle(scenarios);  // Randomize scenarios
            updateHealthLabel();
            displayNextScenario();

            // Hide button after the first screen
            nextButton.setVisible(false);
        });

        // Action to eat/drink
        eatDrinkButton.addActionListener(e -> makeHealthDecision());

        // Action to roll dice for enemy fight
        rollDiceButton.addActionListener(e -> fightEnemy());
    }

    class Scenario {
        String description;
        ImageIcon image;

        public Scenario(String description, ImageIcon image) {
            this.description = description;
            this.image = image;
        }
    }

    private List<Scenario> scenariosList = new ArrayList<>();

    private void loadScenarioData() {
        try {
        scenariosList.add(new Scenario("Lake: A tranquil lake surrounded by lush trees. The water sparkles under the sun. You hear rustling nearby... You can see a fish darting in the clear water.", new ImageIcon(getClass().getResource("/lakeBg.jpg"))));
        scenariosList.add(new Scenario("Desert: A vast desert with endless sand dunes. The sun beats down mercilessly. A shadow looms on the horizon, and you notice a coyote lurking behind a rock.", new ImageIcon(getClass().getResource("/desertBg.jpg"))));
        scenariosList.add(new Scenario("Forest: A dense forest with towering trees. The air is filled with the sounds of nature. Something slithers in the underbrush, possibly a snake waiting to strike.", new ImageIcon(getClass().getResource("/forestBg.jpg"))));
        scenariosList.add(new Scenario("City: A bustling city with tall buildings and noisy streets. A flicker of movement catches your eye in the alleyway. An eagle circles overhead, watching.", new ImageIcon(getClass().getResource("/cityBg.png"))));
        scenariosList.add(new Scenario("Mountain: Rugged mountains rise high into the clouds. The air is thin, and danger lurks among the rocks; a bear might be hiding in the shadows.", new ImageIcon(getClass().getResource("/mountainBg.jpg"))));
        scenariosList.add(new Scenario("Swamp: A murky swamp filled with fog. The ground is unstable, and you can hear distant croaking sounds, hinting at the presence of frogs and snakes.", new ImageIcon(getClass().getResource("/swampBg.jpg"))));
        scenariosList.add(new Scenario("Jungle: A vibrant jungle teeming with life. The foliage is thick, and the sounds of creatures echo all around. Watch out for foxes hiding in the brush.", new ImageIcon(getClass().getResource("/jungleBg.png"))));
        scenariosList.add(new Scenario("Alley: A dark alley littered with trash. You can feel eyes watching you from the shadows, and a dog suddenly barks, threatening your path.", new ImageIcon(getClass().getResource("/alleyBg.png"))));
    } catch (Exception e) {
            System.out.println("Error loading scenario data: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void startGame() {
        Collections.shuffle(scenariosList);
        displayNextScenario();
    }

    public JPanel getGamePanel() {
        return this;
    }

    // Method to display the next scenario
    private void displayNextScenario() {
        // Check if all scenarios have been completed
        if (currentScenarioIndex < 8) {

            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();

            // Fetch the current scenario object
            Scenario currentScenario = scenariosList.get(currentScenarioIndex);

            // Set the description and image
            background.setIcon(scaleImage(currentScenario.image, panelWidth, panelHeight));
            scenarioDescription.setText(currentScenario.description);

            background.revalidate();
            background.repaint();

            // Choose a random enemy
            if (!enemies.isEmpty()) {
                enemy = enemies.get(random.nextInt(enemies.size()));

            }

            // Randomize win condition
            currentWinCondition = random.nextInt(6) + 1; // Range 1-6 to cover all win conditions

            String winConditionDescription;

            switch (currentWinCondition) {
                case 1:
                    winConditionDescription = "Roll an odd number to win.";
                    break;
                case 2:
                    winConditionDescription = "Roll a number greater than 3 to win.";
                    break;
                case 3:
                    winConditionDescription = "Roll a number higher than 4 to win.";
                    break;
                case 4:
                    winConditionDescription = "Roll a 6 to win.";
                    break;
                case 5:
                    winConditionDescription = "Roll a number less than 3 to win.";
                    break;
                case 6:
                    winConditionDescription = "Roll a prime number to win.";
                    break;
                default:
                    winConditionDescription = "Unknown challenge!";
            }

            // Append encounter information and odds to the scenario description
            scenarioDescription.append("\n\nYou encounter a " + enemy + "! Prepare to face it."
                    + "\n\nFight odds: " + winConditionDescription);

            // Reset eat/drink status for the new scenario
            ateOrDrank = false;

            // Randomly show the eat/drink option
            if (random.nextBoolean()) {
                eatDrinkButton.setVisible(true);
                scenarioDescription.append("\n\nYou found food or water. Would you like to eat or drink?");
                eatDrinkButton.setEnabled(true); // Enable the button for the current scenario
            } else {
                eatDrinkButton.setVisible(false);
            }

            // Show the dice roll button
            rollDiceButton.setVisible(true);

            // Decrease hunger level and health accordingly
            hungerLevel = Math.max(hungerLevel - 10, 0);
            if (hungerLevel == 0) {
                catLife = Math.max(catLife - 10, 0); // Decrease health if hunger reaches 0
            }

            // Update the health label
            updateHealthLabel();

        } else {
            // When all scenarios are completed, display the final message
            JOptionPane.showMessageDialog(this, "Congratulations! You've completed all scenarios and caught the rat!");
            eatDrinkButton.setVisible(false);
            rollDiceButton.setVisible(false);
            scenarioDescription.setVisible(false);
            System.exit(0); // Exit the game if health is zero
        }
    }
    private ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();

        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    private void makeHealthDecision() {
        // Only allow to eat/drink once per scenario
        if (ateOrDrank) {
            JOptionPane.showMessageDialog(this, "You can only eat/drink once per scenario!");
            return;
        }

        // Decide if food/water is good or bad
        int decisionOutcome = random.nextInt(2); // 0 = good, 1 = bad
        if (decisionOutcome == 0) {
            int recovery = (currentCat.equals("Orange") ? 20 : 10);
            catLife = Math.min(catLife + recovery, maxLife); // Recovery amount
            hungerLevel = Math.min(hungerLevel + 10, 50); // Increase hunger level by 10, max 50
            JOptionPane.showMessageDialog(this, "You ate/drank something good! You recovered " + recovery + " health and your hunger level increased by 10!");
        } else {
            int damage = 10; // Lose health from bad food
            catLife = Math.max(catLife - damage, 0); // Health cannot go below 0
            hungerLevel = Math.min(hungerLevel + 10, 50); // Increase hunger level by 10, max 50
            JOptionPane.showMessageDialog(this, "You ate/drank something bad! You lost " + damage + " health!");
        }

        ateOrDrank = true; // Set flag to true
        updateHealthLabel();
    }


    private void fightEnemy() {
        // Roll the dice
        int diceRoll = random.nextInt(6) + 1; // Random number between 1 and 6
        int damage = 0; // Initialize damage variable

        // Determine if the roll wins or loses based on the current odds
        boolean wonFight = false;

        switch (currentWinCondition) {
            case 1:
                if (diceRoll % 2 != 0) { // Odd number
                    wonFight = true;
                }
                break;
            case 2:
                if (diceRoll > 3) { // Greater than 3
                    wonFight = true;
                }
                break;
            case 3:
                if (diceRoll > 4) { // Greater than 4
                    wonFight = true;
                }
                break;
            case 4:
                if (diceRoll == 6) { // Exactly 6
                    wonFight = true;
                }
                break;
            case 5:
                if (diceRoll < 3) { // Less than 3
                    wonFight = true;
                }
                break;
            case 6:
                // Check for prime number (2, 3, 5)
                if (diceRoll == 2 || diceRoll == 3 || diceRoll == 5 ) {
                    wonFight = true;
                }
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error in fight conditions.");
        }

        if (wonFight) {
            JOptionPane.showMessageDialog(this, "You rolled " + diceRoll + "!" + " You won the fight! You successfully chased away the " + enemy);
            enemies.remove(enemy);
        } else {
            damage = random.nextInt(21) + 10; // Generate random damage between 10 and 30
            catLife = Math.max(catLife - damage, 0); // Health cannot go below 0
            JOptionPane.showMessageDialog(this, "You rolled " + diceRoll + "!" + " You lost the fight! You took " + damage + " damage from the " + enemy);
            enemies.remove(enemy);
        }

        // Update the health label and outcome text box
        updateHealthLabel();

        // Check for game over
        if (catLife <= 0) {
            JOptionPane.showMessageDialog(this, "Game Over! Your cat has lost all its health.");
            System.exit(0); // Exit the game if health is zero
        } else {
            // Proceed to the next scenario
            currentScenarioIndex++;
            displayNextScenario();
        }
    }
    private void updateHealthLabel() {
        healthLabel.setText("Current Health: " + catLife + " | Hunger Level: " + hungerLevel);
    }
}