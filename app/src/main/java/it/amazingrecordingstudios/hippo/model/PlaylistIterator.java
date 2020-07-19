
package it.amazingrecordingstudios.hippo.model;

import android.util.Log;

import androidx.annotation.Keep;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class PlaylistIterator {

    private static final String TAG = "PlaylistIterator";

    TreeMap<Integer, Schermata> schermateById;
    TreeMap<Integer,Playlist> playlists;
    TreeMapIterator<Integer,Playlist> playlistIterator;
    TreeMap<Integer,Schermata> pagedScreens;
    static final boolean DEFAULT_SKIP_DISABLED_PLAYLISTS = true;
    boolean skipDisabledPlaylists;

    enum Move {
        SET_TO_FIRST,
        FW,
        BW
    }
    Move lastPlaylistSwitchMove;

    Playlist currentPl;
    TreeMap<Integer, Schermata> currentRankedSchermate;
    int currentScreenRank;
    Schermata currentScreen;

    boolean notMovedYet = true;

    public PlaylistIterator(TreeMap<Integer, Schermata> schermateById,
                            TreeMap<Integer,Playlist> playlists) {
        this(schermateById, playlists, DEFAULT_SKIP_DISABLED_PLAYLISTS);
    }

    public PlaylistIterator(TreeMap<Integer, Schermata> schermateById,
                            TreeMap<Integer,Playlist> playlists,
                            boolean skipDisabledPlaylists) {

        this.schermateById = schermateById;
        this.playlists = playlists;
        this.skipDisabledPlaylists = skipDisabledPlaylists;
        filterDisabledPlaylists();
        this.playlistIterator = new TreeMapIterator<>(this.playlists);
        initPagedScreens();

        this.currentPl = this.playlistIterator.next();
        this.lastPlaylistSwitchMove = Move.SET_TO_FIRST;
        Log.v(TAG,"Starting with playlist " + currentPl.getDescription());

        this.currentRankedSchermate = this.currentPl.getRankedSchermate();
    }

    void filterDisabledPlaylists() {
        if(skipDisabledPlaylists) {
            TreeMap<Integer,Playlist> enabledPlaylists = new TreeMap<>();
            for (Integer plRank : playlists.keySet()) {
                Playlist pl = playlists.get(plRank);
                if (!pl.isDisabled()) {
                    enabledPlaylists.put(plRank, pl);
                    Log.v(TAG,"Added one enabled playlist");
                }
                else {
                    Log.v(TAG,"Skipped one disabled playlist");
                }
            }
            this.playlists = enabledPlaylists;
            Log.v(TAG,"Total playlists: " + playlists.size());
            Log.v(TAG,"Using " + this.playlists.size() + " playlists");
        }
    }

    public int screensCount() {
        return screensCount(this.playlists);
    }

    private static int screensCount(TreeMap<Integer,Playlist> playlists) {
        int displayedScreensCountTmp = 0;
        for(Playlist pl: playlists.values()) {
            displayedScreensCountTmp += pl.getRankedSchermate().size();
        }
        return displayedScreensCountTmp;
    }

    void initPagedScreens() {
        this.pagedScreens = new TreeMap<>();
        final int FIRST_PAGE_NUM = 0;
        int currentPageNum = FIRST_PAGE_NUM;
        for(Playlist pl:this.playlists.values()){
            for(Schermata screen:pl.getRankedSchermate().values()) {
                pagedScreens.put(currentPageNum, screen);
                currentPageNum++;
            }
        }
    }

    public Schermata getScreenAt(int position) {
        if(position > this.screensCount()) {
            throw new IndexOutOfBoundsException();
        }

        return pagedScreens.get(position);
    }

    public Schermata getCurrentScreen() {
        if(this.notMovedYet) {
            return this.getNextScreen();
        }

        return this.currentScreen;
    }

    @Keep
    public boolean hasNextScreen() {
        if(this.notMovedYet) {
            // todo limit case: there are no screens,
            // all playlists are empty or there are no playlists
            return true;
        }

        if (currentRankedSchermate.lastKey() != this.currentScreenRank) {
            return true;
        }

        return this.playlistIterator.hasNext();

        //other limit (unlikely) scenario: the last playlist has no screen
    }

    @Keep
    public Schermata getNextScreen() {

        if(this.notMovedYet) {

            Map.Entry<Integer, Schermata> currentScreenEntry = this.currentRankedSchermate.firstEntry();
            this.currentScreen = currentScreenEntry.getValue();
            this.currentScreenRank = currentScreenEntry.getKey();

            this.notMovedYet = false;
            return this.currentScreen;

        }

        Map.Entry<Integer, Schermata> nextScreenEntry;

        if(currentRankedSchermate.lastKey() != this.currentScreenRank) {

            nextScreenEntry = this.currentRankedSchermate.higherEntry(this.currentScreenRank);

        } else {

            if(this.lastPlaylistSwitchMove == Move.BW) {
                // when we go back and forth in a ListIterator, we should skip one
                // because it returns twice the same
                this.playlistIterator.next();
            }

            if(this.playlistIterator.hasNext()) {

                this.currentPl = this.playlistIterator.next();
                this.lastPlaylistSwitchMove = Move.FW;
                Log.v(TAG,"Moving fw to playlist " + currentPl.getDescription());
                this.currentRankedSchermate = this.currentPl.getRankedSchermate();

                nextScreenEntry = this.currentRankedSchermate.firstEntry();
            } else {
                // Exception, should have checked hasNext() first
                throw new NoSuchElementException();//todo print current playlist, rank, and screen
            }
        }

        return nextScreen(nextScreenEntry);
    }

    private Schermata nextScreen(Map.Entry<Integer, Schermata> nextScreenEntry) {
        currentScreenRank = nextScreenEntry.getKey();
        currentScreen = nextScreenEntry.getValue();

        return this.currentScreen;
    }

    @Keep
    public boolean hasPrevScreen() {
        if(this.notMovedYet) {
            // currently cannot go back as a first move
            return false;
        }

        if (!weAreAtFirstScreenOfPlaylist()) {
            return true;
        }

        return this.playlistIterator.hasPrevious();

        //other limit (unlikely) scenario: the first playlist has no screens
    }

    private boolean weAreAtFirstScreenOfPlaylist() {

        return currentRankedSchermate.firstKey() == this.currentScreenRank;
    }

    @Keep
    public Schermata getPrevScreen() {

        if(this.notMovedYet) {
            // currently cannot go back as a first move
            throw new NoSuchElementException();
        }

        Map.Entry<Integer, Schermata> prevScreenEntry;

        if (!weAreAtFirstScreenOfPlaylist()) {

            prevScreenEntry = this.currentRankedSchermate.lowerEntry(this.currentScreenRank);

        } else {

            if(this.lastPlaylistSwitchMove == Move.FW) {
                // when we go back and forth in a ListIterator, we should skip one
                // because it returns twice the same
                this.playlistIterator.previous();
            }

            if(this.playlistIterator.hasPrevious()) {

                this.currentPl = this.playlistIterator.previous();
                this.lastPlaylistSwitchMove = Move.BW;
                Log.v(TAG,"Moving bw to playlist " + currentPl.getDescription());
                this.currentRankedSchermate = this.currentPl.getRankedSchermate();

                prevScreenEntry = this.currentRankedSchermate.lastEntry();
            } else {
                // Exception, should have checked hasPrevious()
                throw new NoSuchElementException();//todo print current playlist, rank, and screen
            }
        }

        return prevScreen(prevScreenEntry);
    }

    private Schermata prevScreen(Map.Entry<Integer, Schermata> prevScreenEntry) {
        currentScreenRank = prevScreenEntry.getKey();
        currentScreen = prevScreenEntry.getValue();

        return this.currentScreen;
    }

}

