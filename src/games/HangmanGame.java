package games;

import java.util.ArrayList;

public class HangmanGame {

	private static char blankChar = '?';

	// the word
	private String word;

	// the word as a list
	private char[] wordList;

	// the word as a list that they see
	private char[] visible;

	private int livesLeft;

	private ArrayList<Character> lettersToGuess;
	private ArrayList<Character> lettersGuessed;

	public HangmanGame(String word, int lives) {
		this.word = word;
		wordList = word.toCharArray();
		visible = new char[wordList.length];
		for (int i = 0; i < visible.length; i++) {
			visible[i] = blankChar;
		}
		livesLeft = lives;
		lettersToGuess = new ArrayList<Character>();
		char[] allLetters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		for (char l : allLetters) {
			lettersToGuess.add(l);
		}
		lettersGuessed = new ArrayList<Character>();
		lettersGuessed.clear();
	}

	public HangmanGame(String word) {
		this(word, 5);
	}

	public HangmanGame() {
		this("qwerty");
	}

	public int getLives() {
		return livesLeft;
	}

	public String getVisible() {
		String str = "";
		for (char l : visible) {
			str += l;
		}
		return str;
	}

	public String getGuessable() {
		return lettersToGuess.toString();
	}

	public String guessLetter(String l) {
		// System.out.println("guessLetter " + l);

		l = l.toLowerCase();
		if (!lettersToGuess.contains(l.charAt(0))) {
			return "Invalid character.  Please try again!";
		}

		if (lettersGuessed.contains(l.charAt(0))) {
			return "You already guessed that!\n`" + getVisible() + "`";
		}
		lettersGuessed.add(l.charAt(0));

		// System.out.println("a");
		// System.out.println(lettersToGuess.remove(l.charAt(0)));
		// System.out.println("b");

		boolean found = false;
		for (int i = 0; i < wordList.length; i++) {
			if (Character.toString(wordList[i]).equals(l)) {
				visible[i] = l.charAt(0);
				found = true;
			}
		}

		if (found) {
			// System.out.println("found");
			if (getVisible().contains(Character.toString(blankChar))) {
				return "`" + l + "` was found!\n`" + getVisible() + "`";
			} else {
				return "\u0006`" + l + "` was found!\nYou win!\n`" + getVisible() + "`";
			}
		} else {
			livesLeft--;
			// System.out.println("nope!");
			if (livesLeft < 1) {
				return "\u0000`" + l + "` was not found!\nYou are out of lives!\nThe word was `" + word + "`";
			} else {
				return "`" + l + "` was not found!\nLives left: `" + livesLeft + "`\n`" + getVisible() + "`";
			}

		}

	}

}
