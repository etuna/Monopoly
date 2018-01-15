package domain;

import java.io.Serializable;

public class Piece  implements Serializable{

	private String name;
	private Player belongTo;
	private int position;

	// Constructor =================
	public Piece(String name) {
		setName(name);
		setBelongTo(null);
		setPosition(0);
	}
	// =============================

	// Getters and Setters =========
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(Player belongTo) {
		this.belongTo = belongTo;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	// =============================
}
