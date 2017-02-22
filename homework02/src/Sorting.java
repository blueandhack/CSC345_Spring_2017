
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @author Yujia Lin
 * CSC 345 Spring 2017 Project 1:  Sorting empirical measurements
 *
 */

/**
 * Interface for all other sorting algorithms to be tested.
 */
interface IntegerSort {

	/** Brief name of the sorting algorithm. */
	String name();

	/**
	 * Method to invoke the selected sorting algorithm.
	 *
	 * The method is expected to permute all elements from array[0] to
	 * array[array.length-1] into non-decreasing order. No other output should
	 * occur.
	 */
	void sort(int[] array);

	void sort(int[] is, int c);
}



class ShafferSort implements IntegerSort {

	public String name() {
		return "quicksort";
	}

	public void sort(int[] array) {
	}

	public void sort(int[] array, int c) {
		qsort(array, 0, array.length - 1, c);
		insertionSort(array);
	}

	// From Data Structures and Algorithm Analysis - Figure 7.11 and 7.12
	private void qsort(int[] A, int i, int j, int c) { // Quicksort
		if (i - j > c) {
			// k will be the first position in the right subarray
			int k = partition(A, i - 1, j, A[j]);
			qsort(A, i, k - 1, c); // Sort left partition
			qsort(A, k + 1, j, c); // Sort right partition
		}
	}

	// The method will find pivot then return it
	private int findpivot(int[] A, int i, int j) {
		return (i + j) / 2;
	}

	private int partition(int[] A, int l, int r, int pivot) {
		do { // Move bounds inward until they meet
			while (A[++l] < pivot)
				;
			while ((r != 0) && (A[--r] > pivot))
				;
			swap(A, l, r); // Swap out-of-place values
		} while (l < r); // Stop when they cross
		swap(A, l, r); // Reverse last, wasted swap
		return l; // Return first position in right partition
	}

	// The method will swap two elements in the array
	private void swap(int[] arr, int x, int y) {
		int temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}

	public void insertionSort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			int temp = array[i];
			int j;
			for (j = i - 1; j >= 0 && temp < array[j]; j--) {
				array[j + 1] = array[j];
			}
			array[j + 1] = temp;
		}
	}

}



/**
 * Driver class for sorting demonstration.
 *
 * This class contains a main() function to demonstrate the sorting algorithms,
 * generating arrays of random int values, calling the above sorting functions,
 * performing empirical timing measurements, and printing the results. Timing
 * measurements are somewhat averaged -- see details below. The program must be
 * launched with one (or two) command line argument, to specify the size of
 * array to test.
 */
class Sorting {

