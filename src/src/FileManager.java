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

	public FileManager() throws IOException {

		keywordReader = new BufferedReader(new FileReader(fileName));

		ArrayList<String> commands = new ArrayList<String>();
		commands.clear();
		String word = "";

		word = keywordReader.readLine();

		while (word != null) {
			commands.add(word);
			word = keywordReader.readLine();
		}

		keywordReader.close();

		for (String str : commands) {
			Commands.interpretCommand(str);
		}

		keywordWriter = new BufferedWriter(new FileWriter(fileName));
		writeFiles();

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

}
