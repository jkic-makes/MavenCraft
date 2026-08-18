package com.mavencraft.joshua;

import java.util.Random;

public class World {
    private final String name;
    private final GameMode gameMode;
    private final int width = 64;
    private final int height = 32;
    private final BlockType[][] grid;

    public World(String name, GameMode gameMode) {
        this.name = name;
        this.gameMode = gameMode;
        this.grid = new BlockType[width][height];
        generateTerrain();
    }

    private void generateTerrain() {
        Random rand = new Random();
        int groundLevel = 16;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y == height - 1) {
                    grid[x][y] = BlockType.BEDROCK;
                } else if (y > groundLevel + 4) {
                    grid[x][y] = BlockType.STONE;
                } else if (y > groundLevel) {
                    grid[x][y] = BlockType.DIRT;
                } else if (y == groundLevel) {
                    grid[x][y] = BlockType.GRASS;
                } else {
                    grid[x][y] = BlockType.AIR;
                }
            }
        }

        for (int x = 5; x < width - 5; x += rand.nextInt(8) + 6) {
            int treeBaseY = groundLevel - 1;
            for (int trunk = 0; trunk < 3; trunk++) {
                grid[x][treeBaseY - trunk] = BlockType.WOOD;
            }
            for (int lx = -1; lx <= 1; lx++) {
                for (int ly = -3; ly <= -2; ly++) {
                    grid[x + lx][treeBaseY + ly] = BlockType.LEAVES;
                }
            }
        }
    }

    public BlockType getBlock(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return BlockType.BEDROCK;
        return grid[x][y];
    }

    public void setBlock(int x, int y, BlockType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            if (grid[x][y] != BlockType.BEDROCK) {
                grid[x][y] = type;
            }
        }
    }

    public String getName() { return name; }
    public GameMode getGameMode() { return gameMode; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}