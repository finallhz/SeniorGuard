package com.example.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.ContactBean;
import com.example.pojo.NewsBean;
import com.example.testapplication.ContactActivity;
import com.example.testapplication.R;
import com.example.utils.ContactUtils;
import com.example.utils.UserDataManager;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    private ArrayList<ContactBean> list;
    private Context context;
    private String userid;
    private int index2;
    //通过构造方法接受要显示的新闻数据集合
    public ContactAdapter(Context context, ArrayList<ContactBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        //1.复用converView优化listview,创建一个view作为getview的返回值用来显示一个条目
        if(convertView != null){
            view = convertView;
        }else {
            //方法一：推荐
            //context:上下文, resource:要转换成view对象的layout的id, root:将layout用root(ViewGroup)包一层作为codify的返回值,一般传null
            view = View.inflate(context, R.layout.item_contact_layout, null);//将一个布局文件转换成一个view对象

            //方法二
            //通过LayoutInflater将布局转换成view对象
            //view =  LayoutInflater.from(context).inflate(R.layout.item_news_layout, null);

            //方法三：系统级开发
            //通过context获取系统服务得到一个LayoutInflater，通过LayoutInflater将一个布局转换为view对象
            //LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view = layoutInflater.inflate(R.layout.item_news_layout, null);

        }
        //2.获取view上的子控件对象
        TextView name = (TextView) view.findViewById(R.id.item_tv_des);
        TextView num = (TextView) view.findViewById(R.id.item_tv_title);
        TextView email = (TextView) view.findViewById(R.id.item_tv_des1);
        Button bt1 = (Button)view.findViewById(R.id.login_btn_login);
        //3.获取postion位置条目对应的list集合中的新闻数据，Bean对象
        ContactBean contactBean = list.get(position);
        //4.将数据设置给这些子控件做显示
        name.setText(contactBean.name);
        num.setText(contactBean.num);
        email.setText(contactBean.email);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String o =  UserDataManager.delContacts(userid,contactBean.cid);
               if(o!=null&&!o.equals(""))
               {
                   Toast.makeText(context.getApplicationContext(), "删除成功！",Toast.LENGTH_LONG).show();
                   //removeContactToList(index2);
                   //这功能有问题，暂时不从列表移除，而是改为销毁联系人Activity强制刷新
               }
            }
        });
        return view;
    }
    public void setUserid(String id){
        userid = id;
    }
    public void getIndex(int index)
    {
        index2 = index;
    }
    public void removeContactToList(int index)
    {
        ContactUtils.removeContactToList(index);
    }
}
