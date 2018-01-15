package domain;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Company.
 */
public class Company  implements Serializable{

	private String name;
	private int price;
	private Player belongTo;
	
	/**
	 * Instantiates a new company.
	 *
	 * @param name the name
	 * @param price the price
	 */
	public Company(String name,int price) {
		setName(name);
		setPrice(price);
		setBelongTo(null);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Gets the belong to.
	 *
	 * @return the belong to
	 */
	public Player getBelongTo() {
		return belongTo;
	}

	/**
	 * Sets the belong to.
	 *
	 * @param belongTo the new belong to
	 */
	public void setBelongTo(Player belongTo) {
		this.belongTo = belongTo;
	}
	
}
