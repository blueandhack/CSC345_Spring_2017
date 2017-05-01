
/* class Proj05StudentCode
 *
 * This is the file that the students must turn in.  The instructors have
 * provided the skeleton for it.
 *
 * Skeleton code: Russell Lewis
 * Author:        Yujia Lin
 */

import java.io.*;
import java.util.Stack;

public class Proj05StudentCode {
	/*
	 * this must open up the filename specified, and then print out a complete
	 * .dot file, which includes all of the information from the graph.
	 */
	public static void printDotFile(Proj05Vertex[] verts, String filename) throws IOException {
		StringBuffer strbuff = new StringBuffer();
		strbuff.append("digraph {\n");

		// traversal verts and get edges, then put them to a string buffer.
		for (Proj05Vertex pv : verts) {
			strbuff.append("  " + pv.name + ";\n");
			if (pv.outEdges.size() != 0) {
				for (Proj05Edge pe : pv.outEdges) {
					int weight = pe.weight;
					strbuff.append(
							"    " + pv.name + " -> " + verts[pe.toIndx].name + " [label=" + weight + "]" + ";\n");
				}
			}
		}
		strbuff.append("}\n");

		// write the string buffer to file.
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
		writer.write(strbuff.toString());

		// flush the stream
		writer.flush();

		// close the stream
		writer.close();
	}

	/*
	 * this checks to see if one index is reachable from another. It prints out
	 * the solution to System.out.
	 *
	 * The 'fromIndx' and 'toIndx' are the indices, in the verts[] array
	 * parameter, of the endpoints of the search. The path must start at the
	 * 'from' node, and get to the 'to' node.
	 */
	public static void reachable(Proj05Vertex[] verts, int fromIndx, int toIndx) {
		if (fromIndx == toIndx) {
			System.out.println("Reachable: " + verts[fromIndx].name);
		} else if (verts[fromIndx].outEdges.size() < 1) {
			System.out.println("There is no path from " + verts[fromIndx].name + " to " + verts[toIndx].name);
		} else {

			// initialization for verts
			for (int i = 0; i < verts.length; i++) {
				verts[i].accObj = null;
				verts[i].accBool = false;
			}

			// use DFS helper method
			DFS(verts[fromIndx]);

			Proj05Vertex v = verts[toIndx];

			if (v.accObj == null) {
				// if last vertex does not have parent vertex, then it does not
				// have a path between fromIndex and toIndex.
				System.out.println("There is no path from " + verts[fromIndx].name + " to " + verts[toIndx].name);
			} else {
				// from toIndex vertex to fromIndex, so we should put names to
				// stack, then it will print correct order.
				Stack<String> vertName = new Stack<String>();

				while (v.accObj != null) {
					if (v.name.equals(verts[fromIndx].name)) {
						break;
					}
					vertName.push(v.name);
					v = (Proj05Vertex) v.accObj;
				}

				String names = "Reachable: " + verts[fromIndx].name;
				while (!vertName.isEmpty()) {
					names = names + " -> " + vertName.pop();
				}

				System.out.println(names);
			}

		}
	}

	// this method use DFS algorithm to find a path.
	private static void DFS(Proj05Vertex v) {
		v.accBool = true;
		for (Proj05Edge e : v.outEdges) {
			if (e.toVrt.accBool != true) {
				e.toVrt.accObj = v;
				DFS(e.toVrt);
			}
		}

	}

	/*
	 * this must run dijkstra's algorithm. It prints out the solution to
	 * System.out - which will include both the path, and also the total length
	 * of the path.
	 *
	 * The 'fromIndx' and 'toIndx' are the indices, in the verts[] array
	 * parameter, of the endpoints of the search. The path must start at the
	 * 'from' node, and get to the 'to' node. And, of course, it must also be
	 * optimal.
	 */
	public static void dijkstra(Proj05Vertex[] verts, int fromIndx, int toIndx) {

		if (fromIndx == toIndx) {
			System.out.println("Dijkstra: len=0 " + verts[fromIndx].name);
		} else if (verts[fromIndx].outEdges.size() < 1) {
			System.out.println("There is no path from " + verts[fromIndx].name + " to " + verts[toIndx].name);
		} else {
			IndexMinPQ<Integer> pq = new IndexMinPQ<>(verts.length);

			// initialization for verts
			for (int i = 0; i < verts.length; i++) {
				if (i == fromIndx) {
					pq.insert(i, 0);
					verts[i].accInt = 0;
				} else {
					pq.insert(i, Integer.MAX_VALUE);
					verts[i].accInt = Integer.MAX_VALUE;
				}
				verts[i].accObj = null;
			}

			// the loop will help to find a path between fromIndex and toIndex.
			while (!pq.isEmpty()) {
				// remove and return best vertex
				int index = pq.delMin();
				Proj05Vertex v = verts[index];
				if (v.accInt != Integer.MAX_VALUE) {
					// get all of neighbors
					for (Proj05Edge e : v.outEdges) {
						int eIndex = 0;

						// find the index in the vertices.
						for (int i = 0; i < verts.length; i++) {
							if (verts[i].name.equals(e.toVrt.name)) {
								eIndex = i;
							}
						}

						// if the vertex's path is big, then change it.
						if (verts[eIndex].accInt > v.accInt + e.weight) {
							e.toVrt.accObj = v;
							e.toVrt.accInt = v.accInt + e.weight;
							pq.changeKey(eIndex, e.toVrt.accInt);
						}
					}
				}
			}

			Proj05Vertex v = verts[toIndx];

			if (v.accObj == null || v.accInt == Integer.MAX_VALUE) {
				// if last vertex does not have parent vertex, then it does not
				// have a path between fromIndex and toIndex.
				System.out.println("There is no path from " + verts[fromIndx].name + " to " + verts[toIndx].name);
			} else {
				// from toIndex vertex to fromIndex, so we should put names to
				// stack, then it will print correct order.
				Stack<String> vertName = new Stack<String>();
				while (v.accObj != null) {
					if (v.name.equals(verts[fromIndx].name)) {
						break;
					}
					vertName.push(v.name);
					v = (Proj05Vertex) v.accObj;
				}

				String names = "Dijkstra: len=" + verts[toIndx].accInt + " " + verts[fromIndx].name;
				while (!vertName.isEmpty()) {
					names = names + " -> " + vertName.pop();
				}
				System.out.println(names);
			}
		}

	}
}
