/*
 * 
 */
package domain;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Jail.
 */
public class Jail implements Serializable {
	
	/** The jail. */
	public static Jail jail;
	
	/** The prisoners. */
	public List<Player> prisoners;
	
	/**
	 * Instantiates a new jail.
	 */
	public Jail() {
		prisoners = new ArrayList<Player>();
	}
	
	/**
	 * Gets the single instance of Jail.
	 *
	 * @return single instance of Jail
	 */
	public static Jail getInstance() {
		if(jail == null) {
			jail = new Jail();
		}
		return jail;
	}
}
