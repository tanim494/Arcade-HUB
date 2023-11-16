package com.tanim.arcadehub;

public class GameModel {
    private String name;
    private int iconResourceId;

    public GameModel(String name, int iconResourceId) {
        this.name = name;
        this.iconResourceId = iconResourceId;
    }

    public String getName() {
        return name;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }
}
