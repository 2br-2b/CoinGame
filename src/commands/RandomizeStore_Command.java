package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.Store;

public class RandomizeStore_Command extends Command {

	public RandomizeStore_Command() {
		super();
		super.name = "randomizeStore";
		super.help = "randomizes the store";
		super.hidden = true;
		super.ownerCommand = true;
		super.guildOnly = false;

	}

	@Override
	protected void execute(CommandEvent event) {
		Store.randomizeStore();
	}
}
