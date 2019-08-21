package scars;

import java.util.ArrayList;
import java.util.HashMap;

public class ScarHandler {
	public static HashMap<String, ArrayList<Scar>> scars = new HashMap<>();

	public static void giveUserScar(String id, Scar scar) {
		System.out.println("Tried to give a user a scar!");

		ArrayList<Scar> list = scars.get(id);
		if (list == null) {
			list = new ArrayList<>();
		}

		boolean found = false;
		for (int i = 0; i < list.size(); i++) {
			Scar sc = list.get(i);
			if (sc.getName().equals(scar.getName())) {
				found = true;
				sc.plusOne(scar);
				break;
			}
		}

		if (!found) {
			list.add(new Scar(scar));
		}
		scars.put(id, list);

	}

	/*
	 * public static boolean removeScar(String id, String scarName) { for (Scar sc :
	 * scars.get(id)) { if (sc.getName().equalsIgnoreCase(scarName)) {
	 * sc.minusOne(); if (sc.getQuantity() < 1) { scars.get(id).remove(sc); } return
	 * true; } } return false; }
	 */
	public static boolean hasScar(String id, String upgradeName) {
		for (Scar sc : scars.get(id)) {
			if (sc.getName().equalsIgnoreCase(upgradeName)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Scar> getScars(String userID) {
		if (scars.get(userID) == null) {
			return new ArrayList<>();
		} else {
			return scars.get(userID);
		}
	}

}
