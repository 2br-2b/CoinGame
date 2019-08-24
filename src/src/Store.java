
package src;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Store extends ListenerAdapter {

	public static String millionPrefix = "**$**";
	public static String billionPrefix = "**$$**";
	public static String longPrefix = ":gem:";
	public static String weaponPrefix = "";// "**W**";
	public static String healingPrefix = "";// "**H**";
	private static MessageChannel c = null;
	public static ArrayList<Upgrade> store;
	public static OffsetDateTime lastRandomized;

	public static String explosionWord = "Explosion";
	public static String burnWord = "Fiery Explosion";
	public static String swordWord = "Merely a flesh wound!";

	public static Upgrade[] randomStuff = { new Upgrade("Batarang", 10000, 14, 10),
			new Upgrade("Rocket", 100000, 100, 10), new Upgrade("Nuclear Bomb", 190000, 190, 5),
			new Upgrade("Baby Shark", 2639860696L, 2000231, 1), new Upgrade("Smash Ball", 500000, 300, 6),
			new Upgrade("Captain America/'s Shield", 17871941, 15000, 1), new Upgrade("Death Star", 113800, 100, 2),
			new Upgrade("Infinity Gauntlet", Integer.MAX_VALUE, 6666, 1), new Upgrade("Sword", 10000, 3, 100),
			new Upgrade(":ring:", " Ring of Power", 100000, 100, 19), new Upgrade("Stormbreaker", 100000, 1000, 1),
			new Upgrade("Shark Repellent Bat Spray", 1996, 10, 4), new Upgrade("Bowcaster", 150000, 4, 10),
			new Upgrade("Lightsaber", 800000, 1977, 6), new Upgrade("Tank", 858000, 150, 10),
			new Upgrade("Pet Dragon", 630000, 3000, 5), new Upgrade("Mjolnir", 4490000, 3000, 1),
			new Upgrade("The Ultimate Ultimate Weapon", 1230000, 1640, 6),
			new Upgrade("The Tumbler Batmobile", 18000000, 493, 1), new Upgrade("Bug", 1, -1, 128),
			new Upgrade("Shards of Narsil", 20000, 20, 6), new Upgrade("Genesis Device", 123456789, 100, 1),
			new Upgrade("Phaser Rifle", 200000, 1, 1), new Upgrade("Six-Shooter", 200000, 10, 1),
			new Upgrade("Lego Brick", 10, 0, 100), new Upgrade("Medpack", 100000, 100, 10),
			new Upgrade("Sandwitch", 50000, 1, 10), new Upgrade("Water Bottle", 10000, 1, 10),
			new Upgrade("Crisp $1,000,000 bill", 999999, 0, 100),
			new Upgrade("Darth Vader/'s Helmet", 1138000, 1138, 1), new Upgrade("Kylo Ren/'s Helmet", 200000, 135, 1),
			new Upgrade("All of the Pokemon", 1510000, 809, 1), new Upgrade("Shield", 5000, 1, 200),
			new Upgrade("Mario/'s Hat", 100000, 100, 1), new Upgrade("Popcorn", 1000, 1, 100),
			new Upgrade("Debug Byte", 1, 0, 256), new Upgrade("Easy Button", 10000, 13, 10),
			new Upgrade(":cookie:", " Cookie", 100, 30, 10), new Upgrade("A bad feeling about this", 120000, 1138, 1),
			new Upgrade("Blender (for food)", 10000, 2, 7), new Upgrade("Blender (the program)", 100000, 28, 10),
			new Upgrade("Kirk/'s Glasses", 1701, 10, 1), new Upgrade("iPhone XR", 100000, 1, 1),
			new Upgrade("Stormtrooper Helmet", 100000, 5, 10), new Upgrade("Diamond Armor", 5307786900L, 1000, 1),
			new Upgrade("Wayne Manor", 800000000, 150000, 1), new Upgrade("Because I/'m Batman!", 682450750, 54321, 1),
			new Upgrade("Hubble Telescope", 2870000000L, 199000, 1),
			new Upgrade("International Space Station", 50400000000L, 1961990, 1),
			new Upgrade("1963 Ferrari GTO", 52000000, 1963, 5), new Upgrade("Ferrari Enzo", 1325000, 55, 7),
			new Upgrade("Honus Wagner Rookie Card", 21000000, 1909, 3), new Upgrade("Book", 451, 1, 10),
			new Upgrade("The Piece of Resistance", 10000, 69, 1), new Upgrade("Thanos/'Sword", 2014000, 2023, 1),
			new Upgrade("Legolas/'s Bow", 301800, 120, 1), new Upgrade("Oculus", 39999, 10, 10), };


	public Store() {

		randomizeStore();
	}

	public static void randomizeStore() {
		int randomItems = 8;

		// Name, cost, boost, inventory
		store = new ArrayList<>();
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

		String message = e.getMessage().getContentRaw().replaceAll("‘", "'");
		c = e.getChannel();

		if (e.getMessage().getCreationTime().isAfter(lastRandomized.plusHours(1))) {
			randomizeStore();
		}
		if (message.startsWith(Main.PREFIX + "sell")) {
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
			if (up.getName().toLowerCase().equals(name.replaceAll("‘", "'").toLowerCase())) {
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
			e.getChannel().sendMessage(e.getAuthor().getAsMention() + " was paid " + Main.addCommas(paid)
					+ Main.CURRENCY + " for his " + theUpgrade.getName() + ".").queue();
			addToStore(theUpgrade);
		} else {
			e.getChannel().sendMessage("Couldn't remove " + name).queue();
		}

	}

	public static void giveUserUpgrade(User u, Upgrade toBuy, ArrayList<Upgrade> list) {
		if (list == null) {
			list = new ArrayList<>();
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

		toBuy.minusOne();
		if (toBuy.getQuantity() < 1) {
			store.remove(toBuy);
		}
	}

	public static void giveUserUpgrade(String id, Upgrade toBuy) {
		ArrayList<Upgrade> list = Main.upgrades.get(id);
		if (list == null) {
			list = new ArrayList<>();
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
	}

	public static void userGiveUserUpgrade(String idgiver, String togive, String idgiven) {
		userGiveUserUpgrade(idgiver, togive, idgiven, c);
	}

	public static void userGiveUserUpgrade(String idgiver, String togive, String idgiven, MessageChannel c) {
		for (Upgrade up : Main.upgrades.get(idgiver)) {

			if (up.getName().toLowerCase().equals(togive.replaceAll("‘", "'").toLowerCase())) {

				up.minusOne();
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
			if (up.getName().equalsIgnoreCase(upgradeName.replaceAll("‘", "'"))) {
				up.minusOne();
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
			if (up.getName().toLowerCase().equals(upgradeName.replaceAll("‘", "'").toLowerCase())) {
				up.minusOne();
				if (up.getQuantity() < 1) {
					Main.upgrades.get(id).remove(up);
				}
				return up;
			}
		}

		return null;
	}

	public static Upgrade getUsersItem(String id, String upgradeName) {
		for (Upgrade up : Main.upgrades.get(id)) {
			if (up.getName().equalsIgnoreCase(upgradeName.replaceAll("‘", "'"))) {
				return up;
			}
		}
		return null;
	}

	public static boolean hasItem(String id, String upgradeName) {
		for (Upgrade up : Main.upgrades.get(id)) {
			if (up.getName().toLowerCase().equals(upgradeName.replaceAll("‘", "'").toLowerCase())) {
				return true;
			}
		}

		return false;
	}

	public static Upgrade getUpgradeOfName(String name) {
		for (Upgrade u : Main.masterUpgradeList) {
			if (u.getName().equalsIgnoreCase(name.replaceAll("‘", "'"))) {
				return u;
			}
		}
		System.out.println("Couldn't find it!");
		return null;
	}

	public static String outdatedPrefix = "**V**";
	public static Upgrade[] pastUpgrades = {
			new Upgrade(outdatedPrefix, "Limited-Edition Collector/'s Edition Easter Egg", 1000, 100, 5),
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
			// deleted to to integer overflow for the boosts
			// new Upgrade("Discord", Long.MAX_VALUE, Integer.MAX_VALUE, 1),
			new Upgrade(outdatedPrefix, "Stocks", (int) (Math.random() * 100000), (int) (Math.random() * 100),
					(int) (Math.random() * 30)),
			// new Upgrade("Kidney on eBay", 57050000, 2, 1),
			new Upgrade(outdatedPrefix, "Star Wars Meme", 100, 1, 1),

			new Upgrade(outdatedPrefix, "The Physical Impossibility of Death in the Mind of Someone Living", 12000000,
					1991),
			new Upgrade(outdatedPrefix + " :basketball:", " Evan Perlmutter/'s Fanhood", 3500, 25, 1),
			new Upgrade(outdatedPrefix, "Fire-Breathing Rubber Duckie", 1000, 1, 10),
			new Upgrade(outdatedPrefix, "Gold Plated Bugatti Veyron", 10000000, 1500, 1),
			new Upgrade(outdatedPrefix, "Magnetic Floating Bed", 1600000, 2000, 1),
			new Upgrade(outdatedPrefix, "insure.com Domain", 16000000, 12345, 1),
			new Upgrade(outdatedPrefix, "Crystal Piano", 3200000, 2008, 1),
			new Upgrade(outdatedPrefix, "Gram of Antimatter", 62500000000L, 7654321, 10),
			new Upgrade(outdatedPrefix, "Huia Bird Feather", 10000, 10, 12),
			new Upgrade(outdatedPrefix, "141-year-old newspaper", 230000000, 141, 1),
			new Upgrade(outdatedPrefix, "Charles Hollander chess set", 600000, 16, 7),

	};
}