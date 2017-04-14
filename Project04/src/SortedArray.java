
/*
 * SortedArray.java
 *
 * CSc 345 Spring 2017 - Project04
 * 
 * Author: Yujia Lin
 *
 * ---
 * The class is a data structure that is sorted array
 */
import java.util.ArrayList;

public class SortedArray implements Proj04Dictionary {

	// the instance variable as an empty array
	private ArrayList<Element> sortedArray;

	// constructor
	public SortedArray() {
		sortedArray = new ArrayList<Element>();
	}

	// insert an element
	public void insert(int key, String data) throws IllegalArgumentException {
		if (data == null || search(key) != null) {
			throw new IllegalArgumentException();
		}
		// find right position because the array is sorted array
		for (int i = 0; i < sortedArray.size(); i++) {
			if (sortedArray.get(i).key > key) {
				sortedArray.add(i, new Element(key, data));
				return;
			}
		}
		// if all of elements less than the element, then add it to end
		sortedArray.add(new Element(key, data));
	}

	// delete an element by key
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

	// search an element by key
	public String search(int key) {
		int found = binarySearch(sortedArray, 0, sortedArray.size() - 1, key);
		if (found != -1) {
			return sortedArray.get(found).data;
		} else {
			return null;
		}
	}

	// the helper method use binary search algorithm
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

	// get keys
	public Integer[] getKeys() {
		Integer[] array = new Integer[this.sortedArray.size()];
		int i = 0;
		for (Element element : sortedArray) {
			array[i] = element.key;
			i++;
		}
		return array;
	}

	// get successor
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

	// the private class is element
	private class Element {
		private int key;
		private String data;

		public Element(int key, String data) {
			this.key = key;
			this.data = data;
		}
	}
}
