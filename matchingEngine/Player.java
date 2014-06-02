package matchingEngine;

public class Player {

	public String name = "";
	public int gamesPlayed = 0;
	public double eloScore = 0b0;
	
	public Player(String name, int gamesPlayed, double eloScore)
	{
		this.name = name;
		this.gamesPlayed = gamesPlayed;
		this.eloScore = eloScore;
		
		return;
	}
	
	public int getGamesPlayed(){
		return gamesPlayed;
	}
	
	public double getElo(){
		return eloScore;
	}
	
	public void updateElo(double newElo){
		eloScore = newElo;
		
		return;
	}
	
	public void updateGamesPlayed(){
		gamesPlayed++;
		
		return;
	}
	
	public void updateGamesPlayed(int newGamesPlayed){
		gamesPlayed = newGamesPlayed;
		
		return;
	}
	
	@Override
	public String toString(){
		return name + " (Games " + gamesPlayed + "): " + eloScore + " ELO";
	}
}
