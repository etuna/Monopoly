package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Animatory extends JPanel implements Runnable{
	
	
	
	private int posX = 0, posY = 0, sizeX = 200, sizeY = 100;
	private DField field;
	
	
	
	public Animatory() {
		
		field = new DField(sizeX, sizeY);
	}
	
	
	

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void paint(Graphics g){
		
	}
}
