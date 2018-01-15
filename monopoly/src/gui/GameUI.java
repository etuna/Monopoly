package gui;

import javax.swing.border.Border;

import Squares.CustomSquare;
import Squares.Square;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import domain.*;
import events.ButtonEvent;
import events.Evnt;
import events.LoadEvent;
import events.SaveEvent;

public class GameUI extends JFrame implements KeyListener, Serializable{

	public JButton roll_dice, bus_move, mrMonopoly_move, regular_move, buy_property, sell_property, pay_rent,
			build_house, build_hotel, build_skyscraper, auction, payGetOutOfJail, end_turn, save_game, load_game;

	public TextArea newsfeed;

	public ImageIcon board_image, die1, die2, die3, speed_die;

	public JPanel button_panel;

	public JLabel test_label;

	public Main m;
	

	private JScrollPane scroll;

	public int num_players = 8;
	public int current_player = 0;

	public Color[] colors = { Color.red, Color.green, Color.blue, Color.yellow, Color.pink, Color.cyan, Color.MAGENTA,
			Color.ORANGE };

	public ArrayList<Player> players;
	public ArrayList<JPanel> playerPanels;
	public ArrayList<JLabel> playerNames;
	public ArrayList<JLabel> playerBalances;
	public ArrayList<JTextArea> playerAssets;
	public ArrayList<JLabel> playerTickets;
	public ArrayList<Dimension> playerDimensions;

	int x = 0;
	int y = 0;

	Graphics g;
	Random r;
	DimensionHandler dimension_handler;
	boolean readyToPaint = false;

	public KeyboardPos kbPos;
	
