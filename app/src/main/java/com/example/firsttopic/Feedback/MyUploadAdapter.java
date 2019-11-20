package com.example.firsttopic.Feedback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.List;
import java.util.Map;

public class MyUploadAdapter extends RecyclerView.Adapter<MyUploadAdapter.MyViewHoder> {
    private Context context;
    private  List<Map> list;
    MyUploadAdapter(Context context, List<Map> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyUploadAdapter.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHoder(LayoutInflater.from(context).inflate(R.layout.upload_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        holder.tvtitleupload.setText(list.get(position).get("title").toString());
        holder.tvtimeupload.setText(list.get(position).get("time").toString());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
   class   MyViewHoder extends RecyclerView.ViewHolder {
         TextView  tvtitleupload,tvtimeupload,tvstate,tvreplycontent,tvreplytime;
       public MyViewHoder(@NonNull View itemView) {
           super(itemView);
           tvtitleupload = itemView.findViewById(R.id.tv_titleupload);
           tvtimeupload = itemView.findViewById(R.id.tv_timeupload);
           tvstate = itemView.findViewById(R.id.tv_state);
           tvreplycontent = itemView.findViewById(R.id.tv_replycontent);
           tvreplytime = itemView.findViewById(R.id.tv_replytime);
       }
   }
}
