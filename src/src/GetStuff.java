
package src;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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

		e = ev;
		c = e.getChannel();
		String message = e.getMessage().getContentRaw();

		// my private channel
		if (c.getId().equals("568466205590159361")) {
			Commands.interpretCommand(message);
			return;
		}

		if (message.toLowerCase().startsWith(Main.GET_HELP_STRING)) {
			getHelp();
		} else if (message.toLowerCase().startsWith(Main.GET_STORE_STRING)) {
			// System.out.println("Show the store");
			Store.showStore(e.getChannel());
		} else if (message.startsWith(Main.GET_MONEY_STRING)) {
			String str = e.getAuthor().getName() + " has " + Main.addCommas(Main.bal.get(e.getAuthor().getId()))
					+ Main.CURRENCY;
			// System.out.println(str);
			c.sendMessage(str).queue();

		} else if (message.startsWith(Main.GET_INVENTORY_STRING)) {
			String str = e.getAuthor().getName() + "'s inventory:\n"
					+ Main.addCommas(Main.bal.get(e.getAuthor().getId())) + Main.CURRENCY;

			int boost = 0;

			if (Main.upgrades.containsKey(e.getAuthor().getId())) {
				ArrayList<Upgrade> list = Main.upgrades.get(e.getAuthor().getId());
				for (Upgrade u : list) {
					boost += u.getTotalBoost();
					str += "\n`" + u.getQuantity() + "x` " + u.getNamePrefix();
				}
			}

			str += "\nTotal boost: " + Main.addCommas(boost);

			// System.out.println(str);
			c.sendMessage(str).queue();

		}

		serializeStuff();

	}

	private void getHelp() {
		String str = "";
		String[] list = e.getMessage().getContentRaw().split(" ");
		if (list.length > 1) {
			switch (list[1]) {
			case "prefix":
				str = Store.outdatedPrefix + " means that something is not sold in the store anymore.\n"
						+ "\uu221E is from the Avengers: Endgame event.\n" + MergableHandler.prefix
						+ " means that the upgrade was merged from two other upgrades.";
				break;

			case "merge":
				for (Mergable m : MergableHandler.possibleMerges) {
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
					+ "give <user_id> <item_name>` gives the user the item!\n`" + Main.PREFIX
					+ "pay @mention <amount>` pays the other person\n`" + Main.PREFIX
					+ "help prefix` lists the item prefixes and what they mean\n`" + Main.PREFIX
					+ "help merge` lists the possible merges";

		}
		c.sendMessage(str).queue();
	}

	private void serializeStuff() {
		try {
			FileOutputStream fos = new FileOutputStream("CoinGameBal.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Main.bal);
			oos.close();
			fos.close();
			System.out.println("Serialized HashMap data is saved in CoinGameBal.ser");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		try {
			FileOutputStream fos = new FileOutputStream("CoinGameUpgrades.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Main.upgrades);
			oos.close();
			fos.close();
			System.out.println("Serialized HashMap data is saved in CoinGameUpgrades.ser");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
