package models;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import views.*;


public abstract class GameBoard {
    protected GameView gameView;
    protected BoardData virtualBoard;
    protected ArrayList<Color> colorList;
    protected JButton[] buttons;
    protected Move playerMove;
    protected Move computerMove;
    private boolean gameOver = false;

    public GameBoard(GameView gameView, BoardData virtualBoard) {
        this.gameView = gameView;
        this.virtualBoard = virtualBoard;
        this.colorList = new ArrayList<>();
        this.playerMove = new PlayerMove(this, virtualBoard);
        this.computerMove = new ComputerMove(this, virtualBoard);
    }

    public void createBoard(int boardSize) {
        JPanel buttonPanel = gameView.getButtonPanel();
        buttonPanel.removeAll();
        buttonPanel.setLayout(new GridLayout(boardSize, boardSize));
        buttons = new JButton[boardSize * boardSize];

        for (int i = 0; i < boardSize * boardSize; i++) {
            final int currentI = i;
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttonPanel.add(buttons[i]);
            colorList.add(Color.white);
            
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(currentI);
                }
            });
        }

        gameView.setButtons(buttons);
        gameView.revalidate();
        gameView.repaint();
    }
    
    public void cpuGame() {
    	while(!gameOver) {
    		if(gameView.getPlayer1()) {
            	gameOver = computerMove.execute(-1, Color.BLUE, false, gameView.getCheck3().isSelected());
                gameView.setPlayer1(false);
                gameView.setPlayer2(true);
                gameView.getCurrentTurn().setText("Current turn is Red");
    		}
    		else {
            	gameOver = computerMove.execute(-1, Color.RED, false, gameView.getCheck3().isSelected());
                gameView.setPlayer2(false);
                gameView.setPlayer1(true);
                gameView.getCurrentTurn().setText("Current turn is Blue");
    		}
    	}
    }
    
    public void loadGame() {
    	
    }

    protected void handleButtonClick(int buttonIndex) {
        if (gameView.getPlayer1() && (gameView.getSelector1().isSelected() || gameView.getSelector2().isSelected())) {
            gameOver = playerMove.execute(buttonIndex, Color.BLUE, gameView.getSelector1().isSelected(), gameView.getCheck3().isSelected());
            gameView.setPlayer1(false);
            gameView.setPlayer2(true);
            gameView.getCurrentTurn().setText("Current turn is Red");
            //Check to see if the red player is CPU
            if(gameView.getCheck2().isSelected() && (!gameOver)) {
            	gameOver = computerMove.execute(-1, Color.RED, false, gameView.getCheck3().isSelected());
                gameView.setPlayer2(false);
                gameView.setPlayer1(true);
                gameView.getCurrentTurn().setText("Current turn is Blue");
            }
        } 
        else if (gameView.getPlayer2() && (gameView.getSelector3().isSelected() || gameView.getSelector4().isSelected())) {
            gameOver = playerMove.execute(buttonIndex, Color.RED, gameView.getSelector3().isSelected(), gameView.getCheck3().isSelected());
            gameView.setPlayer2(false);
            gameView.setPlayer1(true);
            gameView.getCurrentTurn().setText("Current turn is Blue");
            //Check to see if the red player is CPU
            if(gameView.getCheck1().isSelected() && (!gameOver)) {
            	gameOver = computerMove.execute(-1, Color.BLUE, false, gameView.getCheck3().isSelected());
                gameView.setPlayer1(false);
                gameView.setPlayer2(true);
                gameView.getCurrentTurn().setText("Current turn is Red");
            }
        } 
        else {
            JOptionPane.showMessageDialog(gameView, "Please select S or O before making a move", "Invalid Move", JOptionPane.WARNING_MESSAGE);
        }
    }

    protected void updateScore(ArrayList<Integer> pointsList, Color color) {
        if (pointsList != null && pointsList.size() > 0) {
            int points = pointsList.get(0);
            Color purple = new Color(128, 0, 128);

            if (color.equals(Color.blue)) {
                gameView.setBlueTotal(gameView.getBlueTotal() + points);
                gameView.getBluePoints().setText(Integer.toString(gameView.getBlueTotal()));
            } 
            else {
                gameView.setRedTotal(gameView.getRedTotal() + points);
                gameView.getRedPoints().setText(Integer.toString(gameView.getRedTotal()));
            }

            for (int i = 1; i < pointsList.size(); i++) {
                int index = pointsList.get(i);
                if ((color.equals(Color.blue) && (colorList.get(index).equals(Color.red) || colorList.get(index).equals(purple))) ||
                    (color.equals(Color.red) && (colorList.get(index).equals(Color.blue) || colorList.get(index).equals(purple)))) {
                    buttons[index].setBackground(purple);
                    colorList.set(index, purple);
                } 
                else {
                    buttons[index].setBackground(color);
                    colorList.set(index, color);
                }
            }
        }
    }
    
    protected abstract boolean checkGameEnd();

	public JButton[] getButtons() {
		return buttons;
	};
}
