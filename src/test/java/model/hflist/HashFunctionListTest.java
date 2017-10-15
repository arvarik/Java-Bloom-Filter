package model.hflist;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class HashFunctionListTest {

    private HashFunctionList hashFunctionList;
    private HashFunctionList defaultHashFunctionList;

    @Before
    public void beforeTestSetup() {
        hashFunctionList = new DefaultHashFunctionList(5);
        defaultHashFunctionList = new DefaultHashFunctionList();
    }

    @Test
    public void testHashFunctionListConstructor_getMethods() {
        assertEquals(2, defaultHashFunctionList.getNumHashes());
        assertEquals(5, hashFunctionList.getNumHashes());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_whenIllegalSize_throwIllegalArg() {
        new DefaultHashFunctionList(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHashFunctionList_whenIllegalKeyGetIntHashList_throwIllegalArg() {
        hashFunctionList.getIntHashListFromString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHashFunctionList_whenIllegalKeyGetBoundedIntHashList_throwIllegalArg() {
        hashFunctionList.getBoundedIntHashListFromString(null, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHashFunctionList_whenIllegalBoundGetBoundedIntHashList_throwIllegalArg() {
        hashFunctionList.getBoundedIntHashListFromString("Test", 4);
    }
}
