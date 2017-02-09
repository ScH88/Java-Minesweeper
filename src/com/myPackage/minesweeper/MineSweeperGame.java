package com.myPackage.minesweeper;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.myPackage.minesweeper.Graphics.ContentPane;
import com.myPackage.minesweeper.Graphics.Tile;

import java.io.*;
import java.util.Scanner;

public class MineSweeperGame extends JFrame  {
    
    //The array used for storing the tiles
    private static Tile[][] tiles;
    //The width of each box represented in the GUI
    private static int BOX_WIDTH = 20;
    //The number of cells which define the length of the grid length and width wise
    private static int CELLS;
    //The likelihood of a mine being created
    private static int MINE_CREATION_PROBABILITY = 2;
    //The likelihood of an empty space being created
    private static int EMPTYSPACE_CREATION_PROBABILITY = 12;
    //The ContentPane (extends JPanel) object which is used to draw the grid
    private ContentPane jContPane;
    //The score of the MineSweeper game, which is deducted everytime the user left-clicks a tile, with default value of 10000
    private static int score = 10000;
    //The number of flags, which is deducted when a user flags a tile, and increases if the user frees a tile by right-clicking it again
    private static int flags;
    //The text label which displays the score
    private static JLabel scoreLabel;
    //The text label which displays the number of flags
    private static JLabel flagLabel;
    //The status which displays if the game is operational, with true meaning it is still going and false meaning it is finished
    private boolean gameStatus;
    
    public MineSweeperGame(int noOfCells) throws FileNotFoundException {
    	//Set the number of cells as the value passed to the parameter
        CELLS = noOfCells;
        //If the number of cells is 28
        if (CELLS == 28) {
        	//Change the box width to 15;
        	BOX_WIDTH = 15;
        } else {
        	BOX_WIDTH = 20;
        }
        //Call the setFrame function to set up the JFrame and the JPanel
        setFrame();
        //If the number of cells is 9
        if (CELLS == 9) {
        	//Set the number of flags to 10
            flags = 10;
          //If the number of cells is 16
        } else if (CELLS == 16) {
        	//Set the number of flags to 40
            flags = 40;
          //If the number of cells is 28
        }  else if (CELLS == 28) {
        	//Set the number of flags to 99
            flags = 99;
        }
        //Instantiate the tiles array using the number of cells as it's length and width
        tiles = new Tile[CELLS][CELLS];
        // For each cell in the cell length
        for(int i =0; i<CELLS;i++) {
        	//For each cell in the cell height
            for(int j =0; j<CELLS;j++) {
            	//Instantiate the current tile as a new Tile object
                Tile tile = new Tile(i,j,BOX_WIDTH, MINE_CREATION_PROBABILITY, EMPTYSPACE_CREATION_PROBABILITY);  
                //Set the new Tile object in the array
                tiles[i][j] = tile;
            }
        }
        //Call the setContent function to set up the JLabels and other components for the JPanel
        setContent();
        //Set the JFrame visibility to true
        setVisible(true);   
        //Pack the JFrame
        pack();
    }
    
    //ALTERNATE CONSTRUCTOR FOR LOADING A SAVED GAME
    public MineSweeperGame() {
    	//Call the loadgame function to gather data from a save file
    	loadGame();
    	//Call the setFrame function to set up the JFrame and JPanel
        setFrame();
        //Call the setContent function to set up the JLabels and other components for the JPanel
        setContent();
        //Set the JFrame visibility to true
        setVisible(true);   
        //Pack the JFrame
        pack();
    }
    
    public void setFrame() {
    	//Set the title of the JFrame
        setTitle("MineSweeper");
    	 //If the number of cells is 9
        if (CELLS == 9) {
        	//Set the size of this instance
        	 setSize(550, 400);
        //If the number of cells is 16
        } else if (CELLS == 16) {
        	//Set the size of this instance
        	setSize(650, 500);
          //If the number of cells is 28
        }  else if (CELLS == 28) {
        	//Set the size of this instance
        	setSize(650, 650);
        }
   	   //Disable resizing of the JFrame
       setResizable(false);
       //Set location relativity to null, centering the JFrame on the screen
       setLocationRelativeTo(null);
       //Set the layout which will store the score label and the flag label
       setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
       //Instantiate the ContentPane JPanel
       jContPane = new ContentPane();
       //Add the ContentPane to the JFrame
       add(jContPane);
       //Set the size of the frame
       jContPane.setPreferredSize(new Dimension(getWidth(), getHeight()));
       //Set the size of the content pane
       jContPane.setSize(new Dimension(getWidth(), getHeight()));
       //Set the content pane layout to null, allowing coordinate based component placement
       jContPane.setLayout(null);
     //Set the default close operation as "DO_NOTHING_ON_CLOSE", disabling it so a custom close operation can be added
       setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
   }
    
