package com.liuyi.toutiao;

import com.liuyi.toutiao.dao.CommentDAO;
import com.liuyi.toutiao.dao.LoginTicketDAO;
import com.liuyi.toutiao.dao.NewsDAO;
import com.liuyi.toutiao.dao.UserDAO;
import com.liuyi.toutiao.model.Comment;
import com.liuyi.toutiao.model.LoginTicket;
import com.liuyi.toutiao.model.News;
import com.liuyi.toutiao.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitDatabaseTest {

    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    CommentDAO commentDAO;

    @Test
    public void contextLoads() {
        Random random = new Random();
        for(int i = 0; i < 19; i++) {
            User user = new User();
            user.setHeadUrl("/images/img/default.png");
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            news.setCreatedDate(date);
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("Title{%d}", i));
            news.setLink(String.format("http://liuyi.cool/%d.html", i));
            news.setImage(String.format("/images/img/news.jpeg", i));
            newsDAO.addNews(news);

            LoginTicket loginTicket = new LoginTicket();
            loginTicket.setUserId(i+1);
            loginTicket.setStatus(0);
            loginTicket.setExpired(date);
            loginTicket.setTicket(String.format("TICKET%d", i));
            loginTicketDAO.addTicket(loginTicket);

            loginTicketDAO.updateStatus(loginTicket.getTicket(), 2);

            Comment comment = new Comment();
            comment.setContent("content test");
            comment.setCreatedDate(new Date());
            comment.setEntityId(i);
            comment.setEntityType(0);   //0为news的评论
            comment.setStatus(0);
            comment.setUserId(i);
            commentDAO.addComment(comment);

        }
    }

}
