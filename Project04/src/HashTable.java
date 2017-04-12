import java.util.LinkedList;

public class HashTable implements Proj04Dictionary {

	private LinkedList<LinkedList<HashNode>> table;
	private int tableSize = 10;
	private int count;

	public HashTable() {
		table = new LinkedList<LinkedList<HashNode>>();
		for (int i = 0; i < tableSize; i++) {
			table.add(new LinkedList<HashNode>());
		}
		count = 0;
	}

	private int hash(int key) {
		return Math.abs(key) % tableSize;
	}

	public void insert(int key, String data) throws IllegalArgumentException {
		if (search(key) != null) {
			throw new IllegalArgumentException();
		}
		int code = this.hash(key);
		table.get(code).add(new HashNode(key, data));
		count++;
	}

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

	public int getSuccessor(int key) throws IllegalArgumentException {
		if (this.search(key) == null) {
			throw new IllegalArgumentException();
		}

		Integer[] keys = this.getKeys();
		boolean found = false;
		int successor = key;

		for (int j = 0; j < keys.length; ++j) {
			if (key < keys[j]) {
				if (!found) {
					successor = keys[j];
					found = true;
				} else
					successor = Math.min(successor, keys[j]);
			}
		}

		if (!found) {
			throw new IllegalArgumentException();
		}

		return successor;
	}

	private class HashNode {
		private int key;
		private String data;

		public HashNode(int key, String data) {
			this.key = key;
			this.data = data;
		}

	}
}
