package setprocess;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisSet {

	public static void main(String[] args){
		jedisIntersection(SetUtils.generateSets(10, 1000000, 10000));
	}
	
	public static Set jedisIntersection(Set<String>[] sets){
		
		JedisPoolConfig config = new JedisPoolConfig();
		JedisPool pool = new JedisPool(config, "localhost", 6379, 100000);
		Jedis jedis = pool.getResource();
		
		String[] keys = new String[sets.length];
		for (int i = 0; i < sets.length; i++) {
			keys[i] = "set" + i;
		}
		
		jedis.del(keys);
		
		for (int i = 0; i < sets.length; i++) {
			jedis.sadd("set" + i, (String[]) sets[i].toArray(new String[]{}));
		}
		
		
		long t2 = System.currentTimeMillis();
		Set<String> result = jedis.sinter(keys);
		
		long t3 = System.currentTimeMillis();
		System.out.println("redis intersection in " + (t3 - t2) + " ms" + " and result size is " + result.size());
		System.out.println();
		jedis.del(keys);
		jedis.close();
		pool.close();
		return result;
	}


	public static Set jedisUnion(Set<String>[] sets){
		JedisPoolConfig config = new JedisPoolConfig();
		JedisPool pool = new JedisPool(config, "localhost", 6379, 100000);
		Jedis jedis = pool.getResource();
		String[] keys = new String[sets.length];
		for (int i = 0; i < sets.length; i++) {
			keys[i] = "set" + i;
		}
		
		jedis.del(keys);
		for (int i = 0; i < sets.length; i++) {
			jedis.sadd("set" + i, (String[]) sets[i].toArray(new String[]{}));
		}
		
		long t2 = System.currentTimeMillis();
		Set<String> result = jedis.sunion(keys);
		
		long t3 = System.currentTimeMillis();
		System.out.println("redis union in " + (t3 - t2) + " ms" + " and result size is " + result.size());
		System.out.println();
		jedis.del(keys);
		jedis.close();
		pool.close();
		return result;
	}
	
}
