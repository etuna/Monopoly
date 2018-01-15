package domain;

import java.io.Serializable;

public class PositionPair implements Serializable {

	public int layer_position;
	public int square_position;

	//Layer 0 : outer layer, 1:middle layer , 2:inner layer
	//In outer layer : 56 Squares
	//In middle layer : 40 squares
	// In inner layer : 24 squares
	public PositionPair(int layer_position, int square_position) {
		this.layer_position = layer_position;
		this.square_position = square_position;
	}
}
