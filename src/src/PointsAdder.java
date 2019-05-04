/**
 * @author GreedyVagabond
 * @author 2br-2b
 */

package src;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PointsAdder extends ListenerAdapter {
	public static HashMap<String, OffsetDateTime> coolingDown = new HashMap<String, OffsetDateTime>();
	public static final int COOLDOWN_SECONDS = 20;
	private static final int MONEY_PER_MESSAGE = 1;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		String message = e.getMessage().getContentRaw();
		String[] mList = message.split(" ");

		if (mList[0].equals(Main.PREFIX + "pay")) {
			try {
				try {
					if (userGiveUserCoins(e.getAuthor().getId(), Math.abs(Integer.parseInt(mList[2])),
							mList[1].replace("!", "").substring(2, 20))) {
						e.getChannel().sendMessage(
								e.getAuthor().getAsMention() + " paid " + mList[1] + " " + mList[2] + Main.CURRENCY)
								.queue();
					} else {
						e.getChannel().sendMessage("Something went wrong.  Please try again.").queue();
					}
				} catch (java.lang.NumberFormatException ex) {
					e.getChannel().sendMessage(mList[2] + "is not a number!");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (coolingDown.containsKey(e.getAuthor().getId())) {
			if (e.getMessage().getCreationTime()
					.isAfter(coolingDown.get(e.getAuthor().getId()).plusSeconds(COOLDOWN_SECONDS))) {

				int boost = 0;
				if (Main.upgrades.containsKey(e.getAuthor().getId())) {
					ArrayList<Upgrade> list = Main.upgrades.get(e.getAuthor().getId());
					for (Upgrade u : list) {
						boost += u.getTotalBoost();
					}

				}

				for (Scar s : ScarHandler.getScars(e.getAuthor().getId())) {
					boost += s.getTotalBoost();
				}

				if (boost < 1) {
					boost = 1;
				}

				addCoins(e.getAuthor().getId(), MONEY_PER_MESSAGE + boost);
				coolingDown.put(e.getAuthor().getId(), e.getMessage().getCreationTime());
			} else
				return;
		} else {

			int boost = 0;
			if (Main.upgrades.containsKey(e.getAuthor().getId())) {
				ArrayList<Upgrade> list = Main.upgrades.get(e.getAuthor().getId());
				for (Upgrade u : list) {
					boost += u.getBoost();
				}
			}

			for (Scar s : ScarHandler.getScars(e.getAuthor().getId())) {
				boost += s.getTotalBoost();
			}

			if (boost < 1) {
				boost = 1;
			}

			addCoins(e.getAuthor().getId(), MONEY_PER_MESSAGE + boost);
			coolingDown.put(e.getAuthor().getId(), e.getMessage().getCreationTime());

		}

	}

	public static void addCoins(String id, long amount) {
		if (Main.bal.containsKey(id)) {
			Main.bal.put(id, Main.bal.get(id) + amount);
		} else {
			Main.bal.put(id, 1000000 + amount);
		}

		Commands.addCommand("givemoney " + id + " " + amount);
	}

	public static boolean payCoins(String id, long amount) {
		if (Main.bal.containsKey(id)) {
			if (Main.bal.get(id) < amount) {
				return false;
			} else {
				Main.bal.put(id, Main.bal.get(id) - amount);
				Commands.addCommand("givemoney " + id + " -" + amount);
				return true;
			}
		} else {
			return false;
		}

	}

	public static boolean userGiveUserCoins(String giver, long amount, String given) {
		if (payCoins(giver, amount)) {
			addCoins(given, amount);
			return true;
		}
		return false;
	}
}
