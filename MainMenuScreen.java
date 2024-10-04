import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;





public class MainMenuScreen extends JFrame {

    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    // Global variable for music settings
    private boolean isMusicOn = true;
    private Clip clip;

    public Font customFont;

    public MainMenuScreen() {

        // Play the background music when the window opens
        playMusic();

        // Importing custom font from resources
        try (InputStream is = getClass().getResourceAsStream("/Daydream.ttf")) {
            if (is != null) {
                customFont = Font.createFont(Font.TRUETYPE_FONT, is);
                customFont = customFont.deriveFont(Font.BOLD, 20);  // Set font style and size
            } else {
                System.out.println("Error: Font file not found.");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.out.println("Error loading custom font.");
        }

        // Set up the JFrame
        setTitle("Cat and The Rat");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // CardLayout to switch between screens
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add all the screens to the card panel
        cardPanel.add(createMainMenu(), "mainMenu");
        cardPanel.add(createCharacterCreationScreen(), "characterCreation");
        cardPanel.add(createTutorialScreen(), "tutorial");
        cardPanel.add(createCreditsScreen(), "credits");
        cardPanel.add(createSettingsScreen(), "settings");

        // Add the card panel to the frame
        add(cardPanel);

        // Start with the main menu screen
        cardLayout.show(cardPanel, "mainMenu");
    }

    // Main Menu Screen with Background
    private JPanel createMainMenu() {
        JPanel mainMenuPanel = new JPanel(new BorderLayout());

        // Load the background image
        ImageIcon background = new ImageIcon(getClass().getResource("/mainBg.jpg"));
        JLabel backgroundLabel = new JLabel(background);

        // Set up the panel to hold labels over the background
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);  // Make the panel transparent

        // Create GridBagConstraints for label placement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between labels
        gbc.gridx = 0;  // Single column layout
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels for Start, Tutorial, Settings, and Quit Game
        JLabel startLabel = new JLabel("Start");
        JLabel tutorialLabel = new JLabel("Tutorial");
        JLabel settingsLabel = new JLabel("Settings");
        JLabel creditsLabel = new JLabel("Credits");
        JLabel quitLabel = new JLabel("Quit Game");

        // Set custom font and color to white
        if (customFont != null) {
            startLabel.setFont(customFont);
            tutorialLabel.setFont(customFont);
            settingsLabel.setFont(customFont);
            creditsLabel.setFont(customFont);
            quitLabel.setFont(customFont);
        }

        // Make text white
        startLabel.setForeground(Color.WHITE);
        tutorialLabel.setForeground(Color.WHITE);
        settingsLabel.setForeground(Color.WHITE);
        creditsLabel.setForeground(Color.WHITE);
        quitLabel.setForeground(Color.WHITE);

        // Make labels look clickable (like buttons)
        startLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tutorialLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        settingsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        creditsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        quitLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add labels to the panel
        gbc.gridy = 0;  // Row position
        buttonPanel.add(startLabel, gbc);
        gbc.gridy = 1;  // Row position
        buttonPanel.add(tutorialLabel, gbc);
        gbc.gridy = 2;  // Row position
        buttonPanel.add(settingsLabel, gbc);
        gbc.gridy = 3;  // Row position
        buttonPanel.add(creditsLabel, gbc);
        gbc.gridy = 4;
        buttonPanel.add(quitLabel, gbc);

        // Add the button panel on top of the background
        backgroundLabel.setLayout(new GridBagLayout());
        backgroundLabel.add(buttonPanel);

        // Add the background label to the main panel
        mainMenuPanel.add(backgroundLabel);

        // Mouse listeners to handle click events for labels
        startLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(cardPanel, "characterCreation");
            }
        });

        tutorialLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(cardPanel, "tutorial");
            }
        });

        settingsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(cardPanel, "settings");
            }
        });

        creditsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(cardPanel, "credits");
            }
        });

        quitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.exit(0); // Quit the game
            }
        });

        return mainMenuPanel;
    }

    // Method to play music
    public void playMusic() {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/MainMenuMusic.wav");
            if (audioSrc == null) {
                throw new IllegalArgumentException("Audio file not found!");
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Start playing the audio
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the audio if needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to stop the music
    private void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Method to resume the music
    private void resumeMusic() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }


    // Declare variables to store the selected cat color and username
    public String selectedCatColor;
    public String username;

    /// ******** Character Creation Screen ********
    private JPanel createCharacterCreationScreen() {
        // Create the main panel with a custom background
        JPanel characterPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                ImageIcon background = new ImageIcon(getClass().getResource("/characterSelBg.jpg"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        characterPanel.setLayout(new BorderLayout());

        // Load custom font
        Font customFont;
        try {
            InputStream fontStream = getClass().getResourceAsStream("/Daydream.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(24f);
        } catch (Exception e) {
            customFont = new Font("Arial", Font.PLAIN, 24); // Fallback font
        }

        // Create a label for the title
        JLabel titleLabel = new JLabel("Character Creation", JLabel.CENTER);
        titleLabel.setFont(customFont.deriveFont(Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);  // Set text color to contrast with the background
        characterPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a panel to hold the labels (instead of buttons) on the left side
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(0, 1, 10, 10)); // Vertical layout for labels
        labelPanel.setOpaque(false);  // Make panel transparent to show the background

        JLabel colorLabel = new JLabel("    Choose your cat color:");
        colorLabel.setFont(customFont);
        colorLabel.setForeground(Color.WHITE);  // Set text color

        JLabel orangeLabel = new JLabel("         Orange");
        JLabel blackLabel = new JLabel("          Black");
        JLabel whiteLabel = new JLabel("          White");

        // Set font and color for the clickable labels
        orangeLabel.setFont(customFont.deriveFont(Font.BOLD, 16));
        blackLabel.setFont(customFont.deriveFont(Font.BOLD, 16));
        whiteLabel.setFont(customFont.deriveFont(Font.BOLD, 16));
        orangeLabel.setForeground(Color.ORANGE);
        blackLabel.setForeground(Color.GRAY);
        whiteLabel.setForeground(Color.WHITE);

        // Make the labels clickable
        orangeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        blackLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        whiteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add labels to the label panel
        labelPanel.add(colorLabel);
        labelPanel.add(orangeLabel);
        labelPanel.add(blackLabel);
        labelPanel.add(whiteLabel);

        // Create a panel for the cat image on the right side
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false); // Make panel transparent
        JLabel catImageLabel = new JLabel();
        catImageLabel.setHorizontalAlignment(JLabel.CENTER);
        catImageLabel.setVerticalAlignment(JLabel.CENTER); // Center the image vertically
        imagePanel.add(catImageLabel, BorderLayout.CENTER);

        // Create a panel for username input and labels
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout()); // BorderLayout for easier placement
        inputPanel.setOpaque(false);  // Make panel transparent

// Username input field
        JPanel usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.setOpaque(false); // Make panel transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER; // Center the component

        JLabel usernameLabel = new JLabel("Enter your username:");
        usernameLabel.setFont(customFont.deriveFont(Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);  // Set text color

        JTextField usernameField = new JTextField(15);
        usernameField.setOpaque(false); // Make the text field transparent
        usernameField.setForeground(Color.WHITE); // Set text color
        usernameField.setCaretColor(Color.WHITE); // Set caret color to match the text color
        usernameField.setFont(customFont.deriveFont(16f)); // Set custom font to the text field

// Set placeholder text
        String placeholder = "username";
        usernameField.setText(placeholder);
        usernameField.setForeground(Color.GRAY); // Set placeholder text color

// Add focus listener to handle placeholder text
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals(placeholder)) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.WHITE); // Set text color when typing
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(placeholder);
                    usernameField.setForeground(Color.GRAY); // Set placeholder text color
                }
            }
        });


        // Remove the border of the text field to make it transparent
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0))); // Invisible border

        usernamePanel.add(usernameLabel, gbc);
        gbc.gridy = 1; // Positioning the text field below the label
        usernamePanel.add(usernameField, gbc);

        // Add username panel and labels to the input panel
        inputPanel.add(usernamePanel, BorderLayout.NORTH);
        inputPanel.add(labelPanel, BorderLayout.WEST); // Label panel on the left side
        inputPanel.add(imagePanel, BorderLayout.CENTER); // Image panel on the right side

        // Add the input panel to the main panel
        characterPanel.add(inputPanel, BorderLayout.CENTER);

        // Add Save and Back labels to the bottom of the main panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false); // Make panel transparent
        JLabel saveLabel = new JLabel("Save & Continue     ");
        JLabel backLabel = new JLabel("Back");

        // Set font and color for the clickable labels
        saveLabel.setFont(customFont);
        backLabel.setFont(customFont);
        saveLabel.setForeground(Color.WHITE);
        backLabel.setForeground(Color.WHITE);

        // Make the labels clickable
        saveLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        bottomPanel.add(saveLabel);
        bottomPanel.add(backLabel);

        characterPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Mouse listeners for the color labels
        orangeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedCatColor = "Orange";
                catImageLabel.setIcon(new ImageIcon(getClass().getResource("/orngeFront.png")));
                fitImageToPanel(catImageLabel, 250, 300);  // Custom width and height
            }
        });

        blackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedCatColor = "Black";
                catImageLabel.setIcon(new ImageIcon(getClass().getResource("/blckFront.png")));
                fitImageToPanel(catImageLabel, 250, 300);  // Custom width and height
            }
        });

        whiteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedCatColor = "White";
                catImageLabel.setIcon(new ImageIcon(getClass().getResource("/whteFront.png")));
                fitImageToPanel(catImageLabel, 250, 300);  // Custom width and height
            }
        });

        // Mouse listeners for Save and Back labels
        saveLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = usernameField.getText().trim(); // Trim whitespace from the username
                if (username.isEmpty() || selectedCatColor == null) {
                    JOptionPane.showMessageDialog(characterPanel, "Please enter a username and select a cat color.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Pass 'this' to the GameIntroScreen constructor
                    JPanel gameIntroPanel = new GameIntroScreen(username, MainMenuScreen.this, selectedCatColor);
                    cardPanel.add(gameIntroPanel, "gameIntro");

                    // Show the game introduction screen
                    cardLayout.show(cardPanel, "gameIntro");
                }
            }
        });

        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "mainMenu");
            }
        });

