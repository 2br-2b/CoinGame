package games;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Game_Manager extends Command implements Game {

	public Game_Manager() {
		super();
		super.name = "play";
		String[] a = { "p" };
		super.aliases = a;
		super.help = "plays a game";
		super.arguments = "game";
		Command[] children = { new Game_Coin_Flip(), new Game_Lottery(), new Game_Hangman() };
		super.children = children;
	}

	@Override
	protected void execute(CommandEvent event) {
		event.reply("Please enter a game to play!  You can play `coin`, `lotto`, and `hangman`!");
	}

}
