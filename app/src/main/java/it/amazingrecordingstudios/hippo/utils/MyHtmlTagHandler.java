package it.amazingrecordingstudios.hippo.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
//import android.text.style.StrikethroughSpan;

import org.xml.sax.XMLReader;

public class MyHtmlTagHandler implements Html.TagHandler {

    public void handleTag(boolean opening, String tag, Editable output,
                          XMLReader xmlReader) {
        if(tag.equals("CASE")) {
            processCaseHighlight(opening, output);
        }
    }

    private static void processCaseHighlight(boolean opening, Editable output) {
        final StyleSpan bold = new StyleSpan(Typeface.NORMAL);
        final ForegroundColorSpan green = new ForegroundColorSpan(Color.GREEN);
        //To get the color integer associated with a particular color resource ID,
        // use Resources.getColor(int, Resources.Theme)

        int len = output.length();
        if(opening) {
            output.setSpan(
                    green,
                    len, len,
                    Spannable.SPAN_MARK_MARK
            );
            output.setSpan(
                    bold,
                    len, len,
                    Spannable.SPAN_MARK_MARK
            );
        } else {
            Object obj = getLast(output, ForegroundColorSpan.class);
            int where = output.getSpanStart(obj);

            output.removeSpan(obj);

            if (where != len) {
                output.setSpan(green,
                        where,
                        len,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                output.setSpan(bold,
                        where,
                        len,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        /*
        output.setSpan(
                new ForegroundColorSpan(Color.RED),
                0, len,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        output.setSpan(
                new StyleSpan(Typeface.BOLD),
                0, len,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );*/
        // https://stackoverflow.com/questions/4044509/android-how-to-use-the-html-taghandler
        /*
        *
        * */
    }

    private static Object getLast(Editable text, Class kind) {
        Object[] objs = text.getSpans(0, text.length(), kind);

        if (objs.length == 0) {
            return null;
        } else {
            for(int i = objs.length;i>0;i--) {
                if(text.getSpanFlags(objs[i-1]) == Spannable.SPAN_MARK_MARK) {
                    return objs[i-1];
                }
            }
            return null;
        }
    }
}
