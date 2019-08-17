package src;

import java.io.Serializable;

public class Scar implements Gettable, Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int boost;
	private String upgradeUsed;
	private int quantity;

	public Scar(String name, int damage, int quantity, String upgradeUsed) {
		this.name = name;
		this.boost = -damage;
		this.upgradeUsed = upgradeUsed;
		this.quantity = quantity;
	}

	public Scar(String name, int damage, String upgradeUsed) {
		this(name, damage, 1, upgradeUsed);
	}

	public Scar(String name, int damage) {
		this(name, damage, null);
	}

	public Scar(Scar s) {
		this(s.getName(), s.getDamage(), s.getQuantity(), s.getUpgradeUsed());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getBoost() {
		return (boost / quantity);
	}

	public int getTotalBoost() {
		return boost;
	}

	public int getDamage() {
		return -getBoost();
	}

	public int getTotalDamage() {
		return -getTotalBoost();
	}

	public String getUpgradeUsed() {
		return upgradeUsed;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public void plusOne() {
		boost += (boost / quantity);
		quantity++;
	}

	public void plusOne(Scar s) {
		boost += s.getBoost();
		quantity++;
	}

	@Override
	public void minusOne() {
		quantity--;
	}

	@Override
	public String toString() {
		return "`" + getQuantity() + "x` " + getName() + " = " + getTotalDamage();
	}

	public String toStringOfOne() {
		if (getDamage() > 0) {
			return getName() + ": " + getDamage();
		} else {
			return getName() + ": heals" + getBoost();
		}
	}

}
