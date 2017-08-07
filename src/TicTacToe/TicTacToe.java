package TicTacToe;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToe {
	String board[][] = new String[3][3];
	char turn;
	int chanceToBlock, chanceToWin;

	public TicTacToe() {
		setBoard();
		chanceToBlock = 80;
		chanceToWin = 95;
		turn = flipCoin();
	}

	public char start() {
		while (true) {
			printBoard();
			if (checkIfWinner()) {
				switchTurns();
				System.out.println(turn + " is the winner!\n");
				return turn;
			}
			if (checkIfFull()) {
				System.out.println("CATS GAME\n");
				return 'c';
			}
			randomMove();
			switchTurns();
		}
	}

	public void randomMove() {
		String val;
		val = getBestPosition(true, chanceToWin);
		if (val == null) {
			val = getBestPosition(false, chanceToBlock);
		}
		if (val == null) {
			val = getRandomCPFromList(getOpenSpaces());
		}
		int x = Integer.parseInt(val.charAt(0) + "");
		int y = Integer.parseInt(val.charAt(1) + "");
		board[x][y] = turn + "";
	}

	public boolean chanceTotake(int percent) {
		if ((new Random().nextInt(100) + 1) < percent) {
			return true;
		}
		return false;
	}

	public String getBestPosition(boolean winning, int chance) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (winning && !board[i][j].equals(turn)) {
					break;
				}
				if (!winning && board[i][j].equals(turn)) {
					break;
				}
				if (!board[i][j].equals(" ")) {
					try {
						if (board[i][j].equals(board[i + 2][j]) && board[i + 1][j].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i + 1) + "" + String.valueOf(j);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i + 1][j]) && board[i + 2][j].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i + 2) + "" + String.valueOf(j);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i + 1][j].equals(board[i + 2][j]) && board[i][j].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i) + "" + String.valueOf(j);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i][j + 2]) && board[i][j + 1].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i) + "" + String.valueOf(j + 1);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i][j + 1]) && board[i][j + 2].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i) + "" + String.valueOf(j + 2);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j + 1].equals(board[i][j + 2]) && board[i][j].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i) + "" + String.valueOf(j);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j + 1].equals(board[i][j + 2]) && board[i][j].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i) + "" + String.valueOf(j);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i + 1][j + 1].equals(board[i + 2][j + 2]) && board[i][j].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i) + "" + String.valueOf(j);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i + 2][j + 2]) && board[i + 1][j + 1].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i + 1) + "" + String.valueOf(j + 1);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i + 1][j + 1]) && board[i + 2][j + 2].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i + 2) + "" + String.valueOf(j + 2);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i - 1][j + 1].equals(board[i - 2][j + 2]) && board[i][j].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i) + "" + String.valueOf(j);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i - 2][j + 2]) && board[i - 1][j + 1].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i - 1) + "" + String.valueOf(j + 1);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i - 1][j + 1]) && board[i - 2][j + 2].equals(" ")) {
							if (chanceTotake(chance)) {
								return String.valueOf(i - 2) + "" + String.valueOf(j + 2);
							}
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return null;
	}

	public ArrayList<Integer> getOpenSpaces() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j].equals(" ")) {
					list.add(Integer.parseInt(String.valueOf(i) + "" + String.valueOf(j)));
				}
			}
		}
		return list;
	}

	public String getRandomCPFromList(ArrayList<Integer> list) {
		String val = String.valueOf(list.get(new Random().nextInt(list.size())));
		if (Integer.parseInt(val) < 10) {
			val = "0" + val;
		}
		return val;
	}

	public boolean checkIfWinner() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!board[i][j].equals(" ")) {
					try {
						if (board[i][j].equals(board[i + 1][j]) && board[i + 1][j].equals(board[i + 2][j])) {
							return true;
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i][j + 1]) && board[i][j + 1].equals(board[i][j + 2])) {
							return true;
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i + 1][j + 1])
								&& board[i + 1][j + 1].equals(board[i + 2][j + 2])) {
							return true;
						}
					} catch (Exception e) {
					}
					try {
						if (board[i][j].equals(board[i - 1][j + 1])
								&& board[i - 1][j + 1].equals(board[i - 2][j + 2])) {
							return true;
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return false;
	}

	public char flipCoin() {
		int val = new Random().nextInt(2);
		if (val == 0) {
			return 'x';
		} else {
			return 'o';
		}
	}

	public void switchTurns() {
		if (turn == 'x') {
			turn = 'o';
		} else {
			turn = 'x';
		}
	}

	private boolean checkIfFull() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j].equals(" ")) {
					return false;
				}
			}
		}
		return true;
	}

	public void setBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = " ";
			}
		}
	}

	public void printBoard() {
		System.out.print(board[0][0] + "|" + board[0][1] + "|" + board[0][2] + "\n");
		System.out.print("-----\n");
		System.out.print(board[1][0] + "|" + board[1][1] + "|" + board[1][2] + "\n");
		System.out.print("-----\n");
		System.out.print(board[2][0] + "|" + board[2][1] + "|" + board[2][2] + "\n");
		System.out.println();
	}
}