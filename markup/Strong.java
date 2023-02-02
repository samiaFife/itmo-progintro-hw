package markup;

import java.util.List;

public class Strong extends AbstractMark {
    public Strong(List<Mark> list) {

        super(list, "__", "\\textbf{", "}");
    }
}