class TreeMapIterator<K,V> implements Iterator {

    private TreeMap<K,V> treeMap;
    private K currentKey;

    //This is for compatibility with ListIterator<E>:
    // alternating calls to next() and previous() return the same element
    enum Direction {
        NOT_STARTED_YET,
        FW,
        BW
    }
    Direction currentDirection;

    public TreeMapIterator(TreeMap<K,V> playlists) {

        this.treeMap = playlists;
        this.currentKey = playlists.firstKey();
        this.currentDirection = Direction.NOT_STARTED_YET;
    }

    private V getCurrentValue() {
        return this.treeMap.get(this.currentKey);
    }

    private K getCurrentKey() {
        return this.currentKey;
    }

    @Override
    public boolean hasNext() {

        if(this.currentDirection == Direction.NOT_STARTED_YET) {
            return this.treeMap.size() > 0;
        } else if(this.currentDirection == Direction.FW) {
            K higherKey = this.treeMap.higherKey(this.currentKey);
            return higherKey != null;
        } else {//this.currentDirection == Direction.BW) {
            return true;
        }
    }

    public boolean hasPrevious() {
        if(this.currentDirection == Direction.NOT_STARTED_YET) {
            return false;
        } else if(this.currentDirection == Direction.BW) {
            K lowerKey = this.treeMap.lowerKey(this.currentKey);
            return lowerKey != null;
        } else {//this.currentDirection == Direction.FW) {
            return true;
        }
    }

    @Override
    public V next() {

        if(!this.hasNext()) {
            throw new NoSuchElementException();
        }

        if(this.currentDirection == Direction.FW) {
            this.currentKey = this.treeMap.higherKey(this.currentKey);
        }
        //otherwise just change direction and return same element
        //this is both for NOT_STARTED_YET and BW

        this.currentDirection = Direction.FW;
        return this.getCurrentValue();
    }

    public V previous() {
        if(!this.hasPrevious()) {
            throw new NoSuchElementException();
        }

        if(this.currentDirection == Direction.BW) {
            //otherwise just change direction and return same element
            this.currentKey = this.treeMap.lowerKey(this.currentKey);
        }

        this.currentDirection = Direction.BW;
        return this.getCurrentValue();
    }
}
