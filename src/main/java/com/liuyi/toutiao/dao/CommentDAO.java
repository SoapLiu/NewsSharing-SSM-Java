package com.liuyi.toutiao.dao;

import com.liuyi.toutiao.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {

    String TABLE_NAME = "comment";
    String INSERT_FIELD = "content, entity_id, entity_type, created_date, user_id, status";
    String SELECT_FIELD = "id, " + INSERT_FIELD;

    @Insert({"insert into ", TABLE_NAME, " (", INSERT_FIELD,
            ") values (#{content}, #{entityId}, #{entityType}, #{createdDate}, #{userId}, #{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELD, " from ", TABLE_NAME,
            " where entity_type=#{entityType} and entity_id=#{entityId} order by id desc"})
    List<Comment> selectCommentByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId);

    @Select({"select count(id) from ", TABLE_NAME, " where entity_type=#{entityType} and entity_id=#{entityId}"})
    int getCommentCount(@Param("entityType") int entityType, @Param("entityId") int entityId);

    @Update({"update ", TABLE_NAME, " set status=#{status} where entity_type=#{entityType} and enetity_id=#{entityId}"})
    int updateCommentStatus(@Param("entityType") int entityType, @Param("entityId") int entityId, @Param("status") int status);
}
