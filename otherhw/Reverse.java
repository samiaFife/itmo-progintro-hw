import java.io.IOException;
import java.util.Arrays;

public class Reverse {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] numbers = new int[2][2];
        int[] rowSize = new int[2];
        int row = 0;
        try {
            while (scanner.hasNextLine()) {
                String currStr = new String();
                try {
                    currStr = scanner.nextLine();
                } catch (IOException e) {
                    System.out.println("Input reading problem: " + e.getMessage());
                }
                Scanner inRowScanner = new Scanner(currStr);
                int column = 0;
                if (row >= numbers.length) {
                    numbers = Arrays.copyOf(numbers, numbers.length * 2);
                    rowSize = Arrays.copyOf(rowSize, rowSize.length * 2);
                }
                if (numbers[row] == null) {
                    numbers[row] = new int[2];
                }
                while (inRowScanner.hasNextInt()) {
                    int num = inRowScanner.nextInt();
                    if (column >= numbers[row].length) {
                        numbers[row] = Arrays.copyOf(numbers[row], numbers[row].length * 2);
                    }
                    numbers[row][column] = num;
                    rowSize[row]++;
                    column++;
                }
                inRowScanner.close();
                row++;
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Input reading problem: " + e.getMessage());
        }

        for (int j = row - 1; j >= 0; j--) {
            if (numbers[j] == null) {
                System.out.println();
                continue;
            }
            for (int k = rowSize[j] - 1; k >= 0; k--) {
                System.out.print(numbers[j][k] + " ");
            }
            System.out.println();
        }
    }
}