package model.bloomfilter;

import java.math.RoundingMode;
import java.util.List;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;
import java.util.HashMap;

import lombok.NonNull;
import model.hflist.DefaultHashFunctionList;
import model.hflist.HashFunctionList;
import model.hflist.HashType;

import com.google.common.math.DoubleMath;
import model.hflist.MurmurHashFunctionList;


public class DefaultBloomFilter implements BloomFilter {

    private int bloomFilterSize;    // Referred to in stats as m
    private int numHashFunctions;   // Referred to in stats as k
    private int numTerms;           // Referred to in stats as n

    private BitSet bitVector;       // Bit vector of bloomfilter

    @NonNull
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
    }


    /**
     * Main constructor where user can construct bloomfilter with desired bf size and num hfs
     * Uses the default hash function list
     * @param bloomFilterSize Size of bloomfilter in bits
     * @param numHashFunctions Number of hash functions each term should run through
     */
    public DefaultBloomFilter(int bloomFilterSize, int numHashFunctions) {
        if (bloomFilterSize < 5) {
            throw new IllegalArgumentException("BloomFilter size must be 5 or greater");
        }

        if (numHashFunctions < 1) {
            throw new IllegalArgumentException("There must be at least one hash function");
        }

        this.bloomFilterSize = bloomFilterSize;
        this.numHashFunctions = numHashFunctions;

        this.bitVector = new BitSet(bloomFilterSize);
        this.hashFunctionList = new DefaultHashFunctionList(numHashFunctions);
    }


    /**
     * Default constructor for bloomfilter with size 1400 and 10 hash functions
     * This is the optimal bloom filter for 1/1000 desired false positive ratio and
     * expecting around 100 terms to be in the set
     */
    public DefaultBloomFilter(HashType hashType) {
        this.bloomFilterSize = 1400;
        this.numHashFunctions = 10;
        this.numTerms = 0;
        this.bitVector = new BitSet(1400);

        setHashFunctionList(hashType);
    }


    /**
     * Constructor where user can construct bloomfilter with desired bf size, num hfs, and hfl type
     * @param bloomFilterSize Size of bloomfilter in bits
     * @param numHashFunctions Number of hash functions each term should run through
     * @param hashType The type of hash to use. Choices now are DEFAULT or MURMUR
     */
    public DefaultBloomFilter(int bloomFilterSize, int numHashFunctions, HashType hashType) {
        if (bloomFilterSize < 5) {
            throw new IllegalArgumentException("BloomFilter size must be 5 or greater");
        }

        if (numHashFunctions < 1) {
            throw new IllegalArgumentException("There must be at least one hash function");
        }

        this.bloomFilterSize = bloomFilterSize;
        this.numHashFunctions = numHashFunctions;
        this.bitVector = new BitSet(bloomFilterSize);

        setHashFunctionList(numHashFunctions, hashType);
    }

    private void setHashFunctionList(HashType hashType) {
        switch (hashType) {
            case DEFAULT:
                this.hashFunctionList = new DefaultHashFunctionList();
            case MURMUR:
                this.hashFunctionList = new MurmurHashFunctionList();
            default:
                throw new IllegalArgumentException("HashType not valid");
        }
    }

    private void setHashFunctionList(int numHashFunctions, HashType hashType) {
        switch (hashType) {
            case DEFAULT:
                this.hashFunctionList = new DefaultHashFunctionList(numHashFunctions);
            case MURMUR:
                this.hashFunctionList = new DefaultHashFunctionList(numHashFunctions);
                // Actual code is below
                // this.hashFunctionList = new MurmurHashFunctionList(numHashFunctions);
            default:
                throw new IllegalArgumentException("HashType not valid");
        }
    }


    /**
     * Method to help user construct an optimal bloomfilter
     *
     * @param expectedNumTerms     Expected number of terms user's bloomfilter will contain
     * @param desiredFalsePositive Desired false positive ratio r where 0 < r < 1
     * @return A list of size two with optimal bloomfilter size and number of hash functions
     */
    public List<Integer> getOptimalSizeAndNumHfs(int expectedNumTerms, double desiredFalsePositive) {
        if (expectedNumTerms < 5) {
            throw new IllegalArgumentException("Expected number of terms must be 5 or greater");
        }

        if (desiredFalsePositive <= 0 || desiredFalsePositive >= 1) {
            throw new IllegalArgumentException("False positive rate R must be 0 < R < 1.");
        }

        List<Integer> optimalValues = new ArrayList<>();

        double optimalHashFunctions = -1 * DoubleMath.log2(desiredFalsePositive, RoundingMode.FLOOR);
        double optimalBFSize = 1.44 * optimalHashFunctions * expectedNumTerms;

        optimalValues.add((int) optimalBFSize);
        optimalValues.add((int) optimalHashFunctions);

        return optimalValues;
    }

    /**
     * Checks the bloom filter to see status of key
     *
     * @param key Term to check in the bloomfilter
     * @return Response if key was probably in the set or definitely not in the set
     */
    public boolean inTheSet(String key) {
        if (key == null) { return false; }

        List<Integer> bitIndices = getBitIndices(key);

        boolean inTheSet = true;

        for (int index : bitIndices) {
            if (!bitVector.get(index)) { inTheSet = false; }
        }

        return inTheSet;
    }

    /**
     * Checks the bloom filter to see status of key
     * @param key Term to check in the bloomfilter
     * @return String response if key was probably in the set or definitely not in the set
     */
    public String checkTerm(String key) {
        if (inTheSet(key)) {
            return "The term '" + key + "' is probably in the set.";
        } else {
            return "The term '" + key + "' is definitely not in the set, you can add it in.";
        }
    }


    /**
     * Adds a term to the bloom filter
     *
     * @param key Term to add into the bloomfilter
     */
    public void addTerm(String key) {
        if (key == null) { return; }

        List<Integer> bitIndices = getBitIndices(key);

        for (int index : bitIndices) {
            bitVector.set(index);
        }

        numTerms++;
    }


    /**
     * Returns all the hashed outputs of term
     *
     * @param key Term to check in bloomfilter
     * @return List of bit indices corresponding to term
     */
    public List<Integer> getBitIndices(String key) {
        if (key == null) {
            throw new IllegalArgumentException("String cannot be null");
        }
        return this.hashFunctionList.getBoundedIntHashListFromString(key, this.bloomFilterSize);
    }


    /** !@#$%^&*()
     * Gets a few statistics of the current bloomfilter
     *
     * @return Hash map of statistic name and its associated value
     */
    public Map<String, String> getBloomFilterStats() {
        // TODO: Write list of good stats and implement
        Map<String, String> stats = new HashMap<>();


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
     * Clears the bloom filter of all strings
     */
    public void clearBloomFilter() {
        this.numTerms = 0;
        this.bitVector.clear();
    }
}
