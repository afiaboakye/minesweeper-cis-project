package org.cis120.minesweeper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CoverTile extends Tile {
    private String imgFile;
    private static BufferedImage img;
    private String tile;

    public CoverTile(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height, 0, "files/coveredtile.png");
        this.imgFile = "files/coveredtile.png";
        this.tile = "cover";
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

    public void drawTile(Graphics g) {
        g.drawImage(img, this.getXPos(), this.getYPos(), this.getWidth(), this.getHeight(), null);
    }

}