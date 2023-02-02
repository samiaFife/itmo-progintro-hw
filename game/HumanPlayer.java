package game;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        //out.println("Position");
        //out.println(position);
        out.println(cell + "'s move");
        out.println("Enter row and column");
        final Move move;
        int row = -2, col = -2;
        boolean flag;
        Scanner rsc = null;
        do {
            try {
                flag = true;
                String line = in.nextLine();
                ArrayList<String> tokens = new ArrayList<>();
                rsc = new Scanner(line);
                while (rsc.hasNext()) {
                    tokens.add(rsc.next());
                    if (tokens.size() > 2) {
                        flag = false;
                        break;
                    }
                }
                if (tokens.size() < 2 || !flag) {
                    out.println("Bad input, try again.");
                    continue;
                }
                try { //2 токена
                    Integer.parseInt(tokens.get(0));
                    Integer.parseInt(tokens.get(1));
                    row = Integer.parseInt(tokens.get(0)) - 1;
                    col = Integer.parseInt(tokens.get(1)) - 1;
                    if (!position.isValid(new Move(row, col, cell))) {
                        out.println("Bad input, try again.");
                        col = -2;
                        row = -2;
                    }
                } catch (NumberFormatException e) {
                    out.println("Bad input, try again.");
                }
            } catch (Exception e) {
                out.println("Exception caught.");
                if (rsc != null)
                    rsc.close();
                break;
            }
        } while (col == -2 && row == -2);
        if (rsc != null)
            rsc.close();
        move = new Move(row, col, cell);
        return move;
    }
}
