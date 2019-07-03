package com.liuyi.toutiao.async.handler;

import com.liuyi.toutiao.async.EventHandler;
import com.liuyi.toutiao.async.EventModel;
import com.liuyi.toutiao.async.EventType;
import com.liuyi.toutiao.model.Message;
import com.liuyi.toutiao.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel model) {
        //判断是否异常登录
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setFromId(0);
        message.setCreatedDate(new Date());
        message.setContent("suspicious log in activity");
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
