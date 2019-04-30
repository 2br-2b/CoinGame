package src;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CoinFlip extends Game {

	int maxPay;
	private HashMap<String, OffsetDateTime> coolingDown;
	private int COOLDOWN_SECONDS = 10;

	public CoinFlip() {
		super.name = "Coin Flip";
		maxPay = 10000;
		coolingDown = new HashMap<String, OffsetDateTime>();
	}

	@Override
	public void play(MessageReceivedEvent e) {
		if (coolingDown.containsKey(e.getAuthor().getId()) && !e.getMessage().getCreationTime()
				.isAfter(coolingDown.get(e.getAuthor().getId()).plusSeconds(COOLDOWN_SECONDS))) {
			e.getChannel().sendMessage(e.getAuthor().getAsMention() + ", wait a few seconds in between coin flips!")
					.queue();
			return;
		}

		coolingDown.put(e.getAuthor().getId(), e.getMessage().getCreationTime());

		String[] m = e.getMessage().getContentRaw().split(" ");
		int paid = 0;

		try {
			paid = Integer.parseInt(m[2]);
			// System.out.println(paid);
		} catch (NumberFormatException ex) {
			e.getChannel()
					.sendMessage(
							e.getAuthor().getAsMention() + ", " + m[2] + " is not a valid number.  Please try again.")
					.queue();
			return;
		}

		if (paid < 100) {
			e.getChannel().sendMessage(e.getAuthor().getAsMention() + ", you have to pay at least 100" + Main.CURRENCY
					+ " or more to play!").queue();
			return;
		} else if (paid > maxPay) {
			e.getChannel().sendMessage(e.getAuthor().getAsMention() + ", you can only pay up to "
					+ Main.addCommas(maxPay) + Main.CURRENCY + "!").queue();
			return;
		}

		if (PointsAdder.payCoins(e.getAuthor().getId(), paid)) {
			String str = e.getAuthor().getAsMention() + ", you got a ";
			/*
			 * try { Thread.sleep(3000); } catch (InterruptedException e1) {
			 * e1.printStackTrace(); }
			 */

			double rand = Math.random();

			if (rand > 0.5) {
				int winnings = (int) (paid * (1.5 + rand));

				str += "heads! :smiley: You won " + Main.addCommas(winnings) + Main.CURRENCY + "!";
				PointsAdder.addCoins(e.getAuthor().getId(), winnings);
			} else {
				str += "tails! :skull: Better luck next time!";
			}
			e.getChannel().sendMessage(str).queue();
			Store.giveUserUpgrade(e.getAuthor().getId(), new Upgrade("Coin", 10, 1));

		} else {
			e.getChannel().sendMessage(
					e.getAuthor().getAsMention() + ", you don't have " + Main.addCommas(paid) + Main.CURRENCY + "!")
					.queue();
			return;
		}

	}

	// @Override
	public ArrayList<String> getUsers() {
		return new ArrayList<String>();
	}

}
