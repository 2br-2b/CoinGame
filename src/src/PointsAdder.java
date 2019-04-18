/**
 * @author GreedyVagabond
 */

package src;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PointsAdder extends ListenerAdapter {
	private HashMap<User, OffsetDateTime> coolingDown = new HashMap<User, OffsetDateTime>();
	public static final int COOLDOWN_SECONDS = 60;
	private static final int MONEY_PER_MESSAGE = 1;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		if (coolingDown.containsKey(e.getAuthor())) {
			if (e.getMessage().getCreationTime()
					.isAfter(coolingDown.get(e.getAuthor()).plusSeconds(COOLDOWN_SECONDS))) {

				if (e.getMessage().getContentRaw().equalsIgnoreCase(Main.GET_MONEY_STRING))
					return;

				int boost = 0;
				if (Main.upgrades.containsKey(e.getAuthor())) {
					ArrayList<Upgrade> list = Main.upgrades.get(e.getAuthor());
					for (Upgrade u : list) {
						boost += u.getBoost();
					}
				}

				addCoins(e.getAuthor(), MONEY_PER_MESSAGE + boost);
				coolingDown.put(e.getAuthor(), e.getMessage().getCreationTime());
			} else
				return;
		} else {
			if (e.getMessage().getContentRaw().equalsIgnoreCase(Main.GET_MONEY_STRING))
				return;

			int boost = 0;
			if (Main.upgrades.containsKey(e.getAuthor())) {
				ArrayList<Upgrade> list = Main.upgrades.get(e.getAuthor());
				for (Upgrade u : list) {
					boost += u.getBoost();
				}
			}

			addCoins(e.getAuthor(), MONEY_PER_MESSAGE + boost);
			coolingDown.put(e.getAuthor(), e.getMessage().getCreationTime());

		}
	}

	public static void addCoins(User u, long amount) {
		if (Main.bal.containsKey(u)) {
			Main.bal.put(u, Main.bal.get(u) + amount);
		} else {
			Main.bal.put(u, 1000000 + amount);
		}
	}

	public static boolean payCoins(User u, long amount) {
		if (Main.bal.containsKey(u)) {
			if (Main.bal.get(u) < amount) {
				return false;
			} else {
				Main.bal.put(u, Main.bal.get(u) - amount);
				return true;
			}
		} else {
			return false;
		}

	}
}