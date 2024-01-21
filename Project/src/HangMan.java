import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * HangMan class
 */
public class HangMan {

	/**
	 * Read from file and insert words in trie tree
	 * @param file = file that we want to read from
	 * @param trie = trie tree
	 * @throws IOException = exception for input/output
	 * @throws FileNotFoundException = exception for file not found
	 */
	public static void readAndInsertFromFile (File file, Trie trie) throws IOException, FileNotFoundException {

		if (trie == null || trie.getRoot() == null) {
			System.out.println("Trie is not properly initialized.");
			return;
		}

		ArrayList<String> words = new ArrayList<>();
		Scanner scanner = new Scanner(file);

		// Read each line
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// Add each word into the arraylist of strings
			words.add(line);
		}

		// Iterate through the arraylist of word and insert them in trie tree
		for (String word : words) {
			TrieNode.insert(trie.getRoot(), word);
		}
		scanner.close();
	}

	/**
	 * Generate random letters for hint
	 * @param missedLetters = arraylist of missed letters
	 * @return arraylist of random letters
	 */
	public static ArrayList<String> generateRandomLetters(ArrayList<String> missedLetters) {

		// Create a new arraylist of letters
		Random random = new Random();
		ArrayList<String> randomLetters = new ArrayList<>(missedLetters);

		for (int i = 0; i < 10; i++) {
			char randomLetter = (char) ('a' + random.nextInt(26));

			if (!randomLetters.contains(String.valueOf(randomLetter))) {
				randomLetters.add(String.valueOf(randomLetter));
			}
		}
		Collections.shuffle(randomLetters);

		return randomLetters;
	}

	/**
	 * Display the word
	 * @param word = word that we want to display
	 * @param displayArray = array
	 */
	public static void displayWord(char[] word, int[] displayArray) {
		displayArray[0] = 1;
		for (int i = 0; i < word.length; i++) {
			if (displayArray[i] == 1) {
				System.out.print(word[i] + " ");
			} else {
				System.out.print("_ ");
			}
		}
		System.out.println();
	}

	/**
	 * Display hangman
	 * @param mistakes = number of mistakes
	 */
	public static void displayHangman(int mistakes) {
		System.out.println("Mistakes: " + mistakes);
		switch(mistakes) {
			case 0:
				System.out.print("  +---+\n  |   |\n  |\n  |\n  |\n-----");
				break;
			case 1:
				System.out.print("  +---+\n  |   |\n  |   O\n  |\n  |\n-----");
				break;
			case 2:
				System.out.print("  +---+\n  |   |\n  |   O\n  |   |\n  |\n-----");
				break;
			case 3:
				System.out.print("  +---+\n  |   |\n  |   O\n  |  /|\n  |\n-----");
				break;
			case 4:
				System.out.print("  +---+\n  |   |\n  |   O\n  |  /|\\\n  |\n-----");
				break;
			case 5:
				System.out.print("  +---+\n  |   |\n  |   O\n  |  /|\\\n  |  /\n-----");
				break;
			case 6:
				System.out.print("  +---+\n  |   |\n  |   O\n  |  /|\\\n  |  / \\\n-----");
				break;
			default:
				System.out.print("Hangman figure error");
		}

		System.out.print("\n");
	}

	/**
	 * Play the game
	 * @throws IOException = exception for input/output
	 */
	public static void playGame() throws IOException {
		int mistakes;
		Trie trie = new Trie();
		char[] buff = new char[128];

		///////////////////////////
		//
		// File path
		//
		///////////////////////////
		String pathname = "wordsFile";
		File file = new File(pathname);
		readAndInsertFromFile(file, trie);

		///////////////////////////
		//
		// Keyboard input for length
		//
		///////////////////////////
		Scanner scanner = new Scanner(System.in);
		System.out.println("Insert length of the word:");
		int wordLength = scanner.nextInt();
		scanner.nextLine();


		///////////////////////////
		//
		// Get the random word
		//
		///////////////////////////
		String word = TrieNode.pickRandomWord(trie.getRoot(), wordLength);

		// only print first word letter at the beginning
		int[] displayArray = new int[word.length()];
		for (int i = 0; i < word.length(); i++) {
			displayArray[i] = 0;
		}

		///////////////////////////
		//
		// Create set for hintLetters (used set to remove duplicates from the arraylist)
		//
		///////////////////////////
		// Create an arraylist with letters of the word
		ArrayList<String> missedLetters = new ArrayList<>();
		for (char c : word.toCharArray()) {
			missedLetters.add(String.valueOf(c));
		}

		ArrayList<String> randomLetters = generateRandomLetters(missedLetters);
		// Delete the first letter of the word from hint letters
		randomLetters.remove(String.valueOf(missedLetters.get(0)));
		// Transform arraylist into set to remove duplicates
		Set<String> hintLetters = new LinkedHashSet<String>(randomLetters);

		///////////////////////////
		//
		// Words regex
		//
		///////////////////////////
		Regex regex = new Regex(wordLength);

		///////////////////////////
		//
		// Game loop
		//
		///////////////////////////
		mistakes = 0;
		while (mistakes < 6) {
			System.out.println("=============================================================");
			// Display the current state of the word and missed letters
			displayWord(word.toCharArray(), displayArray);
			System.out.println("Hint letters: " + Arrays.toString(hintLetters.toArray()));

			// Print hangman
			displayHangman(mistakes);

			// Give all possible words hints to user
			System.out.println("All possible solutions: ");
			for (int i = 0; i < word.length(); i++) {
				if (displayArray[i] == 1) {
					regex.setLetterList(i, String.valueOf(word.charAt(i)));    // letter guesses, perfect match
				} else {
					regex.setLetterList(i, String.join("", hintLetters));
				}
			}

			TrieNode.displayWordsByRegex(trie.getRoot(), buff, 0, regex);

			// Ask the user to guess a letter
			System.out.println("Guess a letter:");
			char guess = scanner.nextLine().charAt(0);

			if (hintLetters.contains(String.valueOf(guess))) {
				// Validate letter
				if (word.contains(String.valueOf(guess))) {
					// Update the displayArray with the guessed letter
					for (int i = 0; i < word.length(); i++) {
						if (word.charAt(i) == guess) {
							displayArray[i] = 1;
							hintLetters.remove(String.valueOf(guess));
						}
					}
				} else {
					hintLetters.remove(String.valueOf(guess));
					mistakes++;
				}

				// Check if the word is guessed
				boolean isGuessed = true;
				for (int i = 0; i < word.length(); i++) {
					if (displayArray[i] == 0) {
						isGuessed = false;
						break;
					}
				}

				if (isGuessed) {
					System.out.println("=============================================================");
					System.out.println("Congratulations! You guessed the word: " + word);
					break;
				}
			} else {
				System.out.println("Letter is not in hint letters");
			}
		}

		// final print
		displayHangman(mistakes);

		if (mistakes == 6) {
			System.out.println("=============================================================");
			System.out.println("You have lost! Word to guess: " + word);
		}

		scanner.close();
	}

	/**
	 * Main function
	 * @param args = arguments
	 * @throws IOException = exception for input/output
	 */
	public static void main(String[] args) throws IOException {
		playGame();
	}
}
