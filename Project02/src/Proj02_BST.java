import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Proj02_BST {

	public static void main(String[] args) {

		String filename = args[0];

		Scanner input;
		try {

			BinarySearchTree tree = new BinarySearchTree();

			File file = new File(filename);
			input = new Scanner(file);
			while (input.hasNextLine()) {
				// System.out.println(input.nextLine());

				String line = input.nextLine();

				if (line.equals("preOrder")) {
					// System.out.println("The command is preOrder");
					System.out.println(tree.preOrder());
				} else if (line.equals("inOrder")) {
					// System.out.println("The command is inOrder");
					System.out.println(tree.inOrder());
				} else if (line.equals("count")) {
					// System.out.println("The command is count");
					System.out.println("Current tree size: " + tree.count() + " nodes");
				} else {
					String[] command = line.split(" ");

					if (command[0].equals("insert")) {
						int data = Integer.parseInt(command[1]);
						if (!tree.contians(data)) {
							tree.insertTreeNode(Integer.parseInt(command[1]));
						} else {
							System.out
									.println("ERROR: Could not insert " + data + " because it is already in the tree.");
						}

					} else if (command[0].equals("search")) {
						int data = Integer.parseInt(command[1]);

						if (tree.contians(data)) {
							System.out.println("HIT: The value " + data + " was found in the tree.");
						} else {
							System.out.println("MISS: The value " + data + " was *NOT* found in the tree.");
						}

					} else if (command[0].equals("debug")) {
						// System.out.println("The command is debug");
						tree.debug(command[1]);
					} else if (command[0].equals("delete")) {
						int data = Integer.parseInt(command[1]);
						if (tree.contians(data)) {
							tree.delete(data);
						} else {
							System.out.println("ERROR: Could not delete " + data + " because it was not in the tree.");
						}
					}
				}

			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.out.println("can not open file!");
		}

	}

}
