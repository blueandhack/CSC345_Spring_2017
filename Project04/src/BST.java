/*
 * BST.java
 *
 * CSc 345 Spring 2017 - Project04
 * 
 * Author: Yujia Lin
 *
 * ---
 * The class is a data structure that is binary search tree
 */
public class BST implements Proj04Dictionary {

	// instance variables
	// the root is empty tree
	private TreeNode root;
	// count how many node we have
	private int count;

	// constructor
	public BST() {
		count = 0;
		root = null;
	}

	// insert node
	public void insert(int key, String data) throws IllegalArgumentException {
		if (data == null || search(key) != null) {
			throw new IllegalArgumentException();
		}
		root = insert(key, data, root);
		count++;
		// System.out.println("insert: " + count);
	}

	// the helper method help insert function
	private TreeNode insert(int key, String data, TreeNode node) {
		if (node == null) {
			node = new TreeNode(key, data);
		} else if (node.key > key) {
			node.left = insert(key, data, node.left);
		} else if (node.key < key) {
			node.right = insert(key, data, node.right);
		}
		return node;
	}

	// delete node by key
	public void delete(int key) throws IllegalArgumentException {
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}
		root = delete(key, root);
		count--;
	}

	// delete helper method
	private TreeNode delete(int key, TreeNode node) {
		if (node == null) {
			return node;
		} else if (node.key > key) {
			// If data less than the node's data, then go to left
			node.left = delete(key, node.left);
		} else if (node.key < key) {
			// If data more than the node's data, then go to right
			node.right = delete(key, node.right);
		} else if (node.left != null && node.right != null) {
			// If the node has two child
			node.key = findMax(node.left).key;
			node.data = findMax(node.left).data;
			node.left = delete(node.key, node.left);
		} else {
			// If the node has one child or not
			if (node.left != null) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return node;
	}

	// find max node at the subtree
	private TreeNode findMax(TreeNode node) {
		if (node == null) {
			return null;
		} else if (node.right == null) {
			return node;
		}
		return findMax(node.right);
	}

	// search node
	public String search(int key) {
		return search(key, root);
	}

	// search helper method
	private String search(int key, TreeNode node) {
		if (node == null) {
			return null;
		} else if (node.key == key) {
			return node.data;
		} else if (node.key < key) {
			return search(key, node.right);
		} else {
			return search(key, node.left);
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
	private String preOrder(TreeNode node) {
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

	// The class is tree node
	private class TreeNode {
		private int key;
		private String data;

		private TreeNode left;
		private TreeNode right;

		// constructor, create a node but it doesn't have left and right
		public TreeNode(int key, String data) {
			this.key = key;
			this.data = data;
			this.left = null;
			this.right = null;
		}

	}

}
