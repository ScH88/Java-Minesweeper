package com.myPackage.minesweeper;

import javax.swing.*;
import java.io.*;

public class Dialog
{
	//CONSTRUCTOR
    public Dialog() {
    }
    
    public static int newDifficulty() {
        //The number of cells, with default value of 0
        int cells = 0;
        //An array of String objects for use in the oncoming input dialog
        Object[] possibleValues = {"Easy", "Medium", "Hard"};

        //Object for storing a JOptionPane, storing the possibleValues array as a drop-down menu
        Object select = JOptionPane.showInputDialog(null, 
        "Choose Difficulty", "Input",
        JOptionPane.PLAIN_MESSAGE, null,
        possibleValues, possibleValues[0]);
        if (select != null) {
        	//Difficulty determines the number of cells in the game
            //If the "select" JOptionPane's drop-down menu value equals "Easy"
            if (select.equals("Easy")) {
            	//Set the number of cells as 9
                cells = 9;
              //If the "select" JOptionPane's drop-down menu value equals "Medium"
            }   else if (select.equals("Medium")) {
                cells = 16;
              //If the "select" JOptionPane's drop-down menu value equals "Hard"
            }  else if (select.equals("Hard")) {
                cells = 28;
            }
        } else {
        	System.exit(0);
        }
        //Returns the number of cells
        return cells;
    }
    
    public static int gameOver() {
        //The return number (1, 2 or 3) which will determine the function that the mineSweeperGame instance will carry out
        int returnState = 0;
        //File object for naming the file as stated by the file path
        File file = new File("JavaMineSweeperSaveGame.txt");
        //Delete the file defined before
        file.delete();
        //Creates an array of String Object options
        Object[] options = {"Try Again",
        "Change Difficulty",
        "Quit Game"};
        //Create a JOptionPane, storing the array value as defined before
        //The "Try Again" is a "Yes" option, "Change Difficulty" is the "No" option and "Quit Game" is the "cancel" option
        int n = JOptionPane.showOptionDialog(null, "You are dead", "Game Over",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
        options[2]);
        //Depending on which button the user presses, the returnState variable will be one of the following
        //If the value of the JOptionPane equals the yes option
        if (n == JOptionPane.YES_OPTION)  {   
            //Return state value for retrying
            returnState = 1;
          //If the value of the JOptionPane equals the no option
        } else if (n == JOptionPane.NO_OPTION) {
            //Return state value for new game
            returnState = 2;
        //If the value of the JOptionPane equals the cancel option
        } else if (n == JOptionPane.CANCEL_OPTION)   {
            //Return state value for shutting down the program
            returnState = 3;
        } else if (n == JOptionPane.CLOSED_OPTION) {
        	//Return state value for the closed option
        	returnState = 4;
        }
        //Return the returnState variable for the mineSweeperGame instance's gameOver() method
        return returnState;
    }
    
    public static int gameComplete() {
        //The return number (1, 2 or 3) which will determine the function that the mineSweeperGame instance will carry out
        int returnState = 0;
        //An array of String Object values
        Object[] options = {"Try Again",
        "Change Difficulty",
        "Quit Game"};
        //JOptionPane for the array of options Strings
        //"Try Again" is the "yes" option, "Change Difficulty" is the "no" option and "Quit Game" is the "cancel" options
        int n = JOptionPane.showOptionDialog(null,
        "You finally cleared the map of all empty spaces!",
        "Game Completed",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[2]);
        //Depending on which button the user presses, the returnState variable will become one of the following
        //If the "Yes" option is selected in the JOptionPane
        if (n == JOptionPane.YES_OPTION)  {   
            //Return state value for trying again
            returnState = 1;
          //If the "No" option is selected in the JOptionPane
        }  else if (n == JOptionPane.NO_OPTION)  {
            //Return state value for changing difficulty
            returnState = 2;
          //If the "Cancel" option is selected in the JOptionPane
        }  else if (n == JOptionPane.CANCEL_OPTION)  {
            //Return state value for quitting game
            returnState = 3;
        //If the user closes the dialog window
        } else if (n == JOptionPane.CLOSED_OPTION) {
        	//Return state value for the closed option
        	returnState = 4;
        }
        //send the returnState variable back to the mineSweeperGame instance's gameOver() method
        return returnState;
    }
    
    public static int quitGame()  {
        //The return number (1, 2 or 3) which will determine the function that the mineSweeperGame instance will carry out
        int returnState = 0;
        //An array of String Objects
        Object[] options = {"Yes",
        "No", "Cancel"};
        //Instantiate a new JOptionPane option dialog
        //The "Yes" in the array is the "yes" option, the "No" is the "No" option and the "Go Back" is the "cancel" option
        int n = JOptionPane.showOptionDialog(null,
        "Do you want to save your game before quitting?",
        "Quit Game",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[2]);
        //Depending on which button the user presses, the returnState variable will become one of the following
        //If the "yes" option is selected in the JOptionPane
        if (n == JOptionPane.YES_OPTION)  {   
            //Return state value for saving game details onto a file
            returnState = 1;
        //If the "no" option is selected in the JOptionPane
        }  else if (n == JOptionPane.NO_OPTION)  {
            //Return state for quitting the game without saving
            returnState = 2;
            //If the "cancel" option is selected in the JOptionPane
        }  else if (n == JOptionPane.CANCEL_OPTION)  {
            //Return state value for cancelling the window
            returnState = 3;
        }
        //Returns the returnState variable to the MineSweeperGame instance's quitGame() method
        return returnState;
    }
}