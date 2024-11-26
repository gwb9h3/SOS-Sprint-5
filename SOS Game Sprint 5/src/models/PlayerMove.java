package models;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

import views.GameView;

public class PlayerMove extends Move {
    public PlayerMove(GameBoard gameBoard, BoardData virtualBoard) {
        super(gameBoard, virtualBoard);
    }

    @Override
    public boolean execute(int buttonIndex, Color playerColor, boolean isS, boolean write) {
        JButton clickedButton = gameBoard.getButtons()[buttonIndex];
        clickedButton.setEnabled(false);
        if (isS) {
            clickedButton.setText("S");
        } 
        else {
            clickedButton.setText("O");
        }
        ArrayList<Integer> pointsList;
        if (isS) {
            pointsList = virtualBoard.makeSMove(buttonIndex);
        } 
        else {
            pointsList = virtualBoard.makeOMove(buttonIndex);
        }
        
        //Will write to a text file if the player has it selected
        if(write) {
        saveMove(buttonIndex,playerColor,isS);
        }
        gameBoard.updateScore(pointsList, playerColor);
        return gameBoard.checkGameEnd();
    }
}
