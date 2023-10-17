package com.example.appfood.utils;

import com.example.appfood.model.Cart;
import com.example.appfood.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL="http:/192.168.0.152/appfood/";
    public  static List<Cart> manggiohang;
    public  static List<Cart> mangmuahang = new ArrayList<>();

    public  static Customer customer_current = new Customer();
}
