package com.myPackage.minesweeper.Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ContentPane extends JPanel{
	
	//BufferedImage for background image
	private BufferedImage bgImage;
	
	public ContentPane(){
		//Set the border of this content pane to black
		setBorder(BorderFactory.createLineBorder(Color.black));
		//Try statement
		try {
			//Set the background image as the "SeaMines" image in hte resources folder
			bgImage = ImageIO.read(ContentPane.class.getResource("/images/SeaMines.jpg"));
		//If an IOException is caught
		} catch (IOException e) {
			//Print the exception's stack trace
			e.printStackTrace();
		}
		
	}
	
	@Override
    public void paintComponent(Graphics g) {
		//Call paintComponent of superclass
        super.paintComponent(g);       
        //Draw the background image over the whole content pane
        g.drawImage(bgImage, 0, 0, getParent().getWidth(), getParent().getHeight(), null);
    }
    
}