    public void setContent() {
    	//JLabel for rendering the grid
        JLabel gridCont = new JLabel() {
        	public void paintComponent(Graphics g)    {
                //Call the paintComponent of the superclass
                super.paintComponent(g);
                //For every tile length-wise
                for (int i = 0; i < CELLS; i++)   {
                	//For every tile height-wise
                    for (int j = 0; j < CELLS; j++)  {
                    	//Call that tile's drawTile function
                        tiles[i][j].drawTile(g);
                    }
                }
            }
        };
        //Add a mouseListener, which picks up any mouse clicks in the frame
        gridCont.addMouseListener(new MouseGridListener());
        //If the number of cells is 9
        if (CELLS == 9) {
        	//Set the size of the content JLabel
            gridCont.setSize(185, 185);
            //Set the location of the grid content JLabel to be more in the middle
        	gridCont.setLocation(getWidth() / 3, 80); 	
        //If the number of cells is 16
        } else if (CELLS == 16) {
        	//Set the size of the content JLabel
            gridCont.setSize(325, 325);
            //Set the location of the grid content JLabel to be more in the middle
        	gridCont.setLocation(getWidth() / 4, 80);
          //If the number of cells is 28
        }  else if (CELLS == 28) {
        	//Set the size of the content JLabel
            gridCont.setSize(462, 462);
            //Set the location of the grid content JLabel to be more in the middle
        	gridCont.setLocation(getWidth() / 6, 80);
        }
        //Add the grid content JLabel to the ContentPane JPanel
        jContPane.add(gridCont);
        //JLabel for displaying player score
        scoreLabel = new JLabel(new String("<html><body>"
    			+ "<h1 style='color:#0000ff; font-size:12px;'>Score: " + score + "</h1>"		
    			+ "</html></body>"));
        //Set size of score JLabel
        scoreLabel.setSize(100, 100);
        //Set location of score JLabel
        scoreLabel.setLocation(gridCont.getX(), gridCont.getY() + gridCont.getHeight() - 30);
        //Add the score label
        jContPane.add(scoreLabel);
        //JLabel for the flag indicator
        flagLabel = new JLabel(new String("<html><body>"
    			+ "<h1 style='color:#0000ff; font-size:12px;'>Flags: " + flags + "</h1>"		
    			+ "</html></body>"));
        //Set size of flag JLabel
        flagLabel.setSize(100, 100);
        //Set location of flag JLabel
        flagLabel.setLocation(scoreLabel.getX() + scoreLabel.getWidth() + 20, gridCont.getY() + gridCont.getHeight() - 30);
        //Add the flag label
        jContPane.add(flagLabel);
        //JLabel for displaying the page header
        JLabel head = new JLabel(new String("<html><body>"
    			+ "<h1 style='color:#0000ff; font-size:25px;'>Minesweeper</h1>"		
    			+ "</html></body>"));
        //Set the size of the header JLabel
        head.setSize(220, 200);
        //Set the location of the header JLabel
        head.setLocation(getWidth() / 3, -60);
        //Add the header JLabel to the content pane
        jContPane.add(head);
        //Adds a new WindowListener, which if the user wants to quit in the middle of the game, it opens the quit game dialog
        super.addWindowListener (new java.awt.event.WindowAdapter()   {
               //Modifies the close button (the "red square with white X")
               public void windowClosing (java.awt.event.WindowEvent e)  {
                   //Try statement
            	   try  {
                       //If the game is still going
                       if (gameStatus == true)   {
                    	   //Call the quitGameDialog method to ask if the user wants to save his/her game
                           quitGameDialog(); 
                       //If the game is finished
                       }  else if (gameStatus == false) {
                    	   //Close this program
                           System.exit(0);
                       }
                   //If an InputOutputException is caught
                   } catch (IOException i)  {
                       //Print the exception's stack trace
                       i.printStackTrace();
                   }
               }
           }
        );
    	//Set gameStatus to true upon starting
        gameStatus = true; 
    }
    
    public void changeFlagCount(Tile tile) {
        //If the file is flagged
        if (tile.getFlagged() == true)  {
            //Reduce the flag number by 1
            flags -= 1;
            //Change the "Flags" label so that it displays the new number of flags
            flagLabel.setText(new String("<html><body>"
        			+ "<h1 style='color:#0000ff; font-size:12px;'>Flags: " + flags + "</h1>"		
        			+ "</html></body>"));
        //If it tile is not flagged (especially after a flag has been removed and can only add if removed)
        } else if (tile.getFlagged() == false) {
            //Add 1 to the flag number
            flags += 1;
            //Change the "Flags" label so that it displays the new number of flags
            flagLabel.setText(new String("<html><body>"
        			+ "<h1 style='color:#0000ff; font-size:12px;'>Flags: " + flags + "</h1>"		
        			+ "</html></body>"));
        }
        //If there are no flags left in the number of flags
        if (flags == 0)  {
            //Change the text of the flag label
            flagLabel.setText(new String("<html><body>"
        			+ "<h1 style='color:#0000ff; font-size:12px;'>Out of flags!</h1>"		
        			+ "</html></body>"));
        }
    }
    
