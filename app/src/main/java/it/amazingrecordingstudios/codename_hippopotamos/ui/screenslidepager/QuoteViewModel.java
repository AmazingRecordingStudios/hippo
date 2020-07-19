package it.amazingrecordingstudios.codename_hippopotamos.ui.screenslidepager;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.TreeMap;

import it.amazingrecordingstudios.codename_hippopotamos.database.QuotesProvider;
import it.amazingrecordingstudios.codename_hippopotamos.model.Playlist;
import it.amazingrecordingstudios.codename_hippopotamos.model.PlaylistIterator;
import it.amazingrecordingstudios.codename_hippopotamos.model.Schermata;

public class QuoteViewModel extends AndroidViewModel {
    // WIP: Implement the ViewModel

    TreeMap<Integer, Schermata> schermateById;
    TreeMap<Integer, Playlist> playlists;
    PlaylistIterator plItr;
    QuotesProvider quotesProvider;

    public QuoteViewModel(Application application) {
        super(application);

        this.quotesProvider = new QuotesProvider();
        this.quotesProvider.create(application);
    }

    private TreeMap<Integer, Schermata> getSchermateById() {

        return this.schermateById;
    }

    public void init(QuotesProvider.Languages language,
                     String playlistDescriptor) {

        initQuotesProvider(language, playlistDescriptor);

        //TODO FIXME retrieve language settings from shared preferences
        this.schermateById = quotesProvider.getSchermateById();
        this.playlists = quotesProvider.getPlaylistsByRank();
        //TODO
        // keep current schermata in playlist
        this.plItr = new PlaylistIterator(this.schermateById, this.playlists);
    }

    private void initQuotesProvider(QuotesProvider.Languages language,
                                    String playlistDescriptor) {

        //TODO pass playlist filter and language choice
        this.quotesProvider.init(language, playlistDescriptor);
    }

    public int getScreenCount() {

        return this.plItr.screensCount();
    }

    public Schermata getScreenAt(int position) {
        return this.plItr.getScreenAt(position);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        this.quotesProvider.close();
    }

    /*public Schermata getScreen(int screenId) {
        return this.getSchermateById().get(screenId);
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }*/
}
