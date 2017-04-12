import java.util.ArrayList;

public class AVLTree implements Proj04Dictionary {

	private AVLNode root;
	private int count;

	public AVLTree() {
		root = null;
		count = 0;
	}

	public void insert(int key, String data) throws IllegalArgumentException {
		if (search(key) != null) {
			throw new IllegalArgumentException();
		}
		root = insert(root, key, data);
		count++;
	}

	private AVLNode insert(AVLNode subtree, int key, String data) {
		if (subtree == null) {
			subtree = new AVLNode(key, data);
		} else if (subtree.key > key) {
			subtree.left = insert(subtree.left, key, data);
		} else if (subtree.key < key) {
			subtree.right = insert(subtree.right, key, data);
		} else if (subtree.key == key) {
			throw new IllegalArgumentException();
		}
		heightHelper(subtree);
		if (subtree != null) {
			subtree = rebalance(subtree);
		}
		heightHelper(subtree);
		return subtree;
	}

	public void delete(int key) throws IllegalArgumentException {
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}
		root = delete(root, key);
		count--;
	}

	private AVLNode delete(AVLNode subtree, int key) {
		if (subtree == null) {
			return subtree;
		} else if (subtree.key > key) {
			// If data less than the node's data, then go to left
			subtree.left = delete(subtree.left, key);
		} else if (subtree.key < key) {
			// If data more than the node's data, then go to right
			subtree.right = delete(subtree.right, key);
		} else if (subtree.left != null && subtree.right != null) {
			// If the node has two child
			subtree.key = findMax(subtree.left).key;
			subtree.left = delete(subtree.left, subtree.key);
		} else {
			// If the node has one child or not
			if (subtree.left != null) {
				subtree = subtree.left;
			} else {
				subtree = subtree.right;
			}
		}
		heightHelper(subtree);
		if (subtree != null) {
			subtree = rebalance(subtree);
		}
		heightHelper(subtree);
		return subtree;
	}

	// The private method will find max node at the left subtree
	private AVLNode findMax(AVLNode node) {
		if (node == null) {
			return null;
		} else if (node.right == null) {
			return node;
		}
		return findMax(node.right);
	}

	public String search(int key) {
		return search(root, key);
	}

	private String search(AVLNode node, int key) {
		if (node == null) {
			return null;
		} else if (node.key == key) {
			return node.data;
		} else if (node.key < key) {
			return search(node.right, key);
		} else {
			return search(node.left, key);
		}
	}

	public Integer[] getKeys() {
		Integer[] array = new Integer[this.count];
		ArrayList<Integer> keys = new ArrayList<>();
		preOrder(root, keys);
		int i = 0;
		for (Integer integer : keys) {
			array[i] = integer;
			i++;
		}
		return array;
	}

	private void preOrder(AVLNode node, ArrayList<Integer> keys) {
		if (node == null) {
			return;
		} else if (node.left == null && node.right == null) {
			keys.add(node.key);
		}
		keys.add(node.key);
		preOrder(node.left, keys);
		preOrder(node.right, keys);
	}

	public int getSuccessor(int key) throws IllegalArgumentException {
		if (this.search(key) == null) {
			throw new IllegalArgumentException();
		}
		Integer[] keys = this.getKeys();
		boolean found = false;
		int successor = key;
		for (int j = 0; j < keys.length; ++j) {
			if (key < keys[j]) {
				if (!found) {
					successor = keys[j];
					found = true;
				} else
					successor = Math.min(successor, keys[j]);
			}
		}
		if (!found) {
			throw new IllegalArgumentException();
		}
		return successor;
	}

	private int heightHelper(AVLNode node) {
		if (node == null) {
			return 0;
		}
		node.height = Math.max(heightHelper(node.left), heightHelper(node.right));
		return node.height + 1;
	}

	private int getHeight(AVLNode subtree) {
		if (subtree == null) {
			return -1;
		} else {
			return subtree.height;
		}
	}

	private AVLNode rebalance(AVLNode node) {
		if (node.left == null && node.right == null) {
			// do nothing
		}
		// if there are more value in left, then rotate to the right.
		else if ((getHeight(node.left) - getHeight(node.right)) > 1) {
			if (getHeight(node.left.left) >= getHeight(node.left.right))
				node = rotateRight(node);
			else
				node = doubleRotateRight(node);
		}
		// if there are more value in right, then rotate to the left.
		else if ((getHeight(node.right) - getHeight(node.left)) > 1) {
			if (getHeight(node.right.right) >= getHeight(node.right.left))
				node = rotateLeft(node);
			else
				node = doubleRotateLeft(node);
		}
		return node;
	}

	private AVLNode rotateRight(AVLNode subtree) {
		AVLNode newRoot = subtree.left;
		subtree.left = newRoot.right;
		newRoot.right = subtree;
		return newRoot;
	}

	private AVLNode rotateLeft(AVLNode subtree) {
		AVLNode newRoot = subtree.right;
		subtree.right = newRoot.left;
		newRoot.left = subtree;
		return newRoot;
	}

	// double rotate left
	private AVLNode doubleRotateLeft(AVLNode node) {
		node.right = rotateRight(node.right);
		return rotateLeft(node);
	}

	// double rotate rights
	private AVLNode doubleRotateRight(AVLNode node) {
		node.left = rotateLeft(node.left);
		return rotateRight(node);
	}

	private class AVLNode {
		private AVLNode left, right;
		private int height;

		private int key;
		private String data;

		public AVLNode(int key, String data) {
			this.key = key;
			this.data = data;

			left = right = null;
			height = 0;
		}
	}
}
