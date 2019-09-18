
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

	public static Upgrade[] randomStuff = { new Upgrade("International Space Station", 50400000000L, 196199000, 1),
			new Upgrade("Diamond Armor", 5307786900L, 25100000, 1),
			new Upgrade("Hubble Telescope", 2870000000L, 14320231, 1),
			new Upgrade("Baby Shark", 2639860696L, 13199303, 1),
			new Upgrade("Infinity Gauntlet", 2147483647, 13166600, 1),
			new Upgrade("Wayne Manor", 800000000, 3970009, 1),
			new Upgrade("Because I'm Batman!", 682450750, 3402000, 1),
			new Upgrade("141-year-old newspaper", 230000000, 948324, 1),
			new Upgrade("1963 Ferrari GTO", 52000000, 196300, 5),
			new Upgrade("Honus Wagner Rookie Card", 2000000, 8727, 3),
			new Upgrade("Captain America's Shield", 17871941, 113100, 1),
			new Upgrade("Gold Plated Bugatti Veyron", 10004738, 43253, 1), new Upgrade("Mjolnir", 4490000, 30000, 1),
			new Upgrade("Crystal Piano", 3200000, 14324, 1), new Upgrade("Thanos' Sword", 2014000, 10115, 1),
			new Upgrade("The Tumbler Batmobile", 1800000, 6930, 1),
			new Upgrade("Magnetic Floating Bed", 1600000, 8000, 1), new Upgrade("All of the Pokemon", 1510000, 8090, 1),
			new Upgrade("Ferrari Enzo", 1325000, 5500, 7),
			new Upgrade("The Ultimate Ultimate Weapon", 1230000, 4920, 6),
			new Upgrade("Darth Vader's Helmet", 1138000, 7486, 1), new Upgrade("Crisp $1,000,000 bill", 999999, 0, 100),
			new Upgrade("Tank", 858000, 3750, 10), new Upgrade("Lightsaber", 300000, 1977, 6),
			new Upgrade("Pet Dragon", 630000, 3030, 5), new Upgrade("Smash Ball", 400000, 1500, 6),
			new Upgrade("Legolas's Bow", 301800, 2110, 1), new Upgrade("Phaser Rifle", 200000, 1000, 1),
			new Upgrade("Six-Shooter", 130364, 600, 6), new Upgrade("Kylo Ren's Helmet", 200000, 1350, 1),
			new Upgrade("Nuclear Bomb", 500235, 2390, 5), new Upgrade("Bowcaster", 155893, 563, 10),
			new Upgrade("Genesis Device", 765482, 4570, 1), new Upgrade("A bad feeling about this", 166830, 1138, 1),
			new Upgrade("Death Star", 1344339, 7324, 2), new Upgrade("Rocket", 100000, 500, 10),
			new Upgrade("Ring of Power", 2963500, 19776, 19), new Upgrade("Stormbreaker", 1375947, 10000, 1),
			new Upgrade("Medpack", 100000, 500, 10), new Upgrade("Mario's Hat", 1019358, 5342, 1),
			new Upgrade("Blender (the program)", 530000, 2140, 10), new Upgrade("iPhone XR", 300000, 1215, 1),
			new Upgrade("Stormtrooper Helmet", 13630, 70, 10), new Upgrade("Sandwitch", 22414, 84, 10),
			new Upgrade("Oculus", 39999, 199, 10), new Upgrade("Shards of Narsil", 20000, 97, 6),
			new Upgrade("Batarang", 14645, 70, 10), new Upgrade("Sword", 10000, 51, 100),
			new Upgrade("Water Bottle", 10000, 49, 10), new Upgrade("Easy Button", 667186, 2839, 10),
			new Upgrade("Blender (for food)", 10000, 43, 7), new Upgrade("The Piece of Resistance", 998520, 4950, 1),
			new Upgrade("Shield", 5000, 25, 200), new Upgrade("Shark Repellent Bat Spray", 1996, 10, 4),
			new Upgrade("Kirk's Glasses", 957440, 4329, 1), new Upgrade("Popcorn", 1000, 5, 100),
			new Upgrade("Book", 451, 2, 10), new Upgrade("Cookie", 100, 3, 1), new Upgrade("Lego Brick", 200, 1, 100),
			new Upgrade("Bug", 1, -1, 128), new Upgrade("Arc Reactor", 59637044043L, 141467051, 1),
			new Upgrade("Kryptonite", 2110089696934650L, 4096534023712L, 1),
			new Upgrade("Air Force One", 83250280290413900L, 151941677102307L, 1), new Upgrade("Cheat", 1, 1, 1),
			new Upgrade("Ice Cream", 40896568606L, 97793577, 10),
			new Upgrade("Klondike Bar", 4275149189933540000L, 7331994574249420L, 1),
			new Upgrade("Time Machine", 83745097494464L, 172216610960L, 1), new Upgrade("Bribe", 1000000, 0, 1),
			new Upgrade("Nuclear Fireworks", 81247450573750L, 167173305334L, 1),
			new Upgrade("Meme", 1755841378694L, 3886619829L, 1),
			new Upgrade("Bigfoot", 79837291968785L, 164324651505L, 1),
			new Upgrade("Area 51", 5704058330541L, 12338782071L, 1),
			new Upgrade("Loch Ness Monster", 2221819257185L, 4895303336L, 1),
			new Upgrade("The Moon", 839548670437L, 1885876413, 1),
			new Upgrade("A True Cake", 1248529473245030L, 2446152366149L, 5),
			new Upgrade("MissingNo", 55905021527L, 132795976, 1), new Upgrade("Dog", 17728, 62, 3),
			new Upgrade("Airpods", 37910263806L, 90799614, 2),
			new Upgrade("Iron Man Suit", 1858055186797720L, 3615205690620L, 2), new Upgrade("Debug Byte", 1, 0, 256),

	};

	private static ArrayList<ArrayList<Upgrade>> sortedStore = new ArrayList<>();

	public Store() {

		sortStore();

		for (int i = 0; i < sortedStore.size(); i++) {
			ArrayList<Upgrade> l = sortedStore.get(i);
			System.out.print(i + ": ");
			if (l == null) {
				System.out.println(0);
			} else {
				System.out.println(l.size());
			}

			if (i == 7)
				System.out.println("-----");
		}

		randomizeStore();
	}

	private void sortStore() {

		sortedStore = new ArrayList<>();

		for (int i = 0; i < Math.log(Long.MAX_VALUE); i++) {
			sortedStore.add(new ArrayList<Upgrade>());
		}

		for (Upgrade u : randomStuff) {
			int power = (int) Math.log(u.getCost());
			sortedStore.get(power).add(u);

		}

		while (sortedStore.get(sortedStore.size() - 1).size() == 0) {
			sortedStore.remove(sortedStore.size() - 1);
		}

	}

	public static void randomizeStore() {
		int randomItems = 9;

		// Name, cost, boost, inventory
		store = new ArrayList<>();
		store.clear();

		for (int i = 0; i < randomItems; i++) {
			Upgrade randomUpgrade = randomStuff[(int) (Math.random() * randomStuff.length)];

			while (store.contains(randomUpgrade) || randomUpgrade.getQuantity() < 1
					|| randomUpgrade.getCost() > Integer.MAX_VALUE) {
				randomUpgrade = randomStuff[(int) (Math.random() * randomStuff.length)];
			}

			store.add(randomUpgrade);

		}

		for (int i = 0; i < 2; i++) {
			Upgrade randomUpgrade = randomStuff[(int) (Math.random() * randomStuff.length)];

			while (store.contains(randomUpgrade) || randomUpgrade.getQuantity() < 1
					|| randomUpgrade.getCost() <= Integer.MAX_VALUE) {
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
		if (e.getMessage().getCreationTime().isAfter(lastRandomized.plusHours(1))) {
			randomizeStore();
		}

		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		c = e.getChannel();

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
			new Upgrade(outdatedPrefix, "Limited-Edition Collector's Edition Easter Egg", 1000, 100, 5),
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
			new Upgrade(outdatedPrefix + ":basketball:", "Evan Perlmutter's Fanhood", 3500, 25, 1),
			new Upgrade(outdatedPrefix, "Fire-Breathing Rubber Duckie", 1000, 1, 10),
			new Upgrade(outdatedPrefix, "Magnetic Floating Bed", 1600000, 2000, 1),
			new Upgrade(outdatedPrefix, "insure.com Domain", 16000000, 12345, 1),
			new Upgrade(outdatedPrefix, "Gram of Antimatter", 62500000000L, 7654321, 10),
			new Upgrade(outdatedPrefix, "Huia Bird Feather", 10000, 10, 12),
			new Upgrade(outdatedPrefix, "141-year-old newspaper", 230000000, 141, 1),
			new Upgrade(outdatedPrefix, "Charles Hollander chess set", 600000, 16, 7),

	};
}