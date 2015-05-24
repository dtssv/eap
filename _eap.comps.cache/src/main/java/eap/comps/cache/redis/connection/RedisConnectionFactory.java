package eap.comps.cache.redis.connection;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Pool;
import eap.comps.cache.redis.RedisConnection;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * @作者 chiknin@gmail.com
 * @创建时间 
 * @版本 1.00
 * @修改记录
 * <pre>
 * 版本       修改人         修改时间         修改内容描述
 * ----------------------------------------
 * 
 * ----------------------------------------
 * </pre>
 */
public class RedisConnectionFactory {
	
	private Object client;
	
	public RedisConnection getConnection() {
		
		if (client instanceof Jedis) {
			return new JedisConnection((Jedis) client);
		} 
		else if (client instanceof Pool) {
			Object pool = ((Pool) client).getResource();
			if (pool instanceof Jedis) {
				return new JedisConnection((Jedis) pool);
			} else if (pool instanceof ShardedJedis) {
				return new ShardedJedisConnection(((ShardedJedis) pool));
			}
		}
		
		throw new IllegalArgumentException("unknow redis client");
	}

	public Object getClient() {
		return client;
	}

	public void setClient(Object client) {
		this.client = client;
	}
}