
package src;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PlayGames extends ListenerAdapter {

	private static MessageChannel c = null;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		String m = e.getMessage().getContentRaw();
		c = e.getChannel();

		try {
			System.out.println(m.substring(0, (Main.PREFIX.length() + 4)));
			if (m.substring(0, (Main.PREFIX.length() + 4)).equals(Main.PREFIX + "play")) {
				String game = m.substring(Main.PREFIX.length() + 5);

				switch (game) {

				case "risk":
					// play ___ game
					break;

				default:
					c.sendMessage("No game called " + game + " found!").queue();
					break;

				}
			}
		} catch (Exception ex) {

		}

	}
}
