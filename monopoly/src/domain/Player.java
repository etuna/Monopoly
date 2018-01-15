package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Squares.CustomSquare;
import Squares.Square;
import events.Evnt;

// TODO: Auto-generated Javadoc
/**
 * Each player is assigned a Player object which contains player info and state and methods for a player to interact with the game and the board.
 */
public class Player implements Serializable {

	// Variables ====================
	public Main m;
	public Game game;
	public Board board;
	private String name;
	private int balance, totalPath;
	private Piece piece;
	public PositionPair position;
	public Square onSquare;
	private boolean isPlaying;
	private List<Asset> assets;
	public List<Ticket> tickets;
	public List<Card> cards;
	public List<Object>ticketsNcards;
	public Bank bank;
	private boolean isRolled = false;
	public boolean inJail = false;
	public int numturn_inJail=0;
	private int totalFaceValue_rollDice;
	public int move_direction;
	public boolean mrMonopoly_move;
	public boolean busIcon_move;
	public boolean hasMoved;
	public boolean canRollDice;
	public SquareFinder sf;
	public  boolean buy_property_successful = false;
	public boolean turnEnded= false;
	public boolean regularMoved = false;
	public boolean oneMoreRoll = false;
	public boolean paidRent = false;
	public boolean transitMoved = false;
	public boolean subway = false;
	public boolean subwayMoved = true;
	public boolean bankrupt = false;
	// ==============================

	/**
	 * Instantiates a new player.
	 *
	 * @param name the name
	 */
	// Constructor===================
	public Player(String name) {
		m = Main.m;
		game = Game.game;
		board = Board.board;
		bank = Bank.bank;
		sf = board.sf;
		position = new PositionPair(1, 0); // 0 outer , 1 middle, 2 inner :: Other pos comp is square
		
		
		this.name = name;
		subway = false;
		move_direction = 1;
		mrMonopoly_move = false;
		busIcon_move = false;
		hasMoved = false;
		
		isRolled = false;
		canRollDice = false;
		balance = 3200;
		totalPath = 0;
		setPlaying(true);
		assets = new ArrayList<Asset>();
		tickets = new ArrayList<Ticket>();
		cards = new ArrayList<Card>();
		ticketsNcards = new ArrayList<Object>();
	}
	// ==============================

	/**
	 * Buy property.
	 *
	 * @param asset the asset to buy
	 * 
	 * @requires an asset to buy
	 * @modifies the player assets
	 * @effects the asset ownership
	 */
	// player actions================
	public void buyProperty(Asset asset) {
		attemptToBuy(asset);
	}

	/**
	 * Sell property.
	 *
	 * @param asset the asset to sell
	 * 
	 * @requires an asset to sell
	 * @modifies the player assets
	 * @effects the asset ownership
	 */
	public void sellProperty(Asset asset) {
		attemptToSell(asset);
	}

	/**
	 * Attempt to buy.
	 *
	 * @param asset the asset to buy
	 */
	public void attemptToBuy(Asset asset) {
		bank.receiveBuyAttempt(this, asset);
	}

	/**
	 * Attempt to sell.
	 *
	 * @param asset the asset to sell
	 */
	public void attemptToSell(Asset asset) {
		bank.receiveSellAttempt(this, asset);
	}
	// ==============================

	/**
	 * Roll dice.
	 * @requires the dice to be instantiated beforehand
	 * @modifies the face values of the dice
	 * @effects the board
	 */
	// rollDice======================
	public void rollDice(int num) {
		if (!isRolled) {
			System.out.println(
					"curr ind:" + game.getCurrentPlayerIndex() + "  curr player:" + game.getCurrentPlayer().name);
			if(num==2) {
			board.roll2Dice();
			
			}
			else {
				board.roll3Dice();
			}
			totalFaceValue_rollDice = board.getFaceTotal();
			isRolled = true;
		} else {
			System.out.println("You have rolled dice once. You can't roll one more time.");
		}
	}
	// ==============================

	/**
	 * Move.
	 * @requires -
	 * @modifies the player position
	 * @effects the player
	 */
	// move()========================
	public void move() {
		
		totalFaceValue_rollDice = board.getFaceTotal();
		if (this.isRolled) {
			if (hasMoved) {
				System.out.println("You have already moved.");
				return;
			} else {
				board.movePlayer(this, totalFaceValue_rollDice);
				totalPath += totalFaceValue_rollDice;
				
				if(mrMonopoly_move == false && busIcon_move==false) {
					if(oneMoreRoll) {
						
					}else {
				hasMoved = true;}
				}
			}
		} else {
			System.out.println("You have not rolled dice yet.");
		}
	}
	// ==============================


