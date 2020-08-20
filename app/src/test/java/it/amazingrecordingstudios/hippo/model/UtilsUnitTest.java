package it.amazingrecordingstudios.hippo.model;

import org.junit.Test;

import it.amazingrecordingstudios.hippo.utils.Utils;

import static org.junit.Assert.*;

public class UtilsUnitTest {

    @Test
    public void parseGreekNumeralForGeneralCounting() {

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
    public void parseGreekNumeralForGeneralCountingOverAThousand() {
        parseGreekNumeralHelper(1000, "͵α");
        parseGreekNumeralHelper(1001, "͵αα");

        parseGreekNumeralHelper(1910, "͵αϡι");
        parseGreekNumeralHelper(4094, "͵δϙδ");
    }

    public void parseGreekNumeralHelper(int number, String expectedParse) {
        String parsedInt = Utils.parseGreekNumeralForGeneralCounting(number);
        assertEquals(expectedParse, parsedInt);
    }

    @Test
    public void parseGreekNumeralForSectionsOfLiteraryWorks() {
        parseGreekNumeralHelperForSectionsOfLiteraryWorks(1, "α");
        parseGreekNumeralHelperForSectionsOfLiteraryWorks(6, "ζ");
        parseGreekNumeralHelperForSectionsOfLiteraryWorks(10, "κ");
        // α β γ δ ε ζ η θ ἰ κ λ μ ν ξ ο π ρ σ τ υ φ χ ψ ω
    }

    public void parseGreekNumeralHelperForSectionsOfLiteraryWorks(int number, String expectedParse) {
        String parsedInt = Utils.parseGreekNumeralForSectionsOfALiteraryWork(number);
        assertEquals(expectedParse, parsedInt);
    }
}
