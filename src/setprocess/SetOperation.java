package setprocess;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetOperation {
	
	public static void main(String[] args){
		testIntersection();
	}
	
	public static void testIntersection(){
		int setNum = 20;
		int ipNum = 500000;
		Set[] sets = SetUtils.generateSets(setNum, ipNum);
		forceGetIntersection(sets);
		JedisSet.jedis(sets);
	}
	
	public static Set forceGetIntersection(Set[] sets){
		if (sets == null || sets.length <= 1) {
			System.out.println("Set[] is null or Set[].length not greater than 1");
			return null;
		}
		long t2 = System.currentTimeMillis();
		Set result = new HashSet<String>();
		Iterator iter = sets[0].iterator();
		while (iter.hasNext()) {
			boolean flag = true;
			String ip = (String) iter.next();
			for (int i = 1; i < sets.length; i++) {
				if (!sets[i].contains(ip)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result.add(ip);
			}
		}
		long t3 = System.currentTimeMillis();
		System.out.println("forceGetIntersection() in " + (t3 - t2) + " ms" + " and result size is " + result.size());
		System.out.println();
		return result;
	}
}
