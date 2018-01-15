package domain;

import java.io.Serializable;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Die.
 */
public class Die  implements Serializable{
	
	// Variables====================
	private int faceValue;
	
	public Random random;
	// =============================

	/**
	 * Instantiates a new die.
	 */
	// Constructor==================
	public Die() {
		faceValue = 6;
		random = new Random();
	}
	// =============================

	/**
	 * Roll.
	 * @modifies face value
	 * @effects the die itself
	 */
	public void roll() {
		int random_number = random.nextInt(6) + 1;
		setFaceValue(random_number);
	}

	/**
	 * Gets the face value.
	 *
	 * @return the face value
	 */
	// Getters and Setters===========
	public int getFaceValue() {
		return faceValue;
	}

	/**
	 * Sets the face value.
	 *
	 * @param faceValue the new face value
	 */
	public void setFaceValue(int faceValue) {
		this.faceValue = faceValue;
	}
	// =============================
	

	public final static int MRMONOPOLY = 4;
	public final static int MRMONOPOLY2 = 5;
	public final static int BUS = 6;
}
