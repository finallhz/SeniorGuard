package com.example.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.testapplication.ContactActivity;
import com.example.testapplication.HeadIconUtilsActivity;
import com.example.testapplication.LoginActivity;
import com.example.testapplication.MyDataActivity;
import com.example.testapplication.MySignActivity;
import com.example.testapplication.R;
import com.example.utils.GlideImageLoader;
import com.github.mikephil.charting.data.LineRadarDataSet;
import com.youth.banner.Banner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserDetailFragment extends Fragment   {
    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private Context mContext;
    private ImageView IndexChooseImg;
    private TextView userLogin;
    private LinearLayout sign;
    private LinearLayout mydata;
    private LinearLayout message;
    private LinearLayout people;
    private LinearLayout other;
    private SharedPreferences login_sp;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    List<Integer> images=new ArrayList<Integer>();

    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance(String param1) {
        UserDetailFragment fragment = new UserDetailFragment();
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
    //权限检测代码
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //这句代码是加载指定的View布局
        verifyStoragePermissions(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_userdetail, container, false);
        mContext = this.getActivity();
        //根据指定的引用布局加载
        sign = (LinearLayout) rootView.findViewById(R.id.ll1);
        mydata = (LinearLayout) rootView.findViewById(R.id.ll2);
        message = (LinearLayout) rootView.findViewById(R.id.ll3);
        people = (LinearLayout) rootView.findViewById(R.id.ll4);
        other = (LinearLayout) rootView.findViewById(R.id.ll5);
        IndexChooseImg = (ImageView) rootView.findViewById(R.id.ivhead);
        Log.i("设定头像","1");
        IndexChooseImg.setImageResource(R.drawable.user_icon);
        IndexChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1=  new Intent(getActivity(), HeadIconUtilsActivity.class);
                startActivity(int1);
            }
        });
        //需要增加已经登录和注销判断
        userLogin = (TextView) rootView.findViewById(R.id.userlogin);
        //如果已经登录了，就从SharedPreferences中拿取数据
        login_sp= getContext().getSharedPreferences("userInfo", 0);
        String name=login_sp.getString("USER_NAME", "请您登录");
        userLogin.setText(name);
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("测试","1111");
                Intent int1=  new Intent(getActivity(), LoginActivity.class);
                startActivity(int1);
            }
        });
        //个性签名页面
        sign.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View v) {
                Intent newin = new Intent(getActivity(), MySignActivity.class);
                startActivity(newin);
            }
        });
        //我的资料界面
        mydata.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View v) {
                Intent newin = new Intent(getActivity(), MyDataActivity.class);
                startActivity(newin);
            }
        });
        //紧急联系人界面
        people.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View v) {
                Intent newin = new Intent(getActivity(), ContactActivity.class);
                startActivity(newin);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        String mFile = Environment.getExternalStorageDirectory() + "/" +"wode/"+ "outtemp.png";
        if(fileIsExists(mFile)==true)
        {
            Log.i("图片存在","1");
            Bitmap photo = BitmapFactory.decodeFile(mFile);
            IndexChooseImg.setImageBitmap(photo);
        }else
        {
            Log.i("图片不存在","1");
            IndexChooseImg.setImageResource(R.drawable.user_icon);
        }
        super.onResume();
    }

    public boolean fileIsExists(String strFile) {
        try {
            File f=new File(strFile);
            if(f.exists()) {
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
