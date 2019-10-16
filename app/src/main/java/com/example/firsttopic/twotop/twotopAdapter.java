package com.example.firsttopic.twotop;

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

public class twotopAdapter extends RecyclerView.Adapter<twotopAdapter.linerViewHoder> {
    private Context mcontext;
    private List<GetAppdata>  dataget;
    public twotopAdapter(Context context, List<GetAppdata> list){
        mcontext = context;
        dataget = list;
    }
    @NonNull
    @Override
    public linerViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new linerViewHoder(LayoutInflater.from(mcontext).inflate(R.layout.layout_form,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull linerViewHoder holder, int position) {
        if (position == 0){
            holder.tv_intersection.setText("路口");
            holder.tv_red.setText("红灯时长（S）");
            holder.tv_grenn.setText("黄灯时长（S）");
            holder.tv_yellew.setText("绿灯时长（S）");
             ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.linearLayout.getLayoutParams();
            layoutParams.setMargins(0,0,0,0);
            holder.linearLayout.setLayoutParams(layoutParams);

        }else {
            holder.tv_intersection.setText(dataget.get(position-1).getId()+"");
            holder.tv_red.setText(dataget.get(position-1).getRedTime()+"");
            holder.tv_grenn.setText(dataget.get(position-1).getGreenTime()+"");
            holder.tv_yellew.setText(dataget.get(position-1).getYellowTime()+"");
        }

    }



    @Override
    public int getItemCount() {
        return dataget.size()+1;
    }
    class linerViewHoder extends  RecyclerView.ViewHolder{
            private TextView  tv_intersection,tv_red,tv_grenn,tv_yellew;
            private LinearLayout linearLayout;
        public linerViewHoder(@NonNull View itemView) {
            super(itemView);
            tv_intersection = itemView.findViewById(R.id.intersection);
            tv_red = itemView.findViewById(R.id.tv_red);
            tv_grenn = itemView.findViewById(R.id.tv_grenn);
            tv_yellew = itemView.findViewById(R.id.tv_yellow);
            linearLayout = itemView.findViewById(R.id.ll_tou);
        }
    }
}
