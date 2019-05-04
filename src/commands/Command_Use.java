package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.Scar;
import src.ScarHandler;
import src.Store;
import src.Upgrade;

public class Command_Use extends Command {

	public Command_Use() {
		super();
		super.name = "use";
		super.help = "uses an item";
		super.arguments = "@mention item";
		// super.cooldown = 60;

	}

	@Override
	protected void execute(CommandEvent event) {
		if (event.getAuthor().isBot())
			return;

		String[] args = event.getArgs().trim().split(" ");

		if (args.length < 2) {
			event.replyError("Please enter all of the necessary parameters!");
			return;
		}

		try {
			args[0] = event.getArgs().replaceAll("!", "").trim().substring(2, 20);
		} catch (StringIndexOutOfBoundsException ex) {
			event.replyError("Please make sure to begin by `@mention`ing the person you want to attack!");
			return;
		}

		String targetID = args[0].trim();
		;
		String attackerID = event.getAuthor().getId();
		String weaponStr = event.getArgs().substring(22).trim();

		if (Store.hasItem(attackerID, weaponStr)) {
			Upgrade item = Store.getUsersItem(attackerID, weaponStr);
			if (item.useCharge()) {
				Scar scar = item.getScar();
				System.out.println(scar);
				if (scar != null) {
					event.reply("<@" + attackerID + "> used his " + scar.getUpgradeUsed() + "!");
					ScarHandler.giveUserScar(targetID, scar);
					event.reply("<@" + targetID + "> was scarred with a " + scar.getName() + "!  Dealt "
							+ scar.getDamage() + " damage!");
				} else {
					event.reply("You can't attack with a " + weaponStr + "!");
				}

			} else {
				event.replyError("Your " + item.getNamePrefix() + " is out of charges!");
			}
		} else {
			event.reply("You don't have a " + weaponStr + "!");
		}

	}

}
