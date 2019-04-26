package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Lottery extends Game {

	private static ArrayList<String> peopleInLotto;
	static int cost;

	public Lottery() {
		this(10000);
	}

	public Lottery(int cost) {
		this.cost = cost;
		peopleInLotto = new ArrayList<String>();
		super.name = "Lottery";
	}

	@Override
	public void play(MessageReceivedEvent e) {
		String authorID = e.getAuthor().getId();

		if (peopleInLotto.contains(authorID)) {
			if (peopleInLotto.size() > 1) {
				String winner = peopleInLotto.get((int) (Math.random() * peopleInLotto.size()));
				e.getChannel().sendMessage("And the winner is…").queue();
				e.getChannel().sendTyping();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				int winnings = (int) (cost * peopleInLotto.size() + cost * Math.random());

				e.getChannel()
						.sendMessage("<@" + winner + ">!\nYou won " + Main.addCommas(winnings) + Main.CURRENCY + "!")
						.queue();
				PointsAdder.addCoins(winner, winnings);

				Store.removeItem(e.getAuthor().getId(), "Lottery Ticket");
				Store.giveUserUpgrade(winner, new Upgrade("Winning Lottery Ticket", cost, 1));

				peopleInLotto.clear();

			} else {
				e.getChannel().sendMessage("There aren't enough people to play the lotto!  Please try again later!")
						.queue();
			}

		} else {
			if (PointsAdder.payCoins(authorID, cost)) {
				peopleInLotto.add(authorID);
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " was added to the lottery for "
						+ Main.addCommas(cost) + Main.CURRENCY).queue();
				Store.giveUserUpgrade(e.getAuthor().getId(), new Upgrade("Lottery Ticket", cost / 1000, 0));
			} else {
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " doesn't have the " + Main.addCommas(cost)
						+ Main.CURRENCY + " he needs to join the lottery!").queue();
			}
		}

	}

	// @Override
	public ArrayList<String> getUsers() {
		return peopleInLotto;
	}

}
