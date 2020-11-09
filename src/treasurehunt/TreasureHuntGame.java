/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasurehunt;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author rael
 */
public class TreasureHuntGame {

    private int turn;
    private int[][] board;
    private Player player;
    private List<String> treasurePosition;
    private int piratePoints;
    private int digPoints;

    // setting and getting the player name to be used in this class

    public void setPlayerName(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    // setting and getting the treasurePosition to be used in this class

    public List<String> getTreasurePosition() {
        return this.treasurePosition;
    }

    public void setTreasurePosition(List<String> treasurePosition) {
        this.treasurePosition = treasurePosition;
    }

    // setting and getting piratPoints to be used in this class

    public int getPiratPoints() {
        return this.piratePoints;
    }

    public void setPiratePoints(int piratePoints) {
        this.piratePoints = piratePoints;
    }
// setting and getting digPoints to be used in this class

    public int getDigPoints() {
        return this.digPoints;
    }

    public void setDigPoints(int digPoints) {
        this.digPoints = digPoints;
    }

    public TreasureHuntGame() {
        //start at turn 1
        turn = 1;          //this is just going to print an empty board until we actually start playing!
        //set up board
        setUpBoard();
    }

    public void setUpBoard() {
// creating a 10x10 board using 2D array
        int rows;
        int columns;

        board = new int[10][10];
        // rows
        for (rows = 0; rows < board.length; rows++) {
            // columns
            for (columns = 0; columns < board[rows].length; columns++) {

                board[rows][columns] = -1;

            }
        }
    }

    public void printHeader() {

        System.out.println("GAMEBOARD \n");
    }

    public void turnCounter() {
        //define hows turn is
        System.out.println("Argh..Pirate " + this.player.getName() + " …it be your turn to dig for me treasure." + "\n");
        //move to next turn
        this.turn++;
    }

    public void printBoard() {
//go through the rows and columns
        int rows;
        int columns;
        //same count loop as board set up!
        for (rows = 0; rows < board.length; rows++) {
            //rows. the int counting is adding the referencing numbers on the first row 
            int counting = rows + 1;
            if (counting == 10) {
                System.out.print(counting + "|");
            } else {
                System.out.print("0" + counting + "|");
            }
            for (columns = 0; columns < board[rows].length; columns++) {
                //columns
                //the first time this is run, it will be -1 in all spaces
                if (board[rows][columns] == -1) {

                    System.out.print("_|");

                } else {
                    System.out.print("O|");// if the value is not -1 means that the square has been used. 
                    // and it will be shown to the user as a hole represented by the letter "O"
                }

            }

            System.out.println("");
        }
    }

    public boolean gameOver() {
// defining when the game is over. it is over when there is no treasure to be found
        return (treasurePosition.size() == 0);

    }

    public int getUserSquare() {
        // using a Map to convert the column addres from numbers to letters
        Map<String, Integer> alphabet = new HashMap<>();
        alphabet.put("a", 0);
        alphabet.put("b", 1);
        alphabet.put("c", 2);
        alphabet.put("d", 3);
        alphabet.put("e", 4);
        alphabet.put("f", 5);
        alphabet.put("g", 6);
        alphabet.put("h", 7);
        alphabet.put("i", 8);
        alphabet.put("j", 9);

        // row in the board
        int userRow = 0;
// int created to collect the user row. it has been created to avoid validation in the loop condition.
        // the value of this int will be transfered to the userRow.
        int userInput = 0;
        // user column now is string
        String userColumn = new String();
        Scanner kBoard = new Scanner(System.in);
        // boolean to check if the square has been used
        boolean isPositionUsed = false;
        // loop to check if the square given by the user has been used
        do {
            // using the method where the userRow is being collected
            userRow = userRow(userRow, userInput, kBoard);
            // using the method where the userColumn is being collected
            userColumn = userColumn(userColumn, kBoard, alphabet);
            // if the square has the value of 1 it has been used and the user will be asked to pick a new one
            if ((board[userRow][alphabet.get(userColumn)] == 1)) {
                isPositionUsed = true;
                System.out.println("That square has been used. Pick again \n");
            } else { // giving te value of 1 to the used square
                isPositionUsed = false;
                board[userRow][alphabet.get(userColumn)] = 1;
            }// condition to stop the loop. the used square cannot be used again. 
        } while (isPositionUsed);
        // string to hold the user square
        String position = userColumn + (userRow + 1);

        // taking one digPoint from the player each time he plays
        int digPoints = this.player.getDigPoints() - 1;
        // adding 20 piratePoints when the player finds a treasure
        if (this.treasurePosition.contains(position)) {
            // adding 20 piratPoints when the player dig a treasure
            int piratePoints = this.player.getPiratPoints() + 20;
            System.out.println("“Yo-ho-ho and a bottle of rum. I found me some pieces of eight \n");
            // setting the piratePoints
            this.player.setPiratePoints(piratePoints);
            // even when the player finds a piratePoint he still losing one digPoint and it is setted here
            this.player.setDigPoints(digPoints);
            // removes the treasure after it being found
            this.treasurePosition.remove(position);
        } else {
            //setting the digPoints after playing when no treasure is found
            this.player.setDigPoints(digPoints);
            System.out.println("Walk the plank! There be no treasure here! \n");
        }
        System.out.println(this.player.getDigPoints());
        return this.player.getDigPoints();
    }

    // method to get the user row

    public int userRow(int userRow, int userInput, Scanner myScanner) {
        // the loop will continue until the userInput enter a valid value
        while (userInput == 0) {
            try {
                System.out.println("Please enter row");
                // when the user enter a row the value will be stored in userInput
                userInput = myScanner.nextInt();
                //the value of user input will be transfered to user row.
                // userInput -1because the 0 counts and the user does not know it
                userRow = userInput - 1;
                // when the user enter a number that is not beteween 1-10 the value will be 0.
                //it will get into the loop condition and the user will be asked to enter a row number again.
                if (userInput > 10 || userInput < 1) {
                    userInput = 0;
                    throw new InputMismatchException();
                } // in case of the user do not enter a int value it
                //will be catch in this exception and he will get a invalid mensage and will be asked to try agai. 
            } catch (InputMismatchException ex) {
                myScanner.nextLine();
                System.out.println("invalid data");
            }
        }
        return userRow;
    }

    // method to get the user column

    public String userColumn(String userColumn, Scanner myScanner, Map<String, Integer> alphabet) {

        do {

            System.out.println("Please enter column");
            userColumn = myScanner.next();
            // checking if the user is entering a value which correspond to a column value. if it is no he will be asked to try again
        } while (!alphabet.containsKey(userColumn));
        return userColumn;
    }

    public int getTurn() {
        // return the turn to the next player
        return turn;

    }
   

}
