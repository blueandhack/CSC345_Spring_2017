public class BST implements Proj04Dictionary {

	private TreeNode root;
	private int count;

	public BST() {
		count = 0;
		root = null;
	}

	public void insert(int key, String data) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
	}

	public void delete(int key) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
	}

	public String search(int key) {
		return search(key, root);
	}

	private String search(int key, TreeNode node) {
		if (node == null) {
			return null;
		} else if (node.key == key) {
			return node.data;
		} else if (node.key < key) {
			return search(key, node.rightChild);
		} else {
			return search(key, node.leftChild);
		}

	}

	public Integer[] getKeys() {
		throw new RuntimeException("TODO");
	}

	public int getSuccessor(int key) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
	}

	// The class is tree node
	private class TreeNode {
		private int key;
		private String data;

		private TreeNode leftChild;
		private TreeNode rightChild;

		// Constructor, create a node but it doesn't have left and right
		public TreeNode(int key) {
			this.key = key;
			this.leftChild = null;
			this.rightChild = null;
		}

		// To get key
		public int getKey() {
			return key;
		}

		// Set key
		public void setKey(int key) {
			this.key = key;
		}

		// Check the node is it a leaf by no left and right child
		public boolean isLeaf() {
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}

	}

}
