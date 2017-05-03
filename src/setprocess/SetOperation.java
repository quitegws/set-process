package setprocess;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class SetOperation {
	
	public static void main(String[] args){
		testIntersection();
	}
	
	public static void iterateSet(Set set, String name){
		long startTime = System.currentTimeMillis();
		Iterator iter = set.iterator();
		int count = 0;
		while (iter.hasNext()) {
			Object obj = iter.next();
			count++;
		}
		long endTime = System.currentTimeMillis();
		System.out.println("iterate " + name + " in " + (endTime - startTime) + " ms\n");
	}
	
	public static void testIntersection(){
		int setNum = 2;
		int ipNum = 1000000;
		int intersectionIPNum = 1000;
		Set<String>[] sets = SetUtils.generateSets(setNum, ipNum,intersectionIPNum);
		
		Set set1 = forceGetIntersection(sets);
		iterateSet(set1,"set1");
		
		Set set2 = guavaIntersection(sets);
		iterateSet(set2,"set2");
		
		Set set3 = JedisSet.jedisIntersection(sets);
		iterateSet(set3,"set3");
		
		Set set4 = forceGetUnion(sets);
		iterateSet(set4,"set4");
		
		Set set5 = guavaUnion(sets);
		iterateSet(set5,"set5");
		
		Set set6 = JedisSet.jedisUnion(sets);
		iterateSet(set6,"set6");
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
		
		System.out.println("forceGetUnion() in " + (endTime - startTime) 
							+ " ms" + " and result size is " + set.size() + "\n");
		return set;
	}

	public static Set guavaUnion(Set[] sets){
		if (sets == null || sets.length == 0) {
			System.err.println("wrong args in guavaUnion");
			return new HashSet();
		}
		int len = sets.length;
		long startTime = System.currentTimeMillis();
		
		SetView sv = Sets.union(sets[0], sets[1]);
		long endTime = System.currentTimeMillis();
		System.out.println("guavaUnion in  " 
						+ (endTime - startTime) + " ms and result size is " + sv.size() + "\n");
		return sv;
	}
	
	public static Set guavaIntersection(Set[] sets){
		if (sets == null || sets.length <= 1) {
			System.err.println("wrong args in guavaIntersection");
			return new HashSet();
		}
		int len = sets.length;
		long startTime = System.currentTimeMillis();
		
		Set sv = (Set) Sets.intersection(sets[0], sets[1]);
		long endTime = System.currentTimeMillis();
		System.out.println("guavaIntersection in  " 
						+ (endTime - startTime) + " ms and result size is " + sv.size() + "\n");
		return sv;
		
	}
	
	

}
