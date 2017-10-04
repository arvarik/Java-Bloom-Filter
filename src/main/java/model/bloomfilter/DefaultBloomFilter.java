package model.bloomfilter;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.BitSet;

import model.hflist.DefaultHashFunctionList;
import model.hflist.HashFunctionList;

import com.google.common.math.DoubleMath;


public class DefaultBloomFilter implements BloomFilter {

    private int bloomFilterSize;    // Referred to in stats as m
    private int numHashFunctions;   // Referred to in stats as k
    private int numTerms;           // Referred to in stats as n

    private BitSet bitVector;       // Bit vector of bloomfilter
    private HashSet termSet;        // Strictly for purposes of this project,
                                    // actual bloomfilter does not keep terms

    private HashFunctionList hashFunctionList;  // List of hash functions


    /**
     * Default constructor for bloomfilter with size 1400 and 10 hash functions
     * This is the optimal bloom filter for 1/1000 desired false positive ratio and
     * expecting around 100 terms to be in the set
     */
    public DefaultBloomFilter() {
        this.bloomFilterSize = 1400;
        this.numHashFunctions = 10;
        this.numTerms = 0;

        this.bitVector = new BitSet(1400);
        this.hashFunctionList = new DefaultHashFunctionList();
        this.termSet = new HashSet();
    }


    /**
     * Main constructor where user can construct bloomfilter with desired bf size and num hfs
     * @param bloomFilterSize Size of bloomfilter in bits
     * @param numHashFunctions Number of hash functions each term should run through
     */
    public DefaultBloomFilter(int bloomFilterSize, int numHashFunctions) {
        this.bloomFilterSize = bloomFilterSize;
        this.numHashFunctions = numHashFunctions;

        this.bitVector = new BitSet(bloomFilterSize);
        this.hashFunctionList = new DefaultHashFunctionList(numHashFunctions);
        this.termSet = new HashSet();
    }


    /**
     * Method to help user construct an optimal bloomfilter
     *
     * @param expectedNumTerms     Expected number of terms user's bloomfilter will contain
     * @param desiredFalsePositive Desired false positive ratio r where 0 < r < 1
     * @return A list of size two with optimal bloomfilter size and number of hash functions
     */
    public ArrayList<Integer> getOptimalSizeAndNumHfs(int expectedNumTerms, double desiredFalsePositive) {

        if (desiredFalsePositive <= 0 || desiredFalsePositive >= 1) {
            throw new ArithmeticException("False positive rate R must be 0 < R < 1.");
        }

        ArrayList optimalValues = new ArrayList();

        double optimalHashFunctions = -1 * DoubleMath.log2(desiredFalsePositive, RoundingMode.FLOOR);
        double optimalBFSize = 1.44 * optimalHashFunctions * expectedNumTerms;

        optimalValues.add(optimalBFSize);
        optimalValues.add(optimalHashFunctions);

        return optimalValues;
    }

    /**
     * Checks the bloom filter to see status of key
     *
     * @param key Term to check in the bloomfilter
     * @return Response if key was probably in the set or definitely not in the set
     */
    public String checkTerm(String key) {

        ArrayList<Integer> bitIndices = getBitIndices(key);

        boolean inTheSet = true;

        for (int index : bitIndices) {
            if (!bitVector.get(index)) { inTheSet = false; }
        }

        if (inTheSet) {
            return "The term '" + key + "' is probably in the set.";
        }

        return "The term '" + key + "' is definitely not in the set, you can add it in.";

        /** Change return to something objective like boolean or int */
    }


    /**
     * Adds a term to the bloom filter
     *
     * @param key Term to add into the bloomfilter
     */
    public void addTerm(String key) {

        ArrayList<Integer> bitIndices = getBitIndices(key);

        for (int index : bitIndices) {
            this.bitVector.set(index);
        }

        termSet.add(key);

        numTerms++;
    }


    /**
     * Returns all the hashed outputs of term
     *
     * @param key Term to check in bloomfilter
     * @return List of bit indices corresponding to term
     */
    public ArrayList<Integer> getBitIndices(String key) {
        return this.hashFunctionList.getBoundedIntHashListFromString(key, this.bloomFilterSize);
    }


    /** !@#$%^&*()
     * Gets a few statistics of the current bloomfilter
     *
     * @return Hash map of statistic name and its associated value
     */
    public HashMap<String, String> getBloomFilterStats() {
        //TODO Write list of good stats and implement
        HashMap<String, String> stats = new HashMap<>();


        return stats;
    }


    /**
     * Returns the total in-memory storage of bloomfilter in bits
     *
     * @return Size of bloom filter
     */
    public int getBloomFilterSize() {
        return bloomFilterSize;
    }


    /**
     * Returns total number of hash functions applied to each string
     *
     * @return Number of hash functions
     */
    public int getNumHashFunctions() { return numHashFunctions; }


    /**
     * Returns the number of terms in the bloomfilter
     *
     * @return Number of terms in the hash set
     */
    public int getNumTerms() {
        return this.numTerms;
    }


    /**
     * Returns a hash set of terms in the bloomfilter
     *
     * @return Hash set of strings
     */
    public HashSet<String> getTerms() {
        return this.termSet;
    }


    // TODO Get termSet? Also print out term set with another helper method for printing out sets in testing


    /**
     * Clears the bloom filter of all strings
     */
    public void clearBloomFilter() {
        this.numTerms = 0;
        this.bitVector.clear();
        this.termSet.clear();
    }

}
