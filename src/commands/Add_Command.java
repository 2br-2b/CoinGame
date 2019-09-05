package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.PointsAdder;

public class Add_Command extends Command {

	public Add_Command() {
		super();
		super.name = "add";
		super.help = "gives you money!";
		super.arguments = "amount";
		super.hidden = true;
		super.ownerCommand = true;
		super.guildOnly = false;

	}

	@Override
	protected void execute(CommandEvent event) {
		PointsAdder.addCoins(event.getAuthor().getId(), Long.parseLong(event.getArgs()));
		event.reply("Done!");
	}

}
