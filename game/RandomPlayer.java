package game;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class RandomPlayer implements Player {
    private final Random random;
    private final int n, m;

    public RandomPlayer(final int n, final int m, final Random random) {
        this.n = n;
        this.m = m;
        this.random = random;
    }

    public RandomPlayer(final int n, final int m) {
        this(n, m, new Random());
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            int r = random.nextInt(n);
            int c = random.nextInt(m);
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
            //return move;
        }
    }
}
