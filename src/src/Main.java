package src;

import java.util.ArrayList;
import java.util.HashMap;

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
	public static final String[] STRINGS = { GET_MONEY_STRING, GET_BUY_STRING, GET_STORE_STRING, GET_INVENTORY_STRING,
			GET_HELP_STRING };
	public static final String CURRENCY = ":moneybag:";
	public static HashMap<String, Long> bal = new HashMap<String, Long>();
	public static HashMap<String, ArrayList<Upgrade>> upgrades = new HashMap<String, ArrayList<Upgrade>>();
	public static final boolean BOTS_ALLOWED = true;
	public static TextChannel botData;

	public static void main(String args[]) throws Exception {
		System.out.println("Coin Games");
		JDA jda = new JDABuilder("NTY4MjQ4MTg2NzQxOTgxMTk1.XLfUYQ.8cH2XWlfY-mRWZB2cY8M_aS5g_Y").build();

		jda.addEventListener(new PointsAdder());
		jda.addEventListener(new GetStuff());
		jda.addEventListener(new Store());
		jda.addEventListener(new PlayGames());

		new FileManager();

	}

}