	public boolean endTurn() {
		if (hasMoved) {
			
			Square s = m.game.board.sf.findSquare(this);
			if(s instanceof CustomSquare) {
				Asset a = s.getAsset();
				boolean bought = a.isBought;
				Player owner = a.getBelongTo();
				
				if((bought && owner != this) && !paidRent) {
					paidRent=false;
				} else {
					paidRent= true;
				}
				
			} else {
				paidRent=true;
			}
			
			if(paidRent) {
			game.setCurrentPlayerIndex((game.getCurrentPlayerIndex() + 1) % game.getPlayers().size());
			game.setCurrentPlayer(game.getPlayers().get(game.getCurrentPlayerIndex()));
			game.getPlayers().get(game.getCurrentPlayerIndex()).takeTurn();
			paidRent=false;
			return true;}
			else {
				System.out.println("Pay the rent");
				m.gUI.info.setText(m.gUI.info.getText()+"\nPay the rent before you end the turn\n");
				return false;
			}
		} else {
			System.out.println("You have not taken your turn.");
			return false;
		}
	}

	/**
	 * Second move.
	 * 
	 * @requires the player to have already made a move on the current turn
	 * @modifies the current player's position
	 * @effects the game state
	 */
	// second_move()
	public void second_move() {
		// GO SOMEWHERE 
		
		if(mrMonopoly_move== true || busIcon_move==true) {
			mrMonopoly_move= false;
			busIcon_move = false;
			hasMoved = true;
		}
		
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
	 * Gets the balance.
	 *
	 * @return the balance
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * Sets the balance.
	 *
	 * @param balance the new balance
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}

	/**
	 * Gets the total path.
	 *
	 * @return the total path
	 */
	public int getTotalPath() {
		return totalPath;
	}

