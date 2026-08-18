package com.mavencraft.joshua;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, MouseListener, ActionListener {
    private final World world;
    private final GameEngine frame;
    private final Player player;
    private final int tileSize = 20;

    // Input States
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean jumpPressed = false;

    private final Timer gameLoop;

    public GamePanel(GameEngine frame, World world) {
        this.frame = frame;
        this.world = world;
        this.player = new Player(world.getGameMode());

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        // 60 FPS Game Loop (~16ms per frame)
        gameLoop = new Timer(16, this);
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Step 1: Update domain logic (Physics)
        player.update(leftPressed, rightPressed, jumpPressed, world);

        // Step 2: Redraw UI
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Render World
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                BlockType bt = world.getBlock(x, y);
                if (bt != BlockType.AIR) {
                    g2.setColor(bt.getColor());
                    g2.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
                } else {
                    g2.setColor(new Color(135, 206, 235)); // Sky Color
                    g2.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        }

        // Render Player
        g2.setColor(Color.RED);
        g2.fillRect((int) (player.getX() * tileSize), (int) (player.getY() * tileSize),
                    (int) (player.getWidth() * tileSize), (int) (player.getHeight() * tileSize));

        drawHUD(g2);
    }

    private void drawHUD(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(10, 10, 360, 95);

        g2.setColor(Color.WHITE);
        g2.drawString("World: " + world.getName() + " | Mode: " + world.getGameMode().getDisplayName(), 20, 30);
        g2.drawString("Selected Block: " + player.getSelectedBlock().name(), 20, 50);
        
        String countStr = (world.getGameMode() == GameMode.CREATIVE) 
                ? "∞" 
                : String.valueOf(player.getBlockCount(player.getSelectedBlock()));
        g2.drawString("Inventory Count: " + countStr, 20, 70);
        g2.drawString("[A/D] Walk | [SPACE/W] Jump | [1-5] Select Block | [ESC] Menu", 20, 90);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int tileX = e.getX() / tileSize;
        int tileY = e.getY() / tileSize;

        if (tileX < 0 || tileX >= world.getWidth() || tileY < 0 || tileY >= world.getHeight()) return;

        if (SwingUtilities.isLeftMouseButton(e)) {
            // Mine block
            BlockType current = world.getBlock(tileX, tileY);
            if (current != BlockType.AIR && current != BlockType.BEDROCK) {
                world.setBlock(tileX, tileY, BlockType.AIR);
                if (world.getGameMode() == GameMode.SURVIVAL) {
                    player.addBlock(current);
                }
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            // Place block
            if (world.getBlock(tileX, tileY) == BlockType.AIR && !player.overlapsBlock(tileX, tileY)) {
                if (player.useBlock(player.getSelectedBlock(), world.getGameMode())) {
                    world.setBlock(tileX, tileY, player.getSelectedBlock());
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A) leftPressed = true;
        if (code == KeyEvent.VK_D) rightPressed = true;
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_SPACE) jumpPressed = true;

        if (code == KeyEvent.VK_1) player.setSelectedBlock(BlockType.DIRT);
        if (code == KeyEvent.VK_2) player.setSelectedBlock(BlockType.GRASS);
        if (code == KeyEvent.VK_3) player.setSelectedBlock(BlockType.STONE);
        if (code == KeyEvent.VK_4) player.setSelectedBlock(BlockType.WOOD);
        if (code == KeyEvent.VK_5) player.setSelectedBlock(BlockType.LEAVES);

        if (code == KeyEvent.VK_ESCAPE) {
            gameLoop.stop();
            frame.showMainMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false;
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_SPACE) jumpPressed = false;
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}