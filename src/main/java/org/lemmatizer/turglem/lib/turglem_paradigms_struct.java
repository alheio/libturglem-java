package org.lemmatizer.turglem.lib;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class turglem_paradigms_struct extends Structure {
    public Pointer _buffer;
    public int shared;

    public turglem_paradigms_struct() {
        super();
    }

    public turglem_paradigms_struct(Pointer peer) {
        super(peer);
    }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList("_buffer", "shared");
    }

    public static class ByReference extends turglem_paradigms_struct implements Structure.ByReference {
    };
    public static class ByValue extends turglem_paradigms_struct implements Structure.ByValue {
    };
}
