/**
 * 
 */
package src;

/**
 * @author johnwuller
 *
 */
public class Upgrade implements Comparable<Upgrade> {
	private String name;
	private long cost;
	private int boostRate;
	private int quantity;

	public Upgrade(String name, long cost, int boostRate) {
		this.name = name;
		this.cost = cost;
		this.boostRate = boostRate;
		this.quantity = 1;
	}

	public Upgrade(String name, long cost, int boostRate, int quantity) {
		this(name, cost, boostRate);
		this.quantity = quantity;
	}

	public Upgrade(Upgrade u) {
		this(u.getName(), u.getCost(), u.getBoost(), 1);
	}

	@Override
	public String toString() {
		String str = "`" + quantity + "x` " + getName() + " (" + getCost() + Main.CURRENCY;

		if (getBoost() != 0) {
			str += ", provides a boost of " + boostRate + Main.CURRENCY;
		}

		str += ")";

		return str;
	}

	public String getName() {
		return name;
	}

	public long getCost() {
		return cost;
	}

	public int getBoost() {
		return boostRate;
	}

	public int getTotalBoost() {
		return boostRate * quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void plusOne() {
		quantity++;
	}

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
