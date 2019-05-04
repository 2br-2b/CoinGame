package src;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Command_Use extends Command {

	public Command_Use() {
		super();
		super.name = "use";
		super.help = "uses an item";
		super.arguments = "<Usable_Item>";
		// super.cooldown = 60;

	}

	@Override
	protected void execute(CommandEvent event) {
		if (event.getAuthor().isBot())
			return;

		String[] args = event.getArgs().trim().split(" ");

		if (args.length < 2) {
			event.replyError("Please enter all of the necessary parameters!");
		} else if (args.length > 2) {
			event.replyError("Please enter only necessary parameters!");
		}

		try {
			args[0] = event.getArgs().trim().substring(2, 21);
		} catch (StringIndexOutOfBoundsException ex) {
			event.replyError("Please make sure to begin by `@mention`ing the person you want to attack!");
			return;
		}

		String targetID = args[0].trim();
		;
		String attackerID = event.getAuthor().getId();
		String weaponStr = args[1];
		for (int i = 2; i < args.length; i++) {
			weaponStr += " " + args[i];
		}

		if (Store.hasItem(attackerID, weaponStr)) {
			Upgrade item = Store.removeAndReturnItem(attackerID, weaponStr);
			Scar scar = item.getScar();
			System.out.println(scar);
			if (scar != null) {
				event.reply("<@" + attackerID + "> used his " + scar.getUpgradeUsed() + "!");
				ScarHandler.giveUserScar(targetID, scar);
				event.reply("<@" + targetID + "> was scarred with a " + scar.getName() + "!  Dealt " + scar.getDamage()
						+ " damage!");

			} else {
				Store.giveUserUpgrade(attackerID, item);
				event.reply("You can't attack with a " + weaponStr + "!");
			}
		} else {
			event.reply("You don't have a " + weaponStr + "!");
		}

	}

}
