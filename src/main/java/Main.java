import model.bloomfilter.BloomFilter;
import model.bloomfilter.DefaultBloomFilter;
import model.bloomfilter.TermBloomFilter;

import java.util.*;

import static model.hflist.HashType.DEFAULT;
import static model.hflist.HashType.MURMUR;


public class Main {

    public static void main(String[] args) {

        // TODO: Use RandomStringUtils from Apache commons and java.lang.instrumentation for size testing
        // TODO: Do a performance test here that tests speed of containment compared to an ArrayList
        //       Performance test uses nanoTime class
        // TODO: Do a size test here that shows the disparity in size between BF and HashMap


        // Test 3 : Testing DEFAULT/MURMUR BloomFilter
        BloomFilter myDefaultBloomFilter = new DefaultBloomFilter(DEFAULT);
        BloomFilter myMurmurBloomFilter = new DefaultBloomFilter(1000, 6, MURMUR);

        System.out.println(printList(myDefaultBloomFilter.getBitIndices("hello")));
        System.out.println(printList(myMurmurBloomFilter.getBitIndices("hello")) + "\n");

        // ***************************************************************************************** //


        // TEST 2 : Testing out TermBloomFilter
        TermBloomFilter bloomFilter = new TermBloomFilter();

        bloomFilter.addTerm("Hi");
        bloomFilter.addTerm("Hello");
        bloomFilter.addTerm("Arvind");
        bloomFilter.addTerm("Arikatla");
        bloomFilter.addTerm("Java");
        bloomFilter.addTerm("IntelliJ");

        System.out.println(printHashSet(bloomFilter.getTerms()) + "\n");
        System.out.println(printHashMap(bloomFilter.getBloomFilterStats()));

        // ***************************************************************************************** //




    }

    private static String printHashMap(Map<String, String> hashMap) {
        if (hashMap.size() == 0) {
            return "{}";
        }

        List<String> sortedKeys = new ArrayList<>();

        for (String item : hashMap.keySet()) {
            sortedKeys.add(item);
        }

        Collections.sort(sortedKeys);

        String printList = "";

        for (String item : sortedKeys) {
            printList += item + " : " + hashMap.get(item) + "\n";
        }

        return printList;
    }


    private static String printHashSet(Set<String> hashSet) {

        if (hashSet.size() == 0) {
            return "{}";
        }

        List<String> sortedList = new ArrayList<>();

        for (String item : hashSet) {
            sortedList.add(item);
        }

        Collections.sort(sortedList);

        String printList = "{";

        for (String item : sortedList) {
            printList += item + ", ";
        }

        return printList.substring(0, printList.length() - 2) + "}";
    }


    private static String printList(List<Integer> list) {
        if (list.size() == 0) {
            return "{}";
        }

        String printList = "{";

        for (Integer item : list) {
            printList += Integer.toString(item) + ", ";
        }

        return printList.substring(0, printList.length() - 2) + "}";
    }

}
