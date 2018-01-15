/*
 * 
 */
package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Squares.*;

public class Board implements Serializable {

	public String name = "myboard";
	public SquareFinder sf;

	public List<ArrayList<Square>> squares;

	public List<Piece> pieces;

	public List<Square> innerLayer;

	public List<Square> middleLayer;

	public List<Square> outerLayer;

	public static Board board;

	/** The pos handler. */
	public PositionHandler pos_handler;

	/** The m. */
	public Main m;

	/** The game. */
	public Game game;

	/** The die 2. */
	private Die die0, die1, die2;

	/** The speed die. */
	public SpeedDie speedDie;

	/** The speed die result. */
	public int speedDie_result = 0;

	/** The dice. */
	public Die[] dice;

	public boolean squaresAreOK = false;

	/** The total face value roll dice. */
	private int totalFaceValue_rollDice;

	// Our singleton board object=======
	/**
	 * Gets the single instance of Board.
	 *
	 * @return board singleton instance
	 */
	public static Board getInstance() {
		if (board == null) {
			board = new Board();
		}
		return board;
	}
	// ==================================

	public Board() {
		// Get the board ASAP,then config
		sf = new SquareFinder();
	}
	// ==================================

	public void config_board() {
		m = Main.m;
		game = m.game;

		pos_handler = PositionHandler.getInstance();
		pos_handler.setBoard(this);

		dice = new Die[3];
		// Dice
		die0 = new Die();
		die1 = new Die();
		die2 = new Die();
		speedDie = new SpeedDie();
		dice[0] = die0;
		dice[1] = die1;
		dice[2] = die2;
		// Squares------

		squares = new ArrayList<ArrayList<Square>>(3);
		outerLayer = new ArrayList<Square>();
		middleLayer = new ArrayList<Square>();
		innerLayer = new ArrayList<Square>();
		squares.add((ArrayList<Square>) outerLayer);
		squares.add((ArrayList<Square>) middleLayer);
		squares.add((ArrayList<Square>) innerLayer);
		populateSquares();
	}

	// rollDice()====================
	/**
	 * Roll 2 dice.
	 *
	 * @modifies this.dice;
	 * @effects rolls the dice and updates the dice values
	 */
	public void roll2Dice() {
		int total = 0;
		for (int i = 0; i < 2; i++) {
			Die die = dice[i];
			die.roll();
			total += die.getFaceValue();
		}
		speedDie_result = speedDie._roll();
		if (speedDie_result == 5) {
			game.getCurrentPlayer().busIcon_move = true;
		} else if (speedDie_result == 6) {
			game.getCurrentPlayer().mrMonopoly_move = true;
		} else {
			total += speedDie_result;
		}
		totalFaceValue_rollDice = total;

		if (dice[0].getFaceValue() == dice[1].getFaceValue() && game.currentPlayer.inJail) {
			game.currentPlayer.getOutOfJail();
		}

		if (dice[0].getFaceValue() == dice[1].getFaceValue() && game.currentPlayer.oneMoreRoll == false) {
			game.currentPlayer.oneMoreRoll = true;
		} else {
			game.currentPlayer.oneMoreRoll = false;
		}

	}
	// ==============================

	public void roll3Dice() {
		int total = 0;
		for (Die die : dice) {
			die.roll();
			total += die.getFaceValue();
		}
		speedDie_result = speedDie._roll();
		totalFaceValue_rollDice = total;
	}
	// ==============================

	public void refreshDice() {
		for (Die die : dice) {
			die.setFaceValue(6);
		}
	}

	/**
	 * Mr monopoly move.
	 * 
	 * @requires the current player rolling a mr monopoly
	 * @modifies the current player position
	 * @effects the game state and the board
	 * 
	 */

	public void mrMonopolyMove() {
		Player current_player = game.getCurrentPlayer();
		int layer = current_player.getPosition().layer_position;
		int square = current_player.getPosition().square_position;
		int direction = current_player.move_direction;
		int tempSquare = (square + 1) % squares.get(layer).size();
		int retSquare = 0;
		boolean found = false;

		if (direction == 1) {
			while (tempSquare != square) {
				Asset a = squares.get(layer).get(tempSquare).getAsset();

				if ((squares.get(layer).get(tempSquare) instanceof CustomSquare) && a.getBelongTo() == null) {
					square = tempSquare;
					found = true;
				} else {
					tempSquare = (tempSquare + 1) % squares.get(layer).size();
				}
			}

		} else {

			while (tempSquare != square) {
				Asset a = squares.get(layer).get(tempSquare).getAsset();

				if ((squares.get(layer).get(tempSquare) instanceof CustomSquare) && a.getBelongTo() == null) {
					square = tempSquare;
					found = true;
				} else {
					tempSquare = (tempSquare + 1) % squares.get(layer).size();
					if (tempSquare < 0) {
						tempSquare = tempSquare + squares.get(layer).size();
					}
				}
			}
		}

		if (tempSquare == square && !found) {
			tempSquare++;
			while (!(squares.get(layer).get(tempSquare) instanceof CustomSquare)) {
				tempSquare++;
			}
			square = tempSquare;
		}
		if (current_player.oneMoreRoll != true) {
			current_player.second_move();
		}
		current_player.setPosition(new PositionPair(layer, square));
		current_player.mrMonopoly_move = false;
	}

