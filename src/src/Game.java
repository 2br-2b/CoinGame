package src;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class Game {

	protected String name;

	public abstract void play(MessageReceivedEvent e);

	public abstract ArrayList<String> getUsers();

	public String getName() {
		return name;
	};

	public boolean isPlaying(User u) {
		if (getUsers().contains(u.getId())) {
			return true;
		} else {
			return false;
		}

	}

}