// Add input validation for the username field
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLabel.getMouseListeners()[0].mouseClicked(null); // Trigger save action on Enter key press
            }
        });

        return characterPanel;
    }



    // Helper method to display the image without stretching it
    private void fitImageToPanel(JLabel label, int customWidth, int customHeight) {
        ImageIcon icon = (ImageIcon) label.getIcon();
        if (icon != null) {
            Image img = icon.getImage();
            // Scale the image to the custom width and height
            Image scaledImg = img.getScaledInstance(customWidth, customHeight, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImg));
        }
    }

    // Helper method to resize image to fit the label
    private void fitImageToPanel(JLabel label) {
        ImageIcon icon = (ImageIcon) label.getIcon();
        if (icon != null) {
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImg));
        }
    }

    // ******** Tutorial Screen ********
    private JPanel createTutorialScreen() {
        // Importing custom font from resources
        try (InputStream is = getClass().getResourceAsStream("/Daydream.ttf")) {
            if (is != null) {
                customFont = Font.createFont(Font.TRUETYPE_FONT, is);
                customFont = customFont.deriveFont(Font.BOLD, 20);  // Set font style and size
            } else {
                System.out.println("Error: Font file not found.");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.out.println("Error loading custom font.");
        }

        // Creates new panel
        JPanel TutorialPanel = new JPanel(new BorderLayout());
        // Creates image icon
        ImageIcon tutorialBg = new ImageIcon(getClass().getResource("/tutorialBg.jpg"));
        JLabel imageHolder = new JLabel(tutorialBg);
        // Add label to panel
        TutorialPanel.add(imageHolder, BorderLayout.CENTER);

        // Set up the panel to hold buttons over the background
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);  // Make the panel transparent

        // Create GridBagConstraints for button placement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between buttons
        gbc.gridx = 0;  // Single column layout
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create and set up the text label
        JLabel textLabel = new JLabel("<html><b>In this game, your goal is simple: survive treacherous scenarios, defeat enemies, and catch the rat.</b><br>" +
                "Your decisions and dice rolls will determine your fate.<br>" +
                "Chose your cat: White: More life, easier. Orange: Balanced, with better health recovery. Black: Less life, harder!<br>" +
                "Face dangerous enemies across forests, deserts, and more. Roll the dice to see if you can beat the odds.<br>" +
                "Take risks to recover health, but beware—food and water can be scarce or spoiled.<br>" +
                "Survive all scenarios in order to catch the rat. Will you catch it?<br>" +
                "</html>");
        textLabel.setFont(customFont.deriveFont(Font.BOLD, 13));  // Set font and size
        textLabel.setForeground(Color.WHITE);  // Set text color to white

        // Add the text label to the panel
        gbc.gridy = 0;  // Position at the top
        buttonPanel.add(textLabel, gbc);

        // Button to go back to main menu
        JButton backButton = new JButton("Back");
        if (customFont != null) {
            backButton.setFont(customFont);
        }
        backButton.setBorder(BorderFactory.createEmptyBorder());  // Remove button border
        backButton.setForeground(Color.WHITE);  // Set text color to white

        // Add the back button to the panel below the text label
        gbc.gridy = 1;  // Row position
        buttonPanel.add(backButton, gbc);

        // Add the button panel on top of the background
        imageHolder.setLayout(new GridBagLayout());
        imageHolder.add(buttonPanel);

        // Add the background label to the main panel
        TutorialPanel.add(imageHolder);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "mainMenu");
            }
        });

        return TutorialPanel;
    }
    // Credits
    private JPanel createCreditsScreen() {
        // Importing custom font from resources
        try (InputStream is = getClass().getResourceAsStream("/Daydream.ttf")) {
            if (is != null) {
                customFont = Font.createFont(Font.TRUETYPE_FONT, is);
                customFont = customFont.deriveFont(Font.BOLD, 20);  // Set font style and size
            } else {
                System.out.println("Error: Font file not found.");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.out.println("Error loading custom font.");
        }

        // Creates new panel
        JPanel CreditsPanel = new JPanel(new BorderLayout());
        // Creates image icon
        ImageIcon tutorialBg = new ImageIcon(getClass().getResource("/mainBg.jpg"));
        JLabel imageHolder = new JLabel(tutorialBg);
        // Add label to panel
        CreditsPanel.add(imageHolder, BorderLayout.CENTER);

        // Set up the panel to hold buttons over the background
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);  // Make the panel transparent

        // Create GridBagConstraints for button placement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between buttons
        gbc.gridx = 0;  // Single column layout
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create and set up the text label
        JLabel textLabel = new JLabel("<html><b>Game Credits:</b><br>" +
                "Len Lopez: The visionary behind all graphic design, the heartbeat of our audio, and the architect of the main menu<br>" +
                "Kelly Cameron: Mastermind of the code that breathes life into room scenarios and movement through the game<br>" +
                "Caitlyn Cameron: Curator of the user interface, hunger and life mechanics, and the narrative that drives our story<br>" +
                "Pedro Sanford: Creator of the main menu, introduction screen, and the weaver of code integration<br>" +
                "</html>");

        textLabel.setFont(customFont.deriveFont(Font.BOLD, 13));  // Set font and size
        textLabel.setForeground(Color.WHITE);  // Set text color to white

        // Add the text label to the panel
        gbc.gridy = 0;  // Position at the top
        buttonPanel.add(textLabel, gbc);

        // Button to go back to main menu
        JButton backButton = new JButton("Back");
        if (customFont != null) {
            backButton.setFont(customFont);
        }
        backButton.setBorder(BorderFactory.createEmptyBorder());  // Remove button border
        backButton.setForeground(Color.WHITE);  // Set text color to white

        // Add the back button to the panel below the text label
        gbc.gridy = 1;  // Row position
        buttonPanel.add(backButton, gbc);

        // Add the button panel on top of the background
        imageHolder.setLayout(new GridBagLayout());
        imageHolder.add(buttonPanel);

        // Add the background label to the main panel
        CreditsPanel.add(imageHolder);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "mainMenu");
            }
        });

        return CreditsPanel;
    }

    // ******** Settings Screen ********
    private JPanel createSettingsScreen() {

        // Importing custom font from resources
        try (InputStream is = getClass().getResourceAsStream("/Daydream.ttf")) {
            if (is != null) {
                customFont = Font.createFont(Font.TRUETYPE_FONT, is);
                customFont = customFont.deriveFont(Font.BOLD, 20);  // Set font style and size
            } else {
                System.out.println("Error: Font file not found.");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.out.println("Error loading custom font.");
        }

        JPanel SettingsPanel = new JPanel(new BorderLayout());

        // Load the background image
        ImageIcon background = new ImageIcon(getClass().getResource("/settingsBg.jpg"));
        JLabel backgroundLabel = new JLabel(background);

        // Set up the panel to hold buttons over the background
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);  // Make the panel transparent

        // Create GridBagConstraints for button placement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between buttons
        gbc.gridx = 0;  // Single column layout
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add Music Toggle button
        JToggleButton musicToggleButton = new JToggleButton("Music: On", isMusicOn);
        if (customFont != null) {
            musicToggleButton.setFont(customFont);
        }
        musicToggleButton.setForeground(Color.WHITE);
        musicToggleButton.setOpaque(false);  // Make the button transparent
        musicToggleButton.setContentAreaFilled(false);  // Make the button background invisible
        musicToggleButton.setBorder(BorderFactory.createEmptyBorder()); // This removes the border

        gbc.gridy = 0;  // Row position
        buttonPanel.add(musicToggleButton, gbc);

        // Button to return to the main menu
        JButton backButton = new JButton("Back");

        // Set font on buttons to custom font
        if (customFont != null) {
            backButton.setFont(customFont);
        }

        // Remove the button border
        backButton.setBorder(BorderFactory.createEmptyBorder());

        // Set text color to white
        backButton.setForeground(Color.WHITE);

        // Add button to the panel
        gbc.gridy = 1;  // Row position
        buttonPanel.add(backButton, gbc);

        // Add the button panel on top of the background
        backgroundLabel.setLayout(new GridBagLayout());
        backgroundLabel.add(buttonPanel);

        // Add the background label to the main panel
        SettingsPanel.add(backgroundLabel);

        // Event listener for the music toggle button
        musicToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (musicToggleButton.isSelected()) {
                    musicToggleButton.setText("Music: Off");
                    stopMusic();
                } else {
                    musicToggleButton.setText("Music: On");
                    resumeMusic();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "mainMenu");
            }
        });

        return SettingsPanel;
    }

    public static void main(String[] args) {
        // Create and display the JFrame
        SwingUtilities.invokeLater(() -> {
            MainMenuScreen frame = new MainMenuScreen();
            frame.setVisible(true);
        });
    }
}