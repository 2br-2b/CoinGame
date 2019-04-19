
package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GetStuff extends ListenerAdapter {

	private static MessageChannel c = null;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		String message = e.getMessage().getContentRaw();
		c = e.getChannel();

		// my private channel
		if (c.getId().equals("568466205590159361")) {
			Commands.interpretCommand(message);
			return;
		}

		if (message.toLowerCase().contains(Main.GET_HELP_STRING)) {
			String str = "List of commands:\n`" + Main.PREFIX + "store` shows the store.\n`" + Main.PREFIX
					+ "buy <name>` to buy something\n`" + Main.PREFIX
					+ "play <game_name>` to play a game.  Current games:\n\t`lotto`\n`" + Main.PREFIX
					+ "inv` shows your collection!\n`" + Main.PREFIX
					+ "give <user_id> <item_name>` gives the user the item!\n`" + Main.PREFIX
					+ "pay @mention <amount>` pays the other person!";

			c.sendMessage(str).queue();
		} else if (message.toLowerCase().contains(Main.GET_STORE_STRING)) {
			// System.out.println("Show the store");
			Store.showStore(e.getChannel());
		} else if (message.equals(Main.GET_MONEY_STRING)) {
			String str = e.getAuthor().getName() + " has " + Main.addCommas(Main.bal.get(e.getAuthor().getId()))
					+ Main.CURRENCY;
			// System.out.println(str);
			c.sendMessage(str).queue();

		} else if (message.equals(Main.GET_INVENTORY_STRING)) {
			String str = e.getAuthor().getName() + "'s inventory:\n"
					+ Main.addCommas(Main.bal.get(e.getAuthor().getId())) + Main.CURRENCY;

			int boost = 0;

			if (Main.upgrades.containsKey(e.getAuthor().getId())) {
				ArrayList<Upgrade> list = Main.upgrades.get(e.getAuthor().getId());
				for (Upgrade u : list) {
					boost += u.getTotalBoost();
					str += "\n`" + u.getQuantity() + "x` " + u.getName();
				}
			}

			str += "\nTotal boost: " + Main.addCommas(boost);

			// System.out.println(str);
			c.sendMessage(str).queue();

		}

	}
}
