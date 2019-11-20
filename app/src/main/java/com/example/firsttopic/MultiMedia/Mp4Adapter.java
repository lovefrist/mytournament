package com.example.firsttopic.MultiMedia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.List;

public class Mp4Adapter extends RecyclerView.Adapter<Mp4Adapter.ViewHolder> {
    private Context mcontext;
    private List<String> Textlist;
    private List<Integer> Intlist;

    Mp4Adapter(Context context, List<String> list,List<Integer> inylist) {
        mcontext = context;
        Textlist = list;
        Intlist = inylist;
    }

    @NonNull

    @Override
    public Mp4Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.layout_mp4adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageURI(Uri.parse("android.resource://com.example.firsttopic/" + R.drawable.ouw_gar));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Mp4oneActivity.class);
                intent.putExtra("Url",Intlist.get(position));
                intent.putExtra("title",Textlist.get(position));
                mcontext.startActivity(intent);
            }
        });
        holder.textView.setText(Textlist.get(position));

    }


    @Override
    public int getItemCount() {
        return Textlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_rvimgmp4);
            textView = itemView.findViewById(R.id.tv_rvmp4);
        }
    }
}
