package com.Obvious.nasapicturesapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.Obvious.nasapicturesapp.MainActivity;
import com.Obvious.nasapicturesapp.SheetActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Obvious.nasapicturesapp.DataModel.DataModel;
import com.Obvious.nasapicturesapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import static java.security.AccessController.getContext;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    public Activity mContext;

    private OnImageClickListener listener;

    public interface OnImageClickListener{
        void onImageClick(int pos);
    }

    protected void slideInTransition(Context context) {
        mContext.overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
    }

    protected void slideOutTransition() {
        mContext.overridePendingTransition(R.anim.fade_forward, R.anim.slide_out_right);
    }

    private List<DataModel> dataSet;
    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public GridAdapter(Context context, List<DataModel> data){
        this.dataSet = data;
        this.mContext = (Activity) context;
    }

    public void setOnImageClickListener(OnImageClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public GridAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list,parent, false);
        GridViewHolder vh = new GridViewHolder(v);
        return vh;
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
                if(listener!=null){
                    listener.onImageClick(position);
                }
//                Intent i = new Intent(mContext, SheetActivity.class);
//                i.putExtra("position", position);
//                mContext.startActivity(i);
//                mContext.overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
