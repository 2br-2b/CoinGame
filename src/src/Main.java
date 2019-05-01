/**
 * @author GreedyVagabond
 * @author 2br-2b
 */

package src;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;

public class Main {

	public static final String PREFIX = "c!";
	public static final String GET_MONEY_STRING = PREFIX + "bal";
	public static final String GET_BUY_STRING = PREFIX + "buy";
	public static final String GET_STORE_STRING = PREFIX + "store";
	public static final String GET_INVENTORY_STRING = PREFIX + "inv";
	public static final String GET_ADD_STRING = PREFIX + "add";
	public static final String GET_HELP_STRING = PREFIX + "help";
	public static final String CURRENCY = ":moneybag:";
	public static HashMap<String, Long> bal = new HashMap<String, Long>();
	public static HashMap<String, ArrayList<Upgrade>> upgrades = new HashMap<String, ArrayList<Upgrade>>();
	public static final boolean BOTS_ALLOWED = false;
	public static TextChannel botData;

	public static void main(String args[]) throws Exception {
		System.out.println("Coin Games");
		// JDA jda = new
		// JDABuilder("NTY4MjQ4MTg2NzQxOTgxMTk1.XL-D9w.kT2kr9nUpqJt_qK8RgHfOyzTfOA").build();
		JDA jda = new JDABuilder(AccountType.BOT)
				.setToken("NTY4MjQ4MTg2NzQxOTgxMTk1.XL-D9w.kT2kr9nUpqJt_qK8RgHfOyzTfOA").build();

		jda.addEventListener(new PointsAdder());
		jda.addEventListener(new GetStuff());
		jda.addEventListener(new Store());
		jda.addEventListener(new PlayGames());
		jda.addEventListener(new StandardUpgradeMerge());
		jda.addEventListener(new Sweepstakes());

		CommandClientBuilder builder = new CommandClientBuilder();
		EventWaiter waiter = new EventWaiter();

		builder.setPrefix("c!");
		// builder.setGame(Game.listening("c!help"));
		builder.addCommands(new UpgradeMerge(waiter));
		builder.setOwnerId("351804839820525570");
		builder.setHelpWord("\u0000h\u0000e\u0000l\u0000p\u0000");

		CommandClient client = builder.build();

		jda.addEventListener(client);
		jda.addEventListener(waiter);

		new FileManager();
		serializeStuff();

		for (Upgrade u : Store.randomStuff) {
			replaceAllEverywhere(u);
		}

		for (Upgrade u : Store.pastUpgrades) {
			replaceAllEverywhere(u);
		}

		for (Mergable m : UpgradeMerge.possibleMerges) {
			replaceAllEverywhere(m.getUpgrade());
		}

		// replaceAllEverywhere("This is random", Store.randomStuff[0]);

	}

	public static String addCommas(long n) {
		return String.format("%,d", n);
	}

	private static void serializeStuff() {

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

}
