package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	private static final String fileName = "commands.txt";
	static BufferedReader keywordReader;
	static BufferedWriter keywordWriter;
	private static String allCommands;

	static BufferedReader dictionaryFile;

	public static ArrayList<String> dictionary;

	public FileManager() throws IOException {

		keywordReader = new BufferedReader(new FileReader(fileName));

		ArrayList<String> commands = new ArrayList<String>();
		commands.clear();
		String word = "";

		word = keywordReader.readLine();

		while (word != null) {
			commands.add(word);
			writeFiles(word);
			word = keywordReader.readLine();
		}

		keywordReader.close();

		/*
		 * for (String str : commands) { Commands.interpretCommand(str); }
		 */

		keywordWriter = new BufferedWriter(new FileWriter(fileName));
		writeFiles();

		// Dictionary
		dictionaryFile = new BufferedReader(new FileReader("words.txt"));
		dictionary = new ArrayList<String>();
		dictionary.clear();

		word = dictionaryFile.readLine();

		while (word != null) {
			if (word.length() > 4) {
				dictionary.add(word);
			}
			word = dictionaryFile.readLine();
		}

	}

	public static void writeFiles() throws IOException {
		if (keywordWriter != null) {
			keywordWriter.close();
		}
		keywordWriter = new BufferedWriter(new FileWriter(fileName));
		keywordWriter.write(allCommands);
		keywordWriter.flush();

	}

	public static void writeFiles(String str) throws IOException {
		if (allCommands != null) {
			allCommands += "\n" + str;
		} else {
			allCommands = str;
		}
		writeFiles();

	}

	public static String getWord() {
		return dictionary.get((int) (Math.random() * dictionary.size()));
	}

}
