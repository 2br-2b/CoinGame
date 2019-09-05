package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import src.Main;
import src.Mergable;
import src.MergeHandler;
import src.Store;

public class Help_Command extends Command {

	public Help_Command() {
		super();
		super.name = "help";
		super.help = "shows specific help menu";
		super.arguments = "prefix/merge";
		super.guildOnly = false;

	}

	@Override
	protected void execute(CommandEvent event) {
		System.out.println("yay");
		String str = "";
		switch (event.getArgs()) {
		case "prefix":
			str = Store.outdatedPrefix + " means that something is not sold in the store anymore.\n"
					+ "\uu221E is from the Avengers: Endgame event.\n" + MergeHandler.prefix
					+ " means that the upgrade was merged from two other upgrades.\n" + Store.millionPrefix
					+ " means that something is priced at at least 1,000,000" + Main.CURRENCY + '\n'
					+ Store.billionPrefix + " means that something is priced at at least 1,000,000,000" + Main.CURRENCY
					+ "\n" + Store.longPrefix + " means that the item's price is listed as a Long (more than "
					+ Integer.MAX_VALUE + Main.CURRENCY + "!)\n" + Store.weaponPrefix
					+ " means that it can be `c!use`d as a weapon!";

			break;

		case "merge":
			for (Mergable m : MergeHandler.possibleMerges) {
				str += m + "\n";
			}

			break;

		default:
			return;
		}

		event.replyInDm(str);
	}
}