	/**
	 * Driver function for sorting demonstration.
	 *
	 * This function performs an empirical timing measurement for a given size
	 * input array (of random integers), for a variety of sorting algorithms.
	 * The first command-line argument must be the input size, as a decimal
	 * integer. An optional second command-line argument may be the seed to the
	 * random number generator.
	 *
	 * Output is written to standard output, unless there is an error.
	 *
	 * This generates approximately 'ts' random numbers, which are divided into
	 * ranges of size n, and sorted. Thus there are (ts/n) invocations of the
	 * sorting algorithm under test. So, we can compute an average time per
	 * invocation, which is what we print. Since Java is run in a virtual
	 * machine with a just-in-time compiler and optimizer, the first few
	 * invocations might not be as fast as the later ones, and so the first 10%
	 * of the invocations are not timed; they are there to "warm up" the
	 * optimizer. Only the last 90% of the invocations are timed. Also we
	 * trigger the garbage collector just before starting the timing, hopefully
	 * to forestall it slowing down the timed part of the experiment.
	 *
	 * Correct behavior is checked by comparing each sorting class's output to a
	 * reference result from java.utils.Arrays.sort, which we trust to perform
	 * correct sorting. If the results disagree, this will print an error
	 * message and return nonzero status.
	 */
	// Changed the main to class name
	public Sorting(String[] argv) {
		// User must invoke the program with at least one command-line
		// argument, the array size, i.e., the number of integers in
		// the sequence, which need to be put into sorted order.
		if (0 == argv.length) {
			System.err.println("Error:  invocation without arguments");
			System.exit(1);
		}

		// Two arguments is also OK. Three or more is too many.
		// if (argv.length > 2) {
		// System.err.println("Error: too many arguments");
		// System.exit(1);
		// }

		final int n = Integer.parseInt(argv[0]), // test array length
				ts = 5000000, // memory footprint
				reps = ts / n, // # sorting invocations
				trigger = reps / 10; // warm-up invocations

		int c = Integer.parseInt(argv[1]);

		// Optional random number seed
		final int seed = argv.length > 1 ? Integer.parseInt(argv[1]) : 12345;

		// Exit if there is no work to do.
		if (n <= 0)
			System.exit(0);

		// Warn the user if very few warmup runs are planned.
		if (trigger < 10)
			System.err.println("Warning:  only " + trigger + " warmup runs are planned, which is small.");

		// Set up the sorting algorithms
		final int algorithm_count = 1;
		IntegerSort[] methods = new IntegerSort[algorithm_count];
		methods[0] = new ShafferSort();
		assert methods.length == algorithm_count;

		// storage for test data and timing data
		int[][] data = new int[reps][n], answerkey = new int[reps][n];

		// Create a new float array to record times
		float[] record = new float[6];

		// Loop through all algorithms
		for (int alg = 0; alg < algorithm_count; ++alg) {

			// generate random data, and sort it using Java's own sort alg
			java.util.Random gen = new java.util.Random(seed);
			for (int j = 0; j < reps; ++j) {
				for (int i = 0; i < n; ++i)
					answerkey[j][i] = data[j][i] = gen.nextInt();
				java.util.Arrays.sort(answerkey[j]);
			}

			// Now run the algorithm we wish to test, 'reps' times.
			long starttime = 0;
			for (int j = 0; j < reps; ++j) {

				/*
				 * Above, note that trigger = reps/10, which means that 10% of
				 * the experiment is used to "warm up" the JIT compiler, and the
				 * remainer is used for testing.
				 */
				if (j == trigger) {
					java.lang.System.gc();
					java.lang.System.runFinalization();

					// Start the clock!
					starttime = java.lang.System.currentTimeMillis();
				}

				// Run the specified sorting method!
				methods[alg].sort(data[j],c);
			}

			// Stop the clock!
			long endtime = java.lang.System.currentTimeMillis();

			// Check the output for accuracy
			for (int j = 0; j < reps; ++j)
				if (!java.util.Arrays.equals(data[j], answerkey[j])) {
					System.err.println(
							"incorrect sorting result, alg = " + alg + ", size = " + n + ", repetition = " + j);
					System.exit(1);
				}

			// Print the time measurement for this method.
			System.out.print(
					"\t " + methods[alg].name() + " time (ms): " + (endtime - starttime) / (float) (reps - trigger));

			// Record times
			record[alg + 1] = (endtime - starttime) / (float) (reps - trigger);
		}

		// Print the size of the test arrays:
		System.out.println("\t size: " + n);

		// Record size
		record[0] = n;

		// Put records to csv file
		try {
			FileWriter fw = new FileWriter("run_01.csv", true);
			StringBuilder sb = new StringBuilder();

			// Size
			sb.append(record[0]);
			sb.append(",");
			// Zeep Sort
			sb.append(record[1]);
			sb.append("\n");

			fw.write(sb.toString());
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// The main method will run the Sorting 500 times for different size
	public static void main(String[] args) {
		for (int i = 0; i <= 300; i = i + 3) {
			String[] string = new String[2];
			string[0] = "10000";
			string[1] = "" + i;
			Sorting sort = new Sorting(string);
		}
	}
}
