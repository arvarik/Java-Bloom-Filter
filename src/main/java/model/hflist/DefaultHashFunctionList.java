package model.hflist;

import java.util.ArrayList;
import java.util.Random;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;


public class DefaultHashFunctionList implements HashFunctionList {

    private ArrayList<HashFunction> hashFunctionList;

    /**
     * Default constructor for HashFunctionList with size set to 2
     */
    public DefaultHashFunctionList() {
        this.hashFunctionList = new ArrayList<>();
        setHashFunctionList(this.hashFunctionList, 2);
    }

    /**
     * Constructor for HashFunctionList with size parameter
     * @param size
     */
    public DefaultHashFunctionList(int size) {
        this.hashFunctionList = new ArrayList<>();
        setHashFunctionList(this.hashFunctionList, size);
    }


    /**
     * Function to instantiate all HashFunction objects in list
     * @param hashFunctionList Empty HashFunction List
     * @param size Number of hash functions to add to the list
     */
    private void setHashFunctionList(ArrayList<HashFunction> hashFunctionList, int size) {
        for (int i = 0; i < size; i++) {
            Random rn = new Random();
            hashFunctionList.add(Hashing.murmur3_32(rn.nextInt()));
        }
    }


    /**
     * Get number of hash functions in the list
     *
     * @return Size of hash function list
     */
    public int getNumHashes() {
        return hashFunctionList.size();
    }


    /**
     * Override current HashFunctionList instance with a fresh one
     *
     * @param length New HashFunctionList instance length
     */
    public void setNewHashFunctionList(int length) {
        this.hashFunctionList = new ArrayList<>();
        setHashFunctionList(this.hashFunctionList, length);
    }


    /**
     * Returns an int list of hashes generated from given string
     *
     * @param key Term to get hashes from
     * @return List of int hashes
     */
    public ArrayList<Integer> getIntHashListFromString(String key) {

        ArrayList<Integer> intHashList = new ArrayList<>();

        for (HashFunction hf : this.hashFunctionList) {
            intHashList.add(hf.newHasher()
                              .putString(key, Charsets.UTF_8)
                              .hash()
                              .asInt());
        }

        return intHashList;
    }


    /**
     * Returns an int list of bounded hashes generated from given string
     *
     * @param key Term to get hashes from
     * @return List of bounded int hashes
     */
    public ArrayList<Integer> getBoundedIntHashListFromString(String key, int bound) {

        ArrayList<Integer> intHashList = new ArrayList<>();

        for (HashFunction hf : this.hashFunctionList) {
            int hash = hf.newHasher()
                         .putString(key, Charsets.UTF_8)
                         .hash()
                         .asInt();
            hash = (hash < 0) ? -1 * hash % bound : hash % bound;
            intHashList.add(hash);
        }

        return intHashList;
    }
}
