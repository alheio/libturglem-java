package org.lemmatizer.turglem;

import org.lemmatizer.turglem.lib.LibTurglem;

public class EnglishAdapter extends Adapter {
    private static final byte ENGLISH_LETTER_DELIM = 28;

    public EnglishAdapter(LibTurglem libTurglem) {
        super(libTurglem, ENGLISH_LETTER_DELIM, 1024);
    }

    public EnglishAdapter(EnglishAdapter rhs) {
        super(rhs);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new EnglishAdapter(this);
    }

    @Override
    long stringToMAFSALetters(String token, byte[] MAFSAletters) {
        return getLibTurglem().ENGLISH_conv_string_to_letter_utf8(token, MAFSAletters, MAFSAletters.length);
    }

    @Override
    long stringToMAFSALetters(byte[] token, long tokenLen, byte[] MAFSAletters) {
        return getLibTurglem().ENGLISH_conv_binary_to_letter_utf8(token, tokenLen, MAFSAletters, MAFSAletters.length);
    }

    @Override
    long MAFSALettersToString(byte[] MAFSAletters, long len, byte[] dest) {
        return getLibTurglem().ENGLISH_conv_letter_to_string_utf8(MAFSAletters, len, dest, dest.length);
    }
}
