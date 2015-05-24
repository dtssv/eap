package eap.comps.cache.redis.connection;

import redis.clients.jedis.ShardedJedis;

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
public class ShardedJedisConnection extends AbstractJedisConnection {
	
	private ShardedJedis shardedJedis;
	
	public ShardedJedisConnection(ShardedJedis shardedJedis) {
		super(shardedJedis);
		this.shardedJedis = shardedJedis;
	}

	@Override
	public Object getResource() {
		return shardedJedis;
	}

	@Override
	public void close() {
		shardedJedis.close();
	}
}
