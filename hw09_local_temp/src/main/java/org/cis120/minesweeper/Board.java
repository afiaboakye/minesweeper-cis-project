package org.cis120.minesweeper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Board {
    private int difficultyLevel;
    private Tile[][] gameBoard;
    private int score;
    private int tilesLeft;
    private String imgFile;
    private BufferedImage img;
    private int width;
    private int height;

    public Board(int difficultyLevel, Tile[][] gameBoard, int score, int tilesLeft) {
        this.difficultyLevel = difficultyLevel;
        this.gameBoard = gameBoard;
        this.score = score;
        this.tilesLeft = tilesLeft;
        this.imgFile = "files/board.png";
        this.width = 500;
        this.height = 500;
        try {
            if (img == null) {
                img = ImageIO.read(new File(imgFile));
            }
        } catch (IOException e) {
            System.out.println("IOException Gotten");
        }
    }

    public int getDifficultyLevel() {
        return this.difficultyLevel;
    }

    public int getScore() {
        return this.score;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTilesLeft() {
        return this.tilesLeft;
    }

    public Tile[][] getGameBoard() {
        return this.gameBoard;
    }

    public int resetTilesLeft() {
        return (difficultyLevel * difficultyLevel) - difficultyLevel;
    }

    public void setTilesLeft(int newTilesLeft) {
        tilesLeft = newTilesLeft;
    }

    public void decreaseTilesLeft() {
        tilesLeft--;
    }

    public void changeGameTile(Tile t, int x, int y) {
        gameBoard[x][y] = t;
    }

    public boolean userWin() {
        boolean userWon = true;
        for (int i = 0; i < difficultyLevel; i++) {
            for (int j = 0; j < difficultyLevel; j++) {
                if (gameBoard[i][j].getTile().equals("cover")
                        || (gameBoard[i][j].getTile().equals("cover")
                                && gameBoard[i][j].getTile().equals("bomb"))) {
                    userWon = false;
                }
            }
        }
        return userWon;
    }

    public boolean userLose() {
        for (int i = 0; i < difficultyLevel; i++) {
            for (int j = 0; j < difficultyLevel; j++) {
                if (gameBoard[i][j].getImgFile().equals("files/bombtile.png")) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getBombsAround(Tile tile) {
        int surrBombs = 0;
        int x = tile.getXPos();
        int y = tile.getYPos();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (gameBoard[i + x][j + y].getImgFile() == "files/bombtile.png") {
                    surrBombs++;
                }
            }
        }
        return surrBombs;
    }

    public void drawBoard(Graphics g) {
        g.drawImage(img, 0, 0, 500, 500, null);
        for (int i = 0; i < difficultyLevel; i++) {
            for (int j = 0; j < difficultyLevel; j++) {
                this.gameBoard[i][j].drawTile(g);
            }
        }
    }

    public void click(Tile[][] solutionBoard, int x, int y) {
        if (x < 0 || y < 0 || x > difficultyLevel - 1 || y > difficultyLevel - 1) {
            return;
        }
        System.out.println(x);
        System.out.println(y);
        if (!(solutionBoard[x][y].getTile().equals("blank"))) {
            gameBoard[x][y] = solutionBoard[x][y];
            decreaseTilesLeft();
        } else {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    System.out.println("j: " + j + ", i: " + i);
                    if (j == 0 && i == 0) {
                        gameBoard[x][y] = solutionBoard[x][y];
                        decreaseTilesLeft();
                    } else if (x + i > -1 && x + i < difficultyLevel && y + j > -1 &&
                            y + j < difficultyLevel) {
                        if (gameBoard[x + i][y + j].getTile()
                                .equals("cover")
                                && !solutionBoard[x + i][y + j].getTile()
                                        .equals("bomb")) {
                            click(solutionBoard, x + i, y + j);
                        }
                    }
                }
            }
        }

        /*
         * if (solutionBoard[x][y].getImgFile().equals("files/blanktile.png")) {
         * for (int i = -1; i < 2; i++) {
         * for (int j = -1; j < 2; j++) {
         * if (!(i + x < 0 || i + x > difficultyLevel || j + y < 0
         * || j + y > difficultyLevel)) {
         * gameBoard[i + x][j + y] = solutionBoard[i + x][j + y];
         * if (i == 0 && j == 0) {
         * decreaseTilesLeft();
         * continue;
         * } else if (!solutionBoard[i + x][j + y].getImgFile().
         * equals("files/bombtile.png")) {
         * gameBoard[i + x][j + y] = solutionBoard[i + x][j + y];
         * if (i == 0 && j == 0) {
         * decreaseTilesLeft();
         * continue;
         * } else {
         * click(solutionBoard, i + x, j + y);
         * }
         * }
         * 
         * }
         * }
         * }
         * } else {
         * gameBoard[x][y] = solutionBoard[x][y];
         * decreaseTilesLeft();
         * }
         */
    }

    public void reset(Graphics g) {
        int tileWidth = (int) (500 / difficultyLevel);
        int tileHeight = (int) (500 / difficultyLevel);
        for (int i = 0; i < difficultyLevel; i++) {
            for (int j = 0; j < difficultyLevel; j++) {
                gameBoard[i][j] = new CoverTile(
                        i * tileWidth, j * tileHeight,
                        tileWidth, tileHeight
                );
            }
        }
        resetTilesLeft();
        drawBoard(g);
    }

}