package com.example.firsttopic.highstatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

public class HighsTatusAdapter extends RecyclerView.Adapter<HighsTatusAdapter.MyViewHoder> {
    Context context;
    HighsTatusAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public HighsTatusAdapter.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHoder(LayoutInflater.from(context).inflate(R.layout.layout_tatus,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
     class MyViewHoder extends RecyclerView.ViewHolder {
        private TextView tvdetailed,tvhigtname,tvpresent;
        private ImageView iminteng;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            tvdetailed = itemView.findViewById(R.id.tv_detailed);
            tvhigtname = itemView.findViewById(R.id.tv_higtname);
            tvpresent = itemView.findViewById(R.id.tv_present);
            iminteng = itemView.findViewById(R.id.im_inteng);

        }
    }
}
