package model.hflist;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;


public class DefaultHashFunctionList implements HashFunctionList {

    @NonNull
    private List<HashFunction> hashFunctionList;

    /**
     * Default constructor for HashFunctionList with size set to 5
     */
    public DefaultHashFunctionList() {
        this.hashFunctionList = new ArrayList<>();
        setHashFunctionList(6);
    }

    /**
     * Constructor for HashFunctionList with size parameter
     * @param size Number of hash functions to be used
     */
    public DefaultHashFunctionList(int size) {
        this.hashFunctionList = new ArrayList<>();
        setHashFunctionList(size);
    }


    private void setHashFunctionList(int size) {
        if (size < 1 || size > 6) {
            throw new IllegalArgumentException("Number of hash functions should be in range [1 - 6]");
        }
        hashFunctionList.add(Hashing.adler32());
        hashFunctionList.add(Hashing.crc32());
        hashFunctionList.add(Hashing.farmHashFingerprint64());
        hashFunctionList.add(Hashing.murmur3_32());
        hashFunctionList.add(Hashing.sha256());
        hashFunctionList.add(Hashing.sipHash24());
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
        setHashFunctionList(length);
    }

    /**
     * Returns an int list of hashes generated from given string
     *
     * @param key Term to get hashes from
     * @return List of int hashes
     */
    public ArrayList<Integer> getIntHashListFromString(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        ArrayList<Integer> intHashList = new ArrayList<>();

        for (HashFunction hf : hashFunctionList) {
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
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        if (bound < 5) {
            throw new IllegalArgumentException("Bound must be 5 or greater");
        }

        ArrayList<Integer> intHashList = new ArrayList<>();

        for (HashFunction hf : hashFunctionList) {
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