	public GameUI() {
		r = new Random();
		playerDimensions = new ArrayList<Dimension>();
		playerAssets = new ArrayList<JTextArea>();
		playerBalances = new ArrayList<JLabel>();
		dimension_handler = new DimensionHandler();
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new MDispatcher());
	}

	public void paint(Graphics g) {
		super.paint(g);
		this.g = g;

		// g.fillOval(515, 575, 20, 20);

		if (readyToPaint) {
			for (int i = 0; i < players.size(); i++) {
				int move_abit = r.nextInt(10);
				Color c = colors[i];
				g.setColor(c);
				g.fillOval(playerDimensions.get(i).width + move_abit, playerDimensions.get(i).height + move_abit, 20,
						20);

			}

			if (m.game.board.squaresAreOK) {
				conf_buildings(g);
			}
		}
	}

	public void _conf() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		setSize(1500, 1000);
		// setResizable(false);
		add(conf_buttons(), BorderLayout.EAST);
		// add(conf_playerPanel(),BorderLayout.SOUTH);
		add(main_panel(), BorderLayout.WEST);

		/*
		 * Player test = new Player("test 1"); Player test2 = new Player("test 2");
		 * 
		 * // -TEST ArrayList<String> testPlayersNames = new ArrayList<String>();
		 * testPlayersNames.add("test 1"); testPlayersNames.add("test 2");
		 * testPlayersNames.add("test 3"); testPlayersNames.add("test 4");
		 * testPlayersNames.add("test 5");
		 * 
		 * conf_players(5, testPlayersNames); // --------
		 */

		revalidate();
		repaint();

		prepareToPaint();
		add(conf_playerPanel(), BorderLayout.SOUTH);

		// add(conf_boardUI(),BorderLayout.EAST);
		revalidate();
		repaint();

	}

	public JPanel conf_buttons() {
		button_panel = new JPanel();
		button_panel.setBackground(Color.DARK_GRAY);
		button_panel.setPreferredSize(new Dimension(200, 1000));
		button_panel.setLayout(new FlowLayout());
		button_panel.setVisible(true);
		button_panel.setOpaque(true);

		roll_dice = new JButton();
		roll_dice.setText("ROLL DICE");
		roll_dice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.m.game.eventManager.notify(new ButtonEvent());
				// TODO Auto-generated method stub
				boolean isRolled = m.game.currentPlayer.isRolled();
				m.game.currentPlayer.rollDice(2);
				Die[] dice = m.game.board.dice;
				int face_val0 = dice[0].getFaceValue();
				int face_val1 = dice[1].getFaceValue();
				int face_val2 = dice[2].getFaceValue();
				SpeedDie sp = m.game.board.speedDie;
				int sp_result = m.game.board.speedDie_result;
				if (!isRolled) {
					info.setText(info.getText() + "\nPlayer " + players.get(current_player).getName()
							+ " rolled dice  :" + face_val0 + " - " + face_val1 + " and SpeedDie :"
							+ m.game.board.speedDie_result + "\n");
				} else {
					info.setText(info.getText() + "Sorry you have already rolled,you can't roll one more time.\n");
				}
				if (face_val0 == 1) {
					dice0_icon = new ImageIcon(getClass().getResource("img/Dice1.gif"));
					Image image0 = dice0_icon.getImage(); // transform it
					Image new_dice0_icon = image0.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice0_icon = new ImageIcon(new_dice0_icon);
					dice_label0.setIcon(dice0_icon);
				} else if (face_val0 == 2) {
					dice0_icon = new ImageIcon(getClass().getResource("img/Dice2.gif"));
					Image image0 = dice0_icon.getImage(); // transform it
					Image new_dice0_icon = image0.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice0_icon = new ImageIcon(new_dice0_icon);
					dice_label0.setIcon(dice0_icon);
				} else if (face_val0 == 3) {
					dice0_icon = new ImageIcon(getClass().getResource("img/Dice3.gif"));
					Image image0 = dice0_icon.getImage(); // transform it
					Image new_dice0_icon = image0.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice0_icon = new ImageIcon(new_dice0_icon);
					dice_label0.setIcon(dice0_icon);
				} else if (face_val0 == 4) {
					dice0_icon = new ImageIcon(getClass().getResource("img/Dice4.gif"));
					Image image0 = dice0_icon.getImage(); // transform it
					Image new_dice0_icon = image0.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice0_icon = new ImageIcon(new_dice0_icon);
					dice_label0.setIcon(dice0_icon);
				} else if (face_val0 == 5) {
					dice0_icon = new ImageIcon(getClass().getResource("img/Dice5.gif"));
					Image image0 = dice0_icon.getImage(); // transform it
					Image new_dice0_icon = image0.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice0_icon = new ImageIcon(new_dice0_icon);
					dice_label0.setIcon(dice0_icon);
				} else {
					dice0_icon = new ImageIcon(getClass().getResource("img/Dice6.gif"));
					Image image0 = dice0_icon.getImage(); // transform it
					Image new_dice0_icon = image0.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice0_icon = new ImageIcon(new_dice0_icon);
					dice_label0.setIcon(dice0_icon);
				}

				if (face_val1 == 1) {
					dice1_icon = new ImageIcon(getClass().getResource("img/Dice1.gif"));
					Image image1 = dice1_icon.getImage(); // transform it
					Image new_dice1_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice1_icon = new ImageIcon(new_dice1_icon);
					dice_label1.setIcon(dice1_icon);
				} else if (face_val1 == 2) {
					dice1_icon = new ImageIcon(getClass().getResource("img/Dice2.gif"));
					Image image1 = dice1_icon.getImage(); // transform it
					Image new_dice1_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice1_icon = new ImageIcon(new_dice1_icon);
					dice_label1.setIcon(dice1_icon);
				} else if (face_val1 == 3) {
					dice1_icon = new ImageIcon(getClass().getResource("img/Dice3.gif"));
					Image image1 = dice1_icon.getImage(); // transform it
					Image new_dice1_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice1_icon = new ImageIcon(new_dice1_icon);
					dice_label1.setIcon(dice1_icon);
				} else if (face_val1 == 4) {
					dice1_icon = new ImageIcon(getClass().getResource("img/Dice4.gif"));
					Image image1 = dice1_icon.getImage(); // transform it
					Image new_dice1_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice1_icon = new ImageIcon(new_dice1_icon);
					dice_label1.setIcon(dice1_icon);
				} else if (face_val1 == 5) {
					dice1_icon = new ImageIcon(getClass().getResource("img/Dice5.gif"));
					Image image1 = dice1_icon.getImage(); // transform it
					Image new_dice1_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice1_icon = new ImageIcon(new_dice1_icon);
					dice_label1.setIcon(dice1_icon);
				} else {
					dice1_icon = new ImageIcon(getClass().getResource("img/Dice6.gif"));
					Image image1 = dice1_icon.getImage(); // transform it
					Image new_dice1_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice1_icon = new ImageIcon(new_dice1_icon);
					dice_label1.setIcon(dice1_icon);
				}

				SpeedDie sd = m.game.board.speedDie;
				int sd_result = sd.face_value;

				if (sd_result == 1) {
					dice3_icon = new ImageIcon(getClass().getResource("img/RedDice1.png"));
					Image image1 = dice3_icon.getImage(); // transform it
					Image new_dice3_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice3_icon = new ImageIcon(new_dice3_icon);
					dice_label3.setIcon(dice3_icon);
				} else if (sd_result == 2) {
					dice3_icon = new ImageIcon(getClass().getResource("img/RedDice2.png"));
					Image image1 = dice3_icon.getImage(); // transform it
					Image new_dice3_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice3_icon = new ImageIcon(new_dice3_icon);
					dice_label3.setIcon(dice3_icon);
				} else if (sd_result == 3) {
					dice3_icon = new ImageIcon(getClass().getResource("img/RedDice3.png"));
					Image image1 = dice3_icon.getImage(); // transform it
					Image new_dice3_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice3_icon = new ImageIcon(new_dice3_icon);
					dice_label3.setIcon(dice3_icon);
				} else if (sd_result == 4) {
					dice3_icon = new ImageIcon(getClass().getResource("img/RedDice4.png"));
					Image image1 = dice3_icon.getImage(); // transform it
					Image new_dice3_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice3_icon = new ImageIcon(new_dice3_icon);
					dice_label3.setIcon(dice3_icon);
				} else if (sd_result == 5) {
					dice3_icon = new ImageIcon(getClass().getResource("img/bus.png"));
					Image image1 = dice3_icon.getImage(); // transform it
					Image new_dice3_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice3_icon = new ImageIcon(new_dice3_icon);
					dice_label3.setIcon(dice3_icon);
				} else {
					dice3_icon = new ImageIcon(getClass().getResource("img/mr-monopoly.jpg"));
					Image image1 = dice3_icon.getImage(); // transform it
					Image new_dice3_icon = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it
																											// the
																											// smooth
																											// way
					dice3_icon = new ImageIcon(new_dice3_icon);
					dice_label3.setIcon(dice3_icon);
				}

			}
		});

		bus_move = new JButton();
		bus_move.setText("BUS MOVE");
		bus_move.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.m.game.eventManager.notify(new ButtonEvent());
				if (m.game.currentPlayer.busIcon_move == true) {
					String[] choices = { "take a TRAVEL VOUCHER", "move to the nearest CHANCE or COMMUNITY CHEST" };
					String input = (String) JOptionPane.showInputDialog(null, "Choose now...", "The Choice of Bus Move",
							JOptionPane.QUESTION_MESSAGE, null, choices, // Array of choices
							choices[0]);
					System.out.println("input : " + input);
					System.out.println(choices[0] + " " + choices[1]);
					if (input.equals(choices[0])) {
						m.game.currentPlayer.second_move();
					} else {
						System.out.println("geldi buraya kadar");
						m.game.board.busMove(1);
						m.game.currentPlayer.second_move();
						playerDimensions.set(current_player,
								dimension_handler.findDimension(m.game.getCurrentPlayer()));
						repaint();
					}

				}
			}
		});

		mrMonopoly_move = new JButton();
		mrMonopoly_move.setText("MR MONOPOLY MOVE");
		mrMonopoly_move.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (m.game.currentPlayer.mrMonopoly_move == true) {
					m.game.board.mrMonopoly_move();
					playerDimensions.set(current_player, dimension_handler.findDimension(m.game.getCurrentPlayer()));
					repaint();
				}
			}
		});

		regular_move = new JButton();
		regular_move.setText("MOVE");
		regular_move.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(m.game.currentPlayer.subway && !m.game.currentPlayer.subwayMoved) {
					m.game.currentPlayer.subwayMove();
					m.game.currentPlayer.subway=false;
					m.game.currentPlayer.turnEnded=true;
					
				}
				
				if (!m.game.currentPlayer.isRolled()) { 
					info.setText(info.getText()+"\nSorry you have not rolled dice yet.");
					return;
				}
				
				if(m.game.board.checkTransitPassing(m.game.currentPlayer, m.game.board.getFaceTotal()) != null) {
					int []coord = m.game.board.checkTransitPassing(m.game.currentPlayer, m.game.board.getFaceTotal());
					m.game.board.findLocationThen(m.game.currentPlayer, coord, m.game.board.getFaceTotal());
					
					playerDimensions.set(current_player, dimension_handler.findDimension(m.game.getCurrentPlayer()));
					repaint();
				}else {
				
				
				m.game.getCurrentPlayer().move();
				playerDimensions.set(current_player, dimension_handler.findDimension(m.game.getCurrentPlayer()));
				repaint();
				System.out.println(
						"dddddddddddddddd" + playerDimensions.get(0).width + " " + playerDimensions.get(0).height);
				System.out.println("dddddddddddddddd" + playerDimensions.get(1) + " " + playerDimensions.get(1).height);
				}
			}
		});

		buy_property = new JButton();
		buy_property.setText("BUY PROPERTY");
		buy_property.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.m.game.eventManager.notify(new ButtonEvent());
				Player p = m.game.currentPlayer;
				if (p.isRolled() && p.hasMoved) {

					Square s = p.sf.findSquare(p);

					if (s instanceof CustomSquare) {
						Asset a = s.getAsset();

						p.buyProperty(a);
						if (p.buy_property_successful) {
							String asset_names = "";
							for (int k = 0; k < p.getAssets().size(); k++) {
								asset_names += "->" + p.getAssets().get(k).getName() + "\n";
							}

							playerAssets.get(current_player).setText("ASSETS\n========\n\n" + asset_names);
						}
						// playerAssets.get(current_player).setText(playerAssets.get(current_player).getText()+"<html>"+a.getName()+"<br
						// /></html>");
						p.buy_property_successful = false;

					}
				} else {
					if (p.oneMoreRoll == true) {
						Square s = p.sf.findSquare(p);

						if (s instanceof CustomSquare) {
							Asset a = s.getAsset();

							p.buyProperty(a);
							if (p.buy_property_successful) {
								String asset_names = "";
								for (int k = 0; k < p.getAssets().size(); k++) {
									asset_names += "->" + p.getAssets().get(k).getName() + "\n";
								}

								playerAssets.get(current_player).setText("ASSETS\n========\n\n" + asset_names);
							}
							// playerAssets.get(current_player).setText(playerAssets.get(current_player).getText()+"<html>"+a.getName()+"<br
							// /></html>");
							p.buy_property_successful = false;
						}
					}
				}
			}
		});

		sell_property = new JButton();
		sell_property.setText("SELL PROPERTY");
		sell_property.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.m.game.eventManager.notify(new ButtonEvent());
				if (m.game.currentPlayer.getAssets().size() != 0) {
					String[] choices = new String[m.game.currentPlayer.getAssets().size()];

					for (int i = 0; i < m.game.currentPlayer.getAssets().size(); i++) {
						choices[i] = m.game.currentPlayer.getAssets().get(i).getName();
					}

					String input = (String) JOptionPane.showInputDialog(null, "Choose an asset to sell",
							"The Choice of sell property", JOptionPane.QUESTION_MESSAGE, null, choices, // Array of
																										// choices
							choices[0]);

					Asset a = null;
					for (int i = 0; i < m.game.currentPlayer.getAssets().size(); i++) {
						if (input.equals(m.game.currentPlayer.getAssets().get(i).getName())) {
							a = m.game.currentPlayer.getAssets().get(i);

						}

					}

					m.game.currentPlayer.sellProperty(a);
					String asset_names = "";
					for (int k = 0; k < m.game.currentPlayer.getAssets().size(); k++) {
						asset_names += "->" + m.game.currentPlayer.getAssets().get(k).getName() + "\n";
					}

					playerAssets.get(current_player).setText("ASSETS\n========\n\n" + asset_names);

				} else {
					info.setText(info.getText() + "\nYou have no asset to sell.");
				}
			}
		});

		pay_rent = new JButton();
		pay_rent.setText("PAY RENT");
		pay_rent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.m.game.eventManager.notify(new ButtonEvent());
				Square s = m.game.board.sf.findSquare(m.game.currentPlayer);
				if (s instanceof CustomSquare) {
					Asset a = s.getAsset();
					Player owner = a.getBelongTo();

					if (owner != null && owner != m.game.currentPlayer) {
						int buildingStatus = a.getBuildingStatus();
						if (buildingStatus == 0) {
							int amount = a.getRentPrice();
							amount = 20;
							m.game.currentPlayer.payRent(owner, amount);
							m.game.currentPlayer.paidRent = true;
							info.setText(info.getText() + "\n" + m.game.currentPlayer.getName() + " paid $" + amount
									+ " as rent to " + owner.getName() + "\n");

							int ownerIndex = players.indexOf(owner);

							playerBalances.get(ownerIndex).setText("$ " + owner.getBalance());
							playerBalances.get(current_player).setText("$ " + m.game.currentPlayer.getBalance());

						} else {
							int amount = buildingStatus * a.getRentPrice();
							m.game.currentPlayer.payRent(owner, amount);
							m.game.currentPlayer.paidRent = true;
							info.setText(info.getText() + "\n" + m.game.currentPlayer.getName() + " paid $" + amount
									+ " as rent to " + owner.getName() + "\n");

							int ownerIndex = players.indexOf(owner);

							playerBalances.get(ownerIndex).setText("$ " + owner.getBalance());
							playerBalances.get(current_player).setText("$ " + m.game.currentPlayer.getBalance());

						}
					}
					m.game.currentPlayer.paidRent = true;
				}

			}
		});

		build_house = new JButton();
		build_house.setText("BUILD HOUSE");
		build_house.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.m.game.eventManager.notify(new ButtonEvent());
				Square s = m.game.board.sf.findSquare(m.game.currentPlayer);
				if (s instanceof CustomSquare) {
					Asset a = s.getAsset();
					m.game.currentPlayer.buildBuilding(0, a);
				} else {
					info.setText(info.getText() + "\nSorry You CANT build house here");
				}
			}
		});

		build_hotel = new JButton();
		build_hotel.setText("BUILD HOTEL");
		build_hotel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.m.game.eventManager.notify(new ButtonEvent());
				Square s = m.game.board.sf.findSquare(m.game.currentPlayer);
				if (s instanceof CustomSquare) {
					Asset a = s.getAsset();
					m.game.currentPlayer.buildBuilding(0, a);
				} else {
					info.setText(info.getText() + "\nSorry You CANT build hotel here");
				}
			}

		});

		build_skyscraper = new JButton();
		build_skyscraper.setText("BUILD SKYSCRAPER");
		build_skyscraper.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.m.game.eventManager.notify(new ButtonEvent());
				Square s = m.game.board.sf.findSquare(m.game.currentPlayer);
				if (s instanceof CustomSquare) {
					Asset a = s.getAsset();
					m.game.currentPlayer.buildBuilding(0, a);
				} else {
					info.setText(info.getText() + "\nSorry You CANT build skyscraper here");
				}
			}

		});

		auction = new JButton();
		auction.setText("AUCTION");
		auction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.m.game.eventManager.notify(new ButtonEvent());
				if (m.game.currentPlayer.getAssets().size() != 0) {
					String[] choices = new String[m.game.currentPlayer.getAssets().size()];

					for (int i = 0; i < m.game.currentPlayer.getAssets().size(); i++) {
						choices[i] = m.game.currentPlayer.getAssets().get(i).getName();
					}

					String input = (String) JOptionPane.showInputDialog(null, "Choose an asset to sell",
							"The Choice of sell property", JOptionPane.QUESTION_MESSAGE, null, choices, // Array of
																										// choices
							choices[0]);
					
					Asset a = null;
					for (int i = 0; i < m.game.currentPlayer.getAssets().size(); i++) {
						if (input.equals(m.game.currentPlayer.getAssets().get(i).getName())) {
							a = m.game.currentPlayer.getAssets().get(i);

						}

					}
					
					
					int max = 0;
					Player most_paying = null;
					for(int i=0; i<num_players; i++) {
						
						if(i == current_player) {
							//dont ask current player
						}else {
							String message = ("Present an offer, "+players.get(i).getName()+"!\nPlayer "+m.game.currentPlayer.getName() +" wants to sell his propert "+
									input+", what would you pay for this in order to buy?");
							String stringnum = JOptionPane.showInputDialog(m.gUI, message);
						int intnum = Integer.parseInt(stringnum);
						if(max == 0 || intnum>max) {
							max = intnum;
							most_paying = players.get(i);
						}
						}
						
					}
						
						//Give the property to the most_paying player
						m.game.currentPlayer.getAssets().remove(a);
						most_paying.getAssets().add(a);
						a.setBelongTo(most_paying);
						while(most_paying.getBalance()<0) {
						most_paying.checkBankrupt(max);
						}
						
						//balance changes
						m.game.currentPlayer.setBalance(m.game.currentPlayer.getBalance()+max);
						most_paying.setBalance(most_paying.getBalance()-max);
						
						info.setText(info.getText()+"\n"+m.game.currentPlayer.getName()+"'s "+input+" is now owned by "+most_paying.getName()+"\n");
						
						String newAssetOfCurrent="ASSETS\n========\n";
						for(Asset as: m.game.currentPlayer.getAssets()) {
							newAssetOfCurrent += "->"+as.getName();
						}
						
						String newAssetOfMostPaying="ASSETS\n========\n";
						for(Asset as: most_paying.getAssets()) {
							newAssetOfMostPaying+= "->"+as.getName();
						}
						
						playerAssets.get(current_player).setText(newAssetOfCurrent);
						int indexOfMostPaying = players.indexOf(most_paying);
						playerAssets.get(indexOfMostPaying).setText(newAssetOfMostPaying);
						
						
						playerBalances.get(current_player).setText("$ "+m.game.currentPlayer.getBalance());
						playerBalances.get(indexOfMostPaying).setText("$ "+most_paying.getBalance());
						
						
					}
					
					
					
					
					
				}
				
				
				
				
				
				
			
		});

		payGetOutOfJail = new JButton();
		payGetOutOfJail.setText("PAY JAIL PRICE");
		payGetOutOfJail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.m.game.eventManager.notify(new ButtonEvent());
				if (m.game.currentPlayer.inJail) {
					m.game.currentPlayer.payGetOutOfJail(100);
					playerBalances.get(current_player)
							.setText("$ " + Integer.toString(m.game.currentPlayer.getBalance()));
				} else {
					info.setText(info.getText() + "\nYou are not in jail, don't need to pay anything\n");
				}
			}
		});

		end_turn = new JButton();
		end_turn.setText("END TURN");
		end_turn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.m.game.eventManager.notify(new ButtonEvent());
				Game.getInstance().eventManager.notify(new Evnt(Evnt.BUTTON));
				boolean turnEnded = m.game.currentPlayer.endTurn();

				if (turnEnded) {
					current_player = (current_player + 1) % num_players;
					current_player_panel_num.setText(Integer.toString(current_player + 1));
					info.setText(
							info.getText() + "Turn ended. Player " + m.game.currentPlayer.getName() + "'s turn now.\n");
				} else {
					info.setText(info.getText() + "Sorry you can't end the turn now.\n");
				}
				
				
				
				Player winner = null;
				int remaining = 0;
				boolean win =false;
				int winner_index = 0;
				for(Player p: m.game.getPlayers()) {
					if(!p.bankrupt) {
						remaining++;
					}
				}
				
				if(remaining<2) {
					for(Player p: m.game.getPlayers()) {
						if(!p.bankrupt) {
							winner=p;
							winner_index=m.game.getPlayers().indexOf(p);
						}
					}
					playerAssets.get(winner_index).setText("WINNER!");
					end_turn.setEnabled(false);
					regular_move.setEnabled(false);
					buy_property.setEnabled(false);
					sell_property.setEnabled(false);
					auction.setEnabled(false);
					save_game.setEnabled(false);
					load_game.setEnabled(false);
					bus_move.setEnabled(false);
					roll_dice.setEnabled(false);
					pay_rent.setEnabled(false);
					build_house.setEnabled(false);
					build_hotel.setEnabled(false);
					build_skyscraper.setEnabled(false);
					payGetOutOfJail.setEnabled(false);
					mrMonopoly_move.setEnabled(false);
				}
				
				
				
				
			}
		});

		save_game = new JButton();
		save_game.setText("SAVE GAME");
		save_game.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.game.eventManager.notify(new SaveEvent());
				
			}
			
		});

		load_game = new JButton();
		load_game.setText("LOAD GAME");
		
		load_game.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.game.eventManager.notify(new LoadEvent());
				
			}
			
		});

		// Populate buttons into the panel-----
		button_panel.add(roll_dice);
		button_panel.add(bus_move);
		button_panel.add(mrMonopoly_move);
		button_panel.add(regular_move);
		button_panel.add(buy_property);
		button_panel.add(sell_property);
		button_panel.add(pay_rent);
		button_panel.add(build_house);
		button_panel.add(build_hotel);
		button_panel.add(build_skyscraper);
		button_panel.add(auction);
		button_panel.add(payGetOutOfJail);
		button_panel.add(end_turn);
		button_panel.add(save_game);
		button_panel.add(load_game);
		// ---

		return button_panel;
	}

	public JPanel conf_boardUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(600, 600));
		ImageIcon boardIcon = new ImageIcon(getClass().getResource("board4.png"));
		JLabel adp = new JLabel();

		adp.setIcon(boardIcon);
		adp.setVisible(true);

		panel.add(adp);
		panel.setVisible(true);

		return panel;
	}

	public JPanel conf_playerPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(1400, 250));
		panel.setBackground(Color.black);

		for (int i = 0; i < num_players; i++) {

			JScrollPane player_scroll;
			JPanel player_panel = new JPanel();
			JLabel player_name = new JLabel();
			JLabel player_balance = new JLabel();
			JTextArea player_assets = new JTextArea(5, 8);
			JLabel player_tickets = new JLabel();

			player_assets.setSize(100, 100);
			player_assets.setLineWrap(true);
			player_scroll = new JScrollPane(player_assets);

			player_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			playerAssets.add(player_assets);

			String p_assets = "ASSETS\n";
			for (int k = 0; k < players.get(i).getAssets().size(); k++) {
				p_assets += players.get(i).getAssets().get(k).getName() + "\n";
			}

			String p_tickets = "TICKETS\n";
			for (int k = 0; k < players.get(i).getAssets().size(); k++) {
				p_tickets += players.get(i).tickets.get(k).name + "\n";
			}

			player_name.setText(players.get(i).getName());
			player_balance.setText("$ " + Integer.toString(players.get(i).getBalance()));
			player_assets.setText(p_assets);
			player_tickets.setText(p_tickets);

			player_name.setPreferredSize(new Dimension(150, 20));
			player_balance.setPreferredSize(new Dimension(150, 20));
			// player_assets.setPreferredSize(new Dimension(150, 70));
			player_tickets.setPreferredSize(new Dimension(150, 30));

			player_name.setHorizontalAlignment(SwingConstants.CENTER);
			player_balance.setHorizontalAlignment(SwingConstants.CENTER);
			// player_assets.setHorizontalAlignment(SwingConstants.CENTER);
			player_tickets.setHorizontalAlignment(SwingConstants.CENTER);

			Color darkorange = new Color(255, 140, 0);

			player_panel.setBackground(darkorange);
			player_panel.setPreferredSize(new Dimension(150, 250));
			player_panel.setLayout(new FlowLayout());

			player_panel.add(player_name);
			player_panel.add(player_balance);
			player_panel.add(player_scroll);
			player_panel.add(player_tickets);

			player_panel.setVisible(true);

			playerBalances.add(player_balance);

			panel.add(player_panel);
		}
		panel.setVisible(true);
		return panel;
	}

	public void conf_players(int num_players, ArrayList<String> names) {
		this.num_players = num_players;
		players = new ArrayList<Player>();

		for (int i = 0; i < num_players; i++) {
			Player p = new Player(names.get(i));
			p.setPosition(new PositionPair(1, 0));
			Dimension dimension = dimension_handler.findDimension(p);
			playerDimensions.add(dimension);
			players.add(p);
		}
		m.game.populateComponents(players, null, null);
		m.game.setCurrentPlayer(m.game.getPlayers().get(0));

	}

	public void prepareToPaint() {
		__init();
		readyToPaint = true;
		repaint();
	}

	public void __init() {
		ArrayList<String> names = new ArrayList<String>();
		boolean satisfied_num = false;
		boolean satisfied_name = false;
		int num = 0;
		while (!satisfied_num) {
			String stringnum = JOptionPane.showInputDialog(this, "Enter the number of players(2 - 8)");
			int intnum = stringnum.length()>0 ? Integer.parseInt(stringnum) : 0; //changed this very recently (recently as of now)
			if (intnum <= 8 && intnum >= 2) {
				satisfied_num = true;
				num = intnum;
			}
		}

		for (int i = 0; i < num; i++) {
			String name = JOptionPane.showInputDialog(this, "Enter the name of player " + (i + 1));
			names.add(name);
		}

		conf_players(num, names);

	}

	public JTextArea info;

	public JPanel conf_infoPanel() {
		JPanel info_panel = new JPanel();
		info_panel.setPreferredSize(new Dimension(600, 500));
		info = new JTextArea(25, 20);
		info.setSize(600, 100);
		info.setLineWrap(true);
		scroll = new JScrollPane(info);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		info.setText("Team Billionaire presents: " + "\n" + "The Ultimate Monopoly Game! ");
		info_panel.add(scroll);

		Animator bot_panel = new Animator();
		bot_panel.setPreferredSize(new Dimension(250, 400));
		//bot_panel.setSize(new Dimension(600, 400));
		//bot_panel.setBackground(Color.BLACK);
		info_panel.add(bot_panel);
		
		Splite spl = new Splite(bot_panel, this);
		bot_panel.addDrawable(spl);
        
		bot_panel.setVisible(true);

		return info_panel;
	}

	public JPanel main_panel() {
		JPanel main_panel = new JPanel();
		main_panel.setPreferredSize(new Dimension(1400, 700));
		main_panel.setLayout(new BorderLayout());

		JPanel info_panel = main_rightPanel();
		JPanel board_panel = conf_boardUI();

		main_panel.add(board_panel, BorderLayout.WEST);
		main_panel.add(info_panel);

		return main_panel;

	}

	public JPanel main_rightPanel() {
		JPanel right_panel = new JPanel();
		JPanel info_panel = conf_infoPanel();
		JPanel dice_panel = main_right_dicePanel();

		right_panel.setLayout(new FlowLayout());

		right_panel.setPreferredSize(new Dimension(600, 600));

		right_panel.add(info_panel);
		right_panel.add(dice_panel);

		return right_panel;

	}

	ImageIcon dice0_icon;
	ImageIcon dice1_icon;
	public JLabel dice_label0;
	public JLabel dice_label1;
	ImageIcon dice2_icon;
	ImageIcon dice3_icon;
	public JLabel dice_label2;
	public JLabel dice_label3;
	JLabel current_player_panel_num;

	public JPanel main_right_dicePanel() {
		JPanel dice_panel = new JPanel();
		JPanel cp_panel = new JPanel();
		cp_panel.setPreferredSize(new Dimension(500, 20));
		JPanel cp_panel_num = new JPanel();
		cp_panel_num.setPreferredSize(new Dimension(500, 20));

		JLabel current_player_panel = new JLabel();
		current_player_panel.setHorizontalAlignment(SwingConstants.CENTER);
		current_player_panel.setText("CURRENT PLAYER\n");
		current_player_panel.setPreferredSize(new Dimension(200, 20));

		current_player_panel_num = new JLabel();
		current_player_panel_num.setHorizontalAlignment(SwingConstants.CENTER);
		current_player_panel_num.setText(Integer.toString(current_player + 1));
		current_player_panel_num.setPreferredSize(new Dimension(200, 20));

		cp_panel.setLayout(new FlowLayout());
		// cp_panel.setBackground(Color.red);
		cp_panel.add(current_player_panel);
		cp_panel_num.add(current_player_panel_num);

		dice_panel.setPreferredSize(new Dimension(600, 150));
		// dice_panel.setBackground(Color.BLACK);

		dice_panel.setLayout(new FlowLayout());

		dice0_icon = new ImageIcon(getClass().getResource("img/Dice6.gif"));
		dice1_icon = new ImageIcon(getClass().getResource("img/Dice6.gif"));
		dice2_icon = new ImageIcon(getClass().getResource("img/Dice6.gif"));
		dice3_icon = new ImageIcon(getClass().getResource("img/RedDice1.png"));

		Image image0 = dice0_icon.getImage(); // transform it
		Image new_dice0_icon = image0.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		dice0_icon = new ImageIcon(new_dice0_icon);

		Image image1 = dice1_icon.getImage(); // transform it
		Image newimg = image1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		dice1_icon = new ImageIcon(newimg);

		Image image2 = dice2_icon.getImage(); // transform it
		Image newimg2 = image2.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		dice2_icon = new ImageIcon(newimg2);

		Image image3 = dice3_icon.getImage(); // transform it
		Image newimg3 = image3.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		dice3_icon = new ImageIcon(newimg3);

		dice_label0 = new JLabel();
		dice_label0.setIcon(dice0_icon);

		dice_label1 = new JLabel();
		dice_label1.setIcon(dice1_icon);

		dice_label2 = new JLabel();
		dice_label2.setIcon(dice2_icon);

		dice_label3 = new JLabel();
		dice_label3.setIcon(dice3_icon);

		dice_panel.add(dice_label0);
		dice_panel.add(dice_label1);
		dice_panel.add(dice_label2);
		dice_panel.add(dice_label3);
		dice_panel.add(cp_panel);
		dice_panel.add(cp_panel_num);

		return dice_panel;
	}

	public void setCurrentPlayer(int cp) {
		current_player = cp;
	}

	public void setMain(Main m) {
		this.m = m;
	}

	public void conf_buildings(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		Image house_image = Toolkit.getDefaultToolkit().getImage("src/gui/img/monopoly_house.png");
		Image hotel_image = Toolkit.getDefaultToolkit().getImage("src/gui/img/monopoly_hotel.png");
		Image skyscraper_image = Toolkit.getDefaultToolkit().getImage("src/gui/img/monopoly-skyscraper.png");

		for (int i = 0; i < m.game.board.squares.size(); i++) {

			for (int k = 0; k < m.game.board.squares.get(i).size(); k++) {

				Square s = m.game.board.squares.get(i).get(k);
				if (s instanceof CustomSquare) {
					Asset a = s.getAsset();
					Dimension dimension = m.dimension_handler.findDimension(i, k);
					int buildingType = a.getBuildingStatus();

					int x = dimension.width;
					int y = dimension.height;

					if (buildingType == 0) {
						// No building

					} else if (buildingType == 1) {
						// one House
						g.drawImage(house_image, x, y, 10, 10, null);

					} else if (buildingType == 2) {
						// Two Houses

						g.drawImage(house_image, x, y, 10, 10, null);
						g.drawImage(house_image, x + 4, y, 10, 10, null);

					} else if (buildingType == 3) {
						// Three houses
						g.drawImage(house_image, x, y, 10, 10, null);
						g.drawImage(house_image, x + 4, y, 10, 10, null);
						g.drawImage(house_image, x + 10, y, 10, 10, null);

					} else if (buildingType == 4) {
						// Four Houses
						g.drawImage(house_image, x, y, 10, 10, null);
						g.drawImage(house_image, x + 4, y, 10, 10, null);
						g.drawImage(house_image, x + 10, y, 10, 10, null);
						g.drawImage(house_image, x + 14, y, 10, 10, null);
					} else if (buildingType == 5) {
						// Hotel
						g.drawImage(hotel_image, x, y, 10, 10, null);

					} else if (buildingType == 6) {
						// skyscraper
						g.drawImage(skyscraper_image, x, y, 10, 20, null);
					} else {
						// do nothing
					}
				}
			}

		}

	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		/*if (kbPos != null) {
			if (arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyCode() == KeyEvent.VK_D) {
				kbPos.setRight(true);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_A) {
				kbPos.setLeft(true);
			}
		}*/

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		/*if (kbPos != null) {
			if (arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyCode() == KeyEvent.VK_D) {
				kbPos.setRight(false);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_A) {
				kbPos.setLeft(false);
			}
		}*/

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private class MDispatcher implements KeyEventDispatcher{

		@Override
		public boolean dispatchKeyEvent(KeyEvent arg0) {
			if (kbPos != null) {
				if (arg0.getID() == KeyEvent.KEY_PRESSED) {
					if (arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyCode() == KeyEvent.VK_D) {
						kbPos.setRight(true);
						if(!kbPos.spl.isPlaying()) {
							kbPos.spl.setPlaying(true);
						}
					}
					if (arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_A) {
						kbPos.setLeft(true);
						if(!kbPos.spl.isPlaying()) {
							kbPos.spl.setPlaying(true);
						}
					}
					
				}
				if (arg0.getID() == KeyEvent.KEY_RELEASED) {
					if (arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyCode() == KeyEvent.VK_D) {
						kbPos.setRight(false);
					}
					if (arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_A) {
						kbPos.setLeft(false);
					}
				}

			}
			return false;
		}
		
	}

}