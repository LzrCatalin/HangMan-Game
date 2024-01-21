import java.util.ArrayList;
import java.util.Random;

/**
 * TrieNode class
 */
public class TrieNode {
	TrieNode[] children;
	 boolean isEndOfWord;

	/**
	 * Constructor
	 */
	public TrieNode() {
		// 26 strings stored because there are 26 letters in english vocabulary
		this.children = new TrieNode[26];
		this.isEndOfWord = false;
	}

	/**
	 * Insert function
	 * @param key = word that we want to insert
	 */
	public static void insert(TrieNode root, String key) {
		// Sending the root node to a current node so we can use it further
		TrieNode currentNode = root;

		// Iterate through the length of the word
		for (int i = 0; i < key.length(); i++) {
			// Ensure that every letter is a lowercase
			if (!Character.isLowerCase(key.charAt(i))) {
				System.out.println("Only lowercase letters (a-z) are allowed in the Trie.");
				return;
			}

			// Get the exact index of the inserted char
			int index = key.charAt(i) - 'a';

			// If inserted char is null we create a new node for it
			if (currentNode.children[index] == null) {
				currentNode.children[index] = new TrieNode();
			}

			// If inserted char is not null, we move the current node to the founded node with the given char
			currentNode = currentNode.children[index];
		}

		// Mark last node as leaf
		currentNode.isEndOfWord = true;
	}

	/**
	 * Search function
	 * @param key = word that we want to search
	 * @return true if word is found, false otherwise
	 */
	public static boolean search(TrieNode root, String key) {
		// Create a current node to move through the tree
		TrieNode current = root;

		// Iterate through the word letters
		for (int i = 0; i < key.length(); i++) {
			int index = key.charAt(i) - 'a';

			// If checking letter is in tree
			if (current.children[index] == null) {
				// Letter not in tree
				return false;
			}

			// Move down to tree
			current = current.children[index];
		}

		return current.isEndOfWord;
	}

	public static boolean isLeafNode(TrieNode root) {
		return root.isEndOfWord;
	}

	/**
	 * Display function for displaying each word from trie tree
	 * @param root = root of the trie tree
	 * @param buff = buffer for storing each character
	 * @param level = level of the trie tree
	 */
	public static void displayTree(TrieNode root, char[] buff, int level) {
		//Buff - buffer for storing each character
		// Trie tree is not empty
		if (isLeafNode(root)) {
			// Display each word recursively
			System.out.println(new String(buff, 0, level));
		}

		// 26 characters in english alphabet
		for (int j = 0; j < 26; j++) {
			// If exists child
			if(root.children[j] != null) {
				// Add the found character in buffer
				buff[level] = (char) (j + 'a');
				displayTree(root.children[j], buff, level + 1);
			}
		}
	}

	/**
	 * Store function for storing each word from trie tree in arraylist
	 * @param root = root of the trie tree
	 * @param buff = buffer for storing each character
	 * @param level = level of the trie tree
	 * @param wordsList = arraylist of words
	 */
	public static void storeTreeWords(TrieNode root, char[] buff, int level, ArrayList<String> wordsList) {
		// Trie tree is not empty
		if (isLeafNode(root)) {
			// Add each word in arraylist
			wordsList.add(String.valueOf(new String(buff, 0, level)));
		}

		// 26 characters in english alphabet
		for (int j = 0; j < 26; j++) {
			// If exists child
			if(root.children[j] != null) {
				// Add the found character in buffer
				buff[level] = (char) (j + 'a');
				storeTreeWords(root.children[j], buff, level + 1, wordsList);
			}
		}
	}

	/**
	 * Display function for displaying each word from trie tree based on length
	 * @param root = root of the trie tree
	 * @param buff = buffer for storing each character
	 * @param level = level of the trie tree
	 * @param length = length of the word
	 */
	public static void displayWordsByLength(TrieNode root, char[] buff, int level, int length) {
		// Trie tree is not empty
		if (isLeafNode(root) && level == length) {
			// Display each word recursively
			System.out.println(new String(buff, 0, level));
		}

		// 26 characters in english alphabet
		for (int j = 0; j < 26; j++) {
			// If exists child
			if(root.children[j] != null) {
				// Add the found character in buffer
				buff[level] = (char) (j + 'a');
				displayWordsByLength(root.children[j], buff, level + 1, length);
			}
		}
	}

