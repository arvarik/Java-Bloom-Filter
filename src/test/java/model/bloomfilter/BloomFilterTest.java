package model.bloomfilter;

// TODO Implement tests for BF

import java.util.ArrayList;
import java.util.HashMap;

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

    private static void printHashMap(HashMap<String, String> hashMap) {

        //TODO Hashmap print function
    }
}
