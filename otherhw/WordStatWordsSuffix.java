package otherhw;

import java.io.*;
import java.util.*;

public class WordStatWordsSuffix {
    public static boolean notALetter(char ch) {
        return !(Character.DASH_PUNCTUATION == Character.getType(ch) || Character.isLetter(ch) || ch == '-' || ch == '\'');
    }


    public static void main(String[] args) {
        Map<String, Integer> words = new TreeMap<>();
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(args[0]),
                            "utf8"
                    )
            );
            try {
                String word, line;
                while (true) {
                    line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    int left = 0, right;
                    for (int j = 0; j < line.length(); j++) {
                        if (!notALetter(line.charAt(j))) {
                            if (j == 0 || notALetter(line.charAt(j - 1))) {
                                left = j;
                            }
                            if (j == line.length() - 1 || notALetter(line.charAt(j + 1))) {
                                right = j + 1;
                                word = line.substring(left, right).toLowerCase();
                                if (word.length() >= 3) {
                                    word = word.substring(word.length() - 3);
                                }
                                Integer val = words.get(word);
                                if (val != null) {
                                    words.put(word, val + 1);
                                } else {
                                    words.put(word, 1);
                                }
                                left = 0;
                            }
                        }
                    }
                }


                try {
                    BufferedWriter out = new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(args[1]),
                                    "utf8"
                            )
                    );
                    try {
                        for (String key : words.keySet()) {
                            out.write(key + " " + words.get(key));
                            out.newLine();
                        }
                    } catch (IOException e) {
                        System.out.println("Cannot write in output file: " + e.getMessage());
                    } finally {
                        try {
                            out.close();
                            System.out.println("Output closed");
                        } catch (IOException e) {
                            System.out.println("Cannot close output file: " + e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Output file opening trouble: " + e.getMessage());
                }


            } catch (IOException e) {
                System.out.println("Cannot read input file: " + e.getMessage());
            } finally {
                try {
                    in.close();
                    System.out.println("Input closed");
                } catch (IOException e) {
                    System.out.println("Cannot close input file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot open input file: " + e.getMessage());
        }
    }
}