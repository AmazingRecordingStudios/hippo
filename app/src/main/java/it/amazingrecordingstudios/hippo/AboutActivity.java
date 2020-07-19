package it.amazingrecordingstudios.hippo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.widget.TextView;

import it.amazingrecordingstudios.hippo.database.QuotesProvider;

public class AboutActivity extends AppCompatActivity {

    String aboutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        this.aboutText = getAboutText();
        TextView aboutTV = findViewById(R.id.aboutTV);
        aboutTV.setText(Html.fromHtml(this.aboutText));

        aboutTV.setText(fromHtmlWithLineBreaks(this.aboutText));
        setJustified(aboutTV);
    }

    private String getAboutText() {

        QuotesProvider quotesProvider = new QuotesProvider();
        quotesProvider.create(this);
        quotesProvider.init(QuotesProvider.DEFAULT_LANGUAGE, null);
        this.aboutText = quotesProvider.getAboutText();

        return this.aboutText;
    }

    private static Spanned fromHtmlWithLineBreaks(String text) {

        String textWithLineBreaks = text;//text.replaceAll("\\</br.*?>","\\\n");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(textWithLineBreaks, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(textWithLineBreaks);
        }
    }

    private static void setJustified(TextView tv){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
    }
}
