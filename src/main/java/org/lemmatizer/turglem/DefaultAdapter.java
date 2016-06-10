package org.lemmatizer.turglem;

import org.lemmatizer.turglem.lib.LibTurglem;

public class DefaultAdapter extends Adapter {
    private static int DEFAULT_LETTER_DELIM = 70;

    public DefaultAdapter(LibTurglem libTurglem) {
        super(libTurglem, DEFAULT_LETTER_DELIM, 1024);
    }

    public DefaultAdapter(DefaultAdapter rhs) {
        super(rhs);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new DefaultAdapter(this);
    }

    long stringToMAFSALetters(String token, byte[] MAFSAletters) {
        return getLibTurglem().DEFAULT_conv_string_to_letter_utf8(token, MAFSAletters, MAFSAletters.length);
    }

    @Override
    long stringToMAFSALetters(byte[] token, long tokenLen, byte[] MAFSAletters) {
        return getLibTurglem().DEFAULT_conv_binary_to_letter_utf8(token, tokenLen, MAFSAletters, MAFSAletters.length);
    }

    long MAFSALettersToString(byte[] MAFSAletters, long len, byte[] dest) {
        return getLibTurglem().DEFAULT_conv_letter_to_string_utf8(MAFSAletters, len, dest, dest.length);
    }
}
