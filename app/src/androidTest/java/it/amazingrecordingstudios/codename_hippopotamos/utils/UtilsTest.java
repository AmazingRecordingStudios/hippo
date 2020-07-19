package it.amazingrecordingstudios.codename_hippopotamos.utils;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import it.amazingrecordingstudios.codename_hippopotamos.Globals;

import static org.junit.Assert.*;

public class UtilsTest {

    AssetManager assetManager;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assetManager = appContext.getAssets();
    }

    @Test
    public void audioAssetCount() {
        int expectedAudioFilesCount = 18;
        String[] actualAudioFiles
                = Utils.getAssetsInFolder(assetManager,Globals.AUDIO_FILES_SUBFOLDER);
        assertEquals("Wrong number of assets",expectedAudioFilesCount,
                actualAudioFiles.length);
    }

    @Test
    public void audioAssetExists() {

        //NB different bahavior in API 29 and 19
        //android.os.Build.VERSION.SDK_INT
        // asset subfolder name "audio/" (with no slash) was not working in API 19 and
        // works in API 29

        String assetName = "Od.6.1-diosodisseus.ogg";
        String assetFolder = Globals.AUDIO_FILES_SUBFOLDER;

        boolean assetExists = Utils.assetExists(assetManager,assetName,assetFolder);

        String errorMsg = "Asset not found " + assetName + " in "
                + Globals.AUDIO_FILES_SUBFOLDER;
        assertTrue(errorMsg, assetExists);
    }
}