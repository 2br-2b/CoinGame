package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;
import src.Main;
import src.Scar;
import src.ScarHandler;

public class Scars_Command extends Command {

	public Scars_Command() {
		super();
		super.name = "scars";
		super.help = "list your scars";
		// super.arguments = "<Usable_Item>";

	}

	@Override
	protected void execute(CommandEvent event) {
		String id;
		try {
			id = event.getArgs().replace("!", "").trim().substring(2, 20);
		} catch (StringIndexOutOfBoundsException e) {
			id = event.getAuthor().getId();
		}

		String str = "";

		int boost = 0;
		if (ScarHandler.scars.containsKey(id)) {

			for (Scar s : ScarHandler.getScars(id)) {
				boost += s.getTotalDamage();
				str += "\n`" + s.getQuantity() + "x` " + s.getName() + ": `" + Main.addCommas(s.getBoost()) + "`"
						+ Main.CURRENCY;
			}

		} else {
			event.reply("<@" + id + "> doesn't have any scars… yet.");
			return;
		}

		str += "\nTotal loss: " + Main.addCommas(boost) + Main.CURRENCY;

		EmbedBuilder emb = new EmbedBuilder();

		emb.setTitle(Main.getUserFromID(id).getName() + "'s Scars");
		emb.setDescription(str);
		emb.setColor(Main.embedColor);

		event.reply(emb.build());
	}

}
