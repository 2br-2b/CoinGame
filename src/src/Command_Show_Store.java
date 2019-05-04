package src;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Command_Show_Store extends Command {

	public Command_Show_Store() {
		super();
		super.name = "store";
		super.help = "shows the store";
		// super.cooldown = 60;

	}

	@Override
	protected void execute(CommandEvent event) {
		if (event.getAuthor().isBot())
			return;

		String str = "";

		for (Upgrade u : Store.store) {
			str += u + "\n";
		}

		event.reply(str);

	}

}