	/**
	 * Bus move.
	 *
	 * @param moveType
	 *            the move type
	 * 
	 * @requires the current player rolling the bus
	 * @modifies the current player position
	 * @effects the game state and the board
	 */
	public void busMove(int moveType) {

		if (moveType == 0) {// Travel Voucher
			SquareFunction();
		} else { // Move to chance or community chest

			Player current_player = game.getCurrentPlayer();
			int layer = current_player.getPosition().layer_position;
			int square = current_player.getPosition().square_position;
			int direction = current_player.move_direction;
			int tempSquare = square;

			if (direction == 1) {

				while (!(squares.get(layer).get(tempSquare) instanceof ChanceSquare)
						&& !(squares.get(layer).get(tempSquare) instanceof CommunityChestSquare)) {
					tempSquare = (tempSquare + 1) % squares.get(layer).size();
					System.out.println("burada takılıyor");
				}
				square = tempSquare;
				SquareFunction();
			} else {

				while (!(squares.get(layer).get(tempSquare) instanceof ChanceSquare)
						&& !(squares.get(layer).get(tempSquare) instanceof CommunityChestSquare)) {
					tempSquare = (tempSquare - 1) % squares.get(layer).size();
					if (tempSquare < 0) {
						tempSquare = tempSquare + squares.get(layer).size();
					}
				}
				square = tempSquare;
				SquareFunction();
			}
			if (!current_player.oneMoreRoll) {
				current_player.second_move();
				SquareFunction();
			}
			current_player.setPosition(new PositionPair(layer, square));
			SquareFunction();
		}
	}

	/**
	 * Gets the face total.
	 *
	 * @return the face total
	 * 
	 * @requires the dice to be instantiated
	 * @modifies the dice
	 * @effects the dice instances in the board
	 */
	// getFaceTotal()================
	public int getFaceTotal() {
		return totalFaceValue_rollDice;
	}
	// ==============================

	/**
	 * Move player.
	 *
	 * @param p
	 *            the player to move
	 * @param totalFaceValue
	 *            the total face value
	 * 
	 * @requires the current player not having moved in the current turn
	 * @modifies the current player position
	 * @effects the game state and the board
	 */
	// movePlayer() =================
	public void movePlayer(Player p, int totalFaceValue) {

		boolean regMoved = p.regularMoved;

		if (p.inJail) {
			return;
		}

		if (!regMoved) {
			
			if(!p.transitMoved) {
			PositionPair new_position = pos_handler.findPosition(p, totalFaceValue);
			//
			p.setPosition(new_position);}
			p.transitMoved=false;
			SquareFunction();
			p.regularMoved = true;
			System.out.println("ONE MORE ROLL????" + p.oneMoreRoll);
			if (p.oneMoreRoll == true) {
				p.oneMoreTurn();
				p.regularMoved = false;
				System.out.println("??sssasa*??");
			}
		} else {
			System.out.println("YOU HAVE ALREADY TAKEN YOUR REGULAR MOVE");
		}
		if (speedDie_result == 5) {

		} else if (speedDie_result == 6) {

		} else // Do nothing for now
		{

		}

	}
	// ==============================

	/**
	 * Mr Monopoly move.
	 * 
	 * @requires the current player rolling mr monopoly
	 * @modifies the current player position
	 * @effects the game state and the board
	 * 
	 */

	public void mrMonopoly_move() {
		Player current_player = game.getCurrentPlayer();
		if (current_player.mrMonopoly_move) {
			mrMonopolyMove();
		}

	}

	/**
	 * Bus move.
	 *
	 * @param moveType
	 *            the move type
	 * 
	 * @requires the current player rolling the bus
	 * @modifies the current player position
	 * @effects the game state and the board
	 */
	public void bus_move(int moveType) {
		Player current_player = game.getCurrentPlayer();
		if (current_player.busIcon_move) {
			busMove(moveType);
		}
	}

	public List<ArrayList<Square>> getSquares() {
		return squares;
	}

	public void SquareFunction() {
		Square s = game.board.sf.findSquare(game.currentPlayer);
		s._func();
	}

	public int[] checkTransitPassing(Player p, int totalFaceValue) {
		ArrayList<Integer> outerTransits = new ArrayList<Integer>();
		ArrayList<Integer> middleTransits = new ArrayList<Integer>();
		ArrayList<Integer> innerTransits = new ArrayList<Integer>();

		int[] coord = new int[2];

		outerTransits.add(7);
		outerTransits.add(35);

		middleTransits.add(5);
		middleTransits.add(15);
		middleTransits.add(25);
		middleTransits.add(35);

		innerTransits.add(9);
		innerTransits.add(21);

		int square = p.getPosition().square_position;
		int layer = p.getPosition().layer_position;

		if (layer == 0) {
			int res = square + totalFaceValue;

			int check1 = (7 - square) % 56;
			if (check1 < 0) {
				check1 += 56;
			}

			int check2 = (35 - square) % 56;
			if (check2 < 0) {
				check2 += 56;
			}

			if (totalFaceValue % 2 == 0 && ((totalFaceValue > check1) || (totalFaceValue > check2))) {

				if (totalFaceValue > check1) {
					coord[0] = 0;
					coord[1] = 7;
				} else {
					coord[0] = 0;
					coord[1] = 35;
				}

				return coord;
			} else {
				return null;
			}

		} else if (layer == 1) {

			int res = square + totalFaceValue;

			int check1 = (5 - square) % 40;
			if (check1 < 0) {
				check1 += 40;
			}

			int check2 = (15 - square) % 40;
			if (check2 < 0) {
				check2 += 40;
			}
			int check3 = (25 - square) % 40;
			if (check3 < 0) {
				check3 += 40;
			}
			int check4 = (35 - square) % 40;
			if (check4 < 0) {
				check4 += 40;
			}

			if (totalFaceValue % 2 == 0 && ((totalFaceValue > check1) || (totalFaceValue > check2)
					|| (totalFaceValue > check3) || (totalFaceValue > check4))) {

				if (totalFaceValue > check1) {
					coord[0] = 1;
					coord[1] = 5;
				} else if (totalFaceValue > check2) {
					coord[0] = 1;
					coord[1] = 15;
				} else if (totalFaceValue > check3) {
					coord[0] = 1;
					coord[1] = 25;
				} else {
					coord[0] = 1;
					coord[1] = 35;
				}

				return coord;
			} else {
				return null;
			}

		} else {

			int res = square + totalFaceValue;

			int check1 = (9 - square) % 24;
			if (check1 < 0) {
				check1 += 24;
			}

			int check2 = (21 - square) % 24;
			if (check2 < 0) {
				check2 += 24;
			}

			if (totalFaceValue % 2 == 0 && ((totalFaceValue > check1) || (totalFaceValue > check2))) {

				if (totalFaceValue > check1) {
					coord[0] = 2;
					coord[1] = 9;
				} else {
					coord[0] = 2;
					coord[1] = 21;
				}

				return coord;
			} else {
				return null;
			}

		}
	}

