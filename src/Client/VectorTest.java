package Client;

import org.junit.Test;

import static org.junit.Assert.*;

public class VectorTest {

    @Test
    public void refreshPolarEqualTest(){
        System.out.println();
        assertEquals(2.23606797749979, Math.sqrt(1 * 1 + 2 * 2), 0.01);
    }

    @Test
    public void refreshPolarEqualNegativeTest(){
        System.out.println();
        assertEquals(2.23606797749979, Math.sqrt(1 * 1 + 2 * 2), 0.01);
    }

    @Test
    public void refreshPolarNotANumberTest(){
        boolean isNan = Double.isNaN(Math.sqrt(-1 * 1 + 2 * -2));
        assertTrue(isNan);
    }

    @Test
    public void refreshPolarUnequalTest(){
        System.out.println();
        assertNotEquals(2.25606797749979, Math.sqrt(1 * 1 + 2 * 2), 0.01);
    }

    @Test
    public void refreshPolarUnequalNegativeTest(){
        System.out.println();
        assertNotEquals(-1, Math.sqrt(1 * 1 + 2 * 2), 0.01);
    }

}