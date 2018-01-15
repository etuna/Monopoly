package gui;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Squares.Square;
import domain.Board;
import domain.Game;
import domain.Main;
import domain.Player;
import events.*;
public class WindowHelpers extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4572801646474197931L;
	private JButton rollButton, buyButton, endTurnButton, auctionButton;
	private JButton buildHouseButton, buildHotelButton, buildSkyscraperButton;
	private JButton saveGameButton, loadGameButton, gameInformationButton, busMoveButton;
	private JLabel mapImage, player1, player2, dice1, dice2;
	private JPanel dice;
	private JTextArea info;
	private JLayeredPane monopolyBoard;
	private Dimension dimension = new Dimension(180, 60);
	private JLabel[] players;
	private JPanel tools;
	public int numberOfPlayers;
	private JScrollPane scroll;
	private ImageIcon icon;
	private Player currentPlayer; // WE NEED A REFERENCE TO OUR PLAYER CLASS HERE
	public Boolean tourCompleted;
	private Boolean hasAlreadyRolled;
	public Main m;
//	private DiceGenerator theDice = new DiceGenerator(6); // WE NEED A REFERENCE TO OUR DICE GENERATOR HERE
//	private Property[] spaces; // WE NEED TO IMPLEMENT AN ARRAY OF SQUARES INTO THIS LINE
	//--
	public Square[] squares;
	//--
	
	//--
	private ArrayList<Player> thePlayers; // WE NEED TO HAVE AN ARRAY OF PLAYERS IN THIS LINE
	//---
	
	public static final JLabel money1 = new JLabel(); // REPRESENTS PLAYER1'S MONEY
	public static final JLabel money2 = new JLabel(); // REPRESENTS PLAYER2'S MONEY
	public static final Point[] mod = new Point[] { new Point(0, 0), new Point(0, 35), new Point(35, 0),new Point(35, 35) }; // THE IDEA IS TO DEFINE THE RANGE OF THE PLAYER ICONS HERE, CAN BE OVERWRITTEN
	public static final Color[] colors = new Color[] { new Color(150, 150, 0) ,new Color(250, 0, 0), new Color(0, 250, 0), new Color(0, 0, 250)};
	//		 }; // THE COLOR ARRAY HAS THE INTENT TO FILL THE PLAYER'S COLORS IN SEQUENCE; 1ST PLAYER GETS RED, SECOND GETS BROWN AND SO ON.
	
	//NEED TO BE REVISED!!!!!!!!!!!!!!!!!!!
	public static final int[][] locations = { { 620, 450 }, { 478, 450 }, { 360, 450 }, { 241, 450 }, { 121, 450 },
			{ 14, 450 }, { 14, 401 }, { 14, 302 }, { 14, 205 }, { 14, 106 }, { 14, 22 }, { 130, 22 }, { 240, 22 },
			{ 370, 22 }, { 489, 22 }, { 601, 22 }, { 601, 105 }, { 601, 204 }, { 601, 308 }, { 601, 409 }, { 601, 510 },
			{ 601, 601 } }; // HERE IS WERE THE NEWLY COUNSTRUCTED ARRAY OF SQUARES WILL FIT IN. THIS WAS MADE FOR THE 20 SQUARE IMPLEMENTATION
	// OF THE PROTOTYPE. HAVE WE CAN HAVE 3 ARRAYS INSTEAD OF LOCATIONS AND CALL THEM; LOWERARRAY MIDDLEARRAY AND UPPERARRAY, OR LAYERS.
	
	public WindowHelpers() {
		super();
		thePlayers = new ArrayList<Player>();
		numberOfPlayers = 2;
		tourCompleted = false;
		setLayout(new FlowLayout());
		icon = new ImageIcon(getClass().getResource("board.png"));
		mapImage = new JLabel(icon);
		mapImage.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		monopolyBoard = new JLayeredPane();
		monopolyBoard.setPreferredSize(new Dimension(500, 500));
		monopolyBoard.setBorder(BorderFactory.createTitledBorder("Billionaire"));
		monopolyBoard.setOpaque(true);
		monopolyBoard.add(mapImage, 2, 0);
		createButtons();
		add(monopolyBoard);
		add(tools);
	}
	
	
	public void inputNumPlayers(int num) {
		numberOfPlayers = num;
	}
	private void createButtons() {
		tools = new JPanel();
		tools.setLayout(new BoxLayout(tools, BoxLayout.Y_AXIS));
		dice = new JPanel(new FlowLayout());
		dice1 = new JLabel();
		dice1.setSize(200, 100);
		dice2 = new JLabel();
		dice2.setSize(200, 100);
		dice.add(dice1);
		dice.add(dice2);
		rollButton = new JButton("Roll Dice");
		rollButton.setSize(dimension);
		rollButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//m.game.currentPlayer.rollDice();
				System.out.println("ROLLED 2 DICE :"+Board.board.getFaceTotal());
				System.out.println("ROLLED 2 DICE :"+m.game.board.getFaceTotal());
				System.out.println("   CP :"+ m.game.currentPlayer.getName());
				//Main.game.eventManager.notify(new ButtonEvent(ButtonEvent.ROLLBUTTON, new RollEvent()));				
			}
		});
		buildHouseButton = new JButton("Move");
		buildHouseButton.setSize(dimension);
		buildHouseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Player current_player = m.game.currentPlayer;
				System.out.println("old pos:  lyr:"+current_player.getPosition().layer_position+" sq:"+current_player.getPosition().square_position);
				
				current_player.move();
				System.out.println("new pos:  lyr:"+current_player.getPosition().layer_position+" sq:"+current_player.getPosition().square_position);
				System.out.println("new pos:  lyr:"+thePlayers.get(0).getPosition().layer_position+" sq:"+thePlayers.get(0).getPosition().square_position);
				System.out.println("facetotal "+m.game.board.getFaceTotal());
				//Main.game.eventManager.notify(new ButtonEvent(ButtonEvent.BUILDBUTTON, new BuildEvent()));				
			}
		});
		buildHotelButton = new JButton("Build Hotel");
		buildHotelButton.setSize(dimension);
		buildHotelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Main.game.eventManager.notify(new ButtonEvent(ButtonEvent.BUILDBUTTON, new BuildEvent()));				
			}
		});
		buildSkyscraperButton = new JButton("Build Skyscraper");
		buildSkyscraperButton.setSize(dimension);
		buildSkyscraperButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Main.game.eventManager.notify(new ButtonEvent(ButtonEvent.BUILDBUTTON, new BuildEvent()));					
			}
		});
		saveGameButton = new JButton("Save Game");
		saveGameButton.setSize(dimension);
		saveGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Main.game.eventManager.notify(new ButtonEvent(ButtonEvent.SAVEBUTTON, new SaveEvent()));	
			}
		});
		loadGameButton = new JButton("Load Game");
		loadGameButton.setSize(dimension);
		loadGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Main.game.eventManager.notify(new ButtonEvent(ButtonEvent.LOADBUTTON, new LoadEvent()));
				
			}
		});
		gameInformationButton = new JButton("Game Information");
		gameInformationButton.setSize(dimension);
		gameInformationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		auctionButton = new JButton("Auction");
		auctionButton.setSize(dimension);
		auctionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		busMoveButton = new JButton("Bus Move");
		busMoveButton.setSize(dimension);
		busMoveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		endTurnButton = new JButton("End Turn");
		endTurnButton.setSize(dimension);
		endTurnButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				m.game.getCurrentPlayer().endTurn();
				//Main.game.eventManager.notify(new ButtonEvent(ButtonEvent.ENDTURNBUTTON, new EndTurnEvent()));
			}
		});
		buyButton = new JButton("Buy Property");
		buyButton.setSize(dimension);
		buyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Main.game.eventManager.notify(new ButtonEvent(ButtonEvent.BUYBUTTON, new BuyEvent()));				
			}
		});
		createScrollingText();
		tools.add(rollButton);
		tools.add(buyButton);
		tools.add(auctionButton);
		tools.add(busMoveButton);
		tools.add(buildHotelButton);
		tools.add(buildHouseButton);
		tools.add(buildSkyscraperButton);
		tools.add(gameInformationButton);
		tools.add(saveGameButton);
		tools.add(loadGameButton);
		tools.add(endTurnButton);
		tools.add(Box.createVerticalStrut(30));
		tools.add(scroll);
		tools.add(money1);
		tools.add(money2);
		JTextArea dice1anddice2 = new JTextArea("----------------Dice 1-----------Dice 2----------------");
		tools.add(dice1anddice2);
		tools.add(dice);
	}
	public void createScrollingText() {
		JTextArea info = new JTextArea(5, 20);
		info.setSize(400, 600);
		info.setLineWrap(true);
		scroll = new JScrollPane(info);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		info.setText("Team Billionaire presents: " + "\n" + "The Ultimate Monopoly Game! ");
	}
