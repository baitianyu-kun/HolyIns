package com.bai.HolyIns.service;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bai.HolyIns.pojo.Comment;
import com.bai.HolyIns.pojo.Forwarding;
import com.bai.HolyIns.pojo.Likes;
import com.bai.HolyIns.pojo.Subscribe;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.pojo.UserPost;
import com.bai.HolyIns.state.NetWorkProperties;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.HandlerUtils;
import com.bai.HolyIns.utils.HttpUtils;
import com.bai.HolyIns.utils.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ContentService {
    //删除post
    public void deletePost(Handler handler, int photo_id) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.DELETE_POST, "POST");
                    String url_message = "photo_id=" + photo_id;
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("deletePost_status", str, handler, ThreadState.DELETE_POST);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    HandlerUtils.sendStringMessage("deletePost_status", "delete_post_error", handler, ThreadState.DELETE_POST);
                }
            }
        }.start();
    }

    //订阅
    public void Subscribe(Subscribe subscribe, Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.SUBSCRIBE, "POST");
                    String url_message = "json_subscribe=" + URLEncoder.encode(JSONObject.toJSONString(subscribe), "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("subscribe_status", str, handler, ThreadState.SUBSCRIBE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //订阅失败就返回失败
                    HandlerUtils.sendStringMessage("subscribe_status", "subscribe_error", handler, ThreadState.SUBSCRIBE);
                }
            }
        }.start();
    }

    //取消订阅
    public void unSubscribe(int now_user_id, int unsubscribe_user_id, Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.UNSUBSCRIBE, "POST");
                    String url_message = "now_user_id=" + now_user_id + "&unsubscribe_user_id=" + unsubscribe_user_id;
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("unsubscribe_status", str, handler, ThreadState.UN_SUBSCRIBE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //取消订阅失败的话就返回失败
                    HandlerUtils.sendStringMessage("unsubscribe_status", "unsubscribe_error", handler, ThreadState.UN_SUBSCRIBE);
                }
            }
        }.start();
    }

    //获取订阅的人
    public void getSubscribe(int user_id, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.GET_SUBSCRIBE, "POST");
                        String url_message = "user_id=" + user_id;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("get_subscribe_status", str, handler, ThreadState.GET_SUBSCRIBE);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //todo
    //判断是否订阅
    public void getSubscribeStatus(int now_user_id, int subscribe_user_id, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.SUBSCRIBE_STATUS, "POST");
                        String url_message = "now_user_id=" + now_user_id + "&subscribe_user_id=" + subscribe_user_id;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("getSubscribeStatus_status", str, handler, ThreadState.SUBSCRIBE_STATUS);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //获取订阅的post
    public void getSubscribedPost(Handler handler, List<Integer> subscribed_user_id) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.GET_SUBSCRIBED_USER_POST, "POST");
                        String url_message = "subscribed_user_ids=" + URLEncoder.encode(JSON.toJSONString(subscribed_user_id), "UTF-8");
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("get_subscribed_post_status", str, handler, ThreadState.GET_SUBSCRIBED_USER_POST);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //获取推荐的帖子
    public void getRecommendPost(int now_user_id, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.GET_RECOMMEND_POST, "POST");
                        String url_message = "now_user_id=" + now_user_id;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("get_recommend_post_status", str, handler, ThreadState.GET_RECOMMEND_POST);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //点赞
    public void Like(Likes likes, Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.LIKE, "POST");
                    String url_message = "json_like=" + URLEncoder.encode(JSONObject.toJSONString(likes), "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("like_status", str, handler, ThreadState.LIKE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    HandlerUtils.sendStringMessage("like_status", "like_error", handler, ThreadState.LIKE);
                }
            }
        }.start();
    }

    //点赞升级版，会返回like_id
    public void likeUserPost(Likes likes, Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.LIKE_USER_POST, "POST");
                    String url_message = "json_like=" + URLEncoder.encode(JSONObject.toJSONString(likes), "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("like_status", str, handler, ThreadState.LIKE_USER_POST);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    HandlerUtils.sendStringMessage("like_status", "like_error", handler, ThreadState.LIKE_USER_POST);
                }
            }
        }.start();
    }

    //取消点赞
    public void cancelLikePost(int like_id, Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.CANCEL_LIKE, "POST");
                    String url_message = "like_id=" + like_id;
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("cancel_like_status", str, handler, ThreadState.CANCEL_LIKE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    HandlerUtils.sendStringMessage("cancel_like_status", "cancel_like_error", handler, ThreadState.CANCEL_LIKE);
                }
            }
        }.start();
    }

    //拉取评论
    public void getComment(List<Integer> comment_ids, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.GET_COMMENT, "POST");
                        String url_message = "comment_ids=" + URLEncoder.encode(JSONObject.toJSONString(comment_ids), "UTF-8");
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("get_comment_status", str, handler, ThreadState.GET_COMMENT);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //根据photo_id拉取评论
    public void getCommentByPhotoID(int photo_id, Handler handler) {
        final boolean[] serverNotConnected = {true};
        new Thread() {
            @Override
            public void run() {
                while (serverNotConnected[0]) {
                    try {
                        HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.GET_COMMENT_BY_PHOTO_ID, "POST");
                        String url_message = "photo_id=" + photo_id;
                        connection.getOutputStream().write(url_message.getBytes());
                        if (connection.getResponseCode() == 200) {
                            serverNotConnected[0] = false;
                            String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                            HandlerUtils.sendStringMessage("get_comment_by_photo_id_status", str, handler, ThreadState.GET_COMMENT_BY_PHOTO_ID);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //评论
    public void Comment(Comment comment, Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.COMMENT, "POST");
                    String url_message = "json_comment=" + URLEncoder.encode(JSONObject.toJSONString(comment), "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("comment_status", str, handler, ThreadState.COMMENT);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    HandlerUtils.sendStringMessage("comment_status", "comment_error", handler, ThreadState.COMMENT);
                }
            }
        }.start();
    }

    //转发
    public void Forwarding(Forwarding forwarding, Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = HttpUtils.getUrlConnection(NetWorkProperties.FORWARD, "POST");
                    String url_message = "json_forwarding=" + URLEncoder.encode(JSONObject.toJSONString(forwarding), "UTF-8");
                    connection.getOutputStream().write(url_message.getBytes());
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtils.GetStringFromServer(connection.getInputStream());
                        HandlerUtils.sendStringMessage("forwarding_status", str, handler, ThreadState.FORWARD);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                    HandlerUtils.sendStringMessage("forwarding_status", "forwarding_error", handler, ThreadState.FORWARD);
                }
            }
        }.start();
    }
}


