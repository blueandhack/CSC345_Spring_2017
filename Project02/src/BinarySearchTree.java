
/*
 * BinarySearchTree.java
 *
 * CSc 345 Spring 2017 - Project02
 * 
 * Author: Yujia Lin
 *
 * ---
 * The file is class BinarySearchTree, and it contains a private class TreeNode. 
 * So we can create object that binary search tree by the class.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BinarySearchTree {

	private TreeNode root;

	private int count;

	// Constructor
	public BinarySearchTree() {
		this.count = 0;
		this.root = null;
	}

	// Check the tree if it is empty, then return true
	public boolean isEmpty() {
		if (root == null) {
			return true;
		} else {
			return false;
		}
	}

	// Get BST root
	public TreeNode getRoot() {
		return this.root;
	}

	// Check a node in the BST
	public boolean contains(int data) {
		return contains(data, root);
	}

	// Helper method for contains method
	private boolean contains(int data, TreeNode node) {
		if (node == null) {
			return false;
		} else if (node.getData() == data) {
			return true;
		} else if (node.getData() < data) {
			return contains(data, node.rightChild);
		} else {
			return contains(data, node.leftChild);
		}
	}

	// Insert a value to BST
	public void insertTreeNode(int data) {
		root = insertTreeNodeHelper(data, root);
	}

	// Helper method for insert method
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

	// Delete a value from BST
	public void delete(int data) {
		root = delete(data, root);
		this.count--;
	}

	// Helper method for delete method
	private TreeNode delete(int data, TreeNode node) {
		if (node == null) {
			return node;
		} else if (node.getData() > data) {
			// If data less than the node's data, then go to left
			node.leftChild = delete(data, node.leftChild);
		} else if (node.getData() < data) {
			// If data more than the node's data, then go to right
			node.rightChild = delete(data, node.rightChild);
		} else if (node.leftChild != null && node.rightChild != null) {
			// If the node has two child
			node.setData(findMax(node.leftChild).getData());
			node.leftChild = delete(node.data, node.leftChild);
		} else {
			// If the node has one child or not
			if (node.leftChild != null) {
				node = node.leftChild;
			} else {
				node = node.rightChild;
			}
		}
		return node;
	}

	// Helper method for delete method, it can find a max node and return the
	// node
	private TreeNode findMax(TreeNode node) {
		if (node == null) {
			return null;
		} else if (node.rightChild == null) {
			return node;
		}
		return findMax(node.rightChild);
	}

	// Return how many nodes in the BST
	public int count() {
		return this.count;
	}

	// Return a string that is pre order result
	public String preOrder() {
		return preOrder(root);
	}

	// Return a string that is order result
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

		content += str + "}";

		// Writer file
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(newFilename));
			out.write(content);
			out.close();
		} catch (IOException e) {
			System.out.println("Could not open the input file: No such file or directory");
		}

	}

	// Helper method for debug method
	private String debug() {
		return debug(root);
	}

	// Helper method for debug method, it will make a string that contains whole
	// BST every node
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

	// The class is tree node
	private class TreeNode {
		private int data;

		private TreeNode leftChild;
		private TreeNode rightChild;

		// Constructor, create a node but it doesn't have left and right
		public TreeNode(int data) {
			this.data = data;
			this.leftChild = null;
			this.rightChild = null;
		}

		// To get data
		public int getData() {
			return data;
		}

		// Set data
		public void setData(int data) {
			this.data = data;
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
