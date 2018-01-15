package Squares;

import java.awt.Color;
import java.io.Serializable;

import domain.Asset;
import domain.Game;
import domain.PositionPair;


// TODO: Auto-generated Javadoc
/**
 * The Square.
 */
public abstract class Square implements Serializable, SquareFunc {

	/** The title. */
	// Variables====================
	private String name, title;
	
	/** The asset. */
	private Asset asset;
	
	/** The color color. */
	private Color color_color;
	
	/** The color. */
	private String color;
	// =============================

	/**
	 * Instantiates a new square.
	 *
	 * @param name the name
	 * @param title the title
	 * @param asset the asset
	 * @param color the color
	 */
	// OldConstructor ===============
	public Square(String name, String title, Asset asset, Color color) {
		setName(name);
		setTitle(title);
		setAsset(asset);
		// setColor(color_color);
	}
	// =============================

	/**
	 * Instantiates a new square.
	 *
	 * @param name the name
	 * @param title the title
	 * @param asset the asset
	 * @param color the color
	 */
	// New Constructor
	public Square(String name, String title, Asset asset, String color) {
		setName(name);
		setTitle(title);
		setAsset(asset);
		setColor(color);
	}
	// =============================

	/* (non-Javadoc)
	 * @see Squares.SquareFunc#_func()
	 */
	// _func()======================
	public void _func() {
		// Special functions of all the inherited classes
		if (asset != null) {
			//Game.game.eventManager.addSub(Evnt.BUY, new BuySystem());
		}
	}
	// =============================

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	// Getters and Setter ==========
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
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the asset.
	 *
	 * @return the asset
	 */
	public Asset getAsset() {
		return asset;
	}

	/**
	 * Sets the asset.
	 *
	 * @param asset the new asset
	 */
	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	// =============================

}
