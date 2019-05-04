
package src;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GetStuff extends ListenerAdapter {

	private static MessageChannel c = null;
	private static MessageReceivedEvent e = null;

	@Override
	public void onMessageReceived(MessageReceivedEvent ev) {

		if (!Main.BOTS_ALLOWED && ev.getAuthor().isBot())
			return;

		if (e.getMessage().getContentRaw().toLowerCase().startsWith(Main.PREFIX + "help")) {
			String str = "";
			String[] list = e.getMessage().getContentRaw().split(" ");
			if (list.length > 1) {
				switch (list[1]) {
				case "prefix":
					str = Store.outdatedPrefix + " means that something is not sold in the store anymore.\n"
							+ "\uu221E is from the Avengers: Endgame event.\n" + UpgradeMerge.prefix
							+ " means that the upgrade was merged from two other upgrades.\n" + Store.millionPrefix
							+ " means that something is priced at at least 1,000,000" + Main.CURRENCY + '\n'
							+ Store.billionPrefix + " means that something is priced at at least 1,000,000,000"
							+ Main.CURRENCY + "\n" + Store.longPrefix
							+ " means that the item's price is listed as a Long (more than " + Integer.MAX_VALUE
							+ Main.CURRENCY + "!)\n" + Store.weaponPrefix
							+ " means that it can be `c!use`d as a weapon!";

					break;

				case "merge":
					for (Mergable m : UpgradeMerge.possibleMerges) {
						str += m + "\n";
					}

					break;

				default:
					System.err.println("getHelp Error");
					break;
				}
			} else {
				str = "List of commands:\n`" + Main.PREFIX + "store` shows the store.\n`" + Main.PREFIX
						+ "buy <name>` to buy something\n`" + Main.PREFIX
						+ "play <game_name>` to play a game.  Current games:\n\t`lotto`\n" + "\t`flip <wager>`\n"
						+ "\t`hangman <letter>`\n`" + Main.PREFIX + "inv` shows your collection, or `" + Main.PREFIX
						+ "bal` just shows your balance.\n`" + Main.PREFIX
						+ "give @mention <item_name>` gives the user the item!\n`" + Main.PREFIX
						+ "pay @mention <amount>` pays the other person\n`" + Main.PREFIX
						+ "help prefix` lists the item prefixes and what they mean\n`" + Main.PREFIX
						+ "help merge` lists the possible merges\n`" + Main.PREFIX
						+ "use @mention <item_name>` uses an item on the mentioned user\n`" + Main.PREFIX
						+ "scars` shows the scars you have been attacked with";

			}
			c.sendMessage(str).queue();

		}

	}

}