    public static void setScore(int newScore) {
        //Changes the score into the number given by the newScore parameter
        score = newScore;
        //Change the text of the score label
        scoreLabel.setText(new String("<html><body>"
    			+ "<h1 style='color:#0000ff; font-size:12px;'>Score: " + newScore + "</h1>"		
    			+ "</html></body>"));
    }
   
    public void resetScore() {
        //Resets the score back to 10000
        score = 10000;
        //Changes the "Score" label so that it displays the new number of flags
        scoreLabel.setText(new String("<html><body>"
    			+ "<h1 style='color:#0000ff; font-size:12px;'>Score: " + score + "</h1>"		
    			+ "</html></body>"));
    }
    
    public static void setFlags(int newFlags) {
        //Changes the number of flags into the number given by the newFlags parameter
        flags = newFlags;
        //Changes the "Flags" label so that it displays the new number of flags
        flagLabel.setText(new String("<html><body>"
    			+ "<h1 style='color:#0000ff; font-size:12px;'>Score: " + score + "</h1>"		
    			+ "</html></body>"));
    }
   
    public void resetFlags()  {
        //If the number of cells is 9 (Easy mode)
        if (CELLS == 9) {
            //Change number of flags to 10
            flags = 10; 
        //If the number of cells is 16 (Medium mode)
        }  else if (CELLS == 16)  {
            //Changes the number of flags to 40
            flags = 40;
        //If the number of cells is 28 (Hard mode)
        }  else if (CELLS == 28)  {
            //Changes the number of flags to 99
            flags = 99;
        }
        //Changes the "Flags" label so that it displays the new number
        flagLabel.setText(new String("<html><body>"
    			+ "<h1 style='color:#0000ff; font-size:12px;'>Flags: " + flags + "</h1>"		
    			+ "</html></body>"));
    }
    
    synchronized public void deductScore (int minus)  { 
        //Reduces the score by the number given by the minus parameter
        score -= minus; 
        //Changes the "Score" label so that it displays the new score
        scoreLabel.setText(new String("<html><body>"
    			+ "<h1 style='color:#0000ff; font-size:12px;'>Score: " + score + "</h1>"		
    			+ "</html></body>")); 
    }

    public void detonateAllMines()  {
       //Instantiate a new File object according to file path
       File newFile = new File("JaveMineSweeperSaveGame.txt");
       //If the new file exists
       if (newFile.exists()) {
            //Call the deleteSaveFile function to delete the file
            deleteSaveFile();
       }  
       //For every cell in the grid length-wise
       for (int i = 0; i < CELLS; i++)  {
    	   //For every cell in the grid height-wise
           for (int j = 0; j < CELLS; j++)  {
               //If the current tile is a mine
               if (tiles[i][j].getIsMine() == true) {
                   //Set the current tile to it's pressed state
                   tiles[i][j].setButtonPressed();
               }
            }
       }
       //Change the game status to false, finishing it
       gameStatus = false;
    }
  
