package com.liuyi.toutiao.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisAdapter implements InitializingBean {

    private static final Logger log = Logger.getLogger(JedisAdapter.class);

    private JedisPool pool = null;

    public static void print(int index, Object obj) {
        System.out.println(String.format("%d, %s", index, obj.toString()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("localhost", 6379);
    }

    private Jedis getResource() {
        return pool.getResource();
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
            return false;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }
}
