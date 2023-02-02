package markup;

import java.util.List;

public class Strikeout extends AbstractMark {
    public Strikeout(List<Mark> list) {

        super(list, "~", "\\textst{", "}");
    }
}
