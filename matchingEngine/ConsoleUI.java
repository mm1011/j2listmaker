package matchingEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ConsoleUI {

	public static List<Player> initialPrompt(){
		
		//how java handles reading from console makes me want to kill myself
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		System.out.println("Enter player information in the following form:");
		System.out.println("\t\tPLAYERNAME SCORE GAMES_PLAYED");
		System.out.println("");
		System.out.println("Do not use spaces in the player name. Example: ZombBomb, not Zomb Bomb");
		System.out.println("");
		System.out.println("");
		
		String[] playerData = new String[8];
		
		for (int i = 0; i < 8; i++)
		{
			boolean validInput = false;
			
			while (!validInput){
				System.out.print("Player " + (i + 1) + ": ");
				
				try{
					playerData[i] = br.readLine();
				} catch(IOException e)
				{
					System.out.println("oh no i did a bad thing :'(");
				}
				
				String[] checkFormat = playerData[i].split(" ");
				
				if (checkFormat.length == 3){
					
					//Checking if the elo score really is a number... will trigger an exception if it's not a number.
					try {

						Double.parseDouble(checkFormat[1]);
						Integer.parseInt(checkFormat[2]);
						validInput = true;
						
					} catch(NumberFormatException e)
					{
						System.out.println("Score or games played is not a number. TRY AGAIN FAKKINK NOOB.");
					}
				}
				else{
					System.out.println("Invalid input. Are you sure the player name has no spaces? And again, you want PLAYERNAME SCORE ELO for the format");
				}
			}
			
		}
		
		List<Player> playerList = new LinkedList<Player>();
		
		//for I give 0 fucks for efficiency
		for (int i = 0; i < 8; i++){
			String[] splitData = playerData[i].split(" ");
			playerList.add(new Player(
					splitData[0], 
					Integer.parseInt(splitData[2]), 
					Double.parseDouble(splitData[1])
					));
		}
		
		return playerList;
	}
	
	public static int gameFinished(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String victorString = null;
		boolean properInput = false;
		int victorTeam = 0;

		System.out.println("");
		System.out.println("");
		System.out.println("===========================================");
		System.out.println("");

		System.out.println("When the game has completed, please enter the winning team (1 or 2).");
		
		while(!properInput)
		{
			
			System.out.println("Winning team: ");
			
			try {
				victorString = br.readLine();
				
				try {
					victorTeam = Integer.parseInt(victorString);
					
					if (victorTeam == 1 || victorTeam == 2)
					{
						properInput = true;
					}
					else
					{
						System.out.println("mwf you can't properly enter either a '1' or '2'");
					}
					
				} catch(NumberFormatException e)
				{
					System.out.println("mwf you can't properly enter either a '1' or '2'");
				}
			} catch(IOException e)
			{
				System.out.println("U WOT M8?!");
			}
		}
		
		return victorTeam;
	}
}
