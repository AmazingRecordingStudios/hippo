package it.amazingrecordingstudios.hippo.utils;

import android.os.Build;
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

    public static String patchOlderGreekCharsOnAndroid19(String unpatched) {

        // FIXME on older devices (detected in Android 19), this chars are not shown: ϛ ϙ ϡ
        // TODO: this is a temporary patch, find a better fix
        // TODO: also add Espresso test

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            String patched = unpatched.replace("ϛ", "-6-");
            patched = patched.replace("ϙ", "-9-");
            return patched.replace("ϡ", "-9-");
        }
        else {
            return unpatched;
        }

    }
}
