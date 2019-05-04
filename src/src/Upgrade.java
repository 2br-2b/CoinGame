package src;

import java.io.Serializable;

public class Upgrade implements Comparable<Upgrade>, Serializable, Gettable {

	private static final long serialVersionUID = 1L;
	private String name, prefix;
	private long cost;
	private int boostRate;
	private int quantity;
	private Scar effect;

	public Upgrade(String prefix, String name, long cost, int boostRate, int quantity, Scar effect) {
		if (prefix.equals("") || prefix == null) {
			if (cost > Integer.MAX_VALUE) {
				this.prefix = Store.longPrefix;
			} else if (cost >= 1000000000) {
				this.prefix = Store.billionPrefix;
			} else if (cost >= 1000000) {
				this.prefix = Store.millionPrefix;
			} else {
				this.prefix = prefix;
			}
		} else {
			this.prefix = prefix;
		}
		this.name = name;
		this.cost = cost;
		this.boostRate = boostRate;
		this.quantity = quantity;
		this.effect = effect;
	}

	public Upgrade(String prefix, String name, long cost, int boostRate, int quantity) {
		this(prefix, name, cost, boostRate, quantity, null);
	}

	public Upgrade(String prefix, String name, long cost, int boostRate) {
		this(prefix, name, cost, boostRate, 1);
	}

	public Upgrade(String name, long cost, int boostRate, int quantity) {

		this("", name, cost, boostRate, quantity);

	}

	public Upgrade(String name, long cost, int boostRate) {
		this(name, cost, boostRate, 1);
	}

	public Upgrade(Upgrade u) {
		this(u.getPrefix(), u.getName(), u.getCost(), u.getBoost(), 1, u.getScar());
	}

	@Override
	public String toString() {
		return "`" + quantity + "x` " + toStringWithoutNumber();
	}

	public String toStringWithoutNumber() {
		String str = getNamePrefix() + " (" + getCostString() + Main.CURRENCY;

		if (getBoost() != 0) {
			str += ", provides a boost of " + Main.addCommas(boostRate) + Main.CURRENCY;
		}

		str += ")";

		return str;
	}

	public String getPrefix() {
		return prefix;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getNamePrefix() {
		if (prefix == null || prefix.equals(""))
			return name;
		else
			return prefix + " " + name;
	}

	public long getCost() {
		return cost;
	}

	public String getCostString() {
		return Main.addCommas(cost);
	}

	public int getBoost() {
		return boostRate;
	}

	public int getTotalBoost() {
		return boostRate * quantity;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	public Scar getEffect() {
		return effect;
	}

	public Scar getScar() {
		return getEffect();
	}

	@Override
	public void plusOne() {
		quantity++;
	}

	@Override
	public void minusOne() {
		quantity--;
	}

	@Override
	public int compareTo(Upgrade other) {
		return (this.getCost() - other.getCost() > 0) ? 1 : (this.getCost() - other.getCost() == 0) ? 0 : -1;
	}

	/*
	 * @Override public int compare(Upgrade o1, Upgrade o2) { return o1.getCost() -
	 * o2.getCost(); }
	 */

}
