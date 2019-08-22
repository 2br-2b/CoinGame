package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;
import src.Main;
import src.Store;
import src.Upgrade;

public class InfoPerson_Command extends Command {

	public InfoPerson_Command() {
		super();
		super.name = "info";
		super.help = "gives info on an item in a person's inventory";
		super.arguments = "item";
		super.hidden = true;

	}

	@Override
	protected void execute(CommandEvent event) {
		EmbedBuilder emb = new EmbedBuilder();
		String id;
		try {
			id = event.getArgs().replace("!", "").trim().substring(2, 20);
		} catch (StringIndexOutOfBoundsException e) {
			id = event.getAuthor().getId();
		}

		String str;
		try {
			if (Main.upgrades.get(id) == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			event.reply("<@" + id + "> doesn't have an inventory yet!");
			return;
		}

		String upgradeName = event.getArgs().substring(event.getArgs().indexOf(" ")).trim();

		Upgrade u = Store.getUsersItem(id, upgradeName);
		emb.setTitle(u.getNamePrefix());

		/*
		 * if (u.getScar() != null)
		 *
		 * { emb.setDescription("Cost: " + Main.addCommas(u.getCost()) + Main.CURRENCY +
		 * "\nBoost: " + Main.addCommas(u.getBoost()) + Main.CURRENCY + "\nScar: " +
		 * u.getScar().toStringOfOne()); } else {
		 */

		emb.setDescription("Cost: " + Main.addCommas(u.getCost()) + Main.CURRENCY + "\nBoost: "
				+ Main.addCommas(u.getBoost()) + Main.CURRENCY);
		// }
		emb.setColor(Main.embedColor);
		event.reply(emb.build());

	}

}
