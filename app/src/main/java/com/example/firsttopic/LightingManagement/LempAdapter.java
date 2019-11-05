package com.example.firsttopic.LightingManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;
import com.example.firsttopic.twotop.GetAppdata;

import java.util.List;


public class LempAdapter extends RecyclerView.Adapter<LempAdapter.MylempViewHoder>{
    private Context context;
    private List<GetAppdata> list;
    private Getcbbtn getcbbtn;
    LempAdapter(Context context, List<GetAppdata> list,Getcbbtn getcbbtn){
        this.context = context;
        this.list = list;
        this.getcbbtn = getcbbtn;

    }
    @NonNull
    @Override
    public LempAdapter.MylempViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MylempViewHoder(LayoutInflater.from(context).inflate(R.layout.layout_lemp_adapter,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull MylempViewHoder holder, int position) {
        if (position == 0){
            holder.tvintersection.setText("路口");
            holder.tvlempred.setText("红灯时长");
            holder.tvlempyellow.setText("黄灯时长");
            holder.tvlempgreen.setText("绿灯时长");
            holder.tvcz1.setVisibility(View.VISIBLE);
            holder.btncz.setVisibility(View.GONE);
            holder.tvcz1.setText("操作项");
            holder.tvsetup1.setVisibility(View.VISIBLE);
            holder.cbconfirm.setVisibility(View.GONE);
            holder.tvsetup1.setText("设置");
        }else {
            holder.tvintersection.setText(list.get(position-1).getId()+"");
            holder.tvlempred.setText(list.get(position-1).getRedTime()+"");
            holder.tvlempyellow.setText(list.get(position-1).getYellowTime()+"");
            holder.tvlempgreen.setText(list.get(position-1).getGreenTime()+"");
            holder.cbconfirm.setVisibility(View.VISIBLE);
            holder.cbconfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    getcbbtn.getChbex(position,isChecked,holder.cbconfirm);
                }
            });
            holder.btncz.setVisibility(View.VISIBLE);
            holder.btncz.setEnabled(false);
            getcbbtn.getBtn(holder.btncz);
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

     class MylempViewHoder extends RecyclerView.ViewHolder{
       private TextView tvintersection,tvlempred,tvlempyellow,tvlempgreen,tvcz1,tvsetup1;
        private CheckBox cbconfirm;
        private Button btncz;
        public MylempViewHoder(@NonNull View itemView) {
            super(itemView);
            tvintersection =  itemView.findViewById(R.id.tv_intersection);
            tvlempred =  itemView.findViewById(R.id.tv_lempred);
            tvlempyellow =  itemView.findViewById(R.id.tv_lempyellow);
            tvlempgreen = itemView.findViewById(R.id.tv_lempgreen);
            tvcz1 = itemView.findViewById(R.id.tv_cz1);
            tvsetup1 = itemView.findViewById(R.id.tv_setup1);
            cbconfirm = itemView.findViewById(R.id.cb_confirm);
            btncz = itemView.findViewById(R.id.btn_cz);
        }
    }
}
