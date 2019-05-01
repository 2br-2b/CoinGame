package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class StandardUpgradeMerge extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getMessage().getContentRaw().startsWith(Main.PREFIX + "merge")) { //

			String[] items = e.getMessage().getContentRaw().substring(Main.PREFIX.length() + 6).split(",");
			if (items.length < 2)
				return;

			ArrayList<String> itemA = new ArrayList<String>();
			for (String s : items) {
				s = s.trim();
				if (Store.hasItem(e.getAuthor().getId(), s)) {
					itemA.add(s);
				} else {
					e.getChannel().sendMessage("You don't have a `" + s + "`!").queue();
					return;
				}
			}

			UpgradeMerge.completeMerge(itemA, e.getChannel(), e.getAuthor().getId());

		}
	}
}
