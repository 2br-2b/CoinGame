package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.Main;
import src.Upgrade;

public class Give_Command extends Command {

	public Give_Command() {
		super();
		super.name = "give";
		super.help = "gives another person an upgrade";
		super.arguments = "@mention item";
		super.guildOnly = false;

	}

	@Override
	protected void execute(CommandEvent event) {

		String[] args = event.getArgs().trim().split(" ");

		if (args.length < 2) {
			event.replyError("Please enter all of the necessary parameters!");
			return;
		}

		try {
			args[0] = event.getArgs().replaceAll("!", "").trim().substring(2, 20);
		} catch (StringIndexOutOfBoundsException ex) {
			event.replyError("Please make sure to begin by `@mention`ing the person you want to give an item to!");
			return;
		}

		String targetID = args[0].trim();
		String attackerID = event.getAuthor().getId();
		String nameOfObj = event.getArgs().substring(22).trim();

		try {
			if (!Main.isGood(targetID)) {
				event.reply("<@!" + targetID + "> is not a valid user.");
				return;
			}
		} catch (Exception e) {
			event.reply("<@" + targetID + "> is not a valid user.");
			return;
		}

		for (Upgrade up : Main.upgrades.get(attackerID)) {

			if (up.getName().equalsIgnoreCase(nameOfObj)) {

				up.minusOne();
				if (up.getQuantity() < 1) {
					Main.upgrades.get(attackerID).remove(up);
				}

				src.Store.giveUserUpgrade(targetID, up);

				event.reply("<@" + attackerID + "> gave <@" + targetID + "> a " + up.getName() + "!");
				return;
			}
		}
		event.reply("<@" + attackerID + "> doesn't have a " + nameOfObj + " to give!");
	}
}
