package domain;

import java.io.Serializable;

/**
 * Abstract Card Class
 */
public abstract class Card  implements Serializable{

	public String name;
	public Player belongTo;
	public boolean taken;
	
	
	public Card(String name) {
		this.name = name;
		belongTo=null;
		taken=false;
	}


	
	
}
