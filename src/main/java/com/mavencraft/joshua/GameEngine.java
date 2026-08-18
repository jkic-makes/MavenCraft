package com.mavencraft.joshua;

import javax.swing.*;
import java.awt.*;

public class GameEngine extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainContainer = new JPanel(cardLayout);

    public GameEngine() {
        setTitle("MavenCraft Java Engine");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainContainer.add(createFrontPagePanel(), "MainMenu");
        mainContainer.add(createWorldCreationPanel(), "CreateWorld");

        add(mainContainer);
        showMainMenu();
    }

    public void showMainMenu() {
        cardLayout.show(mainContainer, "MainMenu");
    }

    public void showCreateWorld() {
        cardLayout.show(mainContainer, "CreateWorld");
    }

    private JPanel createFrontPagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("MAVENCRAFT", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);

        JButton playBtn = new JButton("Singleplayer");
        JButton exitBtn = new JButton("Quit Game");

        playBtn.addActionListener(e -> showCreateWorld());
        exitBtn.addActionListener(e -> System.exit(0));

        gbc.gridx = 0; gbc.gridy = 0; panel.add(title, gbc);
        gbc.gridy = 1; panel.add(playBtn, gbc);
        gbc.gridy = 2; panel.add(exitBtn, gbc);

        return panel;
    }

    private JPanel createWorldCreationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(45, 45, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create New World", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JTextField nameField = new JTextField("New World", 15);
        JComboBox<GameMode> modeCombo = new JComboBox<>(GameMode.values());

        JLabel modeDesc = new JLabel(GameMode.SURVIVAL.getDescription(), SwingConstants.CENTER);
        modeDesc.setForeground(Color.LIGHT_GRAY);

        modeCombo.addActionListener(e -> {
            GameMode selected = (GameMode) modeCombo.getSelectedItem();
            if (selected != null) {
                modeDesc.setText(selected.getDescription());
            }
        });

        JButton createBtn = new JButton("Create New World");
        JButton cancelBtn = new JButton("Cancel");

        createBtn.addActionListener(e -> {
            String worldName = nameField.getText().trim();
            if (worldName.isEmpty()) worldName = "World";
            GameMode mode = (GameMode) modeCombo.getSelectedItem();

            World newWorld = new World(worldName, mode);
            GamePanel gamePanel = new GamePanel(this, newWorld);
            
            mainContainer.add(gamePanel, "Game");
            cardLayout.show(mainContainer, "Game");
            gamePanel.requestFocusInWindow();
        });

        cancelBtn.addActionListener(e -> showMainMenu());

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(title, gbc);
        gbc.gridy = 1; gbc.gridwidth = 1; panel.add(new JLabel("World Name:"), gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Game Mode:"), gbc);
        gbc.gridx = 1; panel.add(modeCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; panel.add(modeDesc, gbc);
        gbc.gridy = 4; gbc.gridwidth = 1; panel.add(createBtn, gbc);
        gbc.gridx = 1; panel.add(cancelBtn, gbc);

        return panel;
    }
}