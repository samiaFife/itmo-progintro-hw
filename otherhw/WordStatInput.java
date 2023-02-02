import java.util.*;
import java.io.*;

public class WordStatInput {
    public static boolean notALetter(char ch) {
        if (!(Character.DASH_PUNCTUATION == Character.getType(ch) || Character.isLetter(ch) || ch == '-' || ch == '\'')) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(args[0]),
                            "utf8"
                    )
            );
            String[] words = new String[2];
            int[] count = new int[2];
            int it = 0;
            try {
                String word = new String();
                while (true) {
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    int left = 0, right = 0;
                    for (int j = 0; j < line.length(); j++) {
                        if (!notALetter(line.charAt(j))) {
                            if (j == 0 || notALetter(line.charAt(j - 1))) {
                                left = j;
                            }
                            if (j == line.length() - 1 || notALetter(line.charAt(j + 1))) {
                                right = j + 1;
                                word = line.substring(left, right).toLowerCase();
                                boolean flag = false;
                                for (int k = 0; k < it; k++) {
                                    if (words[k].equals(word)) {
                                        flag = true;
                                        count[k]++;
                                        break;
                                    }
                                }
                                if (flag == false) {
                                    if (it >= words.length) {
                                        words = Arrays.copyOf(words, words.length * 2);
                                        count = Arrays.copyOf(count, count.length * 2);
                                    }
                                    words[it] = word;
                                    count[it]++;
                                    it++;
                                }
                                left = 0;
                                right = 0;
                            }
                        }
                    }
                }
            } finally {
                System.out.println("Input closed");
                in.close();
            }

            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            "utf8"
                    )
            );

            try {
                for (int i = 0; i < it; i++) {
                    out.write(words[i] + " " + count[i]);
                    out.newLine();
                }
            } catch (IOException e) {
                System.out.println("Troubles with writer: " + e.getMessage());
            } finally {
                System.out.println("Output closed");
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
    }
}
