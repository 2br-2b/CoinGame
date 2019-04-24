/**
 * @author GreedyVagabond
 * @author 2br-2b
 */

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
	public static final String CURRENCY = ":moneybag:";
	public static HashMap<String, Long> bal = new HashMap<String, Long>();
	public static HashMap<String, ArrayList<Upgrade>> upgrades = new HashMap<String, ArrayList<Upgrade>>();
	public static final boolean BOTS_ALLOWED = false;
	public static TextChannel botData;

	public static void main(String args[]) throws Exception {
		System.out.println("Coin Games");
		JDA jda = new JDABuilder("NTY4MjQ4MTg2NzQxOTgxMTk1.XL-D9w.kT2kr9nUpqJt_qK8RgHfOyzTfOA").build();

		jda.addEventListener(new PointsAdder());
		jda.addEventListener(new GetStuff());
		jda.addEventListener(new Store());
		jda.addEventListener(new PlayGames());
		
		try
	      {
	         FileInputStream fis = new FileInputStream("CoinGameBal.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         bal = (HashMap<String,Long>) ois.readObject();
	         ois.close();
	         fis.close();
	      }catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return;
	      }
		
		try
	      {
	         FileInputStream fis = new FileInputStream("CoinGameUpgrades.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         upgrades = (HashMap<String,ArrayList<Upgrade>>) ois.readObject();
	         ois.close();
	         fis.close();
	      }catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return;
	      }
		
		try
	      {
	         FileInputStream fis = new FileInputStream("CoinGameCoolingDown.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         PointsAdder.coolingDown = (HashMap<String, OffsetDateTime>) ois.readObject();
	         ois.close();
	         fis.close();
	      }catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return;
	      }

		//new FileManager();

	}

	public static String addCommas(long n) {
		return String.format("%,d", n);
	}

}
