package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.MessageChannel;
import scars.Scar;

public class MergeHandler {

	public static ArrayList<Mergable> possibleMerges;
	public static String prefix = "**M**";

	public MergeHandler() {
		possibleMerges = new ArrayList<Mergable>();
		possibleMerges.clear();

		possibleMerges.add(new Mergable("Sword", "Shield", new Upgrade(prefix + " " + Store.weaponPrefix, "Knight",
				10000, 30, 1, new Scar(Store.swordWord, 13, "Knight"))));
		possibleMerges.add(new Mergable("Winning Lottery Ticket", "Easy Button",
				new Upgrade(prefix, "An Easy Win", 100000, 100, 1)));
		possibleMerges.add(
				new Mergable("Nuclear Bomb", "Rocket", new Upgrade(prefix + " " + Store.weaponPrefix, "Nuclear Missile",
						5900000, 450, 5, new Scar("Nuclear " + Store.explosionWord, 30, "Nuclear Missile"))));
		possibleMerges.add(new Mergable("Popcorn", "Rocket", new Upgrade(prefix, "Popped Popcorn", 10000, 10)));
		possibleMerges.add(new Mergable("Kirk" + Store.apostrophe + "s Glasses", "Mario" + Store.apostrophe + "s Hat",
				new Upgrade(prefix, "A Great Disguise", 10000000, 500)));
		possibleMerges.add(new Mergable("Nuclear Missile", "Easy Button", new Upgrade(prefix + " " + Store.weaponPrefix,
				"Nuclear War", 1000000, 1000, 1, new Scar("Nuclear " + Store.explosionWord, 100, "Nuclear War"))));
		possibleMerges.add(new Mergable("Blender (for food)", "Blender (the program)",
				new Upgrade(prefix, "Blender (the world)", 279, 280)));
		possibleMerges.add(new Mergable("Hmm", "Hax", new Upgrade(prefix, "Ham", 300, 500)));
		possibleMerges.add(new Mergable("Mjolnir", "Captain America" + Store.apostrophe + " Shield",
				new Upgrade(prefix + " " + Store.weaponPrefix, "The Avengers" + Store.apostrophe + " Weapons",
						10708070000L, 10000, 1,
						new Scar("Vengence", 30, "The Avengers" + Store.apostrophe + " Weapons"))));
		possibleMerges.add(new Mergable("Mjolnir", "Stormbreaker", new Upgrade(prefix + " " + Store.weaponPrefix,
				"Thor", 2011201700, 13579, 1, new Scar("Thunderstruck", 20, "Thor"))));
		possibleMerges.add(new Mergable("Debug Byte", "Debug Byte", new Upgrade(prefix, "Debug Kilobyte", 1000, 1)));
		String[] l = { "Soul Stone", "Mind Stone", "Reality Stone", "Space Stone", "Time Stone", "Power Stone" };
		possibleMerges.add(new Mergable(l, new Upgrade(prefix + " " + Store.weaponPrefix + " **\u221E**",
				"Infinite Power", 66666666, 654321, 1, new Scar("Snapped out of existance", 10000, "Infinite Power"))));

		l = new String[6];
		for (int i = 0; i < l.length; i++) {
			l[i] = "Shards of Narsil";
		}
		possibleMerges.add(new Mergable(l, new Upgrade(prefix + " " + Store.weaponPrefix, "Anduril", 150000, 500, 1,
				new Scar("Slash Wound", 5, "Anduril"))));

		possibleMerges.add(new Mergable("Genesis Device", "Kirk's Glasses",
				new Upgrade(prefix, "KHAAAAAAAAAAN", 100000000, 10000)));

	}

	public static Upgrade getNewUpgrade(Upgrade u1, Upgrade u2) {
		return getNewUpgrade(u1.getName(), u2.getName());
	}

	public static Upgrade getNewUpgrade(String u1, String u2) {
		ArrayList<String> al = new ArrayList<String>();
		al.add(u1);
		al.add(u2);
		return getNewUpgrade(al);
	}

	public static Upgrade getNewUpgrade(ArrayList<String> u) {
		for (Mergable m : possibleMerges) {
			if (m.isMerge(u)) {
				return new Upgrade(m.getUpgrade());
			}
		}

		return null;
	}

	public static void completeMerge(ArrayList<String> items, MessageChannel channel, String id) {
		if (getNewUpgrade(items) != null) {

			for (String i : items) {
				if (!Store.removeItem(id, i)) {
					channel.sendMessage("Something went wrong.").queue();
					return;
				}
			}

			Store.giveUserUpgrade(id, MergeHandler.getNewUpgrade(items));

			channel.sendMessage("<@" + id + "> merged " + items + " to make a `"
					+ MergeHandler.getNewUpgrade(items).getName() + "`!").queue();

		} else {
			channel.sendMessage("You can't merge " + items + "!").queue();
			return;
		}

	}

	public static void completeMerge(String item1, String item2, MessageChannel c, String authorID) {
		ArrayList<String> items = new ArrayList<String>();
		items.add(item1);
		items.add(item2);
		completeMerge(items, c, authorID);
	}

	public static boolean checkIfSame(ArrayList<String> l1, ArrayList<String> l2) {
		if (l1 == null && l2 == null) {
			return true;
		} else if (l1 == null || l2 == null) {
			return false;
		}

		ArrayList<String> a = new ArrayList<String>(l1);
		ArrayList<String> b = new ArrayList<String>(l2);

		/*
		 * System.out.println("list 1 copy: " + a); System.out.println("list 2 copy: " +
		 * b);
		 * 
		 * System.out.print("list 1 size:" + a.size()); System.out.println("");
		 */
		if (a.size() == b.size()) {
			for (int i = 0; i < a.size(); i++) {
				a.set(i, a.get(i).toLowerCase());
				b.set(i, b.get(i).toLowerCase());
			}
		} else {
			return false;
		}

		int j = a.size();
		for (int i = 0; i < j; i++) {
			String s = a.get(0);
			if (!(a.remove(s) && b.remove(s))) {
				return false;
			}

			/*
			 * System.out.println(""); System.out.println("iteration " + (i + 1));
			 * System.out.println("string: " + s); System.out.println("list 1 copy: " + a);
			 * System.out.println("list 2 copy: " + b);
			 */

		}
		/*
		 * System.out.println(""); System.out.println("");
		 * System.out.println("list 2 copy with removed stuff: " + b);
		 */

		if (b.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
