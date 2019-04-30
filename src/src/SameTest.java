package src;

import java.util.ArrayList;

public class SameTest {

	public static void main(String[] args) {
		ArrayList<String> a1 = new ArrayList<String>();
		ArrayList<String> a2 = new ArrayList<String>();

		a1.add("a");
		a1.add("b");
		a2.add("b");
		a2.add("a");

		System.out.println("list 1: " + a1);
		System.out.println("list 2: " + a2);

		System.out.println(checkIfSame(a1, a2));

	}

	public static boolean checkIfSame(ArrayList<String> l1, ArrayList<String> l2) {
		if (l1 == null && l2 == null) {
			return true;
		} else if (l1 == null || l2 == null) {
			return false;
		}

		ArrayList<String> a = new ArrayList<String>(l1);
		ArrayList<String> b = new ArrayList<String>(l2);

		System.out.println("list 1 copy: " + a);
		System.out.println("list 2 copy: " + b);

		System.out.print("list 1 size:" + a.size());
		System.out.println("");
		for (int i = 0; i < a.size(); i++) {
			String s = a.get(0);
			if (!(a.remove(s) && b.remove(s))) {
				return false;
			}

			System.out.println("");
			System.out.println("iteration " + (i + 1));
			System.out.println("string: " + s);
			System.out.println("list 1 copy: " + a);
			System.out.println("list 2 copy: " + b);

		}
		System.out.println("");
		System.out.println("");
		System.out.println("list 2 copy with removed stuff: " + b);

		if (b.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}