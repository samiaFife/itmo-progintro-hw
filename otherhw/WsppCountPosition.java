package otherhw;

import java.nio.file.NoSuchFileException;
import java.util.*;
import java.io.*;

import static java.util.Map.Entry.comparingByValue;

public class WsppCountPosition {

    public static void main(String[] args) {
        Map<String, ArrayList<String>> words = new LinkedHashMap<>();
        int it, row = 0;
        Scanner in = new Scanner(new File(args[0]));
        try {
            String word;
            while (in.hasNextLine()) {
                String line = in.nextLine();
                row++;
                it = 1;
                Scanner rowScanner = new Scanner(line);
                try {
                    while (rowScanner.hasNextWord()) {
                        word = rowScanner.nextWord().toLowerCase();
                        String pos = row + ":" + it;
                        ArrayList<String> list = words.get(word);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(pos);
                        words.put(word, list);
                        it++;
                    }
                } catch (IOException e) {
                    System.out.println("Reading trouble: " + e.getMessage());
                } finally {
                    rowScanner.close();
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
                    Map<String, Integer> tmpMap = new LinkedHashMap<>();
                    for (String key : words.keySet()) {
                        tmpMap.put(key, words.get(key).size()); //суем сюда пары слово-количество вхождений
                    }
                    ArrayList<Map.Entry<String, Integer>> tmpList = new ArrayList<>(tmpMap.entrySet());
                    tmpList.sort(comparingByValue()); //сортируем по количеству вхождений
                    for (Map.Entry<String, Integer> entry : tmpList) {
                        String key = entry.getKey();
                        ArrayList<String> list = words.get(key);
                        out.write(key + " " + list.size() + " ");
                        for (int i = 0; i < list.size(); i++) {
                            if (i != list.size() - 1) {
                                out.write(list.get(i) + " ");
                            } else {
                                out.write(list.get(i));
                            }

                        }
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
    }
}