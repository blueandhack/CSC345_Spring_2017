import java.util.TreeMap;

public class TreeMap_Wrapper implements Proj04Dictionary {
	private TreeMap<Integer, String> dict;

	public TreeMap_Wrapper() {
		dict = new TreeMap<Integer, String>();
	}

	public void insert(int key, String val) throws IllegalArgumentException {
		if (val == null)
			throw new IllegalArgumentException();

		String oldVal = dict.put(key, val);

		if (oldVal != null) {
			// restore the old value, then report an error
			dict.put(key, oldVal);
			throw new IllegalArgumentException();
		}

		// else, we're done.
		return;
	}

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

	public Integer[] getKeys() {
		return dict.keySet().toArray(new Integer[dict.size()]);
	}

	public int getSuccessor(int key) throws IllegalArgumentException {
		// Throw, if the key is not present.
		if (!dict.containsKey(key))
			throw new IllegalArgumentException();

		Integer succ = dict.ceilingKey(key + 1);
		if (succ == null)
			throw new IllegalArgumentException();

		return succ;
	}
}
