package game;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class GameBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final int n, m, k;
    private int empty;
    private final Cell[][] cells;
    private Cell turn;

    public GameBoard(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.cells = new Cell[n][m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        empty = n * m;
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        empty--;
        int currRow = move.getRow(), currCol = move.getColumn();
        cells[currRow][currCol] = move.getValue();

        int inRow = 1, inCol = 1, inDiag1 = 1, inDiag2 = 1;
        int available = 8;
        Direction[] directions = {
                new Direction(true, true, false, false, false, new int[]{0, -1}), //rowLeft
                new Direction(true, false, true, false, false, new int[]{0, 1}), //rowRight
                new Direction(true, false, false, true, false, new int[]{-1, 0}), //colUp
                new Direction(true, false, false, false, true, new int[]{1, 0}), //colDown
                new Direction(true, false, true, true, false, new int[]{-1, 1}), //diag1Right /
                new Direction(true, true, false, false, true, new int[]{1, -1}), //diag1Left
                new Direction(true, true, false, true, false, new int[]{-1, -1}), //diag2Left \
                new Direction(true, false, true, false, true, new int[]{1, 1}) //diag2Right
        };
        for (int inc = 1; available > 0; inc++) {
            if (inRow == k || inCol == k || inDiag1 == k || inDiag2 == k)
                return Result.WIN;
            for (int i = 0; i < 8; i++) {
                Direction dir = directions[i];
                if (dir.flag) {
                    if ((!dir.toLeft || currCol - inc >= 0)
                            && (!dir.toRight || currCol + inc < m)
                            && (!dir.toDown || currRow + inc < n)
                            && (!dir.toUp || currRow - inc >= 0)
                    ) {
                        if (cells[currRow + dir.coeff[0] * inc][currCol + dir.coeff[1] * inc] == turn) {
                            switch (i / 2) {
                                case 0 -> inRow++;
                                case 1 -> inCol++;
                                case 2 -> inDiag1++;
                                case 3 -> inDiag2++;
                            }
                        } else {
                            directions[i].flag = false;
                            available--;
                        }
                    } else {
                        directions[i].flag = false;
                        available--;
                    }
                }
            }
        }
        if (inRow == k || inCol == k || inDiag1 == k || inDiag2 == k)
            return Result.WIN;
        if (empty == 0) {
            return Result.DRAW;
        }
        if (inRow >= 4 || inCol >= 4 || inDiag1 >= 4 || inDiag2 >= 4)
            return Result.DOUBLE;

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getColumn() && move.getColumn() < m
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < 2 * m; i += 2) {
            sb.append(i / 2 + 1);
            if (i / 2 < 9) sb.append(" ");
            sb.append(" ");
        }
        for (int r = 0; r < n; r++) {
            sb.append("\n");
            sb.append(r + 1);
            if (r < 9) sb.append(" ");
            sb.append("| ");
            for (int c = 0; c < 2 * m; c += 2) {
                sb.append(SYMBOLS.get(cells[r][c / 2])).append("  ");
            }
        }
        return sb.toString();
    }
}
