package it.collideorscopeapps.codename_hippopotamos;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.QuotesProvider;
import it.collideorscopeapps.codename_hippopotamos.model.Playlist;

public class PlaylistsActivity extends ListActivity {

    static final String PLAYLIST_NUMBER_COL_NAME = "playlistNumber";

    // TODO underline the last clicked playlist

    List<Map<String, String>> playlistsNamesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        final String[] columns = {PLAYLIST_NUMBER_COL_NAME,
                QuotePagerActivity.PLAYLIST_NAME_EXTRA_KEY};
        final int[] textViews = {R.id.playlistNumber, R.id.playlistName};
        this.playlistsNamesData = playlistsNamesData();

        SimpleAdapter adapter = new SimpleAdapter(this,playlistsNamesData,
                R.layout.playlist_item,columns,textViews);

        this.setListAdapter(adapter);

        TextView titleTV = findViewById(R.id.playlistsTitle);
        Globals.ensurePreferredTypeface(this,titleTV);
        titleTV.setText(Globals.DEFAULT_TITLE_TEXT);
    }

    List<Map<String, String>> playlistsNamesData() {

        QuotesProvider quotesProvider = new QuotesProvider();
        quotesProvider.create(this);
        quotesProvider.init(QuotesProvider.DEFAULT_LANGUAGE, null);
        TreeMap<Integer,Playlist> playlistByRank = quotesProvider.getPlaylistsByRank();

        List<Map<String, String>> playlistsNamesData = new ArrayList<>();

        int currentPlaylistNumber = 1;
        for(Playlist currentPlaylist:playlistByRank.values()) {

            String playListGreekNumber = parseGreekNumeral(currentPlaylistNumber);
            String playlistName = currentPlaylist.getDescription();

            Map<String, String> currentPlaylistEntry = new TreeMap<>();
            currentPlaylistEntry.put(PLAYLIST_NUMBER_COL_NAME,playListGreekNumber);
            currentPlaylistEntry.put(QuotePagerActivity.PLAYLIST_NAME_EXTRA_KEY,playlistName);

            playlistsNamesData.add(currentPlaylistEntry);

            currentPlaylistNumber++;
        }

        return playlistsNamesData;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //TODO set text style of current item as underlined
        // save the position of the previous selected item
        // and set text to normal

        //TODO check that numbering of position starts at 0, so it's same as ArrayList index
        Map<String, String> playlistData = this.playlistsNamesData.get(position);
        String playlistName = playlistData.get(QuotePagerActivity.PLAYLIST_NAME_EXTRA_KEY);

        Intent intent = new Intent(PlaylistsActivity.this,
                QuotePagerActivity.class);
        intent.putExtra(QuotePagerActivity.PLAYLIST_NAME_EXTRA_KEY, playlistName);
        intent.setAction(QuotePagerActivity.PLAY_ACTION);
        startActivity(intent);
    }

    static String parseGreekNumeral(int number) {

        final String greekNumeralsString = "αβγδεζηθικλμνξοπρστυφχψω";
        char[] greekNumerals = greekNumeralsString.toCharArray();

        if(number > greekNumerals.length) {
            return null;
        }

        return String.valueOf(greekNumerals[number - 1]);
    }
}
