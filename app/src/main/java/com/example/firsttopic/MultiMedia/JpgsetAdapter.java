package com.example.firsttopic.MultiMedia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.ArrayList;

public class JpgsetAdapter extends RecyclerView.Adapter<JpgsetAdapter.LinerHodler> {
    private Context mcontext;
    private ArrayList<Integer> arrayList;
    JpgsetAdapter(Context context, ArrayList<Integer> list){
        arrayList = list;
        mcontext = context;
    }

    @NonNull
    @Override
    public JpgsetAdapter.LinerHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinerHodler(LayoutInflater.from(mcontext).inflate(R.layout.jpg_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinerHodler holder, int position) {
        holder.mimageView.setImageResource(arrayList.get(position));
        holder.mimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,JpgreadActivity.class);
                intent.putExtra("imgUrl",arrayList.get(position));
                mcontext.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class LinerHodler extends  RecyclerView.ViewHolder{
private ImageView mimageView;
        public LinerHodler(@NonNull View itemView) {
            super(itemView);
            mimageView = itemView.findViewById(R.id.iv_Violation);
        }
    }
}
