package eap.comps.cache.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eap.comps.cache.Cache;
import eap.comps.cache.redis.connection.RedisConnectionFactory;

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
public class RedisCache implements Cache {
	
	private RedisConnectionFactory redisConnectionFactory;

	@Override
	public String set(final String key, final String value) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.set(key, value);
			}
		});
	}

	@Override
	public String set(final String key, final String value, final String nxxx, final String expx, final long time) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.set(key, value, nxxx, expx, time);
			}
		});
	}

	@Override
	public String get(final String key) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.get(key);
			}
		});
	}

	@Override
	public Boolean exists(final String key) {
		return execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) {
				return connection.exists(key);
			}
		});
	}

	@Override
	public Long persist(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.persist(key);
			}
		});
	}

	@Override
	public String type(final String key) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.type(key);
			}
		});
	}

	@Override
	public Long expire(final String key, final int seconds) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.expire(key, seconds);
			}
		});
	}

	@Override
	public Long pexpire(final String key, final long milliseconds) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.pexpire(key, milliseconds);
			}
		});
	}

	@Override
	public Long expireAt(final String key, final long unixTime) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.expireAt(key, unixTime);
			}
		});
	}

	@Override
	public Long pexpireAt(final String key, final long millisecondsTimestamp) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.pexpireAt(key, millisecondsTimestamp);
			}
		});
	}

	@Override
	public Long ttl(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.ttl(key);
			}
		});
	}

	@Override
	public Boolean setbit(final String key, final long offset, final boolean value) {
		return execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) {
				return connection.setbit(key, offset, value);
			}
		});
	}

	@Override
	public Boolean setbit(final String key, final long offset, final String value) {
		return execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) {
				return connection.setbit(key, offset, value);
			}
		});
	}

	@Override
	public Boolean getbit(final String key, final long offset) {
		return execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) {
				return connection.getbit(key, offset);
			}
		});
	}

	@Override
	public Long setrange(final String key, final long offset, final String value) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.setrange(key, offset, value);
			}
		});
	}

	@Override
	public String getrange(final String key, final long startOffset, final long endOffset) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.getrange(key, startOffset, endOffset);
			}
		});
	}

	@Override
	public String getSet(final String key, final String value) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.getSet(key, value);
			}
		});
	}

	@Override
	public Long setnx(final String key, final String value) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.setnx(key, value);
			}
		});
	}

	@Override
	public String setex(final String key, final int seconds, final String value) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.setex(key, seconds, value);
			}
		});
	}

	@Override
	public Long decrBy(final String key, final long integer) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.decrBy(key, integer);
			}
		});
	}

	@Override
	public Long decr(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.decr(key);
			}
		});
	}

	@Override
	public Long incrBy(final String key, final long integer) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.incrBy(key, integer);
			}
		});
	}

	@Override
	public Double incrByFloat(final String key, final double value) {
		return execute(new RedisCallback<Double>() {
			@Override
			public Double doInRedis(RedisConnection connection) {
				return connection.incrByFloat(key, value);
			}
		});
	}

	@Override
	public Long incr(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.incr(key);
			}
		});
	}

	@Override
	public Long append(final String key, final String value) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.append(key, value);
			}
		});
	}

	@Override
	public String substr(final String key, final int start, final int end) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.substr(key, start, end);
			}
		});
	}

	@Override
	public Long hset(final String key, final String field, final String value) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.hset(key, field, value);
			}
		});
	}

	@Override
	public String hget(final String key, final String field) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.hget(key, field);
			}
		});
	}

	@Override
	public Long hsetnx(final String key, final String field, final String value) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.hsetnx(key, field, value);
			}
		});
	}

	@Override
	public String hmset(final String key, final Map<String, String> hash) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.hmset(key, hash);
			}
		});
	}

	@Override
	public List<String> hmget(final String key, final String... fields) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.hmget(key, fields);
			}
		});
	}

	@Override
	public Long hincrBy(final String key, final String field, final long value) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.hincrBy(key, field, value);
			}
		});
	}

	@Override
	public Boolean hexists(final String key, final String field) {
		return execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) {
				return connection.hexists(key, field);
			}
		});
	}

	@Override
	public Long hdel(final String key, final String... field) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.hdel(key, field);
			}
		});
	}

	@Override
	public Long hlen(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.hlen(key);
			}
		});
	}

	@Override
	public Set<String> hkeys(final String key) {
		return execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) {
				return connection.hkeys(key);
			}
		});
	}

	@Override
	public List<String> hvals(final String key) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.hvals(key);
			}
		});
	}

	@Override
	public Map<String, String> hgetAll(final String key) {
		return execute(new RedisCallback<Map<String,String>>() {
			@Override
			public Map<String,String> doInRedis(RedisConnection connection) {
				return connection.hgetAll(key);
			}
		});
	}

	@Override
	public Long rpush(final String key, final String... string) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.rpush(key, string);
			}
		});
	}

	@Override
	public Long lpush(final String key, final String... string) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.lpush(key, string);
			}
		});
	}

	@Override
	public Long llen(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.llen(key);
			}
		});
	}

	@Override
	public List<String> lrange(final String key, final long start, final long end) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.lrange(key, start, end);
			}
		});
	}

	@Override
	public String ltrim(final String key, final long start, final long end) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.ltrim(key, start, end);
			}
		});
	}

	@Override
	public String lindex(final String key, final long index) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.lindex(key, index);
			}
		});
	}

	@Override
	public String lset(final String key, final long index, final String value) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.lset(key, index, value);
			}
		});
	}

	@Override
	public Long lrem(final String key, final long count, final String value) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.lrem(key, count, value);
			}
		});
	}

	@Override
	public String lpop(final String key) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.lpop(key);
			}
		});
	}

	@Override
	public String rpop(final String key) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.rpop(key);
			}
		});
	}

	@Override
	public Long sadd(final String key, final String... member) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.sadd(key, member);
			}
		});
	}

	@Override
	public Set<String> smembers(final String key) {
		return execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) {
				return connection.smembers(key);
			}
		});
	}

	@Override
	public Long srem(final String key, final String... member) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.srem(key, member);
			}
		});
	}

	@Override
	public String spop(final String key) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.spop(key);
			}
		});
	}

	@Override
	public Set<String> spop(final String key, final long count) {
		return execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) {
				return connection.spop(key, count);
			}
		});
	}

	@Override
	public Long scard(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.scard(key);
			}
		});
	}

	@Override
	public Boolean sismember(final String key, final String member) {
		return execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) {
				return connection.sismember(key, member);
			}
		});
	}

	@Override
	public String srandmember(final String key) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.srandmember(key);
			}
		});
	}

	@Override
	public List<String> srandmember(final String key, final int count) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.srandmember(key, count);
			}
		});
	}

	@Override
	public Long strlen(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.strlen(key);
			}
		});
	}

	@Override
	public Long lpushx(final String key, final String... string) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.lpushx(key, string);
			}
		});
	}

	@Override
	public Long rpushx(final String key, final String... string) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.rpushx(key, string);
			}
		});
	}

	@Override
	public List<String> blpop(final String arg) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.blpop(arg);
			}
		});
	}

	@Override
	public List<String> blpop(final int timeout, final String key) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.blpop(timeout, key);
			}
		});
	}

	@Override
	public List<String> brpop(final int timeout, final String key) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.brpop(timeout, key);
			}
		});
	}

	@Override
	public Long del(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.del(key);
			}
		});
	}

	@Override
	public String echo(final String string) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.echo(string);
			}
		});
	}

	@Override
	public Long move(final String key, final int dbIndex) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.move(key, dbIndex);
			}
		});
	}

	@Override
	public Long bitcount(final String key) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.bitcount(key);
			}
		});
	}

	@Override
	public Long bitcount(final String key, final long start, final long end) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.bitcount(key, start, end);
			}
		});
	}

	@Override
	public Long del(final String... keys) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.del(keys);
			}
		});
	}

	@Override
	public List<String> blpop(final int timeout, final String... keys) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.blpop(timeout, keys);
			}
		});
	}

	@Override
	public List<String> brpop(final int timeout, final String... keys) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.brpop(timeout, keys);
			}
		});
	}

	@Override
	public List<String> blpop(final String... args) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.blpop(args);
			}
		});
	}

	@Override
	public List<String> brpop(final String... args) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.brpop(args);
			}
		});
	}

	@Override
	public Set<String> keys(final String pattern) {
		return execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) {
				return connection.keys(pattern);
			}
		});
	}

	@Override
	public List<String> mget(final String... keys) {
		return execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) {
				return connection.mget(keys);
			}
		});
	}

	@Override
	public String mset(final String... keysvalues) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.mset(keysvalues);
			}
		});
	}

	@Override
	public Long msetnx(final String... keysvalues) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.msetnx(keysvalues);
			}
		});
	}

	@Override
	public String rename(final String oldkey, final String newkey) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.rename(oldkey, newkey);
			}
		});
	}

	@Override
	public Long renamenx(final String oldkey, final String newkey) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.renamenx(oldkey, newkey);
			}
		});
	}

	@Override
	public String rpoplpush(final String srckey, final String dstkey) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.rpoplpush(srckey, dstkey);
			}
		});
	}

	@Override
	public Set<String> sdiff(final String... keys) {
		return execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) {
				return connection.sdiff(keys);
			}
		});
	}

	@Override
	public Long sdiffstore(final String dstkey, final String... keys) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.sdiffstore(dstkey, keys);
			}
		});
	}

	@Override
	public Set<String> sinter(final String... keys) {
		return execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) {
				return connection.sinter(keys);
			}
		});
	}

	@Override
	public Long sinterstore(final String dstkey, final String... keys) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.sinterstore(dstkey, keys);
			}
		});
	}

	@Override
	public Long smove(final String srckey, final String dstkey, final String member) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.smove(srckey, dstkey, member);
			}
		});
	}

	@Override
	public Long sort(final String key, final String dstkey) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.sort(key, dstkey);
			}
		});
	}

	@Override
	public Set<String> sunion(final String... keys) {
		return execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) {
				return connection.sunion(keys);
			}
		});
	}

	@Override
	public Long sunionstore(final String dstkey, final String... keys) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.sunionstore(dstkey, keys);
			}
		});
	}

	@Override
	public String watch(final String... keys) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.watch(keys);
			}
		});
	}

	@Override
	public String unwatch() {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.unwatch();
			}
		});
	}

	@Override
	public String brpoplpush(final String source, final String destination, final int timeout) {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.brpoplpush(source, destination, timeout);
			}
		});
	}

	@Override
	public String randomKey() {
		return execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) {
				return connection.randomKey();
			}
		});
	}
	
	@Override
	public <T> T execute(RedisCallback<T> action) {
		RedisConnection connection = null;
		try {
			connection = getRedisConnection();
			return action.doInRedis(connection);
		} finally {
			connection.close();
		}
	}

	private RedisConnection getRedisConnection() {
		return redisConnectionFactory.getConnection();
	}
	
	public RedisConnectionFactory getRedisConnectionFactory() {
		return redisConnectionFactory;
	}
	public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
		this.redisConnectionFactory = redisConnectionFactory;
	}
}