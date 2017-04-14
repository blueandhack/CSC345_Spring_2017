/*
 * AVLTree.java
 *
 * CSc 345 Spring 2017 - Project04
 * 
 * Author: Yujia Lin
 *
 * ---
 * The class is a data structure that is AVL tree
 */

public class AVLTree implements Proj04Dictionary {

	// instance variables
	// root is empty tree
	private AVLNode root;
	// count how many node in the tree
	private int count;

	// constructor
	public AVLTree() {
		root = null;
		count = 0;
	}

	// insert node
	public void insert(int key, String data) throws IllegalArgumentException {
		if (data == null || search(key) != null) {
			throw new IllegalArgumentException();
		}
		root = insert(root, key, data);
		count++;
	}

	// help method for insert node
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

	// delete node by key
	public void delete(int key) throws IllegalArgumentException {
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}
		root = delete(root, key);
		count--;
	}

	// helper method for delete
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
			subtree.data = findMax(subtree.left).data;
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

	// search node by key
	public String search(int key) {
		return search(root, key);
	}

	// helper method for search
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

	// get keys
	public Integer[] getKeys() {
		Integer[] array = new Integer[this.count];
		if (count != 0) {
			String[] keys = preOrder(root).split("\\s+");
			int i = 0;
			for (String integer : keys) {
				array[i] = Integer.parseInt(integer);
				i++;
			}
		}
		return array;
	}

	// pre-order to get a string
	private String preOrder(AVLNode node) {
		if (node == null) {
			return "";
		}
		return node.key + " " + preOrder(node.left) + preOrder(node.right);
	}

	// get successor
	public int getSuccessor(int key) throws IllegalArgumentException {
		if (this.search(key) == null) {
			throw new IllegalArgumentException();
		}
		Integer[] keys = this.getKeys();
		int best = Integer.MAX_VALUE;
		boolean found = false;

		for (Integer i : keys) {
			if (i <= key)
				continue;

			found = true;
			if (i < best)
				best = i;
		}

		if (found == false)
			throw new IllegalArgumentException();

		return best;
	}

	// set height
	private int heightHelper(AVLNode node) {
		if (node == null) {
			return 0;
		}
		node.height = Math.max(heightHelper(node.left), heightHelper(node.right));
		return node.height + 1;
	}

	// get height
	private int getHeight(AVLNode subtree) {
		if (subtree == null) {
			return -1;
		} else {
			return subtree.height;
		}
	}

	// re balance the tree
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

	// rotate right
	private AVLNode rotateRight(AVLNode subtree) {
		AVLNode newRoot = subtree.left;
		subtree.left = newRoot.right;
		newRoot.right = subtree;
		return newRoot;
	}

	// rotate left
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

	// the private class is AVL tree node
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
