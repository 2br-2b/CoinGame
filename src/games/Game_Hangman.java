package games;

import java.util.HashMap;
import java.util.Set;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.FileManager;
import src.Main;
import src.PointsAdder;

public class Game_Hangman extends Command implements Game {

	private HashMap<String, HangmanGame> games;

	public Game_Hangman() {
		super.name = "hangman";
		String[] a = { "h" };
		super.aliases = a;
		super.help = "plays hangman";
		super.arguments = "letter";
		games = new HashMap<String, HangmanGame>();
	}

	@Override
	protected void execute(CommandEvent event) {

		if (games.containsKey(event.getAuthor().getId())) {
			if (event.getArgs().trim().length() == 1) {
				String str = games.get(event.getAuthor().getId()).guessLetter(event.getArgs().trim());
				if (str.charAt(0) == '\u0000') {
					// loss
					PointsAdder.addCoins(event.getAuthor().getId(), -1000);
					games.remove(event.getAuthor().getId());
				} else if (str.charAt(0) == '\u0006') {
					// win
					// str.length-27 is the length of the word
					PointsAdder.addCoins(event.getAuthor().getId(), 1000 * (str.length() - 27));
					games.remove(event.getAuthor().getId());
				}

				if (games.keySet().size() > 1) {
					event.reply(event.getAuthor().getAsMention() + "\n" + str);
				} else {
					event.reply(str);
				}

			} else if (event.getArgs().trim().equals("word")) {
				event.reply("`" + games.get(event.getAuthor().getId()).getVisible() + "`");
			} else {
				event.reply("Please only guess one letter at a time!");
			}
		} else if (event.getArgs().trim().equals("hard")) {
			int c = 5000;
			if (PointsAdder.payCoins(event.getAuthor().getId(), c)) {
				String word;
				do {
					word = FileManager.getWord();

				} while (word.length() < 10);

				games.put(event.getAuthor().getId(), new HangmanGame(word));
				event.reply("Hard mode activated:\n" + games.get(event.getAuthor().getId()).getVisible());
			} else {
				event.reply("You don't have the " + c + Main.CURRENCY + " you need to play Hard Mode!");
			}
		} else {
			games.put(event.getAuthor().getId(), new HangmanGame(FileManager.getWord()));
			event.reply(games.get(event.getAuthor().getId()).getVisible());
		}

	}

	// @Override
	public Set<String> getUsers() {
		return games.keySet();
	}

}
