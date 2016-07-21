package helpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class RomanHelperTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void recuperaInteiro() {
        assertEquals(1, RomanHelper.valueOf("I"));
        assertEquals(1, RomanHelper.valueOf("i"));
        assertEquals(3, RomanHelper.valueOf("IiI"));
        assertEquals(3, RomanHelper.valueOf("iiI"));
        assertEquals(4, RomanHelper.valueOf("IV"));
        assertEquals(5, RomanHelper.valueOf("V"));
        assertEquals(6, RomanHelper.valueOf("VI"));
        assertEquals(7, RomanHelper.valueOf("VII"));
        assertEquals(8, RomanHelper.valueOf("VIII"));
        assertEquals(9, RomanHelper.valueOf("IX"));
        assertEquals(10, RomanHelper.valueOf("X"));
        assertEquals(100, RomanHelper.valueOf("C"));
        assertEquals(150, RomanHelper.valueOf("CL"));
    }

    @Test
    public void romanoInvalido() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Algarismo romano inválido.");
        RomanHelper.valueOf("ABRACADABRA");
    }
}
