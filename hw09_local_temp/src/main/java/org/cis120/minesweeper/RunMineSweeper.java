package org.cis120.minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RunMineSweeper implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("MineSweeper");
        frame.setLocation(550, 0);

        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("You're Doing Great!");
        status_panel.add(status);

        final MineSweeper minesweeper = new MineSweeper(status, 9);
        frame.add(minesweeper, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("New Game");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                minesweeper.reset(9);
            }
        });

        control_panel.add(reset);

        final JPanel saved_game_panel = new JPanel();
        frame.add(saved_game_panel, BorderLayout.EAST);

        final JButton saved_game = new JButton("Last Saved Game");
        saved_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                minesweeper.loadSavedBoard("files/savedgame.csv");
            }
        });
        saved_game_panel.add(saved_game);

        final JPanel save_game_panel = new JPanel();
        frame.add(save_game_panel, BorderLayout.WEST);

        final JButton save_game = new JButton("Save This Game");
        save_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (minesweeper.getFirstClickTrue()) {
                    JOptionPane.showMessageDialog(
                            frame, "Make A Move Before Saving a Game!", "Uh - Oh!",
                            JOptionPane.PLAIN_MESSAGE
                    );
                } else {
                    minesweeper.saveBoard("files/savedgame.csv");
                }
            }
        });

        save_game_panel.add(save_game);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JOptionPane.showMessageDialog(
                frame,
                "This game is an implementation of MineSweeper \n"
                        + "and it is a spin off the classic game. It implements \n"
                        + "the same elements that the original game does and \n"
                        + "hopefully is fun! \n \n"
                        + "Welcome to MineSweeper. There are bombs \n"
                        + "hidden throughout the board. If a tile \n "
                        + "has a number, that means there is that many \n"
                        + "bombs around that tile. Get through \n"
                        + "the board by clicking each tile without \n"
                        + "clicking on a bomb. If you would like to save \n"
                        + "your game, make sure to click a tile first. \n \n"
                        + "IMPORTANT: Make sure you have flagged all of \n"
                        + "the places where you think the bomb is!"
                        + " \n \n"

                        + "                           Good Luck!",
                "Instructions for Game",
                JOptionPane.PLAIN_MESSAGE
        );
    }

}