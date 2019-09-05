package games;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Play_Command extends Command implements Game {

	public Play_Command() {
		super();
		super.name = "play";
		String[] a = { "p" };
		super.aliases = a;
		super.help = "plays a game";
		super.arguments = "flip/lotto/hangman";
		Command[] children = { new Game_Coin_Flip(), new Game_Lottery(), new Game_Hangman() };
		super.children = children;
		super.guildOnly = false;
	}

	@Override
	protected void execute(CommandEvent event) {
		event.reply("Please enter a game to play!  You can play `coin`, `lotto`, and `hangman`!");
	}

}
