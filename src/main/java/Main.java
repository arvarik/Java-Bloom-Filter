import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import model.bloomfilter.BloomFilter;
import model.bloomfilter.DefaultBloomFilter;


public class Main {


    public static void main(String[] args) {

        // Testing out Google Guavas hashing capabilities
        HashFunction hf = Hashing.murmur3_32(2);

        HashCode hc = hf.newHasher()
                .putString("Arvind", Charsets.UTF_8)
                .hash();

        System.out.printf("Hash1 of 'Arvind' as int (SEED 2): %d\n\n", hc.asInt());


        // Testing out if BloomFilter constructors are working
        BloomFilter bloomFilter = new DefaultBloomFilter(256, 3);

        System.out.println(bloomFilter.getNumTerms());

    }


}
