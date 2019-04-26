package src;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class UpgradeMerge extends ListenerAdapter {

	public static ArrayList<Mergable> possibleMerges;
	public static final EventWaiter waiter = new EventWaiter();

	public UpgradeMerge() {
		possibleMerges = new ArrayList<Mergable>();
		possibleMerges.clear();
		possibleMerges.add(new Mergable("Sword", "Shield", new Upgrade("Knight", 10000, 30, 1)));
		possibleMerges
				.add(new Mergable("Winning Lottery Ticket", "Easy Button", new Upgrade("An Easy Win", 100000, 100, 1)));
		possibleMerges
				.add(new Mergable("Hax", "Lottery Ticket", new Upgrade("Winning Lottery Ticket", Lottery.cost, 1)));
		// Nuclear Missile
		possibleMerges.add(new Mergable("Nuclear Bomb", "Rocket", new Upgrade(Store.randomStuff[8])));
		possibleMerges.add(new Mergable("Popcorn", "Rocket", new Upgrade("Popped Popcorn", 10000, 10)));
		possibleMerges.add(new Mergable("Kirk" + Store.apostrophe + "s Glasses", "Mario" + Store.apostrophe + "s Hat",
				new Upgrade("A Great Disguise", 10000000, 500)));
		possibleMerges.add(new Mergable("Nuclear Missile", "Easy Button", new Upgrade("Nuclear War", 0, 1)));
		possibleMerges.add(new Mergable("Blender (for food)", "Blender (the program)",
				new Upgrade("Blender (the world)", 279, 280)));
		possibleMerges.add(new Mergable("Hmm", "Hax", new Upgrade("Ham", 300, 500)));
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
		if (e.getMessage().getContentRaw().contains(Main.PREFIX + "merge")) {
			System.out.print("1");
			e.getChannel().sendMessage("Give me the first mergable upgrade.").queue();
			System.out.print("2");
			waiter.waitForEvent(GuildMessageReceivedEvent.class,
					event -> event.getAuthor().equals(e.getAuthor()) && event.getChannel().equals(e.getChannel()),
					event -> {
						System.out.print("3");

						System.out.println("OneDone");
						String number1 = event.getMessage().getContentRaw();
						if (!Store.hasItem(event.getAuthor().getId(), number1)) {
							event.getChannel().sendMessage("You don't have a `" + number1 + "`!").queue();
							return;
						}

						event.getChannel().sendMessage("Give me the second mergable upgrade.").queue();
						waiter.waitForEvent(GuildMessageReceivedEvent.class,
								event2 -> event2.getAuthor().equals(event.getAuthor())
										&& event2.getChannel().equals(event.getChannel()),
								event2 -> {

									System.out.println("TwoDone");
									String number2 = event2.getMessage().getContentRaw();
									if (!Store.hasItem(event.getAuthor().getId(), number2)) {
										event.getChannel().sendMessage("You don't have a `" + number2 + "`!").queue();
										return;
									}

									if (getNewUpgrade(number1, number2) != null) {
										System.out.println("Yay!");

										if (Store.removeItem(e.getAuthor().getId(), number1)
												&& Store.removeItem(e.getAuthor().getId(), number2)) {

											Store.giveUserUpgrade(e.getAuthor().getId(),
													getNewUpgrade(number1, number2));

											event2.getChannel()
													.sendMessage(event2.getAuthor().getAsMention() + " merged a "
															+ number1 + " and a " + number2 + " to make a "
															+ getNewUpgrade(number1, number2).getName() + "!")
													.queue();
										} else {
											event2.getChannel().sendMessage("Something went wrong.").queue();
										}
									} else {
										event2.getChannel()
												.sendMessage("You can't merge a " + number1 + " and a " + number2 + "!")
												.queue();
										return;
									}

								}, 30, TimeUnit.SECONDS, () -> e.getChannel()
										.sendMessage("You did not give me a second Upgrade. Try again.").queue());
					}, 30, TimeUnit.SECONDS,
					() -> e.getChannel().sendMessage("You did not give me an Upgrade. Try again.").queue());
		}
	}

	private class Mergable {
		String u1, u2;
		Upgrade upgrade;

		public Mergable(String u1name, String u2name, Upgrade upgrade) {
			u1 = u1name;
			u2 = u2name;
			this.upgrade = upgrade;
		}

		public boolean isMerge(String u1name, String u2name) {
			if (u1name.equals(u1) && u2name.equals(u2) || u1name.equals(u2) && u2name.equals(u1)) {
				return true;
			} else {
				return false;
			}
		}

		public Upgrade getUpgrade() {
			return new Upgrade(upgrade);
		}

	}

}
