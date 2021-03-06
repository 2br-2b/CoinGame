/**
 * @author GreedyVagabond
 * @author 2br-2b
 */

package src;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import commands.Add_Command;
import commands.Bal_Command;
import commands.Boost_Command;
import commands.Buy_Command;
import commands.Feedback_Command;
import commands.Give_Command;
import commands.InfoPerson_Command;
import commands.Info_Command;
import commands.Inv_Command;
import commands.Merge_Command;
import commands.Merges_Command;
import commands.Pay_Command;
import commands.RandomizeStore_Command;
import commands.Sell_Command;
import commands.Set_Command;
import commands.Store_Command;
import games.Play_Command;
import games.Sweepstakes;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Main {

	public static final String PREFIX = "c!";
	public static final String guildID = "542802660928258048";
	public static final String CURRENCY = ":moneybag:";
	public static final Color embedColor = Color.YELLOW;
	public static HashMap<String, Long> bal = new HashMap<>();
	public static HashMap<String, ArrayList<Upgrade>> upgrades = new HashMap<>();

	public static final boolean BOTS_ALLOWED = false;
	public static TextChannel botData;
	public static Guild g;
	public static JDA jda;
	public static ArrayList<Upgrade> masterUpgradeList;

	public static void main(String args[]) throws Exception {
		System.out.println("Coin Games");

		jda = new JDABuilder(AccountType.BOT).setToken(getKey()).build().awaitReady();

		jda.addEventListener(new PointsAdder());
		// jda.addEventListener(new GetHelp());
		jda.addEventListener(new Store());
		// jda.addEventListener(new PlayGames());
		jda.addEventListener(new Sweepstakes());

		g = jda.getGuildById(guildID);

		CommandClientBuilder builder = new CommandClientBuilder();
		EventWaiter waiter = new EventWaiter();

		builder.setPrefix("c!");
		// builder.setGame(new Game(PREFIX + "help"));

		builder.addCommands(new Bal_Command());
		builder.addCommands(new Inv_Command());
		builder.addCommands(new Boost_Command());

		builder.addCommands(new Store_Command());
		builder.addCommands(new Buy_Command());
		builder.addCommands(new Sell_Command());
		builder.addCommands(new Info_Command());
		builder.addCommands(new InfoPerson_Command());
		builder.addCommands(new Give_Command());
		builder.addCommands(new Pay_Command());

		builder.addCommands(new Play_Command());

		// builder.addCommands(new Weapons_Command());
		// builder.addCommands(new Use_Command());
		// builder.addCommands(new Scars_Command());

		builder.addCommands(new Merge_Command(waiter));
		builder.addCommands(new Merges_Command());
		builder.addCommands(new Add_Command());

		// builder.addCommands(new Help_Command());
		builder.addCommands(new Set_Command());
		builder.addCommands(new RandomizeStore_Command());
		builder.addCommands(new Feedback_Command());

		builder.setOwnerId("351804839820525570");
		builder.setCoOwnerIds("544600923112996901");
		// builder.setHelpWord("notahelpcommand");
		// builder.setEmojis(":smiley:", ":exclamation:", ":sweat_smile:");

		CommandClient client = builder.build();

		jda.addEventListener(client);
		jda.addEventListener(waiter);

		new DictionaryManager();
		new MergeHandler();
		serializeStuffStart();
		serializeStuff();

		masterUpgradeList = new ArrayList<>();
		masterUpgradeList.clear();

		for (Upgrade u : Store.randomStuff) {
			masterUpgradeList.add(new Upgrade(u));
			replaceAllEverywhere(u);
		}

		for (Upgrade u : Store.pastUpgrades) {
			masterUpgradeList.add(new Upgrade(u));
			replaceAllEverywhere(u);
		}

		for (Mergable m : MergeHandler.possibleMerges) {
			masterUpgradeList.add(new Upgrade(m.getUpgrade()));
			replaceAllEverywhere(m.getUpgrade());
		}

		replaceAllEverywhere("Legolas's Bow", new Upgrade("Legolas' Bow", 301800, 2110, 1));

		removeBadUsers();

		System.out.println("Ready!");

		printJson();

		String str = "";
		Scanner ask = new Scanner(System.in); // Create a Scanner object
		while (!str.equals("Goodbye!")) {
			str = ask.nextLine(); // Read user input
			if (str.toLowerCase().equals("messagecount")) {
				System.out.println(Sweepstakes.getTimes());

			} else {
				Commands.interpretCommand(str);
			}
		}
		ask.close();

	}

	private static void printJson() {
		String s = "{";
		ArrayList<String> keyset = new ArrayList<>(bal.keySet());
		for (String key : keyset) {
			s += "\n\"";
			s += key;
			s += "\": {\n\"balance\": ";
			s += bal.get(key);
			s += ",\"boost\": ";

			long boost = 1;
			if (Main.upgrades.containsKey(key)) {
				ArrayList<Upgrade> list = Main.upgrades.get(key);
				for (Upgrade u : list) {
					boost += u.getTotalBoost();
				}

				if (boost < 1) {
					boost = 1;
				}
			}

			s += boost;
			s += ",\"upgrades\": {";
			try {
				for (Upgrade u : upgrades.get(key)) {
					s += '"';
					s += u.getName();
					s += "\": {\"name\": \"";
					s += u.getName();
					s += "\",\"quantity\": ";
					s += u.getQuantity();
					s += "},";
				}
			} catch (NullPointerException e) {}
			s += "}";

			s += "},";
		}
		s.substring(0, s.length());
		s += "}";

		System.out.println(s);

	}

	private static String getKey() {
		BufferedReader keyFile;
		try {
			keyFile = new BufferedReader(new FileReader("key.txt"));
			return keyFile.readLine();
		} catch (FileNotFoundException e) {
			System.err.println("Please save the key in a file `key.txt`!");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Something went wrong.  Please try again.");
			System.exit(-2);
		}
		return null;

	}

	private static void removeBadUsers() {
		removeBadUsers(bal);
		removeBadUsers(upgrades);
		removeBadUsers(PointsAdder.coolingDown);
		serializeStuff();
	}

	private static void removeBadUsers(HashMap bal) {
		// TODO Auto-generated method stub
		ArrayList<String> keyset = new ArrayList<String>(bal.keySet());
		for (int i = 0; i < keyset.size(); i++) {
			String id = keyset.get(i);
			try {
				if (!isGood(id)) {
					bal.remove(i);
					i--;
					System.err.println(id + " is a bot.  It should not have anything!");
				}
			} catch (Exception e) {
				System.err.println(e);
				bal.remove(id);
				i--;
				break;
			}
		}

	}

	public static String addCommas(long n) {
		return String.format("%,d", n);
	}

	@SuppressWarnings("unchecked")
	private static void serializeStuffStart() {

		try {
			FileInputStream fis = new FileInputStream("CoinGameBal.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			bal = (HashMap<String, Long>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}

		try {
			FileInputStream fis = new FileInputStream("CoinGameUpgrades.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			upgrades = (HashMap<String, ArrayList<Upgrade>>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}

		/*
		 * try { FileInputStream fis = new FileInputStream("CoinGameCoolingDown.ser");
		 * ObjectInputStream ois = new ObjectInputStream(fis); PointsAdder.coolingDown =
		 * (HashMap<String, OffsetDateTime>) ois.readObject(); ois.close(); fis.close();
		 * } catch (IOException ioe) { ioe.printStackTrace(); return; } catch
		 * (ClassNotFoundException c) { System.out.println("Class not found");
		 * c.printStackTrace(); return; }
		 */

	}

	public static void serializeStuff() {
		try {
			FileOutputStream fos = new FileOutputStream("CoinGameBal.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Main.bal);
			oos.close();
			fos.close();
			// System.out.println("Serialized HashMap data is saved in CoinGameBal.ser");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		try {
			FileOutputStream fos = new FileOutputStream("CoinGameUpgrades.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Main.upgrades);
			oos.close();
			fos.close();
			// System.out.println("Serialized HashMap data is saved in
			// CoinGameUpgrades.ser");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		try {
			FileOutputStream fos = new FileOutputStream("CoinGameCoolingDown.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(PointsAdder.coolingDown);
			oos.close();
			fos.close();
			// System.out.println("Serialized HashMap data is saved in
			// CoinGameCoolingDown.ser");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public static void replaceAllEverywhere(ArrayList<Upgrade> upgradeList) {
		for (Upgrade upgrade : upgradeList)
			replaceAllEverywhere(upgrade.getName(), upgrade);
	}

	public static void replaceAllEverywhere(Upgrade upgrade) {
		replaceAllEverywhere(upgrade.getName(), upgrade);
	}

	public static void replaceAllEverywhere(String oldUpgradeName, Upgrade newUpgrade) {
		for (String id : upgrades.keySet()) {

			ArrayList<Upgrade> upgradeList = upgrades.get(id);

			for (Upgrade u : upgradeList) {
				if (u.getName().equalsIgnoreCase(oldUpgradeName)) {
					int q = u.getQuantity();
					upgradeList.remove(u);
					for (int i = 0; i < q; i++) {
						Store.giveUserUpgrade(id, newUpgrade);
					}
					break;
				}
			}
		}
	}

	public static User getUserFromID(String id) {
		if (jda.getUserById(id) != null) {
			return jda.getUserById(id);
		} else {
			throw new NullPointerException("Could not find " + id);
		}
	}

	public static Member getMemberFromID(String id) {
		for (Guild g : jda.getGuilds()) {
			if (g.getMemberById(id) != null) {
				return g.getMemberById(id);
			}
		}
		return null;
	}

	public static boolean isGood(String id) throws Exception {
		try {
			if (getUserFromID(id).isBot()) {
				return false;
			} else {
				return true;
			}
		} catch (NullPointerException e) {
			throw e;
		} catch (NumberFormatException e) {
			throw e;
		}
	}

}
