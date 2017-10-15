package model.bloomfilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BloomFilterTest {

    private BloomFilter bloomFilter;
    private BloomFilter defaultBloomFilter;

    @Before
    public void beforeTestSetup() {
        bloomFilter = new DefaultBloomFilter(256, 3);
        defaultBloomFilter = new DefaultBloomFilter();
    }


    @Test
    public void testBloomFilterConstructor_getMethods() {
        // Test all default constructor attributes
        assertEquals(1400, defaultBloomFilter.getBloomFilterSize());
        assertEquals(10, defaultBloomFilter.getNumHashFunctions());
        assertEquals(0, defaultBloomFilter.getNumTerms());
        assertEquals(new HashSet(), defaultBloomFilter.getTerms());

        // Test all main constructor attributes
        assertEquals(256, bloomFilter.getBloomFilterSize());
        assertEquals(3, bloomFilter.getNumHashFunctions());
        assertEquals(0, bloomFilter.getNumTerms());
        assertEquals(new HashSet(), bloomFilter.getTerms());
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
        bloomFilter.getOptimalSizeAndNumHfs(-3, .5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimalMethod_whenNumTermFour_throwIllegalArg() {
        bloomFilter.getOptimalSizeAndNumHfs(4, .5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimalMethod_whenRateGreaterThanOne_throwIllegalArg() {
        bloomFilter.getOptimalSizeAndNumHfs(100, 1.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimalMethod_whenRateLessThanZero_throwIllegalArg() {
        bloomFilter.getOptimalSizeAndNumHfs(100, -.01);
    }

    @Test
    public void testBloomFilter_whenAdd_bfContainsTerm() {
        bloomFilter.addTerm("Test1");
        bloomFilter.addTerm("Test2");
        bloomFilter.addTerm("Test3");

        assertEquals(3, bloomFilter.getNumTerms());
        assertEquals("{Test1, Test2, Test3}", printHashSet(bloomFilter.getTerms()));

        bloomFilter.addTerm("Test4");

        assertEquals(4, bloomFilter.getNumTerms());
        assertEquals("{Test1, Test2, Test3, Test4}", printHashSet(bloomFilter.getTerms()));
    }

    @Test
    public void testBloomFilter_whenAddNull_bfNoChange() {
        bloomFilter.addTerm("Test1");
        bloomFilter.addTerm("Test2");

        assertEquals(2, bloomFilter.getNumTerms());
        assertEquals("{Test1, Test2}", printHashSet(bloomFilter.getTerms()));

        bloomFilter.addTerm(null);

        assertEquals(2, bloomFilter.getNumTerms());
        assertEquals("{Test1, Test2}", printHashSet(bloomFilter.getTerms()));
    }


    @Test
    public void testBloomFilter_setContainment_whenFalse() {
        bloomFilter.addTerm("1-Test");
        bloomFilter.addTerm("2-Test");
        bloomFilter.addTerm("3-Test");

        assertFalse(bloomFilter.inTheSet("4-Test"));
        assertFalse(bloomFilter.inTheSet("!@#$%^&*()"));
        assertFalse(bloomFilter.inTheSet(null));
    }


    @Test
    public void testBloomFilter_setContainment_whenTrue() {
        bloomFilter.addTerm("1-Test");
        bloomFilter.addTerm("!@#$%^&*()");

        assertTrue(bloomFilter.inTheSet("1-Test"));
        assertTrue(bloomFilter.inTheSet("!@#$%^&*()"));
    }


    @Test
    public void testBloomFilter_whenCleared_bfIsEmpty() {
        bloomFilter.addTerm("1-Test");
        bloomFilter.addTerm("2-Test");
        bloomFilter.addTerm("3-Test");

        bloomFilter.clearBloomFilter();

        assertEquals("{}", printHashSet(bloomFilter.getTerms()));
        assertEquals(0, bloomFilter.getNumTerms());
    }

    private static String printHashSet(HashSet<String> list) {

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
