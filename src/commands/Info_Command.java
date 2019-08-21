package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;
import src.Main;
import src.Upgrade;

public class Info_Command extends Command {

	public Info_Command() {
		super();
		super.name = "info";
		super.help = "gives info on an item";
		super.arguments = "item";

	}

	@Override
	protected void execute(CommandEvent event) {
		EmbedBuilder emb = new EmbedBuilder();

		for (Upgrade u : Main.masterUpgradeList) {
			if (u.getName().equalsIgnoreCase(event.getArgs())) {
				emb.setTitle(u.getNamePrefix());
				/*
				 * if (u.getScar() != null) { emb.setDescription("Cost: " +
				 * Main.addCommas(u.getCost()) + Main.CURRENCY + "\nBoost: " +
				 * Main.addCommas(u.getBoost()) + Main.CURRENCY + "\nScar: " +
				 * u.getScar().toStringOfOne()); } else {
				 */

				emb.setDescription("Cost: " + Main.addCommas(u.getCost()) + Main.CURRENCY + "\nBoost: "
						+ Main.addCommas(u.getBoost()) + Main.CURRENCY);
				// }
				emb.setColor(Main.embedColor);
				event.reply(emb.build());
				break;
			}
		}

	}

}
