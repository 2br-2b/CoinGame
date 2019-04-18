
package src;

public class Commands {

	public static void interpretCommand(String command) {
		try {
			String[] commandAsList = command.split(" ");

			String initialCommand = commandAsList[0];

			String userID;

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

				int amount = Integer.parseInt(commandAsList[2]);

				PointsAdder.addCoins(userID, amount);
				break;

			default:
				break;
			}
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
}
