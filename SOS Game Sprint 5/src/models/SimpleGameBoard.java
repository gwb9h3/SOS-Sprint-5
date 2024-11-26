package models;

import javax.swing.*;

import views.*;

public class SimpleGameBoard extends GameBoard {
    public SimpleGameBoard(GameView gameView, BoardData virtualBoard) {
        super(gameView, virtualBoard);
    }

    @Override
    protected boolean checkGameEnd() {
        if (checkSimpleGame(colorList.size())) {
            // Game has ended
        	return true;
        }
        return false;
    }

    private boolean checkSimpleGame(Integer boardLength) {
        if (gameView.getBlueTotal() > 0 || gameView.getRedTotal() > 0) {
            if (gameView.getBlueTotal() > 0) {
                JOptionPane.showMessageDialog(gameView, "The blue player has won");
            } 
            else {
                JOptionPane.showMessageDialog(gameView, "The red player has won");
            }
            disableAllButtons();
            return true;
        }

        for (int i = 0; i < boardLength; i++) {
            if (buttons[i].isEnabled()) {
                return false;
            }
        }

        JOptionPane.showMessageDialog(gameView, "The game is a tie");
        return true;
    }

    private void disableAllButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }
}
