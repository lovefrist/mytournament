package com.example.firsttopic.therrtop;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.List;


public class SetTherrAdapter extends RecyclerView.Adapter<SetTherrAdapter.TherrViewHodler> {
    private Context mcontext;
    private List<SetGeneric> lists;
   public SetTherrAdapter(Context context,List<SetGeneric> list){
        mcontext = context;
       lists = list;

    }
    @NonNull
    @Override
    public TherrViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TherrViewHodler(LayoutInflater.from(mcontext).inflate(R.layout.layput_therr,parent,false) );
    }

    @Override
    public void onBindViewHolder(@NonNull TherrViewHodler holder, int position) {
                if (position == 0){
                    holder.tv_Serial.setText("序号");
                    holder.tv_Car.setText("车号");
                    holder.tv_Money.setText("充值金额");
                    holder.tv_people.setText("操作人");
                    holder.tv_time.setText("时间");
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.tv_Serial.getLayoutParams();
                    layoutParams.setMargins(0,0,0,0);
                    holder.tv_Serial.setLayoutParams(layoutParams);

                }else {
                    holder.tv_Serial.setText(lists.get(position-1).getId()+"");
                    holder.tv_Car.setText(lists.get(position-1).getCarid());
                    holder.tv_Money.setText(lists.get(position-1).getMoney());
                    holder.tv_people.setText(lists.get(position-1).getPopel());
                    holder.tv_time.setText(lists.get(position-1).getTime()+"");
                }

    }


    @Override
    public int getItemCount() {
        return lists.size()+1;
    }
    class TherrViewHodler extends  RecyclerView.ViewHolder{
            private TextView tv_Serial,tv_Car,tv_Money,tv_people,tv_time;
        public TherrViewHodler(@NonNull View itemView) {
            super(itemView);
            tv_Serial = itemView.findViewById(R.id.tv_setSerial);
            tv_Car = itemView.findViewById(R.id.tv_setCar);
            tv_Money = itemView.findViewById(R.id.tv_setMoney);
            tv_people = itemView.findViewById(R.id.tv_setpeople);
            tv_time = itemView.findViewById(R.id.tv_settime);
        }
    }
}
