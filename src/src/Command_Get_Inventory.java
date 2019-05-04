package src;

import java.util.ArrayList;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;

public class Command_Get_Inventory extends Command {

	public Command_Get_Inventory() {
		super();
		super.name = "inv";
		super.help = "get a user's inventory";
		super.arguments = "(@mention)";
		// super.cooldown = 60;

	}

	@Override
	protected void execute(CommandEvent event) {
		String id;
		try {
			id = event.getArgs().replace("!", "").trim().substring(2, 20);
		} catch (StringIndexOutOfBoundsException e) {
			id = event.getAuthor().getId();
		}

		String str;
		try {
			str = Main.addCommas(Main.bal.get(id)) + Main.CURRENCY;
		} catch (NullPointerException e) {
			event.reply("<@" + id + "> doesn't have an inventory yet!");
			return;
		}

		int boost = 1;
		if (Main.upgrades.containsKey(id)) {
			ArrayList<Upgrade> list = Main.upgrades.get(id);
			for (Upgrade u : list) {
				boost += u.getTotalBoost();
				str += "\n`" + u.getQuantity() + "x` " + u.getNamePrefix() + ": `" + Main.addCommas(u.getBoost()) + "`"
						+ Main.CURRENCY;
			}

			for (Scar s : ScarHandler.getScars(id)) {
				boost += s.getTotalBoost();
			}

			if (boost < 1) {
				boost = 1;
			}
		}

		str += "\nTotal boost: " + Main.addCommas(boost);

		EmbedBuilder emb = new EmbedBuilder();

		emb.setTitle(Main.getUserFromID(id).getName() + "'s Inventory");
		emb.setDescription(str);

		event.reply(emb.build());

	}

}