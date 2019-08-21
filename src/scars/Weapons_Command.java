package scars;

import java.util.ArrayList;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;
import src.Main;
import src.Upgrade;

public class Weapons_Command extends Command {

	public Weapons_Command() {
		super();
		super.name = "weapons";
		super.help = "get a user's weapons";
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

		String str = "";

		int damage = 0;
		if (Main.upgrades.containsKey(id)) {
			ArrayList<Upgrade> list = Main.upgrades.get(id);
			for (Upgrade u : list) {
				if (u.getEffect() != null && u.getCharges() > 0) {
					if (str.length() != 0) {
						str += "\n";
					}
					str += "`" + u.getCharges() + "x` " + u.getNamePrefix() + ": `"
							+ Main.addCommas(u.getEffect().getDamage()) + "`" + Main.CURRENCY;
					damage += u.getCharges() * u.getEffect().getDamage();

				}

			}

		}

		str += "\nTotal damage: " + damage;

		EmbedBuilder emb = new EmbedBuilder();

		emb.setTitle(Main.getUserFromID(id).getName() + "'s Weapons");
		emb.setDescription(str);
		emb.setColor(Main.embedColor);

		event.reply(emb.build());

	}

}
