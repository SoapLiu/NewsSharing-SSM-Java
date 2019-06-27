package com.liuyi.toutiao.controller;

import com.liuyi.toutiao.model.*;
import com.liuyi.toutiao.service.MessageService;
import com.liuyi.toutiao.service.UserService;
import com.liuyi.toutiao.util.ToutiaoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    private static final Logger log = Logger.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {

        try {
            Message message = new Message();
            message.setContent(content);
            message.setFromId(fromId);
            message.setToId(toId);
            message.setCreatedDate(new Date());
            message.setConversationId(fromId < toId ?
                    String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            messageService.addMessage(message);
            return ToutiaoUtil.getJSONString(message.getId());
        } catch (Exception e) {
            log.error("发送消息失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "发送消息失败");
        }
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(@RequestParam("conversationId") String conversationId, Model model) {
        try {
            List<Message> messages = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messageVo = new ArrayList<>();
            for(Message m : messages) {
                ViewObject vo = new ViewObject();
                vo.set("messageSender", userService.getUser(m.getFromId()));
                vo.set("messageReceiver", userService.getUser(m.getToId()));
                messageService.updateReadStatus(conversationId, EntityType.HAS_READ);
                vo.set("message", m);
                messageVo.add(vo);
            }
            model.addAttribute("messageVo", messageVo);
        } catch (Exception e) {
            log.error("获取消息详情失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Model model) {
        try {
            User user = hostHolder.getUser();
            List<Message> conversations = messageService.getConversationList(user.getId(), 0, 5);
            List<ViewObject> conversationVos = new ArrayList<>();
            for(Message m : conversations) {
                ViewObject vo = new ViewObject();
                vo.set("conversationReceiver", userService.getUser(m.getToId()));
                vo.set("conversation", m);
                vo.set("unreadCount", messageService.getReadStatusCount(m.getConversationId(), EntityType.UNREAD, m.getToId()));
                conversationVos.add(vo);
            }
            model.addAttribute("conversationVos", conversationVos);
        } catch (Exception e) {
            log.error("获取消息列表失败" + e.getMessage());
        }
        return "letter";
    }
}
