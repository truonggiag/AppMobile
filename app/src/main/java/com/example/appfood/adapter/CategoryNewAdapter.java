package com.example.appfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class CategoryNewAdapter extends RecyclerView.Adapter<CategoryNewAdapter.MyViewHolder> {
    Context context;
    List<CategoryNew> array;

    public CategoryNewAdapter(Context context, List<CategoryNew> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cate_new, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryNew categoryNew = array.get(position);
        holder.txtname.setText(categoryNew.getNamecate());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtprice.setText("Giá: "+decimalFormat.format(Double.parseDouble(categoryNew.getPrice()))+ "Đ");
        Glide.with(context).load(categoryNew.getImage()).into(holder.imgimage);
        holder.setItemClicklListener(new ItemClicklListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick){
                    // click
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("chitiet", categoryNew);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtprice, txtname;
        ImageView imgimage;
    private  ItemClicklListener itemClicklListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtprice = itemView.findViewById(R.id.itemsp_gia);
            txtname = itemView.findViewById(R.id.itemsp_ten);
            imgimage = itemView.findViewById(R.id.itemsp_image);
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
