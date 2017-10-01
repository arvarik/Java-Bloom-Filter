package model.bloomfilter;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;


class DefaultBloomFilter implements BloomFilter {

    int bloomFilterSize;    // Referred to in stats as m
    int numTerms;           // Referred to in stats as n
    int numHashFunctions;   // Referred to in stats as k

    BitSet bitVector;       // Bit vector of bloomfilter




    DefaultBloomFilter() {

    }


    /**
     * Checks the bloom filter to see status of key
     *
     * @param key Term to check in the bloomfilter
     * @return Response if key was probably in the set or definitely not in the set
     */
    public String checkTerm(String key) {
        return null;
    }


    /**
     * Adds a term to the bloom filter
     *
     * @param key Term to add into the bloomfilter
     */
    public void addTerm(String key) {

    }


    /**
     * Returns all the hashed outputs of term
     *
     * @param key Term to check in bloomfilter
     * @return List of bit indices corresponding to term
     */
    public ArrayList<Integer> getBitIndices(String key) {
        return null;
    }


    /**
     * Gets a few statistics of the current bloomfilter
     *
     * @return Hash map of statistic name and its associated value
     */
    public HashMap<String, String> getBloomFilterStats() {
        return null;
    }


    /**
     * Returns the total in-memory storage of bloomfilter in bits
     *
     * @return Size of bloom filter
     */
    public int getBloomFilterSize() {
        return 0;
    }


    /**
     * Returns total number of hash functions applied to each string
     *
     * @return Number of hash functions
     */
    public int getNumHashFunctions() {
        return 0;
    }


    /**
     * Returns the number of terms in the bloomfilter
     *
     * @return
     */
    public int getNumTerms() {
        return 0;
    }


    /**
     * Returns a hash set of terms in the bloomfilter
     *
     * @return Hash set of strings
     */
    public HashSet<String> getTerms() {
        return null;
    }


    /**
     * Clears the bloom filter of all strings
     */
    public void clearBloomFilter() {

    }

}
