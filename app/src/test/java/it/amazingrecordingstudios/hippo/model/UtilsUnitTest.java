package it.amazingrecordingstudios.hippo.model;

import org.junit.Test;

import it.amazingrecordingstudios.hippo.utils.Utils;

import static org.junit.Assert.*;

public class UtilsUnitTest {

    @Test
    public void parseGreekNumeral() {

        parseGreekNumeralHelper(6, "ϛ");
        parseGreekNumeralHelper(66, "ξϛ");
        parseGreekNumeralHelper(666, "χξϛ");

        parseGreekNumeralHelper(1, "α");
        parseGreekNumeralHelper(10, "ι");
        parseGreekNumeralHelper(100, "ρ");

        parseGreekNumeralHelper(241, "σμα");

        parseGreekNumeralHelper(90, "ϙ");
        parseGreekNumeralHelper(900, "ϡ");

        parseGreekNumeralHelper(-1, null);
        parseGreekNumeralHelper(0, null);
        parseGreekNumeralHelper(10000, null);

        parseGreekNumeralHelper(28, "κη");
        parseGreekNumeralHelper(750, "ψν");

        parseGreekNumeralHelper(12, "ιβ");
        parseGreekNumeralHelper(25, "κε");
        parseGreekNumeralHelper(15, "ιε");
    }

    @Test
    public void parseGreekNumeralOverAThousand() {
        parseGreekNumeralHelper(1000, "͵α");
        parseGreekNumeralHelper(1001, "͵αα");

        parseGreekNumeralHelper(1910, "͵αϡι");
        parseGreekNumeralHelper(4094, "͵δϙδ");
    }

    public void parseGreekNumeralHelper(int number, String expectedParse) {
        String parsedInt = Utils.parseGreekNumeral(number);
        assertEquals(expectedParse, parsedInt);
    }
}
