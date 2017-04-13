
import static org.junit.Assert.*;

import org.junit.Test;

public class BSTTest {

	@Test
	public void test() {
		BST bst = new BST();
		bst.insert(1, "a");
		bst.insert(2, "b");
		bst.insert(3, "c");

		// System.out.println(bst.getKeys().length);

		for (int i = 0; i < bst.getKeys().length; i++) {
			assertTrue(i + 1 == bst.getKeys()[i]);
		}

	}

}
