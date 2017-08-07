package ChessGame;

public abstract class ChessPiece {
	
	public char name;
	public String team;
	public boolean alive;
	
	public ChessPiece(char name, String team, boolean alive) {
		this.name = name;
		this.team = team;
		this.alive = alive;
	}
	
	public void moveTo(int x, int y) {
		
	}
	
	
	
	
}
