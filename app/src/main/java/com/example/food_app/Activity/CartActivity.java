package com.example.food_app.Activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.Adapter.CartAdapter;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.databinding.ActivityCartBinding;

import eightbitlab.com.blurview.RenderScriptBlur;

public class CartActivity extends BaseActivity {

    private @NonNull ActivityCartBinding binding;
    private RecyclerView recyclerView;
    private ManagmentCart managmentCart;
    private double tax;
    CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariables();
        calculateTotalPrice();
        initList();


    }
    private void setBlurEffect() {

        float radius = 10f;
        View decorView = (this).getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        binding.blurViewCart.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);

        binding.blurViewCart.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurViewCart.setClipToOutline(true);

        binding.blurview2.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);

        binding.blurview2.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurview2.setClipToOutline(true);
    }

    private void initList() {
        if (managmentCart.getListCart().isEmpty())
        {binding.empty.setVisibility(View.VISIBLE);
        binding.scrollView.setVisibility(View.GONE);
        }else {
            binding.empty.setVisibility(View.GONE);
            binding.scrollView.setVisibility(View.VISIBLE);
        }
        binding.recyclerViewCartFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
         adapter = new CartAdapter(managmentCart.getListCart(), CartActivity.this, () -> calculateTotalPrice());
        binding.recyclerViewCartFood.setAdapter(adapter);


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