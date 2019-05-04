package src;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Command_Set extends Command {

	public Command_Set() {
		super();
		super.name = "set";
		super.help = "sets a user's balance";
		super.arguments = "id, amount";
		super.ownerCommand = true;
		super.hidden = true;
		// super.cooldown = 60;

	}

	@Override
	protected void execute(CommandEvent event) {
		if (event.getAuthor().isBot())
			return;

		String[] args = event.getArgs().trim().split(",");

		if (args.length < 2) {
			event.replyError("Please enter all of the necessary parameters!");
			return;
		} else if (args.length > 2) {
			event.replyError("Please enter only necessary parameters!");
			return;
		}

		try {
			args[0] = event.getArgs().trim().substring(2, 21);
		} catch (StringIndexOutOfBoundsException ex) {
			event.replyError("Please make sure to begin by `@mention`ing the person you want to set!");
			return;
		}

		String targetID = args[0];
		int amount;
		try {
			amount = Integer.parseInt(args[1].trim());
		} catch (NumberFormatException e) {
			event.reply(args[1].trim() + " is not a valid number.  Please try again!");
			return;
		}

		PointsAdder.addCoins(targetID, -Main.bal.get(targetID));
		PointsAdder.addCoins(targetID, amount);

	}

}
