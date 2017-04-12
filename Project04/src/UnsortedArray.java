import java.util.ArrayList;

public class UnsortedArray implements Proj04Dictionary {

	private ArrayList<Element> unsortedArray;

	public UnsortedArray() {
		unsortedArray = new ArrayList<Element>();
	}

	public void insert(int key, String data) throws IllegalArgumentException {
		if (data == null || search(key) != null) {
			throw new IllegalArgumentException();
		}
		this.unsortedArray.add(new Element(key, data));
	}

	public void delete(int key) throws IllegalArgumentException {
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}

		int found = 0;
		for (int i = 0; i < unsortedArray.size(); i++) {
			if (unsortedArray.get(i).key == key) {
				found = i;
				break;
			}
		}
		unsortedArray.remove(found);
	}

	public String search(int key) {
		for (int i = 0; i < this.unsortedArray.size(); i++) {
			if (unsortedArray.get(i).key == key) {
				return unsortedArray.get(i).data;
			}
		}
		return null;
	}

	public Integer[] getKeys() {
		Integer[] array = new Integer[this.unsortedArray.size()];
		int i = 0;
		for (Element element : unsortedArray) {
			array[i] = element.key;
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

		public Element(int key, String data) {
			this.key = key;
			this.data = data;
		}
	}

}
