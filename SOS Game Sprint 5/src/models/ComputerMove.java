package models;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

import views.GameView;

public class ComputerMove extends Move {
    public ComputerMove(GameBoard gameBoard, BoardData virtualBoard) {
        super(gameBoard, virtualBoard);
    }

    @Override
    public boolean execute(int buttonIndex, Color playerColor, boolean isS, boolean write) {
    	JButton[] buttons = gameBoard.getButtons();
    	
    	//Get all of the available moves on the board
        ArrayList<Integer> availableMoves = new ArrayList<>();
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isEnabled()) {
                availableMoves.add(i);
            }
        }
        
        // Find the best move on the board
        if (!availableMoves.isEmpty()) {
        	ArrayList<Integer> bestMove = getBestMove(availableMoves);
        	
        	//Check if no points are possible, if so then set the button to be pressed to a random number
        	if(bestMove.get(0).equals(-1)) {
                int randomIndex = (int) (Math.random() * availableMoves.size());
                buttonIndex = availableMoves.get(randomIndex);
                // Randomly choose S or O
                isS = Math.random() < 0.5; 
        	}
        	else {
        		buttonIndex = bestMove.get(0);
        		if(bestMove.get(1) == 1) {
        			isS = true;
        		}
        		else {
        			isS = false;
        		}
        	}

        }
        //Set the button to clicked and change the text
        JButton clickedButton = buttons[buttonIndex];
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
    
    //Function returns an arrayList where the first value is the index of the best move and the second value represents S or O
    //If there is no scoring move it should return [-1,0] so a random move can be done instead
    public ArrayList<Integer> getBestMove(ArrayList<Integer> availableMoves) {
    	ArrayList<Integer> bestMove = new ArrayList<Integer>();
    	bestMove.add(-1);
    	bestMove.add(0);
        int maxPoints = 0;

        for (int i = 0; i < availableMoves.size();i++) {
        	
            // Check S move (1 represents S in the arrayList)
            int sPoints = checkSMove(availableMoves.get(i));
            if (sPoints > maxPoints) {
                maxPoints = sPoints;
                bestMove.set(0, availableMoves.get(i));
                bestMove.set(1, 1);
            }

            // Check O move (0 represents O in the arrayList)
            int oPoints = checkOMove(availableMoves.get(i));
            if (oPoints > maxPoints) {
                maxPoints = oPoints;
                bestMove.set(0, availableMoves.get(i));
                bestMove.set(1, 0); 
            }
        }
    	return bestMove;
    }
    
    public Integer checkSMove(Integer place) {
    	Integer first, second, boardSize, points = 0;
    	boardSize = virtualBoard.getBoardSize();
    	ArrayList<ArrayList<String>> testBoard = virtualBoard.getGameBoard();
		first = place/boardSize;
		second = place%boardSize;
		
		//Check the 8 different ways SOS could be created via a S
				if(first-2 >= 0 && second-2 >=0 && testBoard.get(first-2).get(second-2).equals("S") && testBoard.get(first-1).get(second-1).equals("O")) {
					points ++;
				}
				if(second-2 >= 0 && testBoard.get(first).get(second-2).equals("S") && testBoard.get(first).get(second-1).equals("O")) {
				    points ++;
				}
				if(first+2 < boardSize && second-2 >=0 && testBoard.get(first+2).get(second-2).equals("S") && testBoard.get(first+1).get(second-1).equals("O")) {
				    points ++;
				}
				if(first+2 < boardSize && testBoard.get(first+2).get(second).equals("S") && testBoard.get(first+1).get(second).equals("O")) {
				    points ++;
				}
				if(first+2 < boardSize && second+2 < boardSize && testBoard.get(first+2).get(second+2).equals("S") && testBoard.get(first+1).get(second+1).equals("O")) {
				    points ++;
				}
				if(second+2 < boardSize && testBoard.get(first).get(second+2).equals("S") && testBoard.get(first).get(second+1).equals("O")) {
				    points ++;
				}
				if(first-2 >= 0 && second+2 < boardSize && testBoard.get(first-2).get(second+2).equals("S") && testBoard.get(first-1).get(second+1).equals("O")) {
				    points ++;
				}
				if(first-2 >= 0 && testBoard.get(first-2).get(second).equals("S") && testBoard.get(first-1).get(second).equals("O")) {
				    points ++;
				}
		return points;
    }
    
    public Integer checkOMove(Integer place) {
    	Integer first, second, boardSize, points = 0;
    	boardSize = virtualBoard.getBoardSize();
    	ArrayList<ArrayList<String>> testBoard = virtualBoard.getGameBoard();
		first = place/boardSize;
		second = place%boardSize;
		
		//Checks the 4 different ways a SOS could be created via a O
				if(first-1 >= 0 && second-1 >= 0 && first+1 < boardSize && second+1 < boardSize && testBoard.get(first-1).get(second-1).equals("S") && testBoard.get(first+1).get(second+1).equals("S")) {
					points ++;
				}
				if(first-1 >= 0 && second+1 < boardSize && first+1 < boardSize && second-1 >= 0 && testBoard.get(first-1).get(second+1).equals("S") && testBoard.get(first+1).get(second-1).equals("S")) {
					points ++;
				}
				if(first-1 >= 0 && first+1 < boardSize && testBoard.get(first-1).get(second).equals("S") && testBoard.get(first+1).get(second).equals("S")) {
				    points ++;
				}
				if(second-1 >= 0 && second+1 < boardSize && testBoard.get(first).get(second-1).equals("S") && testBoard.get(first).get(second+1).equals("S")) {
				    points ++;
				}
		
		return points;
    }
}
