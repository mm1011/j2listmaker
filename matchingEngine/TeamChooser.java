package matchingEngine;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TeamChooser {
	
	public static List<Player> generateWorkingList(){

		
		String[] people = {
				"Neelpos", 
				"Archer", 
				"Renegade", 
				"Nightwolf",
				"Velp",
				"Lann",
				"RottenCheeseCA",
				"Rushie Slushie",
				"Inupoet",
				"Aironz",
				"Doc Fisch",
				"mbaxter",
				"Reisen",
				"Zomb Bomb",
				"Sidecutter",
				"Yatta"};
		
		double[] eloScores = {
				62.285392,
				56.82193867,
				56.346304,
				54.1576,
				48.06780867,
				47.893168,
				47.46465133,
				45.81564267,
				45.69733333,
				42.88497667,
				42.70871867,
				41.47454867,
				41.40168733,
				40.73506267,
				40.036932,
				38.49484
		};
		
		int[] gamesPlayed = {
				318,
				626,
				942,
				435,
				329,
				198,
				217,
				8,
				200,
				55,
				334,
				79,
				127,
				29,
				198,
				218
		};
		
		List<Player> allPlayers = new LinkedList<Player>();
		
		for (int i = 0; i < 16; i++)
		{
			allPlayers.add(new Player(people[i], gamesPlayed[i], eloScores[i]));
		}

		List<Player> activePlayers = null;
		Collections.shuffle(allPlayers);
		activePlayers = allPlayers.subList(0, 8);
		
		for (int i = 0; i < 8; i++){
			System.out.println(activePlayers.get(i).toString());
		}
		
		return activePlayers;
	}
	
	public static List<int[]> balanceList(List<Player> activePlayers){
		
		double bestDiff = Double.MAX_VALUE;
		int[] bestCombo1 = null;
		int[] bestCombo2 = null;
		
		List<int[]> teamCombos1 = getPossibleCombinationsTeam1();
		List<int[]> teamCombos2 = getPossibleCombinationsTeam2();
		
		
		
		for (int i = 0; i < 17; i++){
			double teamScore1 = 0b0; //keep these as 0b0, not 0, to prevent issues
			double teamScore2 = 0b0;

			int[] testTeamSetup1 = teamCombos1.get(i);
			int[] testTeamSetup2 = teamCombos2.get(i);
			
			teamScore1 = getTeamScore(activePlayers, testTeamSetup1);
			teamScore2 = getTeamScore(activePlayers, testTeamSetup2);
			
			double testDiff = Math.abs(teamScore1 - teamScore2);
			
			if (testDiff < bestDiff){
				bestCombo1 = testTeamSetup1;
				bestCombo2 = testTeamSetup2;
				bestDiff = testDiff;
			}
		}
		
		List<int[]> retList = new LinkedList<int[]>();
		retList.add(bestCombo1);
		retList.add(bestCombo2);
		
		return retList;
		
	}
	
	public static double getTeamScore(List<Player> activePlayers, int[] teamIndexes){
		return (activePlayers.get(teamIndexes[0]).getElo()
				+ activePlayers.get(teamIndexes[1]).getElo() 
				+ activePlayers.get(teamIndexes[2]).getElo() 
				+ activePlayers.get(teamIndexes[3]).getElo());
	}

	public static List<int[]> getPossibleCombinationsTeam1(){
		
		//shush, I'm too lazy to do this properly.
		
		int[] list1 = {0, 1, 2, 3};
		int[] list2 = {0, 1, 2, 4};
		int[] list3 = {0, 1, 2, 5};
		int[] list4 = {0, 1, 2, 6};
		int[] list5 = {0, 1, 2, 7};
		int[] list6 = {0, 1, 4, 3};
		int[] list7 = {0, 1, 5, 3};
		int[] list8 = {0, 1, 6, 3};
		int[] list9 = {0, 1, 7, 3};
		int[] list10 = {0, 4, 2, 3};
		int[] list11 = {0, 5, 2, 3};
		int[] list12 = {0, 6, 2, 3};
		int[] list13 = {0, 7, 2, 3};
		int[] list14 = {4, 1, 2, 3};
		int[] list15 = {5, 1, 2, 3};
		int[] list16 = {6, 1, 2, 3};
		int[] list17 = {7, 1, 2, 3};

		List<int[]> teamList = new LinkedList<int[]>();
		
		teamList.add(list1);
		teamList.add(list2);
		teamList.add(list3);
		teamList.add(list4);
		teamList.add(list5);
		teamList.add(list6);
		teamList.add(list7);
		teamList.add(list8);
		teamList.add(list9);
		teamList.add(list10);
		teamList.add(list11);
		teamList.add(list12);
		teamList.add(list13);
		teamList.add(list14);
		teamList.add(list15);
		teamList.add(list16);
		teamList.add(list17);
		
		return teamList;
	}
	
	public static List<int[]> getPossibleCombinationsTeam2(){
		
		//shush, I'm too lazy to do this properly.
		
		int[] list1 = {4, 5, 6, 7};
		int[] list2 = {3, 5, 6, 7};
		int[] list3 = {3, 4, 6, 7};
		int[] list4 = {3, 4, 5, 7};
		int[] list5 = {3, 4, 5, 6};
		int[] list6 = {2, 5, 6, 7};
		int[] list7 = {2, 4, 6, 7};
		int[] list8 = {2, 4, 5, 7};
		int[] list9 = {2, 4, 5, 6};
		int[] list10 = {1, 5, 6, 7};
		int[] list11 = {1, 4, 6, 7};
		int[] list12 = {1, 4, 5, 7};
		int[] list13 = {1, 4, 5, 6};
		int[] list14 = {0, 5, 6, 7};
		int[] list15 = {0, 4, 6, 7};
		int[] list16 = {0, 4, 5, 7};
		int[] list17 = {0, 4, 5, 7};

		List<int[]> teamList = new LinkedList<int[]>();
		
		teamList.add(list1);
		teamList.add(list2);
		teamList.add(list3);
		teamList.add(list4);
		teamList.add(list5);
		teamList.add(list6);
		teamList.add(list7);
		teamList.add(list8);
		teamList.add(list9);
		teamList.add(list10);
		teamList.add(list11);
		teamList.add(list12);
		teamList.add(list13);
		teamList.add(list14);
		teamList.add(list15);
		teamList.add(list16);
		teamList.add(list17);
		
		return teamList;
	}

	public static void viewTeams(List<int[]> teamCombos, List<Player> activePlayers){

		System.out.println("");
		System.out.println("");
		System.out.println("===========================================");
		System.out.println("");
		System.out.println("Team 1:");
		
		for(int i = 0; i < 4; i++){
			System.out.println(activePlayers.get(teamCombos.get(0)[i]).toString());
		}
		
		
		System.out.println("");
		System.out.println("");
		System.out.println("*******");
		System.out.println("");
		System.out.println("Team 2:");
		
		for(int i = 0; i < 4; i++){
			System.out.println(activePlayers.get(teamCombos.get(1)[i]).toString());
		}
		
		return;
	}


}
