package org.cis120.minesweeper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BlankTile extends Tile {
    private String imgFile;
    private BufferedImage img;
    private String tile;

    public BlankTile(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height, 0, "files/blanktile.png");
        this.imgFile = "files/blanktile.png";
        this.tile = "blank";
        try {
            if (img == null) {
                img = ImageIO.read(new File(imgFile));
            }
        } catch (IOException e) {
            System.out.println("IOException Gotten");
        }
    }

    public String getTile() {
        return this.tile;
    }

    @Override
    public void drawTile(Graphics g) {
        g.drawImage(img, this.getXPos(), this.getYPos(), this.getWidth(), this.getHeight(), null);
    }
}