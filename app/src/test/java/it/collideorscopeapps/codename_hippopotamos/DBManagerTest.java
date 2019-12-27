package it.collideorscopeapps.codename_hippopotamos;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.DBManager;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DBManagerTest {

    private static final String FAKE_STRING = "HELLO_WORLD";

    @Mock
    Context mockContext;
    //android.test.mock.MockContext

    // Given a Context object retrieved from Robolectric...
    //private Context context = ApplicationProvider.getApplicationContext();

    @Test@Ignore
    public void readStringFromContext_LocalizedString() {

        mockContext = mock(Context.class);

        // Given a mocked Context injected into the object under test...
        //when(mockContext.getString(R.string.hello_world))
        //        .thenReturn(FAKE_STRING);
        DBManager dbManager = new DBManager(mockContext);

        // ...when the string is returned from the object under test...
        //String result = dbManager.getHelloWorldString();

        // ...then the result should be the expected one.
        //assertThat(result).isEqualTo(FAKE_STRING);

        TreeMap<Integer, Schermata> schermate = dbManager.getSchermateById(DBManager.Languages.EN);

        int extectedMinNumSchermate = 27;
        int extectedMinNumQuotes = 32;
        int maxSchermate = 100;
        int maxQuotes = 100;
        TestUtils.checkSchermate(schermate,extectedMinNumSchermate, maxSchermate);
        TestUtils.checkQuotes(schermate,extectedMinNumQuotes,maxQuotes);
    }

}
