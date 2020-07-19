package it.amazingrecordingstudios.hippo.model;

import java.io.Serializable;
import java.util.ArrayList;

import it.amazingrecordingstudios.hippo.utils.Utils;

public class Schermata implements Serializable {

    //TODO add option in the db: boolean highlighAccent
    //to be used in ear training screens

    public int getId() {
        return id;
    }

    private int id;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLinguisticNotes() {
        return linguisticNotes;
    }

    public String getCitation() {

        if(citation == null) {
            return null;
        } else {
            return "(" + citation + ")";
        }
    }

    public String getEasterEggComment() {
        return easterEggComment;
    }

    private String title;
    private String description;

    public String getTranslation() {
        if(translation == null) {
            return null;
        } else {
            return "(" + translation + ")";
        }
    }

    private String translation;
    private String citation;
    private String linguisticNotes;
    private String easterEggComment;

    public void addWordToList(Quote word) {

        this.wordList.add(word);
    }

    public ArrayList<Quote> getWordList() {
        return this.wordList;
    }

    private ArrayList<Quote> wordList;
    private String wordListAsHtmlString;

    public String getMultilineHtmlWordList() {

        if(this.wordListAsHtmlString != null) {
            return this.wordListAsHtmlString;
        }

        final String newLineWordSeparator = "\n";
        final String noClosing = "";
        this.wordListAsHtmlString = getWordListAsString(this.getWordList(),
                newLineWordSeparator,noClosing);

        return this.wordListAsHtmlString;
    }

    public static String getWordListAsString(ArrayList<Quote> words,
                                             String wordSeparator,
                                             String closing) {

        StringBuilder sb = new StringBuilder();

        if(!Utils.isNullOrEmpty(words)) {
            int currentWordNum = 1;
            for(Quote word : words) {

                String wordText = word.getQuoteText();

                boolean isLastQuote = (currentWordNum == words.size());
                if(isLastQuote) {
                    final String COMMA = ".";
                    if(Utils.isNullOrEmpty(wordText)) {
                        sb.append(closing);
                    } else if(wordText.endsWith(closing)) {
                        sb.append(wordText);
                    } else {
                        sb.append(wordText + closing);
                    }
                }
                else {
                    sb.append(wordText + wordSeparator);
                }

                currentWordNum++;
            }
        }

        return sb.toString();
    }

    private Quote shortQuote;

    public Integer getShortQuoteId() {
        return shortQuoteId;
    }

    public Integer getFullQuoteId() {
        return fullQuoteId;
    }

    private Integer shortQuoteId;

    public Quote getShortQuote() {
        return shortQuote;
    }

    public void setShortQuote(Quote shortQuote) {
        this.shortQuote = shortQuote;
    }

    public Quote getFullQuote() {
        return fulltQuote;
    }

    public void setFulltQuote(Quote fulltQuote) {
        this.fulltQuote = fulltQuote;
    }

    private Quote fulltQuote;
    private Integer fullQuoteId;




    public Schermata(int id,
                     String title,
                     String description,
                     Integer shortQuoteId,
                     Integer fullQuoteId,
                     String translation,
                     String linguisticNotes,
                     String citation,
                     String easterEggComment) {
        this.id = id;
        this.title = title;
        this.translation = translation;
        this.description = description;
        this.shortQuoteId = shortQuoteId;
        this.fullQuoteId = fullQuoteId;
        this.linguisticNotes = linguisticNotes;
        this.citation = citation;
        this.easterEggComment = easterEggComment;

        this.wordList = new ArrayList<Quote>();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if ((obj instanceof Schermata) && (((Schermata) obj).id == this.id)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {

        return this.title + " | " + this.description + " | " + this.translation;
    }
}
