package events;

import java.io.*;

import domain.Game;
import domain.Main;

public class SaveSystem extends Systm{

	private final static int systemType = Systm.SAVE;
	
	@Override
	public void process(Evnt e) {
		SaveEvent ee = (SaveEvent)e;
		String path = ee.path;
		Main.m.game.scCurrentPlayerIndex = Main.gUI.current_player;
		try {
			FileOutputStream fOut = new FileOutputStream(path);
			ObjectOutputStream oOut = new ObjectOutputStream(fOut);
			oOut.writeObject(Main.m);
			oOut.close();
			fOut.close();
			System.out.println("saved?");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	
	public SaveSystem() {
		super(Systm.SAVE);
	}

/*	@Override
	public int getSystemType() {
		return systemType;
		
	}*/

}
