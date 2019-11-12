package com.example.firsttopic.leftIndex;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;
import com.example.firsttopic.fiveTesting.IndexActivity;

import java.util.List;

public class indexAdapter extends RecyclerView.Adapter<indexAdapter.MyLiewHoder> {

    private Context context;
    private List<Integer> imglist;
    private List<String> contentlist;
    private List<Integer> indexlist;

    indexAdapter(Context context, List<Integer> list1, List<String> list2, List<Integer> list3) {
        this.context = context;
        imglist = list1;
        contentlist = list2;
        indexlist = list3;
    }

    @NonNull
    @Override
    public indexAdapter.MyLiewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyLiewHoder(LayoutInflater.from(context).inflate(R.layout.index_supadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyLiewHoder holder, int position) {
        Log.d("asd", "onBindViewHolder: " + imglist);
        holder.indeximage.setBackgroundResource(imglist.get(position));
        holder.tvnametext.setText(contentlist.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IndexActivity.class);
                context.startActivity(intent);
            }
        });
        switch (position) {
            case 0:
                if (indexlist.get(position) < 1000) {
                    holder.tvgrade.setText("弱(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("辐射较弱，涂擦SPF12~15、PA+护肤品");
                } else if (indexlist.get(position) >= 1000 && indexlist.get(position) <= 3000) {
                    holder.tvgrade.setText("中等(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("涂擦SPF大于15、PA+防晒护肤品");
                } else {
                    holder.tvgrade.setText("强(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("尽量减少外出，需要涂抹高倍数防晒霜");
                }
                break;
            case 1:
                if (indexlist.get(position) < 8) {
                    holder.tvgrade.setText("较易发(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("温度低，风较大，较易发生感冒，注意防护");
                } else {
                    holder.tvgrade.setText("少发(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("无明显降温，感冒机率较低");
                }
                break;
            case 2:
                if (indexlist.get(position) < 12) {
                    holder.tvgrade.setText("冷(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("建议穿长袖衬衫、单裤等服装");
                } else if (indexlist.get(position) >= 12 && indexlist.get(position) <= 21) {
                    holder.tvgrade.setText("舒适(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("建议穿短袖衬衫、单裤等服装");
                } else {
                    holder.tvgrade.setText("热(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("适合穿T恤、短薄外套等夏季服装");
                }
                break;
            case 3:
                if (indexlist.get(position) < 3000) {
                    holder.tvgrade.setText("事宜(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("气候适宜，推荐您进行户外运动");
                } else if (indexlist.get(position) >= 3000 && indexlist.get(position) <= 6000) {
                    holder.tvgrade.setText("中(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("易感人群应适当减少室外活动");
                } else {
                    holder.tvgrade.setText("较不易(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("空气氧气含量低，请在室内进行休闲运动");
                }
                break;
            case 4:
                if (indexlist.get(position) < 30) {
                    holder.tvgrade.setText("优(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气");
                } else if (indexlist.get(position) >= 30 && indexlist.get(position) <= 100) {
                    holder.tvgrade.setText("良(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("易感人群应适当减少室外活动");
                } else {
                    holder.tvgrade.setText("污染(" + indexlist.get(position) + ")");
                    holder.tvcontent.setText("空气质量差，不适合户外活动");
                }
                break;

        }
    }

    @Override
    public int getItemCount() {

        return imglist.size();
    }

    class MyLiewHoder extends RecyclerView.ViewHolder {
        private TextView tvcontent, tvgrade, tvnametext;
        private ImageView indeximage;
        private LinearLayout linearLayout;

        public MyLiewHoder(@NonNull View itemView) {
            super(itemView);
            tvnametext = itemView.findViewById(R.id.tv_nametext);
            tvgrade = itemView.findViewById(R.id.tv_grade);
            tvcontent = itemView.findViewById(R.id.tv_content);
            indeximage = itemView.findViewById(R.id.iv_index);
            linearLayout = itemView.findViewById(R.id.ll_index);
        }
    }
}
