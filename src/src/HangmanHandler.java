package src;

import java.util.HashMap;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HangmanHandler extends Game {

	private HashMap<String, HangmanGame> games;

	public HangmanHandler() {
		super.name = "Hangman";
		games = new HashMap<String, HangmanGame>();
	}

	@Override
	public void play(MessageReceivedEvent e) {
		System.out.println("Hangman!");
		String[] messageList = e.getMessage().getContentRaw().split(" ");

		if (games.containsKey(e.getAuthor().getId())) {
			System.out.println(messageList[2].length());
			if (messageList[2].length() == 1) {
				String str = games.get(e.getAuthor().getId()).guessLetter(messageList[2]);
				if (str.charAt(0) == '\u0000') {
					// loss
					PointsAdder.addCoins(e.getAuthor().getId(), -1000);
					games.remove(e.getAuthor().getId());
				} else if (str.charAt(0) == '\u0006') {
					// win
					// str.length-27 is the length of the word
					PointsAdder.addCoins(e.getAuthor().getId(), 1000 * (str.length() - 27));
					games.remove(e.getAuthor().getId());
				}

				e.getChannel().sendMessage(str).queue();

			} else if (messageList[2].equals("word")) {
				e.getChannel().sendMessage("`" + games.get(e.getAuthor().getId()).getVisible() + "`").queue();
			} else if (messageList[2].equals("remain")) {
				e.getChannel().sendMessage(games.get(e.getAuthor().getId()).getGuessable()).queue();
			}
		} else {
			games.put(e.getAuthor().getId(), new HangmanGame(FileManager.getWord()));
			e.getChannel().sendMessage(games.get(e.getAuthor().getId()).getVisible()).queue();
		}

	}

}