	/**
	 * Sets the total path.
	 *
	 * @param totalPath the new total path
	 */
	public void setTotalPath(int totalPath) {
		this.totalPath = totalPath;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public PositionPair getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(PositionPair position) {
		this.position = position;
	}

	/**
	 * Gets the current square.
	 *
	 * @return the current square
	 */
	public Square getCurrentSquare() {
		return Board.board.squares.get(position.layer_position).get(position.square_position);
	}

	/**
	 * Gets the assets.
	 *
	 * @return the assets
	 */
	public List<Asset> getAssets() {
		return assets;
	}

	/**
	 * Sets the assets.
	 *
	 * @param assets the new assets
	 */
	public void setAssets(ArrayList<Asset> assets) {
		this.assets = assets;
	}

	/**
	 * Checks if is rolled.
	 *
	 * @return true, if is rolled
	 */
	public boolean isRolled() {
		return isRolled;
	}

	
	public void payMoneyToPlayer(Player p,int amount) {
		setBalance(getBalance()-amount);
		p.setBalance(p.getBalance()+amount);
		
		int cp = m.game.getCurrentPlayerIndex();
		int p_ind = m.gUI.players.indexOf(p);
		
		m.gUI.playerBalances.get(cp).setText("$ "+getBalance());
		m.gUI.playerBalances.get(p_ind).setText("$ "+p.getBalance());
		checkBankrupt(amount);
		
		
	}
	
	
	/**
	 * Sets the rolled.
	 *
	 * @param isRolled the new rolled
	 */
	public void setRolled(boolean isRolled) {
		this.isRolled = isRolled;
	}

	/**
	 * Gets the total face value roll dice.
	 *
	 * @return the total face value roll dice
	 */
	public int getTotalFaceValue_rollDice() {
		return totalFaceValue_rollDice;
	}

	/**
	 * Sets the total face value roll dice.
	 *
	 * @param totalFaceValue_rollDice the new total face value roll dice
	 */
	public void setTotalFaceValue_rollDice(int totalFaceValue_rollDice) {
		this.totalFaceValue_rollDice = totalFaceValue_rollDice;
	}

	/**
	 * Builds the building.
	 *
	 * @param buildingType the building type
	 * @param a the asset to build on
	 * 
	 * @requires the asset to build on to be buildable by the requesting player
	 * @modifies the asset building state
	 * @effects the board
	 */
	public void buildBuilding(int buildingType, Asset a) {
		// 0 house, 1 hotel, 2 skyscraper
		if (canBuild(a)) {

			if (buildingType == HOUSE) {
				if (a.getBuildingStatus() < 4) {
					if (this.getBalance() > 10) {
						if (bank.houses.size() > 1) {
							a.setBuildingStatus(a.getBuildingStatus() + 1);
							game.bank.payment_system.acceptPay(this, 10);
						} else {
							System.out.println("SORRY,BANK HAS NO ENOUGH HOUSE!");
						}
					} else {
						System.out.println("NOT ENOUGH BALANCE!");
					}
				} else {
					System.out.println("YOU CAN'T BUILD MORE HOUSE!");
				}

			} else if (buildingType == HOTEL) {
				if (a.getBuildingStatus() == 4) {
					if (this.getBalance() > 20) {
						if (bank.hotels.size() > 1) {
							a.setBuildingStatus(5);
							game.bank.payment_system.acceptPay(this, 20);
						} else {
							System.out.println("SORRY,BANK HAS NO ENOUGH HOTELS!");
						}
					} else {
						System.out.println("NOT ENOUGH BALANCE!");
					}
				} else {
					System.out.println("YOU CAN'T BUILD HOUSE!");
				}

			} else if (buildingType == SKYSCRAPER) {
				if (a.getBuildingStatus() == 5) {
					if (this.getBalance() > 50) {
						if (bank.skyscrapers.size() > 1) {
							a.setBuildingStatus(6);
							game.bank.payment_system.acceptPay(this, 50);
						} else {
							System.out.println("SORRY,BANK HAS NO ENOUGH SKYSCRAPERS");
						}
					} else {
						System.out.println("SORRY,NOT ENOUGH BALANCE!");
					}
				} else {
					System.out.println("YOU CANT BUILD SKYSCRAPER!");
				}

			} else {
				System.out.println("ERROR! Wrong building type : " + buildingType);
			}
		} else {
			System.out.println("You can't build on " + a.getName());
		}

	}

	/**
	 * Can build.
	 *
	 * @param a the a
	 * @return true, if successful
	 */
	public boolean canBuild(Asset a) {
		int sameColorCounter = 0;
		String color = a.getColor();
		ArrayList<Asset> allAssets;
		allAssets = m.game.getAssets();
			System.out.println(m.game.getAssets().size()+"---all assets size in player");
		for (Asset asset : allAssets) {
			String col = asset.getColor();

			if (col.equals(color) && asset.getBelongTo() == this) {
				sameColorCounter++;
			}
		}

		if (sameColorCounter >= 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if is playing.
	 *
	 * @return true, if is playing
	 */
	public boolean isPlaying() {
		return isPlaying;
	}

	/**
	 * Sets the playing.
	 *
	 * @param isPlaying the new playing
	 */
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	/**
	 * Config player.
	 *
	 * @param piece the piece
	 * 
	 * @requires a piece
	 * @modifies the player's piece
	 * @effects the player
	 */
	public void config_player(Piece piece) {
		this.piece = piece;
	}

	/**
	 * Go to jail.
	 *
	 * @return true, if successful
	 * 
	 * @requires the player to not be in jail
	 * @modifies the current player
	 * @effects the game state
	 */
	public boolean goToJail() {
		Jail j = m.game.jail;
		j.prisoners.add(this);
		this.inJail = true;

		PositionPair newPos = new PositionPair(1, 10);
		setPosition(newPos);

		isPlaying = false;
		Game.getInstance().eventManager.notify(new Evnt(Evnt.JAIL));
		// So what?

		return true;
	}

	/**
	 * Sell building.
	 *
	 * @param a the asset which owns the to be sold building
	 * @param buildingType the building type
	 * 
	 * @requires the player to have already made a move on the current turn
	 * @modifies the current player
	 * @effects the game state
	 */
	public void sellBuilding(Asset a, int buildingType) {

		if (this == game.getCurrentPlayer()) {
			if (buildingType == 2) {
				if (a.getBuildingStatus() == 6) {
					if (bank.hotels.size() > 1) {
						a.setBuildingStatus(5);
						game.bank.payment_system.pay(this, 201);
					} else {
						System.out.println("NOT ENOUGH HOTEL!,SORRY..");
					}
				}
			} else if (buildingType == 1) {
				if (a.getBuildingStatus() == 5) {
					if (bank.houses.size() >= 4) {
						a.setBuildingStatus(4);
						game.bank.payment_system.pay(this, 201);
					} else {
						System.out.println("NOT ENOUGH HOUSES!,SORRY..");
					}
				}
			} else if (buildingType == 0) {
				if (a.getBuildingStatus() > 0 && a.getBuildingStatus() < 5) {
					a.setBuildingStatus(a.getBuildingStatus() - 1);
					game.bank.payment_system.pay(this, 201);
				}
			}
		}
	}

	public void subwayMove() {
		
		String[] choicesS = new String[200];
		int k = 0;
		for(int i= 0; i<3; i++) {
			for(Square s: m.game.board.squares.get(i)) {
				
				choicesS[k] = s.getTitle();
				k++;
			}
					
		}
		
		String inputDes = (String) JOptionPane.showInputDialog(null, "Select Street to Go", "Street selection",
				JOptionPane.QUESTION_MESSAGE, null, choicesS, // Array of choices
				choicesS[0]);
		
		
		Square des_square;
		int layer = 0;
		int square = 0;
		int sq_tr = 0;
		for(int i= 0; i<3; i++) {
			sq_tr = 0;
			for(Square s: m.game.board.squares.get(i)) {
				
				if(s.getTitle().equals(inputDes)) {
					des_square = s;
					layer = i;
					square = sq_tr;
					
				}
				sq_tr++;
			}
		}
		
		
		setPosition(new PositionPair(layer,square));
		
		
		
		/*
		String[] choices = { "outer", "middle","inner" };
		String input = (String) JOptionPane.showInputDialog(null, "Select Street to Go", "Street selection",
				JOptionPane.QUESTION_MESSAGE, null, choices, // Array of choices
				choices[0]);
		
		
		if(input.equals("outer")) {
			setPosition(new PositionPair(0,0));
		}else if(input.equals("middle")) {
			setPosition(new PositionPair(1,0));
		}else {
			setPosition(new PositionPair(2,0));
		}
		*/
		canRollDice = false;
		isRolled = true;
		hasMoved = true;
		turnEnded=false;
		regularMoved=true;
		subwayMoved=true;
		
		int current_player = m.game.getCurrentPlayerIndex();
		DimensionHandler dimension_handler = m.game.dimension_handler;
		m.gUI.playerDimensions.set(current_player,
				dimension_handler.findDimension(
				m.game.getCurrentPlayer()));
		m.gUI.repaint();
		
		
	}
	
	/**
	 * Take turn.
	 */
	public void takeTurn() {
		
		if(bankrupt) {
			hasMoved=true;
			paidRent=true;
			canRollDice=false;
			isRolled=false;
			turnEnded=true;
			regularMoved=true;
			m.gUI.end_turn.doClick();
			return;
		}
		
		
		if(inJail && numturn_inJail==0) {
		numturn_inJail++;
		canRollDice=false;
		isRolled=false;
		hasMoved=true;
		turnEnded=true;
		regularMoved=true;
		System.out.println("injail && numturn in jail"+numturn_inJail);
		} else {
		if(inJail) {
			System.out.println("injail kısımna gelio");
			getOutOfJail();
		}
		System.out.println("taketurn else kısmına geliyo");
		canRollDice = true;
		isRolled = false;
		hasMoved = false;
		turnEnded=false;
		regularMoved=false;
		}
	}
	
	public void payGetOutOfJail(int amount) {
		bank.payment_system.acceptPay(this, amount);
		System.out.println("paygetout taketurn");
		getOutOfJail();
	}
	
	public void find_square() {
		onSquare = sf.findSquare(this);
	}
	
	
	public void oneMoreTurn() {
		System.out.println("onemoreturn taketurn");
		takeTurn();
	}
	
	public void getOutOfJail() {
		inJail = false;
		m.game.jail.prisoners.remove(this);
		isPlaying= true;
		takeTurn();
	}
	
	
	
	
	public void checkBankrupt(int amount) {
		
		boolean hasAsset = !(assets.size()==0);
		
		if(balance<amount) {
			
			if(hasAsset) {
				sellProperty(assets.get(0));
			} else {
				isPlaying=false;
				bankrupt = true;
				m.gUI.playerAssets.get(m.game.getCurrentPlayerIndex()).setText("BANKRUPT!\nOUT OF GAME");
				m.gUI.end_turn.doClick();
			}
			
		}
		
	}
	
	
	
	
	
	public void payRent(Player owner,int amount) {
		checkBankrupt(amount);
		setBalance(getBalance()-amount);
		owner.setBalance(owner.getBalance()+amount);
		paidRent=true;
	}
	
	

	/** The house. */
	private static int HOUSE = 0;
	
	/** The hotel. */
	private static int HOTEL = 1;
	
	/** The skyscraper. */
	private static int SKYSCRAPER = 2;

}
