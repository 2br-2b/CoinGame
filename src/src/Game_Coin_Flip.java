package src;

import java.util.ArrayList;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Game_Coin_Flip extends Command {

	int maxPay;

	public Game_Coin_Flip() {
		super.name = "flip";
		super.help = "flips a coin based on how much you wager";
		super.arguments = "amount";
		super.cooldown = 10;
		maxPay = 10000;
	}

	@Override
	protected void execute(CommandEvent event) {

		int paid = 0;

		try {
			paid = Integer.parseInt(event.getMessage().getContentRaw());
			// System.out.println(paid);
		} catch (NumberFormatException ex) {
			event.replyError(event.getMessage().getContentRaw() + "is not a valid number.  Please try again.");
			return;
		}

		if (paid < 100) {
			event.replyError("You have to pay at least 100" + Main.CURRENCY + " to play!");
			return;
		} else if (paid > maxPay) {
			event.replyError("You can only pay up to " + Main.addCommas(maxPay) + Main.CURRENCY + "!");
			return;
		}

		if (PointsAdder.payCoins(event.getAuthor().getId(), paid)) {
			String str = event.getAuthor().getAsMention() + ", you got a ";
			/*
			 * try { Thread.sleep(3000); } catch (InterruptedException e1) {
			 * e1.printStackTrace(); }
			 */

			double rand = Math.random();

			if (rand > 0.5) {
				int winnings = (int) (paid * (1.5 + rand));

				str += "heads! :smiley: You won " + Main.addCommas(winnings) + Main.CURRENCY + "!";
				PointsAdder.addCoins(event.getAuthor().getId(), winnings);
			} else {
				str += "tails! :skull: Better luck next time!";
			}
			event.reply(str);
			Store.giveUserUpgrade(event.getAuthor().getId(), new Upgrade("Coin", 10, 1));

		} else {
			event.replyError("You don't have " + Main.addCommas(paid) + Main.CURRENCY + "!");
			return;
		}

	}

	// @Override
	public ArrayList<String> getUsers() {
		return new ArrayList<String>();
	}

}
