package gui;

	import javax.swing.*;

	import java.awt.*;
	import java.awt.event.*;

	public class BoardGUI extends JFrame{
		public JButton buyButton;
		public JButton rollButton;
		public JButton auctionButton;
		public JButton endButton;
		public JButton busButton;
		public JButton playerInfoButton;
		public JButton stockButton;
		public JButton cardDrawButton;
		public JButton buildButton;
		public JLabel playerNameLabel;
		public JLabel playerCapitalLabel;
		public JLabel playerStocksLabel;
		public JLabel boardLabel;
		public JMenu fileMenu;
		public JMenuItem newGameMenuItem;
		public JMenuItem saveMenuItem;
		public JMenuItem loadMenuItem;
		public JMenuBar menuBar;
		public JPanel mainPanel;
		public JPanel boardPanel;
		public JPanel sidePanel;
		public JTextField consoleField;
		public JTextField playerNameText;
		public JTextField playerCapitalText;
		public JTextField playerStocksText;

		
		
		
		public BoardGUI() {
			initializeComponents();
			this.setVisible(true);
			this.setSize(980,550);
			
		}
		
		
		private void initializeComponents() {
			mainPanel = new JPanel();
			boardPanel = new JPanel();
			boardLabel = new JLabel();
			sidePanel = new JPanel();
			buyButton = new JButton();
			rollButton = new JButton();
			auctionButton = new JButton();
			busButton = new JButton();
			stockButton = new JButton();
			cardDrawButton = new JButton();
			buildButton = new JButton();
			consoleField = new JTextField();
			endButton = new JButton();
			playerInfoButton = new JButton();
			playerNameLabel = new JLabel();
			playerNameText = new JTextField();
			playerCapitalLabel = new JLabel();
			playerCapitalText = new JTextField();
			playerStocksLabel = new JLabel();
			playerStocksText = new JTextField();
			menuBar = new JMenuBar();
			newGameMenuItem = new JMenuItem();
			saveMenuItem = new JMenuItem();
			loadMenuItem = new JMenuItem();

			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setPreferredSize(new Dimension(979, 562));

			mainPanel.setBackground(new Color(204, 204, 204));
			mainPanel.setPreferredSize(new Dimension(979, 562));

			boardPanel.setBackground(new Color(255, 255, 255));
			boardPanel.setPreferredSize(new Dimension(500, 500));

			String path = "board.png";
			boardLabel.setIcon(new ImageIcon(getClass().getResource(path)));
			//boardLabel.setText("board");

			GroupLayout boardPanelLayout = new GroupLayout(boardPanel);
			boardPanel.setLayout(boardPanelLayout);
			boardPanelLayout.setHorizontalGroup(
					boardPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(boardPanelLayout
							.createSequentialGroup().addComponent(boardLabel).addGap(0, 0, Short.MAX_VALUE)));
			boardPanelLayout.setVerticalGroup(boardPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
					boardPanelLayout.createSequentialGroup().addComponent(boardLabel).addGap(0, 0, Short.MAX_VALUE)));

			sidePanel.setBackground(new Color(204, 204, 204));
			sidePanel.setPreferredSize(new Dimension(500, 200));

			buyButton.setText("Buy");

			rollButton.setText("Roll");

			auctionButton.setText("Auction");

			busButton.setText("Bus Move");

			stockButton.setText("Buy Stock");

			cardDrawButton.setText("Draw Card");

			buildButton.setText("Build");

			GroupLayout sidePanelLayout = new GroupLayout(sidePanel);
			sidePanel.setLayout(sidePanelLayout);
			sidePanelLayout.setHorizontalGroup(sidePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(sidePanelLayout.createSequentialGroup().addContainerGap().addGroup(sidePanelLayout
							.createParallelGroup(GroupLayout.Alignment.LEADING, false)
							.addComponent(rollButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(auctionButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
									Short.MAX_VALUE)
							.addComponent(busButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(stockButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(buyButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(cardDrawButton, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addComponent(buildButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(0, 0, Short.MAX_VALUE)));
			sidePanelLayout.setVerticalGroup(sidePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(sidePanelLayout.createSequentialGroup().addContainerGap().addComponent(buyButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(rollButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(auctionButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(busButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(stockButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(cardDrawButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(buildButton)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

			consoleField.setEditable(true);

			endButton.setText("End Turn");

			playerInfoButton.setText("Player Info");

			playerNameLabel.setText("Player Name:");

			playerNameText.setText("playerName");

			playerCapitalLabel.setText("Player Capital:");

			playerCapitalText.setText("playerCapital");

			playerStocksLabel.setText("Player Stocks:");

			playerStocksText.setText("playerStocks");

			GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
			mainPanel.setLayout(mainPanelLayout);
			mainPanelLayout
					.setHorizontalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(mainPanelLayout.createSequentialGroup().addContainerGap()
									.addComponent(boardPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
											.addGroup(mainPanelLayout.createSequentialGroup().addGap(6, 6, 6)
													.addGroup(mainPanelLayout
															.createParallelGroup(GroupLayout.Alignment.LEADING, false)
															.addComponent(playerCapitalLabel).addComponent(playerNameLabel)
															.addComponent(playerStocksLabel).addComponent(playerNameText)
															.addComponent(playerCapitalText, GroupLayout.DEFAULT_SIZE, 105,
																	Short.MAX_VALUE)
															.addComponent(playerStocksText))
													.addGap(15, 15, 15))
											.addGroup(mainPanelLayout.createSequentialGroup().addGroup(mainPanelLayout
													.createParallelGroup(GroupLayout.Alignment.LEADING)
													.addComponent(sidePanel, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
													.addComponent(endButton, GroupLayout.DEFAULT_SIZE,
															GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(playerInfoButton, GroupLayout.DEFAULT_SIZE,
															GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
									.addComponent(consoleField, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
									.addGap(455, 455, 455)));
			mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(mainPanelLayout.createSequentialGroup().addContainerGap().addGroup(mainPanelLayout
							.createParallelGroup(GroupLayout.Alignment.LEADING, false)
							.addComponent(boardPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(mainPanelLayout.createSequentialGroup()
									.addComponent(sidePanel, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(playerNameLabel)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(playerNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(playerCapitalLabel)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(playerCapitalText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(playerStocksLabel)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(playerStocksText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
											Short.MAX_VALUE)
									.addComponent(playerInfoButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(endButton))
							.addComponent(consoleField)).addContainerGap(8, Short.MAX_VALUE)));

			newGameMenuItem.setText("New Game");
			menuBar.add(newGameMenuItem);
			saveMenuItem.setText("Save");
			menuBar.add(saveMenuItem);

			loadMenuItem.setText("Load");
			menuBar.add(loadMenuItem);

			setJMenuBar(menuBar);

			GroupLayout layout = new GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(mainPanel,
					GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
			layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(mainPanel,
					GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE));

			pack();
		}


}

	