package markup;

import java.util.List;

public class Emphasis extends AbstractMark {
    public Emphasis(List<Mark> list) {

        super(list, "*", "\\emph{", "}");
    }
}
