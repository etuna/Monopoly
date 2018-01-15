package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import events.*;

public class Game implements Serializable{
	
	/** The game. */
	public static Game game;
	
	/** The board. */
	public Board board;
	
	/** The bank. */
	public Bank bank;
	
	/** The jail. */
	public Jail jail;
	
	public EventManager eventManager;
	
	public TurnState turnState;
	
	/** The players. */
	private List<Player> players;
	
	/** The assets. */
	private ArrayList<Asset> assets;
	
	/** The pieces. */
	private List<Piece> pieces;
	
	/** The bobby bot. */
	public Bobby bobby_bot;
	
	/** The bot thread. */
	private Thread botThread;
	
	/** The current player. */
	public Player currentPlayer;
	
	/** The current player index. */
	private int currentPlayerIndex;
	
	public int scCurrentPlayerIndex;
	public GUIState guiState;
	
	/** The next player index. */
	private int nextPlayerIndex;
	
	/** The player rolled. */
	private int playerRolled;
	
	/** The next state. */
	private int nextState;
	
	/** The doubles. */
	private boolean rollable, doubles;
	
	/** The mr monopoly. */
	public boolean mrMonopoly;
	
	/** The bus. */
	public boolean theBus;
	
	public DimensionHandler dimension_handler;
	
	
	/**
	 * Class Constructor.
	 *
	 * @param players the players
	 * @param assets the assets
	 */
	public Game(List<Player> players, ArrayList<Asset> assets) {
		setPlayers(players);
		setAssets(assets);
		board = Board.getInstance();
		bank = Bank.getInstance();
		dimension_handler =new DimensionHandler();
		
		eventManager = EventManager.getInstance();
	}
	
	
	/**
	 * Class Constructor.
	 */
	public Game() {
		//ASAP get a Game Object, then conf_game
	}
	
	/**
	 * Configure game.
	 *
	 * @requires 
	 * @modifies 
	 * @effects 
	 */
	public void conf_game() {
		pieces = new ArrayList<Piece>();
		players = new ArrayList<Player>();//They are later configured by Main
		assets = new ArrayList<Asset>();
			
		bank = Bank.getInstance();
		bank.config_Bank();
		
		board = Board.getInstance();
		board.config_board();
		board.sf.setGame(this);
		board.populateAssetsToTheSystem();
		System.out.println(assets.size()+"---- assets size");
	
		jail = Jail.getInstance();
		//eventManager = EventManager.getInstance();
		
	
		dimension_handler = new DimensionHandler();
		bobby_bot = Bobby.getInstance();
		//botThread = new Thread(bobby_bot);
		
		playerRolled = 0;
		setCurrentPlayerIndex(0);
		
		setRollable(true);
		setDoubles(true);
		mrMonopoly = false;
		theBus = false;
		turnState = new TurnState();
		initGame();
	}
	
	/**
	 * Gets the single instance of Game.
	 *
	 * @param players the players
	 * @param assets the assets
	 * @return the Game singleton instance
	 */
	public static Game getInstance(List<Player> players, ArrayList<Asset> assets) {
		if(game == null) {
			game = new Game(players,assets);
		}
		return game;
	}
	
	/**
	 * Gets the single instance of Game.
	 *
	 * @return single instance of Game
	 */
	public static Game getInstance() {
		if(game == null) {
			game = new Game();
		}
		return game;
	}
	
	
	/**
	 * Inits the game.
	 *
	 * @modifies this.turnState, this.eventManager
	 */
	public void initGame() {
		
		eventManager = new EventManager();
		SaveSystem saveSystem = new SaveSystem();
		LoadSystem loadSystem = new LoadSystem();
		BotSystem botSystem = new BotSystem();
		//eventManager.notify(new RollEvent());
		eventManager.addSub(Evnt.SAVE, saveSystem);
		eventManager.addSub(Evnt.LOAD, loadSystem);
		eventManager.addSub(Evnt.BUTTON, botSystem);
		eventManager.addSub(Evnt.JAIL, botSystem);
		eventManager.addSub(Evnt.PAYMENT, botSystem);
		eventManager.addSub(Evnt.MRMONOPOLY, botSystem);
		eventManager.addSub(Evnt.BANKRUPT, botSystem);
		eventManager.addSub(Evnt.WIN, botSystem);
		eventManager.addSub(Evnt.WAIT, botSystem);
		
		/*//eventManager.addSub(Evnt.ROLL, new RollSystem());
		eventManager.addSub(Evnt.BUY, new BuySystem());
		eventManager.addSub(Evnt.BUILD, new BuildSystem());
		eventManager.addSub(Evnt.MOVE, new MoveSystem());
		//eventManager.addSub(Evnt.ENDTURN, new EndTurnSystem());
		eventManager.addSub(Evnt.BUTTON, new ButtonSystem());
		eventManager.addSub(Evnt.CONT, new TurnSystem());
		eventManager.addSub(Evnt.ROLL, new TurnSystem());
		*/
		turnState.turnPhase =1;

		
	}
	
