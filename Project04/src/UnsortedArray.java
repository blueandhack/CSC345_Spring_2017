
/*
 * UnsortedArray.java
 *
 * CSc 345 Spring 2017 - Project04
 * 
 * Author: Yujia Lin
 *
 * ---
 * The class is a data structure that is unsorted array
 */

import java.util.ArrayList;

public class UnsortedArray implements Proj04Dictionary {

	// the instance variable as an empty array
	private ArrayList<Element> unsortedArray;

	// constructor
	public UnsortedArray() {
		unsortedArray = new ArrayList<Element>();
	}

	// insert an element
	public void insert(int key, String data) throws IllegalArgumentException {

		// check the key
		if (data == null || search(key) != null) {
			throw new IllegalArgumentException();
		}
		this.unsortedArray.add(new Element(key, data));
	}

	// delete an element by key
	public void delete(int key) throws IllegalArgumentException {

		// check the key
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}

		// get index
		int found = 0;
		for (int i = 0; i < unsortedArray.size(); i++) {
			if (unsortedArray.get(i).key == key) {
				found = i;
				break;
			}
		}
		unsortedArray.remove(found);
	}

	// search an element by key
	public String search(int key) {
		for (int i = 0; i < this.unsortedArray.size(); i++) {
			if (unsortedArray.get(i).key == key) {
				return unsortedArray.get(i).data;
			}
		}
		return null;
	}

	// get keys
	public Integer[] getKeys() {
		Integer[] array = new Integer[this.unsortedArray.size()];
		int i = 0;
		for (Element element : unsortedArray) {
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

		// find best successor in keys
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
