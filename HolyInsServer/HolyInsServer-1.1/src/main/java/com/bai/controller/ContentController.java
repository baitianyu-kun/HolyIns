package com.bai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bai.pojo.Comment;
import com.bai.pojo.Forwarding;
import com.bai.pojo.Likes;
import com.bai.pojo.Subscribe;
import com.bai.service.content_service.ContentService;
import com.bai.state.ActivityStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.AbstractDocument;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    private ContentService contentService;

    @Autowired
    @Qualifier("contentServiceImpl")
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping("/deletePost")
    public String deletePost(@RequestParam("photo_id") int photo_id) {
        if (contentService.deletePost(photo_id) == ActivityStatus.POST_DELETE_SUCCESS)
            return ActivityStatus.STRING_POST_DELETE_SUCCESS;
        else
            return ActivityStatus.STRING_POST_DELETE_FAILED;
    }

    @PostMapping("/likePost")
    public String LikePost(@RequestParam("json_like") String JSON_Likes) {
        //测试数据
        /*Likes likes_test = new Likes();
        likes_test.setPhoto_id(1);
        likes_test.setLike_user_id(1);
        likes_test.setDate_updated(new Date());
        likes_test.setDate_created(new Date());
        JSON_Likes = JSON.toJSONString(likes_test);*/
        //
        Likes likes = JSON.parseObject(JSON_Likes, Likes.class);
        if (contentService.LikePost(likes) == ActivityStatus.LIKE_SUCCESS)
            return ActivityStatus.STRING_LIKE_SUCCESS;
        else
            return ActivityStatus.STRING_LIKE_FAILED;
    }

    @PostMapping("/likeUserPost")
    public String LikeUserPost(@RequestParam("json_like") String JSON_Likes) {
        //测试数据
        /*Likes likes_test = new Likes();
        likes_test.setPhoto_id(1);
        likes_test.setLike_user_id(1);
        likes_test.setDate_updated(new Date());
        likes_test.setDate_created(new Date());
        JSON_Likes = JSON.toJSONString(likes_test);*/
        //
        Likes likes = JSON.parseObject(JSON_Likes, Likes.class);
        int result = contentService.LikeUserPost(likes);
        if (result == ActivityStatus.LIKE_FAILED)
            return ActivityStatus.STRING_LIKE_FAILED;
        else
            return String.valueOf(result);
    }

    @PostMapping("/cancelLikePost")
    public String cancelLikePost(@RequestParam("like_id") int like_id) {
        if (contentService.cancelLikePost(like_id) == ActivityStatus.CANCEL_LIKE_SUCCESS)
            return ActivityStatus.STRING_CANCEL_LIKE_SUCCESS;
        else
            return ActivityStatus.STRING_CANCEL_LIKE_FAILED;
    }

    @PostMapping("/subscribe")
    public String Subscribe(@RequestParam("json_subscribe") String JSON_Subscribe) {

        //测试数据
        //1号关注了21号
        /*Subscribe subscribe_test=new Subscribe();
        subscribe_test.setSubscribe_time(new Date());
        subscribe_test.setSubscribe_userid(21);
        subscribe_test.setNow_user_id(1);
        JSON_Subscribe=JSON.toJSONString(subscribe_test);*/
        //

        Subscribe subscribe = JSON.parseObject(JSON_Subscribe, Subscribe.class);
        if (contentService.Subscribe(subscribe) == ActivityStatus.SUBSCRIBE_SUCCESS)
            return ActivityStatus.STRING_SUBSCRIBE_SUCCESS;
        else
            return ActivityStatus.STRING_SUBSCRIBE_FAILED;
    }

    @PostMapping("/unSubscribe")
    public String unSubscribe(@RequestParam("unsubscribe_user_id") int unsubscribe_user_id, @RequestParam("now_user_id") int now_user_id) {
        if (contentService.unSubscribe(now_user_id, unsubscribe_user_id) == ActivityStatus.UNSUBSCRIBE_SUCCESS) {
            return ActivityStatus.STRING_UNSUBSCRIBE_SUCCESS;
        } else {
            return ActivityStatus.STRING_UNSUBSCRIBE_FAILED;
        }
    }

    @PostMapping("/getSubscribeStatus")
    public String getSubscribeStatus(@RequestParam("now_user_id") int now_user_id, @RequestParam("subscribe_user_id") int subscribe_user_id) {
        if (contentService.getSubscribeStatus(now_user_id, subscribe_user_id) == ActivityStatus.SUBSCRIBED)
            return ActivityStatus.STRING_SUBSCRIBED;
        else
            return ActivityStatus.STRING_UNSUBSCRIBE;
    }

    @PostMapping("/getSubscribe")
    public String GetSubscribe(@RequestParam("user_id") int user_id) {
        return JSON.toJSONString(contentService.getSubscribe(user_id));
    }

    @PostMapping("/getSubscribedPost")
    public String getSubscribedPost(@RequestParam("user_id") int user_id) {
        return JSON.toJSONString(contentService.getSubscribedPost(contentService.getSubscribe(user_id)));
    }

    @PostMapping("/getSubscribedUserPost")
    public String getSubscribedUserPost(@RequestParam("subscribed_user_ids") String JSON_subscribed_user_ids) {
        /*List<Integer> test=new ArrayList<>();
        test.add(2);
        JSON_subscribed_user_ids=JSON.toJSONString(test);*/
        List<Integer> subscribed_user_ids = JSON.parseArray(JSON_subscribed_user_ids, Integer.class);
        return JSON.toJSONString(contentService.getSubscribedUserPost(subscribed_user_ids));
    }

    @PostMapping("/commentPost")
    public String CommentPost(@RequestParam("json_comment") String JSON_Comment) {

        //测试数据
        //1号用户评论了1号照片
        /*Comment comment_test=new Comment();
        comment_test.setComment_time(new Date());
        comment_test.setComment_text("commentPost_controller_测试");
        comment_test.setComment_user_id(1);
        comment_test.setPhoto_id(1);
        JSON_Comment=JSON.toJSONString(comment_test);*/
        //

        Comment comment = JSON.parseObject(JSON_Comment, Comment.class);
        if (contentService.CommentPost(comment) == ActivityStatus.COMMENT_SUCCESS)
            return ActivityStatus.STRING_COMMENT_SUCCESS;
        else
            return ActivityStatus.STRING_COMMENT_FAILED;
    }

    @PostMapping("/forwardPost")
    public String ForwardPost(@RequestParam("json_forwarding") String JSON_Forwarding) {
        //测试数据
        //1号用户转发了1号照片，并附上了文字
        /*Forwarding forwarding_test=new Forwarding();
        forwarding_test.setForward_text("ForwardPost_controller_测试");
        forwarding_test.setForward_user_id(1);
        forwarding_test.setPhoto_id(1);
        forwarding_test.setForward_time(new Date());
        JSON_Forwarding=JSON.toJSONString(forwarding_test);*/
        //

        Forwarding forwarding = JSON.parseObject(JSON_Forwarding, Forwarding.class);
        if (contentService.ForwardPost(forwarding) == ActivityStatus.FORWARD_SUCCESS)
            return ActivityStatus.STRING_FORWARD_SUCCESS;
        else
            return ActivityStatus.STRING_FORWARD_FAILED;
    }

    @PostMapping("/getMessageTest")
    public String getMessageTest(@RequestParam("get_message") String message) {
        System.out.println(message);
        return "success";
    }

    @PostMapping("/getComment")
    public String getComment(@RequestParam("comment_ids") String JSON_comment_ids) {
        //测试数据
        /*
        List<Integer> comment_ids_test = new ArrayList<>();
        comment_ids_test.add(8);
        JSON_comment_ids = JSON.toJSONString(comment_ids_test);
        */
        /*List<Integer> comment_ids = JSONObject.parseObject(JSON_comment_ids, new TypeReference<ArrayList<Integer>>() {
        });
        System.err.println(JSON_comment_ids);*/

        List<Integer> comment_ids = JSONObject.parseArray(JSON_comment_ids, Integer.class);
        String JSON_RETURN = JSON.toJSONString(contentService.getComment(comment_ids));
        if (JSON_RETURN.equals("[]"))
            return ActivityStatus.STRING_GET_COMMENT_FAILED;
        else
            return JSON_RETURN;
    }

    @PostMapping("/getCommentByPhotoID")
    public String getCommentByPhotoID(@RequestParam("photo_id") int photo_id) {
        String JSON_RETURN = JSON.toJSONString(contentService.getCommentByPhotoID(photo_id));
        if (JSON_RETURN.equals("[]")) {
            return ActivityStatus.STRING_GET_COMMENT_BY_PHOTO_ID_FAILED;
        } else {
            return JSON_RETURN;
        }
    }

}
