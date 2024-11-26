package models;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import views.GameView;

public abstract class Move {
    protected GameBoard gameBoard;
    protected BoardData virtualBoard;

    //Initializes the move and sets the current game board and virtual board that will be used in the move logic
    public Move(GameBoard gameBoard, BoardData virtualBoard) {
        this.gameBoard = gameBoard;
        this.virtualBoard = virtualBoard;
    }
    
    public void saveMove(int buttonIndex, Color playerColor, boolean isS) {
        try (PrintWriter out = new PrintWriter(new FileWriter("game_moves.txt", true))) {
            String moveInfo = String.format("%d,%s,%b", buttonIndex, playerColor, isS);
            out.println(moveInfo);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    //Empty function to be used in the child classes
    public abstract boolean execute(int buttonIndex, Color playerColor, boolean isS, boolean write);
}
