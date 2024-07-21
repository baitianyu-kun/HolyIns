package com.bai.HolyIns.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.adapter.PagePostAdapter;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.pojo.UserPost;
import com.bai.HolyIns.service.SearchService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.ToastUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPostFragment extends Fragment {
    //widgets
    private ListView search_post_page_result_list;
    private EditText search_post_page_search_et;
    private Button search_post_page_search_btn;
    //adapter
    private PagePostAdapter pagePostAdapter;
    //other
    private UserInfo now_user_info;
    //service
    private SearchService searchService=new SearchService();
    //handler
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            if (msg.what == ThreadState.FIND_POST_BY_PHOTO_DESCRIPTION) {
                String search_result=bundle.getString("searchPostByPhotoDescription_status");
                if (search_result.equals(ActivityStatus.STRING_FIND_POST_BY_PHOTO_DESCRIBE_FAILED)){
                    ToastUtils.show_ordinary_toast(getContext(),"帖子搜索出错",Toast.LENGTH_SHORT);
                }else {
                    List<UserPost> search_posts= JSON.parseArray(search_result,UserPost.class);
                    pagePostAdapter=new PagePostAdapter(search_posts,getContext(),now_user_info);
                    search_post_page_result_list.setAdapter(pagePostAdapter);
                }
            }
            return true;
        }
    });
    //param
    private static final String ARG_Now_UserInfo = "Now_User_Info";


    public SearchPostFragment() {
        // Required empty public constructor
    }

    public static SearchPostFragment newInstance(UserInfo now_userInfo) {
        SearchPostFragment fragment = new SearchPostFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_Now_UserInfo, now_userInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            now_user_info = (UserInfo) getArguments().getSerializable(ARG_Now_UserInfo);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        search_post_page_search_btn.setOnClickListener(v -> {
            String photo_description = search_post_page_search_et.getText().toString();
            if (photo_description.length() != 0) {
                searchService.searchPostByPhotoDescription(photo_description, handler);
            } else {
                ToastUtils.show_ordinary_toast(getContext(), "请输入搜索条件", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_post, container, false);
        search_post_page_result_list = view.findViewById(R.id.search_post_page_list);
        search_post_page_search_et = view.findViewById(R.id.search_post_page_search_et);
        search_post_page_search_btn = view.findViewById(R.id.search_post_page_search_btn);
        return view;
    }
}