package com.example.duanxiaqingqq.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyItemAdapter extends RecyclerView.Adapter {
    ArrayList<String> mStrings;
    Context mContext;

    public RecyItemAdapter(ArrayList<String> strings, Context context) {
        mStrings = strings;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, null, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.viewText.setText(mStrings.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_text)
        TextView viewText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
