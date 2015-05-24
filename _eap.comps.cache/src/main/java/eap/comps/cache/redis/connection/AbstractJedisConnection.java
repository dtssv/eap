package eap.comps.cache.redis.connection;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisCommands;
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
public abstract class AbstractJedisConnection implements RedisConnection {
	
	private JedisCommands jedisCommands;
	
	public AbstractJedisConnection(JedisCommands jedisCommands) {
		this.jedisCommands = jedisCommands;
	}
	
	@Override
	public String set(String key, String value) {
		return jedisCommands.set(key, value);
	}

	@Override
	public String set(String key, String value, String nxxx, String expx,long time) {
		return jedisCommands.set(key, value, nxxx, expx, time);
	}

	@Override
	public String get(String key) {
		return jedisCommands.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return jedisCommands.exists(key);
	}

	@Override
	public Long persist(String key) {
		return jedisCommands.persist(key);
	}

	@Override
	public String type(String key) {
		return jedisCommands.type(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedisCommands.expire(key, seconds);
	}

	@Override
	public Long pexpire(String key, long milliseconds) {
		return jedisCommands.pexpire(key, milliseconds);
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		return jedisCommands.expireAt(key, unixTime);
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		return jedisCommands.pexpireAt(key, millisecondsTimestamp);
	}

	@Override
	public Long ttl(String key) {
		return jedisCommands.ttl(key);
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		return jedisCommands.setbit(key, offset, value);
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		return jedisCommands.setbit(key, offset, value);
	}

	@Override
	public Boolean getbit(String key, long offset) {
		return jedisCommands.getbit(key, offset);
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		return jedisCommands.setrange(key, offset, value);
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		return jedisCommands.getrange(key, startOffset, endOffset);
	}

	@Override
	public String getSet(String key, String value) {
		return jedisCommands.getSet(key, value);
	}

	@Override
	public Long setnx(String key, String value) {
		return jedisCommands.setnx(key, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return jedisCommands.setex(key, seconds, value);
	}

	@Override
	public Long decrBy(String key, long integer) {
		return jedisCommands.decrBy(key, integer);
	}

	@Override
	public Long decr(String key) {
		return jedisCommands.decr(key);
	}

	@Override
	public Long incrBy(String key, long integer) {
		return jedisCommands.incrBy(key, integer);
	}

	@Override
	public Double incrByFloat(String key, double value) {
		return jedisCommands.incrByFloat(key, value);
	}

	@Override
	public Long incr(String key) {
		return jedisCommands.incr(key);
	}

	@Override
	public Long append(String key, String value) {
		return jedisCommands.append(key, value);
	}

	@Override
	public String substr(String key, int start, int end) {
		return jedisCommands.substr(key, start, end);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return jedisCommands.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCommands.hget(key, field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		return jedisCommands.hsetnx(key, field, value);
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return jedisCommands.hmset(key, hash);
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return jedisCommands.hmget(key, fields);
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		return jedisCommands.hincrBy(key, field, value);
	}

	@Override
	public Boolean hexists(String key, String field) {
		return jedisCommands.hexists(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		return jedisCommands.hdel(key, field);
	}

	@Override
	public Long hlen(String key) {
		return jedisCommands.hlen(key);
	}

	@Override
	public Set<String> hkeys(String key) {
		return jedisCommands.hkeys(key);
	}

	@Override
	public List<String> hvals(String key) {
		return jedisCommands.hvals(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return jedisCommands.hgetAll(key);
	}

	@Override
	public Long rpush(String key, String... string) {
		return jedisCommands.rpush(key, string);
	}

	@Override
	public Long lpush(String key, String... string) {
		return jedisCommands.lpush(key, string);
	}

	@Override
	public Long llen(String key) {
		return jedisCommands.llen(key);
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		return jedisCommands.lrange(key, start, end);
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return jedisCommands.ltrim(key, start, end);
	}

	@Override
	public String lindex(String key, long index) {
		return jedisCommands.lindex(key, index);
	}

	@Override
	public String lset(String key, long index, String value) {
		return jedisCommands.lset(key, index, value);
	}

	@Override
	public Long lrem(String key, long count, String value) {
		return jedisCommands.lrem(key, count, value);
	}

	@Override
	public String lpop(String key) {
		return jedisCommands.lpop(key);
	}

	@Override
	public String rpop(String key) {
		return jedisCommands.rpop(key);
	}

	@Override
	public Long sadd(String key, String... member) {
		return jedisCommands.sadd(key, member);
	}

	@Override
	public Set<String> smembers(String key) {
		return jedisCommands.smembers(key);
	}

	@Override
	public Long srem(String key, String... member) {
		return jedisCommands.srem(key, member);
	}

	@Override
	public String spop(String key) {
		return jedisCommands.spop(key);
	}

	@Override
	public Set<String> spop(String key, long count) {
		return jedisCommands.spop(key, count);
	}

	@Override
	public Long scard(String key) {
		return jedisCommands.scard(key);
	}

	@Override
	public Boolean sismember(String key, String member) {
		return jedisCommands.sismember(key, member);
	}

	@Override
	public String srandmember(String key) {
		return jedisCommands.srandmember(key);
	}

	@Override
	public List<String> srandmember(String key, int count) {
		return jedisCommands.srandmember(key, count);
	}

	@Override
	public Long strlen(String key) {
		return jedisCommands.strlen(key);
	}

	@Override
	public Long lpushx(String key, String... string) {
		return jedisCommands.lpushx(key, string);
	}

	@Override
	public Long rpushx(String key, String... string) {
		return jedisCommands.rpushx(key, string);
	}

	@Override
	public List<String> blpop(String arg) {
		return jedisCommands.blpop(arg);
	}

	@Override
	public List<String> blpop(int timeout, String key) {
		return jedisCommands.blpop(timeout, key);
	}

	@Override
	public List<String> brpop(int timeout, String key) {
		return jedisCommands.brpop(timeout, key);
	}

	@Override
	public Long del(String key) {
		return jedisCommands.del(key);
	}

	@Override
	public String echo(String string) {
		return jedisCommands.echo(string);
	}

	@Override
	public Long move(String key, int dbIndex) {
		return jedisCommands.move(key, dbIndex);
	}

	@Override
	public Long bitcount(String key) {
		return jedisCommands.bitcount(key);
	}

	@Override
	public Long bitcount(String key, long start, long end) {
		return jedisCommands.bitcount(key, start, end);
	}
	
	@Override
	public Long del(String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<String> blpop(int timeout, String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<String> brpop(int timeout, String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<String> blpop(String... args) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<String> brpop(String... args) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Set<String> keys(String pattern) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<String> mget(String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String mset(String... keysvalues) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Long msetnx(String... keysvalues) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String rename(String oldkey, String newkey) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Long renamenx(String oldkey, String newkey) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String rpoplpush(String srckey, String dstkey) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Set<String> sdiff(String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Long sdiffstore(String dstkey, String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Set<String> sinter(String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Long sinterstore(String dstkey, String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Long smove(String srckey, String dstkey, String member) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Long sort(String key, String dstkey) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Set<String> sunion(String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Long sunionstore(String dstkey, String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String watch(String... keys) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String unwatch() {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String brpoplpush(String source, String destination, int timeout) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String randomKey() {
		throw new UnsupportedOperationException("not implemented");
	}
}