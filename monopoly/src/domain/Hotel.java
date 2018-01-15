package domain;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Hotel.
 */
public class Hotel implements Serializable {
	
	/** The rent price. */
	private int build_price,rent_price;
	
	/**
	 * Instantiates a new hotel.
	 *
	 * @param build_price the build price
	 * @param rent_price the rent price
	 */
	public Hotel(int build_price,int rent_price) {
		setBuild_price(build_price);
		setRent_price(rent_price);
	}
	
	/**
	 * Gets the builds the price.
	 *
	 * @return the builds the price
	 */
	public int getBuild_price() {
		return build_price;
	}

	/**
	 * Sets the builds the price.
	 *
	 * @param build_price the new builds the price
	 */
	public void setBuild_price(int build_price) {
		this.build_price = build_price;
	}

	/**
	 * Gets the rent price.
	 *
	 * @return the rent price
	 */
	public int getRent_price() {
		return rent_price;
	}

	/**
	 * Sets the rent price.
	 *
	 * @param rent_price the new rent price
	 */
	public void setRent_price(int rent_price) {
		this.rent_price = rent_price;
	}
}
