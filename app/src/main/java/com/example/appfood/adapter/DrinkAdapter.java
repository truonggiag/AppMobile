package com.example.appfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appfood.R;
import com.example.appfood.model.CategoryNew;

import java.text.DecimalFormat;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.MyViewHolder> {
    Context context;
    List<CategoryNew> array;

    public DrinkAdapter(Context context, List<CategoryNew> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryNew categoryNew = array.get(position);
        holder.namecate.setText(categoryNew.getNamecate());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText("Giá: "+decimalFormat.format(Double.parseDouble(categoryNew.getPrice()))+ "Đ");
        Glide.with(context).load(categoryNew.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView namecate, price;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namecate = itemView.findViewById(R.id.itemdrink_ten);
            price = itemView.findViewById(R.id.itemdrink_gia);
            image = itemView.findViewById(R.id.itemdrink_image);
        }
    }
}
