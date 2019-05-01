package src;

import java.util.ArrayList;

public class Mergable {
	String[] upgradesNeeded;
	Upgrade upgrade;

	public Mergable(String u1name, String u2name, Upgrade upgrade) {
		upgradesNeeded = new String[2];
		upgradesNeeded[0] = u1name;
		upgradesNeeded[1] = u2name;
		this.upgrade = upgrade;
	}

	public Mergable(String[] needed, Upgrade upgrade) {
		upgradesNeeded = needed;
		this.upgrade = upgrade;
	}

	public boolean isMerge(String u1name, String u2name) {
		ArrayList<String> us = new ArrayList<String>();
		us.add(u1name);
		us.add(u2name);
		return isMerge(us);
	}

	public boolean isMerge(String[] u) {
		return UpgradeMerge.checkIfSame(listToArrayList(u), listToArrayList(upgradesNeeded));

	}

	public boolean isMerge(ArrayList<String> u) {
		return UpgradeMerge.checkIfSame(u, listToArrayList(upgradesNeeded));

	}

	public Upgrade getUpgrade() {
		return new Upgrade(upgrade);
	}

	@Override
	public String toString() {
		String str = "`" + upgradesNeeded[0];
		for (int i = 1; i < upgradesNeeded.length; i++) {
			str += "` + `" + upgradesNeeded[i];
		}
		str += "`";

		return str;
	}

	public ArrayList<String> listToArrayList(String[] l) {
		ArrayList<String> al = new ArrayList<String>();
		for (String s : l) {
			al.add(s);
		}
		return al;
	}

}