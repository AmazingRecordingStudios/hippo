package it.amazingrecordingstudios.hippo;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.amazingrecordingstudios.hippo.database.QuotesProvider;

public class CreditsActivity extends ListActivity {

    List<Map<String, String>> creditsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        final String[] columns = {QuotesProvider.CREDIT_FIELD_NAME};
        final int[] textViews = {R.id.creditTV};
        this.creditsData = getCreditsData();

        SimpleAdapter adapter = new SimpleAdapter(this,creditsData,
                R.layout.credits_item,columns,textViews);

        this.setListAdapter(adapter);

        TextView creditsTitleTV = findViewById(R.id.creditsTitle);
        Globals.ensurePreferredTypeface(this,creditsTitleTV);
    }

    List<Map<String, String>> getCreditsData() {

        QuotesProvider quotesProvider = new QuotesProvider();
        quotesProvider.create(this);
        quotesProvider.init(QuotesProvider.DEFAULT_LANGUAGE, null);
        ArrayList<String> credits = quotesProvider.getCredits();

        List<Map<String, String>> creditsData = new ArrayList<>();

        for(String currentCredit:credits) {

            Map<String, String> currentCreditEntry = new TreeMap<>();
            currentCreditEntry.put(QuotesProvider.CREDIT_FIELD_NAME,currentCredit);
            creditsData.add(currentCreditEntry);
        }

        return creditsData;
    }
}
