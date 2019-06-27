package com.liuyi.toutiao.service;

import com.liuyi.toutiao.util.JedisAdapter;
import com.liuyi.toutiao.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 如果喜欢返回1，如果不喜欢返回-1，否则返回0
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        } else if(jedisAdapter.sismember(dislikeKey, String.valueOf(userId))) {
            return -1;
        } else return 0;
    }

    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));
        jedisAdapter.srem(dislikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdapter.sadd(dislikeKey, String.valueOf(userId));
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

}