    public void clearAdjacent(Tile currentTile)  {
        //STEP 1 = Checks each mine around the current tile
        //If the mineAt function for the tile to the right of the current one returns true
    	if (mineAt(currentTile.getX() + 1, currentTile.getY())) {
    		 //Call the tile's setAdjacentMines function, adding 1 to it's existing value
             currentTile.setAdjacentMines(currentTile.getAdjacentMines() + 1);
    	}
    	//If the mineAt function for the tile below the current one returns true
    	if (mineAt(currentTile.getX(), currentTile.getY() + 1)) {
   		   //Call the tile's setAdjacentMines function, adding 1 to it's existing value
           currentTile.setAdjacentMines(currentTile.getAdjacentMines() + 1);
    	}
    	//If the mineAt function for the tile left of the current one returns true
    	if (mineAt(currentTile.getX() - 1, currentTile.getY())) {
   		   //Call the tile's setAdjacentMines function, adding 1 to it's existing value
           currentTile.setAdjacentMines(currentTile.getAdjacentMines() + 1);
    	}
    	//If the mineAt function for the tile above the current one returns true
    	if (mineAt(currentTile.getX(), currentTile.getY() - 1)) {
   		   //Call the tile's setAdjacentMines function, adding 1 to it's existing value
           currentTile.setAdjacentMines(currentTile.getAdjacentMines() + 1);
    	}
    	//If the mineAt function for the tile to the lower-right of the current one returns true
    	if (mineAt(currentTile.getX() + 1, currentTile.getY() + 1)) {
   		   //Call the tile's setAdjacentMines function, adding 1 to it's existing value
           currentTile.setAdjacentMines(currentTile.getAdjacentMines() + 1);
    	}
    	//If the mineAt function for the tile to the upper-left of the current one returns true
    	if (mineAt(currentTile.getX() - 1, currentTile.getY() - 1)) {
   		   //Call the tile's setAdjacentMines function, adding 1 to it's existing value
           currentTile.setAdjacentMines(currentTile.getAdjacentMines() + 1);
    	}
    	//If the mineAt function for the tile to the upper-right of the current one returns true
    	if (mineAt(currentTile.getX() + 1, currentTile.getY() - 1)) {
   		   //Call the tile's setAdjacentMines function, adding 1 to it's existing value
           currentTile.setAdjacentMines(currentTile.getAdjacentMines() + 1);
    	}
    	//If the mineAt function for the tile to the lower-left of the current one returns true
    	if (mineAt(currentTile.getX() - 1, currentTile.getY() + 1)) {
   		   //Call the tile's setAdjacentMines function, adding 1 to it's existing value
           currentTile.setAdjacentMines(currentTile.getAdjacentMines() + 1);
    	}
         
        //STEP 2 = Checks the tiles above, below, left and right of the current tile, and presses them if their mine count is 0 and has them do the same.
        //Tiles stop if they themselves have a mine count of more than 0
    	
        //If the current tile's adjacent mines number is 0
        if (currentTile.getAdjacentMines() == 0) {
        	//If the current tile is not mined
        	if (currentTile.getIsMine() == false) {
        		//If the tile to the right of the current tile is able to be pressed as per expansion
            	if (canExpand(currentTile.getX() + 1, currentTile.getY())) {
            		//Call the tile's setButtonPressed function
                	tiles[currentTile.getX() + 1][currentTile.getY()].setButtonPressed();  
                  //Call the clearAdjacent function to clear the adjacent tiles value of the tile
                    clearAdjacent(tiles[currentTile.getX() + 1][currentTile.getY()]);
            	}
            	//If the tile to the left of the current tile is able to be pressed as per expansion
            	if (canExpand(currentTile.getX() - 1, currentTile.getY())) {
            		//Call the tile's setButtonPressed function
                	tiles[currentTile.getX() - 1][currentTile.getY()].setButtonPressed();  
                  //Call the clearAdjacent function to clear the adjacent tiles value of the tile
                    clearAdjacent(tiles[currentTile.getX() - 1][currentTile.getY()]);
            	}
            	//If the tile to the top of the current tile is able to be pressed as per expansion
            	if (canExpand(currentTile.getX(), currentTile.getY() + 1)) {
            		//Call the tile's setButtonPressed function
                	tiles[currentTile.getX()][currentTile.getY() + 1].setButtonPressed();  
                  //Call the clearAdjacent function to clear the adjacent tiles value of the tile
                    clearAdjacent(tiles[currentTile.getX()][currentTile.getY() + 1]);
            	}
            	//If the tile to the bottom of the current tile is able to be pressed as per expansion
            	if (canExpand(currentTile.getX(), currentTile.getY() - 1)) {
            		//Call the tile's setButtonPressed function
                	tiles[currentTile.getX()][currentTile.getY() - 1].setButtonPressed();  
                  //Call the clearAdjacent function to clear the adjacent tiles value of the tile
                    clearAdjacent(tiles[currentTile.getX()][currentTile.getY() - 1]);
            	}
        	}
        }
    }
    
    public boolean canExpand(int x, int y) {
    	//Boolean variable for return value
    	boolean returnVal = false;
    	//If both coordinates is greater than/equal to 0 and less than the cell width/height
        if (x < CELLS && x >=0 && y < CELLS && y >= 0) {
        	//If the tile is not pressed and not flagged
        	if (tiles[x][y].getButtonPressed() == false && !tiles[x][y].getFlagged()) {
        		//If the tile is not mined
        		if (tiles[x][y].getIsMine() == false) {
        			//Set the return value to true
        			returnVal = true;
            	}
        	}
        }
        //Return the boolean return value
        return returnVal;
    }
    
    public boolean mineAt(int x, int y) {
    	//Boolean variable for the return value
    	boolean returnVal = false;
    	//If both tile coordinates are between 0 and the cell size, and is not pressed
        if (x < CELLS && x >= 0 && y < CELLS && y >= 0) {
        	if (tiles[x][y].getButtonPressed() == false) {
        		//If the tile marked by the coordinates is mined
            	if (tiles[x][y].getIsMine() == true) {
            		//Set the return value to true 
            		returnVal = true;
            	}
        	}
        } 
        //Return the return value
        return returnVal;
    }
    
