package src;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class UpgradeMerge extends Command {

	public static ArrayList<Mergable> possibleMerges;
	public static EventWaiter waiter = new EventWaiter();
	public static String prefix = "**M**";
	public static Thread merge;

	public UpgradeMerge() {
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

	public UpgradeMerge(EventWaiter w) {
		this();
		super.name = "merge";
		super.hidden = true;
		waiter = w;
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

	@Override
	protected void execute(CommandEvent event) {
		if (Math.random() < 2)
			return;

		if (event.getAuthor().isBot() && !Main.BOTS_ALLOWED)
			return;

		if (event.getMessage().getContentRaw().contains(","))
			return;

		int repetitions = 2;
		try {
			repetitions = Integer.parseInt(event.getArgs());
		} catch (Exception e) {
		}

		merge = new MergeThread(event, repetitions);
		merge.start();

	}

	public static void startWaiter(int i, int repetitions, CommandEvent event) {
		ArrayList<String> items = new ArrayList<String>();
		event.reply("Ok! Now give me the upgrade to merge (" + (i + 1) + " of  " + repetitions + ").");
		waiter.waitForEvent(GuildMessageReceivedEvent.class,
				e -> e.getAuthor().getId().equals(event.getAuthor().getId())
						&& e.getChannel().getId().equals(event.getChannel().getId()),
				e -> {
					String firstUpgrade = e.getMessage().getContentRaw();

					System.out.println(1);
					if (!Store.hasItem(e.getAuthor().getId(), firstUpgrade)) {
						e.getChannel().sendMessage("You don't have a `" + firstUpgrade + "`!").queue();
						return;
					}

					items.add(firstUpgrade);
					merge.notify();
				}, 30, TimeUnit.SECONDS, () -> event.reply("You did not give me an upgrade. Try again."));
		System.out.println(2);
		completeMerge(items, event.getChannel(), event.getAuthor().getId());

	}

	public static void completeMerge(ArrayList<String> items, MessageChannel channel, String id) {
		if (getNewUpgrade(items) != null) {

			for (String i : items) {
				if (!Store.removeItem(id, i)) {
					channel.sendMessage("Something went wrong.").queue();
					return;
				}
			}

			Store.giveUserUpgrade(id, UpgradeMerge.getNewUpgrade(items));

			channel.sendMessage("<@" + id + "> merged " + items + " to make a `"
					+ UpgradeMerge.getNewUpgrade(items).getName() + "`!").queue();

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

	/**
	 * 
	 * 
	 * @author 182245310024777728
	 * @param waiter
	 * @param amount
	 * @param filter
	 * @return
	 */
	public static CompletionStage<List<Message>> awaitMessages(EventWaiter waiter, int amount,
			Predicate<MessageReceivedEvent> filter) {
		var future = new CompletableFuture<List<Message>>();
		awaitMessages0(waiter, amount, filter, future, new ArrayList<>());
		return future;
	}

	/**
	 * 
	 * 
	 * 
	 * @author 182245310024777728
	 * @param waiter
	 * @param amount
	 * @param filter
	 * @param future
	 * @param list
	 */
	private static void awaitMessages0(EventWaiter waiter, int amount, Predicate<MessageReceivedEvent> filter,
			CompletableFuture<List<Message>> future, List<Message> list) {
		if (amount == 0) {
			future.complete(list);
			return;
		}
		waiter.waitForEvent(MessageReceivedEvent.class, filter, event -> {
			list.add(event.getMessage());
			awaitMessages0(waiter, amount - 1, filter, future, list);
		});
	}

}
