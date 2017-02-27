import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BinarySearchTree {

	private TreeNode root;

	private int count;

	public BinarySearchTree() {
		this.count = 0;
		this.root = null;
	}

	public boolean isEmpty() {
		if (root == null) {
			return true;
		} else {
			return false;
		}
	}

	public TreeNode getRoot() {
		return this.root;
	}

	public boolean contians(int data) {
		return contians(data, root);
	}

	private boolean contians(int data, TreeNode node) {
		if (node == null) {
			return false;
		} else if (node.getData() == data) {
			return true;
		} else if (node.getData() < data) {
			return contians(data, node.rightChild);
		} else {
			return contians(data, node.leftChild);
		}
	}

	public void insertTreeNode(int data) {
		root = insertTreeNodeHelper(data, root);
	}

	private TreeNode insertTreeNodeHelper(int data, TreeNode node) {
		if (node == null) {
			node = new TreeNode(data);
			this.count++;
		} else if (node.getData() > data) {
			node.leftChild = insertTreeNodeHelper(data, node.leftChild);
		} else if (node.getData() < data) {
			node.rightChild = insertTreeNodeHelper(data, node.rightChild);
		}
		return node;
	}

	public void delete(int data) {
		root = delete(data, root);
		this.count--;
	}

	private TreeNode delete(int data, TreeNode node) {
		if (node == null) {
			return node;
		} else if (node.getData() > data) {
			node.leftChild = delete(data, node.leftChild);
		} else if (node.getData() < data) {
			node.rightChild = delete(data, node.rightChild);
		} else if (node.leftChild != null && node.rightChild != null) {
			node.setData(findMin(node.leftChild).getData());
			node.leftChild = delete(node.data, node.leftChild);
		} else {
			// node = (node.leftChild != null) ? node.leftChild :
			// node.rightChild;
			if (node.leftChild != null) {
				node = node.leftChild;
			} else {
				node = node.rightChild;
			}
		}
		return node;
	}

	private TreeNode findMin(TreeNode node) {
		if (node == null) {
			return null;
		} else if (node.leftChild == null) {
			return node;
		}
		return findMin(node.leftChild);
	}

	public int count() {
		return this.count;
	}

	public String preOrder() {
		return preOrder(root);
	}

	private String preOrder(TreeNode node) {
		if (node == null) {
			return "";
		}
		return node.data + " " + preOrder(node.leftChild) + preOrder(node.rightChild);
	}

	public String inOrder() {
		return inOrder(root);
	}

	private String inOrder(TreeNode node) {
		if (node == null) {
			return "";
		} else if (node.isLeaf()) {
			return "" + node.getData() + " ";
		}
		return inOrder(node.leftChild) + node.data + " " + inOrder(node.rightChild);
	}

	public void debug(String filename) {

		String newFilename = "" + filename;

		String content = "digraph {\n";

		String str = "";

		if (root != null) {
			str = "  DUMMY [style=invis];\n  DUMMY -> " + root.getData() + ";\n  " + root.getData()
					+ " [penwidth=2];\n\n";
			str += debug();
		}

		// System.out.println(str);

		content += str + "}";

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(newFilename));
			out.write(content);
			out.close();
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("can not open file");
		}

	}

	private String debug() {
		return debug(root);
	}

	private String debug(TreeNode node) {
		String left = "\n";

		String right = "\n";
		if (node == null) {
			return "";
		}

		if (node.leftChild != null) {
			left = "  " + node.getData() + " -> " + node.leftChild.getData() + " [label=\"L\"];\n";
		}

		if (node.leftChild == null && node.rightChild != null) {
			left = "  " + node.getData() + " -> L_" + node.getData() + " [style=invis];\n  L_" + node.getData()
					+ " [style=invis];\n\n";
		}

		if (node.rightChild != null) {
			right = "  " + node.getData() + " -> " + node.rightChild.getData() + " [label=\"R\"];\n";
		}

		if (node.rightChild == null && node.leftChild != null) {
			right = "  " + node.getData() + " -> R_" + node.getData() + " [style=invis];\n  R_" + node.getData()
					+ " [style=invis];\n\n";
		}

		String str = "  " + node.data + ";\n\n" + left + debug(node.leftChild) + right + debug(node.rightChild);

		return str;
	}

	private class TreeNode {
		private int data;

		private TreeNode leftChild;
		private TreeNode rightChild;

		public TreeNode() {
			this.leftChild = null;
			this.rightChild = null;
		}

		public TreeNode(int data) {
			this.data = data;
			this.leftChild = null;
			this.rightChild = null;
		}

		public TreeNode(int data, TreeNode leftChild, TreeNode rightChild) {
			this.data = data;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public TreeNode getLeftChild() {
			return leftChild;
		}

		public void setLeftChild(TreeNode leftChild) {
			this.leftChild = leftChild;
		}

		public TreeNode getRightChild() {
			return rightChild;
		}

		public void setRightChild(TreeNode rightChild) {
			this.rightChild = rightChild;
		}

		public boolean isLeaf() {
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}

	}

}
