package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.R;
import com.example.appfood.retrofit.ApiAppFood;
import com.example.appfood.retrofit.RetrofitClient;
import com.example.appfood.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CheckOutActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtemail, txtphone;
    EditText address;
    AppCompatButton btnthanhtoan;
    long tongtien;
    int totalItem;
    ApiAppFood apiAppFood;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        initView();
        countItem();
        intitControl();

    }
    // tính tổng
    private void countItem() {
        totalItem = 0;
        for (int i = 0 ; i < Utils.mangmuahang.size(); i++){
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluong();
        }
    }
     // ánh xạ
    private void initView() {
        apiAppFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAppFood.class);
        toolbar = findViewById(R.id.toobar);
        txtemail = findViewById(R.id.txtemail);
        txttongtien = findViewById(R.id.txttongtien);
        txtphone = findViewById(R.id.txtphone);
        address = findViewById(R.id.txtaddress);
        btnthanhtoan = findViewById(R.id.btnthanhtoan);

    }
    // toolbar back
    private void intitControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien));
        txtemail.setText(Utils.customer_current.getEmail());
        txtphone.setText(Utils.customer_current.getPhone());
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_address = address.getText().toString().trim();
                if (TextUtils.isEmpty(str_address)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập địa chỉ ", Toast.LENGTH_SHORT).show();
                }else {
                    //post data lên sever
                    String str_email = Utils.customer_current.getEmail();
                    String str_phone = Utils.customer_current.getPhone();
                    int id  = Utils.customer_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));  // chỉ để test trong logcat
                    compositeDisposable.add(apiAppFood.createOrder(id, str_address, str_phone, str_email, totalItem, String.valueOf(tongtien), new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    customerModel -> {
                                       Toast.makeText(getApplicationContext(), "Không thành công", Toast.LENGTH_SHORT).show();
                 /*                       Intent thanhtoan = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(thanhtoan);*/
                                    },throwable -> {
                                        Toast.makeText(getApplicationContext(),"Thành công !!!", Toast.LENGTH_SHORT).show();
                                        Utils.mangmuahang.clear();
                                        Intent intent = new Intent(getApplicationContext(), ViewOrdersActivity.class);
                                        startActivity(intent);
                                    }
                            ));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}