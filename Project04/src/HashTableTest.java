
import static org.junit.Assert.*;

import org.junit.Test;

public class HashTableTest {

	@Test
	public void test() {
		HashTable hs = new HashTable();
		hs.insert(1, "a");
		hs.insert(2, "b");
		hs.insert(3, "c");
		hs.insert(4, "d");
		hs.insert(5, "e");
		hs.insert(6, "f");
		assertEquals(3, hs.getSuccessor(2));
		assertEquals(4, hs.getSuccessor(3));

	}

}
