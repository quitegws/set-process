package setprocess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class SetOperation {
	
	public static void main(String[] args){
		test();
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
		System.out.println("iterate " + name + " in " + (endTime - startTime) + " ms count is " + count + "\n");
	}
	
	public static void test(){
		int setNum = 2;
		int ipNum = 100 * 10000;
		int intersectionIPNum = 10000;
		int range = 100;
		Set<String>[] sets = SetUtils.generateSets(setNum, ipNum, intersectionIPNum, range);
		
		Set set1 = forceGetIntersection(sets);
		iterateSet(set1,"set1");
		
		Set set2 = guavaIntersection(sets);
		iterateSet(ImmutableSet.copyOf(set2),"set2");
		
//		Set set3 = JedisSet.jedisIntersection(sets);
//		iterateSet(set3,"set3");
		
		Set set4 = forceGetUnion(sets);
		iterateSet(set4,"set4");
		
		Set set5 = guavaUnion(sets);
		iterateSet(ImmutableSet.copyOf(set5),"set5");
//		
//		Set set6 = JedisSet.jedisUnion(sets);
//		iterateSet(set6,"set6");
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

	public static Set<String> guavaUnion(Set[] sets){
		if (sets == null || sets.length == 0) {
			System.err.println("wrong args in guavaUnion");
			return new HashSet();
		}
		int len = sets.length;
		long startTime = System.currentTimeMillis();
		Set set = new HashSet();
		for (int i = 0; i < sets.length; i++) {
			set = Sets.union(set, sets[i]);
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("guavaUnion in  " 
						+ (endTime - startTime) + " ms and result size is " + set.size() + "\n");
		return set;
	}
	
	public static Set<String> guavaIntersection(Set[] sets){
		if (sets == null || sets.length <= 1) {
			System.err.println("wrong args in guavaIntersection");
			return new HashSet();
		}
		int len = sets.length;
		long startTime = System.currentTimeMillis();
		
//		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//		
//		for (int i = 0; i < sets.length; i++) {
//			map.put(i, sets[i].size());
//		}
//		
		int i = 0;
		int minLen = Integer.MAX_VALUE;
		for (; i < len; i++)
			minLen = sets[i].size() < minLen ? sets[i].size() : minLen;
		i = 0;
		for (; i < len; i++)
			if (sets[i].size() == minLen) 
				break;
		
		Set sv = sets[i];
		
		for (int j = 0; j < sets.length; j++) {
			if (j != i) {
				sv = (Set) Sets.intersection(sv, sets[j]);
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("guavaIntersection in  " 
						+ (endTime - startTime) + " ms and result size is " + sv.size() + "\n");
		return sv;
		
	}
	
	

}
