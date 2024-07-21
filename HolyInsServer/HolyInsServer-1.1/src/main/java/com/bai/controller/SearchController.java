package com.bai.controller;

import com.alibaba.fastjson.JSON;
import com.bai.pojo.UserInfo;
import com.bai.service.search_service.SearchService;
import com.bai.state.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    @Autowired
    @Qualifier("searchServiceImpl")
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/findUserByID")
    public String findUserByID(@RequestParam("user_id") String user_id) {
        return JSON.toJSONString(searchService.findUserByID(user_id));
    }

    @PostMapping("/findUsersByIDs")
    public String findUsersByIDs(@RequestParam("user_ids") String JSON_User_ids) {
        List<Integer> temp = JSON.parseArray(JSON_User_ids, Integer.class);
        return JSON.toJSONString(searchService.findUsersByIDs(temp));
    }

    @PostMapping("/findUserByAccount")
    public String findUserByAccount(@RequestParam("user_account") String user_account) {
        return JSON.toJSONString(searchService.findUserByAccount(user_account));
    }

    @PostMapping("/findUserByEmail")
    public String findUserByEmail(@RequestParam("user_email") String user_email) {
        return JSON.toJSONString(searchService.findUserByEmail(user_email));
    }

    @PostMapping("/findUserByName")
    public String findUserByName(@RequestParam("user_name") String user_name) {
        return JSON.toJSONString(searchService.findUserByName("%" + user_name + "%"));
    }

    @PostMapping("/findAllUser")
    public String findAllUser(@RequestParam("search_condition") String search_condition) {
        //全查询,哪个不为空就添加哪个
        String JSON_RETURN = JSON.toJSONString(searchService.findUserByMixture(search_condition));
        if (JSON_RETURN.equals("[]")) {
            return ActivityStatus.STRING_FIND_ALL_USER_FAILED;
        } else {
            return JSON_RETURN;
        }
    }

    @PostMapping("/findPostByPhotoDescribe")
    public String findPostByPhotoDescribe(@RequestParam("photo_description") String photo_description) {
        String JSON_RETURN = JSON.toJSONString(searchService.findUserPostByPhotoDescribe("%" + photo_description + "%"));
        if (JSON_RETURN.equals("[]"))
            return ActivityStatus.STRING_FIND_POST_BY_PHOTO_DESCRIBE_FAILED;
        else
            return JSON_RETURN;
    }

    @PostMapping("/findPostByHashTag")
    public String findPostByHashTag(@RequestParam("hashtag_text") String hashtag_text) {
        String JSON_RETURN = JSON.toJSONString(searchService.findPostByHashTag("%" + hashtag_text + "%"));
        if (JSON_RETURN.equals("[]"))
            return ActivityStatus.STRING_FIND_POST_BY_HASH_TAG_FAILED;
        else
            return JSON_RETURN;
    }

}
