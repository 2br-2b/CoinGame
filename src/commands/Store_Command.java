package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;
import src.Main;
import src.Store;
import src.Upgrade;

public class Store_Command extends Command {

	public Store_Command() {
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

		EmbedBuilder emb = new EmbedBuilder();
		emb.setTitle("The Store");
		emb.setDescription(str);
		emb.setColor(Main.embedColor);

		event.reply(emb.build());

	}

}
