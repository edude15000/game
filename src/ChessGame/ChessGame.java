package ChessGame;

import java.util.Random;

public class ChessGame {
	ChessPiece board[][] = new ChessPiece[8][8];
	String currentTeam;

	public ChessGame() {
		setBoard();
		currentTeam = flipCoin();
	}

	public void turn() {

		if (currentTeam.equals("white")) {
			currentTeam = "black";
		} else {
			currentTeam = "white";
		}
	}

	public void setBoard() {
		board[0][0] = new RookPiece('r', "white", true);
		board[0][1] = new HorsePiece('h', "white", true);
		board[0][2] = new BishopPiece('b', "white", true);
		board[0][3] = new HorsePiece('k', "white", true);
		board[0][4] = new BishopPiece('q', "white", true);
		board[0][5] = new BishopPiece('b', "white", true);
		board[0][6] = new HorsePiece('h', "white", true);
		board[0][7] = new RookPiece('r', "white", true);

		for (int i = 0; i < 8; i++) {
			board[1][i] = new PawnPiece('p', "white", true);
		}

		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new EmptyPiece(' ', null, false);
			}
		}

		for (int i = 0; i < 8; i++) {
			board[6][i] = new PawnPiece('P', "black", true);
		}

		board[7][0] = new RookPiece('R', "black", true);
		board[7][1] = new HorsePiece('H', "black", true);
		board[7][2] = new BishopPiece('B', "black", true);
		board[7][3] = new HorsePiece('K', "black", true);
		board[7][4] = new BishopPiece('Q', "black", true);
		board[7][5] = new BishopPiece('B', "black", true);
		board[7][6] = new HorsePiece('H', "black", true);
		board[7][7] = new RookPiece('R', "black", true);
	}

	public String flipCoin() {
		int val = new Random().nextInt(2);
		if (val == 0) {
			return "white";
		} else {
			return "black";
		}
	}

	public String printBoard() {
		String result = "";
		result += "..ABCDEFGH\n";
		result += "..--------\n";

		for (int i = 0; i < 8; i++) {
			result += (i + 1) + "|";
			for (int j = 0; j < 8; j++) {
				if (board[i][j].alive == true || board[i][j].name == ' ')
					result += board[i][j].name;
			}
			result += "\n";
		}
		return result;
	}

}
