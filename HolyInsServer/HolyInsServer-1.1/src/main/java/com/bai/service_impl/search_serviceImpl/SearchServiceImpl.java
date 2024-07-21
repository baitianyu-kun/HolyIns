package com.bai.service_impl.search_serviceImpl;

import com.bai.mapper.searchMapper.SearchMapper;
import com.bai.pojo.Post;
import com.bai.pojo.UserInfo;
import com.bai.pojo.UserPost;
import com.bai.service.search_service.SearchService;
import com.bai.service_impl.base_serviceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl extends BaseServiceImpl implements SearchService {

    private SearchMapper searchMapper;

    @Autowired
    public void setSearchMapper(SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }

    @Override
    public UserInfo findUser(Map<String, String> map) {
        return searchMapper.findUser(map);
    }

    @Override
    public UserInfo findUserByID(String user_id) {
        Map<String, String> idmap = new HashMap<>();
        idmap.put("user_id", user_id);
        return searchMapper.findUser(idmap);
    }

    @Override
    public UserInfo findUserByAccount(String user_account) {
        Map<String, String> accountmap = new HashMap<>();
        accountmap.put("account", user_account);
        return findUser(accountmap);
    }

    @Override
    public UserInfo findUserByEmail(String user_email) {
        Map<String, String> emailmap = new HashMap<>();
        emailmap.put("e_mail", user_email);
        return findUser(emailmap);
    }

    @Override
    public List<UserInfo> findUsers(Map<String, List<String>> listMap) {
        return searchMapper.findUsers(listMap);
    }

    @Override
    public List<UserInfo> findUsersByIDs(List<Integer> user_ids) {
        List<String> userIDs = new ArrayList<>();
        for (Integer integer : user_ids) {
            userIDs.add(String.valueOf(integer));
        }
        Map<String, List<String>> idsmap = new HashMap<>();
        idsmap.put("user_ids", userIDs);
        return searchMapper.findUsers(idsmap);
    }

    @Override
    public List<UserInfo> findUserByName(String user_name) {
        return searchMapper.findUserByName(user_name);
    }

    @Override
    public List<UserInfo> findUserByMixture(String search_condition) {
        //全查询,哪个不为空就添加哪个
        List<UserInfo> all_userInfos = new ArrayList<>();
        UserInfo userInfo_by_email = findUserByEmail(search_condition);
        UserInfo userInfo_by_account = findUserByAccount(search_condition);
        List<UserInfo> userInfoList_by_name = findUserByName("%" + search_condition + "%");
        if (userInfo_by_email != null) {
            all_userInfos.add(userInfo_by_email);
        }
        if (userInfo_by_account != null) {
            all_userInfos.add(userInfo_by_account);
        }
        if (userInfoList_by_name != null) {
            all_userInfos.addAll(userInfoList_by_name);
        }
        return all_userInfos;
    }

    @Override
    public List<Post> findPostByPhotoDescribe(String photo_describe) {
        List<Post> allPostByPhotoDescribe = new ArrayList<>();
        for (Integer integer : searchMapper.findPhotoIDByPhotoDescribe(photo_describe)) {
            allPostByPhotoDescribe.add(findSpecificPostByPhotoID(integer));
        }
        return allPostByPhotoDescribe;
    }

    //和上面那个只是返回值不一样
    @Override
    public List<UserPost> findUserPostByPhotoDescribe(String photo_describe) {
        List<UserPost> allPostByPhotoDescribe = new ArrayList<>();
        for (Integer integer : searchMapper.findPhotoIDByPhotoDescribe(photo_describe)) {
            allPostByPhotoDescribe.add(findSpecificUserPostByPhotoID(integer));
        }
        return allPostByPhotoDescribe;
    }

    @Override
    public List<Post> findPostByHashTag(String hashtag_text) {
        List<Post> allPostByHashTag = new ArrayList<>();
        Map<String, List<Integer>> hashtagIDs = new HashMap<>();
        //将获取到的hashtag_text的list作为参数，去寻找photo_id，然后再封装成list，最后在根据这个去寻找具体的post
        hashtagIDs.put("hashtagIDs", searchMapper.findHashTagID(hashtag_text));
        for (Integer integer : searchMapper.findPhotoIDByHashTagID(hashtagIDs)) {
            allPostByHashTag.add(findSpecificPostByPhotoID(integer));
        }
        return allPostByHashTag;
    }

    @Override
    public List<UserPost> findUserPostByHashTag(String hashtag_text) {
        List<UserPost> allPostByHashTag = new ArrayList<>();
        Map<String, List<Integer>> hashtagIDs = new HashMap<>();
        //将获取到的hashtag_text的list作为参数，去寻找photo_id，然后再封装成list，最后在根据这个去寻找具体的post
        hashtagIDs.put("hashtagIDs", searchMapper.findHashTagID(hashtag_text));
        for (Integer integer : searchMapper.findPhotoIDByHashTagID(hashtagIDs)) {
            allPostByHashTag.add(findSpecificUserPostByPhotoID(integer));
        }
        return allPostByHashTag;
    }

    @Override
    public List<Integer> findHashTagID(String hashtag_text) {
        return searchMapper.findHashTagID(hashtag_text);
    }

    @Override
    public List<Integer> findPhotoIDByHashTagID(Map<String, List<Integer>> hashtagIDMap) {
        return searchMapper.findPhotoIDByHashTagID(hashtagIDMap);
    }


}
