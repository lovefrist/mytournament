package com.example.firsttopic.ninthtop;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarmationAdapter extends RecyclerView.Adapter<CarmationAdapter.ViewHodel> {
    @NonNull
    @Override
    public CarmationAdapter.ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 0;
    }
    class ViewHodel extends RecyclerView.ViewHolder{

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
        }
    }
}
