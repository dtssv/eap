package eap.comps.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import eap.base.BaseTest;
import eap.comps.cache.redis.RedisCallback;
import eap.comps.cache.redis.RedisConnection;

public class CacheTest extends BaseTest {
	
	@Autowired
	@Qualifier("cache")
	private Cache cache;
	
	@Test
	public void set_1() {
		cache.set("test1", "1");
		System.out.println(cache.get("test1"));
	}
	
	@Test
	public void pipe_1() {
		List<Object> result = cache.execute(new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) {
				Jedis jedis = (Jedis) connection.getResource();
				Pipeline pipeline = jedis.pipelined();
				pipeline.del("test1");
				pipeline.set("test2", "2");
				pipeline.get("test2");
				return pipeline.syncAndReturnAll();
			}
		});
		
		System.out.println(result);
	}
}
