package commands;

import java.util.ArrayList;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;
import src.Main;
import src.Upgrade;

public class Inv_Command extends Command {

	public Inv_Command() {
		super();
		super.name = "inv";
		super.help = "get a user's inventory";
		super.arguments = "(@mention)";
		super.guildOnly = false;
		// super.cooldown = 60;

	}

	@Override
	protected void execute(CommandEvent event) {
		String id;
		try {
			id = event.getArgs().replace("!", "").replace("<@", "").replace(">", "").trim();
			if (id.equals("") || id.equals("<@>")) {
				throw new Exception();
			}
		} catch (Exception e) {
			id = event.getAuthor().getId();
		}

		String str;
		try {
			str = Main.addCommas(Main.bal.get(id)) + Main.CURRENCY;
			if (Main.upgrades.get(id) == null) {
				throw new NullPointerException();
			}
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

			if (boost < 1) {
				boost = 1;
			}
		}

		str += "\nTotal boost: " + Main.addCommas(boost);

		EmbedBuilder emb = new EmbedBuilder();

		emb.setTitle(Main.getUserFromID(id).getName() + "'s Inventory");
		emb.setDescription(str);
		emb.setColor(Main.embedColor);

		if (Main.upgrades.get(id).size() > 150) {

			EmbedBuilder emb2 = new EmbedBuilder();

			emb2.setTitle(Main.getUserFromID(id).getName() + "'s Inventory");
			emb2.setDescription(Main.addCommas(Main.bal.get(id)) + Main.CURRENCY + "\n\nCheck your DMs\n\nTotal boost: "
					+ Main.addCommas(boost));
			emb2.setColor(Main.embedColor);

			event.reply(emb2.build());

			if (str.length() > 170) {

				ArrayList<String> listOfEmbeds = new ArrayList<>();
				listOfEmbeds.add("");

				for (String line : str.split("\n")) {
					if (listOfEmbeds.get(listOfEmbeds.size() - 1).length() + line.length() > 200) {
						listOfEmbeds.add(line);
					} else {
						listOfEmbeds.set(listOfEmbeds.size() - 1,
								listOfEmbeds.get(listOfEmbeds.size() - 1) + line + "\n");
					}
				}

				EmbedBuilder itterativeEmbed = new EmbedBuilder();

				itterativeEmbed.setTitle(Main.getUserFromID(id).getName() + "'s Inventory");

				for (String embedText : listOfEmbeds) {
					itterativeEmbed.setDescription(embedText);
					itterativeEmbed.setColor(Main.embedColor);
					event.replyInDm(itterativeEmbed.build());
					itterativeEmbed = new EmbedBuilder();
				}

			} else {
				event.replyInDm(emb.build());
			}

		} else {
			event.reply(emb.build());
		}

	}

}
