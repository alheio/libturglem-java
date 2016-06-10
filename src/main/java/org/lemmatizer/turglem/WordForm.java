package org.lemmatizer.turglem;

public class WordForm implements Cloneable {
    private String lexeme;
    private int srcParadigm;
    private int srcForm;

    WordForm() {
    }

    WordForm(WordForm rhs) {
        this.lexeme = new String(rhs.lexeme);
        this.srcParadigm = rhs.srcParadigm;
        this.srcForm = rhs.srcForm;
    }

    public String getLexeme() {
        return lexeme;
    }

    int getSrcParadigm() {
        return srcParadigm;
    }

    int getSrcForm() {
        return srcForm;
    }

    void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    void setSrcParadigm(int srcParadigm) {
        this.srcParadigm = srcParadigm;
    }

    void setSrcForm(int srcForm) {
        this.srcForm = srcForm;
    }

    @Override
    public String toString() {
        return lexeme;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new WordForm(this);
    }
}
