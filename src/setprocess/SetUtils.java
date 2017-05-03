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
		
		Set intersection = new HashSet<>(intersectionNum);
		for (int i = 0; i < intersectionNum; i++) {
			String ipTmp = getOneRandomIPAddress(256);
			while (intersection.contains(ipTmp)) {
				ipTmp = getOneRandomIPAddress(256);
			}
			intersection.add(ipTmp);
		}
		
		for (int i = 0; i < howManySets; i++) {
			Set<String> set = new HashSet<String>();
			set.addAll(intersection);
			long t1 = System.currentTimeMillis();
			for (int j = 0; j < howManyIPsPerSet - intersectionNum; j++) {
				String ip = getOneRandomIPAddress(256);
				while (set.contains(ip)) {
					ip = getOneRandomIPAddress(256);
				}
				set.add(ip);
			}
			result[i] = set;
			long t2 = System.currentTimeMillis();
			System.out.println("generate " + i + "th set done in " + (t2 - t1) + " ms the size is " + result[i].size());
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

	public static String getOneRandomIPAddress(int range){
		if (range <0 || range > 256) {
			System.err.println(range + " is not a valid range!");
			return "";
		}
		String ip = (int)(Math.random() * 255) + "."
				+ (int)(Math.random() * 255) + "."
				+ (int)(Math.random() * 255) + "."
				+ (int)(Math.random() * 255);
		return ip;
	}
	
}
