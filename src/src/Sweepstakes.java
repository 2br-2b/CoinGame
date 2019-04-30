package src;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Sweepstakes extends ListenerAdapter {

	private boolean inRace = false;
	private final static int ODDS = 100;
	private Upgrade u;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot())
			return;
		if (inRace) {
			if (e.getMessage().getContentRaw().contains("<@568248186741981195>")) {
				inRace = false;
				Store.giveUserUpgrade(e.getAuthor().getId(), u);
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " won a " + u.getNamePrefix() + "!").queue();
			}
		} else {
			if (Math.random() > 0.001) {
				u = Store.randomStuff[(int) (Math.random() * Store.randomStuff.length)];
			} else {
				u = Store.pastUpgrades[(int) (Math.random() * Store.pastUpgrades.length)];
			}

			if (Math.random() < 1 / ODDS) {
				// Start a race
				if (Math.random() > 0.5) {
					e.getChannel().sendMessage("Next person to @mention me gets a " + u.getNamePrefix() + "!").queue();
					inRace = true;

				} else {
					// Sweepstakes
					e.getChannel().sendMessage(
							"Sweepstakes!  " + e.getAuthor().getAsMention() + " won a " + u.getNamePrefix() + "!")
							.queue();
					Store.giveUserUpgrade(e.getAuthor().getId(), u);
				}
			}
		}
	}

}
