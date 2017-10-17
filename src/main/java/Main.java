import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;


public class Main {

    public static void main(String[] args) {

        // TODO: Use RandomStringUtils from Apache commons and java.lang.instrumentation for size testing
        // TODO: Do a performance test here that tests speed of containment compared to an ArrayList
        // TODO: Do a size test here that shows the disparity in size between BF and HashMap


        // Testing out Google Guavas hashing capabilities
        HashFunction hf = Hashing.murmur3_32(2);

        HashCode hc = hf.newHasher()
                .putString("Arvind", Charsets.UTF_8)
                .hash();

        System.out.printf("Hash1 of 'Arvind' as int (SEED 2): %d\n\n", hc.asInt());
    }

}
