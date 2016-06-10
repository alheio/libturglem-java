package org.lemmatizer.turglem.lib;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

public class MAFSA_automaton_struct extends Structure {
    public IntByReference ptr_nodes;
    public IntByReference ptr_links;
    public int shared;

    public MAFSA_automaton_struct() {
        super();
    }
    public MAFSA_automaton_struct(Pointer peer) {
        super(peer);
    }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList("ptr_nodes", "ptr_links", "shared");
    }

    public static class ByReference extends MAFSA_automaton_struct implements Structure.ByReference {
    };
    public static class ByValue extends MAFSA_automaton_struct implements Structure.ByValue {
    };
}
