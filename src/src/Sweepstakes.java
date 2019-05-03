package src;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Sweepstakes extends ListenerAdapter {

	private boolean inRace = false;
	private final static int ODDS = 100;
	private static int timesRun = (int) (ODDS * Math.random());
	private Upgrade u;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {

		if (inRace) {
			if (e.getAuthor().isBot())
				return;
			if (e.getMessage().getContentRaw().contains("<@568248186741981195>")) {
				inRace = false;
				Store.giveUserUpgrade(e.getAuthor().getId(), u);
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " won a " + u.getNamePrefix() + "!").queue();
			}
		} else {
			timesRun++;

			if (e.getAuthor().isBot())
				return;
			if (Math.random() < (1.0 / ODDS) || timesRun >= ODDS) {

				if (timesRun >= ODDS) {
					u = Main.masterUpgradeList.get((int) (Math.random() * Main.masterUpgradeList.size()));
					timesRun = 0;
				} else {
					u = Store.randomStuff[(int) (Math.random() * Store.randomStuff.length)];
				}

				if (Math.random() > 0.5) {
					// Start a race
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

	public static int getTimes() {
		return timesRun;
	}

}