	/**
	 * Gets the players.
	 *
	 * @return this.players
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Sets the players.
	 *
	 * @param players the new players
	 * @modifies this.players
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	/**
	 * Adds the player.
	 *
	 * @param name the name
	 * @return the player
	 * @modifies this.players
	 */
	public Player addPlayer(String name) {
		Player p = new Player(name);
		players.add(p);
		return p;
	}
	
	/**
	 * Gets the assets.
	 *
	 * @return this.assets
	 */
	public ArrayList<Asset> getAssets() {
		return assets;
	}

	
	/**
	 * Sets the assets.
	 *
	 * @param assets the new assets
	 * @modifies this.assets
	 * @effects sets this.assets to assets
	 */
	public void setAssets(ArrayList<Asset> assets) {
		this.assets = assets;
	}
	
	/**
	 * Next turn.
	 *
	 * @modifies this
	 */
	public void nextTurn() {
		PlayerEndRolls();
		mrMonopoly = false;
		theBus = false;
		setRollable(true);
	}
	
	
	/**
	 * Save game.
	 */
	// Save & Load Game =============
	public void saveGame() {
		
	}
	
	/**
	 * Load game.
	 */
	public void loadGame() {
		
	}
	// ==============================

	/**
	 * Gets the player rolled.
	 *
	 * @return playerRolled
	 */
	public int getPlayerRolled() {
		return playerRolled;
	}
	
	/**
	 * Player rolled.
	 *
	 * @return the playerRolled
	 * @modifies this.playerRolled
	 * @effects increases this.playerRolled by 1
	 */
	public int playerRolled() {
		return ++playerRolled;
	}

	/**
	 * Player end rolls.
	 */
	public void PlayerEndRolls() {
		this.playerRolled = 0;
	}
	
	/**
	 * Gets the current player.
	 *
	 * @return this.currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Sets the current player.
	 *
	 * @param currentPlayer the new current player
	 * @modifies this.currentPlayer
	 * @effects sets this.currentPlayer to currentPlayer
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * Gets the next player.
	 *
	 * 
	 */
	public void getNextPlayer() {
		
	}
	
	/**
	 * Gets the current player index.
	 *
	 * @return this.currentPlayerIndex
	 */
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	/**
	 * Sets the current player index.
	 *
	 * @param currentPlayerIndex the new current player index
	 * @modifies this.currentPlayerIndex
	 * @effects sets this.currentPlayerIndex to currentPlayerIndex
	 */
	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}
	
	/**
	 * Gets the next player index.
	 *
	 * @return modified nextPlayerIndex
	 * @modifies this.currentPlayerIndex, this.nextPlayerIndex
	 */
	public int getNextPlayerIndex() {
		if(players.size() == currentPlayerIndex) {
			setNextPlayerIndex(0);
		} else {
			nextPlayerIndex++;
		}
		return nextPlayerIndex;
	}

	/**
	 * Sets the next player index.
	 *
	 * @param nextPlayerIndex the new next player index
	 * @modifies this.nextPlayerIndex
	 * @effects sets this.nextPlayerIndex to nextPlayerIndex
	 */
	public void setNextPlayerIndex(int nextPlayerIndex) {
		this.nextPlayerIndex = nextPlayerIndex;
	}
	
	/**
	 * Checks if is rollable.
	 *
	 * @return this.rollable
	 */
	public boolean isRollable() {
		return rollable;
	}

	/**
	 * Sets the rollable.
	 *
	 * @param rollable the new rollable
	 * @modifies this.rollable
	 * @effects sets this.rollable to rollable
	 */
	public void setRollable(boolean rollable) {
		this.rollable = rollable;
	}

	/**
	 * Gets the next state.
	 *
	 * @return this.nextState
	 */
	public int getNextState() {
		return nextState;
	}

	/**
	 * Sets the next state.
	 *
	 * @param nextState the new next state
	 * @modifies this.nextState
	 * @effects sets this.nextState to nextState
	 */
	public void setNextState(int nextState) {
		this.nextState = nextState;
	}

	/**
	 * Checks if is doubles.
	 *
	 * @return this.doubles
	 */
	public boolean isDoubles() {
		return doubles;
	}

	/**
	 * Sets the doubles.
	 *
	 * @param doubles the new doubles
	 * @modifies this.doubles
	 * @effects sets this.doubles to doubles
	 */
	public void setDoubles(boolean doubles) {
		this.doubles = doubles;
	}
	
	/**
	 * Checks if is buildable.
	 *
	 * @return false
	 */
	public boolean isBuildable() {
		
		return false;
	}
	
	/**
	 * Populate components.
	 *
	 * @param players the players
	 * @param assets the assets
	 * @param pieces the pieces
	 * @modifies this.players, this.assets, this.pieces, this.board
	 */
	public void populateComponents(List<Player> players, ArrayList<Asset> assets,ArrayList<Piece> pieces) {
		setPlayers(players);
		//setAssets(assets);
		//this.pieces = pieces;
		
		//board.populateComponents(pieces);
	}

	/** The Constant DEFAULT. */
	public static final int DEFAULT = 0;
	
	/** The Constant ROLL. */
	public static final int ROLL = 1;
	
	/** The Constant DOSQUARE. */
	public static final int DOSQUARE = 2;
	
	/** The Constant LAST. */
	public static final int LAST = 3;
	

}
