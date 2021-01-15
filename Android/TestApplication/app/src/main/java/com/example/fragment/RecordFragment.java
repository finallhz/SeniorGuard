package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.testapplication.BasicChartActivity;
import com.example.testapplication.HeadIconUtilsActivity;
import com.example.testapplication.HeartbeatChartActivity;
import com.example.testapplication.LocateMapActivity;
import com.example.testapplication.R;
import com.example.testapplication.RecommenderActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordFragment extends Fragment {
    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private Context mContext;
    private ImageView IndexChooseImg;
    List<Integer> images=new ArrayList<Integer>();

    public RecordFragment() {
        // Required empty public constructor
    }

    public static RecordFragment newInstance(String param1) {
        RecordFragment fragment = new RecordFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_myrecord, container, false);
        mContext = this.getActivity();
        //指定按钮的点击事件
        //第一个 基本数据点击事件
        ImageView ivv1 = (ImageView)rootView.findViewById(R.id.ivv1);
        TextView ivv11 = (TextView)rootView.findViewById(R.id.ivv11);
        ivv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), BasicChartActivity.class);
                startActivity(it);
            }
        });
        ivv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), BasicChartActivity.class);
                startActivity(it);
            }
        });
        //第二个 心跳数据点击事件
        ImageView ivv2= (ImageView)rootView.findViewById(R.id.ivv2);
        TextView ivv22 = (TextView)rootView.findViewById(R.id.ivv22);
        ivv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), HeartbeatChartActivity.class);
                startActivity(it);
            }
        });
        ivv22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), HeartbeatChartActivity.class);
                startActivity(it);
            }
        });

        //第三个 定位数据点击事件
        ImageView ivv3 = (ImageView)rootView.findViewById(R.id.ivv3);
        TextView ivv33 = (TextView)rootView.findViewById(R.id.ivv33);
        ivv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), LocateMapActivity.class);
                startActivity(it);
            }
        });
        ivv33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), LocateMapActivity.class);
                startActivity(it);
            }
        });


        //第四个 饮食推荐点击事件
        ImageView ivv4 = (ImageView)rootView.findViewById(R.id.ivv4);
        TextView ivv44 = (TextView)rootView.findViewById(R.id.ivv44);
        ivv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), RecommenderActivity.class);
                startActivity(it);
            }
        });
        ivv44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), RecommenderActivity.class);
                startActivity(it);
            }
        });

        //根据指定的引用布局加载
        return rootView;
    }


}
