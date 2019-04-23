package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CoinFlip extends Game {

	int maxPay;

	public CoinFlip() {
		super.name = "Coin Flip";
		maxPay = 10000;
	}

	@Override
	public void play(MessageReceivedEvent e) {
		String[] m = e.getMessage().getContentRaw().split(" ");
		int paid = 0;

		try {
			paid = Integer.parseInt(m[2]);
			System.out.println(paid);
		} catch (NumberFormatException ex) {
			e.getChannel().sendMessage(m[2] + " is not a valid number.  Please try again.").queue();
			return;
		}

		if (paid < 0) {
			e.getChannel().sendMessage("You have to pay more than 0" + Main.CURRENCY + " to play!").queue();
			return;
		} else if (paid > maxPay) {
			e.getChannel().sendMessage("You can only pay up to " + Main.addCommas(maxPay) + Main.CURRENCY + "!")
					.queue();
			return;
		}

		if (PointsAdder.payCoins(e.getAuthor().getId(), paid)) {
			e.getChannel().sendMessage("All right!  Flipping a coin for " + Main.addCommas(paid) + Main.CURRENCY + "…")
					.queue();
			e.getChannel().sendTyping();

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			double rand = Math.random();

			if (rand > 0.5) {
				int winnings = (int) (paid * (1.5 + rand));
				e.getChannel().sendMessage("Heads!  " + e.getAuthor().getAsMention() + " won "
						+ Main.addCommas(winnings) + Main.CURRENCY + "!").queue();
				PointsAdder.addCoins(e.getAuthor().getId(), winnings);

			} else {
				e.getChannel().sendMessage("Tails!  Better luck next time!").queue();

			}
			Store.giveUserUpgrade(e.getAuthor().getId(), new Upgrade("Coin", 0, 1));

		} else {
			e.getChannel().sendMessage("You don't have " + Main.addCommas(paid) + Main.CURRENCY + "!").queue();
			return;
		}

	}

	@Override
	public ArrayList<String> getUsers() {
		return new ArrayList<String>();
	}

}
