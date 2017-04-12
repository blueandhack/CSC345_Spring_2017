import static org.junit.Assert.*;

import org.junit.Test;

public class HashTable_TestCase {

	@Test
	public void test() {
		HashTable hs = new HashTable();
		hs.insert(1, "a");
		hs.insert(2, "b");
		hs.insert(3, "c");
		hs.insert(4, "d");
		assertEquals(3, hs.getSuccessor(2));
		hs.delete(1);
		hs.delete(2);
		hs.delete(3);
		hs.delete(4);

	}

}