    public void gameOver() {     
        //Opens the Dialog class' "gameOver" method, which will produce the appropriate frame and return an int value depending on which button the user presses
        int selectOption = Dialog.gameOver();
        //Performs one of the following functions depending on what number the returnState/selectOption variable is.
        //If the selectOption Dialog equals 1 or 4 (if the player tries again or chooses the closed_option
        if (selectOption == 1 || selectOption == 4) {
        	//For each cell in the cell width
            for (int i = 0; i < CELLS; i++) {
            	//For each cell in the cell height
                for (int j = 0; j < CELLS; j++)  {
                    //Call the current tile's reset function
                    tiles[i][j].reset();
                }
            }
            //Repaint the whole grid
            jContPane.repaint();
            //Reset the number of flags available
            resetFlags();
            //Reset the game status back to true
            gameStatus = true;
         //Otherwise, if the returnState/selectOption variable is 2, allow the user to select difficulty and create a new game
        }   else if (selectOption == 2) {
            //Call the Dialog class' newDifficulty function, returning a variable which defines the number of cells (depending on which difficulty the user chooses) 
            int noOfCells = Dialog.newDifficulty();
            //Create a local MineSweeperGame, but set it to null
            MineSweeperGame newGame = null;
            //Try statement
            try {
                //Instantiate the previously nullified MineSweeperGame instance and set it's cells using the noOfCells variable 
                newGame = new MineSweeperGame(noOfCells);
            //If a FileNotFoundException is found
            } catch (FileNotFoundException f)  {
                //Print the following message onto the console
                System.out.println("FileNotFoundException caught");
            }
            //Remove this MineSweeperGame instance before showing the new one
            dispose();
            //Set this instance's visibility to false
            setVisible(false);
        //If the selectOption returns 3
        }  else if (selectOption == 3)  {
            //Shut down the program
            System.exit(0);
        }
    }
    
    public void checkGameComplete() {
        //Local integer for the number of empty spaces
        int emptyCount = 0;
        //Local integer for the number of mined tiles
        int mineCount = 0;
        
        //For all tiles length-wise
        for (int i = 0; i < tiles.length; i++) {
        	//For all tiles height-wise
            for (int j = 0; j < tiles.length; j++) {
                //If the current tile has been pressed and is not a mine
                if (tiles[i][j].getButtonPressed() == true && tiles[i][j].getIsMine() == false)  {   
                	//Increment the empty space count by 1
                    emptyCount++;    
                //Otherwise, if the tile is a mine
                } else if (tiles[i][j].getIsMine() == true)  {
                    //Increment the mined tile count by 1
                	mineCount++;
                }
            }
        }
        
        //If the number of empty spaces equals the number of tiles minus the number of mines
        if (emptyCount == ((tiles.length * tiles.length) - mineCount))  {
        	//Call the gameComplete function
            gameComplete();
        }
    }
    
    public void gameComplete()  {
       //Local File instance for a file using the save game file path
       File file = new File("JavaMineSweeperSaveGame.txt");
       //If the file exists
       if (file.exists()) {
          //Boolean for the result of attempt to delete the file defined before
          boolean success = file.delete();
          //If unsuccessful
          if (!success)  {
        	  //Throw IllegalArgumentException
             throw new IllegalArgumentException("Could not delete file");  
          }
       }  
       //Open the Dialog class' gameComplete() function, returning the returnState variable which will define the selectOption variable
       int selectOption = Dialog.gameComplete();  
       // If the returnState/selectOption variable equals 1 or 4 (if the user tries again or closes the dialog window)
       if (selectOption == 1 || selectOption == 4) {
    	   //For each tile length-wise
            for (int i = 0; i < CELLS; i++) {
            	//For each tile height-wise
                for (int j = 0; j < CELLS; j++)  {
                    //Reset the current tile
                	tiles[i][j].reset();
                }
            }
            //Repaint the whole grid
            jContPane.repaint();
            //Reset the score if the user wants to play the same game again
            resetScore();
            //Reset the number of flags
            resetFlags();
         // If the returnState/selectOption variable equals 2, allow the user to select difficulty, then create new game and discard current one
         } else if (selectOption == 2) {
            //Call the Dialog class' newDifficulty method, returning a variable which defines the number of cells (depending on which difficulty the user chooses) 
            int noOfCells = Dialog.newDifficulty();
            //Create a new MineSweeperGame variable, but set it null
            MineSweeperGame newGame = null;
            //Try statement
            try  {
                //Instantiates the nullified MineSweeperGame instance and passes the noOfCells/returnState variable as it's parameter
                newGame = new MineSweeperGame(noOfCells);
            //If a FileNotFoundException is caught
            }   catch (FileNotFoundException f)  {
                //Print the following to console
                System.out.println("FileNotFoundException caught");
            } 
            //Dispose of this instance
            dispose();
            //Set the visibility of this instance to false
            setVisible(false);
       //If noOfCells/returnState variable equals 3, close the system
       }  else if (selectOption == 3)  {
    	   //Shut down this program
            System.exit(0);
       }
    }
    
