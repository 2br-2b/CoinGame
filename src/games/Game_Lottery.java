package games;

import java.util.ArrayList;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.Main;
import src.PointsAdder;
import src.Store;
import src.Upgrade;

public class Game_Lottery extends Command implements Game {

	private static ArrayList<String> peopleInLotto;
	int cost;

	public Game_Lottery() {
		super.name = "lottery";
		String[] a = { "lotto", "l" };
		super.aliases = a;
		super.help = "enters you in a lotto";
		super.arguments = "amount";
		peopleInLotto = new ArrayList<String>();
		cost = 10000;
	}

	@Override
	protected void execute(CommandEvent event) {
		String authorID = event.getAuthor().getId();

		if (peopleInLotto.contains(authorID)) {
			if (peopleInLotto.size() > 1) {
				String winner = peopleInLotto.get((int) (Math.random() * peopleInLotto.size()));
				event.reply("And the winner is…");
				event.getChannel().sendTyping();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				int winnings = (int) (cost * peopleInLotto.size() + cost * Math.random());

				event.reply("<@" + winner + ">!\nYou won " + Main.addCommas(winnings) + Main.CURRENCY + "!");
				PointsAdder.addCoins(winner, winnings);

				Store.removeItem(winner, "Lottery Ticket");
				Store.giveUserUpgrade(winner, new Upgrade("Winning Lottery Ticket", cost, 1));

				peopleInLotto.clear();

			} else {
				event.replyError("There aren't enough people to play the lotto!  Please try again later!");
			}

		} else {
			if (PointsAdder.payCoins(authorID, cost)) {
				peopleInLotto.add(authorID);
				event.reply(event.getAuthor().getAsMention() + " was added to the lottery for " + Main.addCommas(cost)
						+ Main.CURRENCY);
				Store.giveUserUpgrade(event.getAuthor().getId(), new Upgrade("Lottery Ticket", cost / 1000, 0));
			} else {
				event.replyError(event.getAuthor().getAsMention() + " doesn't have the " + Main.addCommas(cost)
						+ Main.CURRENCY + " he needs to join the lottery!");
			}
		}
	}

	// @Override
	public ArrayList<String> getUsers() {
		return peopleInLotto;
	}

}
