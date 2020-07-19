package it.amazingrecordingstudios.hippo.model;

import org.junit.Test;

import java.util.TreeMap;

import static org.junit.Assert.*;

public class PlaylistIteratorUnitTest {

    @Test
    public void getNextScreen() {

        PlaylistIterator plItr = getBasicPlaylistIterator();

        //iterate till last screen
        while(plItr.hasNextScreen()) {
            plItr.getNextScreen();
        }
        //than back to first
        while(plItr.hasPrevScreen()) {
            plItr.getPrevScreen();
        }

        // go fw, should change pl and screen
        assertEquals("Wrong screen", 1, plItr.getNextScreen().getId());

    }

    private static Schermata getMockScreen(int id) {
        return new Schermata(id,
                "","",
                0,0,
                "","",
                "","");
    }

    @Test
    public void getPrevScreen() {

        //iterate till last screen
        PlaylistIterator plItr = getBasicPlaylistIterator();
        assertTrue("screen 0 fw ok", 0 == plItr.getNextScreen().getId());
        assertTrue("screen 1 fw ok", 1 == plItr.getNextScreen().getId());
        assertTrue("screen 2 fw ok", 2 == plItr.getNextScreen().getId());

        assertTrue("screen 1 bw ok", 1 == plItr.getPrevScreen().getId());

        assertEquals("Wrong screen", 0, plItr.getPrevScreen().getId());

    }

    private static PlaylistIterator getBasicPlaylistIterator() {


        // create two playlists
        TreeMap<Integer,Playlist> playlists = new TreeMap<>();
        TreeMap<Integer, Schermata> schermateById = new TreeMap<>();

        int firstScreenId = 0;
        int secondScreenId = 1;
        int thirdScreenId = 2;
        // 1st pl with one screen
        {
            TreeMap<Integer, Integer> playListOneAsRankedSchermate = new TreeMap<>();
            int firstScreeenPlayOrder = 0;
            schermateById.put(firstScreenId,getMockScreen(firstScreenId));
            playListOneAsRankedSchermate.put(firstScreenId, firstScreeenPlayOrder);
            Playlist currentPlaylist = new Playlist("1st Pl",
                    playListOneAsRankedSchermate, false);
            currentPlaylist.setSchermate(schermateById);
            playlists.put(1, currentPlaylist);
        }

        // 2nd pl with two screens
        {
            TreeMap<Integer, Integer> playListTwoAsRankedSchermate = new TreeMap<>();
            int secondScreeenPlayOrder = 1;
            schermateById.put(secondScreenId,getMockScreen(secondScreenId));
            playListTwoAsRankedSchermate.put(secondScreenId, secondScreeenPlayOrder);
            int thirdScreeenPlayOrder = 2;
            schermateById.put(thirdScreenId,getMockScreen(thirdScreenId));
            playListTwoAsRankedSchermate.put(thirdScreenId, thirdScreeenPlayOrder);

            Playlist currentPlaylist = new Playlist("2nd Pl",
                    playListTwoAsRankedSchermate, false);
            currentPlaylist.setSchermate(schermateById);
            playlists.put(2,currentPlaylist);
        }

        return new PlaylistIterator(schermateById, playlists);
    }
}