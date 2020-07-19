package it.amazingrecordingstudios.hippo.model;

import android.util.Log;
import java.util.TreeMap;

public class Playlist {

    private static final String TAG = "Playlist";

    private String description;

    public String getDescription() {
        return description;
    }

    private boolean disabled;

    public boolean isDisabled() {
        return  this.disabled;
    }

    public TreeMap<Integer, Schermata> getRankedSchermate() {
        return rankedSchermate;
    }

    private TreeMap<Integer, Schermata> rankedSchermate;// key is the play order
    private TreeMap<Integer, Integer> screenRanksById;// key is the schermata id, value the play rank

    public Playlist(String description, TreeMap<Integer,
            Integer> screenRanksById,
                    boolean disabled) {

        this.description = description;
        this.screenRanksById = screenRanksById;
        this.disabled = disabled;
    }

    public void setSchermate(TreeMap<Integer, Schermata> schermateSuperSet) {

        //TODO add tests for this
        //TODO add test to ensure each screen is properly ranked within a playlsit

        this.rankedSchermate = new TreeMap<>();

        for(Schermata schermata: schermateSuperSet.values()) {
            int currentSchermataId = schermata.getId();
            if(this.screenRanksById.containsKey(currentSchermataId)) {

                int playOrder = this.screenRanksById.get(currentSchermataId);
                this.rankedSchermate.put(playOrder, schermata);
            }
        }

        logRankedScreens();
    }

    private void logRankedScreens() {
        Log.v(TAG, "Ranked screens for playlist " + this.getDescription() + ":");
        for(int playOrder: rankedSchermate.keySet()) {

            Schermata screen = rankedSchermate.get(playOrder);
            Log.v(TAG, "(" + playOrder + ") " + screen.toString());
        }
    }
}
