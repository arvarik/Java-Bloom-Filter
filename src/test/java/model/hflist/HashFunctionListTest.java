package model.hflist;

// TODO Implement tests for HFList

import java.util.ArrayList;
import java.util.HashMap;


public class HashFunctionListTest {

    private HashFunctionList hashFunctionList = new DefaultHashFunctionList(5);
    private HashFunctionList defaultHashFunctionList = new DefaultHashFunctionList();

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
