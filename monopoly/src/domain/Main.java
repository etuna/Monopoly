package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import events.*;
import gui.BoardGUI;
import gui.GameUI;
import gui.WindowHelpers;

public class Main implements Serializable{
	public static Main m;
	public static Game game = Game.getInstance();
	public static GameUI gUI;
	public static DimensionHandler dimension_handler;
	
	protected Object readResolve() {
	    return getInstance();
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dimension_handler = new DimensionHandler();

		//BoardGUI b = new BoardGUI();
		game = Game.getInstance();
		game.conf_game();
		
		WindowHelpers wh = new WindowHelpers();
		
		//wh.__init();
		//wh.setSize(1500,1500);
		System.out.println("asdddddd "+wh.getSize());
		
		gUI =  new GameUI();
		gUI.setMain(m);
		gUI._conf();
		
		/*
		m.game.currentPlayer.setPosition(new PositionPair(0,0));
		gUI.playerDimensions.set(game.getCurrentPlayerIndex(),
				dimension_handler.findDimension(m.game.getCurrentPlayer()));
		gUI.repaint();
		game.board.SquareFunction();
		*/
		
		
		/*
		game.board.rollDice();
		Player p =  new Player("asddee123");
		game.getPlayers().add(p);
		game.playerRolled();
		game.playerRolled();
		*/
		
		System.out.println(game.board.getFaceTotal());
		System.out.println(game.getPlayers().size());
		System.out.println(game.getPlayerRolled());
		
		
		//TEST
		//List<Player> players = new ArrayList<Player>();
		//players.add(new Player("asdf1"));
		//List<Asset> assets = new ArrayList<Asset>();
		//assets.add(new Asset("assetasdf", 33, 22));
		//game = new Game(players, assets);
		
		//test
		//SaveEvent se = new SaveEvent("./save.def");
		//LoadEvent le = new LoadEvent("./save.def");
		//game.eventManager.notify(le);
		
		System.out.println(game.board.getFaceTotal());
		System.out.println(game.getPlayers().size());
		System.out.println(game.getPlayerRolled());
		
		System.out.println(Board.board.getFaceTotal());
	}
	
	public static Main getInstance() {
		if (m == null) {
			m = new Main();
		}
		return m;
	}

	public void _config(ArrayList<Player> players, ArrayList<Asset> assets,ArrayList<Piece> pieces) {
		game.populateComponents(players, assets,pieces);
		game.setCurrentPlayer(players.get(0));
	}
	
}
