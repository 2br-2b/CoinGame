package src;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class UpgradeMerge extends Command {

	public static ArrayList<Mergable> possibleMerges;
	public static EventWaiter waiter = new EventWaiter();
	public static String prefix = "**M**";

	public UpgradeMerge() {
		possibleMerges = new ArrayList<Mergable>();
		possibleMerges.clear();

		possibleMerges.add(new Mergable("Sword", "Shield", new Upgrade(prefix, "Knight", 10000, 30, 1)));
		possibleMerges.add(new Mergable("Winning Lottery Ticket", "Easy Button",
				new Upgrade(prefix, "An Easy Win", 100000, 100, 1)));
		possibleMerges
				.add(new Mergable("Nuclear Bomb", "Rocket", new Upgrade(prefix, "Nuclear Missile", 5900000, 450, 5)));
		possibleMerges.add(new Mergable("Popcorn", "Rocket", new Upgrade(prefix, "Popped Popcorn", 10000, 10)));
		possibleMerges.add(new Mergable("Kirk" + Store.apostrophe + "s Glasses", "Mario" + Store.apostrophe + "s Hat",
				new Upgrade(prefix, "A Great Disguise", 10000000, 500)));
		possibleMerges
				.add(new Mergable("Nuclear Missile", "Easy Button", new Upgrade(prefix, "Nuclear War", 1000000, 1000)));
		possibleMerges.add(new Mergable("Blender (for food)", "Blender (the program)",
				new Upgrade(prefix, "Blender (the world)", 279, 280)));
		possibleMerges.add(new Mergable("Hmm", "Hax", new Upgrade(prefix, "Ham", 300, 500)));
		possibleMerges.add(new Mergable("Mjolnir", "Captain America" + Store.apostrophe + " Shield",
				new Upgrade(prefix, "The Avengers' Weapons", 10708070000L, 10000)));
		possibleMerges.add(new Mergable("Mjolnir", "Stormbreaker", new Upgrade(prefix, "Thor", 2011201700, 13579)));
		possibleMerges.add(new Mergable("Debug Byte", "Debug Byte", new Upgrade(prefix, "Debug Kilobyte", 1000, 1)));
		String[] l = { "Soul Stone", "Mind Stone", "Reality Stone", "Space Stone", "Time Stone", "Power Stone" };
		possibleMerges.add(new Mergable(l, new Upgrade(prefix, "Infinite Power", 66666666, 654321)));

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
		for (Mergable m : possibleMerges) {
			if (m.isMerge(u1, u2)) {
				return new Upgrade(m.getUpgrade());
			}
		}

		return null;
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
		if (event.getAuthor().isBot() && !Main.BOTS_ALLOWED)
			return;

		int repetitions = 2;
		try {
			repetitions = Integer.parseInt(event.getArgs());
		} catch (Exception e) {
		}

		ArrayList<String> items = new ArrayList<String>();
		int i = 0;
		while (i < repetitions) {
			event.reply("Ok! Now give me the upgrade to merge (" + (i + 1) + " of  " + repetitions + ").");
			waiter.waitForEvent(GuildMessageReceivedEvent.class,
					e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel()), e -> {
						String firstUpgrade = e.getMessage().getContentRaw();

						if (!Store.hasItem(e.getAuthor().getId(), firstUpgrade)) {
							e.getChannel().sendMessage("You don't have a `" + firstUpgrade + "`!").queue();
							return;
						}

						items.add(firstUpgrade);
					}, 30, TimeUnit.SECONDS, () -> event.reply("You did not give me an upgrade. Try again."));

			i++;
		}

		completeMerge(items, event.getChannel(), event.getAuthor().getId());
		// make sure you serialize the new data.
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
		ArrayList<String> a2 = new ArrayList<String>(a);
		ArrayList<String> b = new ArrayList<String>(l2);

		for (Object o : a2) {
			if (!(a.remove(o) && b.remove(o))) {
				return false;
			}
		}

		if (b.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
