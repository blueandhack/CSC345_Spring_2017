public class UnsortedArray implements Proj04Dictionary {

	private Element first;
	private int size;

	public UnsortedArray() {
		first = null;
		size = 0;
	}

	public void insert(int key, String data) throws IllegalArgumentException {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		if (search(key) != null) {
			throw new IllegalArgumentException();
		}
		Element ref = first;
		if (ref == null) {
			ref = new Element(key, data);
			size++;
		} else {
			while (ref.next != null) {
				ref = ref.next;
			}
			ref.next = new Element(key, data);
			size++;
		}
	}

	public void delete(int key) throws IllegalArgumentException {
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}

		Element ref = first;

	}

	public String search(int key) {
		Element ref = first;
		if (first == null) {
			return null;
		}
		while (ref.next != null) {
			if (ref.key == key) {
				return ref.data;
			}
		}
		return null;
	}

	public Integer[] getKeys() {
		Integer[] array = new Integer[size];
		int i = 0;
		return array;
	}

	public int getSuccessor(int key) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
	}

	private class Element {
		int key;
		String data;
		Element next;

		public Element(int key, String data) {
			key = key;
			data = data;
			next = null;
		}
	}

}
