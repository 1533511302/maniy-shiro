package top.maniy.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author liuzonghua
 * @Package top.maniy.util
 * @Description:
 * @date 2018/10/9 15:09
 */
@Component
public class JedisUtil {
    @Resource
    private JedisPool jedisPool;

    private Jedis getResource(){
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis =getResource();
        try {
            jedis.set(key,value);
            return value;
        } finally {
            jedis.close();
        }


    }

    public void expire(byte[] key, int i) {
        Jedis jedis =getResource();
        try {
            jedis.expire(key,i);

        }finally {
            jedis.close();
        }
    }

    public void get(byte[] key) {
        Jedis jedis =getResource();
        try {
            jedis.get(key);

        } finally {
            jedis.close();
        }
    }
}
