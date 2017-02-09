package com.myPackage.minesweeper.Testing;

import com.myPackage.minesweeper.Graphics.Tile;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TileTest extends junit.framework.TestCase {
    private Tile tile;    

    public TileTest(String name)  {
    	//Call superclass constructor
        super(name);
    }
    
    public static Test suite()  {
    	//Return a new instance of TestSuite
        return new TestSuite(TileTest.class);
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
    public void tearDown()  {
    }
    
    /**
     * Tests the getY method
     */
    public void testGetX()  {
    	//Instantiate a new tile, with an X position of 2
        Tile tile = new Tile(2, 4, 25, 2, 8);
        //Check if the tile's getX function returns 2
        assertEquals(tile.getX(), 2);
    }
    
    /**
     * Tests the getY method
     */
    public void testGetY()
    {
    	//Instantiate a new tile, with an X position of 4
        Tile tile = new Tile(2, 4, 25, 2, 8);
        //Check if the tile's getY function returns 4
        assertEquals(tile.getY(), 4);
    }
    
    /**
     * Tests the setAdjacentMines method
     */
    public void testSetAdjacentMines()
    {
    	//Instantiate a new tile, with a mine probability of 1/4 and an empty space probability of 3/4
        Tile tile = new Tile(2, 4, 25, 2, 8);
        //set the tile's number of adjacent mines to 5
        tile.setAdjacentMines(5);
        //Check if the tile's getAdjacentMines function returns 5
        assertEquals(tile.getAdjacentMines(), 5);
    }
    
    /**
     * Tests the getButtonPressed method
     */
    public void testGetButtonPressed()
    {
    	//Instantiate a new tile
        Tile tile = new Tile(2, 4, 25, 2, 8);
        //Check if the tile's getButtonPressed function returns true
        assertEquals(tile.getButtonPressed(), false);
        //Call the tile's setButtonPressed function
        tile.setButtonPressed();
        //Check if the tile's getButtonPressed function returns false
        assertEquals(tile.getButtonPressed(), true);
    }
    
    /**
     * Tests the getFlagged button
     */
    public void testGetFlagged()
    {
    	//Instantiate a new tile
        Tile tile = new Tile(2, 4, 25, 2, 8);
        //Check if the tile's getFlagged function returns false
        assertEquals(tile.getFlagged(), false);
        //Call the tile's setFlagged function
        tile.setFlagged();
        //Check if the tile's getFlagged function returns true
        assertEquals(tile.getFlagged(), true);
        //Call the tile's setFlagged function again
        tile.setFlagged();
        //Check if the tile's getFlagged function returns false
        assertEquals(tile.getFlagged(), false);
    }
    
    /**
     * Tests the getIsMine button to see if the tile is a mine
     */
    public void testGetIsMine()
    {
    	//Instantiate a new tile, with a mine probability of 100% and an empty space probability of 0.01%
        Tile tile = new Tile(2, 4, 25, 10, 0);
        //Check if the tile's getIsMine function returns true
        assertEquals(tile.getIsMine(), true);
    }
    
    /**
     * Tests the getIsMine button to see if the tile is an empty space
     */
    public void testGetIsMineForEmptySpace()
    {
    	//Instantiate a new tile, with mine probability of 0.01% and an empty space probability of 100%
        Tile tile = new Tile(2, 4, 25, 0, 10);
        //Check if the tile's getIsMine function returns false
        assertEquals(tile.getIsMine(), false);
    }
    
    /**
     * Tests the reset method
     */
    public void testReset()
    {
    	//Instantiate a new tile
        Tile tile = new Tile(2, 4, 25, 2, 8);
        //Press the button by calling it's setButtonPressed function
        tile.setButtonPressed();
        //Flag the button
        tile.setFlagged();
        //Reset the tile
        tile.reset();
        //Check if the tile's getButtonPressed function returns false
        assertEquals(tile.getButtonPressed(), false);
        //Check if the tile's getFlagged function returns false
        assertEquals(tile.getFlagged(), false);
    }
}
