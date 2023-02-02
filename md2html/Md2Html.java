package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Md2Html {
    private static StringBuilder paragraph = new StringBuilder();
    private static final List<String> markChars = Arrays.asList("*", "**", "_", "__", "-", "--", "+", "++", "`");
    private static final List<Character> canUseTwice = Arrays.asList('*', '_');
    private static final List<Character> canUseOnlyOnce = List.of('`');
    private static String str = "", last_str = "", sep = System.lineSeparator();
    private static char ch, prev_ch, last_ch;
    private static int lsl = System.lineSeparator().length();
    private static final Map<String, String> markCharsToHtml = Map.ofEntries(
            entry("*", "em"),
            entry("_", "em"),
            entry("**", "strong"),
            entry("__", "strong"),
            entry("--", "s"),
            entry("`", "code"),
            entry("++", "u")
    );
    private static final Map<String, Boolean> wasMarkOpening = new HashMap<>(Map.ofEntries(
            entry("*", false),
            entry("_", false),
            entry("**", false),
            entry("__", false),
            entry("--", false),
            entry("`", false),
            entry("++", false)
    ));
    private static final Map<Character, Boolean> prevCharWasMark = new HashMap<>(Map.ofEntries(
            entry('*', false),
            entry('_', false),
            entry('-', false),
            entry('`', false),
            entry('+', false)
    ));

    public static void toHtml(String inputFile, String outputFile) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(inputFile),
                        StandardCharsets.UTF_8
                )
        );
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(outputFile),
                        StandardCharsets.UTF_8
                )
        );
        List<Character> special = Arrays.asList('>', '<', '&');
        boolean screened = false;
        StringBuilder head = new StringBuilder();
        while (true) {
            int typed = reader.read();
            ch = (char) typed;

            //конец параграфа
            if (ch == '\r' || ch == '\n') {
                if ((lsl > 1) && (paragraph.isEmpty() || last_ch == '\n')) {
                    last_ch = ch;
                    continue;
                }
            }
            if (((!sep.equals("\r") && (ch == '\n' && ((paragraph.length() == 0 || paragraph.charAt(paragraph.length() - 1) == '\n'))))
                    || (sep.equals("\r") && (ch == '\r' && ((paragraph.length() == 0 || paragraph.charAt(paragraph.length() - 1) == '\r')))))
                    || typed == -1) {
                if (!paragraph.isEmpty()) {
                    if (paragraph.substring(paragraph.length() - lsl,
                            paragraph.length()).equals(sep)) {
                        paragraph.delete(paragraph.length() - lsl, paragraph.length());
                    }
                    if (!head.isEmpty()) {
                        paragraph.append("</h").append(head.length()).append(">");
                    } else {
                        paragraph.append("</p>");
                    }
                    writer.write(paragraph.toString());
                    writer.newLine();
                    paragraph = new StringBuilder();
                    head = new StringBuilder();
                    continue;
                }
                if (typed == -1) {
                    reader.close();
                    writer.close();
                    break;
                }
                continue;
            }

            //начало параграфа
            if (paragraph.isEmpty()) {
                if (ch == '#') {
                    head.append(ch);
                    continue;
                } else if (!head.isEmpty()) {
                    if (ch == ' ') {
                        paragraph.append("<h").append(head.length()).append(">");
                        continue;
                    }
                }
                paragraph.append("<p>");
                paragraph.append(head);
                head = new StringBuilder();
            }

            //параграф
            //если встретили спец символ
            if (special.contains(ch)) {
                switch (ch) {
                    case '<' -> paragraph.append("&lt;");
                    case '>' -> paragraph.append("&gt;");
                    case '&' -> paragraph.append("&amp;");
                }
                continue;
            }
            //встретили обратный слеш
            if (screened) {
                paragraph.append(ch);
                screened = false;
                continue;
            }
            str += ch;
            //встретили элемент разметки
            if (markChars.contains(str)) {
                //двойное выделение/зафикисировать символ
                parseMark();
                continue;
            } else {
                checkForOpening();
            }
            if (ch == '\\') {
                screened = true;
                continue;
            }
            paragraph.append(ch);
            last_ch = ch;
            last_str = str;
            str = "";
        }
    }

    private static void parseMark() {
        if (!canUseOnlyOnce.contains(ch)) {
            if (prevCharWasMark.get(ch)) {
                if (wasMarkOpening.get(str)) {
                    paragraph.append("</").append(markCharsToHtml.get(str)).append(">");
                    wasMarkOpening.put(str, false);
                    prevCharWasMark.put(ch, false);
                } else {
                    paragraph.append("<").append(markCharsToHtml.get(str)).append(">");
                    wasMarkOpening.put(str, true);
                    prevCharWasMark.put(ch, false);
                }
                last_ch = ch;
                last_str = str;
                str = "";
            } else {
                prevCharWasMark.put(ch, true);
                last_ch = ch;
                last_str = str;
            }
        } else {
            checkForOpening();
            if (!wasMarkOpening.get(str)) {
                paragraph.append("<").append(markCharsToHtml.get(str)).append(">");
                wasMarkOpening.put(str, true);
            } else {
                paragraph.append("</").append(markCharsToHtml.get(str)).append(">");
                wasMarkOpening.put(str, false);
            }
            last_ch = ch;
            last_str = str;
            str = "";
        }
    }

    private static void checkForOpening() {
        if (prevCharWasMark.get(last_ch) == null)
            return;
        if (last_str.length() > 1)
            return;
        if (prevCharWasMark.get(last_ch)) {
            if (canUseTwice.contains(last_ch)) {
                if ((ch == ' ' || ((lsl > 1 && ch == '\r') || (lsl == 1 && (ch == '\n' || ch == '\r')))) && !wasMarkOpening.get(last_str)) {
                    paragraph.append(last_ch);
                } else {
                    if (wasMarkOpening.get(last_str) == null)
                        return;
                    if (wasMarkOpening.get(last_str)) {
                        paragraph.append("</").append(markCharsToHtml.get(last_str)).append(">");
                        wasMarkOpening.put(last_str, false);
                    } else {
                        paragraph.append("<").append(markCharsToHtml.get(last_str)).append(">");
                        wasMarkOpening.put(last_str, true);
                    }
                }
            } else {
                paragraph.append(last_ch);
            }
            prevCharWasMark.put(last_ch, false);
        }
    }

    public static void main(String[] args) {
        try {
            toHtml(args[0], args[1]);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
