package it.amazingrecordingstudios.hippo;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.amazingrecordingstudios.hippo.database.QuotesProvider;
import it.amazingrecordingstudios.hippo.model.Playlist;

import static org.junit.Assert.*;

public class PlaylistsActivityTest {

    @Test
    public void doNotShowDisabledPlaylistOnMenu() {


        QuotesProvider quotesProvider = new QuotesProvider();


        Context appContext;

        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        quotesProvider.create(appContext);
        quotesProvider.init(QuotesProvider.DEFAULT_LANGUAGE, null);
        TreeMap<Integer, Playlist> playlistByRank = quotesProvider.getPlaylistsByRank();
        SharedTestUtils.init(quotesProvider);
        quotesProvider.getSchermateById();

        List<Map<String, String>> playlistsNamesData
                = PlaylistsActivity.getPlaylistsNamesDataHelper(playlistByRank);

        ArrayList<String> plNames = new ArrayList<>();
        for(Map<String, String> plEntry:playlistsNamesData) {
            String plName = plEntry.get(QuotePagerActivity.PLAYLIST_NAME_EXTRA_KEY);
            plNames.add(plName);
        }

        for(Playlist pl:playlistByRank.values()) {
            if(pl.isDisabled()) {
                assertFalse(plNames.contains(pl.getDescription()));
            } else {
                assertTrue(plNames.contains(pl.getDescription()));
            }

        }
    }
}