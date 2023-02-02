package markup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test {
    public static void main(String[] args) {
//        Paragraph paragraph = new Paragraph(List.of(
//                new Strong(List.of(
//                        new Text("1"),
//                        new Strikeout(List.of(
//                                new Text("2"),
//                                new Emphasis(List.of(
//                                        new Text("3"),
//                                        new Text("4")
//                                )),
//                                new Text("5")
//                        )),
//                        new Text("6")
//                ))
//        ));
//
//        StringBuilder sb = new StringBuilder();
//        paragraph.toTex(sb);
//        System.out.println(sb);
//        int n = Integer.parseInt(args[0]);
//        ArrayList<Integer> divs = new ArrayList<>();
//        divs.add(1);
//        divs.add(n);
//        for (int i = 2; i < (int) (Math.sqrt(n)) + 1; i++) {
//            if (n % i == 0) {
//                divs.add(i);
//                if (n / i != i)
//                    divs.add(n / i);
//            }
//        }
//        Collections.sort(divs);
//        System.out.println(divs);
        StringBuilder sb = new StringBuilder("ahabugeca");
        System.out.println(sb.delete(sb.length()-2,sb.length()));
    }
}