//	public void fillSpaces(Property[] p) {
//		spaces = p;
//	} WE MUST FILL THE SPACES WITH SQUARES, E.G: POPULATE THE BOARD SO THAT ITERATIONS CAN TAKE PLACE.
	public void fillPlayers() {
		JLabel[] p = new JLabel[2];
		
		for (int i = 0; i < p.length; i++)
			p[i] = createPlayerMarker(i + 1, colors[i], mod[i]); // WE NEED TO DECIDE HOW TO CREATE THE PLAYER ICONS HERE.
		players = p;
		for (JLabel e : players) {
			e.setVisible(true);
			
			System.out.println(e.getText()+"asasdas");
			monopolyBoard.add(e, 3, 1);
		}
			
	}
	private void enterPlayers(ArrayList<Player> playersIn) {
		thePlayers = playersIn;
	}// WE NEED TO ENLIST THE PLAYERS WITHIN THE GAME WITH A SEQUENCE HERE; PLAYERSIN(1) SHOULD REFER TO PLAYER 1 FOR EXAMPLE.
	public void updateMoney() {
		for (Player p : thePlayers) { 
			if(thePlayers.indexOf(p)==0) {
				money1.setText(p.getName() + "'s balance: " + (new Integer(p.getBalance())).toString());}
			else {
				money2.setText(p.getName() + "'s balance: " + (new Integer(p.getBalance())).toString());
			}
		}
	}// THIS MECHANISM IS NOT NEEDED IF WE DO ALREADY HAVE A MONEY UPDATE MECHANISM IN BACK-END.

	public void setCurrentPlayer(Player p) {
		hasAlreadyRolled = false;
		tourCompleted = false;
		currentPlayer = p;
	}
	// WE NEED TO KEEP TRACK OF THE CURRENT PLAYER THEREFORE THIS METHOD IS NECESSARY BUT IT CAN BE OVERWRITTEN OR IT CAN
	// BE SENT BACK TO THE BACK-END SOURCE CODE.
	public boolean isPlayerDone() {
		return tourCompleted;
	}
	private JLabel createPlayerMarker(int number, Color color, Point mod) {
		JLabel label = new JLabel();
		System.out.println("came create player marker!");
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(Color.black);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		label.setBounds(1000,1000, 30, 30);
		label.setVisible(true);
		label.setText("sadasd");
		
		System.out.println(label.getLocation());
		repaint();
		return label;
		
	}
	// DEPENDS IF WE WANT TO CREATE OUR PLAYER MARKERS THIS WAY.
	public void removePlayer(Player p) {
		//monopolyBoard.remove(players[p.getPlayerNumber()]);
	} 
	//IF THE PLAYER CLASS IS COMPATIBLE, WE MAY HAVE THIS METHOD.
	public void movePlayer(int player, int location) {
		JLabel thePlayer = players[player - 1];
	thePlayer.setLocation(locations[location][0] + mod[player].x, locations[location][1] + mod[player].y);
	}
	// IN ORDER TO MOVE THE PLAYER, WE NEED THE LOCATIONS OF THE BOARD; IN OTHER TERMS
	// WE NEED THE ARRAYLIST OF THE SQUARES TO MAKE THIS ITERATION.
	public void addText(String text) {
		info.setText(info.getText() + "\n" + text);
	}
	/*
	private void roll() {
		hasAlreadyRolled = true;
		int roll1 = theDice.cast(); IF THE DICE TEMPLATE IS COMPATIBLE, THIS CAN BE INCLUDED
		int roll2 = theDice.cast(); SAME GOES FOR DICE 2. ROLL MECHANISM CAN BE TOTALLY ADOPTED IF IT FITS THE SOLUTION.
		dice1.setText((new Integer(roll1)).toString() + "          -");
		dice2.setText("          " + (new Integer(roll2)).toString());
		movePlayer(current.getPlayerNumber(), current.playerMove(roll1 + roll2));
		Property p = spaces[current.getLocation()];
		addText(current.getPlayerName() + " arrived at " + p.getName());
		if (!(p.getOwner() == null) && !(p.getOwner().equals(current)))
			addText(current.getPlayerName() + p.getText());
		p.effect(current);
		updateMoney();
		movePlayer(current.getPlayerNumber(), current.getLocation());
		if (roll1 == roll2) {
			addText("You have rolled double! You can roll again!");
			hasAlreadyRolled = false;
		}
	}*/
	/*/public void actionPerformed(ActionEvent event) {
		if (event.getSource() == rollButton) {
			if (!hasAlreadyRolled)
				roll();
			else
				addText("You have already rolled.");
		}
		if (event.getSource() == endTurnButton) {
			if (hasAlreadyRolled)
				tourCompleted = true;
			else
				addText("You're not done, roll the dice.");
		}
		if (event.getSource() == buyButton) {
			Property p = spaces[current.getLocation()];
			if (p.getOwner() == null) {
				current.get(p);
				current.decreaseMoney(p.getCost());
				p.setOwner(current);
				addText(current.getPlayerName() + " bought " + p.getName() + " for " + p.getCost() + ".");
			} else
				addText("You cannot buy that!");
			updateMoney();
		}
	}/*/ // SOME OF THE ACTION PERFORMED EVENTS ARE HERE; BUT YOU MIGHT NEED TO REVISE ALL OF THEM WITH RESPECT TO OUR SOURCECODE.
	public void __init() {
	/*/	Space first = new Space("Go", 200);
		BoardProperties second = new BoardProperties("Oriental Ave", "BLUE", 1, 25, 250);
		Space third = new Space("Community Chest", 1);
		BoardProperties fourth = new BoardProperties("Vermont Ave", "BLUE", 2, 22, 220);
		BoardProperties fifth = new BoardProperties("Connecticut Ave", "BLUE", 3, 16, 160);
		Space sixth = new Space("Roll once", 2);
		BoardProperties seventh = new BoardProperties("St Charles Place", "PINK", 1, 32, 320);
		Space eigth = new Space("Chance", 3);
		BoardProperties ninth = new BoardProperties("States Ave", "PINK", 2, 24, 240);
		BoardProperties tenth = new BoardProperties("Virginia Ave", "PINK", 3, 30, 300);
		Space eleventh = new Space("Free Parking", 4);
		BoardProperties twelwe = new BoardProperties("St James Place", "ORANGE", 1, 41, 410);
	//	Space thirteen = new Space("Community Chest", 5);
		BoardProperties fourteen = new BoardProperties("Tennessee Ave", "ORANGE", 2, 19, 190);
	//	BoardProperties fifteen = new BoardProperties("New York Ave", "ORANGE", 3, 62, 620);
	//	Space sixteen = new Space("Squeeze Play", 6);
	//	BoardProperties seventeen = new BoardProperties("Pacific Ave", "GREEN", 1, 47, 470);
	//	BoardProperties eighteen = new BoardProperties("North Carolina Ave", "GREEN", 2, 41, 410);
	//	Space nineteen = new Space("Chance", 7);
	//	BoardProperties twenty = new BoardProperties("Pennsylvania Ave", "GREEN", 3, 57, 570);
	//	Property[] spaces = { first, second, third, fourth, fifth, sixth, seventh, eigth, ninth, tenth, eleventh,
				twelwe, thirteen, fourteen, fifteen, sixteen, seventeen, eighteen, nineteen, twenty };/*/
		
		
		// TRY TO UNDERSTAND THE MECHANISM ON TOP OF THIS LINE AND
		// APPLY IT TO THE ORIGINAL BOARD WE HAVE FOR THE MOMENT.
		//
		JFrame frame = new JFrame("Monopoly");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1500,1500);
		WindowHelpers game = new WindowHelpers();
		game.setSize(1500,1500);
		game.setOpaque(true); 
		//frame.setContentPane(game);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
		Object[] options = { "The game supports 2 players for the moment. " };
		Object choice = JOptionPane.showOptionDialog(null, "You will be asked to enter 2 player names now. ", "", JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		ArrayList<Player> playersIn = new ArrayList<Player>();
		
		String name = JOptionPane.showInputDialog("Player 1, enter your name.");
		Player player1 = new Player(name);
		thePlayers.add(player1);
		
		name = JOptionPane.showInputDialog("Player 2, enter your name.");
		Player player2 = new Player(name);
		thePlayers.add(player2);
		
		Main.game.setPlayers(thePlayers);
		Main.game.setCurrentPlayer(Main.game.getPlayers().get(Main.game.getCurrentPlayerIndex()));
		
		//Main.game.setCurrentPlayer(player2);
		//Main.game.config_game(thePlayers, null, null);
		
		game.inputNumPlayers(thePlayers.size());
		
		game.fillPlayers();
		//game.fillSpaces(squares);
		game.enterPlayers(thePlayers);
		game.updateMoney();
		game.repaint();
	/*/	for (int turn = 1; turn > 0; turn++) {
			game.setCurrentPlayer(playersIn.get(turn));
			game.addText("It is " + game.current.getPlayerName() + "'s turn.");
			while (!game.isPlayerDone()) {
				if (game.current.getBalance() < 0) {
					playersIn.remove(turn);
					turn--;
					game.addText(game.current.getPlayerName() + " is bankrupt and removed from the game.");
					game.tourCompleted = true;
				}
			}
			game.current.reset();
			game.updateMoney();
			if (turn < playersIn.size())
				turn++;
			if (turn >= playersIn.size())
				turn = 0;
			if (playersIn.size() == 1)
				turn = -1;
		}
		Player winner = playersIn.get(0);
		JOptionPane.showMessageDialog(null, winner.getPlayerName() + "wins");
	}/*/
}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// OVERRIDE HERE OR REVERT IT TO THE ORIGINAL ONE.
	}}


// COMMENTS POINTED OUT BY MERICKAPTAN FOR TEAMBILLIONAIRE.
