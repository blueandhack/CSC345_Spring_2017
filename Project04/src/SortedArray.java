import java.util.ArrayList;

public class SortedArray implements Proj04Dictionary {

	private ArrayList<Element> sortedArray;

	public SortedArray() {
		sortedArray = new ArrayList<Element>();
	}

	public void insert(int key, String data) throws IllegalArgumentException {
		if (data == null || search(key) != null) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < sortedArray.size(); i++) {
			if (sortedArray.get(i).key > key) {
				sortedArray.add(i, new Element(key, data));
				return;
			}
		}
		sortedArray.add(new Element(key, data));
	}

	public void delete(int key) throws IllegalArgumentException {
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}
		int found = 0;
		for (int i = 0; i < sortedArray.size(); i++) {
			if (sortedArray.get(i).key == key) {
				found = i;
				break;
			}
		}
		sortedArray.remove(found);
	}

	public String search(int key) {
		int found = binarySearch(sortedArray, 0, sortedArray.size() - 1, key);
		if (found != -1) {
			return sortedArray.get(found).data;
		} else {
			return null;
		}
	}

	private int binarySearch(ArrayList<Element> arr, int start, int end, int khey) {
		if (start > end)
			return -1;
		int mid = start + (end - start) / 2;
		if (arr.get(mid).key > khey)
			return binarySearch(arr, start, mid - 1, khey);
		if (arr.get(mid).key < khey)
			return binarySearch(arr, mid + 1, end, khey);
		return mid;
	}

	public Integer[] getKeys() {
		Integer[] array = new Integer[this.sortedArray.size()];
		int i = 0;
		for (Element element : sortedArray) {
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

	private class Element {
		private int key;
		private String data;

		public Element(int key, String data) {
			this.key = key;
			this.data = data;
		}
	}
}
