package setprocess;

import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisSet {

	public static void main(String[] args){
		jedis(SetUtils.generateSets(10, 1000000));
	}
	
	public static void jedis(Set[] sets){
		System.out.println();
		
		Jedis jedis = new Jedis();
		
		String[] keys = new String[sets.length];
		for (int i = 0; i < sets.length; i++) {
			keys[i] = "set" + i;
		}
		
		jedis.del(keys);
		
		for (int i = 0; i < sets.length; i++) {
			jedis.sadd("set" + i, (String[]) sets[i].toArray(new String[]{}));
		}
		
		
		long t2 = System.currentTimeMillis();
		Set result = jedis.sinter(keys);
		
		long t3 = System.currentTimeMillis();
		System.out.println("redis intersection in " + (t3 - t2) + " ms" + " and result size is" + result.size());
		System.out.println();
	}
}
