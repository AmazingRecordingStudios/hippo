package it.amazingrecordingstudios.hippo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import it.amazingrecordingstudios.hippo.model.Quote;
import it.amazingrecordingstudios.hippo.utils.Utils;

public class Globals {

    public static final String TAG = "Globals";
    public static final int PREFERRED_FONT_RESOURCE = R.font.literata;
    public static final String AUDIO_FILES_SUBFOLDER = "audio";
    public static final String FOLDER_SEPARATOR = "/";

    public static final String DEFAULT_TITLE_TEXT
            = "~";

    public static Typeface getPreferredTypeface(Context context) {
        return ResourcesCompat.getFont(
                context,
                PREFERRED_FONT_RESOURCE);
    }

    public static void ensurePreferredTypeface(Context context, TextView tv) {
        Typeface prefTypeface = getPreferredTypeface(context);
        tv.setTypeface(prefTypeface);
    }

    public static String getAudioAssetPath(AssetManager assetManager,
                                           Quote quote) {
        if(quote == null) {
            return null;
        }

        String quoteAudioFileName = quote.getAudioFileName();
        String folder = Globals.AUDIO_FILES_SUBFOLDER;

        return getAssetFilePath(assetManager,quoteAudioFileName,folder);
    }

    static String getAssetFilePath(AssetManager assetManager,
                                    String fileName,
                                    String folder) {
        if(Utils.isNullOrEmpty(fileName)) {
           return null;
        }

        String assetFilePath = null;
        if(Utils.assetExists(assetManager, fileName, folder)) {
            assetFilePath = getFilePath(fileName,folder);
        }
        else {
            Log.e(TAG,"No such asset: " + assetFilePath);

            String[] assetsNames = Utils.getAssetsInFolder(assetManager,folder);
            String assetsNamesJoined = Utils.joinString(assetsNames);

            Log.e(TAG,"Available assets in " + folder + ": "
                    + assetsNamesJoined);
        }

        return assetFilePath;
    }

    public static String getFilePath(String fileName, String folder) {
        String path = folder
                + FOLDER_SEPARATOR
                + fileName;

        return path;
    }
}
