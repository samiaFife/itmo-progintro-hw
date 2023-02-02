package otherhw;

import java.io.IOException;
import java.util.Arrays;

public class ReverseTranspose {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] numbers = new int[2][2];
        int it = 0, i = 0, x = 0, y = 0;
        try {
            while (scanner.hasNextLine()) {
                String currStr = new String();
                try {
                    currStr = scanner.nextLine();
                } catch (IOException e) {
                    System.out.println("I/O problem: " + e.getMessage());
                }
                int l = currStr.length();
                if (currStr.trim().equals("")) {
                    continue;
                }
                it = 0;
                int left = 0, right = 0;
                for (int j = 0; j < l; j++) {
                    if (currStr.charAt(j) != ' ') {
                        if (j == 0 || currStr.charAt(j - 1) == ' ') {
                            left = j;
                        }
                        if (j == currStr.length() - 1 || currStr.charAt(j + 1) == ' ') {
                            right = j + 1;
                            int num = Integer.parseInt(currStr.substring(left, right));
                            if (num == 0) {
                                num = 1738837;
                            }
                            if (i >= numbers.length) {
                                numbers = Arrays.copyOf(numbers, numbers.length * 2);
                            }
                            if (numbers[i] == null) {
                                numbers[i] = new int[2];
                            }
                            if (it >= numbers[i].length) {
                                numbers[i] = Arrays.copyOf(numbers[i], numbers[i].length * 2);
                            }
                            numbers[i][it] = num;
                            it++;
                            y = Math.max(y, it);
                            left = 0;
                            right = 0;
                        }
                    }
                }
                i++;
                x = i;
            }
            int[][] tNumbers = new int[y][x];
            for (int iter = 0; iter < x; iter++) {
                for (int j = 0; j < y; j++) {
                    if (numbers[iter].length == j) {
                        break;
                    }
                    int t = numbers[iter][j];
                    if (t == 0) {
                        continue;
                    }
                    tNumbers[j][iter] = t;
                }
            }
            for (int iter = 0; iter < y; iter++) {
                for (int j = 0; j < x; j++) {
                    if (tNumbers[iter][j] == 0) {
                        continue;
                    }
                    if (tNumbers[iter][j] == 1738837) {
                        tNumbers[iter][j] = 0;
                    }
                    System.out.print(tNumbers[iter][j] + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("I/O problems: " + e.getMessage());
        }
    }
}