package domain;

import java.io.Serializable;

public class PositionHandler implements Serializable {

	public static PositionHandler pos_handler;
	public Main m;
	public Game game;
	public Board board;

	public PositionHandler() {
		m = Main.m;
		game = m.game;

	}

	public static PositionHandler getInstance() {
		if (pos_handler == null) {
			pos_handler = new PositionHandler();
		}
		return pos_handler;
	}

	public PositionPair findPosition(Player p, int dieFaceValue) {
		Main m = Main.m;
		int layer = p.getPosition().layer_position;
		int square = p.getPosition().square_position;
		int move_direction = p.move_direction;

		if (move_direction == 1) {

			if (layer == 0) {
				layer = 0;
				square = (square + dieFaceValue)%56;
				PositionPair newPos = new PositionPair(layer,square);
				return newPos;

			} else if (layer == 1) {
				layer = 1;
				if((square + dieFaceValue) >39) {
					m.game.bank.payment_system.pay(m.game.currentPlayer, 200);
				}
				square = (square + dieFaceValue) % 40;
				PositionPair newPos = new PositionPair(layer,square);
				return newPos;

			} else {
				layer = 2;
				square = (square + dieFaceValue) % 24;
				PositionPair newPos = new PositionPair(layer,square);
				return newPos;

			}

		} else {
			if (layer == 0) {
				layer = 0;
				square = (square - dieFaceValue) % 56;
				if(square<0) {
					square += 56;
				}
				PositionPair newPos = new PositionPair(layer,square);
				return newPos;

			} else if (layer == 1) {
				layer = 1;
				square = (square - dieFaceValue) % 40;
				if(square<0) {
					square += 40;
				}
				PositionPair newPos = new PositionPair(layer,square);
				return newPos;
				

			} else {
				layer = 2;
				square = (square - dieFaceValue) % 24;
				if(square<0) {
					square += 24;
				}
				PositionPair newPos = new PositionPair(layer,square);
				return newPos;

			}
		}
	}

	public void setBoard(Board b) {
		this.board = b;
	}

}
