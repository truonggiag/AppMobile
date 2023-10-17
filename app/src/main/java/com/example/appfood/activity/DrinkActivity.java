package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appfood.R;
import com.example.appfood.adapter.DrinkAdapter;
import com.example.appfood.model.CategoryNew;
import com.example.appfood.retrofit.ApiAppFood;
import com.example.appfood.retrofit.RetrofitClient;
import com.example.appfood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DrinkActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiAppFood apiAppFood;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 3;
    int type;
    DrinkAdapter drinkAdapter;
    List<CategoryNew> categoryNewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        apiAppFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAppFood.class);
        type = getIntent().getIntExtra("type", 3);
        initView();
        ActitonToolBar();
        getData(page);
    }

    private void getData(int page) {
        compositeDisposable.add(apiAppFood.getCategory(page, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryNewModel -> {
                            if (categoryNewModel.isSuccess()){
                                categoryNewList = categoryNewModel.getResult();
                                drinkAdapter = new DrinkAdapter(getApplicationContext(), categoryNewList);
                                recyclerView.setAdapter(drinkAdapter);
                            }
                        },throwable -> {
                            Toast.makeText(getApplicationContext(), "không kết nối được với sever !", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void ActitonToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initView() {
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleview_drink);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        categoryNewList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}