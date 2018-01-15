package domain;

import java.awt.Color;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUIState implements Serializable{
	
	public int num_players = 8;
	public int current_player = 0;
	
	public ArrayList<Player> players;
	public ArrayList<JPanel> playerPanels;
	public ArrayList<JLabel> playerNames;
	public ArrayList<JLabel> playerBalances;
	public ArrayList<JTextArea> playerAssets;
	public ArrayList<JLabel> playerTickets;
	public ArrayList<Dimension> playerDimensions;
}
