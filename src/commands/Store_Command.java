package commands;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;
import src.Main;
import src.Store;
import src.Upgrade;

public class Store_Command extends Command {

	public Store_Command() {
		super();
		super.name = "store";
		super.help = "shows the store";
		super.guildOnly = false;
		// super.cooldown = 60;

	}

	@Override
	protected void execute(CommandEvent event) {
		try{
			Thread.sleep(100);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (event.getAuthor().isBot())
			return;

		while(Store.reseting);

		String str = "";

		if (Store.store.size() == 0) {
			Store.randomizeStore();
		}

		for (Upgrade u : Store.store) {
			str += u + "\n";
		}

		str += "\n*The store resets in " + (60 - Store.lastRandomized.until(OffsetDateTime.now(), ChronoUnit.MINUTES))
				+ " minutes*";

		EmbedBuilder emb = new EmbedBuilder();
		emb.setTitle("The Store");
		emb.setDescription(str);
		emb.setColor(Main.embedColor);

		event.reply(emb.build());

	}

}
