
package src;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ForFun extends ListenerAdapter {

	private static MessageChannel c = null;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!Main.BOTS_ALLOWED && e.getAuthor().isBot())
			return;

		String message = e.getMessage().getContentRaw().toLowerCase();
		c = e.getChannel();

		// TODO add fun commands here

	}
}
