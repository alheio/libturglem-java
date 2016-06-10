package org.lemmatizer.turglem;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import org.lemmatizer.turglem.lib.LibTurglem;
import org.lemmatizer.turglem.lib.turglem_struct;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <pre>
 *     {@code
            public static void main(String[] args) {
                try (
                    Lemmatizer turglem = new Lemmatizer(
                    "/usr/local/lib/libturglem-full.so",
                    "/usr/local/share/turglem/russian/dict_russian.auto",
                    "/usr/local/share/turglem/russian/prediction_russian.auto",
                    "/usr/local/share/turglem/russian/paradigms_russian.bin");

                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                ) {
                    Adapter adapter = new RussianAdapter(turglem.getLibrary());
                    String word;

                    while ((word = br.readLine()) != null && !word.isEmpty()) {
                        for (Iterator<WordForm> itForm = turglem.lemmatize(adapter, word); itForm.hasNext(); ) {
                            WordForm form = itForm.next();
                            System.out.print(form + " ");
                        }
                        System.out.println("\n================");
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
 *     }
 * </pre>
 * TODO: ability to extract part of speech and grammeme
 */
public class Lemmatizer implements Closeable {
    private LibTurglem libTurglem;
    private turglem_struct.ByReference tlemHandle;

    public Lemmatizer(String lib, String dict, String predict, String paradigms) throws IOException {
        libTurglem = (LibTurglem) Native.loadLibrary(lib, LibTurglem.class);
        IntByReference err_no = new IntByReference();
        IntByReference err_what = new IntByReference();

        tlemHandle = libTurglem.turglem_load(dict, predict, paradigms, err_no, err_what);
        if (null == tlemHandle) {
            throw new IOException(
                    libTurglem.turglem_error_no_string(err_no.getValue()) + " " +
                            libTurglem.turglem_error_what_string(err_what.getValue()));
        }
    }

    @Override
    public void close() throws IOException {
        if (null != libTurglem && null != tlemHandle) {
            libTurglem.turglem_close(tlemHandle);
            tlemHandle = null;
            libTurglem = null;
        }
    }

    public LibTurglem getLibrary() {
        return libTurglem;
    }

    public List<WordForm> lemmatize(Adapter adapter, String token) throws UnsupportedEncodingException {
        long lettersLen = adapter.stringToMAFSALetters(token, adapter.MAFSAletters);
        return lemmatize(adapter, lettersLen);
    }

    public List<WordForm> lemmatize(Adapter adapter, char[] token, long tokenLen) {
        // TODO: add LANG_conv_binary_to_letter_utf16 into c-libturglem for better perfomance
        CharBuffer charBuffer = CharBuffer.wrap(token, 0, (int)tokenLen);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = byteBuffer.array();
        long lettersLen = adapter.stringToMAFSALetters(bytes, bytes.length, adapter.MAFSAletters);
        return lemmatize(adapter, lettersLen);
    }

    public List<WordForm> lemmatize(Adapter adapter, byte[] token, long tokenLen) {
        long lettersLen = adapter.stringToMAFSALetters(token, tokenLen, adapter.MAFSAletters);
        return lemmatize(adapter, lettersLen);
    }

    protected List<WordForm> lemmatize(Adapter adapter, long lettersLen) {
        adapter.formsActual.clear();
        if (lettersLen > 0) {
            long fcount = libTurglem.turglem_lemmatize(
                    tlemHandle,
                    adapter.MAFSAletters,lettersLen,
                    adapter.formData,adapter.formData.length,
                    (byte)adapter.getDelimiter(),
                    1
            );

            int srcParadigm, srcForm;
            for (int i = 0; i < fcount && i<adapter.formsPool.length; ++i) {

                srcParadigm = adapter.formData[2*i];
                srcForm = adapter.formData[2*i+1];

                long sz_l_dst = libTurglem.turglem_build_form(
                        tlemHandle,
                        adapter.MAFSAletters, lettersLen,
                        adapter.MAFSAlexeme, adapter.MAFSAlexeme.length,
                        srcParadigm,
                        srcForm,
                        0
                );

                long len = adapter.MAFSALettersToString(adapter.MAFSAlexeme, sz_l_dst, adapter.jStringlexeme);
                if (len > 0) {
                    WordForm wf = adapter.formsPool[i];
                    wf.setLexeme(new String(adapter.jStringlexeme, 0, (int)len));
                    wf.setSrcParadigm(srcParadigm);
                    wf.setSrcForm(srcForm);
                    adapter.formsActual.add(wf);
                }
            }
        }

        return adapter.formsActual;
    }
}
