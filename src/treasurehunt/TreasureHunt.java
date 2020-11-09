/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasurehunt;

import java.util.*;
import java.io.*;

/**
 *
 * @author rael
 */
public class TreasureHunt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{

        Scanner kBoard = new Scanner(System.in);
        // string created to hold the positions that has been read from the file
        String[] treasurePosition = readFromFile().split(";");
        //string to hold the players details
        Player[] players = new Player[4];
        int[] digPoints = {4, 5, 6, 7};
        int amountOfPlayers = 0;
        // using the method created for defining the amount of players
        amountOfPlayers = loadAmountPlayers(amountOfPlayers, kBoard);
        // using the method created to collect the user details and assign the points
        loadPlayers(kBoard, players, amountOfPlayers, digPoints);
        // game rules will be shown to the players
        System.out.println("Game Rules:");
        System.out.println("");
        System.out.println("* Each player may only select a square that exists on the “Map”; ");
        System.out.println("* A square can only be selected once (i.e. you cannot ‘dig’ in the same place more than once); ");
        System.out.println("* If the player finds some treasure, will be added 20 “Pirate Points” to their total and mark; ");
        System.out.println("* If the player does not find any treasure, then they do not score any “Pirate Points”;");
        System.out.println("* In all cases, will be subtract 1 from the Player’s “Dig Points”; ");
        System.out.println("* If a Player has no “Dig Points” left, then they miss their turn; ");
        System.out.println("* If all the treasure is found, then the game is over;");
        System.out.println("* If no player has any “Dig Points” left, then the game is over. ");
        System.out.println("");

        // using the method which display the board
        showBoard(amountOfPlayers, players, new ArrayList<String>(Arrays.asList(treasurePosition)), amountOfPlayers);

    }
// the method to display the board.

    public static void showBoard(int amountOfPlayers, Player[] players, List<String> treasurePosition, int amtOfPlayers) {
        // importing the methods from the TreasureHuntGame class
        TreasureHuntGame myGame = new TreasureHuntGame();
        //setting the treasurePosition into the board
        myGame.setTreasurePosition(treasurePosition);
        int j = 0;
        // this loop will display the board on each turn until the game is over
        do {
            for (; j <= amtOfPlayers - 1; j++) {
                // methods defined in TreasureHuntGame class
                myGame.setPlayerName(players[j]);
                myGame.printHeader();
                myGame.turnCounter();
                // printing the column names to make easer for the player to know where each column is
                System.out.print("   A " + "B " + "C " + "D " + "E " + "F " + "G " + "H " + "I " + "J \n");
                myGame.printBoard();
                // defines that the player cannot play when he is out of digPoints
                int userDigpoints = myGame.getUserSquare();
                if (userDigpoints <= 0) {
                    System.out.println("Argh,  Captain, me shovel has broken! \n");
                    amtOfPlayers--;
                }
                    
                }
            
            j = 0;
            // defines when the game finishs
        } while ((!myGame.gameOver()) && amtOfPlayers > 0);
        // defining the winner of the game
        Player winner = players[0];
        boolean isDraw = true;

        for (Player p : players) {    // the winner will be who has more piratePoints
            if (p != null && p.getPiratPoints() > winner.getPiratPoints()) {
                winner = p;
                //if they have the same amount of piratePoints the winner is who has more DigPoints left
            } else if (p != null && p.getPiratPoints() == winner.getPiratPoints() && p.getDigPoints() > winner.getDigPoints()) {
                winner = p;
                // if they have the same amount of piratePoints and digPoints it is a draw
            } else if (p != null && p.getPiratPoints() == winner.getPiratPoints() && p.getDigPoints() == winner.getDigPoints()) {
                isDraw = false;
            }
        }// if it is not a draw the winner will be anouced
        if (!isDraw) {
            System.out.println("Shiver me Timbers, me hearties, sure hasn’t " + winner.getName() + " won the game. Keelhaul the rest of them!");
            // if it is a draw it will be anounced 
        } else {
            System.out.println("Shiver me Timbers, me hearties, sure hasn’t " + players[0].getName() + " won the game. Keelhaul the rest of them!");
        }
    }

    //method where the user details are being collected and the points are being assigned
    public static void loadPlayers(Scanner kBoard, Player[] players, int qtdPlayers, int[] digPoints) {
        // variable to control the loop
        int i = 0;
        // string that hold the players details
        Player player;
        Random random = new Random();

        // loop for collecting the user details
        do {
            kBoard.nextLine();
            player = new Player();
            do {// getting the name and surname from the user. 
                System.out.println("Please enter yor name and surname");
                player.setName(kBoard.nextLine());
                // if the user do not enter name and surname he will be asked to do it
                if (player.getName().indexOf(" ") == -1) {
                    System.out.println("you must enter name and surname \n");
                }// condition to validate the name
            } while (player.getName().indexOf(" ") == -1);

            try {
                System.out.println("Please enter your age");
                player.setAge(kBoard.nextInt());
                // controling players age. player must be over 12 to play the game
                if (player.getAge() < 12) {
                    System.out.println("invalid age \n");

                } else {
                    // creating a int for the digPoints that the user will receive
                    int playerDigPoints;

                    //the digPoints are selected in a random way and multiplaied by 5
                    playerDigPoints = digPoints[random.nextInt(digPoints.length)] * 5;
                    player.setDigPoints(playerDigPoints);
                    //setting the Pairate points of the player. each player receive 100 and it is deductet the digPoints from it.
                    player.setPiratePoints(100 - playerDigPoints);
                    //creating an array of players to hold their detais and points
                    players[i] = player;
                    i++;

                }       // to avoid the program to crash in case the user enter a invalid data
            } catch (InputMismatchException ex) {
                System.out.println("invalid data \n");

            }

            // defining when the loop will stop 
        } while (i < qtdPlayers || player.getAge() < 12);
    }

    // method to define the amount of people who will play the game
    public static int loadAmountPlayers(int qtdPlayers, Scanner kBoard) {
        while (qtdPlayers == 0) {

            try {
                System.out.println("please enter the number of players");
                qtdPlayers = kBoard.nextInt();
                // the quantity of players must be more than 2 and maximum of 4
                if ((qtdPlayers > 4) || (qtdPlayers < 2)) {
                    // if the quantity of players is more than 4 or less than 2 it will clean the user entry and ask again until a valid enter.
                    qtdPlayers = 0;
                    throw new InputMismatchException();
                }
                // avoid the program to crash in case the user enter a invalid data
            } catch (InputMismatchException ex) {
                kBoard.nextLine();
                System.out.println("invalid data \n");

            }

        }
        return qtdPlayers;
    }

    //method where the treasure position will be read from the file
    public static String readFromFile() throws FileNotFoundException, IOException {
        // creating a string builder to assign the positions
        StringBuilder treasurePosition = new StringBuilder();
       //getting an absolute and unique path from the root directories
        String path = new File(".").getCanonicalPath();
        // complementing the canonicalPhath with the file path in the program 
        path += ("\\build\\classes\\treasurehunt\\PiratePete.txt");
        // using bufferedReader to read the positions of the treasure in the file
        BufferedReader br = new BufferedReader(new FileReader(path));
       
        try {  
            // the string line will read line by line
            String line = br.readLine();
            // the reader will stop reading the lines when it is empty 
            while (line != null) {
                //assigning the position into the treasurePosition
                treasurePosition.append(line);
                line = br.readLine();
            }
            
            //saiving the positions
        } finally {
            br.close();

        } // converting the treasure position into String
        return treasurePosition.toString();

    }
}
