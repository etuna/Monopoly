package domain;

import java.io.Serializable;

import Squares.*;

public class SquareFinder implements Serializable {
	Game game;
	
	public SquareFinder() {
		
	}
	
	// 0 outer , 1 middle, 2 inner 
	public Square findSquare(Player p) {
		Square ret_square = null;
		
		int layer = p.getPosition().layer_position;
		int square = p.getPosition().square_position;
		
		if(layer == 0) {
			ret_square = game.board.outerLayer.get(square);
		} else if(layer == 1) {
			ret_square = game.board.middleLayer.get(square);
		}else {
			ret_square = game.board.innerLayer.get(square);
		}
		
		return ret_square;
	}
	
	public void setGame(Game g) {
		game = g;
	}

}
