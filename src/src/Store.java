
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
	static OffsetDateTime lastRandomized;
	public static Upgrade[] randomStuff = {
			new Upgrade("This is random", (int) (Math.random() * 100000), (int) (Math.random() * 100),
					(int) (Math.random() * 30)),
			new Upgrade("Batterang", 10000, 7, 1000), new Upgrade("Crisp $1,000,000,000 bill", 999999999, 0, 100),
			new Upgrade("Darth Vader’s Helmet", 2000000, 1138, 1), new Upgrade("All of the Pokemon", 1000000, 809, 151),
			new Upgrade("Mario’s Hat", 100000, 100, 1), new Upgrade("Rocket", 100000, 1, 100),
			new Upgrade("Nuclear Bomb", 190000, 19, 5), new Upgrade("Nuclear Missile", 5900000, 10, 5),
			new Upgrade("Baby Shark", 2639860696L, 50000, 15), new Upgrade("Smash Ball", 500000, 30, 10),
			new Upgrade("Captain America’s Shield", 17871941, 30, 1), new Upgrade("Death Star", 1138000, 100, 2),
			new Upgrade("Infinity Gauntlet", Integer.MAX_VALUE, 666666, 2),
			new Upgrade("Fire-Breathing Rubber Duckie", 1000, 1, 100), new Upgrade("Popcorn", 1000, 1, 10000),
			new Upgrade("Sword", 10000, 3, 100), new Upgrade("Shield", 5000, 1, 200),
			new Upgrade("Ring of Power", 100000, 100, 19), new Upgrade("Discord", Long.MAX_VALUE, Integer.MAX_VALUE, 1),
			new Upgrade("Debug Byte", 1, 0, Integer.MAX_VALUE), new Upgrade("Easy Button", 10000, 13, 10),
			new Upgrade("Cookie", 100, 30, 10), new Upgrade("Hax", 1, 500, 1), new Upgrade("Lol", 42, 24, 1),
			new Upgrade("Yay", 30, 1, 20), new Upgrade("Hmm", 10, 10, 1), new Upgrade("Stormbreaker", 100000, 1000, 1),
			new Upgrade("Limited-Edition Collector's Edition Easter Egg", 1000, 100, 5) };

	public static Upgrade[] infinityStones = { new Upgrade("Soul Stone", 1000000, 1000, 1),
			new Upgrade("Mind Stone", 1000000, 1000, 1), new Upgrade("Reality Stone", 1000000, 1000, 1),
			new Upgrade("Space Stone", 1000000, 1000, 1), new Upgrade("Soul Stone", 1000000, 1000, 1),
			new Upgrade("Power Stone", 1000000, 1000, 1) };

	public Store() {

		randomizeStore();
	}

	public static void randomizeStore() {
		int randomItems = 7;
		randomStuff[0] = new Upgrade("This is random", (int) (Math.random() * 100000), (int) (Math.random() * 100),
				(int) (Math.random() * 30));

		// Name, cost, boost, inventory
		store = new ArrayList<Upgrade>();
		store.clear();

		for (int i = 0; i < randomItems; i++) {
			Upgrade randomUpgrade = randomStuff[(int) (Math.random() * randomStuff.length)];

			while (store.contains(randomUpgrade) || randomUpgrade.getQuantity() < 1) {
				randomUpgrade = randomStuff[(int) (Math.random() * randomStuff.length)];
			}

			store.add(randomUpgrade);

		}

		store.add(infinityStones[(int) (Math.random() * 6)]);

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

		if (message.contains(Main.PREFIX + "give")) {
			String[] messageSplit = message.split(" ");
			String nameOfObj = messageSplit[2];
			for (int i = 3; i < messageSplit.length; i++) {
				nameOfObj += " " + messageSplit[i];
			}
			userGiveUserUpgrade(e.getAuthor().getId(), nameOfObj, messageSplit[1]);
		} else if (message.contains(Main.GET_BUY_STRING)) {
			buySomething(message.substring(Main.GET_BUY_STRING.length() + 1), e.getAuthor());
		} else if (message.contains(Main.GET_ADD_STRING)) {
			if (e.getAuthor().getId().equals("351804839820525570")) {
				PointsAdder.addCoins(e.getAuthor().getId(),
						Long.parseLong(message.substring(Main.GET_ADD_STRING.length() + 1)));
			}
		}

	}

	public static void showStore(MessageChannel messageChannel) {
		String str = "";

		for (Upgrade u : store) {
			str += u + "\n";
		}

		// System.out.println(str);
		messageChannel.sendMessage(str).queue();
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

				c.sendMessage(u.getAsMention() + " bought one " + toBuy.getName() + " for " + toBuy.getCostString()
						+ Main.CURRENCY).queue();

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

	public static void userGiveUserUpgrade(String idgiver, String togive, String idgiven) {
		userGiveUserUpgrade(idgiver, togive, idgiven, c);
	}

	public static void userGiveUserUpgrade(String idgiver, String togive, String idgiven, MessageChannel c) {
		for (Upgrade up : Main.upgrades.get(idgiver)) {

			if (up.getName().toLowerCase().equals(togive.toLowerCase())) {

				up.minusOne();
				Commands.addCommand("remove " + idgiver + " " + togive);
				if (up.getQuantity() < 1) {
					Main.upgrades.get(idgiver).remove(up);
				}

				giveUserUpgrade(idgiven, up);

				c.sendMessage("<@" + idgiver + "> gave <@" + idgiven + "> a " + up.getName() + "!").queue();
				return;
			}
		}
		c.sendMessage("<@" + idgiver + "> doesn't have a " + togive + " to give!").queue();

	}

	public static boolean removeItem(String id, String upgradeName) {
		for (Upgrade up : Main.upgrades.get(id)) {
			if (up.getName().toLowerCase().equals(upgradeName.toLowerCase())) {
				up.minusOne();
				Commands.addCommand("remove " + id + " " + upgradeName);
				if (up.getQuantity() < 1) {
					Main.upgrades.get(id).remove(up);
					return true;
				}
			}
		}

		return false;
	}

	public static Upgrade getUpgradeOfName(String name) {
		for (Upgrade u : randomStuff) {
			if (u.getName().equals(name)) {
				return u;
			}
		}
		System.out.println("Couldn't find it!");
		return null;
	}
}