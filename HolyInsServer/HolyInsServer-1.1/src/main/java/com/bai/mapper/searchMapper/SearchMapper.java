package com.bai.mapper.searchMapper;

import com.bai.pojo.Post;
import com.bai.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
    /*
   有各种情况，比如传入id，传入account，传入email，传入啥就去找啥，还有他们组成的list也一样,传入id list就去找所有在这个list里的user
    */
    //查询某个user
    UserInfo findUser(Map<String, String> map);

    //查询好多user,account集合、email集合、id集合，不过目前好像没啥需求，先写上吧
    List<UserInfo> findUsers(Map<String, List<String>> listMap);

    //根据用户名模糊查询
    List<UserInfo> findUserByName(@Param("name") String username);

    //根据photo_description模糊查询,得到photo_id然后再去业务层findPost(int photo_id)
    List<Integer> findPhotoIDByPhotoDescribe(@Param("photo_description")String photo_description);

    //通过hashtag_text去寻找hashtag_id，然后根据id去找photo_id
    List<Integer> findHashTagID(@Param("hashtag_text") String hashtag_text);
    List<Integer> findPhotoIDByHashTagID(Map<String,List<Integer>> hashtagIDMap);
}
