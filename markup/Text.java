package markup;

public class Text implements Mark {
    private String text;

    public Text(String str) {
        this.text = str;
    }

    public void toMarkdown(StringBuilder sb) {
        sb.append(text);
    }

    public void toTex(StringBuilder sb) {
        sb.append(text);
    }
}

