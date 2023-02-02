package otherhw;

import java.io.IOException;
import java.util.Arrays;

public class ReverseAbc {
    private static int codeToInt(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-') {
                sb.append(s.charAt(i));
            } else {
                sb.append((char) (s.charAt(i) - 'a' + '0'));
            }
        }
        return (Integer.parseInt(sb.toString()));
    }

    private static String codeToString(int n) {
        String s = Integer.toString(n);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-') {
                sb.append('-');
            } else {
                sb.append((char) (s.charAt(i) - '0' + 'a'));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] numbers = new int[2][2];
        int[] rowSize = new int[2];
        int row = 0;
        try {
            while (scanner.hasNextLine()) {
                String currStr = new String();
                currStr = scanner.nextLine();
                Scanner inRowScanner = new Scanner(currStr);
                try {
                    int column = 0;
                    if (row >= numbers.length) {
                        numbers = Arrays.copyOf(numbers, numbers.length * 2);
                        rowSize = Arrays.copyOf(rowSize, rowSize.length * 2);
                    }
                    if (numbers[row] == null) {
                        numbers[row] = new int[2];
                    }
                    while (inRowScanner.hasNext()) {
                        String next = inRowScanner.next();
                        int num = codeToInt(next);
                        if (column >= numbers[row].length) {
                            numbers[row] = Arrays.copyOf(numbers[row], numbers[row].length * 2);
                        }
                        numbers[row][column] = num;
                        rowSize[row]++;
                        column++;
                    }
                    row++;
                } catch (IOException e) {
                    System.out.println("Input reading problem: " + e.getMessage());
                } finally {
                    inRowScanner.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Input reading problem: " + e.getMessage());
        } finally {
            try {
                scanner.close();
            } catch (IOException e) {
                System.out.println("Reader closing trouble :" + e.getMessage());
            }
        }

        for (int j = row - 1; j >= 0; j--) {
            if (numbers[j] == null) {
                System.out.println();
                continue;
            }
            for (int k = rowSize[j] - 1; k >= 0; k--) {
                System.out.print(codeToString(numbers[j][k]) + " ");
            }
            System.out.println();
        }
    }
}