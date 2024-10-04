import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class gameProduction extends JPanel {
    // List of all possible rooms
    private final List<String> allRooms = new ArrayList<>();
    // List of rooms that have not been visited yet
    private final List<String> availableRooms = new ArrayList<>();
    // Player's health initialized to 100
    private int playerHealth = 100;

    public gameProduction() {
        // Initialize the list of all possible rooms
        allRooms.add("Forest");
        allRooms.add("Lake");
        allRooms.add("Desert");
        allRooms.add("Swamp");
        allRooms.add("City");
        allRooms.add("Jungle");
        allRooms.add("Alley");
        allRooms.add("Mountain");
        // Reset the available rooms to include all rooms
        resetAvailableRooms();
        // Show the room selection screen
        showRoomSelection();
    }

//
    // Helper method to get image as ImageIcon from resources
    private ImageIcon getImageIcon(String path) {
        return new ImageIcon(getClass().getResource(path));
    }

    private void resetAvailableRooms() {
        // Clear the list of available rooms and add all rooms back
        availableRooms.clear();
        availableRooms.addAll(allRooms);
    }

    // Method to display the room selection window
    private void showRoomSelection() {
        // Remove all components from the panel
        removeAll();
        // Set the layout with horizontal and vertical gaps of 10 pixels
        setLayout(new BorderLayout(10, 10));

        // Add a welcome label at the top
        JLabel label = new JLabel("Welcome to the Cat and the Rat!", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // If there are no available rooms left, end the game
        if (availableRooms.isEmpty()) {
            showEndGame();
            return;
        }

        // Prompt the player to choose a room
        JLabel prompt = new JLabel("Choose one of the available rooms:", SwingConstants.CENTER);
        add(prompt, BorderLayout.CENTER);

        // Randomly shuffle the available rooms
        Collections.shuffle(availableRooms);
        // Get the first room from the shuffled list
        String room1 = availableRooms.get(0);

        // Create a panel for the room selection buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        // Create a button for the first room
        JButton room1Button = new JButton("Room 1: " + room1);
        buttonPanel.add(room1Button);

        // If there is more than one room available, add a second button
        if (availableRooms.size() > 1) {
            // Get the second room
            String room2 = availableRooms.get(1);
            // Create a button for the second room
            JButton room2Button = new JButton("Room 2: " + room2);
            buttonPanel.add(room2Button);

            // Add action listener for the second room button
            room2Button.addActionListener(e -> {
                // Remove the selected room from available rooms
                availableRooms.remove(room2);
                // Clear the panel and refresh
                removeAll();
                revalidate();
                repaint();
                // Show the selected room
                showRoom(room2);
            });
        }

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener for the first room button
        room1Button.addActionListener(e -> {
            // Remove the selected room from available rooms
            availableRooms.remove(room1);
            // Clear the panel and refresh
            removeAll();
            revalidate();
            repaint();
            // Show the selected room
            showRoom(room1);
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method to display the selected room based on its name
    private void showRoom(String roomName) {
        switch (roomName) {
            case "Forest":
                forestRoom();
                break;
            case "Lake":
                lakeRoom();
                break;
            case "Desert":
                desertRoom();
                break;
            case "Swamp":
                swampRoom();
                break;
            case "City":
                cityRoom();
                break;
            case "Jungle":
                jungleRoom();
                break;
            case "Alley":
                alleyRoom();
                break;
            case "Mountain":
                mountainRoom();
                break;

            default:
                throw new IllegalArgumentException("Unknown room: " + roomName);
        }
    }

    // Method for the Forest room
    private void forestRoom() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel background = new JLabel(getImageIcon("forestBg.jpg"));
        background.setLayout(new BorderLayout());
        add(background);
        // Create a label with a description of the forest room
        JLabel label = new JLabel("<html>You enter forestRoom: A mysterious forest.<br>" +
                "The forest is dense, with trees towering over like silent sentinels.<br>" +
                "The air is thick with fog, and the ground is covered in leaves that crunch with every step.<br>" +
                "There's a rumor among the animals that a coyote lurks in these woods.<br>" +
                "If you encounter the coyote, you will need to roll a dice to determine the cat's fate.</html>", SwingConstants.CENTER);
        background.add(label, BorderLayout.CENTER);

        // Add a continue button at the bottom
        JButton continueButton = new JButton("Continue");
        add(continueButton, BorderLayout.SOUTH);

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Generate a random number to determine if an encounter occurs
            Random random = new Random();
            int encounterChance = random.nextInt(5);

            // Clear the panel and refresh
            removeAll();
            revalidate();
            repaint();

            // If encounterChance is less than 2, show the coyote encounter
            if (encounterChance < 2) {
                showCoyoteEncounter();
            } else {
                // Otherwise, proceed to food interaction
                showFoodInteraction();
            }
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method for the swamp room
    private void swampRoom() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel background = new JLabel(getImageIcon("forestBg.jpg"));
        background.setLayout(new BorderLayout());
        add(background);
        // Create a label with a description of the forest room
        JLabel label = new JLabel("<html>You enter swampRoom: A mysterious swamp.<br>" +
                ".</html>", SwingConstants.CENTER);
     //   background.add(label, BorderLayout.CENTER);

        // Add a continue button at the bottom
        JButton continueButton = new JButton("Continue");
        add(continueButton, BorderLayout.SOUTH);

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Generate a random number to determine if an encounter occurs
            Random random = new Random();
            int encounterChance = random.nextInt(5);

            // Clear the panel and refresh
            removeAll();
            revalidate();
            repaint();

            // If encounterChance is less than 3, show the snake encounter
            if (encounterChance < 3) {
                // showSnakeEncounter
                showSnakeEncounter();
            } else {
                // Otherwise, proceed to water interaction
                showWaterInteraction();
            }
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }
    private void showSnakeEncounter() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll the dice
        JLabel label = new JLabel("A snake appears! Roll the dice to determine your fate:", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button, initially disabled
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int diceRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the player escapes unharmed
            if (diceRoll % 2 == 0) {
                resultLabel.setText("Rolled " + diceRoll + ": The snake slithered away, and you escape unharmed.");
            } else {
                // If the dice roll is odd, the player loses 50% health
                resultLabel.setText("Rolled " + diceRoll + ": The snake attacks! You lose 30% of your health.");
                playerHealth -= 50;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Proceed to food interaction
            removeAll();
            revalidate();
            repaint();
            showFoodInteraction();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method for the city room
    private void cityRoom() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel background = new JLabel(getImageIcon("cityBg.jpg"));
        background.setLayout(new BorderLayout());
        add(background);
        // Create a label with a description of the city room
        JLabel label = new JLabel("<html>You enter city: .</html>", SwingConstants.CENTER);
        background.add(label, BorderLayout.CENTER);

        // Add a continue button at the bottom
        JButton continueButton = new JButton("Continue");
        add(continueButton, BorderLayout.SOUTH);

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Generate a random number to determine if an encounter occurs
            Random random = new Random();
            int encounterChance = random.nextInt(5);

            // Clear the panel and refresh
            removeAll();
            revalidate();
            repaint();

            // If encounterChance is less than 4, show the dog Encounter
            if (encounterChance < 4) {
                showDogEncounter();
            } else {
                // Otherwise, proceed to food interaction
                showCoyoteEncounter();
            }
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method for the jungle room
    private void jungleRoom() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel background = new JLabel(getImageIcon("jungleBg.jpg"));
        background.setLayout(new BorderLayout());
        add(background);
        // Create a label with a description of the city room
        JLabel label = new JLabel("<html>You enter Jungle Room: .</html>", SwingConstants.CENTER);
        background.add(label, BorderLayout.CENTER);

        // Add a continue button at the bottom
        JButton continueButton = new JButton("Continue");
        add(continueButton, BorderLayout.SOUTH);

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Generate a random number to determine if an encounter occurs
            Random random = new Random();
            int encounterChance = random.nextInt(5);

            // Clear the panel and refresh
            removeAll();
            revalidate();
            repaint();
            // make it encounter a monkey  if roll is less than 4, show money encounter
            if (encounterChance < 2) {
                // show MoneyEncounter
                showMonkeyEncounter();
            } else {
                // Encounter hawk
                // showHawkInteraction
                showHawkInteraction();
            }
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }
    private void showMonkeyEncounter() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll the dice
        JLabel label = new JLabel("A monkey appears! Roll the dice to determine your fate:", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button, initially disabled
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int diceRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the player escapes unharmed
            if (diceRoll % 2 == 0) {
                resultLabel.setText("Rolled " + diceRoll + ": You defeated the monkey and only lost 10% of your health.");
            } else {
                // If the dice roll is odd, the player loses 50% health
                resultLabel.setText("Rolled " + diceRoll + ": The monkey attacks! You lose 50% of your health.");
                playerHealth -= 50;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Proceed to food interaction
            removeAll();
            revalidate();
            repaint();
            showFoodInteraction();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }
    private void showHawkInteraction() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll the dice
        JLabel label = new JLabel("A hawk appears! Roll the dice to determine your fate:", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button, initially disabled
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int diceRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the player escapes unharmed
            if (diceRoll % 2 == 0) {
                resultLabel.setText("Rolled " + diceRoll + ": You defeat the hawk, and you escape unharmed.");
            } else {
                // If the dice roll is odd, the player loses 50% health
                resultLabel.setText("Rolled " + diceRoll + ": The hawk attacks! You lose 50% of your health.");
                playerHealth -= 50;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Proceed to food interaction
            removeAll();
            revalidate();
            repaint();
            showFoodInteraction();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method for the alley room
    private void alleyRoom() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel background = new JLabel(getImageIcon("alleyBg.jpg"));
        background.setLayout(new BorderLayout());
        add(background);
        // Create a label with a description of the alley room
        JLabel label = new JLabel("<html>You enter alley: .</html>", SwingConstants.CENTER);
        background.add(label, BorderLayout.CENTER);

        // Add a continue button at the bottom
        JButton continueButton = new JButton("Continue");
        add(continueButton, BorderLayout.SOUTH);

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Generate a random number to determine if an encounter occurs
            Random random = new Random();
            int encounterChance = random.nextInt(5);

            // Clear the panel and refresh
            removeAll();
            revalidate();
            repaint();

            // encounter
            // If encounterChance is less than 6, show the dog encounter
            if (encounterChance < 6) {
                showDogEncounter();
            } else {
                // Otherwise, proceed to food interaction
                showFoodInteraction();
            }
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method for the mountain room
    private void mountainRoom() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel background = new JLabel(getImageIcon("mountainBg.jpg"));
        background.setLayout(new BorderLayout());
        add(background);
        // Create a label with a description of the mountain room
        JLabel label = new JLabel("<html>You enter mountain: .</html>", SwingConstants.CENTER);
        background.add(label, BorderLayout.CENTER);

        // Add a continue button at the bottom
        JButton continueButton = new JButton("Continue");
        add(continueButton, BorderLayout.SOUTH);

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Generate a random number to determine if an encounter occurs
            Random random = new Random();
            int encounterChance = random.nextInt(5);

            // Clear the panel and refresh
            removeAll();
            revalidate();
            repaint();

            // If encounterChance is less than 3, show the mountain lion encounter
            if (encounterChance < 3) {
                // make mountain lion encounter
                showMountainLionEncounter();
            } else {
                // Otherwise, bird encounter
                showBirdEncounter();
            }
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    private void showBirdEncounter() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll the dice
        JLabel label = new JLabel("A Bird appears! Roll the dice to determine your fate:", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button, initially disabled
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int diceRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the player escapes unharmed
            if (diceRoll % 2 == 0) {
                resultLabel.setText("Rolled " + diceRoll + ": The bird only chrips, and you escape unharmed.");
            } else {
                // If the dice roll is odd, the player loses 50% health
                resultLabel.setText("Rolled " + diceRoll + ": The bird attacks! You lose 25% of your health.");
                playerHealth -= 25;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Proceed to food interaction
            removeAll();
            revalidate();
            repaint();
            showFoodInteraction();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    private void showMountainLionEncounter() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll the dice
        JLabel label = new JLabel("A Mountain Lion appears! Roll the dice to determine your fate:", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button, initially disabled
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int diceRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the player escapes with only losing 25% health
            if (diceRoll % 2 == 0) {
                resultLabel.setText("Rolled " + diceRoll + ": The Mountain Lion attacks you but you only lose 25% of your health.");
            } else {
                // If the dice roll is odd, the player loses 75% health
                resultLabel.setText("Rolled " + diceRoll + ": The Mountain Lion attacks! You lose 75% of your health.");
                playerHealth -= 75;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Proceed to food interaction
            removeAll();
            revalidate();
            repaint();
            showFoodInteraction();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }


    private void showCoyoteEncounter() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll the dice
        JLabel label = new JLabel("A coyote appears! Roll the dice to determine your fate:", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button, initially disabled
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int diceRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the player escapes unharmed
            if (diceRoll % 2 == 0) {
                resultLabel.setText("Rolled " + diceRoll + ": The coyote only snarls, and you escape unharmed.");
            } else {
                // If the dice roll is odd, the player loses 50% health
                resultLabel.setText("Rolled " + diceRoll + ": The coyote attacks! You lose 50% of your health.");
                playerHealth -= 50;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Proceed to food interaction
            removeAll();
            revalidate();
            repaint();
            showFoodInteraction();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    private void showFoodInteraction() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll for food
        JLabel label = new JLabel("Rolling for food... Click to roll the dice.", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button to proceed to water roll, initially disabled
        JButton continueButton = new JButton("Continue to Water Roll");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int foodRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the player finds food
            if (foodRoll % 2 == 0) {
                resultLabel.setText("Rolled " + foodRoll + ": You got food and restored energy!");
            } else {
                // If the dice roll is odd, the player loses 50% health
                resultLabel.setText("Rolled " + foodRoll + ": No food found, you lose 50% of your health.");
                playerHealth -= 50;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Proceed to water interaction
            removeAll();
            revalidate();
            repaint();
            showWaterInteraction();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    private void showWaterInteraction() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll for water
        JLabel label = new JLabel("Rolling for water... Click to roll the dice.", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button to proceed to the next room, initially disabled
        JButton continueButton = new JButton("Continue to Next Room");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int waterRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the player finds fresh water
            if (waterRoll % 2 == 0) {
                resultLabel.setText("Rolled " + waterRoll + ": You got fresh water and replenished hydration!");
            } else {
                // If the dice roll is odd, the player drinks poisoned water and health is halved
                resultLabel.setText("Rolled " + waterRoll + ": Poisoned water! Health is cut in half.");
                playerHealth -= playerHealth / 2;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Return to room selection
            removeAll();
            revalidate();
            repaint();
            showRoomSelection();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method for the Lake room
    private void lakeRoom() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel background = new JLabel(getImageIcon("/lakeBg.jpg"));
        background.setLayout(new BorderLayout(10, 10));
        add(background);
        // Create a label with a description of the lake room
        JLabel label = new JLabel("<html>You enter the Lake room: A serene lake with crystal-clear water.<br>" +
                "The path leads you to a shimmering lake, inviting you for a drink or a chance to catch a fish.<br>" +
                "However, a dog might be guarding its territory by the water's edge.<br>" +
                "Will you encounter the dog? Roll the dice to find out.</html>", SwingConstants.CENTER);
        background.add(label, BorderLayout.CENTER);

        // Add a continue button at the bottom
        JButton continueButton = new JButton("Continue");
        add(continueButton, BorderLayout.SOUTH);

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Generate a random number to determine if an encounter occurs
            Random random = new Random();
            int encounterChance = random.nextInt(2);

            // Clear the panel and refresh
            removeAll();
            revalidate();
            repaint();

            // If encounterChance is 0, show the dog encounter
            if (encounterChance == 0) {
                showDogEncounter();
            } else {
                // Otherwise, proceed to fishing interaction
                showFishing();
            }
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    private void showDogEncounter() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll the dice
        JLabel label = new JLabel("A dog appears! Roll the dice to determine your fate:", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button, initially disabled
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int diceRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the dog does not attack
            if (diceRoll % 2 == 0) {
                resultLabel.setText("Rolled " + diceRoll + ": The dog barks but doesn't attack. You escape unharmed.");
            } else {
                // If the dice roll is odd, the player loses 75% health
                resultLabel.setText("Rolled " + diceRoll + ": The dog attacks! You lose 75% of your health.");
                playerHealth -= 75;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Proceed to fishing interaction
            removeAll();
            revalidate();
            repaint();
            showFishing();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method for the fishing interaction
    private void showFishing() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label prompting the player to roll for fishing
        JLabel label = new JLabel("Time to fish! Roll the dice to see if you catch something:", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Create a panel for the roll and continue buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a roll button
        JButton rollButton = new JButton("Roll the Dice");
        buttonPanel.add(rollButton);

        // Create a continue button to proceed to the next room, initially disabled
        JButton continueButton = new JButton("Continue to Next Room");
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Label to display the result of the dice roll
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the roll button
        rollButton.addActionListener(e -> {
            // Roll a dice (values between 1 and 6)
            Random random = new Random();
            int fishRoll = random.nextInt(6) + 1;

            // If the dice roll is even, the player catches a fish
            if (fishRoll % 2 == 0) {
                resultLabel.setText("Rolled " + fishRoll + ": You caught a fish and gained energy!");
            } else {
                // If the dice roll is odd, the player loses 25% health
                resultLabel.setText("Rolled " + fishRoll + ": No fish caught, you lose 25% of your health.");
                playerHealth -= 25;

                // Ensure player health doesn't go below 0
                playerHealth = Math.max(playerHealth, 0);
            }

            // Disable the roll button and enable the continue button
            rollButton.setEnabled(false);
            continueButton.setEnabled(true);
        });

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Return to room selection
            removeAll();
            revalidate();
            repaint();
            showRoomSelection();
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method for the Forest room
    private void desertRoom() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        JLabel background = new JLabel(getImageIcon("desertBg.jpg"));
        background.setLayout(new BorderLayout());
        add(background);
        // Create a label with a description of the forest room
        JLabel label = new JLabel("<html>You enter desertRoom: A barren, scorching landscape. <br>" +
                "The heat is opressive, and there's not much to offer. Beware of potential threats.</html>", SwingConstants.CENTER);
        background.add(label, BorderLayout.CENTER);

        // Add a continue button at the bottom
        JButton continueButton = new JButton("Continue");
        add(continueButton, BorderLayout.SOUTH);

        // Action listener for the continue button
        continueButton.addActionListener(e -> {
            // Generate a random number to determine if an encounter occurs
            Random random = new Random();
            int encounterChance = random.nextInt(5);

            // Clear the panel and refresh
            removeAll();
            revalidate();
            repaint();

            // If encounterChance is less than 2, show the coyote encounter
            if (encounterChance < 2) {
                showSnakeEncounter();
            } else {
                // Otherwise, proceed to food interaction
                showHawkInteraction();
            }
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Method to display the end of the game
    private void showEndGame() {
        // Clear the panel and set a new layout
        removeAll();
        setLayout(new BorderLayout(10, 10));

        // Create a label to display the final message and player's health
        JLabel label = new JLabel("<html>Congratulations! You've completed all rooms in the journey.<br>" +
                "Your final health is: " + playerHealth + "</html>", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Create a panel for the restart and end buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create a restart game button
        JButton restartButton = new JButton("Restart Game");
        // Create an end game button
        JButton endButton = new JButton("End Game");

        // Add buttons to the panel
        buttonPanel.add(restartButton);
        buttonPanel.add(endButton);

        // Add the button panel to the bottom of the layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for the restart button
        restartButton.addActionListener(e -> {
            // Reset player's health and available rooms
            playerHealth = 100;
            resetAvailableRooms();
            // Return to room selection
            removeAll();
            revalidate();
            repaint();
            showRoomSelection();
        });

        // Action listener for the end game button
        endButton.addActionListener(e -> {
            // Exit the application
            System.exit(0);
        });

        // Refresh the panel to display the new components
        revalidate();
        repaint();
    }

    // Main method to start the game
    public static void main(String[] args) {
        // Create a new frame for the game
        JFrame frame = new JFrame("Cat and the Rat Game");
        // Create an instance of the game
        gameProduction game = new gameProduction();
        // Set default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the frame
        //Updated frame size to 1000 x 700
        frame.setSize(1000, 700);
        // Add the game panel to the frame
        frame.setContentPane(game);
        // Make the frame visible
        frame.setVisible(true);
    }
}