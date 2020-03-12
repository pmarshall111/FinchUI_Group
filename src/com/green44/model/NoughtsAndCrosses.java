package com.green44.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//made as part of year 1 Brunel CS assessment. There are 5 assessed methods where the arguments are fixed.
//added on at the bottom are separate functions that allow you to play against the computer (which moves randomly)
public class NoughtsAndCrosses {
    private static char NOUGHTS = 'O';
    private static char CROSSES = 'X';
    private static char SPACE = '.';

    //assessed
    public static boolean validateSquare(char c) {
        c = Character.toUpperCase(c);
        return (c == NOUGHTS || c == CROSSES || c == SPACE);
    }

    //assessed
    public static int countNumCharOnBoard(char[][] board, char c) {
        int count = 0;
        for (int row = 0; row<board.length; row++) {
            for (int col = 0; col<board[row].length; col++) {
                char charOnBoard = Character.toUpperCase(board[row][col]);
                if (c == charOnBoard) {
                    count++;
                }
            }
        }
        return count;
    }

    //assessed
    public static boolean validateBoard(char[][] board) {
        //checks each square if valid character.
        //counts if noughts and crosses are within 1 of the other
        int noughts = 0, crosses = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                char charOnBoard = Character.toUpperCase(board[row][col]);
                if (!validateSquare(charOnBoard)) {
                    return false;
                } else if (charOnBoard == NOUGHTS) {
                    noughts++;
                } else if (charOnBoard == CROSSES) {
                    crosses++;
                }
            }
        }
        int diff = Math.abs(noughts - crosses);
        return diff <= 1;
    }

    //assessed
    public static char detectWin(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (winVertical(board, row, col) || winHorizontal(board, row, col) ||
                        winDiagUp(board, row, col) || winDiagDown(board, row, col)) {
                    return Character.toUpperCase(board[row][col]);
                }
            }
        }
        return SPACE; //indicates nobody has won.
    }

    private static boolean winVertical(char[][] board, int currRow, int currCol) {
        try {
            char above = board[currRow - 1][currCol], current = board[currRow][currCol], below = board[currRow + 1][currCol];
            if (above == current && current == below) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        return false;
    }

    private static boolean winHorizontal(char[][] board, int currRow, int currCol) {
        try {
            char left = board[currRow][currCol-1], current = board[currRow][currCol], right = board[currRow][currCol+1];
            if (left == current && current == right) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        return false;
    }

    private static boolean winDiagUp(char[][] board, int currRow, int currCol) {
        try {
            char belowLeft = board[currRow + 1][currCol-1], current = board[currRow][currCol], aboveRight = board[currRow - 1][currCol+1];
            if (belowLeft == current && current == aboveRight) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        return false;
    }

    private static boolean winDiagDown(char[][] board, int currRow, int currCol) {
        try {
            char aboveLeft = board[currRow - 1][currCol-1], current = board[currRow][currCol], belowRight = board[currRow + 1][currCol+1];
            if (aboveLeft == current && current == belowRight) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        return false;
    }

    private static char whosTurn(char[][] board) {
        int noughts = countNumCharOnBoard(board, NOUGHTS);
        int crosses = countNumCharOnBoard(board, CROSSES);
        if (noughts >= crosses) {
            return CROSSES;
        } else {
            return NOUGHTS;
        }
    }

    //assessed
    public static char[][] makeRandomMove(char[][] board) {
        ArrayList<Coordinates> emptySquares = findEmptySquares(board);
        //If no possible moves, we just return the board
        if (emptySquares.isEmpty()) {
            return board;
        } else {
            char newMove = whosTurn(board);
            char[][] newBoard = makeDeepCopy(board);
            int randomIndex = (int) Math.floor(Math.random()*emptySquares.size());
            Coordinates randCoords = emptySquares.get(randomIndex);
            newBoard[randCoords.getRow()][randCoords.getCol()] = newMove;
            return newBoard;
        }
    }

    private static ArrayList<Coordinates> findEmptySquares(char[][] board) {
        ArrayList<Coordinates> emptySquares = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == SPACE) {
                    emptySquares.add(new Coordinates(row, col));
                }
            }
        }
        return emptySquares;
    }

    private static char[][] makeDeepCopy (char[][] board) {
        //makes deep copy only for 2D arrays.
        char[][] newBoard = board.clone();
        for (int row = 0; row<board.length; row++) {
            newBoard[row] = board[row].clone();
        }
        return newBoard;
    }

    private static class Coordinates {
        private int row;
        private int col;

        public Coordinates(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }


    //everything from here downwards is just so I can play. Not assessed.
    
    //NOTES FOR ADDING TO GUI
    //needs to be initialised in new thread due to javafx gui needing own thread
    //has a new String field which the gui will manually update whenever user adds new input
    //will sleep whenever needs user input. When user does input, gui will send an interrupt to wake it up and thread will resume
    //no scanner class. Instead we sleep and get woken up by interrupt.
    private static char[][] playingBoard;
    private static boolean isFirst;
    private static char userCharacter;
    private static String userInput;
    private static long LONG_TIME = Long.MAX_VALUE;

    public static void main(String[] args) {
        play();
        userInput="";
    }
   

    public static void play() {
        isFirst = goesFirstOrSecond();
        playingBoard = new char[][]{{SPACE,SPACE,SPACE}, {SPACE,SPACE,SPACE}, {SPACE,SPACE,SPACE}};
        addMoves();
        printWinner();
        if (wantsToPlayAgain()) {
            play();
        } else {
            System.out.println("Bye");
        }
    }

    private static void printBoard(char[][] board) {
        int moves = countNumCharOnBoard(board, CROSSES) + countNumCharOnBoard(board, NOUGHTS);
        if (detectWin(board) != SPACE) {
            System.out.println("----------- Moves: " + moves + ". Game won by: " + detectWin(board) + " -------------");
        } else {
            System.out.println("----------- Moves: " + moves + ". Next turn: " + whosTurn(board) + " -------------");
        }
        for (char[] row: board) {
            System.out.println("| " + row[0] + " | " + row[1] + " | " + row[2] + " |");
        }
    }

    private static boolean goesFirstOrSecond() {
    	userInput="";
        System.out.println("Do you want to go first or second?");
        ArrayList<String> firstAnswers = new ArrayList<>(Arrays.asList("first", "1", "f", "1st"));
        ArrayList<String> secondAnswers = new ArrayList<>(Arrays.asList("second", "2", "s", "2nd"));
        waitForUserInput();
        while (!firstAnswers.contains(userInput) && !secondAnswers.contains(userInput)) {
        	System.out.println("That is not a valid answer. Please try again.");
        	waitForUserInput();
        }
        return firstAnswers.contains(userInput);
    }
    
    private static void waitForUserInput() {
    	try {
			Thread.sleep(LONG_TIME);
		} catch (InterruptedException e) {
			//this wakes up the thread. interrupt method should be called after updating the user input.
		}
    }
    
    public static void updateUserInput(String s) {
    	userInput = s;
    	System.out.println("You entered " + s);
    }

    private static void printWinner() {
        char winner = detectWin(playingBoard);
        if (winner == SPACE) {
            printBoard(playingBoard);
            System.out.println("Game was drawn, bor-ing!");
        } else if (userCharacter == winner) {
            System.out.println("You won, Congratulations!");
        } else {
            printBoard(playingBoard);
            System.out.println("The computer won. Unlucky.");
        }
    }

    private static boolean wantsToPlayAgain() {
        System.out.println("Do you want to play again?");
        ArrayList<String> yesAnswers = new ArrayList<>(Arrays.asList("yes", "of course", "why not", "definitely", "y"));
        ArrayList<String> noAnswers = new ArrayList<>(Arrays.asList("no", "nope", "please don't make me", "n"));
        waitForUserInput();
        while (!yesAnswers.contains(userInput) && !noAnswers.contains(userInput)) {
        	System.out.println("That is not a valid answer. Please try again.");
        	waitForUserInput();
        }
        return yesAnswers.contains(userInput);
    }

    private static void addMoves() {
        if (!isFirst) {
            playingBoard = makeComputerMove(playingBoard);
            userCharacter = whosTurn(playingBoard);
        }
        userCharacter = whosTurn(playingBoard);
        while (!findEmptySquares(playingBoard).isEmpty() && detectWin(playingBoard) == SPACE) {
            printBoard(playingBoard);
            Coordinates move = askForMove(playingBoard);
            playingBoard[move.getRow()][move.getCol()] = whosTurn(playingBoard);
            printBoard(playingBoard);
            playingBoard = makeComputerMove(playingBoard);
        }
    }

    private static Coordinates askForMove(char[][] board) {
    	System.out.println("\nWhich row would you like to go in? (1, 2 or 3)");
    	waitForUserInput();
    	while (!userInput.matches("^[123]$")) {
    		System.out.println("That is not a valid row. Please try again.");
    		waitForUserInput();
    	}
    	int row = Integer.parseInt(userInput)-1;
    	System.out.println("Which column would you like to go in? (1, 2 or 3)");
    	waitForUserInput();
    	while (!userInput.matches("^[123]$")) {
    		System.out.println("That is not a valid column. Please try again.");
    		waitForUserInput();
    	}
    	int col = Integer.parseInt(userInput)-1;
    	if (board[row][col] == SPACE) {
    		return new Coordinates(row, col);
    	} else {
    		System.out.println("Sorry, that area currently has a " + board[row][col] + " in. Please select another.");
        	return askForMove(board);
    	}
    }

    private static char[][] makeComputerMove(char[][] board) {
        try {
            if (findEmptySquares(board).isEmpty() || detectWin(board) != SPACE) {
                return board;
            } else {
                Thread.sleep(1000);
                System.out.println("\nComputer making move...");
                Thread.sleep(1000);
                return makeRandomMove(board);
            }
        } catch (InterruptedException e) {return board;}
    }

}
