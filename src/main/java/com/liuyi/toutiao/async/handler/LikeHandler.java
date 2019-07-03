package com.liuyi.toutiao.async.handler;

import com.liuyi.toutiao.async.EventHandler;
import com.liuyi.toutiao.async.EventModel;
import com.liuyi.toutiao.async.EventType;
import com.liuyi.toutiao.controller.MessageController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {

    private static final Logger log = Logger.getLogger(LikeHandler.class);

    @Autowired
    MessageController messageController;

    @Override
    public void doHandle(EventModel model) {
        try {
            messageController.addMessage(model.getActorId(),
                    model.getEnetityOwnerId(),
                    "user: " + model.getActorId() + " likes your post!");
        } catch (Exception e) {
            log.error("处理事件失败" + e.getMessage());
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
