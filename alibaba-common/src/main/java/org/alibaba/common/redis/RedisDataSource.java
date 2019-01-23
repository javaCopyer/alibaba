package org.alibaba.common.redis;

import java.util.Set;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


@Component(value="redisDataSource")
public class RedisDataSource {

	@Resource
	private ShardedJedisPool shardedJedisPool;
	@Resource
	private JedisPool jedisPool;
	@Resource
	private Jedis jedis;

	public ShardedJedis getRedisClient() {
		try {
			return shardedJedisPool.getResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Jedis getRedis() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			if (jedis != null) {
				jedis.close();
			}
			e.printStackTrace();
		}
		return jedis;
	}

	public Set<String> keys(String pattern) {
		return jedis.keys(pattern);
	}

	public void closeResource(ShardedJedis shardedJedis) {
		shardedJedis.close();
	}

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
