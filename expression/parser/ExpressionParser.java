package expression.parser;

import expression.Const;
import expression.TripleExpression;
import expression.Variable;
import expression.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExpressionParser implements TripleParser {
    private static String source;
    private static int pos;
    private static TripleExpression currentExpr = null; //текущее выражение (обновляется после каждого getElement, зануляется при '(')
    private static boolean inAction = false; //является ли распознаваемый элемент потенциальным операндом бинарного выражения
    private enum Operations {EXPRESSION, SET, CLEAR, ADD, SUBTRACT, MULTIPLY, DIVISION}
    private static final Map<Operations, Set<Character>> LESS_PRIORITY = new HashMap<>(Map.of(
            Operations.EXPRESSION, Set.of(')'),
            Operations.SET, Set.of(')', 's', 'c'),
            Operations.CLEAR, Set.of(')', 's', 'c'),
            Operations.ADD, Set.of(')', 's', 'c', '+'),
            Operations.SUBTRACT, Set.of(')', 's', 'c', '+', '-'),
            Operations.MULTIPLY, Set.of(')', 's', 'c', '+', '-', '*', '/'),
            Operations.DIVISION, Set.of(')', 's', 'c', '+', '-', '*', '/')
    ));


    public TripleExpression parse(String expression) {
        source = expression;
        pos = 0;
        currentExpr = null;
        TripleExpression result = null;
        while (!eof()) {
            skipWhitespace();
            result = getElement();
            skipWhitespace();
        }
        return result;
    }

    private static TripleExpression getElement() {
        if (take(')')) {
            return currentExpr;
        } else if (take('x') || take('y') || take('z')) {
            currentExpr = getVar();
        } else if (take('*') || take('/')) {
            currentExpr = getFirstPriority();
        } else if (take('+') || take('-')) {
            currentExpr = getSecondPriority();
        } else if (take('s') || take('c')) {
            currentExpr = getThirdPriority();
        } else if (take('(')) {
            currentExpr = null;
            return getExpression();
        } else {
            currentExpr = getNumber();
        }
        inAction = false;
        return currentExpr;
    }

    private static TripleExpression getVar() {
        TripleExpression res = null;
        switch (source.charAt(pos)) {
            case 'x' -> res = new Variable("x");
            case 'y' -> res = new Variable("y");
            case 'z' -> res = new Variable("z");
        }
        pos++;
        return res;
    }

    private static TripleExpression getFirstPriority() {
        inAction = true;
        if (take('*')) {
            pos++;
            skipWhitespace();
            return new Multiply((CommonInterface) currentExpr, (CommonInterface) analizeByPriority(Operations.MULTIPLY));
        } else if (take('/')) {
            pos++;
            skipWhitespace();
            return new Divide((CommonInterface) currentExpr, (CommonInterface) analizeByPriority(Operations.DIVISION));
        }
        return null;
    }

    private static TripleExpression getSecondPriority() {
        if (take('+')) {
            inAction = true;
            pos++;
            skipWhitespace();
            return new Add((CommonInterface) currentExpr, (CommonInterface) analizeByPriority(Operations.ADD));
        } else {
            if (betweenNext('0', '9')
                    && (currentExpr == null || inAction)) //слева знак операции или начало выражения, причем сразу после минусы цифры - отрицательное число
                return getNumber();
            pos++; //смотрим символ после минуса
            if (currentExpr == null || inAction) { //начало выражения или слева был знак операции - унарный минус
                skipWhitespace();
                return new Minus((CommonInterface) getElement());
            }
            inAction = true;
            skipWhitespace();
            return new Subtract((CommonInterface) currentExpr, (CommonInterface) analizeByPriority(Operations.SUBTRACT));
        }
    }

    private static TripleExpression getThirdPriority() {
        inAction = true;
        StringBuilder sb = new StringBuilder();
        if (take('s')) {
            for (int i = 0; i < 2; i++) sb.append(source.charAt(++pos));
            if (sb.toString().equals("et")) {
                pos++;
                return new BinarySet((CommonInterface) currentExpr, (CommonInterface) analizeByPriority(Operations.SET));
            }
        } else if (take('c')) {
            for (int i = 0; i < 4; i++) sb.append(source.charAt(++pos));
            if (sb.toString().equals("lear")) {
                pos++;
                return new BinaryClear((CommonInterface) currentExpr, (CommonInterface) analizeByPriority(Operations.CLEAR));
            }
        }
        return null;
    }

    private static TripleExpression analizeByPriority(Operations operation) {
        TripleExpression result = null;
        while (!eof() && !checkLessPriority(operation)) { //парс останавливается перед операциями меньшего приоритета
            skipWhitespace();
            result = getElement();
            skipWhitespace();
        }
        if (operation == Operations.EXPRESSION) pos++; //пропустим ')'
        return result;
    }

    private static boolean checkLessPriority(Operations operation) {
        if (take('-') && inAction) return false;
        return LESS_PRIORITY.get(operation).contains(source.charAt(pos));
    }

    private static TripleExpression getExpression() {
        pos++; //пропустим '('
        skipWhitespace();
        return analizeByPriority(Operations.EXPRESSION);
    }

    private static boolean betweenNext(char from, char to) {
        return pos < source.length() - 1 && (from <= source.charAt(pos + 1) && source.charAt(pos + 1) <= to);
    }

    private static TripleExpression getNumber() {
        final StringBuilder sb = new StringBuilder();
        takeInteger(sb);
        return new Const(Integer.parseInt(sb.toString()));
    }

    private static void takeInteger(final StringBuilder sb) {
        if (take('-')) {
            sb.append('-');
            pos++;
        }
        if (take('0')) {
            sb.append('0');
            pos++;
        } else if (between('1', '9')) {
            takeDigits(sb);
        }
    }

    private static void takeDigits(final StringBuilder sb) {
        while (!eof() && between('0', '9')) {
            sb.append(source.charAt(pos++));
        }
    }

    private static boolean between(char from, char to) {
        return from <= source.charAt(pos) && source.charAt(pos) <= to;
    }

    private static boolean take(char expected) {
        return source.charAt(pos) == expected;
    }

    private static void skipWhitespace() {
        while (!eof() && Character.isWhitespace(source.charAt(pos))) {
            pos++;
        }
    }

    private static boolean eof() {
        return pos >= source.length();
    }

    public static void main(String[] args) {
        String example = "-1 set 0";
        ExpressionParser p = new ExpressionParser();
        System.out.println(p.parse(example).toString());
        System.out.println(p.parse(example).evaluate(1, 1, 1));

    }
}
