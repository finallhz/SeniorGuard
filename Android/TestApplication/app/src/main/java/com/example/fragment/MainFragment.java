package com.example.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.adapter.NewsAdapter;
import com.example.pojo.NewsBean;
import com.example.testapplication.R;
import com.example.utils.GlideImageLoader;
import com.example.utils.NewsUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

//底部导航栏相关碎片
//社区导航栏相关碎片
public class MainFragment extends Fragment  {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private  Context mContext;
    List<Integer> images=new ArrayList<Integer>();
    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CommunityFragment.
     */
    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //这句代码是加载指定的View布局
        View rootView = inflater.inflate(R.layout.fragment_mainpage, container, false);
        mContext = this.getActivity();
        //根据指定的引用布局加载
        Banner banner = (Banner) rootView.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        images.add(R.drawable.s1);
        images.add(R.drawable.s2);
        images.add(R.drawable.s3);
        banner.setImages(images);
        banner.start();
        TextView tv1 = (TextView)rootView.findViewById(R.id.tv1);
        tv1.setText(R.string.sgname);
        TextView tv2 = (TextView)rootView.findViewById(R.id.tv2);
        tv2.setText(R.string.sgdetail);
        return rootView;
    }


}
