package com.bai.HolyIns.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bai.HolyIns.R;
import com.bai.HolyIns.adapter.SearchUserAdapter;
import com.bai.HolyIns.pojo.UserInfo;
import com.bai.HolyIns.service.SearchService;
import com.bai.HolyIns.state.ActivityStatus;
import com.bai.HolyIns.state.ThreadState;
import com.bai.HolyIns.utils.ToastUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchUserFragment extends Fragment {
    //widgets
    private ListView search_user_page_result_list;
    private EditText search_user_page_search_et;
    private Button search_user_page_search_btn;
    //adapter
    private SearchUserAdapter searchUserAdapter;
    //other
    private UserInfo now_user_info;
    //service
    private SearchService searchService = new SearchService();
    //param
    private static final String ARG_Now_UserInfo = "Now_User_Info";
    //handler
    private Handler handler = new Handler(msg -> {
        Bundle bundle = msg.getData();
        if (msg.what == ThreadState.FIND_ALL_USER) {
            String search_result = bundle.getString("search_user_status");
            if (search_result.equals(ActivityStatus.STRING_FIND_ALL_USER_FAILED)) {
                ToastUtils.show_ordinary_toast(getContext(), "用户搜索出错", Toast.LENGTH_SHORT);
            } else {
                List<UserInfo> search_userInfos = JSON.parseArray(search_result, UserInfo.class);
                searchUserAdapter = new SearchUserAdapter(search_userInfos, getContext(), now_user_info);
                search_user_page_result_list.setAdapter(searchUserAdapter);
            }
        }
        return true;
    });

    public SearchUserFragment() {
        // Required empty public constructor
    }

    public static SearchUserFragment newInstance(UserInfo now_userInfo) {
        SearchUserFragment fragment = new SearchUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_Now_UserInfo, now_userInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        search_user_page_search_btn.setOnClickListener(v -> {
            String search_condition = search_user_page_search_et.getText().toString();
            if (search_condition.length() != 0) {
                searchService.searchUser(search_condition, handler);
            } else {
                ToastUtils.show_ordinary_toast(getContext(), "请输入搜索条件", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            now_user_info = (UserInfo) getArguments().getSerializable(ARG_Now_UserInfo);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        search_user_page_result_list = view.findViewById(R.id.search_user_page_list);
        search_user_page_search_et = view.findViewById(R.id.search_user_page_search_et);
        search_user_page_search_btn = view.findViewById(R.id.search_user_page_search_btn);
        return view;
    }
}