package com.example.appfood.retrofit;

import com.example.appfood.model.AccountModel;
import com.example.appfood.model.CategoryNewModel;
import com.example.appfood.model.CustomerModel;
import com.example.appfood.model.OrdersModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiAppFood {

    //category new
    @GET("getspmoi.php")
    Observable<CategoryNewModel> getCateNew();
    // profile
    @GET("profile.php")
    Observable<AccountModel> getaccount();


    // post data loại category
    @POST("detail.php")
    @FormUrlEncoded
    Observable<CategoryNewModel> getCategory(
            @Field("page") int page,
            @Field("type") int type
    );

    // post customer register
    @POST("register.php")
    @FormUrlEncoded
    Observable<CustomerModel> register(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("phone") String phone
    );

    //post data login
        @POST("login.php")
        @FormUrlEncoded
        Observable<CustomerModel> login(
                @Field("email") String email,
                @Field("pass") String pass
        );

    // order sản phẩm
    @POST("order.php")
    @FormUrlEncoded
    Observable<CustomerModel> createOrder(
            @Field("iduser") int id,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("quantity") int quantity,
            @Field("total") String total,
            @Field("detail") String detail

            );

    //View Orders lịch sử đơn hàng
    @POST("vieworder.php")
    @FormUrlEncoded
    Observable<OrdersModel> viewOrders  (
            @Field("iduser") int id
    );

    //Search Category
    @POST("search.php")
    @FormUrlEncoded
    Observable<CategoryNewModel> search  (
            @Field("search") String search
    );



}
