package game;

public class Match {
    private final Game game;
    private final int winsToEnd;
    private final int n, m, k;

    public Match(Game game, int winsToEnd, int n, int m, int k) {
        this.game = game;
        this.winsToEnd = winsToEnd;
        this.n = n;
        this.m = m;
        this.k = k;
    }

    public void begin() {
        GameBoard board = new GameBoard(n, m, k);
        int score1 = 0, score2 = 0, first = 1;
        int result;
        do {
            System.out.println("Player " + (first) + " plays as X");
            System.out.println("Player " + (3 - first) + " plays as O");
            result = game.play(board, first);
            switch (result) {
                case 1 -> score1++;
                case 2 -> score2++;
            }
            result = result == 0 ? 1 : result;
            System.out.println("Player " + result + " score is: " + (result == 1 ? score1 : score2));
            System.out.println("Player " + (3 - result) + " score is: " + (result == 1 ? score2 : score1));
            System.out.println();
            first = 3 - first;
        } while (score1 != this.winsToEnd && score2 != this.winsToEnd);
        System.out.println("Player " + result + " has won!");
    }
}
