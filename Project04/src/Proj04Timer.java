// Proj04Timer
//
// main() class for the timing of operations in Project 4.  This will perform
// various operations many times over, and report the elapsed time.
//
// This program has four command-line parameters:
//     - 3 integers (start, end, step) : the range of dataset sizes to explore
//     - 1 string, giving the name of the operation to check.
//
// In all cases, this program will perform 10 iterations of each test (using
// identical but random input data for each data structure type).  It will
// discard the min & max times, and then average the rest.
//
// Authors: Russ Lewis, Andrew Predoehl


import java.util.HashSet;
import java.util.ArrayList;


public class Proj04Timer
{
	public static void main(String[] args)
	{
		// we need exactly 4 command-line parameters, and the first
		// three are ints.
		if (args.length != 4)
		{
			System.err.println("ERROR: Give 3 integers (start,end,step) and 1 string (operation to test).");
			System.exit(1);
		}


		// read the integer parameters.
		int start=0,end=0,step=0;
		try {
			start = Integer.parseInt(args[0]);
			end   = Integer.parseInt(args[1]);
			step  = Integer.parseInt(args[2]);
		} catch (Exception e) {
			System.err.println("ERROR: Could not part the first 3 parameters.  Give 3 integers (start,end,step) and 1 string (operation to test).");
			System.exit(1);
		}

		// sanity-check the parameters that we just read
		if (start <= 0 || start > end || step <= 0)
		{
			System.err.println("ERROR: Invalid integer parameters; the range is not valid.");
			System.exit(1);
		}


		// check to make sure that the 'type' is valid.
		if ( ! args[3].equals("insert")      &&
		     ! args[3].equals("delete")      &&
		     ! args[3].equals("search-hit")  &&
		     ! args[3].equals("search-miss") &&
		     ! args[3].equals("getKeys")     &&
		     ! args[3].equals("getSuccessor"))
		{
			System.err.printf("ERROR: The test type '%s' is not a recognized operation.  The possible operations are: insert,delete,search-hit,search-miss,getKeys,getSuccessor");
			System.exit(1);
		}


		// this is the header line for the .csv that we'll produce.
		System.out.println("Size, Unsorted Array, Sorted Array, BST, AVL, Hash Table, HashMap, TreeMap");


		// Main Loop
		//
		// This loop simply iterates over the various testcase sizes.
		//
		// For each pass of this loop, we simply call a helper
		// function; the helper will generate several sets of input
		// data (we use the same input data sets for all dictionary
		// types), and then runs the test for each dictionary type -
		// each using the same set of input datasets.
		for (int size=start; size<=end; size+=step)
			runTest(args[3], size);
	}



	// this function runs tests across all of the data structure types.
	//
	// This function is responsible for:
	//   - Confirming that the operation type is sane
	//   - Creating the data structures
	//   - Performing init, if required for this test
	//
	// This function calls runOneTest() - passing the dictionary as a
	// parameter - to generate the times for one particular dictionary.
	// That function returns a float, which is printed by this function.
	public static void runTest(String type, int size)
	{
		final int COUNT = 10;

		// generate 10 datasets; each is a set of keys and values
		// (neither set is sorted, although the two arrays in each
		// dataset are coordinate), along with a set of "probe"
		// values.  How we use the probe values varies based on the
		// testcase type; see elsewhere.

		System.err.printf("size=%d : generating the input data...", size);

		int   [][] keys = new int   [COUNT][];
		String[][] vals = new String[COUNT][];

		for (int i=0; i<COUNT; i++)
		{
			keys[i] = randIntArray(size);
			vals[i] = new String[size];

			for (int j=0; j<size; j++)
				vals[i][j] = "valueOf_"+keys[i][j];
		}

		System.err.println("done.");


		// now, build the arrays of dictionaries which will be used in
		// the tests.  This is a 2D array; the internal arrays are of
		// length 'COUNT', as they represent a set of dictionaries
		// (all of the same type) to be used in a runTestSet() call;
		// the outer array represents the variety of different
		// classes.
		
		Proj04Dictionary[][] empties = new Proj04Dictionary[7][COUNT];
		for (int cls=0; cls<7;   cls++)
		for (int i  =0; i<COUNT; i++)
		switch (cls)
		{
		case 0:	empties[cls][i] = new   UnsortedArray();	break;
		case 1:	empties[cls][i] = new     SortedArray();	break;
		case 2:	empties[cls][i] = new             BST();	break;
		case 3:	empties[cls][i] = new         AVLTree();	break;
		case 4:	empties[cls][i] = new       HashTable();	break;
		case 5:	empties[cls][i] = new HashMap_Wrapper();	break;
		case 6:	empties[cls][i] = new TreeMap_Wrapper();	break;
		}


		// print out the beginning of the output line; then iterate
		// through the classes, and print out each one; then print
		// a newline at the end.
		System.out.print(size);

		for (int i=0; i<7; i++)
		{
			double time = runTestSet(empties[i],
			                         type, keys,vals);
			System.out.printf(", %.3f", time);
		}

		System.out.println();
	}



