package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class UpgradeMerge extends ListenerAdapter {

	public static ArrayList<Mergable> possibleMerges;

	public UpgradeMerge() {
		possibleMerges.add(new Mergable("Sword", "Shield", new Upgrade("Knight", 10000, 30, 1)));
		possibleMerges
				.add(new Mergable("Winning Lottery Ticket", "Easy Button", new Upgrade("A Disaster", 10000, 0, 1)));
		possibleMerges
				.add(new Mergable("Hax", "Lottery Ticket", new Upgrade("Winning Lottery Ticket", Lottery.cost, 1)));
	}

	public static Upgrade getNewUpgrade(Upgrade u1, Upgrade u2) {
		for (Mergable m : possibleMerges) {
			if (m.isMerge(u1.getName(), u2.getName())) {
				return new Upgrade(m.getUpgrade());
			}
		}

		return null;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {

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
