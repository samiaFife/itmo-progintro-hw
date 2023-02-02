package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Game {
    private final boolean log;
    private final Player player1, player2;

    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board, int first) {
        while (true) {
            int result1;
            do {
                result1 = move(board, first == 1 ? player1 : player2, first);
                if (result1 != -1 && result1 != -2) {
                    return result1;
                }
            } while (result1 == -2);
            int result2;
            do {
                result2 = move(board, first == 1 ? player2 : player1, 3 - first);
                if (result2 != -1 && result2 != -2) {
                    return result2;
                }
            } while (result2 == -2);
        }
    }

    private int move(final Board board, final Player player, final int no) {
        log("Position:\n" + board);
        final Move move;
        final Result result;
        Result result1;
        try {
            move = player.move(board.getPosition(), board.getCell());
            log("Player " + no + " move: " + move);
            result1 = board.makeMove(move);
        } catch(Exception e){
            result1 = Result.LOSE;
        }
        result = result1;
        //log("Position:\n" + board);
        if (result == Result.DOUBLE) {
            log("Player " + no + " plays again!");
            return -2;
        } else if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return 3 - no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
