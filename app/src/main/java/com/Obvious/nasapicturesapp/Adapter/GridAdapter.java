package com.Obvious.nasapicturesapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Obvious.nasapicturesapp.DataModel.DataModel;
import com.Obvious.nasapicturesapp.R;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {



    private List<DataModel> dataSet;
    Context mContext;

    public GridAdapter(Context context, List<DataModel> data){
        this.dataSet = data;
        this.mContext = context;
    }



    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.adapter_list,parent,false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
         DataModel models = dataSet.get(position);

        if(models.getUrl()!=null){
            Glide.with(this.mContext).load(models.getUrl()).apply(new RequestOptions().placeholder(R.drawable.image)).into(holder.image);
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}
