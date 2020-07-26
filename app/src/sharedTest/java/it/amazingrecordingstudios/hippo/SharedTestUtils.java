package it.amazingrecordingstudios.hippo;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.TreeMap;

import it.amazingrecordingstudios.hippo.model.Quote;
import it.amazingrecordingstudios.hippo.model.Schermata;
import it.amazingrecordingstudios.hippo.utils.Utils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SharedTestUtils {

    public static final String TAG = "SharedTestUtils";

    public static void checkSchermate(TreeMap<Integer, Schermata> schermate,
                                int extectedMinNumSchermate, int expectedMax) {

        boolean enoughSchermate = schermate.size() >= extectedMinNumSchermate;

        String failMessageSchermate = "Expected " + extectedMinNumSchermate
                + " schermate, found " + schermate.size();
        assertTrue(failMessageSchermate,enoughSchermate);

        boolean tooManySchermate = schermate.size() > expectedMax;

        String failMsgTooManySchermate = "Expected max " + expectedMax
                + " schermate, found " + schermate.size();
        assertFalse(failMsgTooManySchermate,tooManySchermate);
    }


    public static void checkQuotes(TreeMap<Integer, Schermata> schermate,
                             int extectedMinNumQuotes, int expectedMax) {
        int quotesCount = 0;
        for(Schermata schermata:schermate.values()) {
            quotesCount += schermata.getWordList().size();
        }

        // a quote can be reused in more screens
        // so the quote count can be higher here
        boolean enoughQuotes = quotesCount >= extectedMinNumQuotes;

        String failMessageQuotes = "Expected " + extectedMinNumQuotes
                + " quotes, found " + quotesCount;
        assertTrue(failMessageQuotes,enoughQuotes);

        boolean tooManyQuotes = quotesCount > expectedMax;

        String failMsgTooManyQuotes = "Expected max " + expectedMax
                + " quotes, found " + quotesCount;
        assertFalse(failMsgTooManyQuotes,tooManyQuotes);
    }

    public static void appendLineToStringBuilder(StringBuilder sb,
                                                 String preamble,
                                                 String lineCore) {
        final String doubleNewLine = "\n\n";

        if(lineCore != null) {
            sb.append(preamble + lineCore);
            sb.append(doubleNewLine);
        }
    }

    public static void appendLineToStringBuilder(StringBuilder sb, String str) {

        appendLineToStringBuilder(sb, "", str);
    }

    public static void appendEmptyLineToStringBuilder(StringBuilder sb) {

        appendLineToStringBuilder(sb, "<br />");
    }

    public static String getShortQuoteAsString(Schermata screen) {
        return getQuoteAsString(screen.getShortQuote());
    }

    public static String getFullQuoteAsString(Schermata screen) {
        return getQuoteAsString(screen.getFullQuote());
    }

    public static String getQuoteAsString(Quote quote) {
        String string = "";
        if(!Utils.isNullOrEmpty(quote)) {
            string = quote.getQuoteText();
        }

        return string;
    }

    public static String getSinglelineHtmlWordList(Schermata screen) {
        return Schermata.getWordListAsString(screen.getWordList(),
                ", ", ".");
    }

    public static <C, F> F getFieldValue(Class<C> someClass,
                                         C someObject,
                                         String fieldName) {
        F value = null;

        try {
            Field field =
                    someClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            value = (F) field.get(someObject);
        }
        catch (Exception e) {
            Log.e(TAG,e.toString());
        }

        return value;
    }
}