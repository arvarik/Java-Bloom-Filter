package model.bloomfilter;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TermBloomFilterTest {

    private TermBloomFilter termBloomFilter;
    private TermBloomFilter defaultTermBloomFilter;

    @Before
    public void beforeTestSetup() {
        termBloomFilter = new TermBloomFilter(256, 3);
        defaultTermBloomFilter = new TermBloomFilter();
    }


    @Test
    public void testBloomFilterConstructor_getMethods() {
        // Test all default constructor attributes
        assertEquals(1400, defaultTermBloomFilter.getBloomFilterSize());
        assertEquals(10, defaultTermBloomFilter.getNumHashFunctions());
        assertEquals(0, defaultTermBloomFilter.getNumTerms());
        assertEquals(new HashSet(), defaultTermBloomFilter.getTerms());

        // Test all main constructor attributes
        assertEquals(256, termBloomFilter.getBloomFilterSize());
        assertEquals(3, termBloomFilter.getNumHashFunctions());
        assertEquals(0, termBloomFilter.getNumTerms());
        assertEquals(new HashSet(), termBloomFilter.getTerms());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_whenIllegalSize_throwIllegalArg() {
        new DefaultBloomFilter(4, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_whenIllegalNumHashFunctions_throwIllegalArg() {
        new DefaultBloomFilter(10, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimalMethod_whenNumTermNegative_throwIllegalArg() {
        termBloomFilter.getOptimalSizeAndNumHfs(-3, .5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimalMethod_whenNumTermFour_throwIllegalArg() {
        termBloomFilter.getOptimalSizeAndNumHfs(4, .5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimalMethod_whenRateGreaterThanOne_throwIllegalArg() {
        termBloomFilter.getOptimalSizeAndNumHfs(100, 1.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimalMethod_whenRateLessThanZero_throwIllegalArg() {
        termBloomFilter.getOptimalSizeAndNumHfs(100, -.01);
    }

    @Test
    public void testBloomFilter_whenAdd_bfContainsTerm() {
        termBloomFilter.addTerm("Test1");
        termBloomFilter.addTerm("Test2");
        termBloomFilter.addTerm("Test3");

        assertEquals(3, termBloomFilter.getNumTerms());
        assertEquals("{Test1, Test2, Test3}", printHashSet(termBloomFilter.getTerms()));

        termBloomFilter.addTerm("Test4");

        assertEquals(4, termBloomFilter.getNumTerms());
        assertEquals("{Test1, Test2, Test3, Test4}", printHashSet(termBloomFilter.getTerms()));
    }

    @Test
    public void testBloomFilter_whenAddNull_bfNoChange() {
        termBloomFilter.addTerm("Test1");
        termBloomFilter.addTerm("Test2");

        assertEquals(2, termBloomFilter.getNumTerms());
        assertEquals("{Test1, Test2}", printHashSet(termBloomFilter.getTerms()));

        termBloomFilter.addTerm(null);

        assertEquals(2, termBloomFilter.getNumTerms());
        assertEquals("{Test1, Test2}", printHashSet(termBloomFilter.getTerms()));
    }


    @Test
    public void testBloomFilter_setContainment_whenFalse() {
        termBloomFilter.addTerm("1-Test");
        termBloomFilter.addTerm("2-Test");
        termBloomFilter.addTerm("3-Test");

        assertFalse(termBloomFilter.inTheSet("4-Test"));
        assertFalse(termBloomFilter.inTheSet("!@#$%^&*()"));
        assertFalse(termBloomFilter.inTheSet(null));
    }


    @Test
    public void testBloomFilter_setContainment_whenTrue() {
        termBloomFilter.addTerm("1-Test");
        termBloomFilter.addTerm("!@#$%^&*()");

        assertTrue(termBloomFilter.inTheSet("1-Test"));
        assertTrue(termBloomFilter.inTheSet("!@#$%^&*()"));
    }


    @Test
    public void testBloomFilter_whenCleared_bfIsEmpty() {
        termBloomFilter.addTerm("1-Test");
        termBloomFilter.addTerm("2-Test");
        termBloomFilter.addTerm("3-Test");

        termBloomFilter.clearBloomFilter();

        assertEquals("{}", printHashSet(termBloomFilter.getTerms()));
        assertEquals(0, termBloomFilter.getNumTerms());
    }

    private static String printHashSet(Set<String> list) {

        if (list.size() == 0) {
            return "{}";
        }

        List<String> sortedList = new ArrayList<>();

        for (String item : list) {
            sortedList.add(item);
        }

        Collections.sort(sortedList);

        String printList = "{";

        for (String item : sortedList) {
            printList += item + ", ";
        }

        return printList.substring(0, printList.length() - 2) + "}";
    }

}

