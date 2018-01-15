package domain;

import java.io.Serializable;

import javax.swing.JPanel;

public class Bobby implements Serializable{
	
	public static final int HAPPY = 0;
	public static final int SAD = 1;
	public static final int ANGRY = 2;
	public static final int NEUTRAL = 3;
	
	public static Bobby bobby;
	public int state;
	public int timer=0;
	
	
	public synchronized int getTimer() {
		return timer;
	}



	public synchronized void setTimer(int timer) {
		this.timer = timer;
	}
	public synchronized void incrementTimer() {
		this.timer++;
	}



	public Bobby() {
		// TODO Auto-generated constructor stub
		
		state = NEUTRAL; //initial state of bot
		
		
	}
	
	
	
	public synchronized int setState(int newState) {
		state = newState;
		return state;
	}
	
	public synchronized int getState () {
		return state;
	}


	public static Bobby getInstance() {
		// TODO Auto-generated method stub
		if(bobby == null) {
			bobby = new Bobby();
		}
		return bobby;
	}
	
}