    public void quitGameDialog() throws IOException  {
        //Calls the Dialog class' quitGame method, which returns an int value which defines the local selectOption variable
        int selectOption = Dialog.quitGame();
        //If selectOption return value from the dialog equals 1, call the saveGame method (which will create a text file containing current game data)
        if (selectOption == 1) {
        	//Call the saveGame function
            saveGame();
            //Shut down this program
            System.exit(0);
        //If selectOption/returnState variable equals 2, quit the game without saving
        } else if (selectOption == 2)  {
        	//Shut down this program
            System.exit(0);
        //If selectOption/returnState variable equals 3, return to the current game by calling the checkGameComplete method
        } else if (selectOption == 3) {
            //Call the checkGameComplete function
        	checkGameComplete();
        }
    }
    
    public void saveGame() {
    	//Try statement
    	try  {
        //Create a new File instance and gives it the name "JavaMineSweeperSaveGame.txt" (the ".txt" at the end meaning it is a text file)
        File newFile = new File ("JavaMineSweeperSaveGame.txt");
        //Instantiate a new Bufferedwriter for the text file
        BufferedWriter output =  new BufferedWriter(new FileWriter(newFile));
        //Instantiate a new PrintWriter object, passing the output variable as it's parameter 
        PrintWriter print = new PrintWriter(output);
                
        //If the new file does not exist
        if (!newFile.exists()) {
        	//Create a new file associated with the File object
            newFile.createNewFile();    
        }
            //Write the title
            output.write("//SaveGame for MineSweeperGame");
            //Move down one line
            print.println();
            //Write the "[Cells]" switch
            output.write("[Cells]");
            //Move down one line
            print.println();
            //Write the number of cells onto the save file
            output.write(this.getCells() + ",");
            //Move down one line
            print.println();
            //Write the "[Score]" switch onto the save file
            output.write("[Score]");
            //Move down one line
            print.println();
            //Write the score onto the save file
            output.write(this.getScore() + ",");
            //Move down one line
            print.println();
            //Write the "[Flags]" switch onto the save file
            output.write("[Flags]");
            //Move down one line
            print.println();
            //Write the number of flags onto the save file
            output.write(this.getFlags() + ",");
            //Move down one line
            print.println();
        
            //For each tile object length-wise
            for (int i = 0; i < CELLS; i++) {
            	//For each tile height-wise
                for (int j = 0; j < CELLS; j++) {
                    //If the current tile is not a mined tile
                    if (tiles[i][j].getIsMine() == false)  {
                        //Write the tile down as an empty space
                    	output.write("[EmptySpace]");
                    	//Move down one line
                        print.println();
                        //Write the X and Y coordinates of the tile
                        output.write(tiles[i][j].getX() + "," + tiles[i][j].getY());
                        //Move down one line
                        print.println();
                    }
                    //If the current tile is a mined tile
                    if (tiles[i][j].getIsMine() == true) {
                    	//Write the tile down as a mined tile
                        output.write("[SetMine]");
                        //Move down one line
                        print.println();
                        //Write the X and Y coordinates of the tile
                        output.write(tiles[i][j].getX() + "," + tiles[i][j].getY());
                        //Move down one line
                        print.println();
                    }
                    //If the current tile has been pressed
                    if (tiles[i][j].getButtonPressed() == true)  {
                    	//Write the tile down as pressed
                        output.write("[PressButton]");
                        //Move down one line
                        print.println();
                        //Write the X and Y coordinates of the tile
                        output.write(tiles[i][j].getX() + "," + tiles[i][j].getY() + "," + tiles[i][j].getAdjacentMines() + ",");
                        //Move down one line
                        print.println();
                    }
                    //If a tile has a flag placed over it
                    if (tiles[i][j].getFlagged() == true) {
                    	//Write the tile down as flagged
                        output.write("[FlagTile]");
                        //Move down one line
                        print.println();
                        //Write the X and Y coordinates of the tile
                        output.write(tiles[i][j].getX() + "," + tiles[i][j].getY());
                        //Move down one line
                        print.println();
                    }
                }
            }
            //Close the Writer object
            output.close();
            //Close the PrintWriter object
            print.close();
            //Make the save file read-only
            newFile.setReadOnly();
            //Set the file's writable status to false
            newFile.setWritable(false);
            //Close down the system 
            System.exit(0);
        //If an IOException is caught
    	} catch (IOException e) {
            //Print the exception's stack trace
            e.printStackTrace();
        }
   }
    
