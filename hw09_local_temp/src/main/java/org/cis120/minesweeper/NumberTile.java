package org.cis120.minesweeper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class NumberTile extends Tile {
    private String imgFile;
    private BufferedImage img;
    private String tile;

    public NumberTile(int xPos, int yPos, int width, int height, int surrBombs) {
        super(xPos, yPos, width, height, surrBombs, null);
        if (surrBombs == 1) {
            imgFile = "files/onetile.png";
            super.setImgFile(imgFile);
            this.tile = "1";
        } else if (surrBombs == 2) {
            imgFile = "files/twotile.png";
            this.tile = "2";
            super.setImgFile(imgFile);
        } else if (surrBombs == 3) {
            imgFile = "files/threetile.png";
            this.tile = "3";
            super.setImgFile(imgFile);
        } else if (surrBombs == 4) {
            imgFile = "files/fourtile.png";
            this.tile = "4";
            super.setImgFile(imgFile);
        } else if (surrBombs == 5) {
            imgFile = "files/fivetile.png";
            this.tile = "5";
            super.setImgFile(imgFile);
        } else if (surrBombs == 6) {
            imgFile = "files/sixtile.png";
            this.tile = "6";
            super.setImgFile(imgFile);
        } else if (surrBombs == 7) {
            imgFile = "files/seventile.png";
            this.tile = "7";
            super.setImgFile(imgFile);
        } else if (surrBombs == 8) {
            imgFile = "files/eighttile.png";
            this.tile = "8";
            super.setImgFile(imgFile);
        }
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