package src;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class UpgradeMerge extends Command {

	public static ArrayList<Mergable> possibleMerges;
	public static EventWaiter waiter;

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
	
	public UpgradeMerge(EventWaiter w)
	{
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
	protected void execute(CommandEvent event) 
	{
		Boolean didExecute = false;
		event.reply("Ok! Now give me the first upgrade to merge.");
		waiter.waitForEvent(GuildMessageReceivedEvent.class, e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel()), e -> {
            String firstUpgrade = e.getMessage().getContentRaw();
			//code to buy the first upgrade. Set didExecute to true if the user successfully purchased the first upgrade.
			event.reply("Ok! Now give me the second upgrade to merge."); //Only do this if the purchase was successful
		}, 30, TimeUnit.SECONDS, () -> event.reply("You did not give me an upgrade. Try again."));
		
		if(didExecute)
		{
			waiter.waitForEvent(GuildMessageReceivedEvent.class, e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel()), e -> {
				String secondUpgrade = e.getMessage().getContentRaw();
				//code to buy the second upgrade.
			
			}, 30, TimeUnit.SECONDS, () -> event.reply("You did not give me an upgrade. Try again."));
		}
		
		//make sure you serialize the new data.
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
