package com.bai.mapper.contentMapper;

import com.bai.mapper.baseMapper.BaseMapper;
import com.bai.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/*
内容页面，处理首页内容拉取、点赞、关注
 */
@Mapper
public interface ContentMapper extends BaseMapper {

    //点赞
    //分别插入
    int insertIntoLikeTable(Likes likes);

    int insertIntoPhotoLikeTable(@Param("like_id") int like_id, @Param("photo_id") int photo_id);

    //取消点赞
    int deleteLikeTable(@Param("like_id") int like_id);

    int deletePhotoLikeTable(@Param("like_id") int like_id);

    //删除帖子,delete左连接多表连删，牛逼克拉斯
    int deletePost(@Param("photo_id") int photo_id);

    //关注
    int subscribe(Subscribe subscribe);

    //取消关注
    int unSubscribe(@Param("now_user_id") int now_user_id, @Param("unsubscribe_user_id") int unsubscribe_user_id);

    //获取关注的人的id，用来拉取首页
    //获取关注的人，返回的是关注的人的user_id
    List<Integer> getSubscribe(@Param("user_id") int user_id);

    //添加评论
    int insertIntoCommentsTable(Comment comment);

    int insertIntoPhotoCommentTable(@Param("comment_id") int comment_id, @Param("photo_id") int photo_id);

    //获取评论
    List<Comment> getComment(Map<String, List<Integer>> comment_list_map);

    //通过photo_id获取评论
    List<Comment> getCommentByPhotoID(@Param("photo_id") int photo_id);

    //转发
    //分别插入
    //插入forwarding当中
    int insertIntoForwardingTable(Forwarding forwarding);

    //插入photo_forward当中
    int insertIntoPhotoForwardingTable(@Param("forward_id") int forward_id, @Param("photo_id") int photo_id);

}
