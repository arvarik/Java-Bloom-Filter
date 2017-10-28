import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import model.bloomfilter.TermBloomFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class Main {

    public static void main(String[] args) {

        // TODO: Use RandomStringUtils from Apache commons and java.lang.instrumentation for size testing
        // TODO: Do a performance test here that tests speed of containment compared to an ArrayList
        //       Performance test uses nanoTime class
        // TODO: Do a size test here that shows the disparity in size between BF and HashMap


        // TEST 1 : Testing out Google Guavas hashing capabilities
        HashFunction hf = Hashing.murmur3_32(2);

        HashCode hc = hf.newHasher()
                .putString("Arvind", Charsets.UTF_8)
                .hash();

        System.out.printf("Hash1 of 'Arvind' as int (SEED 2): %d\n\n", hc.asInt());

        // ***************************************************************************************** //


        // TEST 2 : Testing out TermBloomFilter
        TermBloomFilter bloomFilter = new TermBloomFilter();

        bloomFilter.addTerm("Hi");
        bloomFilter.addTerm("Hello");
        bloomFilter.addTerm("Arvind");

        System.out.println(printHashSet(bloomFilter.getTerms()));

        // ***************************************************************************************** //


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
