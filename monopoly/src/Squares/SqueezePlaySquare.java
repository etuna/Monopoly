package Squares;

import java.awt.Color;
import java.util.ArrayList;

import domain.*;

public class SqueezePlaySquare extends Square implements SquareFunc {
	
	Main m;
	Game game;
	Board board;
	ArrayList<Player> players;
	Player current_player;

	public SqueezePlaySquare(String name, String title, Asset asset, String color) {
		super(name, title, asset, color);
		m = Main.m;
		game = m.game;
		board = game.board;
		players = (ArrayList<Player>) game.getPlayers();
		// TODO Auto-generated constructor stub
	}
	
	public void _func() {
		current_player = game.getCurrentPlayer();
		current_player.setRolled(false);
		int total_dieFaceValue= 0;
		board.roll2Dice();
		total_dieFaceValue = board.getFaceTotal();
		
		if(total_dieFaceValue>=5 && total_dieFaceValue<=9) {
			int total_num_players = players.size();
			current_player.setBalance(current_player.getBalance()+total_num_players*50);
			for(Player p : players) {
				if(p != current_player) {
					p.setBalance(p.getBalance()-50);
				}
			}
			
		}else if( (total_dieFaceValue == 3) ||(total_dieFaceValue == 4) ||(total_dieFaceValue == 10) ||(total_dieFaceValue == 11)) {
			int total_num_players = players.size();
			current_player.setBalance(current_player.getBalance()+total_num_players*100);
			for(Player p : players) {
				if(p != current_player) {
					p.setBalance(p.getBalance()-100);
				}
			}
		}else {
			int total_num_players = players.size();
			current_player.setBalance(current_player.getBalance()+total_num_players*200);
			for(Player p : players) {
				if(p != current_player) {
					p.setBalance(p.getBalance()-200);
				}
			}
		}
		
		
		
		
		
	}

}
