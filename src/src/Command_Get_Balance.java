package src;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Command_Get_Balance extends Command {

	public Command_Get_Balance() {
		super();
		super.name = "bal";
		super.help = "get a user's balance";
		super.arguments = "(@mention)";
		// super.cooldown = 60;

	}

	@Override
	protected void execute(CommandEvent event) {
		String id;
		try {
			id = event.getArgs().replace("!", "").trim().substring(2, 20);
		} catch (StringIndexOutOfBoundsException e) {
			id = event.getAuthor().getId();
		}

		try {
			event.reply("<@" + id + "> has " + Main.addCommas(Main.bal.get(id)) + Main.CURRENCY);
		} catch (NullPointerException e) {
			event.reply("That user doesn't have a balance!");
		}

	}

}
