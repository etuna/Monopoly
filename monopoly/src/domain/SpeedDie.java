package domain;

import java.io.Serializable;
import java.util.Random;

import events.Evnt;

public class SpeedDie implements Serializable {
	
	Random random;
	public int face_value = 1;
	
	public SpeedDie() {
		random = new Random();
	}
	
	public int _roll() {
		
		int value;
		value = random.nextInt(6)+1;
		face_value = value;
		if(face_value == 6) {
			Game.getInstance().eventManager.notify(new Evnt(Evnt.MRMONOPOLY));
		}
		//5 : BUS
		//6 : MR.MONOPOLY
		
		return value;
	}

}
