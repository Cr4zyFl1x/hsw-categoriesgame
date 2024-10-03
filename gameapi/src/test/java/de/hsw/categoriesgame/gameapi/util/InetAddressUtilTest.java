package de.hsw.categoriesgame.gameapi.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class InetAddressUtilTest {

    @ParameterizedTest
    @CsvSource(value = {
            "127.1|127.0.0.1",
            "::1|0:0:0:0:0:0:0:1",
            "10.8.1|10.8.0.1",
            "2003::1:9:45a|2003:0:0:0:0:1:9:45a"
    }, delimiter = '|')
    public void testGetLongIP(final String actual, final String expected)
    {
        final String full = InetAddressUtil.getLongIP(actual);
        assertEquals(expected, full);
    }
}