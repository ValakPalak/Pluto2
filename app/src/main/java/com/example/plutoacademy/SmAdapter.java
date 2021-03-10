package com.example.plutoacademy;

import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class SmAdapter extends RecyclerView.Adapter<SmAdapter.ExampleViewHolder> {
    private final ArrayList<SmModel> mExampleList;
    Context context;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;


        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageviewid);
        }
    }

    public SmAdapter(ArrayList<SmModel> mExampleList,Context context) {
        this.mExampleList = mExampleList;
        this.context=context;
    }

    @NotNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.smlogo, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {

        Glide.with(context).load(mExampleList.get(position).getLogo()).into(holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buy_url=mExampleList.get(position).getLink();
                if (buy_url.startsWith("https://") || buy_url.startsWith("http://")) {
                    Uri uri = Uri.parse(buy_url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Invalid Url", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
