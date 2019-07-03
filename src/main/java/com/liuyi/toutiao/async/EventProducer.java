package com.liuyi.toutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.liuyi.toutiao.util.JedisAdapter;
import com.liuyi.toutiao.util.RedisKeyUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    private static final Logger log = Logger.getLogger(EventProducer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model) {
        try {
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            log.error("触发事件发生错误" + e.getMessage());
            return false;
        }
    }
}
