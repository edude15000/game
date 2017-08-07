package TicTacToe;

public class Play {

	public static void main(String[] args) {
		int x = 0, o = 0, c = 0;
		for (int i = 0; i < 100; i++) {
			TicTacToe T = new TicTacToe();
			char win = T.start();
			if (win == 'x') {
				x++;
			} else if (win == 'o') {
				o++;
			} else {
				c++;
			}
		}
		System.out.println("X WINS: " + x);
		System.out.println("O WINS: " + o);
		System.out.println("CATS GAMES: " + c);
	}
}