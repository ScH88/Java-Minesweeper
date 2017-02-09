package com.myPackage.minesweeper.Testing;

import junit.framework.Test;
import junit.framework.TestSuite;
import java.io.*;

import com.myPackage.minesweeper.Graphics.Tile;
import com.myPackage.minesweeper.MineSweeperGame;

public class MineSweeperGameTest extends junit.framework.TestCase {
	//
	
    public MineSweeperGameTest(String name)   {
    	//Call superclass constructor
    	super(name);
    }

    public static Test suite()    {
    	//Return an instance of TestSuite
    	return new TestSuite(MineSweeperGameTest.class);
    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    public void setUp()  {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    public void tearDown()   {
    }
    
    /**
     * Tests the setCells method
     */
    public void testSetCells() throws FileNotFoundException   {
        //Instantiate a new MineSweeperGame instance and give it's cells the value of 20
        MineSweeperGame mineSweeperGame = new MineSweeperGame(20);
        //Check if the getCells method returns 20
        assertEquals(mineSweeperGame.getCells(), 20);
    }
        
    /**
     * Tests the setFlagCount method
     */
    public void testSetFlagCount() throws FileNotFoundException   {
        //Instantiate a new MineSweeperGame instance and give it's cells the value of 9
        MineSweeperGame mineSweeperGame = new MineSweeperGame(9);
        //Check if the getFlags method returns 10 (because 9 cells means it's on Easy Mode
        assertEquals(mineSweeperGame.getFlags(), 10);
        
        //Instantiate a local tile object
        Tile tile = mineSweeperGame.getTile(0,0);
        //Flag the tile
        tile.setFlagged();
        //Reduce the flag count of the MineSweeperGame instance by sending it the flagged tile object
        mineSweeperGame.changeFlagCount(tile);
        //Check if the flag count is now 9
        assertEquals(mineSweeperGame.getFlags(), 9);
        //Unflag the tile object
        tile.setFlagged();
        //Bring the flag count back up by seinding it the unflagged tile object
        mineSweeperGame.changeFlagCount(tile);
        //Check if the flag count is back up to 10
        assertEquals(mineSweeperGame.getFlags(), 10);
                
        //Create a separate MineSweeperGame instance, giving it a value of 16 (medium difficulty) 
        MineSweeperGame mineSweeperGame2 = new MineSweeperGame(16);
        //Check if the separate instance has a total of 40 flags
        assertEquals(mineSweeperGame2.getFlags(), 40);
        
        //Create new MineSweeperGame instance and give it's cells the value of 28(hard difficulty)
        MineSweeperGame mineSweeperGame3 = new MineSweeperGame(28);
        //Check if the separate instance has a total of 99 flags
        assertEquals(mineSweeperGame3.getFlags(), 99);        
    }
    
    /**
     * Tests the deductScore method
     */
    public void testDeductScore() throws FileNotFoundException    {
        //Instantiate a new MineSweeperGame instance and give it's cells the value of 9
        MineSweeperGame mineSweeperGame = new MineSweeperGame(9);
        //Reduce the score by 4000, leaving 6000 points
        mineSweeperGame.deductScore(4000);
        //Check if the getScore method returns 6000
        assertEquals(mineSweeperGame.getScore(), 6000);
    }
    
    /**
     * Tests the detonateAllMines method
     */
    public void testDetonateAllMines() throws FileNotFoundException   {
        //Instantiate a new MineSweeperGame instance and give it's cells the value of 9
        MineSweeperGame mineSweeperGame = new MineSweeperGame(9);
        
        //For every tile in the grid width
        for (int i = 0; i < mineSweeperGame.getCells(); i++)    {
        	//For every tile in the grid height
            for (int j = 0; j < mineSweeperGame.getCells(); j++)    {
            	//If the current tile in the game instance is mined
                if (mineSweeperGame.getTile(i, j).getIsMine() == true)    {
                	//Check if the current tile's pressed status returns false
                    assertEquals(mineSweeperGame.getTile(i, j).getButtonPressed(), false);
                }
            }
        }
        
        //Detonate(press) all mines in the grid
        mineSweeperGame.detonateAllMines();
        
        //For every tile in the grid (width)
        for (int i = 0; i < mineSweeperGame.getCells(); i++)     {
        	//For every tile in the grid (height)
            for (int j = 0; j < mineSweeperGame.getCells(); j++)    {
            	//If the current tile in the game instance is mined
                if (mineSweeperGame.getTile(i, j).getIsMine() == true)     {
                	//Check if the current tile's pressed status returns true
                    assertEquals(mineSweeperGame.getTile(i, j).getButtonPressed(), true);
                }
            }
        }
    }
    
    /**
     * Tests the clearAdjacent method (One-time use. Has to be compiled before testing (by right clicking and selecting this method) due to the random nature of mine creation)
     */
    public void testClearAdjacent() throws FileNotFoundException   {
        //STEP 1 - Create new MineSweeperGame instance and give it's cells the value of 9
        MineSweeperGame mineSweeperGame = new MineSweeperGame(9);
        
        //Tests a tile with no mines nearby by pressing it and sending it to the clearAdjacent method
        mineSweeperGame.getTile(3, 7).setButtonPressed();
        //Call the clearAdjacent function on the pressed cell
        mineSweeperGame.clearAdjacent(mineSweeperGame.getTile(3, 7));
        
        //Checks if the tile to the top is pressed
        assertEquals(mineSweeperGame.getTile(3, 6).getButtonPressed(), true);
        //Checks if the getAdjacentMines method returns the appropriate value
        assertEquals(mineSweeperGame.getTile(3, 6).getAdjacentMines(), 0);
        
        //Checks if the button to the bottom is pressed 
        assertEquals(mineSweeperGame.getTile(3, 8).getButtonPressed(), true);
        //Checks if the getAdjacentMines method returns the appropriate value
        assertEquals(mineSweeperGame.getTile(3, 8).getAdjacentMines(), 0);
        
        //Checks if the button to the top-left is pressed
        assertEquals(mineSweeperGame.getTile(2, 6).getButtonPressed(), true);
        //Checks if the getAdjacentMines method returns the appropriate value
        assertEquals(mineSweeperGame.getTile(2, 6).getAdjacentMines(), 1);
        
        //Checks if the button to the left is pressed
        assertEquals(mineSweeperGame.getTile(2, 7).getButtonPressed(), true);
        //Checks if the getAdjacentMines method returns the appropriate value
        assertEquals(mineSweeperGame.getTile(2, 7).getAdjacentMines(), 2);
        
        //Checks if the button to the bottom-left is pressed 
        assertEquals(mineSweeperGame.getTile(2, 8).getButtonPressed(), true);
        //Checks if the getAdjacentMines method returns the appropriate value
        assertEquals(mineSweeperGame.getTile(2, 8).getAdjacentMines(), 2);
        
        //Checks if the button to the top-right is pressed
        assertEquals(mineSweeperGame.getTile(4, 6).getButtonPressed(), true);
        //Checks if the getAdjacentMines method returns the appropriate value
        assertEquals(mineSweeperGame.getTile(4, 6).getAdjacentMines(), 1);
        
        //Checks if the button to the right is pressed
        assertEquals(mineSweeperGame.getTile(4, 7).getButtonPressed(), true);
        //Checks if the getAdjacentMines method returns the appropriate value
        assertEquals(mineSweeperGame.getTile(4, 7).getAdjacentMines(), 0);
        
        //Checks if the button to the bottom-right is pressed
        assertEquals(mineSweeperGame.getTile(4, 8).getButtonPressed(), true);
        //Checks if the getAdjacentMines method returns the appropriate value
        assertEquals(mineSweeperGame.getTile(4, 8).getAdjacentMines(), 0);

        
        //STEP 2 - Tests a tile (with x position of 6 and y position of 4) with mines nearby by pressing it
        mineSweeperGame.getTile(6, 4).setButtonPressed();       
        //Clear all adjacent tiles of the same tile
        mineSweeperGame.clearAdjacent(mineSweeperGame.getTile(6, 4));
        //Checks if the tile itself has 4 adjacent mines
        assertEquals(mineSweeperGame.getTile(6, 4).getAdjacentMines(), 4);

        //Checks if the tile to the top is not pressed
        assertEquals(mineSweeperGame.getTile(6, 3).getButtonPressed(), false);
        //Checks if the tile to the top is a mine
        assertEquals(mineSweeperGame.getTile(6, 3).getIsMine(), true);
        
        //Presses the button at the bottom of the tile
        mineSweeperGame.getTile(6, 5).setButtonPressed();
        //Sends the bottom button to the clearAdjacent method with itself as a parameter
        mineSweeperGame.clearAdjacent(mineSweeperGame.getTile(6, 5));
        //Checks if the tile at the bottom returns the appropriate adjacent mines 
        assertEquals(mineSweeperGame.getTile(6, 5).getAdjacentMines(), 2);
        
        //Presses the button at the top-left
        mineSweeperGame.getTile(5, 3).setButtonPressed();
        //Sends the top-left button to the clearAdjacent method with itself as a parameter
        mineSweeperGame.clearAdjacent(mineSweeperGame.getTile(5, 3));
        //Checks if the top-left button returns the appropriate adjacent mines
        assertEquals(mineSweeperGame.getTile(5, 3).getAdjacentMines(), 4);
        
        //Checks if the button to the left of the tile is not pressed
        assertEquals(mineSweeperGame.getTile(5, 4).getButtonPressed(), false);
        //Checks if the button to the left of the tile is a mine
        assertEquals(mineSweeperGame.getTile(5, 4).getIsMine(), true);
        
        //Checks if the button to the bottom-left of the tile is not pressed
        assertEquals(mineSweeperGame.getTile(5, 5).getButtonPressed(), false);
        //Checks if the button to the bottom-left of the tile is a mine
        assertEquals(mineSweeperGame.getTile(5, 5).getIsMine(), true);
        
        //Checks if the button to the top-right of the tile is not pressed
        assertEquals(mineSweeperGame.getTile(7, 3).getButtonPressed(), false);
        //Checks if the button to the top-right of the tile is a mine
        assertEquals(mineSweeperGame.getTile(7, 3).getIsMine(), true);
        
        //Checks if the button to the right of the tile is not pressed
        mineSweeperGame.getTile(7, 4).setButtonPressed();
        //Clears the adjacent mines to the tile at the right
        mineSweeperGame.clearAdjacent(mineSweeperGame.getTile(7, 4));
        //Checks if the tile to the right returns the appropriate number of adjacent mines
        assertEquals(mineSweeperGame.getTile(7, 4).getAdjacentMines(), 2);
        
        //Presses the tile to the bottom-right of the tile
        mineSweeperGame.getTile(7, 5).setButtonPressed();
        //Checks if the button to the bottom-right is pressed
        mineSweeperGame.clearAdjacent(mineSweeperGame.getTile(7, 5));
        //Checks if the getAdjacentMines method of the bottom-right tile returns the appropriate value
        assertEquals(mineSweeperGame.getTile(7, 5).getAdjacentMines(), 1);
    }
    
    /**
     * Tests to see if the gameComplete dialog box opens after all mines are cleared
     */
    public void testCheckGameComplete() throws FileNotFoundException   {
        //Create new MineSweeperGame instance and give it's cells the value of 9
        MineSweeperGame mineSweeperGame = new MineSweeperGame(9);
        
        //For every tile in the grid (width)
        for (int i = 0; i < mineSweeperGame.getCells(); i++)    {
        	//For every tile in the grid (height)
            for (int j = 0; j < mineSweeperGame.getCells(); j++)    {
            	//If the current tile in the game instance is not mined
                if (mineSweeperGame.getTile(i, j).getIsMine() == false)   {
                	//Press the current tile
                    mineSweeperGame.getTile(i, j).setButtonPressed();
                }
            }
        }
        //Open the "game complete" dialog, confirming all buttons have been pressed
        mineSweeperGame.checkGameComplete();
    }
    
}