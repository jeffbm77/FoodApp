package com.example.food_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app.Activity.ListFoodActivity;
import com.example.food_app.Domain.Category;
import com.example.food_app.R;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {
    ArrayList<Category> items;
    Context context;

    public CategoryAdapter(ArrayList<Category> list) {
        this.items = list;
    }


    @NonNull
    @Override
    public CategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new viewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.titreCategory.setText(items.get(position).getName());

        float radius = 10f;
        View decorView = ((Activity) holder.itemView.getContext()).getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        holder.blurView.setupWith(rootView, new RenderScriptBlur(holder.itemView.getContext()))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);
        holder.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        holder.blurView.setClipToOutline(true);

        int drawableResourceId = context.getResources().getIdentifier(items.get(position).getImagePath(), "drawable", context.getPackageName());
        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);

       holder.itemView.setOnClickListener(v ->{
        Intent intent = new Intent(context, ListFoodActivity.class);
        intent.putExtra("CategoryId", items.get(position).getId());
        intent.putExtra("CategoryName", items.get(position).getName());
        context.startActivity(intent);
    });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView titreCategory;
        ImageView pic;
        BlurView blurView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titreCategory = itemView.findViewById(R.id.titreCategory);
            blurView = itemView.findViewById(R.id.blurview2);
            pic = itemView.findViewById(R.id.imageCategory);
        }
    }
}