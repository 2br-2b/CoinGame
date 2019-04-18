
package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PlayGames extends ListenerAdapter {

	private static MessageChannel c = null;

	private static ArrayList<String> peopleInLotto;

	public PlayGames() {
		peopleInLotto = new ArrayList<String>();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		String m = e.getMessage().getContentRaw();
		c = e.getChannel();

		try {

			if (m.substring(0, (Main.PREFIX.length() + 4)).equals(Main.PREFIX + "play")) {
				String game = m.substring(Main.PREFIX.length() + 5);
				// String nameOf game

				switch (game) {

				case "lotto":
				case "lottery":
					playLotto(e);
					break;

				case "math":
					playMath(e);
					break;

				default:
					c.sendMessage("No game called " + game + " found!").queue();
					break;

				}
			}
		} catch (Exception ex) {

		}

	}

	private void playMath(MessageReceivedEvent e) {
		// TODO Auto-generated method stub

	}

	private void playLotto(MessageReceivedEvent e) {
		playLotto(e, 100000);
	}

	private void playLotto(MessageReceivedEvent e, int COST) {
		String authorID = e.getAuthor().getId();

		if (peopleInLotto.contains(authorID)) {
			if (peopleInLotto.size() > 1) {
				String winner = peopleInLotto.get((int) (Math.random() * peopleInLotto.size()));
				e.getChannel().sendMessage("And the winner is…").queue();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				int winnings = (int) (COST * peopleInLotto.size() + COST * Math.random());

				e.getChannel()
						.sendMessage("<@" + winner + ">!\nYou won " + Main.addCommas(winnings) + Main.CURRENCY + "!")
						.queue();
				PointsAdder.addCoins(winner, winnings);
				Store.giveUserUpgrade(winner, new Upgrade("Winning Lottery ticket", COST, 1));

				peopleInLotto.clear();

			} else {
				e.getChannel().sendMessage("There aren't enough people to play the lotto!  Please try again later!")
						.queue();
			}

		} else {
			if (PointsAdder.payCoins(authorID, COST)) {
				peopleInLotto.add(authorID);
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " was added to the lottery for "
						+ Main.addCommas(COST) + Main.CURRENCY).queue();
			} else {
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " doesn't have the " + Main.addCommas(COST)
						+ Main.CURRENCY + " he needs to join the lottery!").queue();
			}
		}
	}
}