	public void findLocationThen(Player p, int[] coord, int totalFaceValue) {

		
		if(!p.regularMoved) {
		int layer = p.getPosition().layer_position;
		int square = p.getPosition().square_position;

		int layerC = coord[0];
		int squareC = coord[1];

		if (layerC == 0) {

			if (squareC == 7) {

				int sim_pos = (square + totalFaceValue) % 56;
				int diff = sim_pos - 7;
				layer = 1;
				square = (5 + diff) % 40;
				p.setPosition(new PositionPair(layer, square));

			} else {
				int sim_pos = (square + totalFaceValue) % 56;
				int diff = sim_pos - 35;
				layer = 1;
				square = (25 + diff) % 40;
				p.setPosition(new PositionPair(layer, square));
			}

		} else if (layerC == 1) {

			if (squareC == 5) {
				int sim_pos = (square + totalFaceValue) % 40;
				int diff = sim_pos - 5;
				layer = 0;
				square = (7 + diff) % 56;
				p.setPosition(new PositionPair(layer, square));

			} else if (squareC == 15) {
				int sim_pos = (square + totalFaceValue) % 40;
				int diff = sim_pos - 15;
				layer = 2;
				square = (9 + diff) % 24;
				p.setPosition(new PositionPair(layer, square));

			} else if (squareC == 25) {
				int sim_pos = (square + totalFaceValue) % 40;
				int diff = sim_pos - 25;
				layer = 0;
				square = (35 + diff) % 56;
				p.setPosition(new PositionPair(layer, square));

			} else {

				int sim_pos = (square + totalFaceValue) % 40;
				int diff = (sim_pos - 35) % 40;
				if (diff < 0) {
					diff += 40;
				}
				layer = 2;
				square = (9 + diff) % 24;
				p.setPosition(new PositionPair(layer, square));
			}

		} else {

			if (squareC == 9) {
				int sim_pos = (square + totalFaceValue) % 24;
				int diff = sim_pos - 9;
				layer = 1;
				square = (15 + diff) % 40;
				p.setPosition(new PositionPair(layer, square));

			} else {
				int sim_pos = (square + totalFaceValue) % 24;
				int diff = sim_pos - 21;
				layer = 1;
				square = (35 + diff) % 40;
				p.setPosition(new PositionPair(layer, square));
			}

		}
		p.transitMoved=true;
		p.move();
		} else {
			m.gUI.info.setText(m.gUI.info.getText()+"\nYou have already moved ...");
		}
	}