    public static void loadGame() {
      //Try statement
      try  {
          //Create a new File object and give it the name "JavaMineSweeperSaveGame" (the same name as used in the saveGame() method)
          File file = new File("JavaMineSweeperSaveGame.txt");
          //Create a new scanner object that scans the file (if it exists. If not, a FileNotFoundException is caught)
          Scanner scan = new Scanner(file);
          //String for the current mode of scanning (e.g. lines for cells have 1 value, coordinates have 2 (with and extra for number of adjacent mines) e.t.c)
          String current = "";
          //While the file has any new lines in front of the current one, continue scanning
          while (scan.hasNextLine())  {
                //Declare the current line as a local string variable
                String newLine = scan.nextLine().trim();
                //Ignore any empty lines or any lines commented out with a "//" at the beginning
                if (newLine.length() !=0 && !newLine.startsWith("//"))  {
                    //It the line begins with "[" and ends with "]", it is a switch, and the command variable will be modified accordingly
                    if (newLine.startsWith("[") && newLine.endsWith("]")) {
                        //If the switch contains the word "Cells"
                        if (newLine.contains("Cells")) {
                        	//Scan the next line using a new Scanner object and use a delimiter for commas
                            Scanner scanLine = new Scanner(scan.nextLine().trim()).useDelimiter(",");    
                        	//Scan the number of cells from the saveGame file
                            CELLS = scanLine.nextInt();
                            //
                            System.out.println(CELLS);
                            //If the number of cells is 28
                            if (CELLS == 28) {
                            	//Change the box width to 15;
                            	BOX_WIDTH = 15;
                            //Otherwise
                            } else {
                            	//Change the box width to 20
                            	BOX_WIDTH = 20;
                            }
                            //Instantiate the tiles array using the number of cells as it's length and width
                            tiles = new Tile[CELLS][CELLS];
                            //Close the local scanner
                            scanLine.close();
                        //If the switch contains the word "Score"
                        }   else if (newLine.contains("Score"))  {  
                        	//Scan the next line using a new Scanner object and use a delimiter for commas
                            Scanner scanLine = new Scanner(scan.nextLine().trim()).useDelimiter(",");    
                            //Scan the score from the saveGame file
                            score = scanLine.nextInt();
                            //Close the local scanner
                            scanLine.close();
                        //If the switch contains the word "Flags"
                        }  else if (newLine.contains("Flags"))  { 
                        	//Scan the next line using a new Scanner object and use a delimiter for commas
                            Scanner scanLine = new Scanner(scan.nextLine().trim()).useDelimiter(","); 
                            //Scan the number of flags available to the player
                            flags = scanLine.nextInt();
                            //Close the local scanner
                            scanLine.close();
                        //If the switch contains the word "EmptySpace"
                        }  else if (newLine.contains("EmptySpace"))  {
                        	//Scan the next line using a new Scanner object and use a delimiter for commas
                            Scanner scanLine = new Scanner(scan.nextLine().trim()).useDelimiter(",");   
                          //Scan the tile's x postion
                            int i = scanLine.nextInt();
                            //Scan the tile's y position
                            int j = scanLine.nextInt();
                            //Instantiate the tile using the x and y coordinates
                            tiles[i][j] = new Tile(i,j,BOX_WIDTH); 
                            //Set that tile as an empty space
                            tiles[i][j].setMine(false);
                            //Close the local scanner
                            scanLine.close();
                        //If the switch contains the word "PressButton"
                        } else if (newLine.contains("PressButton")) {
                        	//Scan the next line using a new Scanner object and use a delimiter for commas
                            Scanner scanLine = new Scanner(scan.nextLine().trim()).useDelimiter(",");
                          //Scan the tile's x position
                            int i = scanLine.nextInt();
                            //Scan the tile's y position
                            int j = scanLine.nextInt();
                            //Scan the tile's adjacent mines
                            int saveAdjacentMines = scanLine.nextInt();
                            //Set this tile as pressed
                            tiles[i][j].setButtonPressed();
                            //Get the pressed button to show off how many adjacent mines there are, by first calling the getTile method using the x and y positions as parameters, and then setting the tile's adjacentMines using the saveAdjacentMines variable as a parameter
                            tiles[i][j].setAdjacentMines(saveAdjacentMines);
                            //Close the local scanner
                            scanLine.close();
                        //If the switch contains the word "FlagTile"
                        }  else if (newLine.contains("FlagTile")) {   
                        	//Scan the next line using a new Scanner object and use a delimiter for commas
                            Scanner scanLine = new Scanner(scan.nextLine().trim()).useDelimiter(",");    
                          //Scan the tile's x position
                            int i = scanLine.nextInt();
                            //Scan the tile's y position
                            int j = scanLine.nextInt();
                            //Show any flags placed by the user, by first calling the getTile method using the x and y positions as parameters, and then setting the tile's setFlagged method to true
                            tiles[i][j].setFlagged();
                            //Close the local scanner
                            scanLine.close();
                        //If the switch contains the word "SetMine"
                        }  else if (newLine.contains("SetMine"))  { 
                        	//Scan the next line using a new Scanner object and use a delimiter for commas
                            Scanner scanLine = new Scanner(scan.nextLine().trim()).useDelimiter(",");    
                            //Scan the tile's x position
                            int i = scanLine.nextInt();
                            //Scan the tile's y position
                            int j = scanLine.nextInt();
                            //Instantiate the tile using the x and y coordinates
                            tiles[i][j] = new Tile(i,j,BOX_WIDTH); 
                          	//Set the exact tile coordinate as a mine
                            tiles[i][j].setMine(true);	
                            //Close the local scanner
                            scanLine.close();
                        }
                        //Once one of these "IF" conditions have been met, break out of this iteration and proceed to the next one
                        continue;
                    }
                } 
          }   
          //Close the scanner in charge of the file
          scan.close(); 
      //If a FileNotFoundException is found
      }  catch (FileNotFoundException f)  {
          //Print the following message to console
          System.out.println("SaveGame not found. Start a new one");
      }
  }
  
