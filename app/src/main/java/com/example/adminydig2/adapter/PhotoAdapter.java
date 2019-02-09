package com.example.adminydig2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.example.adminydig2.R;
import com.example.adminydig2.model.PhotoModel;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<PhotoModel> photoModelArrayList;

    public PhotoAdapter(Context ctx,ArrayList<PhotoModel> photoModelArrayList){
        layoutInflater = LayoutInflater.from(ctx);
        this.photoModelArrayList = photoModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.recycler_view, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewCaption.setText(photoModelArrayList.get(position).getCaption());
        holder.textViewTampilkansamapi.setText(photoModelArrayList.get(position).getTampilkansampai());
        holder.textViewStatus.setText(photoModelArrayList.get(position).getStatusphoto());
        Picasso.get().load(photoModelArrayList.get(position).getImgURL()).into(holder.imageViewPhoto);
    }

    @Override
    public int getItemCount() {
        return photoModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCaption, textViewTampilkansamapi, textViewStatus;
        ImageView imageViewPhoto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCaption = itemView.findViewById(R.id.textViewCaption);
            textViewTampilkansamapi = itemView.findViewById(R.id.textViewTampilkansampai);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
        }
    }
}
