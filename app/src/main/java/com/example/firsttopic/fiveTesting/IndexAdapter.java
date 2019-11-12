package com.example.firsttopic.fiveTesting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;
import com.example.firsttopic.sixgettop.ChatActivity;

import java.util.List;

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.LinerHoder> {
    private Context mcontext;
    private List<String> texttitlelist;
   private  List<String> applistdet;
   public IndexAdapter(Context context, List<String> list, List<String> applist){
       mcontext = context;
       texttitlelist = list;
       applistdet = applist;
    }
    @NonNull
    @Override
    public IndexAdapter.LinerHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinerHoder(LayoutInflater.from(mcontext).inflate(R.layout.indexadapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinerHoder holder, int position) {
        holder.titletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"触发点击事件",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext, ChatActivity.class);
                intent.putExtra(texttitlelist.get(position),position);
                mcontext.startActivity(intent);
            }
        });
        if (position == 5){
            holder.titletext.setText(texttitlelist.get(position));
            holder.numbertext.setText(applistdet.get(position));
            if (Integer.parseInt(applistdet.get(position))>=4){
//                holder.relativeLayout.setBackgroundColor(Color.rgb(255,0,0));background
                holder.relativeLayout.setBackgroundResource(R.drawable.boxborderred);

            }
        }else {

            holder.titletext.setText(texttitlelist.get(position));
            holder.numbertext.setText(applistdet.get(position));
        }

    }


    @Override
    public int getItemCount() {
        return texttitlelist.size();
    }
   class LinerHoder extends RecyclerView.ViewHolder {
private TextView titletext,numbertext;
private RelativeLayout relativeLayout;
       public LinerHoder(@NonNull View itemView) {
           super(itemView);
           titletext = itemView.findViewById(R.id.tv_indextext);
           numbertext = itemView.findViewById(R.id.tv_number);
           relativeLayout = itemView.findViewById(R.id.ll_color);
       }
   }
}
