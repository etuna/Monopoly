package events;

import java.io.*;

import domain.Board;
import domain.Game;
import domain.Main;
import domain.PaymentSystem;
import gui.GameUI;

public class LoadSystem extends Systm{
//	private final static int systemType = Systm.LOAD;

	
	public void process2(Evnt e) {
		LoadEvent ee = (LoadEvent)e;
		String path = ee.path;
		try {
			FileInputStream fIn = new FileInputStream(path);
			ObjectInputStream oIn = new ObjectInputStream(fIn);
			try {
				Main.game = (Game) oIn.readObject();
				Game.game = Main.game;
				Game.game.board = Main.game.board;
				Game.game.bank = Main.game.bank;
				Board.board = Main.game.board;
				Main.game.bank.board = Main.game.board;
				PaymentSystem.payment_system = Main.game.bank.payment_system;
				PaymentSystem.payment_system.bank = Main.game.bank;
				
				System.out.println("loaded?");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			oIn.close();
			fIn.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Main.gUI.current_player = Main.game.getCurrentPlayerIndex();
		
	}
	
	@Override
	public void process(Evnt e) {
		LoadEvent ee = (LoadEvent)e;
		String path = ee.path;
		try {
			FileInputStream fIn = new FileInputStream(path);
			ObjectInputStream oIn = new ObjectInputStream(fIn);
			try {
				Main.m = (Main) oIn.readObject();
				
				Main.game = Main.m.game;
				Main.gUI = Main.m.gUI;
				Main.dimension_handler = Main.m.dimension_handler;
				
				
				
				
				Game.game = Main.m.game;
				Game.game.board = Main.m.game.board;
				Game.game.bank = Main.m.game.bank;
				
				Board.board = Main.m.game.board;
				
				Main.game.bank.board = Main.game.board;
				PaymentSystem.payment_system = Main.game.bank.payment_system;
				PaymentSystem.payment_system.bank = Main.game.bank;
				
				System.out.println("loaded?");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			oIn.close();
			fIn.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Main.m.gUI =  new GameUI();
		Main.m.gUI.setMain(Main.m);
		Main.m.gUI._conf();
		
		Main.m.gUI.current_player = Main.game.getCurrentPlayerIndex();
		Main.m.gUI.repaint();
		
	}

	
	public LoadSystem() {
		super(Systm.LOAD);
	}
	/*@Override
	public int getSystemType() {
		return systemType;
	}*/
	

}
