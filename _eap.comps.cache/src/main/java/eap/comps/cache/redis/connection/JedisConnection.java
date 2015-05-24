package eap.comps.cache.redis.connection;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

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
public class JedisConnection extends AbstractJedisConnection {
	
	private Jedis jedis;
	
	public JedisConnection(Jedis jedis) {
		super(jedis);
		this.jedis = jedis;
	}

	@Override
	public Long del(String... keys) {
		return jedis.del(keys);
	}

	@Override
	public List<String> blpop(int timeout, String... keys) {
		return jedis.blpop(timeout, keys);
	}

	@Override
	public List<String> brpop(int timeout, String... keys) {
		return jedis.brpop(timeout, keys);
	}

	@Override
	public List<String> blpop(String... args) {
		return jedis.blpop(args);
	}

	@Override
	public List<String> brpop(String... args) {
		return jedis.brpop(args);
	}

	@Override
	public Set<String> keys(String pattern) {
		return jedis.keys(pattern);
	}

	@Override
	public List<String> mget(String... keys) {
		return jedis.mget(keys);
	}

	@Override
	public String mset(String... keysvalues) {
		return jedis.mset(keysvalues);
	}

	@Override
	public Long msetnx(String... keysvalues) {
		return jedis.msetnx(keysvalues);
	}

	@Override
	public String rename(String oldkey, String newkey) {
		return jedis.rename(oldkey, newkey);
	}

	@Override
	public Long renamenx(String oldkey, String newkey) {
		return jedis.renamenx(oldkey, newkey);
	}

	@Override
	public String rpoplpush(String srckey, String dstkey) {
		return jedis.rpoplpush(srckey, dstkey);
	}

	@Override
	public Set<String> sdiff(String... keys) {
		return jedis.sdiff(keys);
	}

	@Override
	public Long sdiffstore(String dstkey, String... keys) {
		return jedis.sdiffstore(dstkey, keys);
	}

	@Override
	public Set<String> sinter(String... keys) {
		return jedis.sinter(keys);
	}

	@Override
	public Long sinterstore(String dstkey, String... keys) {
		return jedis.sinterstore(dstkey, keys);
	}

	@Override
	public Long smove(String srckey, String dstkey, String member) {
		return jedis.smove(srckey, dstkey, member);
	}

	@Override
	public Long sort(String key, String dstkey) {
		return jedis.sort(key, dstkey);
	}

	@Override
	public Set<String> sunion(String... keys) {
		return jedis.sunion(keys);
	}

	@Override
	public Long sunionstore(String dstkey, String... keys) {
		return jedis.sunionstore(dstkey, keys);
	}

	@Override
	public String watch(String... keys) {
		return jedis.watch(keys);
	}

	@Override
	public String unwatch() {
		return jedis.unwatch();
	}

	@Override
	public String brpoplpush(String source, String destination, int timeout) {
		return jedis.brpoplpush(source, destination, timeout);
	}

	@Override
	public String randomKey() {
		return jedis.randomKey();
	}

	@Override
	public void close() {
		jedis.close();
	}

	@Override
	public Object getResource() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
}