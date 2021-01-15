package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.example.pojo.FoodBean;
import com.example.pojo.NewsBean;
import com.example.testapplication.FoodDetailActivity;
import com.example.testapplication.FoodRecommendActivity;
import com.example.testapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//用于加载食品数据的Adapter
public class FoodAdapter extends BaseAdapter {
    MyViewHolder holder;
    private ArrayList<FoodBean> list;
    private Context context;
    public List<View> viewlist=new ArrayList<View>();
    //通过构造方法接受要显示的新闻数据集合
    public FoodAdapter(Context context, ArrayList<FoodBean> list) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index = position;
        if(convertView==null){
            convertView=View.inflate(context,R.layout.item_food_layout,null);
            holder=new MyViewHolder();
            holder.mImageView= (ImageView) convertView.findViewById(R.id.item_img_icon);
            holder.mTextView= (TextView) convertView.findViewById(R.id.item_tv_title);
            holder.mCheckBox= (CheckBox) convertView.findViewById(R.id.cb);
            convertView.setTag(holder);
        }else {
            holder= (MyViewHolder) convertView.getTag();
        }


        //3.获取postion位置条目对应的list集合中的新闻数据，Bean对象
        FoodBean newsBean = list.get(position);
        //4.将数据设置给这些子控件做显示
        holder.mImageView.setImageDrawable(newsBean.icon);//设置imageView的图片
        holder.mTextView.setText(newsBean.name);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("1111","点击！");
                //跳转,这里功能还暂未实现
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra("id",list.get(position).id);
                context.startActivity(intent);
            }
        });
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("list","触发");

                if(isChecked==true)
                {
                    list.get(index).type1 = 1;
                    Log.i("list","触发真"+position);
                    //当前位置的id
                    int srcid = list.get(position).id;
                    FoodRecommendActivity.checkList.add(srcid);
                }else
                {
                    list.get(index).type1 = 0;
                    Log.i("list","触发假"+position);
                    FoodRecommendActivity.checkList.remove(list.get(position).id);
                }

            }
        });
        //因为ListView存在复用问题，所以记录CheckBox的状态
        if(list.get(position).type1 == 1){
            holder.mCheckBox.setChecked(true);
        }else {
            holder.mCheckBox.setChecked(false);
        }

        return convertView;
    }

    public static class  MyViewHolder {
        ImageView mImageView;
        TextView mTextView;
        CheckBox mCheckBox;
    }
}
