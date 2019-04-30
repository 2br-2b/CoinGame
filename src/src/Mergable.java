package src;

public class Mergable {
	String u1, u2;
	Upgrade upgrade;

	public Mergable(String u1name, String u2name, Upgrade upgrade) {
		u1 = u1name;
		u2 = u2name;
		this.upgrade = upgrade;
	}

	public boolean isMerge(String u1name, String u2name) {
		if (u1name.toLowerCase().equals(u1.toLowerCase()) && u2name.toLowerCase().equals(u2.toLowerCase())
				|| u1name.toLowerCase().equals(u2.toLowerCase()) && u2name.toLowerCase().equals(u1.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	public Upgrade getUpgrade() {
		return new Upgrade(upgrade);
	}

	@Override
	public String toString() {
		return "`" + u1 + "` + `" + u2 + "`";
	}

}