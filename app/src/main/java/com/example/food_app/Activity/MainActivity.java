package com.example.food_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.Adapter.BestFoodAdapter;
import com.example.food_app.Adapter.CategoryAdapter;
import com.example.food_app.Domain.Category;
import com.example.food_app.Domain.Foods;
import com.example.food_app.Domain.Location;
import com.example.food_app.Domain.Price;
import com.example.food_app.Domain.Time;
import com.example.food_app.R;
import com.example.food_app.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends  BaseActivity{
    private ActivityMainBinding binding;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initLocation();
        initTime();
        initPrice();
        initBestFoods();
        initCategory();
        setVariable();
    }

    private void setVariable() {
        binding.btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        //
        binding.searchBtn.setOnClickListener(v -> {
            String search = binding.editSearchText.getText().toString();
            if (!search.isEmpty()){
                Intent intent = new Intent(MainActivity.this, ListFoodActivity.class);
                intent.putExtra("search", search);
                intent.putExtra("isSearch", true);
                startActivity(intent);
            }


        });

    }

    private void initCategory() {
        DatabaseReference myRef = db.getReference("Category");
        binding.progressBarCategorie.setVisibility(View.VISIBLE);
        ArrayList<Category> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot issue : dataSnapshot.getChildren()){
                        list.add(issue.getValue(Category.class));
                    }
                    if (list.size() > 0){
                        RecyclerView.Adapter adapter = new CategoryAdapter(list);
                        binding.recyclerViewCategory.setAdapter(adapter);
                        //binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

                    }
                    binding.progressBarCategorie.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());

            }
        });
    }

    private void initBestFoods() {
        DatabaseReference myRef = db.getReference("Foods");
        binding.progressBarbestFood.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issu : dataSnapshot.getChildren()) {
                        list.add(issu.getValue(Foods.class));
                    }
                    if (!list.isEmpty()) {
                        RecyclerView.Adapter  adapter = new BestFoodAdapter(list);
                        binding.recyclerViewbestFood.setAdapter(adapter);
                    }
                    binding.progressBarbestFood.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());

            }

        });
    }



    private void  initLocation() {
        DatabaseReference myRef = db.getReference("Location");
        ArrayList<Location> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   for (DataSnapshot issue : dataSnapshot.getChildren()){
                       list.add(issue.getValue(Location.class));
                   }
                       ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
                       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       binding.locationSP.setAdapter(adapter);
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());

            }
        });
    }
    private void initTime() {
        DatabaseReference myRef = db.getReference("Time");
        ArrayList<Time> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   for (DataSnapshot issue : dataSnapshot.getChildren()){
                       list.add(issue.getValue(Time.class));
                   }
                       ArrayAdapter<Time> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
                       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       binding.timeSP.setAdapter(adapter);
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());

            }
        });
    }
    private void initPrice() {
        DatabaseReference myRef = db.getReference("Price");
        ArrayList<Price> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   for (DataSnapshot issue : dataSnapshot.getChildren()){
                       list.add(issue.getValue(Price.class));
                   }
                       ArrayAdapter<Price> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
                       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       binding.prixSP.setAdapter(adapter);
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());

            }
        });
    }
}
