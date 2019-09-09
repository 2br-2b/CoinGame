package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Feedback_Command extends Command {

	public Feedback_Command() {
		super();
		super.name = "feedback";
		super.help = "use to give feedback";

	}

	@Override
	protected void execute(CommandEvent event) {
		event.reply("Go to https://github.com/2br-2b/CoinGame/issues and create a new issue!");
		event.reply("<@351804839820525570>");
		// Main.getMemberByID("351804839820525570").
	}

}
