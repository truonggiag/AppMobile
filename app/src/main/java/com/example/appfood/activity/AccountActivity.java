package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.R;
import com.example.appfood.model.Account;
import com.example.appfood.retrofit.ApiAppFood;
import com.example.appfood.retrofit.RetrofitClient;
import com.example.appfood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.internal.Util;

public class AccountActivity extends AppCompatActivity {
    TextView txtgiohang, txthoatdong, txtlogout, txtemail, txtphone, txtname, txthome;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiAppFood apiAppFood;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
/*    List<Account> mangAccount;
    AccountAdapter accountAdapter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initView();
        ActionToolBar();

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
        txtname.setText(Utils.customer_current.getUsername());
        txtemail.setText(Utils.customer_current.getEmail());
        txtphone.setText(Utils.customer_current.getPhone());
        txtgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(giohang);
            }
        });
        txthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(home);
            }
        });
        txthoatdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hoatdong = new Intent(getApplicationContext(), ViewOrdersActivity.class);
                startActivity(hoatdong);
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().delete("user");
                Intent logout = new Intent(getApplicationContext(), RegisterActivity.class);
                Toast.makeText(getApplicationContext(), "Xin chào và hẹn gặp lại ", Toast.LENGTH_SHORT).show();
                startActivity(logout);
            }
        });
    }

    private void initView() {
        txtemail = findViewById(R.id.promail);
        txtphone = findViewById(R.id.prophone);
        txtname = findViewById(R.id.proname);
        txtlogout = findViewById(R.id.logout_pr);
        txthome = findViewById(R.id.home_pr);
        txtgiohang = findViewById(R.id.giohang_pr);
        txthoatdong = findViewById(R.id.hoatdong_pr);
        toolbar = findViewById(R.id.toobar);
        apiAppFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAppFood.class);
        //khoi tao list
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}