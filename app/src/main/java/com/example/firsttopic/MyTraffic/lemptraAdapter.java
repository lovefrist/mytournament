package com.example.firsttopic.MyTraffic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.List;
import java.util.Map;

public class lemptraAdapter extends RecyclerView.Adapter<lemptraAdapter.MyViewHonder> {
    private Context context;
    private List<Map> list;
    private List<Map> listnowstatehen;
    private List<Map> listnowstatezhong;
    private static ButtonDlong mbuttonDlong;

    lemptraAdapter(Context context, List<Map> list, List<Map> listnowstate, List<Map> listnowstatezhong) {
        this.context = context;
        this.list = list;
        this.listnowstatehen = listnowstate;
        this.listnowstatezhong = listnowstatezhong;
    }

    @NonNull
    @Override
    public lemptraAdapter.MyViewHonder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHonder(LayoutInflater.from(context).inflate(R.layout.layout_lempadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHonder holder, int position) {
        holder.tvintersection.setText("路口" + (position + 1));
        holder.tvgreentime.setText("绿灯"+list.get(position).get("GreenTime").toString()+"秒");
        holder.tvyellowtime.setText("黄灯"+list.get(position).get("YellowTime").toString()+"秒");
        holder.tvredtime.setText("红灯"+list.get(position).get("RedTime").toString()+"秒");

        if (listnowstatehen.get(position).get("Status").toString().equals("Red")) {
            holder.tvlemptimehen.setText("红灯"+ listnowstatehen.get(position).get("Time").toString() + "秒");
            holder.tvyuantimehen.setText(listnowstatehen.get(position).get("Time").toString());
            holder.tvyuantimehen.setBackgroundResource(R.drawable.yuan46red);
        } else if (listnowstatehen.get(position).get("Status").toString().equals("Green")) {
            holder.tvlemptimehen.setText("绿灯"+ listnowstatehen.get(position).get("Time").toString() + "秒");
            holder.tvyuantimehen.setText(listnowstatehen.get(position).get("Time").toString());
            holder.tvyuantimehen.setBackgroundResource(R.drawable.yuan46green);
        } else {
            holder.tvlemptimehen.setText("黄灯"+ listnowstatehen.get(position).get("Time").toString() + "秒");
            holder.tvyuantimehen.setText(listnowstatehen.get(position).get("Time").toString());
            holder.tvyuantimehen.setBackgroundResource(R.drawable.yuan46yellor);
        }


        if (listnowstatezhong.get(position).get("Status").toString().equals("Red")) {
            holder.tvlemptimezhong.setText("红灯"+ listnowstatezhong.get(position).get("Time").toString() + "秒");
            holder.tvyuantimezhong.setText(listnowstatezhong.get(position).get("Time").toString());
            holder.tvyuantimezhong.setBackgroundResource(R.drawable.yuan46red);

        } else if (listnowstatezhong.get(position).get("Status").toString().equals("Green")) {
            holder.tvlemptimezhong.setText("绿灯" + listnowstatezhong.get(position).get("Time").toString() + "秒");
            holder.tvyuantimezhong.setText(listnowstatezhong.get(position).get("Time").toString());
            holder.tvyuantimezhong.setBackgroundResource(R.drawable.yuan46green);
        } else {
            holder.tvlemptimezhong.setText("黄灯" + listnowstatezhong.get(position).get("Time").toString() + "秒");
            holder.tvyuantimezhong.setText(listnowstatezhong.get(position).get("Time").toString());
            holder.tvyuantimezhong.setBackgroundResource(R.drawable.yuan46yellor);
        }
        holder.btnlemphen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbuttonDlong.onClick(position+1,"1-1");
            }
        });
        holder.btnlempzhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbuttonDlong.onClick(position+1,"1-2");
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHonder extends RecyclerView.ViewHolder {
        private TextView tvintersection, tvgreentime, tvyellowtime, tvredtime, tvlemptimehen, tvlemptimezhong, tvyuantimehen, tvyuantimezhong;
        private Button btnlemphen, btnlempzhong;

        public MyViewHonder(@NonNull View itemView) {
            super(itemView);
            tvintersection = itemView.findViewById(R.id.tv_intersectionnow);
            tvgreentime = itemView.findViewById(R.id.tv_greentime);
            tvyellowtime = itemView.findViewById(R.id.tv_yellowtime);
            tvredtime = itemView.findViewById(R.id.tv_redtime);
            tvlemptimehen = itemView.findViewById(R.id.tv_lemptimehen);
            tvlemptimezhong = itemView.findViewById(R.id.tv_lemptimezhong);
            tvyuantimehen = itemView.findViewById(R.id.tv_yuantimehen);
            tvyuantimezhong = itemView.findViewById(R.id.tv_yuantimezhong);
            btnlemphen = itemView.findViewById(R.id.btn_lemphen);
            btnlempzhong = itemView.findViewById(R.id.btn_lempzhong);
        }
    }
    public static void onclk(ButtonDlong buttonDlong){
        mbuttonDlong = buttonDlong;
    }

}
