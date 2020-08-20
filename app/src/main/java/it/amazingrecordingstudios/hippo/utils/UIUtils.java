package it.amazingrecordingstudios.hippo.utils;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public class UIUtils {

    static MyHtmlTagHandler htmlTagHandler;

    static {
        htmlTagHandler = new MyHtmlTagHandler();
    }

    public static void setHtmlText(TextView tv, String htmlText) {
        if(Utils.isNullOrEmpty(htmlText)) {
            Log.d(Utils.TAG,"Null html text string passed for text view " + tv.toString());
        } else {
            tv.setText(Html.fromHtml(htmlText,
                    null,
                    htmlTagHandler));
        }
    }
}
