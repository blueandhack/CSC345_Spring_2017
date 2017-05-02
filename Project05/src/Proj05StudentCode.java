
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
	 * 
	 * The idea from 19_graphs_in_code page 93
	 */
	public static void reachable(Proj05Vertex[] verts, int fromIndx, int toIndx) {
		if (fromIndx == toIndx) {
			// if fromIndex equals toIndex, then the path is itself.
			System.out.println("Reachable: " + verts[fromIndx].name);
		} else if (verts[fromIndx].outEdges.size() < 1) {
			// if the vertex does not have edges, then the vertex is island. So,
			// no path.
			System.out.println("There is no path from " + verts[fromIndx].name + " to " + verts[toIndx].name);
		} else {

			// initialization for verts
			for (int i = 0; i < verts.length; i++) {
				verts[i].accObj = null;
				verts[i].accBool = false;
			}

			// use DFS helper method
			DFS(verts[fromIndx]);

			Proj05Vertex vertex = verts[toIndx];

			if (vertex.accObj == null) {
				// if last vertex does not have parent vertex, then it does not
				// have a path between fromIndex and toIndex.
				System.out.println("There is no path from " + verts[fromIndx].name + " to " + verts[toIndx].name);
			} else {
				// from toIndex vertex to fromIndex, so we should put names to
				// stack, then it will print correct order.
				Stack<String> vertName = new Stack<String>();

				while (vertex.accObj != null) {
					if (vertex.name.equals(verts[fromIndx].name)) {
						break;
					}
					vertName.push(vertex.name);
					vertex = (Proj05Vertex) vertex.accObj;
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
	private static void DFS(Proj05Vertex vertex) {
		// search from v
		vertex.accBool = true;
		// visit the unvisited
		for (Proj05Edge edge : vertex.outEdges) {
			if (edge.toVrt.accBool != true) {
				// store the link toward fromIndex
				edge.toVrt.accObj = vertex;
				DFS(edge.toVrt);
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
	 * 
	 * The pseudo code from 20_graph_algs page 257
	 */
	public static void dijkstra(Proj05Vertex[] verts, int fromIndx, int toIndx) {

		if (fromIndx == toIndx) {
			// if the fromIndex equals toIndex, then the path is itself.
			System.out.println("Dijkstra: len=0 " + verts[fromIndx].name);
		} else if (verts[fromIndx].outEdges.size() < 1) {
			// if the vertex does not have edges, then the vertex is island. So,
			// no path.
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
				Proj05Vertex vertex = verts[index];
				// if the vertex does not an island (no one direct it), then to
				// check the path.
				if (vertex.accInt != Integer.MAX_VALUE) {
					// get all of neighbors
					for (Proj05Edge edege : vertex.outEdges) {
						// if the vertex's path is big, then change it.
						if (verts[edege.toIndx].accInt > vertex.accInt + edege.weight) {
							edege.toVrt.accObj = vertex;
							edege.toVrt.accInt = vertex.accInt + edege.weight;
							// update vertex's priority in q, also.
							pq.changeKey(edege.toIndx, edege.toVrt.accInt);
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

				// store the result to a string and print it.
				String names = "Dijkstra: len=" + verts[toIndx].accInt + " " + verts[fromIndx].name;
				while (!vertName.isEmpty()) {
					names = names + " -> " + vertName.pop();
				}
				System.out.println(names);
			}
		}

	}
}
