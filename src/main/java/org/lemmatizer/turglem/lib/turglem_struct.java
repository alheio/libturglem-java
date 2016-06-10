package org.lemmatizer.turglem.lib;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class turglem_struct extends Structure {
    public MAFSA_automaton_struct.ByReference words;
    public turglem_paradigms_struct.ByReference paradigms;
    public MAFSA_automaton_struct.ByReference prediction;


    public turglem_struct() {
        super();
    }

    public turglem_struct(Pointer peer) {
        super(peer);
    }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList("words", "paradigms", "prediction");
    }

    public static class ByReference extends turglem_struct implements Structure.ByReference {
    };
    public static class ByValue extends turglem_struct implements Structure.ByValue {
    };
}
