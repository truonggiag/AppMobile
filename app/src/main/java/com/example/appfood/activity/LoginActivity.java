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

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    TextView txtregister, txttresetpass, txtnext;
    EditText email, pass;
    AppCompatButton button;
    ApiAppFood apiAppFood;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initControll();
    }

    private void initControll() {
        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
            }
        });
        txtnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(next);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                String str_pass = pass.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đẩy đủ thông tin !!!", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(str_pass)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đẩy đủ thông tin !!!", Toast.LENGTH_SHORT).show();
                }else {
                    // lưu thông tin
                    Paper.book().write("email", str_email);
                    Paper.book().write("pass", str_pass);

                    compositeDisposable.add(apiAppFood.login(str_email, str_pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    customerModel -> {
                                        if (customerModel.isSuccess()){

                                            Utils.customer_current = customerModel.getResult().get(0);
                                            // lưu lại thoong tin người dùng
                                            Paper.book().write("customer", customerModel.getResult().get(0));
                                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công !!!", Toast.LENGTH_SHORT).show();
                                            Intent login = new Intent(getApplicationContext(), HomeActivity.class);
                                            startActivity(login);
                                            finish();
                                        }
                                    },throwable -> {
                                        Toast.makeText(getApplicationContext(), "Sai tên đăng nhập hoặc mật khẩu !!!", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void initView() {
        Paper.init(this);
        apiAppFood  = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAppFood.class);
        txtnext = findViewById(R.id.next);
        txtregister = findViewById(R.id.txtregister);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        button = findViewById(R.id.btnlogin);
        // đọc dữ liệu
        if (Paper.book().read("email") != null && Paper.book().read("pass") != null){
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.customer_current.getEmail() != null && Utils.customer_current.getPass() != null){
            email.setText(Utils.customer_current.getEmail());
            pass.setText(Utils.customer_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}