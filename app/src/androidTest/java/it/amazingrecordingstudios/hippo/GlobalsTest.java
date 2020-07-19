package it.amazingrecordingstudios.hippo;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import it.amazingrecordingstudios.hippo.model.Quote;

import static org.junit.Assert.*;

public class GlobalsTest {

    AssetManager assetManager;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assetManager = appContext.getAssets();
    }

    @Test
    public void getAudioAssetPath() {

        String assetFileName = "Od.6.1-diosodisseus.ogg";

        Quote quote = new Quote(0,"","",assetFileName);
        String expectedPath = Globals.AUDIO_FILES_SUBFOLDER
                + Globals.FOLDER_SEPARATOR + assetFileName;
        String actualPath = Globals.getAudioAssetPath(assetManager,quote);
        assertEquals("Wrong asset path",expectedPath,actualPath);
    }
}