package src;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class StandardUpgradeMerge extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getMessage().getContentRaw().startsWith(Main.PREFIX + "merge")) { //
			System.out.print("1");

			String[] items = e.getMessage().getContentRaw().substring(Main.PREFIX.length() + 5).split(",");
			if (items.length < 2)
				return;

			for (int i = 0; i < items.length; i++) {
				items[i] = items[i].trim();
				if (!Store.hasItem(e.getAuthor().getId(), items[i])) {
					e.getChannel().sendMessage("You don't have a `" + items[i] + "`!").queue();
					return;
				}
			}

			UpgradeMerge.completeMerge(items[0], items[1], e.getChannel(), e.getAuthor().getId());

		}
	}
}
