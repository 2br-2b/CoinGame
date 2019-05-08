package commands;

import java.util.ArrayList;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import src.MergeHandler;
import src.Store;

public class Merge_Command extends Command {

	public static EventWaiter waiter = new EventWaiter();
	public static Thread merge;

	public Merge_Command() {
		super();
		super.name = "merge";
		super.help = "merges more than one upgrade to make a (usually) better upgrade";
		super.arguments = "upgrade 1, upgrade 2, …";
		// super.cooldown = 60;

	}

	public Merge_Command(EventWaiter w) {
		super();
		super.name = "merge";
		super.help = "merges more than one upgrade to make a (usually) better upgrade";
		super.arguments = "upgrade 1, upgrade 2, …";
		waiter = w;

	}

	@Override
	protected void execute(CommandEvent event) {
		if (event.getArgs().contains(",")) {

			String[] items = event.getArgs().split(",");

			if (items.length < 2)
				return;

			ArrayList<String> itemArrayList = new ArrayList<String>();
			for (String s : items) {
				s = s.trim();
				if (Store.hasItem(event.getAuthor().getId(), s)) {
					itemArrayList.add(s);
				} else {
					event.replyError("You don't have a `" + s + "`!");
					return;
				}
			}

			if (MergeHandler.getNewUpgrade(itemArrayList) != null) {

				for (String i : itemArrayList) {
					if (!Store.removeItem(event.getAuthor().getId(), i)) {
						event.reply("Something went wrong removing the " + i);
						return;
					}
				}

				Store.giveUserUpgrade(event.getAuthor().getId(), MergeHandler.getNewUpgrade(itemArrayList));

				event.reply(event.getAuthor().getAsMention() + " merged " + itemArrayList + " to make a `"
						+ MergeHandler.getNewUpgrade(itemArrayList).getName() + "`!");

			} else {
				event.replyError("You can't merge " + itemArrayList + "!");
				return;
			}

		}

		/*
		 * if (Math.random() < 2) return;
		 * 
		 * if (event.getAuthor().isBot() && !Main.BOTS_ALLOWED) return;
		 * 
		 * if (event.getMessage().getContentRaw().contains(",")) return;
		 * 
		 * int repetitions = 2; try { repetitions = Integer.parseInt(event.getArgs()); }
		 * catch (Exception e) { }
		 * 
		 * merge = new MergeThread(event, repetitions); merge.start();
		 * 
		 */

	}

}
