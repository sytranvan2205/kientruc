package test;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main {
	public static void main(String[] args) {
		JedisPool pool = new JedisPool("localhost",6379);
		try(Jedis jedis = pool.getResource()){
			jedis.set("foo", "bar");
			System.out.println(jedis.get("foo"));
			
            Map<String, String> hash = new HashMap<>();;
            hash.put("name", "John");
            jedis.hset("user-session:123", hash);
            System.out.println(jedis.hgetAll("user-session:123"));
		}
	}
}