	// runTestSet() - runs a set of testcasese (one time each) on a set
	//                of dictionaries (each dictionary is used for only a
	//                single test)
	//
	// There should be as many arrays of keys - and arrays of vals - as
	// there are dictionaries.  For each of these groups, we call
	// runOneTest() to do the test.  We sum up all of the times returned
	// (discarding the min and max), and return the average as a float.
	public static float runTestSet(Proj04Dictionary[] empties,
	                               String type, int[][] keys, String[][] vals)
	{
		// sanity checking
		if (empties.length <= 2 ||
		    empties.length != keys.length ||
		    empties.length != vals.length)
		{
			throw new IllegalArgumentException();
		}


		// we total up all of the time, for all of the passes
		// (including the most extreme).  But we will *ALSO* keep
		// track of the max and min.  At the end of the loop, we can
		// simply subtract out those two values to get the
		// sum-without-extremes.
		long  min = Long.MAX_VALUE, max = Long.MIN_VALUE;
		float totalTime = 0;

		for (int i=0; i<empties.length; i++)
		{
			long time = runOneTest(empties[i], type, keys[i],vals[i]);

			if (time == -1)
			{
				// any crash, in any single testcase, makes
				// the test invalid.
				return Float.NaN;
			}

			totalTime += time;

			if (time > max)
				max = time;
			if (time < min)
				min = time;
		}

		// remove the two extremes, and then return the average
		totalTime -= (min+max);
		return totalTime / (empties.length-2);
	}


	// runOneTest() - runs the test on a single dictionary
	//
	// This function takes as input a single dictionary (empty), along
	// with the keys and values to use in it.  This function will
	// initialize the dictionary before starting the clock (if necessary)
	// and then perform the particular test - timing as we go.
	//
	// It returns the time (in milliseconds) taken to run this test (not
	// counting init).
	//
	// P.S. This test also runs the GC before the test (before the clock
	//      begins) to minimize the chance that the GC will interfere
	//      with timing.
	public static long runOneTest(Proj04Dictionary dict,
	                              String type, int[] keys, String[] vals)
	{
		// sanity checking
		if (keys.length < 1 || keys.length != vals.length)
			throw new IllegalArgumentException(""+keys.length+" "+vals.length);


		try {
			/* do we need to initialize the dictionary? */
			initDict(dict, type, keys,vals);

			/* get the GC out of the way */
			System.gc();
			System.runFinalization();


			/* collect the current time */
			long starttime = System.currentTimeMillis();

			/* perform the actual test */
			doTest(dict, type, keys,vals);

			/* report elapsed time */
			return System.currentTimeMillis() - starttime;

		} catch (Exception e) {
			System.err.println("  CRASH IN USER CODE:");
			e.printStackTrace();
			return -1;
		}
	}



	public static void doTest(Proj04Dictionary dict,
	                          String type, int[] keys, String[] vals)
	{
		// sanity checks
		if (keys.length != vals.length)
			throw new IllegalArgumentException();

		/*
		 * The type string could be any of the Proj04Dictionary methods.
		 */
		if (type.equals("insert"))
		{
			for (int i=0; i<keys.length; i++)
				dict.insert(keys[i], vals[i]);
		}
		else if (type.equals("delete"))
		{
			for (int i=0; i<keys.length; i++)
				dict.delete(keys[i]);
		}
		else if (type.equals("search-hit"))
		{
			for (int i=0; i<keys.length; i++)
				dict.search(keys[i]);
		}
		else if (type.equals("search-miss"))
		{
			// Not guaranteed to miss, but very very likely to miss.
			for (int i=0; i<keys.length; i++)
				dict.search(keys[i]+17);
		}
		else if (type.equals("getKeys"))
		{
			// Not guaranteed to miss, but very very likely to miss.
			Integer[] key2 = null;
			for (int i=0; i<keys.length; i++)
				key2 = dict.getKeys();
		}
		else if (type.equals("getSuccessor"))
		{
			// Find the max key -- it does not have a successor, so don't ask.
			// Linear time, but oh well, no way to avoid it.
			int maxkey = keys[0];
			for (int i=1; i<keys.length; i++)
				maxkey = Math.max(maxkey, keys[i]);

			// getSuccessor for all other keys
			for (int i=0; i<keys.length; i++)
				if (keys[i] != maxkey)
					dict.getSuccessor(keys[i]);
		}
		else
		{
			throw new RuntimeException("bad type");
		}
	}



	// initDict() - initializes a Dictionary before the clock begins (if
	//              necessary)
	public static void initDict(Proj04Dictionary dict,
	                            String type, int[] keys, String[] vals)
	{
		if (type.equals("insert"))
			return;   // NOP

		/*
		 * Create a permutation with which to shuffle records.
		 */
		ArrayList<Integer> perm = new ArrayList<Integer>(keys.length);
		for (int i=0; i < keys.length; ++i)
			perm.add(i);
		java.util.Collections.shuffle(perm);

		// all of the other test types require that we have valid
		// data in the dictionary.
		for (int i=0; i<keys.length; i++)
			dict.insert(keys[perm.get(i)], vals[perm.get(i)]);
	}



	public static int[] randIntArray(int size)
	{
		HashSet<Integer> nums = new HashSet<Integer>();

		int[] retval = new int[size];
		for (int i=0; i<size; i++)
		{
			int val;
			do {
				val = (int)((Math.random()-.25) * (1000*1000));
			} while (nums.contains(val));

			nums.add(val);
			retval[i] = val;
		}
		return retval;
	}
}

