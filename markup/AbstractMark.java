package markup;

import java.util.List;

public abstract class AbstractMark implements Mark {
    protected List<Mark> content;
    protected String mdTag, texTagOpen, texTagClose;

    protected AbstractMark(List<Mark> list, String md, String texOpen, String texClose) {
        this.content = list;
        this.mdTag = md;
        this.texTagOpen = texOpen;
        this.texTagClose = texClose;
    }

    public void toMarkdown(StringBuilder sb) {
        sb.append(mdTag);
        for (Mark mark : content) {
            mark.toMarkdown(sb);
        }
        sb.append(mdTag);
    }

    public void toTex(StringBuilder sb) {
        sb.append(texTagOpen);
        for (Mark mark : content) {
            mark.toTex(sb);
        }
        sb.append(texTagClose);
    }
}
