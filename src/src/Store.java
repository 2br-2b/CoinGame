
package src;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Store extends ListenerAdapter {

	public static String apostrophe = "'";
	public static String millionPrefix = "**$**";
	public static String billionPrefix = "**$$**";
	public static String longPrefix = ":gem:";
	public static String weaponPrefix = "**W**";
	private static MessageChannel c = null;
	public static ArrayList<Upgrade> store;
	static OffsetDateTime lastRandomized;

	public static String explosionWord = "Explosion";
	public static String burnWord = "Fiery Explosion";
	public static String swordWord = "Merely a flesh wound!";

	public static Upgrade[] randomStuff = {

			new Upgrade(weaponPrefix, "Batarang", 10000, 7, 10, new Scar("Batarang Knockout!", 2, "Batarang")),
			new Upgrade(weaponPrefix, "Rocket", 100000, 1, 10, new Scar(explosionWord, 18, "Rocket")),
			new Upgrade(weaponPrefix, "Nuclear Bomb", 190000, 19, 5,
					new Scar("Nuclear " + explosionWord, 1, "Nuclear Bomb")),
			new Upgrade(weaponPrefix, "Baby Shark", 2639860696L, 50000, 1, new Scar("Shark Bite", 10, "Baby Shark")),
			new Upgrade(weaponPrefix, "Smash Ball", 500000, 30, 6,
					new Scar("Smashed by Fox with No Items on Final Destination", 30, "Smash Ball")),
			new Upgrade(weaponPrefix, "Captain America" + apostrophe + " Shield", 17871941, 30, 1,
					new Scar("Didn't watch your LANGUAGE!", 1, "Captain America" + apostrophe + " Shield")),
			new Upgrade(weaponPrefix, "Death Star", 1138000, 100, 2,
					new Scar("They fired when ready", 1, "Death Star")),
			new Upgrade(weaponPrefix, "Infinity Gauntlet", Integer.MAX_VALUE, 6666, 1,
					new Scar("\"Aw, Snap\" Moment", 600, "Infinity Gauntlet")),
			new Upgrade(weaponPrefix, "Fire-Breathing Rubber Duckie", 1000, 1, 10,
					new Scar(burnWord, 1, "Fire-Breathing Rubber Duckie")),
			new Upgrade(weaponPrefix, "Sword", 10000, 3, 100, new Scar(swordWord, 1, "Sword")),
			new Upgrade(weaponPrefix + " :ring:", "Ring of Power", 100000, 100, 19,
					new Scar("Lost a finger", 10, "Ring of Power")),
			new Upgrade(weaponPrefix, "Stormbreaker", 100000, 1000, 1, new Scar("Head Shot", 30, "Stormbreaker")),
			new Upgrade(weaponPrefix, "Shark Repellent Bat Spray", 1996, 10, 4,
					new Scar("Helicopter Crash", 1, "Shark Repellent Bat Spray")),
			new Upgrade(weaponPrefix, "Bowcaster", 150000, 4, 10, new Scar("Wookie Shot", 1, "Bowcaster")),
			new Upgrade(weaponPrefix, "Lightsaber", 8000000000L, 1977, 6, new Scar("Lost a hand", 15, "Lightsaber")),
			new Upgrade(weaponPrefix, "Tank", 8580000, 150, 10, new Scar("Shot by a Tank", 1, "Tank")),
			new Upgrade(weaponPrefix, "Pet Dragon", 10000000, 3000, 5, new Scar(burnWord, 1, "Pet Dragon")),
			new Upgrade(weaponPrefix, "Mjolnir", 449000000, 3000, 1, new Scar("Unworthily Hammered", 1, "Mjolnir")),
			new Upgrade(weaponPrefix, "The Ultimate Ultimate Weapon", 123000000, 1640, 6,
					new Scar("Lack of Inner Piece", 1, "The Ultimate Ultimate Weapon")),
			new Upgrade(weaponPrefix, "The Tumbler Batmobile", 18000000, 493, 1,
					new Scar("Uncoolness for not being Batman", 10, "The Tumbler Batmobile")),
			new Upgrade(weaponPrefix, "Bug", 1, -1, 128, new Scar("Bug Bite", 1, "Bug")),
			new Upgrade(weaponPrefix, "Shards of Narsil", 20000, 20, 6,
					new Scar("Got a splinter", 1, "Shards of Narsil")),

			new Upgrade("Crisp $1,000,000 bill", 999999, 0, 100),
			new Upgrade("Darth Vader" + apostrophe + "s Helmet", 2000000, 1138, 1),
			new Upgrade("Kylo Ren" + apostrophe + "s Helmet", 200000, 135, 1),
			new Upgrade("All of the Pokemon", 1510000, 809, 1), new Upgrade("Shield", 5000, 1, 200),
			new Upgrade("Mario" + apostrophe + "s Hat", 100000, 100, 1), new Upgrade("Popcorn", 1000, 1, 100),
			new Upgrade("Debug Byte", 1, 0, 256), new Upgrade("Easy Button", 10000, 13, 10),
			new Upgrade(":cookie:", "Cookie", 100, 30, 10), new Upgrade("A bad feeling about this", 120000, 1138, 1),
			new Upgrade("Blender (for food)", 10000, 2, 7), new Upgrade("Blender (the program)", 100000, 28, 10),
			new Upgrade("Kirk" + apostrophe + "s Glasses", 1701, 10, 1), new Upgrade("iPhone XR", 100000, 1, 1),
			new Upgrade("Stormtrooper Helmet", 100000, 5, 10), new Upgrade("Diamond Armor", 5307786900L, 1000, 1),
			new Upgrade("Wayne Manor", 800000000, 150000),
			new Upgrade("Because I" + apostrophe + "m Batman!", 682450750, 54321),
			new Upgrade("Hubble Telescope", 2870000000L, 199000),
			new Upgrade("International Space Station", 50400000000L, 1961990),
			new Upgrade("1963 Ferrari GTO", 52000000, 1963, 5),
			new Upgrade("Gold Plated Bugatti Veyron", 10000000, 1500),
			new Upgrade("The Physical Impossibility of Death in the Mind of Someone Living", 12000000, 1991),
			new Upgrade("Magnetic Floating Bed", 1600000, 2000), new Upgrade("insure.com Domain", 16000000, 12345),
			new Upgrade("Crystal Piano", 3200000, 2008), new Upgrade("Gram of Antimatter", 62500000000L, 7654321, 10),

			new Upgrade("Huia Bird Feather", 10000, 10, 12), new Upgrade("141-year-old newspaper", 230000000, 141, 1),
			new Upgrade("Charles Hollander chess set", 600000, 16, 7), new Upgrade("Ferrari Enzo", 1325000, 55, 7),
			new Upgrade("Honus Wagner Rookie Card", 21000000, 1909, 3),
			new Upgrade(":basketball:", "Evan Perlmutter" + apostrophe + "s Fanhood", 3500, 25, 1),
			new Upgrade("Kidney on eBay", 57050000, 2, 1), new Upgrade("Book", 451, 1, 10), };

	public Store() {

		randomizeStore();
	}

	public static void randomizeStore() {
		int randomItems = 8;
		randomStuff[0] = new Upgrade(":chart:", "Stocks", (int) (Math.random() * 100000), (int) (Math.random() * 100),
				(int) (Math.random() * 30));

		Main.replaceAllEverywhere(randomStuff[0]);

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

		Collections.sort(store);
		lastRandomized = OffsetDateTime.now();

	}

	public static void addToStore(Upgrade up) {
		for (int i = 0; i < store.size(); i++) {
			Upgrade u = store.get(i);
			if (up.getName().equals(u.getName())) {
				u.plusOne();
				return;
			}
		}

		store.add(new Upgrade(up));
		Collections.sort(store);

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
		} else if (message.startsWith(Main.GET_BUY_STRING)) {
			buySomething(message.substring(Main.GET_BUY_STRING.length() + 1), e.getAuthor());
		} else if (message.startsWith(Main.GET_ADD_STRING)) {
			if (e.getAuthor().getId().equals("351804839820525570")) {
				PointsAdder.addCoins(e.getAuthor().getId(),
						Long.parseLong(message.substring(Main.GET_ADD_STRING.length() + 1)));
			}
		} else if (message.startsWith(Main.PREFIX + "sell")) {
			sellUpgrade(e);
		}

	}

	private void sellUpgrade(MessageReceivedEvent e) {
		String[] mList = e.getMessage().getContentRaw().split(" ");

		String name = mList[1];

		for (int i = 2; i < mList.length; i++) {
			name += " " + mList[i];
		}

		Upgrade theUpgrade = null;

		for (Upgrade up : Main.upgrades.get(e.getAuthor().getId())) {
			if (up.getName().toLowerCase().equals(name.toLowerCase())) {
				theUpgrade = up;
				break;
			}
		}

		if (removeItem(e.getAuthor().getId(), name)) {
			int paid = (int) (theUpgrade.getCost() * (Math.random() * 0.2 + 0.85));
			if (theUpgrade.getCost() == 0) {
				paid = (int) (10000 * (Math.random() * 0.2 + 0.85));
			}
			PointsAdder.addCoins(e.getAuthor().getId(), paid);
			e.getChannel().sendMessage(e.getAuthor().getAsMention() + " was paid " + paid + Main.CURRENCY + " for his "
					+ theUpgrade.getName() + ".").queue();
			addToStore(theUpgrade);
		} else {
			e.getChannel().sendMessage("Couldn't remove " + name).queue();
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
			if (up.getName().toLowerCase().equals(thingToPurchase.toLowerCase())
					|| up.getNamePrefix().toLowerCase().equals(thingToPurchase.toLowerCase())) {
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
				}
				return true;
			}
		}

		return false;
	}

	public static Upgrade removeAndReturnItem(String id, String upgradeName) {
		for (Upgrade up : Main.upgrades.get(id)) {
			if (up.getName().toLowerCase().equals(upgradeName.toLowerCase())) {
				up.minusOne();
				Commands.addCommand("remove " + id + " " + upgradeName);
				if (up.getQuantity() < 1) {
					Main.upgrades.get(id).remove(up);
				}
				return up;
			}
		}

		return null;
	}

	public static boolean hasItem(String id, String upgradeName) {
		for (Upgrade up : Main.upgrades.get(id)) {
			if (up.getName().toLowerCase().equals(upgradeName.toLowerCase())) {
				return true;
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

	public static String outdatedPrefix = "**V**";
	public static Upgrade[] pastUpgrades = {
			new Upgrade(outdatedPrefix, "Limited-Edition Collector" + apostrophe + "s Edition Easter Egg", 1000, 100,
					5),
			new Upgrade(outdatedPrefix, "Yay", 30, 1, 20), new Upgrade(outdatedPrefix, "Hmm", 10, 10, 1),
			new Upgrade(outdatedPrefix, "Hax", 1, 500, 1), new Upgrade(outdatedPrefix, "Lol", 42, 24, 1),
			new Upgrade(outdatedPrefix, "Crisp $1,000,000,000 bill", 999999999, 0, 100),
			new Upgrade("This is random", (int) (Math.random() * 100000), (int) (Math.random() * 100),
					(int) (Math.random() * 30)),
			new Upgrade("**\u221E**", "Soul Stone", 1000000, 1000, 1),
			new Upgrade("**\u221E**", "Mind Stone", 1000000, 1000, 1),
			new Upgrade("**\u221E**", "Reality Stone", 1000000, 1000, 1),
			new Upgrade("**\u221E**", "Space Stone", 1000000, 1000, 1),
			new Upgrade("**\u221E**", "Time Stone", 1000000, 1000, 1),
			new Upgrade("**\u221E**", "Power Stone", 1000000, 1000, 1),
			// new Upgrade("Discord", Long.MAX_VALUE, Integer.MAX_VALUE, 1),
	};
}