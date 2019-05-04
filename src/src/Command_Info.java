package src;

import java.awt.Color;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;

public class Command_Info extends Command {

	public Command_Info() {
		super();
		super.name = "info";
		super.help = "gives info on an item";
		super.arguments = "item";
		// super.cooldown = 60;

	}

	@Override
	protected void execute(CommandEvent event) {
		EmbedBuilder emb = new EmbedBuilder();

		for (Upgrade u : Main.masterUpgradeList) {
			if (u.getName().equalsIgnoreCase(event.getArgs())) {
				emb.setTitle(u.getNamePrefix());
				if (u.getScar() != null) {
					emb.setDescription("Cost: " + Main.addCommas(u.getCost()) + Main.CURRENCY + "\nBoost: "
							+ Main.addCommas(u.getBoost()) + Main.CURRENCY + "\nScar: " + u.getScar().toStringOfOne());
				} else {

					emb.setDescription("Cost: " + Main.addCommas(u.getCost()) + Main.CURRENCY + "\nBoost: "
							+ Main.addCommas(u.getBoost()) + Main.CURRENCY);
				}
				emb.setColor(Color.YELLOW);
				event.reply(emb.build());
				break;
			}
		}

	}

}
