package commands;

import java.util.ArrayList;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.Main;
import src.Upgrade;

public class Boost_Command extends Command {

	public Boost_Command() {
		super();
		super.name = "boost";
		super.help = "get a user's boost level";
		super.arguments = "(@mention)";
		super.guildOnly = false;
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

		String str = "<@!" + id + "> has a boost of ";

		int boost = 1;
		if (Main.upgrades.containsKey(id)) {
			ArrayList<Upgrade> list = Main.upgrades.get(id);
			for (Upgrade u : list) {
				boost += u.getTotalBoost();
			}

			if (boost < 1) {
				boost = 1;
			}
		}

		str += Main.addCommas(boost) + Main.CURRENCY;

		event.reply(str);

	}

}
