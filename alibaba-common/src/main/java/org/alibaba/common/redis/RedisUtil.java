package org.alibaba.common.redis;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alibaba.common.context.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;


public class RedisUtil {

	protected static RedisDataSource source;

	private RedisUtil() {
	}

	static {
		source = (RedisDataSource) SpringContextHolder.getBean("redisDataSource");
	}

	

	public static Long publish(String channel, String value) {
		Jedis jedis = source.getRedis();
		if (jedis != null) {
			return jedis.publish(channel, value);
		}
		return null;
	}
	
	public static void subscribe(JedisPubSub jedisPubSub, String ...channels) {
		Jedis jedis = source.getRedis();
		if (jedis != null) {
			jedis.subscribe(jedisPubSub, channels);
		}
	}

	/**
	 * 设置单个值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(String key, String value) {
		boolean flag = false;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				String result = shardedJedis.set(key, value);
				if (StringUtils.equals(result, "OK")) {
					flag = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				source.closeResource(shardedJedis);
			}
		}
		return flag;
	}

	/**
	 * 获取单个值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static String get(String key) {
		String result = "";
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				result = shardedJedis.get(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				source.closeResource(shardedJedis);
			}
		}
		return result;
	}

	/**
	 * 获取所有key
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static Set<String> keys(String pattern) {
		return source.keys(pattern);
	}

	/**
	 * 检查key是否存在
	 *
	 * @param key
	 * @return
	 */
	public static Boolean exists(String key) {
		Boolean result = false;
		ShardedJedis shardedJedis = source.getRedisClient();
		try {
			if (null != (shardedJedis)) {
				result = shardedJedis.exists(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String type(String key) {
		String result = "";
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				result = shardedJedis.type(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				source.closeResource(shardedJedis);
			}
		}
		return result;
	}

	/**
	 * 设置key某段时间后失效
	 *
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public static Long expire(String key, int seconds) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				result = shardedJedis.expire(key, seconds);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				source.closeResource(shardedJedis);
			}
		}
		return result;
	}

	/**
	 * 设置key在某个时间点失效
	 *
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public static Long expireAt(String key, long unixTime) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				result = shardedJedis.expireAt(key, unixTime);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	public static Long ttl(String key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				result = shardedJedis.ttl(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	public static boolean setbit(String key, long offset, boolean value) {
		Boolean result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				result = shardedJedis.setbit(key, offset, value);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	public static boolean getbit(String key, long offset) {
		Boolean result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				result = shardedJedis.getbit(key, offset);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	public static long setrange(String key, long offset, String value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				result = shardedJedis.setrange(key, offset, value);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	public static String getrange(String key, long startOffset, long endOffset) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (null != (shardedJedis)) {
			try {
				result = shardedJedis.getrange(key, startOffset, endOffset);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	public static String getSet(String key, String value) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getSet(key, value);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long setnx(String key, String value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.setnx(key, value);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String setex(String key, int seconds, String value) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.setex(key, seconds, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long decrBy(String key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.decrBy(key, integer);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long decr(String key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.decr(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long incrBy(String key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.incrBy(key, integer);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long incr(String key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.incr(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long append(String key, String value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.append(key, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String substr(String key, int start, int end) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.substr(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hset(String key, String field, String value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hset(key, field, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String hget(String key, String field) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hget(key, field);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hsetnx(String key, String field, String value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hsetnx(key, field, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String hmset(String key, Map<String, String> hash) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hmset(key, hash);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<String> hmget(String key, String... fields) {
		List<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hmget(key, fields);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hincrBy(String key, String field, long value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hincrBy(key, field, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Boolean hexists(String key, String field) {
		Boolean result = false;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hexists(key, field);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long del(String key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.del(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hdel(String key, String field) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hdel(key, field);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hlen(String key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hlen(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> hkeys(String key) {
		Set<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hkeys(key);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<String> hvals(String key) {
		List<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hvals(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Map<String, String> hgetAll(String key) {
		Map<String, String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hgetAll(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	// ================list ====== l表示 list或 left, r表示right====================
	public static Long rpush(String key, String string) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.rpush(key, string);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long lpush(String key, String string) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.lpush(key, string);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long llen(String key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.llen(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<String> lrange(String key, long start, long end) {
		List<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.lrange(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String ltrim(String key, long start, long end) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.ltrim(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String lindex(String key, long index) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.lindex(key, index);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String lset(String key, long index, String value) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.lset(key, index, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long lrem(String key, long count, String value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.lrem(key, count, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String lpop(String key) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.lpop(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String rpop(String key) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.rpop(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	// return 1 add a not exist value ,
	// return 0 add a exist value
	public static Long sadd(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.sadd(key, member);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> smembers(String key) {
		Set<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.smembers(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long srem(String key, String member) {
		ShardedJedis shardedJedis = source.getRedisClient();

		Long result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.srem(key, member);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String spop(String key) {
		ShardedJedis shardedJedis = source.getRedisClient();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.spop(key);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long scard(String key) {
		ShardedJedis shardedJedis = source.getRedisClient();
		Long result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.scard(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Boolean sismember(String key, String member) {
		ShardedJedis shardedJedis = source.getRedisClient();
		Boolean result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.sismember(key, member);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String srandmember(String key) {
		ShardedJedis shardedJedis = source.getRedisClient();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.srandmember(key);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zadd(String key, double score, String member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.zadd(key, score, member);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> zrange(String key, int start, int end) {
		Set<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.zrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zrem(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.zrem(key, member);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Double zincrby(String key, double score, String member) {
		Double result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zincrby(key, score, member);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zrank(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrank(key, member);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zrevrank(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrank(key, member);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> zrevrange(String key, int start, int end) {
		Set<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrange(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrangeWithScores(String key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeWithScores(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeWithScores(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zcard(String key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zcard(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Double zscore(String key, String member) {
		Double result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zscore(key, member);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<String> sort(String key) {
		List<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sort(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<String> sort(String key, SortingParams sortingParameters) {
		List<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sort(key, sortingParameters);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zcount(String key, double min, double max) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zcount(key, min, max);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> zrangeByScore(String key, double min, double max) {
		Set<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScore(key, min, max);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> zrevrangeByScore(String key, double max, double min) {
		Set<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScore(key, max, min);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		Set<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScore(key, min, max, offset, count);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		Set<String> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScore(key, max, min, offset, count);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScoreWithScores(key, min, max);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScoreWithScores(key, max, min);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zremrangeByRank(String key, int start, int end) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zremrangeByRank(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zremrangeByScore(String key, double start, double end) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zremrangeByScore(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.linsert(key, where, pivot, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String set(byte[] key, byte[] value) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.set(key, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static byte[] get(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.get(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Boolean exists(byte[] key) {
		Boolean result = false;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.exists(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String type(byte[] key) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.type(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long expire(byte[] key, int seconds) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.expire(key, seconds);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long expireAt(byte[] key, long unixTime) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.expireAt(key, unixTime);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long ttl(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.ttl(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static byte[] getSet(byte[] key, byte[] value) {
		byte[] result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.getSet(key, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long setnx(byte[] key, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.setnx(key, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String setex(byte[] key, int seconds, byte[] value) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.setex(key, seconds, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long decrBy(byte[] key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.decrBy(key, integer);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long decr(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.decr(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long incrBy(byte[] key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.incrBy(key, integer);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long incr(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.incr(key);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long append(byte[] key, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.append(key, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static byte[] substr(byte[] key, int start, int end) {
		byte[] result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.substr(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hset(byte[] key, byte[] field, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hset(key, field, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static byte[] hget(byte[] key, byte[] field) {
		byte[] result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hget(key, field);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hsetnx(byte[] key, byte[] field, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hsetnx(key, field, value);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String hmset(byte[] key, Map<byte[], byte[]> hash) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hmset(key, hash);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<byte[]> hmget(byte[] key, byte[]... fields) {
		List<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hmget(key, fields);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hincrBy(byte[] key, byte[] field, long value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hincrBy(key, field, value);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Boolean hexists(byte[] key, byte[] field) {
		Boolean result = false;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hexists(key, field);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hdel(byte[] key, byte[] field) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hdel(key, field);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hlen(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hlen(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<byte[]> hkeys(byte[] key) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hkeys(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Collection<byte[]> hvals(byte[] key) {
		Collection<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hvals(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Map<byte[], byte[]> hgetAll(byte[] key) {
		Map<byte[], byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hgetAll(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long rpush(byte[] key, byte[] string) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.rpush(key, string);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long lpush(byte[] key, byte[] string) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lpush(key, string);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long llen(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.llen(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<byte[]> lrange(byte[] key, int start, int end) {
		List<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lrange(key, start, end);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String ltrim(byte[] key, int start, int end) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.ltrim(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static byte[] lindex(byte[] key, int index) {
		byte[] result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lindex(key, index);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String lset(byte[] key, int index, byte[] value) {
		String result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lset(key, index, value);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long lrem(byte[] key, int count, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lrem(key, count, value);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static byte[] lpop(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lpop(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static byte[] rpop(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.rpop(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long sadd(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sadd(key, member);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<byte[]> smembers(byte[] key) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.smembers(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long srem(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.srem(key, member);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static byte[] spop(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.spop(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long scard(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.scard(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Boolean sismember(byte[] key, byte[] member) {
		Boolean result = false;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sismember(key, member);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static byte[] srandmember(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.srandmember(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zadd(byte[] key, double score, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zadd(key, score, member);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<byte[]> zrange(byte[] key, int start, int end) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrange(key, start, end);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zrem(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrem(key, member);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Double zincrby(byte[] key, double score, byte[] member) {
		Double result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zincrby(key, score, member);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zrank(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrank(key, member);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zrevrank(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrank(key, member);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<byte[]> zrevrange(byte[] key, int start, int end) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrange(key, start, end);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeWithScores(key, start, end);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeWithScores(key, start, end);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zcard(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zcard(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Double zscore(byte[] key, byte[] member) {
		Double result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zscore(key, member);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<byte[]> sort(byte[] key) {
		List<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sort(key);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		List<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sort(key, sortingParameters);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zcount(byte[] key, double min, double max) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zcount(key, min, max);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScore(key, min, max);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScore(key, min, max, offset, count);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScoreWithScores(key, min, max);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScore(key, max, min);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScore(key, max, min, offset, count);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScoreWithScores(key, max, min);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zremrangeByRank(byte[] key, int start, int end) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zremrangeByRank(key, start, end);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zremrangeByScore(byte[] key, double start, double end) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zremrangeByScore(key, start, end);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = source.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.linsert(key, where, pivot, value);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Jedis getShard(byte[] key) {
		ShardedJedis shardedJedis = source.getRedisClient();
		Jedis result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getShard(key);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Jedis getShard(String key) {
		ShardedJedis shardedJedis = source.getRedisClient();
		Jedis result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getShard(key);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static JedisShardInfo getShardInfo(byte[] key) {
		ShardedJedis shardedJedis = source.getRedisClient();
		JedisShardInfo result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getShardInfo(key);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static JedisShardInfo getShardInfo(String key) {
		ShardedJedis shardedJedis = source.getRedisClient();
		JedisShardInfo result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getShardInfo(key);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String getKeyTag(String key) {
		ShardedJedis shardedJedis = source.getRedisClient();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getKeyTag(key);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Collection<JedisShardInfo> getAllShardInfo() {
		ShardedJedis shardedJedis = source.getRedisClient();
		Collection<JedisShardInfo> result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getAllShardInfo();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Collection<Jedis> getAllShards() {
		ShardedJedis shardedJedis = source.getRedisClient();
		Collection<Jedis> result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getAllShards();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> getRedisKeys(String key) {
		Set<String> result = null;
		ShardedJedis jedis = null;
		try {
			jedis = source.getRedisClient();
			Set<String> keys = new HashSet<String>();
			for (Jedis j : jedis.getAllShards()) {
				keys.addAll(j.keys(key));
				j.close();
			}
			return keys;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) jedis.close();
		}
		return result;
	}

}
