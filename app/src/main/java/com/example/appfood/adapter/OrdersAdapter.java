package com.example.appfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;
import com.example.appfood.model.Orders;
import com.example.appfood.model.OrdersModel;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<Orders> listorder;

    public OrdersAdapter(Context context, List<Orders> listorder) {
        this.context = context;
        this.listorder = listorder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orders orders = listorder.get(position);
/*
        holder.txtorders.setText("Đơn hàng: " + orders.getId());
*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(
            holder.redetail.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(orders.getItem().size());
        //adapter detail
        DetailAdapter detailAdapter = new DetailAdapter(orders.getItem());
        holder.redetail.setLayoutManager(layoutManager);
        holder.redetail.setAdapter(detailAdapter);
        holder.redetail.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return listorder.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtorders;
        RecyclerView redetail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
/*
            txtorders = itemView.findViewById(R.id.idorders);
*/
            redetail = itemView.findViewById(R.id.recycleview_detail);
        }
    }
}
