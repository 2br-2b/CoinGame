package commands;

import java.util.ArrayList;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.User;
import src.Main;
import src.PointsAdder;
import src.Store;
import src.Upgrade;

public class Buy_Command extends Command {

	public Buy_Command() {
		super();
		super.name = "buy";
		super.help = "buys something";
		super.arguments = "item to buy";

	}

	@Override
	protected void execute(CommandEvent event) {
		ArrayList<Upgrade> list;

		if (Main.upgrades.containsKey(event.getAuthor().getId())) {
			list = Main.upgrades.get(event.getAuthor().getId());
		} else {
			list = new ArrayList<Upgrade>();
		}

		Upgrade toBuy = null;

		for (Upgrade up : Store.store) {
			if (up.getName().equalsIgnoreCase(event.getArgs())
					|| up.getNamePrefix().equalsIgnoreCase(event.getArgs())) {
				toBuy = up;
				break;
			}
		}

		User u = event.getAuthor();
		if (toBuy == null) {
			event.replyError("Couldn't find any `" + event.getArgs() + "`s in the store!");
		} else {
			if (PointsAdder.payCoins(u.getId(), toBuy.getCost())) {

				Store.giveUserUpgrade(u, toBuy, list);

				event.reply(u.getAsMention() + " bought one " + toBuy.getName() + " for " + toBuy.getCostString()
						+ Main.CURRENCY);

			} else {
				event.replyError("You don't have enough to buy that!");
			}
		}

		Main.upgrades.put(u.getId(), list);

	}

}
