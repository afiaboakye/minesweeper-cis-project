package org.cis120.minesweeper;

import java.awt.Graphics;

public abstract class Tile {
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int bombsAround;
    private String imgFile;

    // Tile Constructor
    public Tile(int xPos, int yPos, int width, int height, int bombsAround, String imgFile) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.bombsAround = bombsAround;
        this.imgFile = imgFile;

    }

    // Getters
    public int getXPos() {
        return this.xPos;
    }

    public int getYPos() {
        return this.yPos;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getBombsAround() {
        return this.bombsAround;
    }

    public String getImgFile() {
        return this.imgFile;
    }

    // Setters
    public void setXPos(int newX) {
        this.xPos = newX;
    }

    public void setYPos(int newY) {
        this.yPos = newY;
    }

    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    public void setImgFile(String newImgFile) {
        this.imgFile = newImgFile;
    }

    public abstract String getTile();

    public abstract void drawTile(Graphics g);
}