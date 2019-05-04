package src;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Command_Get_Scars extends Command {

	public Command_Get_Scars() {
		super();
		super.name = "scars";
		super.help = "list your scars";
		// super.arguments = "<Usable_Item>";

	}

	@Override
	protected void execute(CommandEvent event) {

		event.reply(ScarHandler.getScars(event.getAuthor().getId()).toString());
	}

}
