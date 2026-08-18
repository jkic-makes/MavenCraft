package com.mavencraft.joshua;

public enum GameMode {
    SURVIVAL("Survival", "Resource gathering, block durability, and limited inventory."),
    CREATIVE("Creative", "Unlimited resources, instant block breaking, and free placement.");

    private final String displayName;
    private final String description;

    GameMode(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}