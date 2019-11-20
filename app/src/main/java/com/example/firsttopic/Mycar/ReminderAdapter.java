package com.example.firsttopic.Mycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHoder> {
    private Context context;
    private List<Integer> list;
    private MyOnclick myOnclick;

    ReminderAdapter(Context context, List<Integer> list,MyOnclick myOnclick) {
        this.context = context;
        this.list = list;
        this.myOnclick = myOnclick;
    }

    @NonNull
    @Override
    public ReminderAdapter.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHoder(LayoutInflater.from(context).inflate(R.layout.layout_carreminder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
//        boxborderred
        holder.numbertext.setText(list.get(position).toString());
        holder.titletext.setText((position+1)+"号小车");
        if (list.get(position)<100){
            holder.tvcontent.setText("警告");
            holder.relativeLayout.setBackgroundResource(R.drawable.boxborderred);
        }else {
            holder.tvcontent.setText("正常");
            holder.relativeLayout.setBackgroundResource(R.drawable.boxborderwai);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnclick.onCliick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        private TextView titletext, numbertext, tvcontent;
        private RelativeLayout relativeLayout;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            titletext = itemView.findViewById(R.id.tv_carreminname);
            numbertext = itemView.findViewById(R.id.tv_carmoney);
            tvcontent = itemView.findViewById(R.id.tv_carcontent);
            relativeLayout = itemView.findViewById(R.id.ll_carcoler);
        }
    }

}
