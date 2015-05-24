package eap.comps.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eap.comps.cache.redis.RedisCallback;

//import redis.clients.jedis.Client;
//import redis.clients.jedis.ScanResult;
//import redis.clients.jedis.SortingParams;
//import redis.clients.jedis.Tuple;

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
public interface Cache {
	
	// ----------------------------------------
	// @see redis.clients.jedis.JedisCommands
	// ----------------------------------------
	
	String set(final String key, final String value);

	String set(final String key, final String value, final String nxxx, final String expx, final long time);

	String get(final String key);

	Boolean exists(final String key);

	Long persist(final String key);

	String type(final String key);

	Long expire(final String key, final int seconds);

	Long pexpire(final String key, final long milliseconds);

	Long expireAt(final String key, final long unixTime);

	Long pexpireAt(final String key, final long millisecondsTimestamp);

	Long ttl(final String key);

	Boolean setbit(final String key, final long offset, boolean value);

	Boolean setbit(final String key, final long offset, final String value);

	Boolean getbit(final String key, final long offset);

	Long setrange(final String key, final long offset, final String value);

	String getrange(final String key, final long startOffset, final long endOffset);

	String getSet(final String key, final String value);

	Long setnx(final String key, final String value);

	String setex(final String key, final int seconds, final String value);

	Long decrBy(final String key, final long integer);

	Long decr(final String key);

	Long incrBy(final String key, final long integer);

	Double incrByFloat(final String key, final double value);

	Long incr(final String key);

	Long append(final String key, final String value);

	String substr(final String key, final int start, final int end);

	Long hset(final String key, final String field, final String value);

	String hget(final String key, final String field);

	Long hsetnx(final String key, final String field, final String value);

	String hmset(final String key, final Map<String, String> hash);

	List<String> hmget(final String key, final String... fields);

	Long hincrBy(final String key, final String field, final long value);

	Boolean hexists(final String key, final String field);

	Long hdel(final String key, final String... field);

	Long hlen(final String key);

	Set<String> hkeys(final String key);

	List<String> hvals(final String key);

	Map<String, String> hgetAll(final String key);

	Long rpush(final String key, final String... string);

	Long lpush(final String key, final String... string);

	Long llen(final String key);

	List<String> lrange(final String key, final long start, final long end);

	String ltrim(final String key, final long start, final long end);

	String lindex(final String key, final long index);

	String lset(final String key, final long index, final String value);

	Long lrem(final String key, final long count, final String value);

	String lpop(final String key);

	String rpop(final String key);

	Long sadd(final String key, final String... member);

	Set<String> smembers(final String key);

	Long srem(final String key, final String... member);

	String spop(final String key);

	Set<String> spop(final String key, final long count);

	Long scard(final String key);

	Boolean sismember(final String key, final String member);

	String srandmember(final String key);

	List<String> srandmember(final String key, final int count);

