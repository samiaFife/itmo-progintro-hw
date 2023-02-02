import java.util.*;
import java.io.*;

public class Wspp {

    public static void main(String[] args) {
        Map<String, ArrayList<Integer>> words = new LinkedHashMap<>();
        int it = 1;
        Scanner in = new Scanner(new File(args[0]));
        try {
            String word = new String();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                Scanner sc = new Scanner(line);
                try {
                    while (sc.hasNextWord()) {
                        word = sc.nextWord().toLowerCase();
                        ArrayList<Integer> list = words.get(word);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(it);
                        words.put(word, list);
                        it++;
                    }
                } catch(IOException e) {
                    System.out.println("Troubles with reading: " + e.getMessage());
                } finally {
                    sc.close();
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
                        ArrayList<Integer> list = words.get(key);
                        out.write(key + " " + list.size() + " ");
                        for (int i = 0; i < list.size(); i++) {
                            if (i != list.size() - 1) {
                                out.write(list.get(i) + " ");
                            } else {
                                out.write(Integer.toString(list.get(i)));
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