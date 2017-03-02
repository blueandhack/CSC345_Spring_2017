
/*
 * Proj02_BST.java
 *
 * CSc 345 Spring 2017 - Project02
 * 
 * Author: Yujia Lin
 *
 * ---
 * The file contains main method, we can run the program in there.
 * It will read a file, then it create a empty tree by BinarySearchTree.java.
 * And we can use those command:
 * insert, search, delete, count, inOrder, preOrder, debug
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Proj02_BST {

	// The main method will read some file that user given, and create a BST.
	public static void main(String[] args) {

		// Check argument
		if (args.length != 1) {
			System.out.println("SYNTAX: ./example_proj02_bst <filename>");
			System.exit(0);
		}

		// Get filename
		String filename = args[0];

		Scanner input;
		try {

			// Create a empty binary search tree
			BinarySearchTree tree = new BinarySearchTree();

			File file = new File(filename);
			input = new Scanner(file);

			// Do the loop, and read every line
			while (input.hasNextLine()) {

				// Read a line
				String line = input.nextLine();

				if (line.equals("preOrder")) {
					// Command preOrder
					System.out.println(tree.preOrder());
				} else if (line.equals("inOrder")) {
					// Command inOrder
					System.out.println(tree.inOrder());
				} else if (line.equals("count")) {
					// Command count
					System.out.println("Current tree size: " + tree.count() + " nodes");
				} else {
					String[] command = line.split(" ");

					if (command[0].equals("insert")) {
						// Command insert
						int data = Integer.parseInt(command[1]);

						if (data < 0) {
							System.out.println(
									"ERROR: Invalid testcase!  A numeric parameter is less than zero!  val=" + data);
							System.exit(0);
						}

						if (!tree.contains(data)) {
							tree.insertTreeNode(Integer.parseInt(command[1]));
						} else {
							System.out
									.println("ERROR: Could not insert " + data + " because it is already in the tree.");
						}

					} else if (command[0].equals("search")) {
						// Command search

						int data = Integer.parseInt(command[1]);

						if (data < 0) {
							System.out.println(
									"ERROR: Invalid testcase!  A numeric parameter is less than zero!  val=" + data);
							System.exit(0);
						}

						if (tree.contains(data)) {
							System.out.println("HIT: The value " + data + " was found in the tree.");
						} else {
							System.out.println("MISS: The value " + data + " was *NOT* found in the tree.");
						}

					} else if (command[0].equals("debug")) {
						// Command debug
						tree.debug(command[1]);
					} else if (command[0].equals("delete")) {
						// Command delete

						int data = Integer.parseInt(command[1]);

						if (data < 0) {
							System.out.println(
									"ERROR: Invalid testcase!  A numeric parameter is less than zero!  val=" + data);
							System.exit(0);
						}

						if (tree.contains(data)) {
							tree.delete(data);
						} else {
							System.out.println("ERROR: Could not delete " + data + " because it was not in the tree.");
						}
					}
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not open the input file: No such file or directory");
		}

	}

}
