import java.io.*;
import java.nio.charset.StandardCharsets;

public class Scanner {
    private Reader reader;
    private static final int BUFFER_SIZE = 64;
    private char[] buffer = new char[BUFFER_SIZE];
    private String sep = System.lineSeparator();
    private int lsl = sep.length();

    public Scanner(InputStream inputStreamName) {
        reader = new InputStreamReader(
                inputStreamName
        );
    }

    public Scanner(String str) {
        try {
            reader = new StringReader(
                    str
            );
        } catch (NullPointerException e) {
            System.out.println("The reading string is null");
            throw new NullPointerException();
        }
    }

    public Scanner(File fileName) {
        try {
            reader = new FileReader(
                    fileName, StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            System.out.println("Trouble with file reader: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    private void bufferRefill() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            buffer[i] = Character.MIN_VALUE;
        }
    }

    private int typed; //сюда вводим буфер
    private boolean tokenEnded = false; //если был взят токен до того как кончился буфер
    private int ended = 0; //если буфер не кончился после взятия токена - продолжим с этой позиции
    private String lastToken = null; //сохраним после проверки на наличие следующего токена, чтобы не брать его два раза
    private String lastLine = null;
    private String lastWord = null;

    private enum tokenType {token, line, word}

    private boolean isGoodSymbol(char ch, tokenType type) {
        if (type == tokenType.token) {
            if (ch != ' ' && ch != '\r' && ch != '\n') {
                return true;
            }
        } else if (type == tokenType.line) {
            if (ch != '\r' && ch != '\n') {
                return true;
            }
        } else if (type == tokenType.word) {
            if (Character.DASH_PUNCTUATION == Character.getType(ch) || Character.isLetter(ch) || ch == '-' || ch == '\'') {
                return true;
            }
        }
        return false;
    }

    private String parse(tokenType type) throws IOException {
        if (type == tokenType.token) { //если было hasNext: запоминаем слово, считавшееся при проверке
            lastLine = null;//если перед этим были hasNextLine/Word то стираем считавшиеся токены
            lastWord = null;
            if (!(lastToken == null)) {
                String s = lastToken;
                lastToken = null;
                return s;
            }
        }
        if (type == tokenType.word) {
            lastLine = null;
            lastToken = null;
            if (!(lastWord == null)) {
                String s = lastWord;
                lastWord = null;
                return s;
            }
        }
        if (type == tokenType.line) {
            lastToken = null;
            lastWord = null;
            if (!(lastLine == null)) {
                String s = lastLine;
                lastLine = null;
                return s;
            }
        }
        StringBuilder sb = new StringBuilder(); //Сюда собираем токен
        while (true) {
            if (!tokenEnded) { //Не считывать новый буфер, если в прошлый раз слово кончилось до конца буфера
                bufferRefill(); //стираем буфер чтоб не заполнялся мусором в случае typed < BUFFER_SIZE
                typed = reader.read(buffer); //Ловим исключение, если ридер не смог открыться
                if (typed == -1) { //конец ввода
                    if (sb.length() != 0) { //если есть что вернуть - возвращаем
                        return sb.toString();
                    } else {
                        return null;
                    }
                }

            } else {
                tokenEnded = false;
            }
            for (int i = ended; i < typed; i++) { //Начинать парсинг с позиции, где кончилось прошлое слово
                ended = 0;
                char ch = buffer[i];

                if (isGoodSymbol(ch, type)) { //Встретили букву или символ, но не конец строки
                    sb.append(ch);
                } else { //конец строки или пробел
                    if (lsl == 2 && ch == '\r') {
                        continue;
                    }
                    if (i != typed - 1) { //фиксируем конец токена до конца буфера
                        ended = i + 1;
                        tokenEnded = true;
                    }
                    if (type == tokenType.line) { //строка кончается до перевода -> вернем
                        return sb.toString();
                    } else {
                        if (!sb.isEmpty()) { //есть что возвращать - вернем
                            return sb.toString();
                        } else { //перевод строки/конец буфера, но не пробел, при этом нет валидных символов - ищем дальше
                            break;
                        }
                    }
                }
            }
        }
    }

    public String next() throws IOException {
        return parse(tokenType.token);
    }

    public String nextLine() throws IOException {
        return parse(tokenType.line);
    }

    public String nextWord() throws IOException {
        return parse(tokenType.word);
    }

    public int nextInt() throws IOException {
        String s = parse(tokenType.token);
        return Integer.parseInt(s);
    }

    public boolean hasNext() throws IOException {
        lastToken = null;
        String s = parse(tokenType.token);
        if (s != null) {
            lastToken = s; //сохранаяем считавшийся токен
            return true;
        }
        return false;
    }

    public boolean hasNextInt() throws IOException {
        lastToken = null;
        String s = parse(tokenType.token);
        if (s != null) {
            try {
                int n = Integer.parseInt(s);
                lastToken = s;
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public boolean hasNextWord() throws IOException {
        lastWord = null;
        String s = parse(tokenType.word);
        if (s != null) {
            lastWord = s;
            return true;
        } else {
            return false;
        }
    }

    public boolean hasNextLine() throws IOException {
        lastLine = null;
        String s = parse(tokenType.line);
        if (s != null) {
            lastLine = s;
            return true;
        }
        return false;
    }

    public void close() throws IOException {
        reader.close();
    }
}