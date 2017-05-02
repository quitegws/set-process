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
		int intersectionIPNum = 10000;
		Set<String>[] sets = SetUtils.generateSets(setNum, ipNum,intersectionIPNum);
		forceGetUnion(sets);
		JedisSet.jedisUnion(sets);
		forceGetIntersection(sets);
		JedisSet.jedisIntersection(sets);
	}
	
	public static Set<String> forceGetIntersection(Set<?>[] sets){
		if (sets == null || sets.length <= 1) {
			System.out.println("Set[] is null or Set[].length not greater than 1");
			return null;
		}
		long t2 = System.currentTimeMillis();
		Set<String> result = new HashSet<String>();
		Iterator<?> iter = sets[0].iterator();
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

	public static Set<String> forceGetUnion(Set<?>[] sets){
		System.out.println();
		
		long startTime = System.currentTimeMillis();
		
		Set<String> set = new HashSet<String>();
		
		for (int i = 0; i < sets.length; i++) {
			Iterator<?> iter = sets[i].iterator();
			while (iter.hasNext()) {
				String ip = (String) iter.next();
				set.add(ip);
			}
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("forceGetUnion() in " + (endTime - startTime) + " ms" + " and result size is " + set.size());
		return set;
	}
}
