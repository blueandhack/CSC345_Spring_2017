public class AVLTree implements Proj04Dictionary {

	private AVLNode root;

	public AVLTree() {
		root = null;
	}

	public void insert(int key, String data) throws IllegalArgumentException {
		if (search(key) != null) {
			throw new IllegalArgumentException();
		}

	}

	public void delete(int key) throws IllegalArgumentException {
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}
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
		throw new RuntimeException("TODO");
	}

	public int getSuccessor(int key) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
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
