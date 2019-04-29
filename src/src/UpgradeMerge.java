package src;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

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
	}

	public UpgradeMerge(EventWaiter w) {
		this();
		super.name = "merge";
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

	@Override
	protected void execute(CommandEvent event) {
		Boolean didExecute = false;
		event.reply("Ok! Now give me the first upgrade to merge.");
		waiter.waitForEvent(GuildMessageReceivedEvent.class,
				e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel()), e -> {
					String firstUpgrade = e.getMessage().getContentRaw();

					if (!Store.hasItem(e.getAuthor().getId(), firstUpgrade)) {
						e.getChannel().sendMessage("You don't have a `" + firstUpgrade + "`!").queue();
						return;
					}
					didExecute = true;
					event.reply("Ok! Now give me the second upgrade to merge."); // Only do this if the purchase was
																					// successful
				}, 30, TimeUnit.SECONDS, () -> event.reply("You did not give me an upgrade. Try again."));

		if (didExecute) {
			waiter.waitForEvent(GuildMessageReceivedEvent.class,
					e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel()), e -> {
						String secondUpgrade = e.getMessage().getContentRaw();
						if (!Store.hasItem(e.getAuthor().getId(), secondUpgrade)) {
							e.getChannel().sendMessage("You don't have a `" + secondUpgrade + "`!").queue();
							return;
						}

					}, 30, TimeUnit.SECONDS, () -> event.reply("You did not give me an upgrade. Try again."));
		}

		// make sure you serialize the new data.
	}

}
