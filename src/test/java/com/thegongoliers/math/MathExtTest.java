package com.thegongoliers.math;

import com.thegongoliers.math.exceptions.OutOfBoundsException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathExtTest {

    @Test
    public void testSign(){
        assertEquals(1, MathExt.sign(10));
        assertEquals(0, MathExt.sign(0));
        assertEquals(-1, MathExt.sign(-10));
    }

    @Test
    public void testSquare(){
        assertEquals(0, MathExt.square(0), 0.0001);
        assertEquals(1, MathExt.square(1), 0.0001);
        assertEquals(4, MathExt.square(2), 0.0001);
        assertEquals(4, MathExt.square(-2), 0.0001);
    }

    @Test
    public void testPercent(){
        assertEquals(0, MathExt.percent(10, 0), 0.0001);
        assertEquals(2.5, MathExt.percent(10, 25), 0.0001);
        assertEquals(5, MathExt.percent(10, 50), 0.0001);
        assertEquals(10, MathExt.percent(10, 100), 0.0001);
        assertEquals(15, MathExt.percent(10, 150), 0.0001);
    }

    @Test
    public void testNormalize(){
        assertEquals(0, MathExt.normalize(0, 0, 100), 0.0001);
        assertEquals(0.5, MathExt.normalize(50, 0, 100), 0.0001);
        assertEquals(1, MathExt.normalize(100, 0, 100), 0.0001);
    }

    @Test (expected = OutOfBoundsException.class)
    public void testNormalizeAbove(){
        MathExt.normalize(150, 0, 100);
    }

    @Test (expected = OutOfBoundsException.class)
    public void testNormalizeBelow(){
        MathExt.normalize(-10, 0, 100);
    }

    @Test
    public void testToRange(){
        assertEquals(0, MathExt.toRange(0, 0, 100), 0.0001);
        assertEquals(50, MathExt.toRange(50, 0, 100), 0.0001);
        assertEquals(100, MathExt.toRange(100, 0, 100), 0.0001);
        assertEquals(0, MathExt.toRange(-10, 0, 100), 0.0001);
        assertEquals(100, MathExt.toRange(110, 0, 100), 0.0001);

    }

    @Test
    public void testRoundToInt(){
        assertEquals(0, MathExt.roundToInt(0));
        assertEquals(0, MathExt.roundToInt(0.2));
        assertEquals(1, MathExt.roundToInt(0.5));
        assertEquals(1, MathExt.roundToInt(0.7));
        assertEquals(1, MathExt.roundToInt(1));
    }

    @Test
    public void testRoundPlaces(){
        assertEquals(0.1, MathExt.roundPlaces(0.12, 1), 0.0001);
        assertEquals(0.0, MathExt.roundPlaces(0.02, 1), 0.0001);
        assertEquals(0.12, MathExt.roundPlaces(0.12, 2), 0.0001);
        assertEquals(0.12, MathExt.roundPlaces(0.12, 3), 0.0001);
        assertEquals(0.2, MathExt.roundPlaces(0.15, 1), 0.0001);
        assertEquals(0.15, MathExt.roundPlaces(0.15, 2), 0.0001);
        assertEquals(0, MathExt.roundPlaces(0.15, 0), 0.0001);
    }

    @Test
    public void testSnap(){
        assertEquals(0, MathExt.snap(2, 10), 0.0001);
        assertEquals(10, MathExt.snap(5, 10), 0.0001);
        assertEquals(20, MathExt.snap(25, 20), 0.0001);
    }

    @Test
    public void testApproxEqual(){
        assertTrue(MathExt.approxEqual(1, 1, 0.0001));
        assertFalse(MathExt.approxEqual(1.2, 1, 0.0001));
        assertTrue(MathExt.approxEqual(1.2, 1, 0.2));
        assertFalse(MathExt.approxEqual(1.2, 1, 0.1));
    }

    @Test
    public void testRateLimit(){
        assertEquals(0, MathExt.rateLimit(0.1, 0, 0), 0.0001);
        assertEquals(0.1, MathExt.rateLimit(0.1, 1, 0), 0.0001);
        assertEquals(0.3, MathExt.rateLimit(0.2, 1, 0.1), 0.0001);
        assertEquals(1.5, MathExt.rateLimit(1.5, 1.5, 0.3), 0.0001);
    }

}