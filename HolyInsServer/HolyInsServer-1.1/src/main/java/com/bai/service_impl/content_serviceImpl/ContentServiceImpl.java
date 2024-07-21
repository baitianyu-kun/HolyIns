package com.bai.service_impl.content_serviceImpl;

import com.bai.mapper.contentMapper.ContentMapper;
import com.bai.pojo.*;
import com.bai.service.content_service.ContentService;
import com.bai.service_impl.base_serviceImpl.BaseServiceImpl;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl extends BaseServiceImpl implements ContentService {
    private ContentMapper contentMapper;

    @Autowired
    public void setContentMapper(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }

    @Override
    /*
     * @Description: 
     * @Param: [likes]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public int LikePost(Likes likes) {
        Likes _likes = likes;
        //如果第一个表插入成功，（影响的行数不为0）
        if (contentMapper.insertIntoLikeTable(likes) != 0) {
            //如果第二个表插入成功（影响的行数不为0）
            if (contentMapper.insertIntoPhotoLikeTable(_likes.getLike_id(), _likes.getPhoto_id()) != 0)
                return ActivityStatus.LIKE_SUCCESS;
            else
                return ActivityStatus.LIKE_FAILED;
        } else {
            return ActivityStatus.LIKE_FAILED;
        }
    }

    //插入成功之后返回like_id
    @Override
    /*
     * @Description:
     * @Param: [likes]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/20 11:00
     **/
    public int LikeUserPost(Likes likes) {
        Likes _likes = likes;
        //如果第一个表插入成功，（影响的行数不为0）
        if (contentMapper.insertIntoLikeTable(likes) != 0) {
            //如果第二个表插入成功（影响的行数不为0）
            if (contentMapper.insertIntoPhotoLikeTable(_likes.getLike_id(), _likes.getPhoto_id()) != 0)
                return _likes.getLike_id();
            else
                return ActivityStatus.LIKE_FAILED;
        } else {
            return ActivityStatus.LIKE_FAILED;
        }
    }

    @Override
    /*
     * @Description: 
     * @Param: [like_id]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    public int cancelLikePost(int like_id) {
        if (contentMapper.deleteLikeTable(like_id) != 0) {
            if (contentMapper.deletePhotoLikeTable(like_id) != 0)
                return ActivityStatus.CANCEL_LIKE_SUCCESS;
            else
                return ActivityStatus.CANCEL_LIKE_FAILED;
        } else {
            return ActivityStatus.CANCEL_LIKE_FAILED;
        }
    }

    @Override
    /*
     * @Description: 
     * @Param: [photo_id]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public int deletePost(int photo_id) {
        if (contentMapper.deletePost(photo_id) != 0)
            return ActivityStatus.POST_DELETE_SUCCESS;
        else
            return ActivityStatus.POST_DELETE_FAILED;
    }

    @Override
    /*
     * @Description: 
     * @Param: [subscribe]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public int Subscribe(Subscribe subscribe) {
        if (contentMapper.subscribe(subscribe) != 0)
            return ActivityStatus.SUBSCRIBE_SUCCESS;
        else
            return ActivityStatus.SUBSCRIBE_FAILED;
    }

    @Override
    /*
     * @Description: 
     * @Param: [now_user_id, unsubscribe_user_id]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public int unSubscribe(int now_user_id, int unsubscribe_user_id) {
        if (contentMapper.unSubscribe(now_user_id, unsubscribe_user_id) != 0)
            return ActivityStatus.UNSUBSCRIBE_SUCCESS;
        else
            return ActivityStatus.UNSUBSCRIBE_FAILED;
    }

    @Override
    /*
     * @Description: 
     * @Param: [now_user_id, subscribe_user_id]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public int getSubscribeStatus(int now_user_id, int subscribe_user_id) {
        //获取所有关注了的人，如果发现我关注的人中有这个人，则返回已经关注，否则则返回没有关注
        int flag = 0;
        for (Integer integer : contentMapper.getSubscribe(now_user_id)) {
            if (integer == subscribe_user_id) {
                flag = 1;
                break;
            }
        }
        if (flag == 1)
            return ActivityStatus.SUBSCRIBED;
        else
            return ActivityStatus.UNSUBSCRIBE;
    }

    @Override
    /*
     * @Description: 
     * @Param: [user_id]
     * @Return: java.util.List<java.lang.Integer>
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public List<Integer> getSubscribe(int user_id) {
        return contentMapper.getSubscribe(user_id);
    }

    @Override
    /*
     * @Description: 
     * @Param: [user_ids]
     * @Return: java.util.List<java.util.List<com.bai.pojo.Post>>
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public List<List<Post>> getSubscribedPost(List<Integer> user_ids) {
        List<List<Post>> list_posts = new ArrayList<>();
        for (Integer user_id : user_ids) {
            list_posts.add(findAllPostsByUserID(user_id));
        }
        return list_posts;
    }

    @Override
    /*
     * @Description: 
     * @Param: [user_ids]
     * @Return: java.util.List<com.bai.pojo.UserPost>
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public List<UserPost> getSubscribedUserPost(List<Integer> user_ids) {
        List<UserPost> list_posts = new ArrayList<>();
        for (Integer user_id : user_ids) {
            for (UserPost userPost : findAllUserPostsByUserID(user_id)) {
                list_posts.add(userPost);
            }
        }
       /* for (Integer user_id : user_ids) {
            list_posts.add(findAllUserPostsByUserID(user_id));
        }*/
        return list_posts;
    }

    @Override
    /*
     * @Description: 
     * @Param: [comment]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public int CommentPost(Comment comment) {
        Comment _comment = comment;
        if (contentMapper.insertIntoCommentsTable(_comment) != 0) {
            if (contentMapper.insertIntoPhotoCommentTable(_comment.getComment_id(), _comment.getPhoto_id()) != 0)
                return ActivityStatus.COMMENT_SUCCESS;
            else
                return ActivityStatus.COMMENT_FAILED;
        } else {
            return ActivityStatus.COMMENT_FAILED;
        }
    }

    @Override
    /*
     * @Description: 
     * @Param: [comment_ids]
     * @Return: java.util.List<com.bai.pojo.Comment>
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public List<Comment> getComment(List<Integer> comment_ids) {
        //使用map
        Map<String, List<Integer>> comment_list_map = new HashMap<>();
        comment_list_map.put("comment_ids", comment_ids);
        return contentMapper.getComment(comment_list_map);
    }

    @Override
    /*
     * @Description: 
     * @Param: [photo_id]
     * @Return: java.util.List<com.bai.pojo.Comment>
     * @Author: baitianyu
     * @Date: 2021/5/6 21:57
     **/
    
    public List<Comment> getCommentByPhotoID(int photo_id) {
        return contentMapper.getCommentByPhotoID(photo_id);
    }

    @Override
    /*
     * @Description:
     * @Param: [forwarding]
     * @Return: int
     * @Author: baitianyu
     * @Date: 2021/5/6 21:59
     **/

    public int ForwardPost(Forwarding forwarding) {
        Forwarding _forwarding = forwarding;
        if (contentMapper.insertIntoForwardingTable(_forwarding) != 0) {
            if (contentMapper.insertIntoPhotoForwardingTable(_forwarding.getForward_id(), _forwarding.getPhoto_id()) != 0)
                return ActivityStatus.FORWARD_SUCCESS;
            else
                return ActivityStatus.FORWARD_FAILED;
        } else {
            return ActivityStatus.FORWARD_FAILED;
        }
    }

}
