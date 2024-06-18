package com.example.food_app.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {

    private @NonNull ActivityCartBinding binding;
    private RecyclerView recyclerView;
    private ManagmentCart managmentCart;
    private double tax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariables();
        calculateTotalPrice();
        initList();


    }

    private void initList() {

    }

    private void setVariables() {
        binding.cartBtnBack.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void calculateTotalPrice() {
        double pourcentageTax = 0.02;
        double livraison = 10;
        tax = Math.round((managmentCart.getTotalFee() * pourcentageTax) * 100.0) / 100.0;
        double total = Math.round((managmentCart.getTotalFee() + tax + livraison) * 100.0) / 100.0;
        double subTotal = Math.round((managmentCart.getTotalFee()) * 100.0) / 100.0;

        binding.totaFood.setText("$" + total);
        binding.total.setText("$" + subTotal);
        binding.tax.setText("$" + tax);
        binding.livreson.setText("$" + livraison);


    }
}