/* class Proj03TestDRiver
 *
 * This class is *NOT* designed to be a general-purpose test for all of the
 * functionality of your program.  Instead, it is a simple test, which
 * confirms that some basic functionality works.
 *
 * Primarily, it confirms that you have not changed the declarations of the
 * methods in AVLTree which we defined in the original file.
 */

public class Proj03TestDriver {
	public static void main(String[] args) {
		System.out.println("Project 3 Test Driver");
		System.out.println();
		System.out.println("This program runs some very simple tests, on some simple operations.  If it");
		System.out.println("reports errors, it probably means that you have violated the spec by changing");
		System.out.println("the AVLTree class.  If it does *NOT* report any errors, that doesn't mean that");
		System.out.println("your code entirely works - but only that it has passed a *VERY* simple test.");
		System.out.println("Use the grading script for better (though still not perfect testing.");
		System.out.println();

		AVLTree tree1 = new AVLTree();
		AVLTree tree2 = new AVLTree();

		System.out.println("Searching an empty tree...");
		if (tree1.search(10) != null)
			System.out.println("   ERROR");

		System.out.println("Inserting in to a tree...");
		tree1.insert(10);

		System.out.println("Checking to make sure that the 2nd tree is still empty...");
		if (tree2.search(10) != null)
			System.out.println("   ERROR");

		System.out.println("Search check in the first tree...");
		if (tree1.search(10).val != 10)
			System.out.println("   ERROR");

		System.out.println("Inserting a second value...");
		tree1.insert(20);

		System.out.println("Checking the shape of the tree...");
		AVLNode search10 = tree1.search(10);
		AVLNode search20 = tree1.search(20);
		if (search10.left != null || search10.right != search20 || search10.val != 10 || search20.left != null
				|| search20.right != null || search20.val != 20) {
			System.out.println("   ERROR");
		}

		System.out.println("Double-checking search...");
		if (tree1.search(10) != search10 || tree1.search(20) != search20) {
			System.out.println("   ERROR");
		}

		System.out.println("Testing duplicate-vaelu insertion...");
		boolean exceptionFound = false;
		try {
			tree1.insert(20);
		} catch (IllegalArgumentException e) {
			exceptionFound = true;
		}
		if (exceptionFound == false)
			System.out.println("   ERROR");

		System.out.println("Testing simple delete...");
		tree2.insert(10);
		tree2.delete(10);
		if (tree2.search(10) != null)
			System.out.println("   ERROR");

		System.out.println("Testing delete-not-exist...");
		exceptionFound = false;
		try {
			tree1.delete(100);
		} catch (IllegalArgumentException e) {
			exceptionFound = true;
		}
		if (exceptionFound == false)
			System.out.println("   ERROR");

		System.out.println("Creating a simple tree, and then doing a rebalance...");
		AVLNode reb10 = new AVLNode(10);
		AVLNode reb20 = new AVLNode(20);
		AVLNode reb30 = new AVLNode(30);

		reb10.right = reb20;
		reb10.height = 2;
		reb20.right = reb30;
		reb20.height = 1;

		AVLNode afterReb = AVLTree.rebalance(reb10);
		if (afterReb != reb20)
			System.out.println("   ERROR");

		System.out.println("Confirming that nodes were moved around, not values...");
		if (reb10.val != 10 || reb20.val != 20 || reb30.val != 30)
			System.out.println("   ERROR");

		System.out.println("Checking that the shape of the rebalanced tree is correct...");
		if (reb20.left != reb10 || reb20.right != reb30 || reb10.left != null || reb10.right != null
				|| reb30.left != null || reb30.right != null || reb20.height != 1 || reb10.height != 0
				|| reb30.height != 0) {
			/*
			 * Russ wrote some debug code, to find errors in his own code. But
			 * if you hit other errors, you can write your own debug code,
			 * elsewhere. :)
			 */
			System.out.println(reb10);
			System.out.println(reb20);
			System.out.println(reb30);
			System.out.println();
			System.out.println(reb10.left);
			System.out.println(reb10.right);
			System.out.println();
			System.out.println(reb20.left);
			System.out.println(reb20.right);
			System.out.println();
			System.out.println(reb30.left);
			System.out.println(reb30.right);
			System.out.println();
			System.out.println(reb10.height);
			System.out.println(reb20.height);
			System.out.println(reb30.height);

			System.out.println("   ERROR");
		}

		System.out.println("Checking getHeight()...");
		if (AVLTree.getHeight(null) != -1 || AVLTree.getHeight(reb10) != 0 || AVLTree.getHeight(reb20) != 1) {
			System.out.println("   ERROR");
		}

		System.out.println("Checking out rotateRight...");
		AVLTree preRotRt = new AVLTree();
		preRotRt.insert(400);
		preRotRt.insert(200);
		preRotRt.insert(500);
		preRotRt.insert(100);
		preRotRt.insert(300);

		AVLNode preRotRtRoot = preRotRt.search(400);
		AVLNode postRotRt = AVLTree.rotateRight(preRotRtRoot);

		AVLNode prr_l = postRotRt.left;
		AVLNode prr_r = postRotRt.right;
		AVLNode prr_rl = prr_r.left;
		AVLNode prr_rr = prr_r.right;
		if (postRotRt.val != 200 || prr_l.val != 100 || prr_r.val != 400 || prr_rl.val != 300 || prr_rr.val != 500) {
			System.out.println("   ERROR");
		}

		System.out.println("Checking out rotateLeft...");
		AVLTree preRotLt = new AVLTree();
		preRotLt.insert(1002);
		preRotLt.insert(1001);
		preRotLt.insert(1004);
		preRotLt.insert(1003);
		preRotLt.insert(1005);

		AVLNode preRotLtRoot = preRotLt.search(1002);
		AVLNode postRotLt = AVLTree.rotateLeft(preRotLtRoot);

		AVLNode prl_l = postRotLt.left;
		AVLNode prl_r = postRotLt.right;
		AVLNode prl_ll = prl_l.left;
		AVLNode prl_lr = prl_l.right;
		if (postRotLt.val != 1004 || prl_l.val != 1002 || prl_r.val != 1005 || prl_ll.val != 1001
				|| prl_lr.val != 1003) {
			System.out.println("   ERROR");
		}

		System.out.println();
		System.out.println("Simple tests completed.  Look for ERRORs in the output above.");
	}
}
