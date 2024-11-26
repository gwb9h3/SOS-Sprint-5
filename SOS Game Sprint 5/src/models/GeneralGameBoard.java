package models;

import javax.swing.JOptionPane;

import views.*;

public class GeneralGameBoard extends GameBoard {
    public GeneralGameBoard(GameView gameView, BoardData virtualBoard) {
        super(gameView, virtualBoard);
    }

    @Override
    protected boolean checkGameEnd() {
        if (checkGeneralGame(colorList.size())) {
            // Game has ended
        	return true;
        }
        return false;
    }

    private boolean checkGeneralGame(Integer boardLength) {
        for (int i = 0; i < boardLength; i++) {
            if (buttons[i].isEnabled()) {
                return false;
            }
        }

        if (gameView.getBlueTotal() > gameView.getRedTotal()) {
            JOptionPane.showMessageDialog(gameView, "The blue player has won");
        } 
        else if (gameView.getRedTotal() > gameView.getBlueTotal()) {
            JOptionPane.showMessageDialog(gameView, "The red player has won");
        } 
        else {
            JOptionPane.showMessageDialog(gameView, "The game is a tie");
        }

        return true;
    }
}
