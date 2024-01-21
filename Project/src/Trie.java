
/**
 * Trie class
 */
public class Trie {
	TrieNode root;

	/**
	 * Constructor
	 */
	public Trie() {
		this.root = new TrieNode();
	}

	TrieNode getRoot() {
		return root;
	}

	public void setRoot(TrieNode root) {
		this.root = root;
	}
}


