package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.example.appfood.R;
import com.example.appfood.model.Cart;
import com.example.appfood.model.CategoryNew;
import com.example.appfood.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import okhttp3.internal.Util;

public class DetailActivity extends AppCompatActivity {
    TextView namecate, price, mota;
    Button btnaddcart;
    ImageView image;
    Spinner spinner;
    Toolbar toolbar;
    CategoryNew categoryNew;
    NotificationBadge badge;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcart();
            }
        });
    }

    private void addcart() {
        if (Utils.manggiohang.size() > 0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.manggiohang.size(); i++){
                if (Utils.manggiohang.get(i).getIdsp() == categoryNew.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong(categoryNew.getPrice()) * Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    flag = true;
                }
            }
            if (flag == false){
                long gia = Long.parseLong(categoryNew.getPrice()) * soluong;
                Cart cart  = new Cart();
                cart.setGiasp(gia);
                cart.setSoluong(soluong);
                cart.setIdsp(categoryNew.getId());
                cart.setTensp(categoryNew.getNamecate());
                cart.setHinhanh(categoryNew.getImage());
                Utils.manggiohang.add(cart);
            }
        }else{
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(categoryNew.getPrice()) * soluong;
            Cart cart  = new Cart();
            cart.setGiasp(gia);
            cart.setSoluong(soluong);
            cart.setIdsp(categoryNew.getId());
            cart.setTensp(categoryNew.getNamecate());
            cart.setHinhanh(categoryNew.getImage());
            Utils.manggiohang.add(cart);
        }
        int totalItem = 0;
        for (int i = 0 ; i < Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null){

            int totalItem = 0;
            for (int i = 0 ; i < Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    private void initData() {
        categoryNew = categoryNew = (CategoryNew) getIntent().getSerializableExtra("chitiet");
        namecate.setText(categoryNew.getNamecate());
        mota.setText(categoryNew.getDescription());
        Glide.with(getApplicationContext()).load(categoryNew.getImage()).into(image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        DetailActivity myViewHolder;
        price.setText("Giá: "+decimalFormat.format(Double.parseDouble(categoryNew.getPrice()))+ "VNĐ");
        Integer[] so = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterspin);
    }

    private void initView() {
        namecate = findViewById(R.id.txtnamecate);
        price = findViewById(R.id.txtprice);
        mota = findViewById(R.id.txtmotachitiet);
        btnaddcart = findViewById(R.id.btnaddcart);
        spinner = findViewById(R.id.spinner);
        image = findViewById(R.id.imgdetail);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutcart = findViewById(R.id.framecart);
        frameLayoutcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });
        if (Utils.manggiohang != null){

            int totalItem = 0;
            for (int i = 0 ; i < Utils.manggiohang.size(); i++){
                    totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
}