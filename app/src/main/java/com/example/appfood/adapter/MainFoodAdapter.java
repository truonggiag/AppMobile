package com.example.appfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appfood.Interface.ItemClicklListener;
import com.example.appfood.R;
import com.example.appfood.activity.DetailActivity;
import com.example.appfood.model.CategoryNew;

import java.text.DecimalFormat;
import java.util.List;

public class MainFoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        List<CategoryNew> array;
        private static final int VIEW_TYPE_DATA = 0;
        private static final int VIEW_TYPE_LOADING = 1;

    public MainFoodAdapter(Context context, List<CategoryNew> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mainfood, parent, false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return  new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            CategoryNew category = array.get(position);
            myViewHolder.namecate.setText(category.getNamecate());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.price.setText("Giá: "+decimalFormat.format(Double.parseDouble(category.getPrice()))+ "Đ");
            // holder.description.setText(category.getDescription());
            Glide.with(context).load(category.getImage()).into(myViewHolder.image);
            myViewHolder.setItemClicklListener(new ItemClicklListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if (!isLongClick){
                        // click
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("chitiet", category);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) ==  null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }



    // Loading extends từ RecycleView.ViewHolder
    public  class LoadingViewHolder extends RecyclerView.ViewHolder{
            ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //khai báo biến
        TextView namecate, price;
        ImageView image;
        private ItemClicklListener itemClicklListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namecate = itemView.findViewById(R.id.itemmf_ten);
            price = itemView.findViewById(R.id.itemmf_gia);
            image = itemView.findViewById(R.id.itemmf_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClicklListener(ItemClicklListener itemClicklListener) {
            this.itemClicklListener = itemClicklListener;
        }

        @Override
        public void onClick(View view) {
            itemClicklListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
