
/*
 * AVLTree.java
 *
 * CSc 345 Spring 2017 - Project03
 * 
 * Author: Yujia Lin
 *
 * ---
 * The file is class AVLTree, and it contains many method about AVLTree. 
 * So we can create object that AVLTree by the class.
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/* class AVLTree
 *
 * Models a complete AVLTree.  Individual nodes are modeled by the AVLNode
 * class.
 *
 * The constructor creates an empty tree (root=null).
 *
 * This class has many methods which perform basic operations.  Most of these
 * (non-static) methods have (static) recursive helper methods, which take an
 * AVLNode as the first paramter; these are static because it is valid for the
 * parameter to be null.  Thus, the base case for each of these methods is an
 * empty tree.  (See the method documentation below for details; there are a
 * few exceptions to this rule.)
 *
 * Methods that perform modifications on the tree all use the x=change(x)
 * style.  These include highly visible methods (such as insert and delete),
 * and also utility methods (such as rotateRight).
 */
public class AVLTree {
	// ALERT
	//
	// You *must not* add any fields other than this one to this class!
	// This field is sufficient for everything that you need to do.
	//
	// Also, remember that you can't change the AVLNode class *at all* -
	// and so you cannot add fields there, either!
	//
	// However, you can (and should!) add some static helper methods to
	// this class.
	private AVLNode root;

	/*
	 * default constructor
	 *
	 * Initializes this object to represent an empty tree.
	 */
	public AVLTree() {
		// See my comment in the constructor for AVLNode. I like
		// to explicitly set things to null, instead of simply
		// using the default values.
		// - Russ
		root = null;
	}

