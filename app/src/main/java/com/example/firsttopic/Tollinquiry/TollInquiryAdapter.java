package com.example.firsttopic.Tollinquiry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

public class TollInquiryAdapter extends RecyclerView.Adapter<TollInquiryAdapter.MyViewHoder> {
    private Context context;
    TollInquiryAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public TollInquiryAdapter.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHoder(LayoutInflater.from(context).inflate(R.layout.layout_tolladapter,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class MyViewHoder extends RecyclerView.ViewHolder {
        private TextView tviccar,tvcarbrand,tvpoplename,tvadmissiontime,tvappearancetime,tvmoneyamount;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            tviccar = itemView.findViewById(R.id.tv_iccar);
            tvcarbrand = itemView.findViewById(R.id.tv_caridadap);
            tvpoplename = itemView.findViewById(R.id.tv_poplename);
            tvadmissiontime = itemView.findViewById(R.id.tv_admissiontime);
            tvappearancetime = itemView.findViewById(R.id.tv_appearancetime);
            tvmoneyamount = itemView.findViewById(R.id.tv_moneyamount);
        }
    }
}
