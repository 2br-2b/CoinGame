
package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GetStuff extends ListenerAdapter {

	private static MessageChannel c = null;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		String message = e.getMessage().getContentRaw();
		c = e.getChannel();

		if (message.equals(Main.GET_MONEY_STRING)) {
			String str = e.getAuthor().getName() + " has " + Main.bal.get(e.getAuthor()) + Main.CURRENCY;
			System.out.println(str);
			c.sendMessage(str).queue();

		} else if (message.equals(Main.GET_INVENTORY_STRING)) {
			String str = e.getAuthor().getName() + "'s inventory:\n" + Main.bal.get(e.getAuthor()) + Main.CURRENCY;

			int boost = 0;

			if (Main.upgrades.containsKey(e.getAuthor())) {
				ArrayList<Upgrade> list = Main.upgrades.get(e.getAuthor());
				for (Upgrade u : list) {
					boost += u.getTotalBoost();
					str += "\n`" + u.getQuantity() + "x` " + u.getName();
				}
			}

			str += "\nTotal boost: " + boost;

			System.out.println(str);
			c.sendMessage(str).queue();

		}

	}
}
