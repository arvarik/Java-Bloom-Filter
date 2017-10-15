package model.bloomfilter;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;


public interface BloomFilter {

    /**
     * Method to help user construct an optimal bloomfilter
     * @param expectedNumTerms Expected number of terms user's bloomfilter will contain
     * @param desiredFalsePositive Desired false positive ratio r where 0 < r < 1
     * @return A list of size two with optimal bloomfilter size and number of hash functions
     */
    ArrayList<Integer> getOptimalSizeAndNumHfs(int expectedNumTerms, double desiredFalsePositive);


    /**
     * Checks the bloom filter to see status of key
     * @param key Term to check in the bloomfilter
     * @return Response if key was probably in the set (1) or definitely not in the set (0)
     */
    boolean inTheSet(String key);


    /**
     * Checks the bloom filter to see status of key
     * @param key Term to check in the bloomfilter
     * @return String response if key was probably in the set or definitely not in the set
     */
    String checkTerm(String key);


    /**
     * Adds a term to the bloom filter
     * @param key Term to add into the bloomfilter
     */
    void addTerm(String key);


    /**
     * Returns all the hashed outputs of term
     * @param key Term to check in bloomfilter
     * @return List of bit indices corresponding to term
     */
    ArrayList<Integer> getBitIndices(String key);


    /**
     * Gets a few statistics of the current bloomfilter
     * @return Hash map of statistic name and its associated value
     */
    HashMap<String, String> getBloomFilterStats();


    /**
     * Returns the total in-memory storage of bloomfilter in bits
     * @return Size of bloom filter
     */
    int getBloomFilterSize();


    /**
     * Returns total number of hash functions applied to each string
     * @return Number of hash functions
     */
    int getNumHashFunctions();


    /**
     * Returns the number of terms in the bloomfilter
     * @return Number of terms in the bloomfilter
     */
    int getNumTerms();


    /**
     * Returns a hash set of terms in the bloomfilter
     * @return Hash set of strings
     */
    HashSet<String> getTerms();


    /**
     * Clears the bloom filter of all strings
     */
    void clearBloomFilter();

}
