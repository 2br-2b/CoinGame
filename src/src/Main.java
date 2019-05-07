/**
 * @author GreedyVagabond
 * @author 2br-2b
 */

package src;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import commands.Bal_Command;
import commands.Info_Command;
import commands.Inv_Command;
import commands.Scars_Command;
import commands.Set_Command;
import commands.Store_Command;
import commands.Use_Command;
import commands.Weapons_Command;
import games.Game_Manager;
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
	public static HashMap<String, Long> bal = new HashMap<String, Long>();
	public static HashMap<String, ArrayList<Upgrade>> upgrades = new HashMap<String, ArrayList<Upgrade>>();

	public static final boolean BOTS_ALLOWED = false;
	public static TextChannel botData;
	public static Guild g;
	public static JDA jda;
	public static ArrayList<Upgrade> masterUpgradeList;

	public static void main(String args[]) throws Exception {
		System.out.println("Coin Games");
		jda = new JDABuilder(AccountType.BOT).setToken("NTY4MjQ4MTg2NzQxOTgxMTk1.XMtYjg.ZChMtNriH1MrpduZ0Q-He_SQ_f4")
				.build().awaitReady();

		jda.addEventListener(new PointsAdder());
		jda.addEventListener(new GetHelp());
		jda.addEventListener(new Store());
		// jda.addEventListener(new PlayGames());
		jda.addEventListener(new StandardUpgradeMerge());
		jda.addEventListener(new Sweepstakes());

		g = jda.getGuildById(guildID);

		CommandClientBuilder builder = new CommandClientBuilder();
		EventWaiter waiter = new EventWaiter();

		builder.setPrefix("c!");
		// builder.setGame(Game.listening(PREFIX+"help"));

		builder.addCommands(new Bal_Command());
		builder.addCommands(new Inv_Command());
		builder.addCommands(new Scars_Command());
		builder.addCommands(new Info_Command());
		builder.addCommands(new Set_Command());
		builder.addCommands(new Store_Command());
		builder.addCommands(new Use_Command());
		builder.addCommands(new Game_Manager());
		builder.addCommands(new Weapons_Command());

		builder.addCommands(new UpgradeMerge(waiter));

		builder.setOwnerId("351804839820525570");
		builder.setCoOwnerIds("544600923112996901");
		builder.setHelpWord("notahelpcommand");
		builder.setEmojis(":smiley:", ":exclamation:", ":sweat_smile:");

		CommandClient client = builder.build();

		jda.addEventListener(client);
		jda.addEventListener(waiter);

		new FileManager();
		serializeStuffStart();
		serializeStuff();

		masterUpgradeList = new ArrayList<Upgrade>();
		masterUpgradeList.clear();

		for (Upgrade u : Store.randomStuff) {
			masterUpgradeList.add(new Upgrade(u));
			// replaceAllEverywhere(u);
		}

		for (Upgrade u : Store.pastUpgrades) {
			masterUpgradeList.add(new Upgrade(u));
			// replaceAllEverywhere(u);
		}

		for (Mergable m : UpgradeMerge.possibleMerges) {
			masterUpgradeList.add(new Upgrade(m.getUpgrade()));
			// replaceAllEverywhere(m.getUpgrade());
		}

		removeBadUsers();

		System.out.println("Ready!");

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

	private static void removeBadUsers() {
		removeBadUsers(bal);
		removeBadUsers(upgrades);
		removeBadUsers(ScarHandler.scars);
		removeBadUsers(PointsAdder.coolingDown);
		serializeStuff();
	}

	private static void removeBadUsers(HashMap bal) {
		// TODO Auto-generated method stub
		ArrayList<String> keyset = new ArrayList<String>(bal.keySet());
		for (int i = 0; i < keyset.size(); i++) {
			String id = keyset.get(i);
			try {
				getUserFromID(id);
			} catch (NullPointerException e) {
				System.err.println(e);
				bal.remove(id);
				removeBadUsers(bal);
				break;
			} catch (NumberFormatException e) {
				System.err.println(e);
				bal.remove(id);
				removeBadUsers(bal);
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

		try {
			FileInputStream fis = new FileInputStream("CoinGameScars.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			ScarHandler.scars = (HashMap<String, ArrayList<Scar>>) ois.readObject();
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

		try {
			FileOutputStream fos = new FileOutputStream("CoinGameScars.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ScarHandler.scars);
			oos.close();
			fos.close();
			// System.out.println("Serialized HashMap data is saved in CoinGameScars.ser");
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
				if (u.getName().toLowerCase().equals(oldUpgradeName.toLowerCase())) {
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
		return jda.getUserById(id);
	}

	public static Member getMemberFromID(String id) {
		for (Guild g : jda.getGuilds()) {
			if (g.getMemberById(id) != null) {
				return g.getMemberById(id);
			}
		}
		return null;
	}

}