	public boolean populateSquares() {

		// outer layer squares

		Square o1 = new SubwaySquare("SubwaySquare", "SubwaySquare", null, null);
		Square o2 = new CustomSquare("LakeStreet", "Lake Street", LAKESTREET, "pink");
		Square o3 = new CommunityChestSquare("CommunityChestSquare", "Community Chest Square", null, null);
		((CommunityChestSquare) o3).setCommunityChestCard(communitychestcard0);
		
		
		Square o4 = new CustomSquare("NicolletAvenue", "Nicollet Avenue", NICOLLETAVENUE, "pink");
		Square o5 = new CustomSquare("HennepinAvenue", "Hennepin Avenue", HENNEPINAVENUE, "pink");
		Square o6 = new BusTicketSquare("BusTicket", "Bus Ticket", null, null);
		Square o7 = new CheckerCabSquare("CheckerCabCo.", "Checker Cab Co.", null, null);
		Square o8 = new ReadingRailroadSquare("ReadingRailroad", "Reading Railroad", null, null);
		Square o9 = new CustomSquare("EsplanadeAvenue", "Esplanade Avenue", ESPLANADEAVENUE, "lightgreen");
		Square o10 = new CustomSquare("CanalStreet", "Canal Street", CANALSTREET, "lightgreen");
		Square o11 = new ChanceSquare("ChanceSquare", "Chance Square", null, null);
		((ChanceSquare) o11).setChanceCard(chancecard0);
		
		Square o12 = new CableCompanySquare("CableCompany", "Cable Company", null, null);
		Square o13 = new CustomSquare("MagazineStreet", "Magazine Street", MAGAZINESTREET, "lightgreen");
		Square o14 = new CustomSquare("BourbonStreet", "Bourbon Street", BOURBONSTREET, "lightgreen");
		Square o15 = new HollandTunnelSquare("HollandTunnel", "Holland Tunnel", null, null);
		Square o16 = new AuctionSquare("AuctionSquare", "Auction Square", null, null);
		Square o17 = new CustomSquare("KatyFreeway", "Katy Freeway", KATYFREEWAY, "lightyellow");
		Square o18 = new CustomSquare("WestheimerRoad", "Westheimer Road", WESTHEIMERROAD, "lightyellow");
		Square o19 = new InternetProviderSquare("InternetProviderSquare", "Internet Service Provider", null, null);
		Square o20 = new CustomSquare("KirbyDrive", "Kirby Drive", KIRBYDRIVE, "lightyellow");
		Square o21 = new CustomSquare("CullenBoulevard", "Cullen Boulevard", CULLENBOULEVARD, "lightyellow");
		Square o22 = new ChanceSquare("ChanceSquare", "Chance Square", null, null);
		((ChanceSquare) o22).setChanceCard(chancecard1);
		
		Square o23 = new BlackWhiteCabSquare("BlackAndWhiteCabCo", "Black and White Cab Co.", null, null);
		Square o24 = new CustomSquare("DekalbAvenue", "Dekalb Avenue", DEKALBAVENUE, "weirdgreen");
		Square o25 = new CommunityChestSquare("CommunityChest", "Community Chest", null, null);
		((CommunityChestSquare) o25).setCommunityChestCard(communitychestcard1);
		
		Square o26 = new CustomSquare("AndrewYoungIntlBoulevard", "Andrew Young Intl Boulevard",
				ANDREWYOUNGINTLBOULEVARD, "weirdgreen");
		Square o27 = new CustomSquare("DecaturStreet", "Decatur Street", DECATURSTREET, "weirdgreen");
		Square o28 = new CustomSquare("PeachTreeStreet", "Peach Tree Street", PEACHTREESTREET, "weirdgreen");
		Square o29 = new PayDaySquare("PayDay", "Pay Day", null, null);
		Square o30 = new CustomSquare("RandolphStreet", "Randolph Street", RANDOLPHSTREET, "darkpurple");
		Square o31 = new ChanceSquare("ChanceSquare", "Chance Square", null, null);
		((ChanceSquare) o31).setChanceCard(chancecard2);
		
		Square o32 = new CustomSquare("LakeShoreDrive", "Lake Shore Drive", LAKESHOREDRIVE, "darkpurple");
		Square o33 = new CustomSquare("WackerDrive", "Wacker Drive", WACKERDRIVE, "darkpurple");
		Square o34 = new CustomSquare("MichiganAvenue", "Michigan Avenue", MICHIGANAVENUE, "darkpurple");
		Square o35 = new YellowCabSquare("YellowCabCo.", "Yellow Cab Co.", null, null);
		Square o36 = new BORailroadSquare("B&ORailroad", "B&O Railroad", null, null);
		Square o37 = new CommunityChestSquare("CommunityChest", "Community Chest", null, null);
		((CommunityChestSquare) o37).setCommunityChestCard(communitychestcard2);
		
		Square o38 = new CustomSquare("SouthTemple", "South Temple", SOUTHTEMPLE, "darkyellow");
		Square o39 = new CustomSquare("WestTemple", "West Temple", WESTTEMPLE, "darkyellow");
		Square o40 = new TrashCollectorSquare("TrashCollector", "Trash Collector", null, null);
		Square o41 = new CustomSquare("NorthTemple", "North Temple", NORTHTEMPLE, "darkyellow");
		Square o42 = new CustomSquare("TempleSquare", "Temple Square", TEMPLESQUARE, "darkyellow");
		Square o43 = new GoToJailSquare("GoToJail", "Go To Jail", null, null);
		Square o44 = new CustomSquare("SouthStreet", "South Street", SOUTHSTREET, "cream");
		Square o45 = new CustomSquare("BroadStreet", "Broad Street", BROADSTREET, "cream");
		Square o46 = new CustomSquare("WalnutStreet", "Walnut Street", WALNETSTREET, "cream");
		Square o47 = new CommunityChestSquare("CommunityChest", "Community Chest", null, null);
		((CommunityChestSquare) o47).setCommunityChestCard(communitychestcard3);
		
		Square o48 = new CustomSquare("MarketStreet", "Market Street", MARKETSTREET, "cream");
		Square o49 = new BusTicketSquare("BusTicketSquare", "Bus Ticket Square", null, null);
		Square o50 = new SewageSystemSquare("SewageSystem", "Sewage System", null, null);
		Square o51 = new UTECabSquare("UTECabCo.", "UTE Cab Co.", null, null);
		Square o52 = new BirthdayGiftSquare("BirthdayGiftSquare", "Birthday Gift Square", null, null);
		Square o53 = new CustomSquare("MulhollandDrive", "Mulholland Drive", MULHOLLANDRIVE, "darkred");
		Square o54 = new CustomSquare("VenturaBoulevard", "Ventura Boulevard", VENTURABOULEVARD, "darkred");
		Square o55 = new ChanceSquare("ChanceSquare", "Chance Square", null, null);
		((ChanceSquare) o55).setChanceCard(chancecard3);
		
		Square o56 = new CustomSquare("RodeoDrive", "Rodeo Drive", RODEODRIVE, "darkred");
		//4 chance square outer
		//4 community chest outer 
		
		outerLayer.add(o1);
		outerLayer.add(o2);
		outerLayer.add(o3);
		outerLayer.add(o4);
		outerLayer.add(o5);
		outerLayer.add(o6);
		outerLayer.add(o7);
		outerLayer.add(o8);
		outerLayer.add(o9);
		outerLayer.add(o10);
		outerLayer.add(o11);
		outerLayer.add(o12);
		outerLayer.add(o13);
		outerLayer.add(o14);
		outerLayer.add(o15);
		outerLayer.add(o16);
		outerLayer.add(o17);
		outerLayer.add(o18);
		outerLayer.add(o19);
		outerLayer.add(o20);
		outerLayer.add(o21);
		outerLayer.add(o22);
		outerLayer.add(o23);
		outerLayer.add(o24);
		outerLayer.add(o25);
		outerLayer.add(o26);
		outerLayer.add(o27);
		outerLayer.add(o28);
		outerLayer.add(o29);
		outerLayer.add(o30);
		outerLayer.add(o31);
		outerLayer.add(o32);
		outerLayer.add(o33);
		outerLayer.add(o34);
		outerLayer.add(o35);
		outerLayer.add(o36);
		outerLayer.add(o37);
		outerLayer.add(o38);
		outerLayer.add(o39);
		outerLayer.add(o40);
		outerLayer.add(o41);
		outerLayer.add(o42);
		outerLayer.add(o43);
		outerLayer.add(o44);
		outerLayer.add(o45);
		outerLayer.add(o46);
		outerLayer.add(o47);
		outerLayer.add(o48);
		outerLayer.add(o49);
		outerLayer.add(o50);
		outerLayer.add(o51);
		outerLayer.add(o52);
		outerLayer.add(o53);
		outerLayer.add(o54);
		outerLayer.add(o55);
		outerLayer.add(o56);

		Square m1 = new GoSquare("GoSquare", "Go Square", null, null);
		Square m2 = new CustomSquare("MediterraneanAvenue", "MediterraneanAvenue", MEDITERRANEANAVENUE, "purple");
		Square m3 = new CommunityChestSquare("CommunityChest", "Community Chest", null, null);
		((CommunityChestSquare) m3).setCommunityChestCard(communitychestcard4);
		
		Square m4 = new CustomSquare("BalticAvenue", "Baltic Avenue", BALTICAVENUE, "purple");
		Square m5 = new IncomeTaxSquare("IncomeTaxSquare", "Income Tax Square", null, null);
		Square m6 = new TransitStationSquare("TransitStationSquare", "Transit Station", null, null);
		Square m7 = new CustomSquare("OrientalAvenue", "Oriental Avenue", ORIENTALAVENUE, "lightpurple");
		Square m8 = new ChanceSquare("ChanceSquare", "Chance Square", null, null);
		((ChanceSquare) m8).setChanceCard(chancecard4);
		
		Square m9 = new CustomSquare("VermontAvenue", "Vermont Avenue", VERMONTAVENUE, "lightpurple");
		Square m10 = new CustomSquare("ConnecticutAvenue", "Connecticut Avenue", CONNECTICUTAVENUE, "lightpurple");
		Square m11 = new JailSquare("JailSquare", "Jail Square", null, null);
		Square m12 = new CustomSquare("StCharlesPlace", "St Charles Place", STCHARLESPLACE, "darkpink");
		Square m13 = new ElectricCompanySquare("ElectricCompanySquare", "Electric Company Square", null, null);
		Square m14 = new CustomSquare("StatesAvenue", "StatesAvenue", STATESAVENUE, "darkpink");
		Square m15 = new CustomSquare("VirginiaAvenue", "Virginia Avenue", VIRGINIAAVENUE, "darkpink");
		Square m16 = new PennsylvaniaRailroadSquare("PennsylvaniaRailroadSquare", "Pennsylvania Railroad Square", null,
				null);
		Square m17 = new CustomSquare("StJamesPlace", "St James Place", STJAMESPLACE, "orange");
		Square m18 = new CommunityChestSquare("CommunityChestSquare", "Community Chest Square", null, null);
		((CommunityChestSquare) m18).setCommunityChestCard(communitychestcard5);
		
		Square m19 = new CustomSquare("TennesseeAvenue", "Tennessee Avenue", TENNESSEEAVENUE, "orange");
		Square m20 = new CustomSquare("NewYorkAvenue", "New York Avenue", NEWYORKAVENUE, "orange");
		Square m21 = new FreeParkingSquare("FreeParkingSquare", "Free Parking Square", null, null);
		Square m22 = new CustomSquare("KentuckyAvenue", "Kentucky Avenue", KENTUCKYAVENUE, "red");
		Square m23 = new ChanceSquare("ChanceSquare", "Chance Square", null, null);
		((ChanceSquare) m23).setChanceCard(chancecard5);
		
		Square m24 = new CustomSquare("IndianaAvenue", "Indiana Avenue", INDIANAAVENUE, "red");
		Square m25 = new CustomSquare("IllinoisAvenue", "Illinois Avenue", ILLINOISAVENUE, "red");
		Square m26 = new TransitStationSquare("TransitStationSquare", "Transit Station Square", null, null);
		Square m27 = new CustomSquare("AtlanticAvenue", "Atlantic Avenue", ATLANTICAVENUE, "yellow");
		Square m28 = new CustomSquare("VentnorAvenue", "Ventnor Avenue", VENTNORAVENUE, "yellow");
		Square m29 = new WaterWorksSquare("WaterWorksSquare", "Water Works Square", null, null);
		Square m30 = new CustomSquare("MarvinGardens", "Marvin Gardens", MARVINGARDENS, "yellow");
		Square m31 = new RollThreeSquare("RollThreeSquare", "Roll Three Square", null, null);
		Square m32 = new CustomSquare("PacificAvenue", "Pacific Avenue", PACIFICAVENUE, "green");
		Square m33 = new CustomSquare("NorthCarolinaAvenue", "North Carolina Avenue", NORTHCAROLINAAVENUE, "green");
		Square m34 = new CommunityChestSquare("CommunityChestSquare", "Community Chest Square", null, null);
		((CommunityChestSquare) m34).setCommunityChestCard(communitychestcard6);
		
		Square m35 = new CustomSquare("PennsylvaniaAvenue", "Pennsylvania Avenue", PENNSYLVANIAAVENUE, "green");
		Square m36 = new ShortLineSquare("ShortLineSquare", "Short Line Square", null, null);
		Square m37 = new ChanceSquare("ChanceSquare", "Chance Square", null, null);
		((ChanceSquare) m37).setChanceCard(chancecard6);
		
		Square m38 = new CustomSquare("ParkPlace", "Park Place", PARKPLACE, "blue");
		Square m39 = new LuxuryTaxSquare("LuxuryTaxSquare", "Luxury Tax Square", null, null);

		Square m40 = new CustomSquare("Boardwalk", "Boardwalk", BOARDWALK, "blue");
		//3 chance square middle
		//3 community chest middle
		
		((JailSquare) m11)._conf(m);
		((LuxuryTaxSquare) m39)._conf(m);

		middleLayer.add(m1);
		middleLayer.add(m2);
		middleLayer.add(m3);
		middleLayer.add(m4);
		middleLayer.add(m5);
		middleLayer.add(m6);
		middleLayer.add(m7);
		middleLayer.add(m8);
		middleLayer.add(m9);
		middleLayer.add(m10);
		middleLayer.add(m11);
		middleLayer.add(m12);
		middleLayer.add(m13);
		middleLayer.add(m14);
		middleLayer.add(m15);
		middleLayer.add(m16);
		middleLayer.add(m17);
		middleLayer.add(m18);
		middleLayer.add(m19);
		middleLayer.add(m20);
		middleLayer.add(m21);
		middleLayer.add(m22);
		middleLayer.add(m23);
		middleLayer.add(m24);
		middleLayer.add(m25);
		middleLayer.add(m26);
		middleLayer.add(m27);
		middleLayer.add(m28);
		middleLayer.add(m29);
		middleLayer.add(m30);
		middleLayer.add(m31);
		middleLayer.add(m32);
		middleLayer.add(m33);
		middleLayer.add(m34);
		middleLayer.add(m35);
		middleLayer.add(m36);
		middleLayer.add(m37);
		middleLayer.add(m38);
		middleLayer.add(m39);
		middleLayer.add(m40);

		Square i1 = new SqueezePlaySquare("SqueezePlay", "Squeeze Play", null, null);
		Square i2 = new CustomSquare("TheEmbarcadero", "The Embarcadero", THEEMBARCADERO, "white");
		Square i3 = new CustomSquare("FishermansWharf", "Fisherman's Wharf", FISHERMANSWHARF, "white");
		Square i4 = new TelephoneCompanySquare("TelephoneCompany", "Telephone Company", null, null);
		Square i5 = new CommunityChestSquare("CommunityChestSquare", "Community Chest Square", null, null);
		((CommunityChestSquare) i5).setCommunityChestCard(communitychestcard7);
		
		Square i6 = new CustomSquare("BeaconStreet", "Beacon Street", BEACONSTREET, "black");
		Square i7 = new BonusSquare("BonusSquare", "Bonus Square", null, null);
		Square i8 = new CustomSquare("BoylstonStreet", "Boylston Street", BOYLSTONSTREET, "black");
		Square i9 = new CustomSquare("NewburyStreet", "Newbury Street", NEWBURYSTREET, "black");
		Square i10 = new TransitStationSquare("TransitStationSquare", "Transit Station", null, null);
		Square i11 = new CustomSquare("FifthAvenue", "Fifth Avenue", FIFTHAVENUE, "gray");
		Square i12 = new CustomSquare("MadisonAvenue", "Madison Avenue", MADISONAVENUE, "gray");
		Square i13 = new StockExchangeSquare("StockExchange", "Stock Exchange", null, null);
		Square i14 = new CustomSquare("WallStreet", "Wall Street", WALLSTREET, "gray");
		Square i15 = new TaxRefundSquare("TaxRefund", "Tax Refund", null, null);
		Square i16 = new GasCompanySquare("GasCompany", "GasCompany", null, null);
		Square i17 = new ChanceSquare("ChanceSquare", "Chance Square", null, null);
		((ChanceSquare) i17).setChanceCard(chancecard7);
		
		Square i18 = new CustomSquare("FloridaAvenue", "Florida Avenue", FLORIDAAVENUE, "darkorange");
		Square i19 = new HollandTunnelSquare("HollandTunnel", "Holland Tunnel", null, null);
		Square i20 = new CustomSquare("MiamiAvenue", "Miami Avenue", MIAMIAVENUE, "darkorange");
		Square i21 = new CustomSquare("BiscayneAvenue", "Biscayne Avenue", BISCAYNEAVENUE, "darkorange");
		Square i22 = new TransitStationSquare("TransitStation", "TransitStation", null, null);
		Square i23 = new ReverseDirectionSquare("ReverseDirection", "Reverse Direction", null, null);
		Square i24 = new CustomSquare("LombardStreet", "Lombard Street", LOMBARDSTREET, "white");
		//1 chance square inner
		//1 community chest inner
		innerLayer.add(i1);
		innerLayer.add(i2);
		innerLayer.add(i3);
		innerLayer.add(i4);
		innerLayer.add(i5);
		innerLayer.add(i6);
		innerLayer.add(i7);
		innerLayer.add(i8);
		innerLayer.add(i9);
		innerLayer.add(i10);
		innerLayer.add(i11);
		innerLayer.add(i12);
		innerLayer.add(i13);
		innerLayer.add(i14);
		innerLayer.add(i15);
		innerLayer.add(i16);
		innerLayer.add(i17);
		innerLayer.add(i18);
		innerLayer.add(i19);
		innerLayer.add(i20);
		innerLayer.add(i21);
		innerLayer.add(i22);
		innerLayer.add(i23);
		innerLayer.add(i24);

		// -----
		squaresAreOK = true;

		return true;
	}

