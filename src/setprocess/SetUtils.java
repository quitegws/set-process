package setprocess;

import java.util.HashSet;
import java.util.Set;

public class SetUtils {

	//generate random ip addresses
	public static Set<String>[] generateSets(int howManySets, int howManyIPsPerSet, int intersectionNum){
		if (howManySets <= 0 || howManyIPsPerSet <= 0) {
			System.out.println("wrong args in generateSets()");
			return null;
		}
		long startTime = System.currentTimeMillis();
		Set<String>[] result = new HashSet[howManySets];
		
		String[] intersection = new String[intersectionNum];
		for (int i = 0; i < intersectionNum; i++) {
			intersection[i] = (int)(Math.random() * 255) + "."
					+ (int)(Math.random() * 255) + "."
					+ (int)(Math.random() * 255) + "."
					+ (int)(Math.random() * 255);
		}
		
		for (int i = 0; i < howManySets; i++) {
			long t1 = System.currentTimeMillis();
			Set<String> set = new HashSet<String>();
			for (int j = 0; j < howManyIPsPerSet - intersectionNum; j++) {
				String ip = (int)(Math.random() * 255) + "."
							+ (int)(Math.random() * 255) + "."
							+ (int)(Math.random() * 255) + "."
							+ (int)(Math.random() * 255);
				set.add(ip);
			}
			
			for (int j = 0; j < intersectionNum; j++) {
				set.add(intersection[j]);
			}
			result[i] = set;
			long t2 = System.currentTimeMillis();
			System.out.println("generate " + i + "th set done in " + (t2 - t1) + " ms");
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("generateSets("+ howManySets+", "+ howManyIPsPerSet +") in " + (endTime - startTime) + " ms");

		return result;
	}

	
	
	public static void howLongTime(){
		long startTime = System.currentTimeMillis();
		
		long endTime = System.currentTimeMillis();
		System.out.println("howLongTime is " + (endTime - startTime) + " ms");
	}

}
