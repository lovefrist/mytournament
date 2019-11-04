package com.example.firsttopic.transportquery;

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

public class RecyclerDlongAdapter extends RecyclerView.Adapter<RecyclerDlongAdapter.MyViewHodir> {
    private Context context;
    private List<Map<String,Integer>> list;
    private int numberpop = 0;
    RecyclerDlongAdapter(Context context, List<Map<String,Integer>> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public RecyclerDlongAdapter.MyViewHodir onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHodir(LayoutInflater.from(context).inflate(R.layout.layou_dlong_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodir holder, int position) {
//        if (position != 0){
//            numberpop +=list.get(position-1).get("pople");
//        }

        if (position == 0){
            holder.tvserial.setText("序号");
            holder.tvbusnum.setText("公交车编号");
            holder.tpople.setText("承载人数");
        }
        else if (position+1 == list.size()+1){
            holder.tvserial.setVisibility(View.GONE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2.0f);
            holder.tvbusnum.setLayoutParams(lp);
            holder.tvbusnum.setText("总计");
            holder.tpople.setText(numberpop+"");
        }
        else {
            holder.tvserial.setText(list.get(position-1).get("serial")+"");
            holder.tvbusnum.setText(list.get(position-1).get("busnum")+"");
            holder.tpople.setText(list.get(position-1).get("pople")+"");
            numberpop +=list.get(position-1).get("pople");
        }

    }



    @Override
    public int getItemCount() {
        return list.size()+1;
    }
    class MyViewHodir extends RecyclerView.ViewHolder {
        TextView tvserial,tvbusnum,tpople;
        public MyViewHodir(@NonNull View itemView) {
            super(itemView);
            tvserial = itemView.findViewById(R.id.tv_serial);
            tvbusnum = itemView.findViewById(R.id.tv_busnum);
            tpople = itemView.findViewById(R.id.tv_pople);
        }
    }
}
