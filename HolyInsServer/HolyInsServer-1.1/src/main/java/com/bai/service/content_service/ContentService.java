package com.bai.service.content_service;

import com.bai.pojo.*;
import com.bai.service.base_service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ContentService {
    //帖子点赞
    int LikePost(Likes likes);
    //帖子点赞2,点赞成功之后会返回点赞的id
    int LikeUserPost(Likes likes);
    //取消点赞
    int cancelLikePost(int like_id);
    //删除帖子
    int deletePost(@Param("photo_id")int photo_id);

    //关注作者
    int Subscribe(Subscribe subscribe);
    //取消关注
    int unSubscribe(int now_user_id, int unsubscribe_user_id);
    //获取关注状态
    int getSubscribeStatus(int now_user_id, int subscribe_user_id);

    //获取关注人的id，然后在业务层findPost吧，不能把公共的做成公共的controller
    List<Integer> getSubscribe(int user_id);

    //拉取首页post,根据我订阅的人的id,一个人有好多个帖子，组成List<Post>,再把所有人的这些帖子再包装成一个List
    List<List<Post>> getSubscribedPost(List<Integer> user_ids);
    List<UserPost>getSubscribedUserPost(List<Integer> user_ids);

    //评论帖子
    int CommentPost(Comment comment);

    //获取评论
    List<Comment> getComment(List<Integer> comment_ids);
    //通过photo_id获取评论
    List<Comment> getCommentByPhotoID(int photo_id);

    //转发帖子
    int ForwardPost(Forwarding forwarding);
}
