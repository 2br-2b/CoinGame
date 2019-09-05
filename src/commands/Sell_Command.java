package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.Main;
import src.PointsAdder;
import src.Store;
import src.Upgrade;

public class Sell_Command extends Command {

	public Sell_Command() {
		super();
		super.name = "sell";
		super.help = "sells an item";
		super.arguments = "item to sell";
		super.guildOnly = false;

	}

	@Override
	protected void execute(CommandEvent event) {

		String name = event.getArgs().replaceAll("‘", "'").toLowerCase();

		Upgrade theUpgrade = null;

		for (Upgrade up : Main.upgrades.get(event.getAuthor().getId())) {
			if (up.getName().toLowerCase().equals(name)) {
				theUpgrade = up;
				break;
			}
		}

		if (Store.removeItem(event.getAuthor().getId(), name)) {
			int paid = (int) (theUpgrade.getCost() * (Math.random() * 0.2 + 0.85));
			if (theUpgrade.getCost() == 0) {
				paid = (int) (10000 * (Math.random() * 0.2 + 0.85));
			}
			PointsAdder.addCoins(event.getAuthor().getId(), paid);
			event.reply(event.getAuthor().getAsMention() + " was paid " + Main.addCommas(paid) + Main.CURRENCY
					+ " for his " + theUpgrade.getName() + ".");
			Store.addToStore(theUpgrade);
		} else {
			event.replyError("Couldn't remove " + name);
		}
	}

}