  public void deleteSaveFile() {
	  //boolean for finding and deleting a file according to file path
      boolean deletion = new File("JavaMineSweeperSaveGame").delete();
      //If the boolean indicates that deletion is successful
      if (deletion)  {
    	  //Print message to console
          System.out.println("Deletion successful");
      //If deletion is unsuccessful
      } else if (!deletion)  {
    	  //Print message to console
          System.out.println("Failed");    
      }
  }
  
    private class MouseGridListener extends MouseAdapter {
    	@Override
    	public void mouseClicked(MouseEvent e)  {       
        //The X and Y position of what will be the target cell
    	int xpos, ypos;
    	//Set the x position as the x position of the mouse event
        xpos = e.getX();
        //Set the y position as the y position of the mouse event
        ypos = e.getY();
        //Get the cell x position by dividing the mouse event x by the box width
        xpos = xpos / BOX_WIDTH;
        //Get the cell y position by dividing the mouse event y by the box width
        ypos = ypos / BOX_WIDTH;
        
        //Only perform if the tile's x and y positions are less than the length of cells
        if (xpos < CELLS && ypos < CELLS)  {
            //Sets the tile which the user clicks on as a local variable to process
            Tile selectCell = tiles[xpos][ypos];
            //If the left mouse button is clicked
            if(e.getModifiers() == MouseEvent.BUTTON1_MASK)  {
                //If the tile hasn't been pressed already, check it's nearby tiles, then set the button pressed. Then replace it's former self with it's new state
                if (selectCell.getButtonPressed() == false && selectCell.getFlagged() == false)  {
                    //Call the clearAdjacent method using the current tile as the parameter, clearing any adjacent tiles
                    clearAdjacent(selectCell);
                    //Reduce the score by 10
                    deductScore(10);
                    //Set the tile to it's pressed state
                    selectCell.setButtonPressed(); 
                    //Set the cell in the tiles array so that it is in the same state as the local selectCell variable
                    tiles[xpos][ypos] = selectCell;
                    //If the tile is a mine, detonate all mines in the grid, repaint the grid and call the "Game Over" dialog box 
                    if (selectCell.getIsMine() == true)   {
                       //Detonate all mines in the grid
                        detonateAllMines();
                        //Repaint the whole grid
                        jContPane.repaint();
                        //Call the gameOver() method
                        gameOver();
                    } else {
                    	//Check if all empty spaces have been pressed
                    	checkGameComplete();
                    }
                }
                
            //If the right mouse button is clicked
            } else if(e.getModifiers() == MouseEvent.BUTTON3_MASK)  {
               //If the current cell hasn't been clicked
            	if (!selectCell.getButtonPressed()) { 
            	   //If the number of flags hasn't been expired and the flag has already been placed on the tile, 
            		if (flags > 0 || (flags == 0 && selectCell.getFlagged() == true))  {
            			//Set the selected cell as flagged
            			selectCell.setFlagged();
            			//Change the game's flag count
            			changeFlagCount(selectCell);
            		}
               }
            } 
        } 
        //Repaint the content pane
        jContPane.repaint();
    }
  }
    
    //GETTER FUNCTIONS
    
    public static Tile getTile(int i, int j)  {
        //Returns the tile indicated by x position(i) and y position(j)
        return tiles[i][j];
    }
    
    public int getCells() {
        //Returns the number of cells
        return CELLS;
    }
    
    public int getFlags()  {
        //Returns the number of flags
        return flags;
    }
    
    public int getScore()  {
        //Return the score
        return score;
    }
}
