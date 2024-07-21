package com.bai.HolyIns.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bai.HolyIns.R;
import com.bai.HolyIns.pojo.UserInfo;

import java.util.ArrayList;


public class PersonInfoDetailsFragment extends Fragment {
    //widgets
    private ListView person_info_details_list_view;
    //other
    private UserInfo mPersonInfoInDetail;
    private static final String ARG_PersonInfoInDetail="PersonInfoInDetail";

    public PersonInfoDetailsFragment() {
        // Required empty public constructor
    }

    public static PersonInfoDetailsFragment newInstance(UserInfo PersonInfoInDetail) {
        PersonInfoDetailsFragment fragment = new PersonInfoDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PersonInfoInDetail, PersonInfoInDetail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String data[]= {
                "用户名:"+mPersonInfoInDetail.getName()
                ,"性别:"+mPersonInfoInDetail.getSex()
                ,"账号:"+mPersonInfoInDetail.getAccount()
                ,"E-mail:"+mPersonInfoInDetail.getE_mail()
                ,"注册时间:"+mPersonInfoInDetail.getRegisterTime()
        };
        person_info_details_list_view.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_person_info_in_details,R.id.person_info_details_item_tv,data));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPersonInfoInDetail= (UserInfo) getArguments().getSerializable(ARG_PersonInfoInDetail);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person_info_details, container, false);
        person_info_details_list_view=view.findViewById(R.id.person_info_details_list);
        return view;
    }
}