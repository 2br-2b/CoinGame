package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DictionaryManager {

	static BufferedReader dictionaryFile;

	public static ArrayList<String> dictionary;

	public DictionaryManager() throws IOException {
		// Dictionary
		dictionaryFile = new BufferedReader(new FileReader("words.txt"));
		dictionary = new ArrayList<String>();
		dictionary.clear();

		String word = dictionaryFile.readLine();

		while (word != null) {
			if (word.length() > 4) {
				dictionary.add(word);
			}
			word = dictionaryFile.readLine();
		}

	}

	public static String getWord() {
		return dictionary.get((int) (Math.random() * dictionary.size()));
	}

}
