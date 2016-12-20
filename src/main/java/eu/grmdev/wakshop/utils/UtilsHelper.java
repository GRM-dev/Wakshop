package eu.grmdev.wakshop.utils;

import java.util.Iterator;
import java.util.List;

public class UtilsHelper {
	
	/**
	 * Changes first array to have same elements as second
	 * Given arrays a1 and a2
	 * Returns array a1 with elements present in a2, but without ones that aren't
	 * in a2
	 * 
	 * @param a1
	 *           first array - origin
	 * @param a2
	 *           second array - destination
	 * @return origin array changed to destination
	 */
	public static <T> List<T> combineSubtractArrays(List<T> a1, List<T> a2) {
		Iterator<T> it = a1.iterator();
		while (it.hasNext()) {
			T e1 = it.next();
			if (!a2.contains(e1)) {
				a1.remove(e1);
			}
		}
		if (a1.size() == a2.size()) { return a1; }
		it = a2.iterator();
		while (it.hasNext()) {
			T e2 = it.next();
			if (!a1.contains(e2)) {
				a1.add(e2);
			}
		}
		return a1;
	}
}
