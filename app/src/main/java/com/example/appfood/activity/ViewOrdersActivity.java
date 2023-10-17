package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appfood.R;
import com.example.appfood.adapter.OrdersAdapter;
import com.example.appfood.retrofit.ApiAppFood;
import com.example.appfood.retrofit.RetrofitClient;
import com.example.appfood.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewOrdersActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiAppFood apiAppFood;
    RecyclerView reorders;
    Toolbar toolbar;
    ImageView btnhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        initView();
        initToolbar();
        getOrders();
    }
    private void getOrders() {
        compositeDisposable.add(apiAppFood.viewOrders(Utils.customer_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ordersModel -> {
                            OrdersAdapter adapter = new OrdersAdapter(getApplicationContext(), ordersModel.getResult());
                            reorders.setAdapter(adapter);
/*
                            Toast.makeText(getApplicationContext(), ordersModel.getResult().get(0).getItem().get(0).getNamecate(), Toast.LENGTH_SHORT).show();
*/
                        },
                        throwable -> {

                        }
                ));
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnhome = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(btnhome);
            }
        });
    }

    private void initView() {
        apiAppFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAppFood.class);
        reorders = findViewById(R.id.recycleview_orders);
        toolbar = findViewById(R.id.toobar);
        btnhome = findViewById(R.id.viewhome);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reorders.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}