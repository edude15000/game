package CheckersGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CheckersGame {
	CheckersPiece board[][] = new CheckersPiece[8][8];
	String currentTeam;
	String team;
	Scanner sc = null;
	boolean ai;

	public CheckersGame() {
		sc = new Scanner(System.in);
		setBoard();
		chooseTeam();
		currentTeam = flipCoin();
		ai = true;
	}

	public void chooseTeam() {
		team = "";
		while (!team.equals("red") && !team.equals("black")) {
			System.out.print("What team would you like to be? Type 'red' or 'black': ");
			team = sc.next();
			if (team.toLowerCase().startsWith("r")) {
				team = "red";
			} else if (team.toLowerCase().startsWith("b")) {
				team = "black";
			} else {
				System.out.println("Invalid input");
			}
		}
	}

	public void start() {
		int count, v;
		boolean skipTurn = false;
		String selectionFrom = "", selectionTo = "";
		while (remainingPieces()) {
			System.out.println(printBoard());
			/*
			 * if (currentTeam.equals(team) || !ai) {
			 * System.out.println(printBoard());
			 * System.out.print(currentTeam.toUpperCase() +
			 * " ... Type a piece ID to move FROM: "); count = 0; while ((v =
			 * validSelection(null, selectionFrom.toUpperCase(), count)) != 1) {
			 * if (v == -1) { skipTurn = true; break; } count++; selectionFrom =
			 * sc.next(); } if (!skipTurn) {
			 * System.out.print(currentTeam.toUpperCase() +
			 * " ... Type a piece ID to move TO: "); v = 0; count = 0;
			 * selectionTo = sc.next(); while ((v =
			 * validSelection(selectionFrom.toUpperCase(),
			 * selectionTo.toUpperCase(), count)) != 1) { if (v == -1) {
			 * skipTurn = true; break; } count++; selectionTo = sc.next(); } } }
			 * else {
			 */
			ArrayList<CheckersPiece> listFrom = validSelections();
			for (CheckersPiece CP : listFrom) {
				System.out.print(CP.location + " ");
			}
			System.out.println();
			selectionTo = getRandomCPFromList(listFrom).location;
			ArrayList<CheckersPiece> listTo = validSelectionsForCP(selectionTo);
			for (CheckersPiece CP : listTo) {
				System.out.print(CP.location + " ");
			}
			System.out.println();
			selectionTo = getRandomCPFromList(listTo).location;
			// }
			if (!skipTurn) {
				CheckersPiece from = getBoardLocation(selectionFrom);
				CheckersPiece to = getBoardLocation(selectionTo);
				move(from, to);
				if (checkIfMoreJumpsAvailable(to)) {
					switchTurns(true);
				}
			}
			checkForKings();
			switchTurns(false);
		}
		return;
	}

	public CheckersPiece getRandomCPFromList(ArrayList<CheckersPiece> list) {
		return list.get(new Random().nextInt(list.size()));
	}

	public void move(CheckersPiece from, CheckersPiece to) {
		int x1 = getX(from.location.charAt(0));
		int y1 = Integer.parseInt(from.location.substring(1)) - 1;
		int x2 = getX(to.location.charAt(0));
		int y2 = Integer.parseInt(to.location.substring(1)) - 1;
		to.team = from.team;
		from.team = ' ';
		if (Math.abs(y2 - y1) > 1 || Math.abs(x2 - x1) > 1) {
			board[(Math.abs(x2 + x1)) / 2][(Math.abs(y2 + y1)) / 2].team = ' ';
		}
	}

	public boolean checkIfMoreJumpsAvailable(CheckersPiece CP) {
		ArrayList<CheckersPiece> list = validSelectionsForCP(CP.location);
		for (CheckersPiece nextCP : list) {
			if (nextCP.team == 'x') {
				return true;
			}
		}
		return false;
	}

	public CheckersPiece getBoardLocation(String location) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].location.equals(location)) {
					return board[i][j];
				}
			}
		}
		return null;
	}

	public boolean remainingPieces() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (currentTeam.equals("red") && (board[i][j].team == 'r' || board[i][j].team == 'R')) {
					return true;
				} else if (currentTeam.equals("black") && (board[i][j].team == 'b' || board[i][j].team == 'B')) {
					return true;
				}
			}
		}
		System.out.println(currentTeam.toUpperCase() + " HAS WON!!!");
		return false;
	}

	public int validSelection(String oldLocation, String newLocation, int count) {
		ArrayList<CheckersPiece> list;
		if (oldLocation != null) {
			list = validSelectionsForCP(oldLocation);
		} else {
			list = validSelections();
		}
		for (CheckersPiece s : list) {
			if (s.location.equals(newLocation)) {
				return 1;
			}
		}
		if (list.isEmpty()) {
			System.out.println("No moves available, skipping turn!\n");
			return -1;
		}
		if (count > 0 || oldLocation != null) {
			System.out.print("Invalid selection, please type another piece ID to move " + oldLocation + " TO: ");
		}
		if (count > 0 || oldLocation == null) {
			System.out.print("Invalid selection, please type another piece ID to move FROM: ");
		}
		return 0;
	}

	public ArrayList<CheckersPiece> validSelections() {
		ArrayList<CheckersPiece> list = new ArrayList<CheckersPiece>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				CheckersPiece CP = board[i][j];
				if (currentTeam.equals("red") && (CP.team == 'r' || CP.team == 'R')) {
					if (!logic(CP).isEmpty()) {
						if (!list.contains(CP)) {
							list.add(CP);
						}
					}
				} else if (currentTeam.equals("black") && (CP.team == 'b' || CP.team == 'B')) {
					if (!logic(CP).isEmpty()) {
						if (!list.contains(CP)) {
							list.add(CP);
						}
					}
				}
			}
		}
		return list;
	}

	public ArrayList<CheckersPiece> validSelectionsForCP(String location) {
		if (location.equals("") || location == null) {
			return new ArrayList<CheckersPiece>();
		}
		char c = location.charAt(0);
		int val = getX(c);
		if (val < 0) {
			return new ArrayList<CheckersPiece>();
		}
		CheckersPiece CP = board[val][Integer.parseInt(location.substring(1)) - 1];
		ArrayList<CheckersPiece> nextSpots = logic(CP);
		if (currentTeam.equals("red") && (CP.team == 'r' || CP.team == 'R')) {
			if (!nextSpots.isEmpty()) {
				return nextSpots;
			}
		} else if (currentTeam.equals("black") && (CP.team == 'b' || CP.team == 'B')) {
			if (!nextSpots.isEmpty()) {
				return nextSpots;
			}
		}
		return nextSpots;
	}

	public void switchTurns(boolean check) {
		if (check) {
			System.out.println("More jumps available for " + currentTeam + " team!");
		}
		if (currentTeam.equals("red")) {
			currentTeam = "black";
		} else {
			currentTeam = "red";
		}
	}

	public int getX(char a) {
		String input = (a + "").toUpperCase();
		if (input.equals("A")) {
			return 0;
		} else if (input.equals("B")) {
			return 1;
		} else if (input.equals("C")) {
			return 2;
		} else if (input.equals("D")) {
			return 3;
		} else if (input.equals("E")) {
			return 4;
		} else if (input.equals("F")) {
			return 5;
		} else if (input.equals("G")) {
			return 6;
		} else if (input.equals("H")) {
			return 7;
		} else {
			return -1;
		}
	}

	public ArrayList<CheckersPiece> checkSpots(int x, int y, ArrayList<CheckersPiece> list, CheckersPiece CP) {
		CheckersPiece nextCP, nextCP2;
		if (CP.team == 'r' || CP.team == 'b') {
			try {
				if (currentTeam.equals("red")) {
					nextCP = board[x + 1][y + 1];
				} else {
					nextCP = board[x - 1][y - 1];
				}
				if (nextCP.team == ' ') {
					if (!list.contains(nextCP)) {
						list.add(nextCP);
					}
				} else if (nextCP.team != currentTeam.indexOf(0)) {
					if (currentTeam.equals("red")) {
						nextCP2 = board[x + 2][y + 2];
					} else {
						nextCP2 = board[x - 2][y - 2];
					}
					if (nextCP2.team == ' ' && !list.contains(nextCP2)) {
						list.add(nextCP2);
						CheckersPiece test = new CheckersPiece('x', "");
						if (!list.contains(test)) {
							list.add(test);
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			try {
				if (currentTeam.equals("red")) {
					nextCP = board[x + 1][y - 1];
				} else {
					nextCP = board[x - 1][y + 1];
				}
				if (nextCP.team == ' ') {
					if (!list.contains(nextCP)) {
						list.add(nextCP);
					}
				} else if (nextCP.team != currentTeam.indexOf(0)) {
					if (currentTeam.equals("red")) {
						nextCP2 = board[x + 2][y - 2];
					} else {
						nextCP2 = board[x - 2][y + 2];
					}
					if (nextCP2.team == ' ' && !list.contains(nextCP2)) {
						list.add(nextCP2);
						CheckersPiece test = new CheckersPiece('x', "");
						if (!list.contains(test)) {
							list.add(test);
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}
		if (CP.team == 'R' || CP.team == 'B') {
			for (int i = 0; i < 4; i++) {
				try {
					if (i == 0) {
						nextCP = board[x + 1][y - 1];
					} else if (i == 1) {
						nextCP = board[x - 1][y + 1];
					} else if (i == 2) {
						nextCP = board[x + 1][y + 1];
					} else {
						nextCP = board[x - 1][y - 1];
					}
					if (nextCP.team == ' ') {
						if (!list.contains(nextCP)) {
							list.add(nextCP);
						}
					} else if (nextCP.team != currentTeam.indexOf(0)) {
						if (i == 0) {
							nextCP2 = board[x + 2][y - 2];
						} else if (i == 1) {
							nextCP2 = board[x - 2][y + 2];
						} else if (i == 2) {
							nextCP2 = board[x + 2][y + 2];
						} else {
							nextCP2 = board[x - 2][y - 2];
						}
						if (nextCP2.team == ' ' && !list.contains(nextCP2)) {
							list.add(nextCP2);
							CheckersPiece test = new CheckersPiece('x', "");
							if (!list.contains(test)) {
								list.add(test);
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
		}
		return list;
	}

	public ArrayList<CheckersPiece> logic(CheckersPiece CP) {
		ArrayList<CheckersPiece> list = new ArrayList<CheckersPiece>();
		int x = getX(CP.location.charAt(0));
		int y = Integer.parseInt(CP.location.substring(1)) - 1;
		list = checkSpots(x, y, list, CP);
		return list;
	}

	public void setBoard() {
		for (char i = 'A'; i <= 'H'; i++) {
			int count = getX(i);
			for (int j = 0; j < 8; j++) {
				String location = i + "" + (j + 1);
				if (i == 'A' || i == 'B') {
					board[count][j] = new CheckersPiece('r', location);
				} else if (i == 'G' || i == 'H') {
					board[count][j] = new CheckersPiece('b', location);
				} else {
					board[count][j] = new CheckersPiece(' ', location);
				}
			}
		}
	}

	public String flipCoin() {
		int val = new Random().nextInt(2);
		if (val == 0) {
			return "black";
		} else {
			return "red";
		}
	}

	public void checkForKings() {
		for (int i = 0; i < 8; i++) {
			if (board[0][i].team == 'b') {
				board[0][i].team = 'B';
			}
			if (board[7][i].team == 'r') {
				board[7][i].team = 'R';
			}
		}
	}

	public String printBoard() {
		String result = "";
		result += "..12345678\n..--------\n";
		int count = 0;
		for (char alphabet = 'A'; alphabet <= 'H'; alphabet++) {
			result += alphabet + "|";
			for (int j = 0; j < 8; j++) {
				result += board[count][j].team;
			}
			count++;
			result += "\n";
		}
		return result;
	}

	public void setBoardTest() {
		for (char i = 'A'; i <= 'H'; i++) {
			int count = getX(i);
			for (int j = 0; j < 8; j++) {
				String location = i + "" + (j + 1);
				if (i == 'G') {
					board[count][j] = new CheckersPiece('r', location);
				} else if (i == 'B') {
					board[count][j] = new CheckersPiece('b', location);
				} else {
					board[count][j] = new CheckersPiece(' ', location);
				}
			}
		}
	}

}