	/**
	 * Populate components.
	 *
	 * @param pieces
	 *            the pieces
	 * @modifies this.pieces
	 * @effects sets this.pieces to pieces
	 */

	public void populateComponents(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}

	public void populateAssetsToTheSystem() {
		ArrayList<Asset> allAssets = new ArrayList<Asset>();

		allAssets.add(LAKESTREET);
		allAssets.add(NICOLLETAVENUE);
		allAssets.add(HENNEPINAVENUE);
		allAssets.add(ESPLANADEAVENUE);
		allAssets.add(CANALSTREET);
		allAssets.add(MAGAZINESTREET);
		allAssets.add(BOURBONSTREET);
		allAssets.add(KATYFREEWAY);
		allAssets.add(WESTHEIMERROAD);
		allAssets.add(KIRBYDRIVE);
		allAssets.add(CULLENBOULEVARD);
		allAssets.add(DEKALBAVENUE);
		allAssets.add(ANDREWYOUNGINTLBOULEVARD);
		allAssets.add(DECATURSTREET);
		allAssets.add(PEACHTREESTREET);
		allAssets.add(RANDOLPHSTREET);
		allAssets.add(LAKESHOREDRIVE);
		allAssets.add(WACKERDRIVE);
		allAssets.add(MICHIGANAVENUE);
		allAssets.add(SOUTHTEMPLE);
		allAssets.add(WESTTEMPLE);
		allAssets.add(NORTHTEMPLE);
		allAssets.add(TEMPLESQUARE);
		allAssets.add(SOUTHSTREET);
		allAssets.add(BROADSTREET);
		allAssets.add(WALNETSTREET);
		allAssets.add(MARKETSTREET);
		allAssets.add(MULHOLLANDRIVE);
		allAssets.add(VENTURABOULEVARD);
		allAssets.add(RODEODRIVE);

		// --
		allAssets.add(MEDITERRANEANAVENUE);
		allAssets.add(BALTICAVENUE);
		allAssets.add(ORIENTALAVENUE);
		allAssets.add(VERMONTAVENUE);
		allAssets.add(CONNECTICUTAVENUE);
		allAssets.add(STCHARLESPLACE);
		allAssets.add(STATESAVENUE);
		allAssets.add(VIRGINIAAVENUE);
		allAssets.add(STJAMESPLACE);
		allAssets.add(TENNESSEEAVENUE);
		allAssets.add(NEWYORKAVENUE);
		allAssets.add(KENTUCKYAVENUE);
		allAssets.add(INDIANAAVENUE);
		allAssets.add(ILLINOISAVENUE);
		allAssets.add(ATLANTICAVENUE);
		allAssets.add(VENTNORAVENUE);
		allAssets.add(MARVINGARDENS);
		allAssets.add(PACIFICAVENUE);
		allAssets.add(NORTHCAROLINAAVENUE);
		allAssets.add(PENNSYLVANIAAVENUE);
		allAssets.add(PARKPLACE);
		allAssets.add(BOARDWALK);
		// --

		allAssets.add(THEEMBARCADERO);
		allAssets.add(FISHERMANSWHARF);
		allAssets.add(BEACONSTREET);
		allAssets.add(BOYLSTONSTREET);
		allAssets.add(NEWBURYSTREET);
		allAssets.add(FIFTHAVENUE);
		allAssets.add(MADISONAVENUE);
		allAssets.add(WALLSTREET);
		allAssets.add(FLORIDAAVENUE);
		allAssets.add(MIAMIAVENUE);
		allAssets.add(BISCAYNEAVENUE);
		allAssets.add(LOMBARDSTREET);

		m.game.setAssets(allAssets);

	}

	
	