	Long strlen(final String key);

//	Long zadd(final String key, final double score, final String member);

//	Long zadd(final String key, Map<String, Double> scoreMembers);

//	Set<String> zrange(final String key, final long start, final long end);

//	Long zrem(final String key, final String... member);

//	Double zincrby(final String key, final double score, final String member);

//	Long zrank(final String key, final String member);

//	Long zrevrank(final String key, final String member);

//	Set<String> zrevrange(final String key, final long start, final long end);

//	Set<Tuple> zrangeWithScores(final String key, final long start, final long end);

//	Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end);

//	Long zcard(final String key);

//	Double zscore(final String key, final String member);

//	List<String> sort(final String key);

//	List<String> sort(final String key, SortingParams sortingParameters);

//	Long zcount(final String key, final double min, final double max);

//	Long zcount(final String key, final String min, final String max);

//	Set<String> zrangeByScore(final String key, final double min, final double max);

//	Set<String> zrangeByScore(final String key, final String min, final String max);

//	Set<String> zrevrangeByScore(final String key, final double max, final double min);

//	Set<String> zrangeByScore(final String key, final double min, final double max, final int offset, final int count);

//	Set<String> zrevrangeByScore(final String key, final String max, final String min);

//	Set<String> zrangeByScore(final String key, final String min, final String max, final int offset, final int count);

//	Set<String> zrevrangeByScore(final String key, final double max, final double min, final int offset, final int count);

//	Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max);

//	Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min);

//	Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max, final int offset, final int count);

//	Set<String> zrevrangeByScore(final String key, final String max, final String min, final int offset, final int count);

//	Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max);

//	Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min);

//	Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max, final int offset, final int count);

//	Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset, final int count);

//	Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min, final int offset, final int count);

//	Long zremrangeByRank(final String key, final long start, final long end);

//	Long zremrangeByScore(final String key, final double start, final double end);

//	Long zremrangeByScore(final String key, final String start, final String end);

//	Long zlexcount(final String key, final String min, final String max);

//	Set<String> zrangeByLex(final String key, final String min, final String max);

//	Set<String> zrangeByLex(final String key, final String min, final String max, final int offset,
//	final int count);

//	Set<String> zrevrangeByLex(final String key, final String max, final String min);

//	Set<String> zrevrangeByLex(final String key, final String max, final String min,
//	final int offset, final int count);

//	Long zremrangeByLex(final String key, final String min, final String max);

//	Long linsert(final String key, Client.LIST_POSITION where, final String pivot, final String value);

	Long lpushx(final String key, final String... string);

	Long rpushx(final String key, final String... string);

	List<String> blpop(final String arg);

	List<String> blpop(final int timeout, final String key);

	List<String> brpop(final int timeout, final String key);

	Long del(final String key);

	String echo(final String string);

	Long move(final String key, final int dbIndex);

	Long bitcount(final String key);

	Long bitcount(final String key, final long start, final long end);

//	ScanResult<Map.Entry<String, final String>> hscan(final String key, final String cursor);

//	ScanResult<String> sscan(final String key, final String cursor);

//	ScanResult<Tuple> zscan(final String key, final String cursor);

//	Long pfadd(final String key, final String... elements);

//	long pfcount(final String key);
	
	
	// ----------------------------------------
	// @see redis.clients.jedis.MultiKeyCommands
	// ----------------------------------------
	
	Long del(final String... keys);

	List<String> blpop(final int timeout, final String... keys);

	List<String> brpop(final int timeout, final String... keys);

	List<String> blpop(final String... args);

	List<String> brpop(final String... args);

	Set<String> keys(final String pattern);

	List<String> mget(final String... keys);

	String mset(final String... keysvalues);

	Long msetnx(final String... keysvalues);

	String rename(final String oldkey, final String newkey);

	Long renamenx(final String oldkey, final String newkey);

	String rpoplpush(final String srckey, final String dstkey);

	Set<String> sdiff(final String... keys);

	Long sdiffstore(final String dstkey, final String... keys);

	Set<String> sinter(final String... keys);

	Long sinterstore(final String dstkey, final String... keys);

	Long smove(final String srckey, final String dstkey, final String member);

//	Long sort(final String key, SortingParams sortingParameters, final String dstkey);

	Long sort(final String key, final String dstkey);

	Set<String> sunion(final String... keys);

	Long sunionstore(final String dstkey, final String... keys);

	String watch(final String... keys);

	String unwatch();

//	Long zinterstore(final String dstkey, final String... sets);

//	Long zinterstore(final String dstkey, ZParams params, final String... sets);

//	Long zunionstore(final String dstkey, final String... sets);

//	Long zunionstore(final String dstkey, ZParams params, final String... sets);

	String brpoplpush(final String source, final String destination, final int timeout);

//	Long publish(final String channel, final String message);

//	void subscribe(JedisPubSub jedisPubSub, final String... channels);

//	void psubscribe(JedisPubSub jedisPubSub, final String... patterns);

	String randomKey();

//	Long bitop(BitOP op, final String destKey, final String... srcKeys);

//	ScanResult<String> scan(final String cursor);

//	String pfmerge(final String destkey, final String... sourcekeys);

//	long pfcount(final String... keys);
	
	// ----------------------------------------
	// ext
	// ----------------------------------------
	
	public <T> T execute(RedisCallback<T> action);
}