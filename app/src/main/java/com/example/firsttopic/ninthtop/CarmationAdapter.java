package com.example.firsttopic.ninthtop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.R;

import java.util.HashMap;
import java.util.List;

public class CarmationAdapter extends RecyclerView.Adapter<CarmationAdapter.ViewHodel> {
    private Context mcontext;
    private List<HashMap<String,String>> mlists;
    private getDataValue my;


    CarmationAdapter(Context context, List<HashMap<String,String>> lists,getDataValue my) {
        mcontext = context;
        mlists = lists;
        this.my = my;
    }

    @NonNull
    @Override
    public CarmationAdapter.ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHodel(LayoutInflater.from(mcontext).inflate(R.layout.layout_account_adapt, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.mtv_sequence.setText(mlists.get(position).get("id"));
        holder.miv_carlog.setImageResource(Integer.parseInt(mlists.get(position).get("carid")));
        holder.mtv_carnumber.setText(mlists.get(position).get("carnum"));
        holder.mtv_name.setText(mlists.get(position).get("name"));
        holder.mtv_balance.setText(mlists.get(position).get("balance"));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            my.getValue(position+1,isChecked);

        });
        my.getbtnview(position,holder.mbtnrecharge);
        my.getchexview(position,holder.checkBox);
        my.getTextView(holder.mtv_balance);
        holder.mbtnrecharge.setEnabled(false);
        holder.mbtnrecharge.setOnClickListener(v -> my.getView(position+1));
    }

    @Override
    public int getItemCount() {
        return mlists.size();
    }

    class ViewHodel extends RecyclerView.ViewHolder {
        private TextView mtv_sequence,mtv_carnumber,mtv_name,mtv_balance;
        private ImageView miv_carlog;
        private Button mbtnrecharge;
        private CheckBox checkBox;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            mtv_sequence = itemView.findViewById(R.id.tv_sequence);
            mtv_carnumber = itemView.findViewById(R.id.tv_carnumber);
            mtv_name = itemView.findViewById(R.id.tv_name);
            mtv_balance = itemView.findViewById(R.id.tv_balance);
            miv_carlog = itemView.findViewById(R.id.iv_carlog);
            mbtnrecharge = itemView.findViewById(R.id.btn_recharge);
            checkBox  = itemView.findViewById(R.id.cb_choice);
        }
    }
}
