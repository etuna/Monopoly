package domain;

import java.awt.Dimension;
import java.io.Serializable;

public class DimensionHandler implements Serializable {

	Dimension[] inner_layer_squares = { new Dimension(410, 425), new Dimension(350, 425), new Dimension(315, 425),
			new Dimension(285, 425), new Dimension(250, 425), new Dimension(215, 425), new Dimension(155, 425),
			new Dimension(155, 380), new Dimension(155, 345), new Dimension(155, 310), new Dimension(155, 280),
			new Dimension(155, 245), new Dimension(155, 195), new Dimension(210, 195), new Dimension(245, 195),
			new Dimension(280, 195), new Dimension(315, 195), new Dimension(350, 195), new Dimension(410, 195),
			new Dimension(410, 240), new Dimension(410, 275), new Dimension(410, 305), new Dimension(410, 340),
			new Dimension(410, 375) };
	Dimension[] middle_layer_squares = { new Dimension(490, 490), new Dimension(420, 490), new Dimension(385, 490),
			new Dimension(350, 490), new Dimension(315, 490), new Dimension(280, 490), new Dimension(245, 490),
			new Dimension(215, 490), new Dimension(180, 490), new Dimension(145, 490), new Dimension(100, 490),
			new Dimension(100, 445), new Dimension(100, 410), new Dimension(100, 375), new Dimension(100, 345),
			new Dimension(100, 310), new Dimension(100, 275), new Dimension(100, 240), new Dimension(100, 210),
			new Dimension(100, 175), new Dimension(100, 120), new Dimension(145, 120), new Dimension(180, 120),
			new Dimension(215, 120), new Dimension(250, 120), new Dimension(285, 120), new Dimension(320, 120),
			new Dimension(355, 120), new Dimension(385, 120), new Dimension(420, 120), new Dimension(470, 120),
			new Dimension(470, 175), new Dimension(470, 210), new Dimension(470, 240), new Dimension(470, 275),
			new Dimension(470, 310), new Dimension(470, 345), new Dimension(470, 380), new Dimension(470, 415),
			new Dimension(470, 445) };
	Dimension[] outer_layer_squares = { new Dimension(550, 560), new Dimension(500, 560), new Dimension(465, 560),
			new Dimension(430, 560), new Dimension(395, 560), new Dimension(360, 560), new Dimension(325, 560),
			new Dimension(290, 560), new Dimension(255, 560), new Dimension(220, 560), new Dimension(185, 560),
			new Dimension(150, 560), new Dimension(115, 560), new Dimension(80, 560), new Dimension(45, 560),
			new Dimension(45, 525), new Dimension(45, 490), new Dimension(45, 455), new Dimension(45, 420),
			new Dimension(45, 385), new Dimension(45, 350), new Dimension(45, 315), new Dimension(45, 280),
			new Dimension(45, 245), new Dimension(45, 210), new Dimension(45, 175), new Dimension(45, 140),
			new Dimension(45, 105), new Dimension(45, 70), new Dimension(80, 70), new Dimension(115, 70),
			new Dimension(150, 70), new Dimension(185, 70), new Dimension(220, 70), new Dimension(255, 70),
			new Dimension(290, 70), new Dimension(325, 70), new Dimension(360, 70), new Dimension(395, 70),
			new Dimension(430, 70),new Dimension(465, 70),new Dimension(500, 70),new Dimension(550, 70),
			new Dimension(550, 105),new Dimension(550, 140),new Dimension(560, 175),new Dimension(560, 210),
			new Dimension(560, 245),new Dimension(560, 280),new Dimension(560, 315),new Dimension(560, 350),
			new Dimension(560, 385),new Dimension(560, 420),new Dimension(560, 455),new Dimension(560, 490),
			new Dimension(560, 525)};
	public final String name = "Dimmy Anderson";
	public static DimensionHandler dimmy;

	public DimensionHandler() {

	}

	public DimensionHandler getInstance() {
		if (dimmy == null) {
			dimmy = new DimensionHandler();
		}
		return dimmy;
	}

	public Dimension findDimension(Player p) {

		Dimension dimension = new Dimension(0, 0);

		int layer = p.getPosition().layer_position;
		int square = p.getPosition().square_position;
		dimension = _lookUp(layer, square);

		return dimension;
	}
	public Dimension findDimension(int layer, int square) {

		Dimension dimension = new Dimension(0, 0);

		dimension = _lookUp(layer, square);

		return dimension;
	}

	public Dimension _lookUp(int layer, int square) {
		Dimension dimension = null;
		
		if(layer == 0) {
			dimension = outer_layer_squares[square];
			
		}else if(layer == 1) {
			dimension = middle_layer_squares[square];
		}else {
			dimension = inner_layer_squares[square];
		}
		
		return dimension;
	}

}
