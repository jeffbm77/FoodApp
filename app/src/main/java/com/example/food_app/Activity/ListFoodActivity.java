package com.example.food_app.Activity;

import static com.google.firebase.database.ktx.DatabaseKt.database;
import static com.google.firebase.database.ktx.DatabaseKt.getDatabase;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.Adapter.ListFoodAdapter;
import com.example.food_app.Domain.Foods;
import com.example.food_app.R;
import com.example.food_app.databinding.ActivityListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodActivity extends BaseActivity {

    private ActivityListFoodBinding binding;
    private RecyclerView.Adapter adapterListFood;

    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityListFoodBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getIntentExtra();
        initlistFood();

    }

    private void initlistFood() {
        DatabaseReference myref = db.getReference("Foods");
        binding.progressBarListFood.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query;
        if (isSearch) {
            query = myref.orderByChild("Title").startAt(searchText).endAt(searchText + "\uf8ff");
        } else {
            query = myref.orderByChild("CategoryId").equalTo(categoryId);

        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        list.add(issue.getValue(Foods.class));
                    }
                    if (!list.isEmpty()) {
                        binding.recyclerViewListFood.setLayoutManager(new GridLayoutManager(ListFoodActivity.this, 2));
                        adapterListFood = new ListFoodAdapter(list);
                        binding.recyclerViewListFood.setAdapter(adapterListFood);
                    }
                    binding.progressBarListFood.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId", 0);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("search");
        isSearch = getIntent().getBooleanExtra("isSearch",false);

        binding.listTitreFood.setText(categoryName);
        binding.listBtnBack.setOnClickListener(v -> finish());

    }

}