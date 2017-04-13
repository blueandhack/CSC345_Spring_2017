
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
		hs.insert(7, "a");
		hs.insert(8, "b");
		hs.insert(9, "c");
		hs.insert(10, "d");
		hs.insert(11, "e");
		hs.insert(-188888, "f");
		assertEquals(3, hs.getSuccessor(2));
		assertEquals(4, hs.getSuccessor(3));
		// for (int i = 0; i < hs.getKeys().length; i++) {
		// System.out.println(hs.getKeys()[i]);
		// }
		assertEquals("c", hs.search(3));
		assertEquals("f", hs.search(-188888));
		assertEquals("e", hs.search(11));
		hs.getKeys();
	}

}
