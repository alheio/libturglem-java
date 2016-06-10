package org.lemmatizer.turglem.lib;

import com.sun.jna.Library;
import com.sun.jna.ptr.IntByReference;

public interface LibTurglem extends Library {

    /*
     * turglem_load:
     * Loads all lemmatizer data from files: main automaton, prediction automaton, paradigms.
     * prediction may be 0, if you are not going to use prediction at all.
     *
     * extern turglem turglem_load(
     *      const char *fname_dic_autom,
     *      const char *fname_predict_autom,
     *      const char *fname_paradigms,
     *      int *err_no,
     *      int *err_what
     * );
     */
    turglem_struct.ByReference turglem_load(
            String fn_dic,
            String fn_predict,
            String fn_paradigms,
            IntByReference err_no,
            IntByReference err_what
    );


    void turglem_close(turglem_struct.ByReference lem);


    /*
     * turglem_lemmatize:
     *
     * Lemmitizes string of letters, fills array of pairs (flexia_number, form_number)
     * and returns the number of such pairs.
     *
     * extern size_t turglem_lemmatize(
     *      turglem lem,
     *      const MAFSA_letter *l,
     *      size_t sz_l,
     *      int *out_pair_array,
     *      size_t sz_array,
     *      MAFSA_letter delim,
     *      int use_prediction
     * );
     */

    long turglem_lemmatize(
            turglem_struct.ByReference lem,
            byte[] l,
            long sz_l,
            int[] out_pair_array,
            long sz_array,
            byte delim,
            int use_prediction
    );


    /*
     * turglem_build_form (builds form number n_form):
     *
     * s_in  / sz_s_in   -- source string, for which was called turglem_lemmatize
     * s_out / sz_s_out  -- output buffer (RETURNS the number of letters used from buffer)
     * s_flexia + s_form -- flexia and form pair returned by turglem_lemmatize
     * n_form            -- form to build
     *
     * extern size_t turglem_build_form(
     *      turglem lem,
     *      const MAFSA_letter *s_in,
     *      size_t sz_s_in,
     *      MAFSA_letter *s_out,
     *      size_t sz_s_out,
     *      int src_paradigm,
     *      int src_form,
     *      int n_form
     *  );
     */

    long turglem_build_form(
            turglem_struct.ByReference lem,
            byte[] s_in,
            long sz_s_in,
            byte[] s_out,
            long sz_s_out,
            int src_paradigm,
            int src_form,
            int n_form);


    long RUSSIAN_conv_string_to_letter_utf8(String s, byte[] l, long sz_l);
    long RUSSIAN_conv_binary_to_letter_utf8 (byte[] s, long sz_s, byte[] l, long sz_l);
    long RUSSIAN_conv_letter_to_string_utf8(byte[] l, long sz_l, byte[] s, long sz_s);

    long ENGLISH_conv_string_to_letter_utf8(String s, byte[] l, long sz_l);
    long ENGLISH_conv_binary_to_letter_utf8 (byte[] s, long sz_s, byte[] l, long sz_l);
    long ENGLISH_conv_letter_to_string_utf8(byte[] l, long sz_l, byte[] s, long sz_s);

    long DEFAULT_conv_string_to_letter_utf8(String s, byte[] l, long sz_l);
    long DEFAULT_conv_binary_to_letter_utf8 (byte[] s, long sz_s, byte[] l, long sz_l);
    long DEFAULT_conv_letter_to_string_utf8(byte[] l, long sz_l, byte[] s, long sz_s);

    String turglem_error_no_string(int err_no);
    String turglem_error_what_string(int err_what);
}
