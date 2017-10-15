package model.hflist;

import java.util.ArrayList;

public interface HashFunctionList {

    /**
     * Get number of hash functions in the list
     * @return Number of hash functions in the list
     */
    int getNumHashes();


    /**
     * Override current HashFunctionList instance with a fresh one
     * @param length New HashFunctionList instance length
     */
    void setNewHashFunctionList(int length);


    /**
     * Returns an int list of hashes generated from given string
     * @param key Term to get hashes from
     * @return List of int hashes
     */
    ArrayList<Integer> getIntHashListFromString(String key);


    /**
     * Returns an int list of bounded hashes generated from given string
     * @param key Term to get hashes from
     * @return List of bounded int hashes
     */
    ArrayList<Integer> getBoundedIntHashListFromString(String key, int bound);

}
