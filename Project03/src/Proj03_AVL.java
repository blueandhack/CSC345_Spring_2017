import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Proj03_AVL {

	public static void main(String[] args) {

		// Check argument
		if (args.length != 1) {
			System.out.println("SYNTAX: ./proj03_AVL <filename>");
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

				String[] command = line.split(" ");

				if (command.length == 1) {
					continue;
				}

				if (command[1].equals("preOrder")) {
					// Command preOrder
					tree[Integer.parseInt(command[0])].print_preOrder();
				} else if (command[1].equals("inOrder")) {
					// Command inOrder
					tree[Integer.parseInt(command[0])].print_inOrder();
				} else {

					if (command[1].equals("insert")) {
						// Command insert
						int data = Integer.parseInt(command[2]);

						if (data < 0) {
							System.out.println(
									"ERROR: Invalid testcase!  A numeric parameter is less than zero!  val=" + data);
							System.exit(0);
						}

						tree[Integer.parseInt(command[0])].insert(Integer.parseInt(command[2]));

					} else if (command[1].equals("search")) {
						// Command search

						int data = Integer.parseInt(command[2]);

						if (data < 0) {
							System.out.println(
									"ERROR: Invalid testcase!  A numeric parameter is less than zero!  val=" + data);
							System.exit(0);
						}

						if (tree[Integer.parseInt(command[0])].search(data) != null) {
							System.out.println("HIT: The value " + data + " was found in the tree.");
						} else {
							System.out.println("MISS: The value " + data + " was *NOT* found in the tree.");
						}

					} else if (command[1].equals("debug")) {
						// Command debug
						tree[Integer.parseInt(command[0])].debug(command[2]);
					} else if (command[1].equals("delete")) {
						// Command delete

						int data = Integer.parseInt(command[2]);

						if (data < 0) {
							System.out.println(
									"ERROR: Invalid testcase!  A numeric parameter is less than zero!  val=" + data);
							System.exit(0);
						}

						tree[Integer.parseInt(command[0])].delete(data);
					}
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not open the input file: No such file or directory");
		}

	}

}
