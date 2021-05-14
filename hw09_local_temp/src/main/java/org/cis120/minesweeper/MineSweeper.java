package org.cis120.minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("serial")
public class MineSweeper extends JPanel {

    private Board b;
    private int difficultyLevel;
    private JLabel status;
    private Tile[][] solutionBoard;
    private boolean firstClick;

    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 500;

    public MineSweeper(JLabel statusInit, int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        Tile[][] gameBoard = new Tile[difficultyLevel][difficultyLevel];
        this.solutionBoard = new Tile[difficultyLevel][difficultyLevel];
        int tileWidth = (int) (BOARD_WIDTH / difficultyLevel);
        int tileHeight = (int) (BOARD_HEIGHT / difficultyLevel);
        for (int i = 0; i < difficultyLevel; i++) {
            for (int j = 0; j < difficultyLevel; j++) {
                gameBoard[i][j] = new CoverTile(
                        i * tileWidth, j * tileHeight,
                        tileWidth, tileHeight
                );
            }
        }
        this.b = new Board(
                difficultyLevel, gameBoard, 0,
                (difficultyLevel * difficultyLevel) - difficultyLevel
        );
        this.status = statusInit;
        this.firstClick = true;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (status.getText().equals("You're Doing Great!")) {
                    Point point = e.getPoint();
                    if (e.getButton() == 1) {
                        if (firstClick) {
                            for (int i = 0; i < difficultyLevel; i++) {
                                for (int j = 0; j < difficultyLevel; j++) {
                                    solutionBoard[i][j] = new CoverTile(
                                            i * tileWidth,
                                            j * tileHeight, tileWidth, tileHeight
                                    );
                                }
                            }
                            int bombs = difficultyLevel;
                            while (bombs != 0) {
                                int i = (int) (difficultyLevel * Math.random());
                                int j = (int) (difficultyLevel * Math.random());
                                if (i != (point.x / tileWidth) && j != (point.y / tileHeight)
                                        && !(solutionBoard[i][j].getTile().equals("bomb"))) {
                                    solutionBoard[i][j] = new BombTile(
                                            i
                                                    * tileWidth,
                                            j * tileHeight,
                                            tileWidth, tileHeight
                                    );

                                    bombs--;
                                }
                            }
                            for (int i = 0; i < difficultyLevel; i++) {
                                for (int j = 0; j < difficultyLevel; j++) {
                                    if (!(solutionBoard[i][j].getTile().equals("bomb"))) {
                                        int surrBombs = 0;
                                        for (int k = -1; k < 2; k++) {
                                            for (int l = -1; l < 2; l++) {
                                                if (((k + i) > -1 && (k + i) < difficultyLevel &&
                                                        (l + j) < difficultyLevel
                                                        && (l + j) > -1)) {
                                                    if (solutionBoard[k + i][l + j].getTile()
                                                            .equals("bomb")) {
                                                        surrBombs++;
                                                    }
                                                }
                                            }
                                        }
                                        if (surrBombs == 0) {
                                            solutionBoard[i][j] = new BlankTile(
                                                    i * tileWidth, j * tileHeight,
                                                    tileWidth, tileHeight
                                            );
                                        } else {
                                            solutionBoard[i][j] = new NumberTile(
                                                    i * tileWidth, j * tileHeight,
                                                    tileWidth, tileHeight, surrBombs
                                            );
                                        }
                                    }
                                }
                            }
                            firstClick = false;
                            b.click(solutionBoard, (point.x / tileWidth), (point.y / tileHeight));
                            repaint();
                        } else {
                            b.click(
                                    solutionBoard, (int) (point.x / tileWidth),
                                    (int) (point.y / tileHeight)
                            );
                            updateStatus();
                            repaint();
                        }
                    }
                    if (e.getButton() != 0 && e.getButton() != 1) {
                        int x = gameBoard[(int) (point.x / tileWidth)][(int) (point.y / tileHeight)]
                                .getXPos();
                        int y = gameBoard[(int) (point.x / tileWidth)][(int) (point.y / tileHeight)]
                                .getYPos();
                        gameBoard[(int) (point.x / tileWidth)][(int) (point.y
                                / tileHeight)] = new FlagTile(x, y, tileWidth, tileHeight);
                        repaint();
                    }
                }
            }
        });
    }

    public boolean getFirstClickTrue() {
        return firstClick;
    }

    public String extractColumn(String csvLine, int csvColumn) {
        if (csvLine == null) {
            return null;
        }
        int commas = 0;
        for (int i = 0; i < csvLine.length(); i++) {
            if (csvLine.charAt(i) == ',') {
                commas++;
            }
        }
        if (csvLine != null && commas >= csvColumn) {
            String[] lineArray = csvLine.split(",", csvColumn + 2);
            return lineArray[csvColumn];
        }
        return null;
    }

    public List<String> csvFileToTiles(String pathToCSVFile, int boardColumn) {
        List<String> tiles = new LinkedList<String>();
        FileLineIterator file = new FileLineIterator(pathToCSVFile);

        while (file.hasNext()) {
            String column = extractColumn(file.next(), boardColumn);
            if (column != null) {
                tiles.add(column);
            }
        }
        return tiles;
    }

    public void loadSavedBoard(String pathToCSVFile) {
        List<String> savedDifficultyLevel = csvFileToTiles(pathToCSVFile, 0);
        List<String> savedTilesLeft = csvFileToTiles(pathToCSVFile, 1);
        List<String> solutionBoardTiles = csvFileToTiles(pathToCSVFile, 2);
        List<String> gameBoardTiles = csvFileToTiles(pathToCSVFile, 3);

        int tileWidth = (int) (BOARD_WIDTH / difficultyLevel);
        int tileHeight = (int) (BOARD_HEIGHT / difficultyLevel);

        difficultyLevel = Integer.parseInt(savedDifficultyLevel.get(0));
        b.setTilesLeft(Integer.parseInt(savedTilesLeft.get(0)));
        for (int i = 0; i < difficultyLevel; i++) {
            for (int j = 0; j < difficultyLevel; j++) {
                int index = j + (difficultyLevel * i);
                System.out.println(index);
                if (solutionBoardTiles.get(index).equals("blank")) {
                    System.out.println(index);
                    solutionBoard[i][j] = new BlankTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight
                    );
                } else if (solutionBoardTiles.get(index).equals("cover")) {
                    solutionBoard[i][j] = new CoverTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight
                    );
                } else if (solutionBoardTiles.get(index).equals("bomb")) {
                    solutionBoard[i][j] = new BombTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight
                    );
                } else if (solutionBoardTiles.get(index).equals("flag")) {
                    solutionBoard[i][j] = new FlagTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight
                    );
                } else {
                    solutionBoard[i][j] = new NumberTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight,
                            Integer.parseInt(solutionBoardTiles.get(index))
                    );
                }
            }
        }
        for (int i = 0; i < difficultyLevel; i++) {
            for (int j = 0; j < difficultyLevel; j++) {
                int index = j + (difficultyLevel * i);
                if (gameBoardTiles.get(index).equals("blank")) {
                    Tile newTile = new BlankTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight
                    );
                    b.changeGameTile(newTile, i, j);
                } else if (gameBoardTiles.get(index).equals("cover")) {
                    Tile newTile = new CoverTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight
                    );
                    b.changeGameTile(newTile, i, j);
                } else if (gameBoardTiles.get(index).equals("bomb")) {
                    Tile newTile = new BombTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight
                    );
                    b.changeGameTile(newTile, i, j);
                } else if (gameBoardTiles.get(index).equals("flag")) {
                    Tile newTile = new FlagTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight
                    );
                    b.changeGameTile(newTile, i, j);
                } else {
                    Tile newTile = new NumberTile(
                            i * tileWidth, j * tileHeight,
                            tileWidth, tileHeight,
                            Integer.parseInt(gameBoardTiles.get(index))
                    );
                    b.changeGameTile(newTile, i, j);
                }
            }
        }

        b.drawBoard(getGraphics());
        repaint();
    }

    public void saveBoard(String fileName) {
        File file = new File(fileName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            file = null;
        } catch (IOException e) {
            System.out.println("IOException Gotten");
            writer = null;
        }
        try {
            for (int i = 0; i < difficultyLevel; i++) {
                for (int j = 0; j < difficultyLevel; j++) {
                    String difficulty = String.valueOf(difficultyLevel);
                    String tilesLeft = String.valueOf(b.getTilesLeft());
                    String solution = solutionBoard[i][j].getTile();
                    String game = b.getGameBoard()[i][j].getTile();
                    writer.write(difficulty + "," + tilesLeft + "," + solution + "," + game);

                    if ((i + 1) * (j + 1) != difficultyLevel * difficultyLevel) {
                        writer.newLine();
                    }
                }
            }
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            file = null;
        } catch (IOException e) {
            System.out.println("IOException Gotten");
            writer = null;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        b.drawBoard(g);
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void reset(int difficultyLevel) {
        b.reset(getGraphics());
        solutionBoard = new Tile[difficultyLevel][difficultyLevel];
        firstClick = true;
        status.setText("You're Doing Great!");
        repaint();
    }

    private void updateStatus() {
        if (b.userLose()) {
            status.setText("You Lost!");
            repaint();
        } else if (b.userWin()) {
            status.setText("You Won!");
        } else {
            b.drawBoard(getGraphics());
            status.setText("You're Doing Great!");
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}