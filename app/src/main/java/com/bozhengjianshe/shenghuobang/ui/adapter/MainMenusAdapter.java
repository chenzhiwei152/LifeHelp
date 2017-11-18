package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.ui.bean.MainMenuInfo;

import java.util.ArrayList;

/**
 * Created by liu.zhenrong on 2016/6/21.
 */
public class MainMenusAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MainMenuInfo> menus;
    private int currentPosition = -1;

    public MainMenusAdapter(Context context, ArrayList<MainMenuInfo> menus) {
        this.context = context;
        this.menus = menus;
    }

    @Override
    public int getCount() {
        return menus!=null?menus.size():0;
    }

    @Override
    public Object getItem(int position) {
        return menus!=null?menus.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.jm_item_mainmenu,null);
            viewHolder.iv_menu_icon = (ImageView) convertView.findViewById(R.id.iv_menu_icon);
            viewHolder.tv_menu_name = (TextView) convertView.findViewById(R.id.tv_menu_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MainMenuInfo menuInfo = (MainMenuInfo) getItem(position);
        if(menuInfo!=null){
            viewHolder.iv_menu_icon.setBackgroundResource(menuInfo.iconID);
            viewHolder.tv_menu_name.setText(menuInfo.menuName);
        }
        if (currentPosition < position) {
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(convertView, "scaleX", 0, 1);
            //animatorX.setInterpolator(new AnticipateOvershootInterpolator());
            animatorX.setDuration(1000+currentPosition*200).start();


            ObjectAnimator animatorY = ObjectAnimator.ofFloat(convertView, "scaleY", 0, 1);
            //animatorY.setInterpolator(new AnticipateOvershootInterpolator());
            animatorY.setDuration(1000+currentPosition*200).start();

            currentPosition = position;
        }
        return convertView;
    }

    private class ViewHolder{
        public ImageView iv_menu_icon;
        public TextView tv_menu_name;
    }
}
