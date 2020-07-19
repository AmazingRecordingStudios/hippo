package it.amazingrecordingstudios.hippo.model;

import java.io.Serializable;

public class Quote implements Serializable {

    private int id;
    private int positionInSchermata;

    public int getPositionInSchermata() {
        return positionInSchermata;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public String getPhoneticTranscription() {
        return phoneticTranscription;
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    private String quoteText;
    private String phoneticTranscription;
    private String audioFileName;

    // TODO translation, according to user preference (english, italian)
    // TODO translation into schermata class?

    public Quote(int id,
                 int positionInSchermata,
                 String quoteText,
                 String phoneticTranscription,
                 String audioFileName) {
        this(id,quoteText,phoneticTranscription,audioFileName);

        this.positionInSchermata = positionInSchermata;
    }

    public Quote(int id,
                 String quoteText,
                 String phoneticTranscription,
                 String audioFileName) {

        this.id = id;
        this.quoteText = quoteText;
        this.phoneticTranscription = phoneticTranscription;
        this.audioFileName = audioFileName;
    }
}
