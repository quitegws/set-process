package setprocess;

import java.util.HashSet;
import java.util.Set;

public class SetUtils {

	//generate random ip addresses
	public static Set[] generateSets(int howManySets, int howManyIPsPerSet){
		if (howManySets <= 0 || howManyIPsPerSet <= 0) {
			System.out.println("wrong args in generateSets()");
			return null;
		}
		long startTime = System.currentTimeMillis();
		Set[] result = new HashSet[howManySets];
		
		for (int i = 0; i < howManySets; i++) {
			long t1 = System.currentTimeMillis();
			Set set = new HashSet<String>();
			for (int j = 0; j < howManyIPsPerSet; j++) {
				String ip = (int)(Math.random() * 50) + "."
							+ (int)(Math.random() * 50) + "."
							+ (int)(Math.random() * 50) + "."
							+ (int)(Math.random() * 50);
				set.add(ip);
			}
			result[i] = set;
			long t2 = System.currentTimeMillis();
			System.out.println("generate " + i + "th set done in " + (t2 - t1) + " ms");
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("generateSets("+ howManySets+", "+ howManyIPsPerSet +") in " + (endTime - startTime) + " ms");

		return result;
	}

}
