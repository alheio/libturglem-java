package org.lemmatizer.turglem;

import org.lemmatizer.turglem.lib.LibTurglem;

import java.util.ArrayList;
import java.util.List;

public class Adapter implements Cloneable {
    private final static int maxForms = 20;
    private int delimiter;
    private LibTurglem libTurglem;

    byte[] MAFSAletters;
    int[] formData;
    byte[] MAFSAlexeme;
    byte[] jStringlexeme;

    final WordForm[] formsPool;
    List<WordForm> formsActual;

    public Adapter(LibTurglem libTurglem, int delimiter, int bufferSize) {
        this.libTurglem = libTurglem;
        this.delimiter = delimiter;

        this.MAFSAletters = new byte[bufferSize];
        this.formData = new int[2*bufferSize];
        this.MAFSAlexeme = new byte[2*bufferSize];
        this.jStringlexeme = new byte[2*bufferSize];

        this.formsPool = new WordForm[maxForms];
        for (int i = 0; i<maxForms; ++i) {
            this.formsPool[i] = new WordForm();
        }

        this.formsActual = new ArrayList<>(maxForms);
    }

    public Adapter(Adapter rhs) {
        this.delimiter = rhs.delimiter;
        this.libTurglem = rhs.libTurglem;
        this.MAFSAletters = rhs.MAFSAletters.clone();
        this.formData = rhs.formData.clone();
        this.MAFSAlexeme = rhs.MAFSAlexeme.clone();
        this.jStringlexeme = rhs.jStringlexeme.clone();
        this.formsPool = rhs.formsPool.clone();

        this.formsActual = new ArrayList<>(rhs.formsActual.size());
        for (WordForm wf: rhs.formsActual) {
            this.formsActual.add(new WordForm(wf));
        }
    }

    protected LibTurglem getLibTurglem() {
        return libTurglem;
    }

    int getDelimiter() {
        return delimiter;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Adapter(this);
    }

    long stringToMAFSALetters(String token, byte[] MAFSAletters) {
        return 0;
    }

    long stringToMAFSALetters(byte[] token, long tokenLen, byte[] MAFSAletters) {
        return 0;
    }

    long MAFSALettersToString(byte[] MAFSAletters, long len, byte[] dest) {
        return 0;
    }
}