	/*
	 * debug(String)
	 *
	 * Opens a file with the given filename, and then fills that file with .dot
	 * file source code, to generate a picture for this tree. This wrapper opens
	 * (and closes) the file, and adds the necessary prefix/suffix text inside
	 * it. However, the actual body of the file should be printed by a recursive
	 * helper method.
	 *
	 * - Note from Russ: I have not yet defined the helper method, since I
	 * assume that different students might use different Java classes for
	 * printing. One option is to pass a FileWriter as a parameter; another is
	 * to *return* a String.
	 */
	public void debug(String filename) {

		String newFilename = "" + filename;

		String content = "digraph {\n";

		String str = "";

		if (root != null) {
			str = "  DUMMY [style=invis];\n  DUMMY -> " + root.val + ";\n  " + root.val + " [penwidth=2];\n\n";
			str += debug(root);
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

	private String debug(AVLNode node) {
		String left = "\n";

		String right = "\n";
		if (node == null) {
			return "";
		}

		if (node.left != null) {
			left = "  " + node.val + " -> " + node.left.val + " [label=\"L\"];\n";
		}

		if (node.left == null && node.right != null) {
			left = "  " + node.val + " -> L_" + node.val + " [style=invis];\n  L_" + node.val + " [style=invis];\n\n";
		}

		if (node.right != null) {
			right = "  " + node.val + " -> " + node.right.val + " [label=\"R\"];\n";
		}

		if (node.right == null && node.left != null) {
			right = "  " + node.val + " -> R_" + node.val + " [style=invis];\n  R_" + node.val + " [style=invis];\n\n";
		}

		String str = "  " + node.val + " [label=\"" + node.val + "\\nh=" + node.height + "\"]" + ";\n\n" + left
				+ debug(node.left) + right + debug(node.right);

		return str;
	}

	/*
	 * print_inOrder()
	 *
	 * Prints out an in-order traversal of the tree to stdout (followed by a
	 * newline). The keys are separated by spaces, and the list (if non-empty)
	 * has a trailing space. If the tree is empty, this prints out nothing
	 * except for the newline. Uses a static recursive helper method.
	 *
	 * static print_inOrder(AVLNode)
	 *
	 * Helper method for the public method. Is static; takes an AVLNode
	 * parameter, which might be null. If the parameter is null, it does
	 * nothing. Works recursively.
	 */
	public void print_inOrder() {
		print_inOrder(root);
	}

	private static void print_inOrder(AVLNode tree) {
		String inorder = inOrder(tree);
		System.out.println(inorder);
	}

	private static String inOrder(AVLNode node) {
		if (node == null) {
			return "";
		} else if (node.left == null && node.right == null) {
			return "" + node.val + " ";
		}
		return inOrder(node.left) + node.val + " " + inOrder(node.right);
	}

	/*
	 * print_preOrder()
	 *
	 * See print_inOrder() above - works in exactly the same way, except
	 * produces a pre-order traversal instead of an in-order traversal.
	 */
	public void print_preOrder() {
		print_preOrder(root);
	}

	private static void print_preOrder(AVLNode tree) {
		String preorder = preOrder(tree);
		System.out.println(preorder);
	}

	private static String preOrder(AVLNode node) {
		if (node == null) {
			return "";
		}
		return node.val + " " + preOrder(node.left) + preOrder(node.right);
	}

	public void print_inOrder_range(int low, int hi) {
		print_inOrder_range(root, low, hi);
		System.out.print("\n");
	}

	private static void print_inOrder_range(AVLNode node, int low, int hi) {
		if (node == null) {
			return;
		}
		if (low < node.val) {
			print_inOrder_range(node.left, low, hi);
		}
		if (node.val >= low && node.val <= hi) {
			System.out.print(node.val + " ");
		}
		if (hi > node.val) {
			print_inOrder_range(node.right, low, hi);
		}
	}

	/*
	 * search(int)
	 *
	 * Searches the tree for a given key. Returns the node where the tree
	 * exists, or null if the key does not exist. May be implemented recursively
	 * (with a helper method), or as a loop.
	 */
	public AVLNode search(int val) {
		return search(val, root);
	}

	private AVLNode search(int data, AVLNode node) {
		if (node == null) {
			return null;
		} else if (node.val == data) {
			return node;
		} else if (node.val < data) {
			return search(data, node.right);
		} else {
			return search(data, node.left);
		}
	}

	/*
	 * insert(int)
	 *
	 * Inserts a new key into the tree; throws IllegalArgumentException if the
	 * key is a duplicate.
	 *
	 * Uses the x=change(x) style to modify the tree. Uses a static, recursive
	 * helper method to implement the modification. The helper method is
	 * responsible for *all* of the changes associated with inserting a new
	 * value, including all rebalances.
	 *
	 * static insert(AVLNode,int)
	 *
	 * Static, recursive helper method for insert(int). Takes an AVLNode
	 * parameter, which might be null; if the parameter is null, then it creates
	 * a new node and returns it.
	 *
	 * Always returns the root of the new tree; does all of the rebalancing work
	 * associated with the insert.
	 */
	public void insert(int val) throws IllegalArgumentException {
		root = insert(root, val);
	}

	private static AVLNode insert(AVLNode subtree, int val) throws IllegalArgumentException {
		if (subtree == null) {
			subtree = new AVLNode(val);
		} else if (subtree.val > val) {
			subtree.left = insert(subtree.left, val);
		} else if (subtree.val < val) {
			subtree.right = insert(subtree.right, val);
		} else if (subtree.val == val) {
			throw new IllegalArgumentException();
		}
		heightHelper(subtree);
		if (subtree != null) {
			subtree = rebalance(subtree);
		}
		heightHelper(subtree);
		return subtree;
	}

	/*
	 * The private helper function will make sure a subtree's every nodes have
	 * correct height
	 */
	private static int heightHelper(AVLNode node) {
		if (node == null) {
			return 0;
		}
		node.height = Math.max(heightHelper(node.left), heightHelper(node.right));
		return node.height + 1;
	}

	public boolean check_all_heights() {
		return check_all_heights_helper(root);
	}

	private boolean check_all_heights_helper(AVLNode node) {
		if (node == null) {
			return true;
		} else if (node.right == null && node.left == null) {
			if (node.height == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (node.height == Math.max(node.left.height, node.right.height) + 1) {
				return check_all_heights_helper(node.left) && check_all_heights_helper(node.right);
			} else {
				return false;
			}
		}

	}

	/*
	 * delete(int)
	 *
	 * Deletes a key from the tree; throws IllegalArgumentException if the key
	 * does not exist.
	 *
	 * Uses the x=change(x) style to modify the tree. Uses a static, recursive
	 * helper method to implement the modification. The helper method is
	 * responsible for *all* of the changes associated with deleting the value,
	 * including all rebalances.
	 *
	 * static delete(AVLNode,int)
	 *
	 * Static, recursive helper method for delete(int). Takes an AVLNode
	 * parameter, which might be null; if the parameter is null, then we are
	 * attempting to delete from an empty tree, and thus the delete fails. On
	 * the other hand, this method can *return* null quite normally; this
	 * happens when we delete the last node from the tree.
	 *
	 * Always returns the root of the new tree (which might be null); does all
	 * of the rebalancing work associated with the delete. (But see note in the
	 * spec about possibly skipping rebalance-on-delete.)
	 */
	public void delete(int val) throws IllegalArgumentException {
		if (search(val) == null) {
			throw new IllegalArgumentException();
		}
		root = delete(root, val);
	}

	public static AVLNode delete(AVLNode subtree, int val) {
		if (subtree == null) {
			return subtree;
		} else if (subtree.val > val) {
			// If data less than the node's data, then go to left
			subtree.left = delete(subtree.left, val);
		} else if (subtree.val < val) {
			// If data more than the node's data, then go to right
			subtree.right = delete(subtree.right, val);
		} else if (subtree.left != null && subtree.right != null) {
			// If the node has two child
			subtree.val = findMax(subtree.left).val;
			subtree.left = delete(subtree.left, subtree.val);
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
	private static AVLNode findMax(AVLNode node) {
		if (node == null) {
			return null;
		} else if (node.right == null) {
			return node;
		}
		return findMax(node.right);
	}

	/*
	 * static rebalance(AVLNode)
	 *
	 * Performs a rebalance (if required) on the current node. This method
	 * assumes that the parameter is non-null; if it is passed a null parameter,
	 * it will hit NullPointerException. However, it is valid for either (or
	 * both) of the children of this node to be null; to be clear, it is OK to
	 * call this on a leaf node.
	 *
	 * This method assumes that the height is correct, in the current node and
	 * all its descendants; thus, after an insert or delete, the *CALLER* must
	 * update the height before calling this method.
	 *
	 * This method returns the root of the new tree. If no rotations were
	 * required, then this is simply the parameter; if rotations were required,
	 * then this returns the root after the rotation.
	 *
	 * This method is NON-RECURSIVE. It runs in O(1) time. As such, it only
	 * checks for imbalances at THIS PARTICULAR NODE, never at any descendants.
	 */
	public static AVLNode rebalance(AVLNode node) {
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

	/*
	 * static getHeight(AVLNode)
	 *
	 * Helper method that returns the height of a subtree. The parameter may be
	 * null.
	 *
	 * Since a single node has height=0, this should return -1 if passed a null
	 * parameter.
	 *
	 * This method is NON-RECURSIVE. It runs in O(1) time. As such, it may only
	 * check the height field in the current node; it does not look at any other
	 * nodes.
	 */
	public static int getHeight(AVLNode subtree) {
		if (subtree == null) {
			return -1;
		} else {
			return subtree.height;
		}
	}

	/*
	 * static rotateRight(AVLNode)
	 *
	 * Performs a right-rotation at the current node. This method assumes that
	 * both the parameter, and its left child, are non-null; if either is null,
	 * then a NullPointerException will result.
	 *
	 * Returns the root of the new tree, which is always the node which (used to
	 * be) the left child of the old root.
	 *
	 * This method works by changing REFERENCES NOT VALUES. That is, it does not
	 * inspect (or change) the .val field of any node; instead, it changes the
	 * left and right pointers.
	 *
	 * This method updates the height of all appropriate nodes after rearranging
	 * the references.
	 *
	 * This method is NON-RECURSIVE. It runs in O(1) time. As such, it may only
	 * check the fields in a fixed number of nodes.
	 */
	public static AVLNode rotateRight(AVLNode subtree) {
		AVLNode newRoot = subtree.left;
		subtree.left = newRoot.right;
		newRoot.right = subtree;
		return newRoot;
	}

	/*
	 * static rotateLeft(AVLNode)
	 *
	 * See the documentation for rotateRight() above. This is simply its mirror
	 * image.
	 */
	public static AVLNode rotateLeft(AVLNode subtree) {
		AVLNode newRoot = subtree.right;
		subtree.right = newRoot.left;
		newRoot.left = subtree;
		return newRoot;
	}

	// double rotate left
	private static AVLNode doubleRotateLeft(AVLNode node) {
		node.right = rotateRight(node.right);
		return rotateLeft(node);
	}

	// double rotate rights
	private static AVLNode doubleRotateRight(AVLNode node) {
		node.left = rotateLeft(node.left);
		return rotateRight(node);
	}

}