	/**
	 * Store function for storing each word from trie tree in arraylist based on length
	 * @param root = root of the trie tree
	 * @param buff = buffer for storing each character
	 * @param level = level of the trie tree
	 * @param wordsList = arraylist of words
	 */
	public static void storeTreeWordsByLength(TrieNode root, char[] buff, int level, int length, ArrayList<String> wordsList) {
		// Trie tree is not empty
		if (isLeafNode(root) && level == length) {
			// Add each word in arraylist
			wordsList.add(String.valueOf(new String(buff, 0, level)));
		}

		// 26 characters in english alphabet
		for (int j = 0; j < 26; j++) {
			// If exists child
			if(root.children[j] != null) {
				if (level < length) {
					// Add the found character in buffer
					buff[level] = (char) (j + 'a');
					// Recursive call
					storeTreeWordsByLength(root.children[j], buff, level + 1, length, wordsList);
					buff[level] = '\0';
				}
			}
		}
	}

	/**
	 * Display function for displaying each word from trie tree based on length and regex
	 * @param root = root of the trie tree
	 * @param buff = buffer for storing each character
	 * @param level = level of the trie tree
	 * @param regex = regex object
	 */
	public static void displayWordsByRegex(TrieNode root, char[] buff, int level, Regex regex) {
		// Trie tree is not empty
		if (isLeafNode(root) && level == regex.getWordLength()) {
			// Display each word recursively
			System.out.println(new String(buff, 0, level));
		}

		// 26 characters in english alphabet
		for (int j = 0; j < 26; j++) {
			// If exists child
			if(root.children[j] != null) {
				// Get child value and transform it to string
				String currentLetter = Character.toString((char) (j + 'a'));
				// Check if child string is inside letters for the level of tree
				if (level < regex.wordLength && regex.getLettersAtIndex(level).contains(currentLetter)) {
					// Add letter to buff
					buff[level] = (char) (j + 'a');
					// Recursive call
					displayWordsByRegex(root.children[j], buff, level + 1, regex);
				}
			}
		}
	}

	/**
	 * Store function for storing each word from trie tree in arraylist based on length and regex
	 * @param root = root of the trie tree
	 * @param buff = buffer for storing each character
	 * @param level = level of the trie tree
	 * @param wordsList = arraylist of words
	 * @param regex = regex object
	 */
	public static void storeWordsByRegex(TrieNode root, char[] buff, int level, ArrayList<String> wordsList, Regex regex) {
		// Trie tree is not empty
		if (isLeafNode(root) && level == regex.wordLength) {
			// Add each word in arraylist
			wordsList.add(String.valueOf(new String(buff, 0, level)));
		}

		// 26 characters in english alphabet
		for (int j = 0; j < 26; j++) {
			// If exists child
			if(root.children[j] != null) {
				// Get child value and transform it to string
				String currentLetter = Character.toString((char) (j + 'a'));
				// Check if child string is inside letters for the level of tree
				if (level < regex.wordLength && regex.getLettersAtIndex(level).contains(currentLetter)) {
					// Add letter to buff
					buff[level] = (char) (j + 'a');
					// Recursive call
					storeWordsByRegex(root.children[j], buff, level + 1, wordsList, regex);
				}
			}
		}
	}

	/**
	 * Pick random word from trie tree
	 * @param root = root of the trie tree
	 * @return randomly picked word
	 */
	public static String pickRandomWord(TrieNode root, int length) {
		// Check if root is empty
		if (root == null) {
			return null;
		}

		// Create an ArrayList to store words of the specified length
		ArrayList<String> wordsList = new ArrayList<>();
		char[] buff = new char[length];

		// Call the store function to populate wordsList
		TrieNode.storeTreeWordsByLength(root, buff, 0, length, wordsList);

		// Check if list of words is empty
		if (wordsList.isEmpty()) {
			System.out.printf("There are no words with length [%d]\n", length);
		}

		// Use Random to pick a random index from wordsList
		Random random = new Random();
		int randomIndex = random.nextInt(wordsList.size());

		// Return the randomly picked word
		return wordsList.get(randomIndex);
	}

	/**
	 * Pick random word from trie tree based on length
	 * @param root = root of the trie tree
	 * @param length = length of the word
	 * @return randomly picked word
	 */
	public static String pickRandomWordByRegex(TrieNode root, int length, Regex regex) {
		// Check if root is empty
		if (root == null) {
			return null;
		}

		// Create an ArrayList to store words of the specified length
		ArrayList<String> wordsList = new ArrayList<>();
		char[] buff = new char[length];

		// Call the store function to populate wordsList
		TrieNode.storeWordsByRegex(root, buff, 0, wordsList, regex);

		// Check if list of words is empty
		if (wordsList.isEmpty()) {
			System.out.printf("There are no words with length [%d]\n", length);
		}

		// Use Random to pick a random index from wordsList
		Random random = new Random();
		int randomIndex = random.nextInt(wordsList.size());

		// Return the randomly picked word
		return wordsList.get(randomIndex);
	}
}
