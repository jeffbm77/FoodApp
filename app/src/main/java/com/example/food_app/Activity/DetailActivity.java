package com.example.food_app.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.food_app.Domain.Foods;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.R;
import com.example.food_app.databinding.ActivityDetailBinding;
import com.example.food_app.databinding.ActivityMainBinding;

import eightbitlab.com.blurview.RenderScriptBlur;

public class DetailActivity extends BaseActivity {
    private ActivityDetailBinding binding;
    private Foods object;
    private int number = 0;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        managmentCart = new ManagmentCart(this);
        getBundleExtra();
        setVariable();
        setBlurEffect();

    }

    private void setBlurEffect() {

        float radius = 10f;
        View decorView = (this).getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        binding.blurview.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);

        binding.blurview.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurview.setClipToOutline(true);

        binding.blurview2.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);

        binding.blurview2.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurview2.setClipToOutline(true);
    }

    @SuppressLint("SetTextI18n")
    private void setVariable() {

        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.detailImage);

        binding.detailPriceFood.setText("$" + object.getPrice());
        binding.detailTitreFood.setText(object.getTitle());
        binding.detailDescriptionFood.setText(object.getDescription());
        binding.detailNumberStart.setText(object.getStar() + "Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.detailTotalPriceFood.setText((object.getPrice() * number) + "$");



        binding.detailBtnPus.setOnClickListener(v -> {
            number++;
            binding.detailNumberFood.setText(number + "");
            binding.detailTotalPriceFood.setText((object.getPrice() * number) + "$");
        });

        binding.detailBtnMoin.setOnClickListener(v -> {
            if (number > 1) {
                number--;
                binding.detailNumberFood.setText(number + "");
                binding.detailTotalPriceFood.setText((object.getPrice() * number) + "$");
            } else if (number == 1) {
                binding.detailNumberFood.setText("0");
                binding.detailTotalPriceFood.setText( "$0");
                number = 0;


            }
        });

        binding.detailBtnBack.setOnClickListener(v -> {
            finish();
        });

        binding.detailAddBtnFood.setOnClickListener(v -> {
            object.setNumberInCart(number);
            managmentCart.insertFood(object);
        });

    }

    private void getBundleExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}