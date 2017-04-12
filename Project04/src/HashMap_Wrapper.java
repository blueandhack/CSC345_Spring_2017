
/**
 * This file demonstrates how to implement the Proj04Dictionary
 * interface.
 * This implementation simply uses Java's HashMap library, so we
 * can expect speedy Dictionary operations, but slow search for
 * successors, and the code here is very short because HashMap
 * is already implemented elsewhere.
 */
import java.util.HashMap;
import java.util.Set;

public class HashMap_Wrapper implements Proj04Dictionary {
	// Private HashMap implements our dictionary
	private HashMap<Integer, String> dict;

	// Default constructor
	public HashMap_Wrapper() {
		// We decline to request an initial capacity or load factor.
		dict = new HashMap<Integer, String>();
	}

	// Insert a record (a key, satellite-data pair) into the
	// dictionary. This should be quick since we are using a
	// HashMap.
	public void insert(int key, String sat) throws IllegalArgumentException {
		if (sat == null)
			throw new IllegalArgumentException();

		// Simultaneous insertion, plus the return value tells us
		// whether the key is currently in use already.
		String oldVal = dict.put(key, sat);

		// Check the results -- if true, the key was already in use.
		if (oldVal != null) {
			// restore the old value, then report an error
			dict.put(key, oldVal);
			throw new IllegalArgumentException();
		}

		// else, we're done.
		return;
	}

	// Remove the given key (silently), or throw an exception if
	// the key is not found in the dictionary.
	public void delete(int key) throws IllegalArgumentException {
		// Look up the given value, and remove it if we find it.
		String oldVal = dict.remove(key);

		// If no satellite data is found, the key was bad.
		if (null == oldVal) {
			throw new IllegalArgumentException();
		}
	}

	public String search(int key) {
		// So easy -- the get method does exactly what we want already.
		return dict.get(key);
	}

	// Also pretty simple -- extract all keys.
	public Integer[] getKeys() {
		return dict.keySet().toArray(new Integer[dict.size()]);
	}

	/**
	 * Find the successor key, laboriously. Everything else about the HashMap is
	 * wonderfully fast, but finding the successor key is painfully slow. Here
	 * comes the pain now.
	 */
	public int getSuccessor(int key) throws IllegalArgumentException {
		if (!dict.containsKey(key))
			throw new IllegalArgumentException();

		Set<Integer> keys = dict.keySet();

		int best = Integer.MAX_VALUE;
		boolean found = false;

		for (Integer i : keys) {
			if (i <= key)
				continue;

			found = true;
			if (i < best)
				best = i;
		}

		if (found == false)
			throw new IllegalArgumentException();

		return best;
	}
}
