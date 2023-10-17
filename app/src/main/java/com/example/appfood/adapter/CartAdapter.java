package com.example.appfood.adapter;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appfood.Interface.IImageClickListenner;
import com.example.appfood.R;
import com.example.appfood.model.Cart;
import com.example.appfood.model.EventBus.TotalEvent;
import com.example.appfood.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.item_giohang_ten.setText(cart.getTensp());
        holder.item_giohang_soluong.setText(cart.getSoluong() +" ");
        Glide.with(context).load(cart.getHinhanh()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format((cart.getGiasp()))+ "Đ");
        long gia = cart.getSoluong() * cart.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Utils.mangmuahang.add(cart);
                    EventBus.getDefault().postSticky(new TotalEvent());
                }else{
                    for (int i = 0; i < Utils.mangmuahang.size(); i++){
                        if (Utils.mangmuahang.get(i).getIdsp() == cart.getIdsp()){ 
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TotalEvent());
                        }
                    }
                }
            }
        });


        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1){
                        if (cartList.get(pos).getSoluong() > 1){
                            int soluongmoi = cartList.get(pos).getSoluong()-1;
                            cartList.get(pos).setSoluong(soluongmoi);

                            holder.item_giohang_soluong.setText(cartList.get(pos).getSoluong() +" ");
                            long gia = cartList.get(pos).getSoluong() * cartList.get(pos).getGiasp();
                            holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                            EventBus.getDefault().postSticky(new TotalEvent());
                        } else if (cartList.get(pos).getSoluong() == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                            builder.setTitle("Thông báo !!!");
                            builder.setMessage("Xóa sản phẩm khỏi giỏ hàng !!!");
                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Utils.manggiohang.remove(pos);
                                    notifyDataSetChanged();
                                    EventBus.getDefault().postSticky(new TotalEvent());
                                }
                            });
                            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.show();
                        }
                }else if (giatri == 2 ){
                        if (cartList.get(pos).getSoluong() < 11){
                            int soluongmoi = cartList.get(pos).getSoluong()+1;
                            cartList.get(pos).setSoluong(soluongmoi);
                        }
                    holder.item_giohang_soluong.setText(cartList.get(pos).getSoluong() +" ");
                    long gia = cartList.get(pos).getSoluong() * cartList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TotalEvent());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgtru, imgcong;
        TextView item_giohang_ten, item_giohang_gia, item_giohang_giasp2, item_giohang_soluong;
        IImageClickListenner listenner;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_ten = itemView.findViewById(R.id.item_giohang_ten);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            checkBox = itemView.findViewById(R.id.item_giohang_check);

            // event click
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }

        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru){
                listenner.onImageClick(view, getAdapterPosition(), 1);
                // tru 1
            }else if (view == imgcong){
                // cong 1
                listenner.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }
}
