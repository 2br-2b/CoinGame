
package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PlayGames extends ListenerAdapter {

	private static MessageChannel c = null;

	private static ArrayList<Game> games;

	public PlayGames() {
		games = new ArrayList<Game>();
		games.add(new Lottery());
		games.add(new CoinFlip());
		games.add(new HangmanHandler());
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		String m = e.getMessage().getContentRaw();
		c = e.getChannel();

		if (m.equals(Main.PREFIX + "help play")) {
			String str = "You can play the following games:";
			for (Game g : games) {
				str += "\n\t" + g.getName();
			}
			c.sendMessage(str).queue();
		}

		try {
			if (m.substring(0, (Main.PREFIX.length() + 4)).equals(Main.PREFIX + "play")) {
				String game = m.substring(Main.PREFIX.length() + 5);
				// String nameOf game

				if (game.contains("lotto") || game.contains("lottery")) {
					games.get(0).play(e);
				} else if (game.contains("flip")) {
					games.get(1).play(e);
				} else if (game.contains("hangman")) {
					games.get(2).play(e);
				} else {
					c.sendMessage("No game called " + game + " found!").queue();
					return;
				}

			}
		} catch (Exception ex) {
		}

	}
}
