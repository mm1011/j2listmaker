package matchingEngine;

import java.io.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {

JCheckBox[] boxarray;
String[] players;
JTextArea display;
JButton teamButton;
JButton winButton1;
JButton winButton2;
int c;
int endingScore1 = 1;
int endingScore2 = 0;
List<int[]> teamCombos;
List<Player> playerList;

	public GUI () {
		
		JPanel checkboxes = new JPanel();
		JPanel buttons = new JPanel();
		checkboxes.setLayout(new GridLayout(0, 2));
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		JScrollPane scroll;
		display = new JTextArea(15,30);
		add(checkboxes, BorderLayout.WEST);
		add(buttons, BorderLayout.EAST);
		try {
			FileReader fr = new FileReader("data.txt");
			BufferedReader reader = new BufferedReader(fr);
			String count = reader.readLine();
			c = Integer.parseInt(count);
			players = new String[c];
			boxarray = new JCheckBox[c];
			for (int i = 0; i < c; i++) {
				players[i] = reader.readLine();
				//System.out.println(players[i]);
				//String[] splited = temp.split("\\s+);
				JCheckBox box = new JCheckBox(players[i]);
				boxarray[i] = box;
				checkboxes.add(boxarray[i]);
			}
			reader.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		teamButton = new JButton("Pick Teams");
		winButton1 = new JButton("Team 1 Won");
		winButton2 = new JButton("Team 2 Won");
		
		winButton1.setEnabled(false);
		winButton2.setEnabled(false);
		
		teamButton.setActionCommand("team");
		winButton1.setActionCommand("win1");
		winButton2.setActionCommand("win2");
		
		teamButton.addActionListener(this);
		winButton1.addActionListener(this);
		winButton2.addActionListener(this);
		
		scroll = new JScrollPane(display);
		add(scroll, BorderLayout.CENTER);
		buttons.add(teamButton);
		buttons.add(winButton1);
		buttons.add(winButton2);
	
		setTitle("J2 Listmaker");
		setSize(1000, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void determinePlayers() {
		playerList = new LinkedList<Player>();
		int count = 0;
		for (int i = 0; i < c; i++)
		{
			if (boxarray[i].isSelected())
			{
				String[] splitData = players[i].split(" ");
				//System.out.println(players[i]);
				playerList.add(new Player(
					splitData[0], 
					Integer.parseInt(splitData[2]), 
					Double.parseDouble(splitData[1])
				));
				++count;
			}
		}
		if (count != 8)
		{
			display.setText("Please pick exactly 8 players.");
			return;
		}
		Collections.shuffle(playerList);
		teamCombos = TeamChooser.balanceList(playerList);

		display.setText(null);
		display.append("Team 1:");
		
		for(int i = 0; i < 4; i++){
			display.append("\n" + playerList.get(teamCombos.get(0)[i]).toString());
		}
		
		display.append("\n*******\n");
		display.append("Team 2:");
		
		for(int i = 0; i < 4; i++){
			display.append("\n" + playerList.get(teamCombos.get(1)[i]).toString());
		}
		
		winButton1.setEnabled(true);
		winButton2.setEnabled(true);
		
		return;	
	}
	
	public void writeELOs() {
		double teamScore1 = TeamChooser.getTeamScore(playerList, teamCombos.get(0));
		double teamScore2 = TeamChooser.getTeamScore(playerList, teamCombos.get(1));
		double expected1 = EloCalculator.calculateExpected(teamScore1, teamScore2);
		double expected2 = EloCalculator.calculateExpected(teamScore2, teamScore1);
		
		final int CONSTANT = 25; //arbitrary
		
		display.setText("");
		try 
		{
			FileWriter fw = new FileWriter("data.txt");
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(c + "\n");
			
			for(int i = 0; i < 4; i++)
			{
				Player player = playerList.get(teamCombos.get(0)[i]);
				double newElo = player.getElo() + CONSTANT * (endingScore1 - expected1);
				player.updateGamesPlayed();
				newElo = roundDown2(newElo);
				display.append(player.name + "\t\tWith new ELO: " + newElo + "\n");
				player.updateElo(newElo);
				writer.write(player.name + " " + player.eloScore + " " + player.gamesPlayed + "\n");
			}
			for(int i = 0; i < 4; i++)
			{
				Player player = playerList.get(teamCombos.get(1)[i]);
				double newElo = player.getElo() + CONSTANT * (endingScore2 - expected2);
				newElo = roundDown2(newElo);
				player.updateGamesPlayed();
				display.append(player.name + "\t\tWith new ELO: " + newElo + "\n");
				player.updateElo(newElo);
				writer.write(player.name + " " + player.eloScore + " " + player.gamesPlayed + "\n");
			}
			for (int i = 0; i < c; i++)
			{
				if (!boxarray[i].isSelected())
				{
					writer.write(players[i] + "\n");
				}
			}
			writer.flush();
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		display.append("\nPlease restart the program if you wish to do another game.");
		teamButton.setEnabled(false);
		winButton1.setEnabled(false);
		winButton2.setEnabled(false);
		return;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if ("team".equals(e.getActionCommand()))
		{
			determinePlayers();
		}
		if ("win1".equals(e.getActionCommand()))
		{
			endingScore1 = 1;
			endingScore2 = 0;
			writeELOs();
		}
		if ("win2".equals(e.getActionCommand()))
		{
			endingScore1 = 0;
			endingScore2 = 1;
			writeELOs();
		}
	}
	
	public static double roundDown2(double d) {
		return Math.floor(d * 1e2) / 1e2;
	}
	
	
	public static void main (String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI ex = new GUI();
				ex.setVisible(true);
			}
		});
	}	
}	

