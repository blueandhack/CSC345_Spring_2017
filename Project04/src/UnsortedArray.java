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
		Element last = ref;
		while (ref != null) {
			if (ref.key == key) {
				last = ref.next;
				return;
			}
			last = ref;
			ref = ref.next;
		}
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
		Element ref = first;
		while (ref != null) {
			array[i] = ref.key;
			i++;
		}
		return array;
	}

	public int getSuccessor(int key) throws IllegalArgumentException {
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}
		Integer[] keys = this.getKeys();

		boolean found = false;
		int successor = key;
		for (int i = 0; i < keys.length; i++) {
			if (key < keys[i]) {
				if (!found) {
					successor = keys[i];
					found = true;
				} else {
					successor = Math.min(successor, keys[i]);
				}
			}
		}

		if (!found) {
			throw new IllegalArgumentException();
		}
		return successor;
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
