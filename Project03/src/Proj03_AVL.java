
/*
 * Proj03_AVL.java
 *
 * CSc 345 Spring 2017 - Project03
 * 
 * Author: Yujia Lin
 *
 * ---
 * The file contains main method, we can run the program in there.
 * It will read a file, then it create four empty AVLTrees by AVLTree.java
 * And we can use those command:
 * insert, search, delete, inOrder, preOrder, debug
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Proj03_AVL {

	public static void main(String[] args) {

		// Check argument
		if (args.length != 1) {
			System.err.println("SYNTAX: ./Proj03_AVL <filename>");
			System.exit(0);
		}

		// Get filename
		String filename = args[0];

		Scanner input;
		try {

			// Create a empty binary search tree
			AVLTree[] tree = new AVLTree[4];
			for (int i = 0; i < tree.length; i++) {
				tree[i] = new AVLTree();
			}

			File file = new File(filename);
			input = new Scanner(file);

			// Do the loop, and read every line
			while (input.hasNextLine()) {
				// Read a line
				String line = input.nextLine();

				String[] oCommand = line.split("\\s+");

				// remove some whitespaces form the array
				List<String> list = new ArrayList<String>(Arrays.asList(oCommand));
				list.removeAll(Arrays.asList(""));

				// get commands
				String[] command = list.toArray(oCommand);

				if (command.length <= 1) {
					// if the line is blank then just skip it
					continue;
				}

				if (command[1].equals("preOrder")) {
					// Command preOrder
					tree[Integer.parseInt(command[0])].print_preOrder();
				} else if (command[1].equals("inOrder")) {
					// Command inOrder
					tree[Integer.parseInt(command[0])].print_inOrder();
				} else {

					if (command[1].equals("range")) {
						int low = 0, high = 0;
						low = Integer.parseInt(command[2]);
						high = Integer.parseInt(command[3]);

						tree[Integer.parseInt(command[0])].print_inOrder_range(low, high);

					} else if (command[1].equals("insert")) {
						// Command insert
						int data = 0;
						try {
							data = Integer.parseInt(command[2]);
						} catch (Exception e) {
						}

						if (data < 0) {
							System.err.println(
									"ERROR: Invalid testcase!  A numeric parameter is less than zero!  val=" + data);
							System.exit(0);
						}

						try {
							tree[Integer.parseInt(command[0])].insert(Integer.parseInt(command[2]));
						} catch (Exception e) {
							System.err
									.println("ERROR: Could not insert " + data + " because it is already in the tree.");
						}

					} else if (command[1].equals("search")) {
						// Command search
						int data = 0;
						try {
							data = Integer.parseInt(command[2]);
						} catch (Exception e) {
						}

						if (data < 0) {
							System.err.println(
									"ERROR: Invalid testcase!  A numeric parameter is less than zero!  val=" + data);
							System.exit(0);
						}

						if (tree[Integer.parseInt(command[0])].search(data) != null) {
							System.out.println("HIT: The value " + data + " was found in tree " + command[0] + ".");
						} else {
							System.out.println(
									"MISS: The value " + data + " was *NOT* found in tree " + command[0] + ".");
						}

					} else if (command[1].equals("debug")) {
						// Command debug
						tree[Integer.parseInt(command[0])].debug(command[2]);
					} else if (command[1].equals("delete")) {
						// Command delete
						int data = 0;
						try {
							data = Integer.parseInt(command[2]);
						} catch (Exception e) {
						}

						if (data < 0) {
							System.err.println(
									"ERROR: Invalid testcase!  A numeric parameter is less than zero!  val=" + data);
							System.exit(0);
						}

						try {
							tree[Integer.parseInt(command[0])].delete(data);
						} catch (Exception e) {
							System.err.println("ERROR: Could not delete " + data + " because it was not in the tree.");
						}
					}
				}

			}
		} catch (FileNotFoundException e) {
			System.err.println("Could not open the input file: No such file or directory");
		}

	}

}
