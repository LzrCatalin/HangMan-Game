import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Test class
 */
public class testClass {

	/**
	 * Test for Regex.java
	 */
	@Test
	/**
	 * Test for Regex.java constructor
	 */
	public void testInsert() {
		Trie trie = new Trie();
		TrieNode.insert(trie.getRoot(), "test");
		TrieNode.insert(trie.getRoot(), "test");
		TrieNode.insert(trie.getRoot(), "test");
	}

	@Test
	/**
	 * Test TrieNode.java for search function
	 */
	public void testSearch() {
		Trie trie = new Trie();
		TrieNode.insert(trie.getRoot(), "test");
		TrieNode.search(trie.getRoot(), "test");
	}

	@Test
	/**
	 * Test for TrieNode.java isLeafNode function
	 */
	public void testIsLeafNode() {
		Trie trie = new Trie();
		TrieNode.insert(trie.getRoot(), "test");
		TrieNode.isLeafNode(trie.getRoot());
	}

	@Test
	/**
	 * Test for TrieNode.java display function
	 */
	public void testDisplayTree() {
		Trie trie = new Trie();
		TrieNode.insert(trie.getRoot(), "test");
		TrieNode.insert(trie.getRoot(), "teste");
		TrieNode.insert(trie.getRoot(), "acasa");
		TrieNode.insert(trie.getRoot(), "maine");
		TrieNode.displayTree(trie.getRoot(), new char[100], 0);
	}

	@Test
	/**
	 * Test for TrieNode.java store function
	 */
	public void testStoreTreeWords() {
		Trie trie = new Trie();
		ArrayList<String> wordsList = new ArrayList<>();
		TrieNode.insert(trie.getRoot(), "test");
		TrieNode.storeTreeWords(trie.getRoot(), new char[100], 0, wordsList);
	}

	@Test
	/**
	 * Test for TrieNode.java display by length function
	 */
	public void testDisplayWordsByLength() {
		Trie trie = new Trie();
		TrieNode.insert(trie.getRoot(), "test");
		TrieNode.insert(trie.getRoot(), "teste");
		TrieNode.insert(trie.getRoot(), "acasa");
		TrieNode.insert(trie.getRoot(), "maine");
		TrieNode.displayWordsByLength(trie.getRoot(), new char[100], 0, 4);
	}

	@Test
	/**
	 * Test for TrieNode.java pick random word function
	 */
	public void testPickRandomWord() {
		Trie trie = new Trie();
		TrieNode.insert(trie.getRoot(), "test");
		TrieNode.insert(trie.getRoot(), "teste");
		System.out.println(TrieNode.pickRandomWord(trie.getRoot(), 4));
	}
}
