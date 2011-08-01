package com.java1337.samples.interview;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EnumeratorTest {

    public class Enumerator {
        public List<Integer> enumerate(int max) {
            int counter = 0;
            List<Integer> returnMe = new ArrayList<Integer>(max);
            while (counter < max) {
               counter = counter++;
               returnMe.add(counter);
            }
            return returnMe;
        }
    }

    @Test
    public void testEnumeratorOne() {
        Enumerator enumerator = new Enumerator();
        List<Integer> actual = enumerator.enumerate(3);
        assertEquals(3, actual.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(i, actual.get(i).intValue());
        }
    }

    @Test
    public void testEnumeratorTwo() {
        Enumerator enumerator = new Enumerator();
        List<Integer> actual = enumerator.enumerate(3);
        assertEquals(3, actual.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(i+1, actual.get(i).intValue());
        }
    }

    @Test
    public void testEnumeratorThree() {
        Enumerator enumerator = new Enumerator();
        List<Integer> actual = enumerator.enumerate(0);
        assertEquals(0, actual.size());
    }
}
