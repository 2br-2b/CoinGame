package src;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class StandardUpgradeMerge extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getMessage().getContentRaw().startsWith(Main.PREFIX + "merge")) { //
			System.out.print("1");

			String[] items = e.getMessage().getContentRaw().substring(Main.PREFIX.length() + 5).split(",");
			for (int i = 0; i < items.length; i++) {
				items[i] = items[i].trim();
				if (!Store.hasItem(e.getAuthor().getId(), items[i])) {
					e.getChannel().sendMessage("You don't have a `" + items[i] + "`!").queue();
					return;
				}
			}
			if (UpgradeMerge.getNewUpgrade(items[0], items[1]) != null) {

				if (Store.removeItem(e.getAuthor().getId(), items[0])
						&& Store.removeItem(e.getAuthor().getId(), items[1])) {

					Store.giveUserUpgrade(e.getAuthor().getId(), UpgradeMerge.getNewUpgrade(items[0], items[1]));

					e.getChannel()
							.sendMessage(e.getAuthor().getAsMention() + " merged a `" + items[0] + "` and a `"
									+ items[1] + "` to make a `"
									+ UpgradeMerge.getNewUpgrade(items[0], items[1]).getName() + "`!")
							.queue();
				} else {
					e.getChannel().sendMessage("Something went wrong.").queue();
				}
			} else {
				e.getChannel().sendMessage("You can't merge a `" + items[0] + "` and a `" + items[1] + "`!").queue();
				return;
			}

		}
	}
}
