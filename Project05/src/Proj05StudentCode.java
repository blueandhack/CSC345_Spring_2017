
/* class Proj05StudentCode
 *
 * This is the file that the students must turn in.  The instructors have
 * provided the skeleton for it.
 *
 * Skeleton code: Russell Lewis
 * Author:        Yujia Lin
 */

import java.io.*;
import java.util.ArrayList;

public class Proj05StudentCode {
	/*
	 * this must open up the filename specified, and then print out a complete
	 * .dot file, which includes all of the information from the graph.
	 */
	public static void printDotFile(Proj05Vertex[] verts, String filename) throws IOException {
		StringBuffer strbuff = new StringBuffer();
		strbuff.append("digraph {\n");

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
		if (verts[fromIndx].outEdges.size() < 1) {
			System.out.println("There is no path from " + verts[fromIndx] + " to " + verts[toIndx]);
		} else {
			String targrt = verts[toIndx].name;
			// for(Proj05Edge pe: verts[fromIndx].outEdges){
			//
			// }

			ArrayList<Proj05Vertex> toSave = new ArrayList<Proj05Vertex>();
			toSave.add(verts[fromIndx]);

			System.out.println(verts[fromIndx].outEdges.size());
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
		throw new RuntimeException("TODO");
	}
}
