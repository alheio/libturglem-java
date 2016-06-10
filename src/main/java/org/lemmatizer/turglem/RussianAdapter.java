package org.lemmatizer.turglem;

import org.lemmatizer.turglem.lib.LibTurglem;

public class RussianAdapter extends Adapter {
    private static final int RUSSIAN_LETTER_DELIM = 33;

    public RussianAdapter(LibTurglem libTurglem) {
        super(libTurglem, RUSSIAN_LETTER_DELIM, 1024);
    }

    public RussianAdapter(RussianAdapter rhs) {
        super(rhs);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new RussianAdapter(this);
    }

    @Override
    long stringToMAFSALetters(String token, byte[] MAFSAletters) {
        return getLibTurglem().RUSSIAN_conv_string_to_letter_utf8(token, MAFSAletters, MAFSAletters.length);
    }

    @Override
    long stringToMAFSALetters(byte[] token, long tokenLen, byte[] MAFSAletters) {
        return getLibTurglem().RUSSIAN_conv_binary_to_letter_utf8(token, tokenLen, MAFSAletters, MAFSAletters.length);
    }

    @Override
    long MAFSALettersToString(byte[] MAFSAletters, long len, byte[] dest) {
        return getLibTurglem().RUSSIAN_conv_letter_to_string_utf8(MAFSAletters, len, dest, dest.length);
    }
}
