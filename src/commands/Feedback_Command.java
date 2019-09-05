package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Feedback_Command extends Command {

	public Feedback_Command() {
		super();
		super.name = "feedback";
		super.help = "brings you to the github page to open a new issue";
		super.arguments = "feedback";

	}

	@Override
	protected void execute(CommandEvent event) {
		event.reply("Go to https://github.com/2br-2b/CoinGame/issues and create a new issue!");
	}

}
