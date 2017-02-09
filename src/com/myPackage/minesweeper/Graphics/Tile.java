package com.myPackage.minesweeper.Graphics;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class Tile
{ 
    //This tile's x and y positions in the grid    
    private int x,y;
    //The graphical width of this tile
    private int boxWidth;
    //The number of nearby mines
    private int adjacentMines;
    //Boolean for if the tile has been pressed or not, with default value of false
    private boolean buttonPressed = false;
    //Boolean for if the tile has a flag placed on it, with default value of false
    private boolean flagged = false;
    //Boolean for if the tile is an underlying mine or not
    private boolean isMined;
    //Probability of this tile becoming a mine
    private int mineProb;
    //Probability of this tile becoming an empty space
    private int emptySpaceProb;
    
    public Tile(int x, int y, int boxWidth, int mineProb, int emptySpaceProb)  {
    	//Set the X position of this instance as the one passed to the parameter
        this.x = x;
        //Set the Y position of this instance as the one passed to the parameter
        this.y = y;
      //Set the graphical width of this instance as the one passed to the parameter
        this.boxWidth = boxWidth;
        //Set this tile's mine probability as the one passed to the parameter
        this.mineProb = mineProb;
        //Set this tile's empty space probability as the one passed to the parameter
        this.emptySpaceProb = emptySpaceProb;
        //Create a Random object
        Random rand = new Random();
        //Get an integer from the Random object between 1 and 12
        int value = rand.nextInt(11) + 1; 
        //If the next double found via the Randomizer is less than/equal to the mine probability in the parameter
        if(value <= mineProb)    { 
        	//Set this tile as a mine by setting the isMine value to true
             this.isMined = true;
        //Otherwise, if the next double found via the Randomizer is less than/equal to the empty space probability
        }  else if(value <= emptySpaceProb)  {
        	//Set this tile as an empty space by setting the isMine value to false
             this.isMined = false;
        }  
    }
        
    public Tile(int x, int y, int boxWidth)  {
        	//Set the X position of this instance as the one passed to the parameter
            this.x = x;
            //Set the Y position of this instance as the one passed to the parameter
            this.y = y;
          //Set the graphical width of this instance as the one passed to the parameter
            this.boxWidth = boxWidth;
    }
    
    public void drawTile(Graphics g)   {
    	//X position scaling, storing the x position multiplied by the box width
        int xScaled = x * boxWidth ;
         //Y position scaling, storing the y position multiplied by the box width
        int yScaled = y * boxWidth ;
        //Set the graphics object's currently selected colour to black
        g.setColor(Color.black);
        //Draw a black square acting as the background
        g.drawRect(xScaled,yScaled,boxWidth,boxWidth);
        
        //If this button hasn't been pressed yet
        if (buttonPressed == false)   {
            //Set the currently selected colour to blue
            g.setColor(Color.blue);
            //Draw a smaller blue (filled) square
            g.fillRect(xScaled + 1, yScaled + 1, boxWidth - 1, boxWidth - 1);
        //Otherwise, if the button has been pressed and is not a mine
        }  else if (buttonPressed == true && isMined == false)  {
            //Set the currently selected colour to cyan
            g.setColor(Color.cyan);     
            //Draw a cyan square over the existing blue square of the tile
            g.fillRect(xScaled + 1, yScaled + 1, boxWidth - 1, boxWidth - 1);
            //If the tile's adjacentMines field equals 1
            if (adjacentMines == 1)  {
            	//Set the currently selected colour to blue
                g.setColor(Color.blue);
                //Draw a blue "1" in the middle of the square
                g.drawString("" + adjacentMines, xScaled + 5, yScaled + 15);
            //If the tile's adjacentMines field equals 2
            }  else if (adjacentMines == 2)  { 
            	//Set the currently selected colour to green
                g.setColor(Color.green);
                //Draw a green "2" in the middle of the square
                g.drawString("" + adjacentMines, xScaled + 5, yScaled + 15);
            // If the tile's adjacentMines field is 3 or more
            }  else if (adjacentMines >=3) {
            	//Set the currently selected colour to red
                g.setColor(Color.red);
                //Draw a red "3" (or any number above 3) in the middle of the square
                g.drawString("" + adjacentMines, xScaled + 5, yScaled + 15);
            }
          //If the tile is pressed and is a mine
          }  else if (buttonPressed == true && isMined == true)   {
            //Set the currently selected colour to red
            g.setColor(Color.red);
            //Draw a red square over the existing blue square
            g.fillRect(xScaled + 1, yScaled + 1, boxWidth - 1, boxWidth - 1);
            //Set the currently selected colour to black
            g.setColor(Color.black);
            //Draw a black (filled) oval over the red square
            g.fillOval(xScaled + 2, yScaled + 2, boxWidth - 4, boxWidth - 4);
            //Draw black lines spanning the square, with varying starting and ending X and Ys
            g.drawLine(xScaled, yScaled, xScaled+boxWidth, yScaled+boxWidth);
            g.drawLine(xScaled, yScaled+boxWidth, xScaled+boxWidth, yScaled);
            g.drawLine(xScaled, yScaled + (boxWidth / 4), xScaled+boxWidth, yScaled+boxWidth - (boxWidth / 4));
            g.drawLine(xScaled, yScaled+boxWidth - (boxWidth / 4), xScaled+boxWidth, yScaled + (boxWidth / 4));
            g.drawLine(xScaled + (boxWidth / 4), yScaled , xScaled+boxWidth - (boxWidth / 4), yScaled+boxWidth);
            g.drawLine(xScaled +boxWidth - (boxWidth / 4), yScaled, xScaled + (boxWidth / 4), yScaled + boxWidth);

        }
        //If this tile has a flag tile placed over it, draw the flag tile
        if (flagged == true)   {
            //Set the currently selected colour to yellow
            g.setColor(Color.yellow);
            //Draw a yellow square over the existing blue square
            g.fillRect(xScaled + 1, yScaled + 1, boxWidth - 1, boxWidth - 1);
            //Set the currently selected colour to black
            g.setColor(Color.black);
            //Draw a black cross (x) over the yellow tile
            g.drawLine(xScaled,yScaled,xScaled+boxWidth,yScaled+boxWidth);
            g.drawLine(xScaled,yScaled+boxWidth,xScaled+boxWidth,yScaled);          
        }
    }
    
    public void reset()   {
        //If this button has already been pressed
        if (buttonPressed == true)  {
        	//Unpress this instance
            buttonPressed = false;
        }
        //If this tile has been flagged
        if (flagged == true) {
        	//Unflag this tile
            flagged = false;
        }
        //Creates a Random object
        Random rand = new Random();
        //Get an integer from the Random object between 1 and 12
        int value = rand.nextInt(11) + 1; 
        //If the next double found via the Randomizer is less than/equal to the mine probability in the parameter
        if(value <= mineProb)    { 
        	//Set this tile as a mine by setting the isMine value to true
             isMined = true;
        //Otherwise, if the next double found via the Randomizer is less than/equal to the empty space probability
        }  else if(value <= emptySpaceProb)  {
        	//Set this tile as an empty space by setting the isMine value to false
             isMined = false;
        }  
        //Resets this tile's adjacentMines back to 0
        setAdjacentMines(0);
    }
    
    //SETTER FUNCTIONS
    
    public void setMine(boolean set)  {
    	//If the boolean value in the parameter is true
    	if (set == true) {
    		//Set the mined status of this tile to true
    		isMined = true;
    	//If the boolean value in the parameter is false
    	} else if (set == false) {
        	//Set the mined status of this tile to true
        	isMined = false;
        } 	
    }
    
    public void setAdjacentMines(int noOfMines)  {
        //Sets the tile's adjacentMines field (the number of nearby mines) as the value passed to the parameter
        adjacentMines = noOfMines;
    }
    
    public void setButtonPressed()  {
        //If this tile's buttonPressed value is false
        if (buttonPressed == false) {
        	//Set the buttonPressed value to true
            buttonPressed = true;
        }
    }
    
    public void setFlagged()   {
        //If this tile has not been pressed and is not flagged
        if (buttonPressed == false && flagged == false)  {
            //Set this tile's flagged value to true
        	flagged = true;
        //Otherwise, if this tile has not been pressed and is already flagged
        } else if (buttonPressed == false && flagged == true)  {
        	//Set this tile's flagged value to true
            flagged = false;
        }
    }
    
    //GETTER FUNCTIONS
    
    public int getX() {
        //Return this tile's x position
        return x;
    }
    
    public int getY()   {
        //Return this tile's y position
        return y;
    }
    
    public int getAdjacentMines() {
        //Return the number of mines near the tile
        return adjacentMines;
    }
    
    public boolean getButtonPressed()   {
        //Return the buttonPressed value
        return buttonPressed;
    }
    
    public boolean getFlagged()   {
        //Return if this tile is flagged or not
        return flagged;
    }
    
    public boolean getIsMine()  {
        //Returns if this tile is mined
        return isMined;
    }
}