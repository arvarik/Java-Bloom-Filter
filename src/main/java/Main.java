import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import model.hflist.HashFunctionList;
import model.hflist.DefaultHashFunctionList;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

        HashFunction hf = Hashing.murmur3_32(2);

        HashCode hc = hf.newHasher()
                .putString("Arvind", Charsets.UTF_8)
                .hash();

        System.out.printf("Hash1 of 'Arvind' as int (SEED 2): %d\n\n", hc.asInt());


    }


}
