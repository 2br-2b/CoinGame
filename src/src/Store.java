/**
 * @author GreedyVagabond
 */

package src;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Store extends ListenerAdapter {

	private static MessageChannel c = null;
	public static ArrayList<Upgrade> store;
	OffsetDateTime lastRandomized;

	public Store() {

		randomizeStore();
	}

	public void randomizeStore() {
		int randomItems = 5;

		// Name, cost, boost, inventory
		Upgrade[] randomStuff = { new Upgrade("Batterang", 10000, 1, 1000),
				new Upgrade("Crisp $1,000,000,000 bill", 999999999, 0, 100),
				new Upgrade("Darth Vader’s Helmet", 2000000000, 0, 1),
				new Upgrade("All of the Pokémon", 1000000000, 809, 151), new Upgrade("Mario's Hat", 10000000, 0, 1),
				new Upgrade("Rocket", 100000, 1, 100), new Upgrade("Nuclear Bomb", 190000000, 0, 5),
				new Upgrade("Nuclear Missile", 590000000, 0, 5), new Upgrade("Baby Shark", 2639860696L, 5, 15),
				new Upgrade("Smash Ball", 5000000, 30, 10), new Upgrade("Captain America's Shield", 17870000, 3, 1),
				new Upgrade("Death Star", 1138000, 100, 2), new Upgrade("Infinity Gauntlet", Integer.MAX_VALUE, 66, 2),
				new Upgrade("Fire-Breathing Rubber Duckie", 100000, 1, 100),
				new Upgrade("Easter egg", 1000000, 1, 100) };

		store = new ArrayList<Upgrade>();

		store.add(new Upgrade("Debug Byte", 1, 0, 10000000));
		store.add(new Upgrade("Discord", Long.MAX_VALUE, Integer.MAX_VALUE, 1));

		for (int i = 0; i < randomItems; i++) {
			Upgrade randomUpgrade = randomStuff[(int) (Math.random() * randomStuff.length)];

			while (store.contains(randomUpgrade) || randomUpgrade.getQuantity() < 1) {
				randomUpgrade = randomStuff[(int) (Math.random() * randomStuff.length)];
			}

			store.add(randomUpgrade);

		}

		Collections.sort(store);
		lastRandomized = OffsetDateTime.now();

	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		String message = e.getMessage().getContentRaw();
		c = e.getChannel();

		if (e.getMessage().getCreationTime().isAfter(lastRandomized.plusHours(1))) {
			randomizeStore();
		}

		if (message.toLowerCase().contains(Main.GET_HELP_STRING)) {
			String str = "List of commands:";
			for (String s : Main.STRINGS) {
				str += "\n" + s;
			}

			c.sendMessage(str).queue();
		} else if (message.toLowerCase().contains(Main.GET_STORE_STRING)) {
			// System.out.println("Show the store");
			showStore();
		} else if (message.contains(Main.GET_BUY_STRING)) {
			buySomething(message.substring(Main.GET_BUY_STRING.length() + 1), e.getAuthor());
		} else if (message.contains(Main.GET_ADD_STRING)) {
			if (e.getAuthor().getId().equals("351804839820525570")) {
				PointsAdder.addCoins(e.getAuthor().getId(),
						Long.parseLong(message.substring(Main.GET_ADD_STRING.length() + 1)));
			}
		}

	}

	public void showStore() {
		String str = "";

		for (Upgrade u : store) {
			str += u + "\n";
		}

		// System.out.println(str);
		c.sendMessage(str).queue();
	}

	public void buySomething(String thingToPurchase, User u) {
		ArrayList<Upgrade> list;

		if (Main.upgrades.containsKey(u.getId())) {
			list = Main.upgrades.get(u.getId());
		} else {
			list = new ArrayList<Upgrade>();
		}

		Upgrade toBuy = null;

		for (Upgrade up : store) {
			if (up.getName().toLowerCase().equals(thingToPurchase.toLowerCase())) {
				toBuy = up;
				break;
			}
		}

		if (toBuy == null) {
			c.sendMessage("Couldn't find any `" + thingToPurchase + "`s in the store!").queue();
		} else {
			if (PointsAdder.payCoins(u.getId(), toBuy.getCost())) {

				giveUserUpgrade(u, toBuy, list);

				c.sendMessage(
						u.getAsMention() + " bought one " + toBuy.getName() + " for " + toBuy.getCost() + Main.CURRENCY)
						.queue();

			} else {
				c.sendMessage("You don't have enough to buy that!").queue();
			}
		}

		Main.upgrades.put(u.getId(), list);

	}

	private void giveUserUpgrade(User u, Upgrade toBuy, ArrayList<Upgrade> list) {
		if (list == null) {
			list = new ArrayList<Upgrade>();
		}
		boolean found = false;

		for (int i = 0; i < list.size(); i++) {
			Upgrade up = list.get(i);
			if (up.getName().equals(toBuy.getName())) {
				found = true;
				up.plusOne();
				break;
			}
		}

		if (!found) {
			list.add(new Upgrade(toBuy));
		}

		Commands.addCommand("giveitem " + u.getId() + " " + toBuy.getBoost() + " " + toBuy.getName());

		toBuy.minusOne();
		if (toBuy.getQuantity() < 1) {
			store.remove(toBuy);
		}
	}

	public static void giveUserUpgrade(String id, Upgrade toBuy) {
		ArrayList<Upgrade> list = Main.upgrades.get(id);
		if (list == null) {
			list = new ArrayList<Upgrade>();
		}

		boolean found = false;
		for (int i = 0; i < list.size(); i++) {
			Upgrade up = list.get(i);
			if (up.getName().equals(toBuy.getName())) {
				found = true;
				up.plusOne();
				break;
			}
		}

		if (!found) {
			list.add(new Upgrade(toBuy));
		}

		Main.upgrades.put(id, list);

		Commands.addCommand("giveitem " + id + " " + toBuy.getBoost() + " " + toBuy.getName());
	}
}