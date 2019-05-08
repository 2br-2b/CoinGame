package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;
import src.Main;
import src.Mergable;
import src.MergeHandler;

public class Merges_Command extends Command {

	public Merges_Command() {
		super.name = "merges";
		super.help = "shows you the possible merges";

	}

	@Override
	protected void execute(CommandEvent event) {

		String str = "";

		for (Mergable m : MergeHandler.possibleMerges) {
			str += m + "\n";
		}

		EmbedBuilder emb = new EmbedBuilder();

		emb.setTitle("Possible Merges:");
		emb.setDescription(str);
		emb.setColor(Main.embedColor);

		event.reply(emb.build());
	}

}
