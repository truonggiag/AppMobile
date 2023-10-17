package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appfood.R;
import com.example.appfood.adapter.CartAdapter;
import com.example.appfood.model.Cart;
import com.example.appfood.model.EventBus.TotalEvent;
import com.example.appfood.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.internal.Util;

public class CartActivity extends AppCompatActivity {
    TextView giohangtrong, tongtien, txtshopping;
   Toolbar toolbar;
    RecyclerView recyclerView;
    Button thanhtoan;
    CartAdapter adapter;
    long tongtiensp;
    //List<Cart> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        initControl();
        TotalCart();
    }

    private void TotalCart() {
        tongtiensp = 0 ;
        for (int i = 0; i < Utils.mangmuahang.size(); i++){
            tongtiensp = tongtiensp + (Utils.mangmuahang.get(i). getGiasp() * Utils.mangmuahang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp));

    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shopping = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(shopping);
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
        }else{
            adapter = new CartAdapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent thanhtoan = new Intent(getApplicationContext(), CheckOutActivity.class);
                thanhtoan.putExtra("tongtien", tongtiensp);
                Utils.manggiohang.clear();
                startActivity(thanhtoan);
            }
        });
    }


    private void initView() {
        txtshopping = findViewById(R.id.shoppingcart);
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toobar);
        thanhtoan = findViewById(R.id.btnmuahang);
        recyclerView = findViewById(R.id.recycleviewgiohang);
        thanhtoan = findViewById(R.id.btnmuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public  void eventTotal(TotalEvent event){
        if (event != null){
            TotalCart();
        }
    }
}