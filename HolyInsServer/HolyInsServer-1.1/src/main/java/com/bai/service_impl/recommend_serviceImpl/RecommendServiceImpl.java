package com.bai.service_impl.recommend_serviceImpl;

import com.bai.pojo.UserPost;
import com.bai.service.recommend_service.RecommendService;
import com.bai.service.search_service.SearchService;
import com.bai.service_impl.base_serviceImpl.BaseServiceImpl;
import com.hankcs.hanlp.HanLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RecommendServiceImpl extends BaseServiceImpl implements RecommendService {

    private SearchService searchService;

    @Autowired
    @Qualifier("searchServiceImpl")
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    /*
     * @Description:
     * @Param: [now_user_id]
     * @Return: java.util.List<com.bai.pojo.UserPost>
     * @Author: baitianyu
     * @Date: 2021/5/6 22:02
     **/

    public List<UserPost> getRecommendPost(int now_user_id) {
        //这个用户所有照片的文案
        List<String> allThisUserPhotoDescriptionKeyWords = new ArrayList<>();
        List<UserPost> allThisUserPost = findAllUserPostsByUserID(now_user_id);
        HashSet<UserPost> allRecommendPostSet = new HashSet<>();
        //根据该用户的photo_description去查找关键字，然后匹配
        for (UserPost userPost : allThisUserPost) {
            allThisUserPhotoDescriptionKeyWords.addAll(HanLP.extractKeyword(userPost.getPhoto().getPhoto_description(), 1));
        }
        for (String allThisUserPhotoDescriptionKeyWord : allThisUserPhotoDescriptionKeyWords) {
            allRecommendPostSet.addAll(searchService.findUserPostByPhotoDescribe("%" + allThisUserPhotoDescriptionKeyWord + "%"));
            //allRecommendPost.addAll(searchService.findUserPostByPhotoDescribe("%" + allThisUserPhotoDescriptionKeyWord + "%"));
        }
        /*for (UserPost userPost : allRecommendPost) {
            System.err.println(userPost.getPhoto().getPhoto_id() + " " + userPost.getPhoto().getPhoto_description());
        }*/
        //System.err.println("size="+allRecommendPost.size());
        /*for (UserPost userPost : allRecommendPostSet) {
            System.err.println(userPost);
        }*/
        List<UserPost> allRecommendPost = new ArrayList<>(allRecommendPostSet);
        return allRecommendPost;
    }
}
