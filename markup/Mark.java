package markup;

interface Mark {
    void toMarkdown(StringBuilder sb);
    void toTex(StringBuilder sb);
}
