package model.bloomfilter;

import java.util.ArrayList;
import java.util.HashMap;


interface BloomFilter {

    /**
     * Checks the bloom filter to see status of key
     * @param key Term to check in the bloom filter
     * @return Response if key was probably in the set or definitely not in the set
     */
    String checkTerm(String key);


    /**
     * Adds a term to the bloom filter
     * @param key Term to add into the bloom filter
     */
    void addTerm(String key);


    /**
     * Returns all the hashed outputs of term
     * @param key Term to check in bloom filter
     * @return List of bit indices corresponding to term
     */
    ArrayList<Integer> getBitIndices(String key);


    /**
     * Gets a few statistics of the current bloomfilter
     * @return Hash map of statistic name and its associated value
     */
    HashMap<String, String> getBloomFilterStats();


    /**
     * Returns the total in-memory storage of bloom filter in bits
     * @return Size of bloom filter
     */
    int getBloomFilterSize();


    /**
     * Returns total number of hash functions applied to each string
     * @return Number of hash functions
     */
    int getNumHashFunctions();


    /**
     * Clears the bloom filter of all strings
     */
    void clearBloomFilter();

}
