package model.bloomfilter;

// TODO Implement BF Test

import java.util.ArrayList;

public class BloomFilterTest {

    private BloomFilter bloomFilter = new DefaultBloomFilter(256, 3);
    private BloomFilter defaultBloomFilter = new DefaultBloomFilter();

    private static void printArrayList(ArrayList<Integer> list) {
        int length = list.size();
        String printList = "{";

        for (int i = 0; i < length - 1; i++) {
            printList += Integer.toString(list.get(i)) + ", ";
        }

        printList += Integer.toString(list.get(length - 1)) + "}";

        System.out.println(printList);
    }
}
