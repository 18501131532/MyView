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

public class RecyclerAdapter extends RecyclerView.Adapter {
    ArrayList<String> mStrings;
    ArrayList<Bean> mArrayLists;
    Context mContext;

    public RecyclerAdapter(ArrayList<String> strings, ArrayList<Bean> arrayLists, Context context) {
        mStrings = strings;
        mArrayLists = arrayLists;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, null, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.groupText.setText(mArrayLists.get(position).text.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolder.groupRlv.setLayoutManager(linearLayoutManager);
        RecyItemAdapter recyItemAdapter = new RecyItemAdapter(mStrings, mContext);
        viewHolder.groupRlv.setAdapter(recyItemAdapter);
    }

    @Override
    public int getItemCount() {
        return mArrayLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.group_text)
        TextView groupText;
        @BindView(R.id.group_rlv)
        RecyclerView groupRlv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
