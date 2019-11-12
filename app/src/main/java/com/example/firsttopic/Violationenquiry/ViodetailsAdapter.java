package com.example.firsttopic.Violationenquiry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.List;
import java.util.Map;

public class ViodetailsAdapter extends RecyclerView.Adapter<ViodetailsAdapter.MydetailsViewHoder> {
    private Context context;
    private List<Map<String,String>>  list1;
    private VioMyview myview;
    ViodetailsAdapter(Context context, List<Map<String,String>>  list1,VioMyview myview){
        this.context = context;
        this.list1 = list1;
        this.myview = myview;

    }
    @NonNull
    @Override
    public ViodetailsAdapter.MydetailsViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MydetailsViewHoder(LayoutInflater.from(context).inflate(R.layout.viodetaul_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MydetailsViewHoder holder, int position) {
        holder.tvdetailstime.setText(list1.get(position).get("pdatetime"));
        holder.tvregion.setText(list1.get(position).get("paddr"));
        holder.tvreason.setText(list1.get(position).get("premarks"));
        holder.tvviobranch.setText(list1.get(position).get("pscore"));
        holder.tvviomeny.setText(list1.get(position).get("pmoney"));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myview.onclick();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list1.size();
    }
   class   MydetailsViewHoder extends RecyclerView.ViewHolder {
       TextView tvdetailstime,tvhandling,tvregion,tvreason,tvviobranch,tvviomeny;
       LinearLayout linearLayout;
        public MydetailsViewHoder(@NonNull View itemView) {
           super(itemView);
            tvdetailstime = itemView.findViewById(R.id.tv_detailstime);
            tvhandling = itemView.findViewById(R.id.tv_handling);
            tvregion = itemView.findViewById(R.id.tv_region);
            tvreason = itemView.findViewById(R.id.tv_reason);
            tvviobranch = itemView.findViewById(R.id.tv_viodetailbranch);
            tvviomeny = itemView.findViewById(R.id.tv_viodetailmeny);
            linearLayout = itemView.findViewById(R.id.ll_xnlfsq);
       }
   }
}
