
/*
 * HashTable.java
 *
 * CSc 345 Spring 2017 - Project04
 * 
 * Author: Yujia Lin
 *
 * ---
 * The class is a data structure that is hash table
 */

import java.util.LinkedList;

public class HashTable implements Proj04Dictionary {

	// instance variable
	// table can contains all linked list
	private LinkedList<LinkedList<HashNode>> table;
	// table size
	private int tableSize = 1024;
	// how many hash node we have
	private int count;

	// constructor
	public HashTable() {
		table = new LinkedList<LinkedList<HashNode>>();
		for (int i = 0; i < tableSize; i++) {
			table.add(new LinkedList<HashNode>());
		}
		count = 0;
	}

	// hash function can get hash code, then find position at the table
	private int hash(int key) {
		return Math.abs(key) % tableSize;
	}

	// insert hash node to table
	public void insert(int key, String data) throws IllegalArgumentException {
		// check
		if (data == null || search(key) != null) {
			throw new IllegalArgumentException();
		}

		// add hash node to table
		int code = this.hash(key);
		table.get(code).add(new HashNode(key, data));
		count++;

		// resize the table
		if (count / tableSize > 0.75) {
			resize();
		}
	}

	// resize the table
	private void resize() {
		tableSize = tableSize * 2;
		LinkedList<LinkedList<HashNode>> oldTable = table;
		table = new LinkedList<LinkedList<HashNode>>();
		for (int i = 0; i < tableSize; i++) {
			table.add(new LinkedList<HashNode>());
		}
		for (int i = 0; i < oldTable.size(); i++) {
			if (oldTable.get(i).size() != 0) {
				for (int j = 0; j < oldTable.get(i).size(); j++) {
					int code = this.hash(oldTable.get(i).get(j).key);
					table.get(code).add(oldTable.get(i).get(j));
				}
			}
		}
	}

	// delete hash node by key
	public void delete(int key) throws IllegalArgumentException {
		if (search(key) == null) {
			throw new IllegalArgumentException();
		}
		int code = this.hash(key);
		int i;
		for (i = 0; i < table.get(code).size(); i++) {
			if (table.get(code).get(i).key == key) {
				break;
			}
		}
		table.get(code).remove(i);
		count--;
	}

	// search hash node by key
	public String search(int key) {
		int code = this.hash(key);
		HashNode temp = null;
		for (int i = 0; i < table.get(code).size(); i++) {
			if (table.get(code).get(i).key == key) {
				temp = table.get(code).get(i);
				break;
			}
		}
		if (temp == null) {
			return null;
		}
		return temp.data;
	}

	// get keys
	public Integer[] getKeys() {
		Integer[] array = new Integer[count];
		int i = 0;
		for (int j = 0; j < tableSize; j++) {
			for (int k = 0; k < table.get(j).size(); k++) {
				array[i] = table.get(j).get(k).key;
				i++;
			}
		}
		return array;
	}

	// get successor
	public int getSuccessor(int key) throws IllegalArgumentException {
		if (this.search(key) == null) {
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

	// the private class is hash node
	private class HashNode {
		private int key;
		private String data;

		public HashNode(int key, String data) {
			this.key = key;
			this.data = data;
		}

	}
}
