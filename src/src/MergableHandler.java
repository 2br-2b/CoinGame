package src;

import java.util.ArrayList;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MergableHandler extends ListenerAdapter {

	public static ArrayList<Mergable> possibleMerges;
	public static final EventWaiter waiter = new EventWaiter();
	public static String prefix = "**M**";

	public MergableHandler() {
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
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getMessage().getContentRaw().startsWith(Main.PREFIX + "merge")) {
			// System.out.print("1");

			String[] items = e.getMessage().getContentRaw().substring(Main.PREFIX.length() + 5).split(",");
			for (int i = 0; i < items.length; i++) {
				items[i] = items[i].trim();
				if (!Store.hasItem(e.getAuthor().getId(), items[i])) {
					e.getChannel().sendMessage("You don't have a `" + items[i] + "`!").queue();
					return;
				}
			}
			if (getNewUpgrade(items[0], items[1]) != null) {

				if (Store.removeItem(e.getAuthor().getId(), items[0])
						&& Store.removeItem(e.getAuthor().getId(), items[1])) {

					Store.giveUserUpgrade(e.getAuthor().getId(), getNewUpgrade(items[0], items[1]));

					e.getChannel()
							.sendMessage(e.getAuthor().getAsMention() + " merged a `" + items[0] + "` and a `"
									+ items[1] + "` to make a `" + getNewUpgrade(items[0], items[1]).getName() + "`!")
							.queue();
				} else {
					e.getChannel().sendMessage("Something went wrong.").queue();
				}
			} else {
				e.getChannel().sendMessage("You can't merge a `" + items[0] + "` and a `" + items[1] + "`!").queue();
				return;
			}

			/*
			 * String[] number1 = e.getMessage().getContentRaw().split(" ", 2); if
			 * (!Store.hasItem(e.getAuthor().getId(), number1[1])) {
			 * e.getChannel().sendMessage("You don't have a `" + number1[1] + "`!").queue();
			 * return; } System.out.println("OneDone");
			 * e.getChannel().sendMessage("Give me the second mergable upgrade.").queue();
			 * waiter.waitForEvent(GuildMessageReceivedEvent.class, event2 ->
			 * event2.getAuthor().equals(e.getAuthor()) &&
			 * event2.getChannel().equals(e.getChannel()), event2 -> {
			 * 
			 * System.out.println("TwoDone"); String number2 =
			 * event2.getMessage().getContentRaw(); if
			 * (!Store.hasItem(event2.getAuthor().getId(), number2)) {
			 * event2.getChannel().sendMessage("You don't have a `" + number2 +
			 * "`!").queue(); return; }
			 * 
			 * if (getNewUpgrade(number1[1], number2) != null) { System.out.println("Yay!");
			 * 
			 * if (Store.removeItem(e.getAuthor().getId(), number1[1]) &&
			 * Store.removeItem(e.getAuthor().getId(), number2)) {
			 * 
			 * Store.giveUserUpgrade(e.getAuthor().getId(), getNewUpgrade(number1[1],
			 * number2));
			 * 
			 * event2.getChannel() .sendMessage(event2.getAuthor().getAsMention() +
			 * " merged a " + number1 + " and a " + number2 + " to make a " +
			 * getNewUpgrade(number1[1], number2).getName() + "!") .queue(); } else {
			 * event2.getChannel().sendMessage("Something went wrong.").queue(); } } else {
			 * event2.getChannel().sendMessage("You can't merge a " + number1 + " and a " +
			 * number2 + "!") .queue(); return; }
			 * 
			 * }, 30, TimeUnit.SECONDS, () ->
			 * e.getChannel().sendMessage("You did not give me a second Upgrade. Try again."
			 * ).queue());
			 */}
	}

}
