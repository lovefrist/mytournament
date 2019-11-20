package com.example.firsttopic.Mycar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.List;
import java.util.Map;

public class RechargeAdapteer extends RecyclerView.Adapter<RechargeAdapteer.MyViewHoder> {
    private Context context;
    private List<Map> list;
    RechargeAdapteer(Context context, List<Map> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public RechargeAdapteer.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHoder(LayoutInflater.from(context).inflate(R.layout.layout_recharge,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        if (list.size()==0){
            holder.tvcarnumber.setText("目前");
            holder.tvcaramount.setText("暂无");
            holder.tvcartime.setText("充值记录");
        }else {
            holder.tvserialnum.setText(list.get(position).get("id").toString());
            holder.tvcarnumber.setText(list.get(position).get("carid").toString());
            holder.tvcaramount.setText(list.get(position).get("money").toString());
            holder.tvcartime.setText(list.get(position).get("timedata").toString());
        }
    }
    @Override
    public int getItemCount() {
        int num=0;
        if (list.size() == 0){
            num = list.size()+1;
        }else {
            num = list.size();
        }
        return num;
    }
    class MyViewHoder extends RecyclerView.ViewHolder {
         TextView tvserialnum,tvcarnumber,tvcaramount,tvcartime;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            tvserialnum = itemView.findViewById(R.id.tv_serialnum);
            tvcarnumber = itemView.findViewById(R.id.tv_carnumbernew);
            tvcaramount = itemView.findViewById(R.id.tv_caramount);
            tvcartime = itemView.findViewById(R.id.tv_cartime);
        }
    }
}