	public static ChanceCard chancecard0 = new ChanceCard("cc");
	public static ChanceCard chancecard1 = new ChanceCard("cc");
	public static ChanceCard chancecard2 = new ChanceCard("cc");
	public static ChanceCard chancecard3 = new ChanceCard("cc");
	public static ChanceCard chancecard4 = new ChanceCard("cc");
	public static ChanceCard chancecard5 = new ChanceCard("cc");
	public static ChanceCard chancecard6 = new ChanceCard("cc");
	public static ChanceCard chancecard7 = new ChanceCard("cc");
	
	public static CommunityChestCard communitychestcard0 = new CommunityChestCard("ccc");
	public static CommunityChestCard communitychestcard1 = new CommunityChestCard("ccc");
	public static CommunityChestCard communitychestcard2 = new CommunityChestCard("ccc");
	public static CommunityChestCard communitychestcard3 = new CommunityChestCard("ccc");
	public static CommunityChestCard communitychestcard4 = new CommunityChestCard("ccc");
	public static CommunityChestCard communitychestcard5 = new CommunityChestCard("ccc");
	public static CommunityChestCard communitychestcard6 = new CommunityChestCard("ccc");
	public static CommunityChestCard communitychestcard7 = new CommunityChestCard("ccc");
	
	public static Asset LAKESTREET = new Asset("LAKE STREET", 0, 0, "pink");
	public static Asset NICOLLETAVENUE = new Asset("NICOLLET AVENUE", 0, 0, "pink");
	public static Asset HENNEPINAVENUE = new Asset("HENNEPIN AVENUE", 0, 0, "pink");
	public static Asset ESPLANADEAVENUE = new Asset("ESPLANADE AVENUE", 0, 0, "lightgreen");
	public static Asset CANALSTREET = new Asset("CANALSTREET", 0, 0, "lightgreen");
	public static Asset MAGAZINESTREET = new Asset("MAGAZINESTREET", 0, 0, "lightgreen");
	public static Asset BOURBONSTREET = new Asset("BOURBON STREET", 0, 0, "lightgreen");
	public static Asset KATYFREEWAY = new Asset("KATY FREEWAY", 0, 0, "lightyellow");
	public static Asset WESTHEIMERROAD = new Asset("WESTHEIMER ROAD", 0, 0, "lightyellow");
	public static Asset KIRBYDRIVE = new Asset("KIRBY DRIVE", 0, 0, "lightyellow");
	public static Asset CULLENBOULEVARD = new Asset("CULLEN BOULEVARD", 0, 0, "lightyellow");
	public static Asset DEKALBAVENUE = new Asset("DEKALB AVENUE", 0, 0, "weirdgreen");
	public static Asset ANDREWYOUNGINTLBOULEVARD = new Asset("ANDREWYOUNGINTL BOULEVARD", 0, 0, "weirdgreen");
	public static Asset DECATURSTREET = new Asset("DECATUR STREET", 0, 0, "weirdgreen");
	public static Asset PEACHTREESTREET = new Asset("PEACHTREE STREET", 0, 0, "weirdgreen");
	public static Asset RANDOLPHSTREET = new Asset("RANDOLPH STREET", 0, 0, "darkpurple");
	public static Asset LAKESHOREDRIVE = new Asset("LAKESHORE DRIVE", 0, 0, "darkpurple");
	public static Asset WACKERDRIVE = new Asset("WACKER DRIVE", 0, 0, "darkpurple");
	public static Asset MICHIGANAVENUE = new Asset("MICHIGAN AVENUE", 0, 0, "darkpurple");
	public static Asset SOUTHTEMPLE = new Asset("SOUTH TEMPLE", 0, 0, "darkyellow");
	public static Asset WESTTEMPLE = new Asset("WEST TEMPLE", 0, 0, "darkyellow");
	public static Asset NORTHTEMPLE = new Asset("NORTH TEMPLE", 0, 0, "darkyellow");
	public static Asset TEMPLESQUARE = new Asset("TEMPLE SQUARE", 0, 0, "darkyellow");
	public static Asset SOUTHSTREET = new Asset("SOUTH STREET", 0, 0, "cream");
	public static Asset BROADSTREET = new Asset("BROAD STREET", 0, 0, "cream");
	public static Asset WALNETSTREET = new Asset("WALNET STREET", 0, 0, "cream");
	public static Asset MARKETSTREET = new Asset("MARKET STREET", 0, 0, "cream");
	public static Asset MULHOLLANDRIVE = new Asset("MULHOLLAN DRIVE", 0, 0, "darkred");
	public static Asset VENTURABOULEVARD = new Asset("VENTURA BOULEVARD", 0, 0, "darkred");
	public static Asset RODEODRIVE = new Asset("RODEO DRIVE", 0, 0, "darkred");
	// --
	public static Asset MEDITERRANEANAVENUE = new Asset("MEDITERRANEAN AVENUE", 100, 0, "purple");
	public static Asset BALTICAVENUE = new Asset("BALTIC AVENUE", 100, 0, "purple");
	public static Asset ORIENTALAVENUE = new Asset("ORIENTAL AVENUE", 100, 0, "lightpurple");
	public static Asset VERMONTAVENUE = new Asset("VERMONT AVENUE", 100, 0, "lightpurple");
	public static Asset CONNECTICUTAVENUE = new Asset("CONNECTICUT AVENUE", 100, 0, "lightpurple");
	public static Asset STCHARLESPLACE = new Asset("STCHARLES PLACE", 100, 0, "darkpink");
	public static Asset STATESAVENUE = new Asset("STATES AVENUE", 100, 0, "darkpink");
	public static Asset VIRGINIAAVENUE = new Asset("VIRGINIA AVENUE", 100, 0, "darkpink");
	public static Asset STJAMESPLACE = new Asset("STJAMES PLACE", 100, 0, "orange");
	public static Asset TENNESSEEAVENUE = new Asset("TENNESSEE AVENUE", 100, 0, "orange");
	public static Asset NEWYORKAVENUE = new Asset("NEWYORK AVENUE", 100, 0, "orange");
	public static Asset KENTUCKYAVENUE = new Asset("KENTUCKY AVENUE", 100, 0, "red");
	public static Asset INDIANAAVENUE = new Asset("INDIANA AVENUE", 100, 0, "red");
	public static Asset ILLINOISAVENUE = new Asset("ILLINOIS AVENUE", 100, 0, "red");
	public static Asset ATLANTICAVENUE = new Asset("ATLANTIC AVENUE", 100, 0, "yellow");
	public static Asset VENTNORAVENUE = new Asset("VENTNOR AVENUE", 100, 0, "yellow");
	public static Asset MARVINGARDENS = new Asset("MARVIN GARDENS", 100, 0, "yellow");
	public static Asset PACIFICAVENUE = new Asset("PACIFIC AVENUE", 100, 0, "green");
	public static Asset NORTHCAROLINAAVENUE = new Asset("NORTHCAROLINA AVENUE", 100, 0, "green");
	public static Asset PENNSYLVANIAAVENUE = new Asset("PENNSYLVANIAAVENUE", 100, 0, "green");
	public static Asset PARKPLACE = new Asset("PARKPLACE", 100, 0, "blue");
	public static Asset BOARDWALK = new Asset("BOARDWALK", 100, 0, "blue");
	// --
	public static Asset THEEMBARCADERO = new Asset("THE EMBARCADERO", 0, 0, "white");
	public static Asset FISHERMANSWHARF = new Asset("FISHERMANS WHARF", 0, 0, "white");
	public static Asset BEACONSTREET = new Asset("BEACON STREET", 0, 0, "black");
	public static Asset BOYLSTONSTREET = new Asset("BOYLSTON STREET", 0, 0, "black");
	public static Asset NEWBURYSTREET = new Asset("NEWBURY STREET", 0, 0, "black");
	public static Asset FIFTHAVENUE = new Asset("FIFTH AVENUE", 0, 0, "gray");
	public static Asset MADISONAVENUE = new Asset("MADISON AVENUE", 0, 0, "gray");
	public static Asset WALLSTREET = new Asset("WALLSTREET", 0, 0, "gray");
	public static Asset FLORIDAAVENUE = new Asset("FLORIDA AVENUE", 0, 0, "darkorange");
	public static Asset MIAMIAVENUE = new Asset("MIAMI AVENUE", 0, 0, "darkorange");
	public static Asset BISCAYNEAVENUE = new Asset("BISCAYNE AVENUE", 0, 0, "darkorange");
	public static Asset LOMBARDSTREET = new Asset("LOMBARDSTREET", 0, 0, "white");

}
