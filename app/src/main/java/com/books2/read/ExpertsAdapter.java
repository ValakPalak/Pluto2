package com.books2.read;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExpertsAdapter extends RecyclerView.Adapter<ExpertsAdapter.ExpertsViewHolder> {
    public ArrayList<ExpertsModel> ExpertsList;
    OnItemClick itemClick;

    public static class ExpertsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mExpertImage;
        public TextView mExpertName;
        public TextView mExpertType;
        OnItemClick itemClick;
        public ExpertsViewHolder(@NonNull View itemView,OnItemClick onItemClick) {
            super(itemView);
            mExpertImage = itemView.findViewById(R.id.ExpertItemImageView);
            mExpertName = itemView.findViewById(R.id.ExpertName);
            mExpertType = itemView.findViewById(R.id.ExpertType);
            itemClick=  onItemClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClick.Onclick(getAdapterPosition());

        }
    }

    @NonNull
    @Override
    public ExpertsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.experts_item, parent, false);
        return new ExpertsViewHolder(v,itemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpertsViewHolder holder, int position) {
        ExpertsModel currItem = ExpertsList.get(position);

        Picasso.get()
                .load(currItem.getmExpertImage()).into(holder.mExpertImage);
        holder.mExpertName.setText(currItem.getmExpertName()+"");
        holder.mExpertType.setText(currItem.getmExpertType()+"");

    }

    @Override
    public int getItemCount() {
        return ExpertsList.size();
    }

    public ExpertsAdapter(ArrayList<ExpertsModel> expertsList, OnItemClick itemClick) {
        ExpertsList = expertsList;
        this.itemClick = itemClick;
    }
}
