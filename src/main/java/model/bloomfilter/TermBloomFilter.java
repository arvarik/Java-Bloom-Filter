package model.bloomfilter;

import java.util.HashSet;
import java.util.Set;

/**
 * Exactly like the default bloomfilter except the TermBloomFilter keeps
 * a set of the terms for displaying how a bloomfilter works
 */
public class TermBloomFilter extends DefaultBloomFilter implements BloomFilter {

    private Set<String> termSet;

    /**
     * Default constructor for bloomfilter with size 1400 and 10 hash functions
     * This is the optimal bloom filter for 1/1000 desired false positive ratio and
     * expecting around 100 terms to be in the set
     */
    public TermBloomFilter() {
        super();
        this.termSet = new HashSet<>();
    }


    /**
     * Main constructor where user can construct bloomfilter with desired bf size and num hfs
     * @param bloomFilterSize Size of bloomfilter in bits
     * @param numHashFunctions Number of hash functions each term should run through
     */
    public TermBloomFilter(int bloomFilterSize, int numHashFunctions) {
        super(bloomFilterSize, numHashFunctions);
        this.termSet = new HashSet<>();
    }


    /**
     * Adds a term to the bloom filter
     *
     * @param key Term to add into the bloomfilter
     */
    @Override
    public void addTerm(String key) {
        if (key == null) { return; }
        super.addTerm(key);
        termSet.add(key);
    }


    /**
     * Returns a hash set of terms in the bloomfilter
     *
     * @return Hash set of strings
     */
    public Set<String> getTerms() {
        return this.termSet;
    }


    /**
     * Clears the bloom filter of all strings
     */
    @Override
    public void clearBloomFilter() {
        super.clearBloomFilter();
        this.termSet.clear();
    }
}
