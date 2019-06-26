package com.liuyi.toutiao.service;

import com.liuyi.toutiao.dao.CommentDAO;
import com.liuyi.toutiao.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    public List<Comment> selectCommentByEntity(int entityType, int entityId) {
        return commentDAO.selectCommentByEntity(entityType, entityId);
    }

    public int getCommentCount(int entityType, int entityId) {
        return commentDAO.getCommentCount(entityType, entityId);
    }

    public void deleteComment(int entityType, int entityId) {
        commentDAO.updateCommentStatus(entityType, entityId, 1);
    }

}
