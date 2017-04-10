public class HashTable implements Proj04Dictionary {
	public void insert(int key, String data) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
	}

	public void delete(int key) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
	}

	public String search(int key) {
		throw new RuntimeException("TODO");
	}

	public Integer[] getKeys() {
		throw new RuntimeException("TODO");
	}

	public int getSuccessor(int key) throws IllegalArgumentException {
		throw new RuntimeException("TODO");
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
