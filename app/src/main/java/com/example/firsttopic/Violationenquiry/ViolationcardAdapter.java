package com.example.firsttopic.Violationenquiry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.List;
import java.util.Map;

public class ViolationcardAdapter extends RecyclerView.Adapter<ViolationcardAdapter.MyViolatHoder> {
    private Context context;
    private List<Map<String, String>> list;
    private Vioinf vioinf;
    ViolationcardAdapter(Context context, List<Map<String, String>> list,Vioinf vioinf) {
        this.context = context;
        this.list = list;
        this.vioinf = vioinf;
    }

    @NonNull
    @Override
    public ViolationcardAdapter.MyViolatHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViolatHoder(LayoutInflater.from(context).inflate(R.layout.violation_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViolatHoder holder, int position) {
        holder.tvcaridadap.setText(list.get(position).get("carnumber"));
        holder.tvfrequency.setText(list.get(position).get("number"));
        holder.tvviobranch.setText(list.get(position).get("pscore"));
        holder.tvviomeny.setText(list.get(position).get("pmoney"));
        vioinf.getLayoutonView(holder.lacard);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vioinf.getImageonClick(list.get(position).get("carnumber"),position);
            }
        });
        holder.lacard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vioinf.getLayoutonClick(list.get(position).get("carnumber"),position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViolatHoder extends RecyclerView.ViewHolder {
        TextView tvcaridadap, tvfrequency, tvviobranch, tvviomeny;
        ImageView imageView;
        LinearLayout lacard;

        public MyViolatHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_minus);
            tvcaridadap = itemView.findViewById(R.id.tv_caridadap);
            tvfrequency = itemView.findViewById(R.id.tv_frequency);
            tvviobranch = itemView.findViewById(R.id.tv_viobranch);
            tvviomeny = itemView.findViewById(R.id.tv_viomeny);
            lacard = itemView.findViewById(R.id.la_card);
        }
    }
}
