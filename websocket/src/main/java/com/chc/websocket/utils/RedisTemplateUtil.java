package com.chc.websocket.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisTemplateUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public RedisTemplateUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *  设置失效时间
     */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#expire#redis expire exception==>{}",e.getMessage());
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#hasKey#redis haskey exception ==>{}",e.getMessage());
            return false;
        }
    }

    public Boolean delete(String key){
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#delete#redis delete exception ==>{}",e.getMessage());
            return false;
        }
    }


    /**************************************************** String start **********************************************/
    public Object get(String key){
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#get#redis get exception ==>{}",e.getMessage());
            return null;
        }
    }

    public boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#set#redis set exception ==>{}",e.getMessage());
            return false;
        }
    }

    public boolean setAndExpire(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#setAndExpire#redis setAndExpire exception ==>{}",e.getMessage());
            return false;
        }
    }

    public Long increment(String key, long delta){
        try {
            if(delta<0){
                throw new RuntimeException("递增因子必须大于0");
            }
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#increment#redis increment exception ==>{}",e.getMessage());
            return null;
        }
    }

    public Long decrement(String key, long delta){
        try {
            if(delta<0){
                throw new RuntimeException("递减因子必须大于0");
            }
            return redisTemplate.opsForValue().increment(key, -delta);
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#decrement#redis decrement exception ==>{}",e.getMessage());
            return null;
        }
    }

    /************************************************** Hash *************************************************/
    /**
     * HashGet
     */
    public Object hget(String key,String item){
        try {
            return redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#hget#redis hget exception ==>{}",e.getMessage());
            return false;
        }
    }

    /**
     * 获取hashKey对应的所有键值
     */
    public Map<Object,Object> hmget(String key){
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#hmget#redis hmget exception ==>{}",e.getMessage());
            return null;
        }
    }

    /**
     * HashSet
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#hmset#redis hmset exception ==>{}",e.getMessage());
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     */
    public boolean hmsetAndExpire(String key, Map<String,Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#hmsetAndExpire#redis hmsetAndExpire exception ==>{}",e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     */
    public boolean hset(String key,String item,Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#hset#redis hset exception ==>{}",e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hsetAndExpire(String key,String item,Object value,long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("#RedisTemplateUtil#hsetAndExpire#redis hsetAndExpire exception ==>{}",e.getMessage());
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     */
    public double hashIncrement(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return
     */
    public double hashDecrement(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item,-by);
    }

    public Boolean addZset(String key,Object obj,double seqNo){
        return redisTemplate.opsForZSet().add(key,obj,seqNo);
    }
}

