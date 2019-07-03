package com.liuyi.toutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.liuyi.toutiao.util.JedisAdapter;
import com.liuyi.toutiao.util.RedisKeyUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private static final Logger log = Logger.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    //一个事件（EventType）所触发的处理器（EventHandler）列表
    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    //将触发事件与触发的handler对应，初始化config
    public void afterPropertiesSet() throws Exception {
        //遍历上下文里所有实现EventHandler的类
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans != null) {
            //遍历实现了EventHandler的类
            for(Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                //查找这些类所支持处理的事件
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for(EventType type : eventTypes) {
                    //如果config里没有包含这个事件，则添加
                    if(!config.containsKey(type)) {
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    //如果config里包含这个事件，则在该事件对应的handlerList中添加handler
                    config.get(type).add(entry.getValue());
                }
            }
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);
                    for(String event : events) {
                        if(event.equals(key)) continue;
                        EventModel eventModel = JSONObject.parseObject(event, EventModel.class);
                        if(!config.containsKey(eventModel.getType())) {
                            log.error("不能识别的事件");
                            continue;
                        }
                        for(EventHandler eventHandler : config.get(eventModel.getType())) {
                            eventHandler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
