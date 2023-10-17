package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.R;
import com.example.appfood.retrofit.ApiAppFood;
import com.example.appfood.retrofit.RetrofitClient;
import com.example.appfood.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    TextView txtlogin, txtnext;
    EditText email, pass, repass, phone, username;
    AppCompatButton button;
    ApiAppFood apiAppFood;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initControll();
    }

    private void initControll() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
       txtlogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent login = new Intent(getApplicationContext(), LoginActivity.class);
               startActivity(login);
               finish();
           }
       });
       txtnext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent next = new Intent(getApplicationContext(), HomeActivity.class);
               startActivity(next);
           }
       });
    }

    private void register() {
        String str_email = email.getText().toString().trim();  // trim() mục đích để cắt chuỗi 2 đầu
        String str_pass = pass.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        if (TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(), "Vui lòng điền đẩy đủ thông tin !!!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_phone)) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đẩy đủ thông tin !!!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_username)) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đẩy đủ thông tin !!!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_pass)) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đẩy đủ thông tin !!!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_repass)) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đẩy đủ thông tin !!!", Toast.LENGTH_SHORT).show();
        }else {
            if (str_pass.equals(str_repass)){
                // Nếu pass trùng thì post data lên sever
                compositeDisposable.add(apiAppFood.register(str_email, str_pass, str_phone, str_username)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                customerModel -> {
                                    if (customerModel.isSuccess()){
                                        Utils.customer_current.setEmail(str_email);
                                        Utils.customer_current.setPass(str_pass);
                                        Toast.makeText(getApplicationContext(), "Đăng ký thành công !!!", Toast.LENGTH_SHORT).show();
                                        Intent register = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(register);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(),customerModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },throwable -> {
                                    Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            }else {
                Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp, vui lòng nhập lại ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        apiAppFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAppFood.class);
        txtnext = findViewById(R.id.next);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
        phone = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        button = findViewById(R.id.btnregister);
        txtlogin = findViewById(R.id.txtlogin);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}