import java.util.ArrayList;
import java.util.Collections;


/**
 * Regex class
 */
public class Regex {
	int wordLength;
	ArrayList<String> letters;

	/**
	 * Constructor
	 * @param wordLength = length of the word
	 */
	public Regex(int wordLength) {
		this.wordLength = wordLength;
		// Create an arraylist of strings (empty) with the given length
		this.letters = new ArrayList<>(Collections.nCopies(wordLength, ""));
	}

	/**
	 * Getters and setters
	 */

	public int getWordLength() {
		return wordLength;
	}
	public ArrayList<String> getLetters() {
		return letters;
	}
	public String getLettersAtIndex(int index) {
		return letters.get(index);
	}
	public void setLetterList(int index, String letters) {
		this.letters.set(index, letters);
	}
}
