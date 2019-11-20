package com.example.firsttopic.Mycar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

public class ControlreAdapter extends RecyclerView.Adapter<ControlreAdapter.MyViewHoder> {
    private Context context;
    ControlreAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ControlreAdapter.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHoder(LayoutInflater.from(context).inflate(R.layout.layout_controlreadapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        holder.tvuserid.setText(position+1+"");
        holder.rbcontrkai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                        holder.rbcontrkai.setBackgroundResource(R.drawable.controlr_kai);
                        holder.rbcontrguan.setBackgroundResource(R.drawable.dlong_recycler_adapter);
                }else {
                    holder.rbcontrkai.setBackgroundResource(R.drawable.dlong_recycler_adapter);
                    holder.rbcontrguan.setBackgroundResource(R.drawable.controlr_kai);

                }
            }
        });
        holder.rbcontrguan.setBackgroundColor(Color.rgb(127,255,0));
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView tvuserid;
        RadioButton rbcontrkai,rbcontrguan;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            tvuserid = itemView.findViewById(R.id.tv_userid);
            rbcontrkai = itemView.findViewById(R.id.rb_contrkai);
            rbcontrguan = itemView.findViewById(R.id.rb_contrguan);
        }
    }
}
