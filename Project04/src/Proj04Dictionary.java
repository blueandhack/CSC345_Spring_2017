// Proj04Dictionary - interface used in CSC 345 Project 4.
//
// This interface representing a Dictionary ADT.  For simplicity, our keys are
// type int, and the satellite data are always (non-null) Strings.
// This (key, satellite data) pair is called a * record *.
//
// The constructor for each class that implements this interface should create
// an empty data structure.  We then insert, delete, search, etc.
//
// See the comments below for the formal definitions of each function.
//
// Authors: Russ Lewis, Andrew Predoehl


public interface Proj04Dictionary
{
	// insert(int,String)
	//
	// This function inserts a new record into the dictionary.
	// Any key is allowed.  However, the String must always be non-null.
	//
	// This function throws IllegalArgumentException if either of the
	// following are true:
	//     - key already exists in the data structure
	//     - String is null
	void insert(int key, String data)
		throws IllegalArgumentException;


	// delete(int)
	//
	// This function deletes the key from the dictionary.
	//
	// This function throws IllegalArgumentException if:
	//     - the key does not exist in the data structure
	void delete(int key)
		throws IllegalArgumentException;


	// String search(int)
	//
	// This function searches the data structure for the key.  If the key
	// is found, then this returns the String associated with it; if the
	// key is not found, it returns null.
	String search(int key);


	// Integer[] getKeys()
	//
	// This function generates an exhaustive list of all keys in the
	// data structure, and returns them as an array.  A new array may be
	// allocated for each call; do *NOT* reuse the same array over and
	// over.
	//
	// NOTE: The keys in the array are *NOT* necessarily sorted.
	//
	// IMPLEMENTATION HINT: While you must return an array at the end,
	// you may use some other form - such as an ArrayList - as a
	// temporary variable as you build the retval.  
	// Then you can convert an ArrayList<Integer> to Integer[] with
	// the toArray() method.
	Integer[] getKeys();

	// int getSuccessor(int)
	//
	// Searches the tree for the given key, and then returns the
	// successor key in the data structure - that is, the next key in a
	// sorted list of the keys.
	//
	// This function throws IllegalArgumentException if either of the
	// following are true:
	//     - the key does not exist
	//     - the key does not have any successor
	int getSuccessor(int key)
		throws IllegalArgumentException;
}

