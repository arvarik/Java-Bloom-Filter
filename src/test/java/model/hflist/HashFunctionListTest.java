package model.hflist;

// TODO Implement HFList Test

import java.util.ArrayList;


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

}
