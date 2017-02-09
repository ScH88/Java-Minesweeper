package com.myPackage.minesweeper;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

//
public class startGame {
		
	//MAIN CLASS - Is first function to be called upon program running
    public static void main(String args[])  {
        //Print the following String to the console
    	System.out.println("Starting MineSweeper Game...");
    	//Create a new Runnable to invoke later
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    //Declare a local File variable with the "JavaMineSweeperSaveGame.txt" name
                    File newFile = new File ("JavaMineSweeperSaveGame.txt"); 
                    //Check if the file exists 
                    if (newFile.exists())  {
                        //If the file exists, instantiate MineSweeperGame using an alternate constructor for loading game
                    	 MineSweeperGame newGame = new MineSweeperGame();
                    	//loadGame();
                    //If the file does not exist
                    } else if (!newFile.exists()) {
                        //Try statement
                    	try {
                    		//Call the Dialog class' newDifficulty method, which returns an int value which defines the noOfCells variable
                            int noOfCells = Dialog.newDifficulty();
                            //Once the Dialog has an option selected, create a new MineSweeperGame instance using the noOfCells variable as the parameter
                            MineSweeperGame newGame = new MineSweeperGame(noOfCells);
                        //If an IOException is caught
                    	}  catch (IOException e)  {
                            //Print the following string to console
                            System.out.println("IOException caught");
                        }
                    }
                }
            }
        );
    }          
}
