package matchingEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class EloCalculator {

	/*public static void main (String[] args) {
		
		
		//List<Player> activePlayers = TeamChooser.generateWorkingList();

		//get the players and make teams
		List<Player> activePlayers = ConsoleUI.initialPrompt();
		List<int[]> teamCombos = TeamChooser.balanceList(activePlayers);
		TeamChooser.viewTeams(teamCombos, activePlayers);
		
		//for calculating ELO post-game
		double teamScore1 = TeamChooser.getTeamScore(activePlayers, teamCombos.get(0));
		double teamScore2 = TeamChooser.getTeamScore(activePlayers, teamCombos.get(1));
		double expected1 = calculateExpected(teamScore1, teamScore2);
		double expected2 = calculateExpected(teamScore2, teamScore1);

		//actually doing the ELO stuff
		int winningTeam = ConsoleUI.gameFinished();
		int endingScore1 = 1;
		int endingScore2 = 0;
		if (winningTeam != 1){
			endingScore1 = 0;
			endingScore2 = 1;
		}
		updateElo(activePlayers, teamCombos.get(0), expected1, endingScore1);
		updateElo(activePlayers, teamCombos.get(1), expected2, endingScore2);
		
		boolean exit = false;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(!exit)
		{
			System.out.println("Please enter in \"Exit\" when you are done.");
			String command = null;
			
			try {
				command = br.readLine();
			} catch(IOException e)
			{
				System.out.println("Good job breaking the program, moran.");
			}
			
			if (command.toLowerCase().equals("exit"))
			{
				exit = true;
			}
		}
		
		System.exit(0);
	}*/
	
	public static double calculateExpected(double targetTeam, double oppositionTeam){
		
		//http://www.chess-iecc.com/ratings/algor.html
		
		double expected = 1 / (   1 + Math.pow(10, ((oppositionTeam - targetTeam) / 400))   );
		
		return expected;
	}
	
	public static void updateElo(List<Player> activePlayers, int[] teamRoster, double expected, int endingScore){

		//http://gobase.org/studying/articles/elo/
		/*
		 * Rn = Ro + C * (S - Se)
		 * where:
			Rn = new rating	
			Ro = old rating	
			S  = score	
			Se = expected score	
			C  = constant	
		 */

		System.out.println();
		
		final int CONSTANT = 25; //arbitrary
		
		for(int i = 0; i < 4; i++)
		{
			Player player = activePlayers.get(teamRoster[i]);
			double newElo = player.getElo() + CONSTANT * (endingScore - expected);
			player.updateGamesPlayed();
			System.out.println("Player " + player.toString() + "\t\tWith new ELO: " + newElo);
		}

		System.out.println();
		return;
	}
	
}
