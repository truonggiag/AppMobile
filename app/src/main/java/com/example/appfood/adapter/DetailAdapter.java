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
import com.example.appfood.model.Item;
import com.example.appfood.model.Orders;

import java.text.DecimalFormat;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    List<Item> itemList;


    public DetailAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtname.setText("Sản phẩm: "+ item.getNamecate());
        holder.txtquantity.setText("Số lượng: "+ item.getQuantity());





    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgdetail;
        TextView txtname, txtquantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgdetail = itemView.findViewById(R.id.item_imgdetail);
            txtname  = itemView.findViewById(R.id.item_namecatedetail);
            txtquantity = itemView.findViewById(R.id.item_quantitydetail);

        }
    }
}
