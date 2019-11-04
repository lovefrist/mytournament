package com.example.firsttopic.transportquery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.firsttopic.R;

import java.util.List;
import java.util.Map;

public class XendlistAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Map>[] lists;
    XendlistAdapter(Context context , List<Map>[] lists) {
        this.context = context;
        this.lists =lists;
        Log.d("Xend", "XendlistAdapter: "+lists[0]);
    }

    public String[] groupStrings = {"中医院站", "联想大厦站"};

    //获取分组的个数
    @Override
    public int getGroupCount() {
        return groupStrings.length;
    }

    //
    @Override
    public int getChildrenCount(int groupPosition) {
        return lists[groupPosition].size();
    }

    //获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupStrings[groupPosition];
    }

    //   获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return lists[groupPosition].get(childPosition);
    }

    //       获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //      获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //     获取显示指定分组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.parent_layout, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tvTitle.setText(groupStrings[groupPosition]);
        return convertView;
    }

    //获取显示指定分组中的指定子选项的视图
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chind_layout, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvuantity = convertView.findViewById(R.id.tv_uantity);
            childViewHolder.tvpople = convertView.findViewById(R.id.tv_pople);
            childViewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            childViewHolder.tvdistance = convertView.findViewById(R.id.tv_distance);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tvuantity.setText(lists[groupPosition].get(childPosition).get("车牌号").toString());
        childViewHolder.tvdistance.setText(lists[groupPosition].get(childPosition).get("距离").toString()+"米");
        childViewHolder.tv_time.setText(lists[groupPosition].get(childPosition).get("时间").toString());
        childViewHolder.tvpople.setText(lists[groupPosition].get(childPosition).get("容量").toString());
        return convertView;
    }

    //指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
    }

    static class ChildViewHolder {
        TextView tvuantity, tvpople, tv_time, tvdistance;
    }

}
