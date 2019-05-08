
package src;

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

			// makegive <id_giver> <id_given> <name of object>
			case "makegive":
				String giverID = commandAsList[1];
				String givenID = commandAsList[2];

				String nameOfObj = commandAsList[3];

				for (int i = 4; i < commandAsList.length; i++) {
					nameOfObj += " " + commandAsList[i];
				}

				Store.userGiveUserUpgrade(giverID, nameOfObj, givenID);
				break;

			// remove <id> <name of object>
			case "remove":
				String id = commandAsList[1];
				String objectName = commandAsList[2];

				for (int i = 3; i < commandAsList.length; i++) {
					objectName += " " + commandAsList[i];
				}

				Store.removeItem(id, objectName);
				break;

			default:
				System.err.println("Invalid command:\n" + initialCommand);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
