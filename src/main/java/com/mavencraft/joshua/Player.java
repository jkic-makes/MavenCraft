package com.mavencraft.joshua;

import java.util.EnumMap;
import java.util.Map;

public class Player {
    private double x = 10.0;
    private double y = 5.0;
    private double velX = 0.0;
    private double velY = 0.0;
    
    private final double width = 0.8;
    private final double height = 1.8;
    private boolean isGrounded = false;

    // Physics Constants
    private final double gravity = 0.03;
    private final double jumpStrength = -0.55;
    private final double moveSpeed = 0.15;

    private BlockType selectedBlock = BlockType.DIRT;
    private final Map<BlockType, Integer> inventory = new EnumMap<>(BlockType.class);

    public Player(GameMode gameMode) {
        for (BlockType bt : BlockType.values()) {
            inventory.put(bt, gameMode == GameMode.CREATIVE ? 999 : 10);
        }
    }

    public void update(boolean leftPressed, boolean rightPressed, boolean jumpPressed, World world) {
        // 1. Horizontal Movement & Collisions
        velX = 0;
        if (leftPressed) velX -= moveSpeed;
        if (rightPressed) velX += moveSpeed;

        x += velX;
        if (checkCollision(world)) {
            if (velX > 0) x = Math.floor(x + width) - width - 0.001;
            else if (velX < 0) x = Math.floor(x) + 1.0;
            velX = 0;
        }

        // 2. Vertical Movement (Gravity) & Collisions
        velY += gravity;
        y += velY;
        isGrounded = false;

        if (checkCollision(world)) {
            if (velY > 0) { // Landing on top of a solid block
                y = Math.floor(y + height) - height - 0.001;
                isGrounded = true;
            } else if (velY < 0) { // Hitting ceiling
                y = Math.floor(y) + 1.0;
            }
            velY = 0;
        }

        // 3. Jump Logic
        if (jumpPressed && isGrounded) {
            velY = jumpStrength;
            isGrounded = false;
        }

        // Screen boundary safety
        if (x < 0) x = 0;
        if (x + width > world.getWidth()) x = world.getWidth() - width;
    }

    private boolean checkCollision(World world) {
        int minX = (int) Math.floor(x);
        int maxX = (int) Math.floor(x + width);
        int minY = (int) Math.floor(y);
        int maxY = (int) Math.floor(y + height);

        for (int bx = minX; bx <= maxX; bx++) {
            for (int by = minY; by <= maxY; by++) {
                if (world.getBlock(bx, by).isSolid()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean overlapsBlock(int tileX, int tileY) {
        return (tileX >= Math.floor(x) && tileX <= Math.floor(x + width)) &&
               (tileY >= Math.floor(y) && tileY <= Math.floor(y + height));
    }

    // Inventory Helpers
    public int getBlockCount(BlockType type) {
        return inventory.getOrDefault(type, 0);
    }

    public void addBlock(BlockType type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public boolean useBlock(BlockType type, GameMode mode) {
        int count = inventory.getOrDefault(type, 0);
        if (mode == GameMode.CREATIVE || count > 0) {
            if (mode == GameMode.SURVIVAL) {
                inventory.put(type, count - 1);
            }
            return true;
        }
        return false;
    }

    // Getters & Setters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public BlockType getSelectedBlock() { return selectedBlock; }
    public void setSelectedBlock(BlockType selectedBlock) { this.selectedBlock = selectedBlock; }
}