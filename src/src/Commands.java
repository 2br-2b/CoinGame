
package src;

import java.io.IOException;

public class Commands {

	public static void interpretCommand(String command) {
		try {
			if (command.substring(0, Main.PREFIX.length()).equals(Main.PREFIX)) {
				command = command.substring(Main.PREFIX.length());
			}

			String[] commandAsList = command.split(" ");

			String initialCommand = commandAsList[0];

			String userID;
			int amount;

			switch (initialCommand) {

			// giveitem <userID> <item_boost> <item_name>
			case "giveitem":
				userID = commandAsList[1];

				String name = commandAsList[3];

				for (int i = 4; i < commandAsList.length; i++) {
					name += " " + commandAsList[i];
				}

				Store.giveUserUpgrade(userID, new Upgrade(name, 0, Integer.parseInt(commandAsList[2])));
				break;

			// givemoney <userID> <amount>
			case "givemoney":
				userID = commandAsList[1];

				amount = Integer.parseInt(commandAsList[2]);

				PointsAdder.addCoins(userID, amount);
				break;

			case "shufflestore":
			case "randomizestore":
				Store.randomizeStore();
				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void addCommand(String string) {
		try {
			FileManager.writeFiles(string);
		} catch (IOException ex) {
			System.err.println(ex);
		}

	}
}
