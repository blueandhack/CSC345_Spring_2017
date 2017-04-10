
public class SortedArray implements Proj04Dictionary {

	private Element first;
	private int size;

	public SortedArray() {
		first = null;
		size = 0;
	}

	public void insert(int key, String data) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
	}

	public void delete(int key) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
	}

	public String search(int key) {
		Element ref = first;
		if (ref == null) {
			return null;
		}
		Integer[] keys = this.getKeys();
		return "";
	}

	public Integer[] getKeys() {
		Integer[] array = new Integer[size];
		int i = 0;
		Element ref = first;
		while (ref != null) {
			array[i] = ref.key;
			i++;
		}
		return array;
	}

	public int getSuccessor(int key) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
	}

	private class Element {
		private int key;
		private String data;
		private Element next;

		public Element(int key, String data) {
			key = key;
			data = data;
			next = null;
		}
	}
}
