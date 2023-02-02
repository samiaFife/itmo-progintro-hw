package game;

import java.util.Scanner;

import static java.lang.Math.max;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        int n, m, k, p;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Enter n, m, k:");
            n = sc.nextInt();
            m = sc.nextInt();
            k = sc.nextInt();
        } while (n <= 0 || m <= 0 || (k > max(n, m)));
        System.out.println("Enter number of wins:");
        p = sc.nextInt();
        final Game game = new Game(true, new HumanPlayer(), new HumanPlayer());
        final Match match = new Match(game, p, n, m, k);
        match.begin();
    }
}
