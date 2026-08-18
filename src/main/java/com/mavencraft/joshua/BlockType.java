package com.mavencraft.joshua;

import java.awt.Color;

public enum BlockType {
    AIR(new Color(0, 0, 0, 0), false, 0),
    GRASS(new Color(34, 139, 34), true, 3),
    DIRT(new Color(139, 69, 19), true, 2),
    STONE(new Color(128, 128, 128), true, 5),
    BEDROCK(new Color(50, 50, 50), true, -1),
    WOOD(new Color(160, 82, 45), true, 3),
    LEAVES(new Color(50, 205, 50), true, 1);

    private final Color color;
    private final boolean solid;
    private final int hardness;

    BlockType(Color color, boolean solid, int hardness) {
        this.color = color;
        this.solid = solid;
        this.hardness = hardness;
    }

    public Color getColor() { return color; }
    public boolean isSolid() { return solid; }
    public int getHardness() { return hardness; }
}