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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.food_app.Activity.DetailActivity;
import com.example.food_app.Domain.Foods;
import com.example.food_app.R;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.viewholder> {
    ArrayList<Foods> items;
    Context context;

    public ListFoodAdapter(ArrayList<Foods> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ListFoodAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_list_food, parent, false);
        return new viewholder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText("$" + items.get(position).getPrice());
        holder.timeTxt.setText(items.get(position).getTimeValue() + "mn");
        holder.starTxt.setText("" + items.get(position).getStar());



        float radius = 10f;
        View decorView = ((Activity) holder.itemView.getContext()).getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        holder.blurView.setupWith(rootView, new RenderScriptBlur(holder.itemView.getContext())).setFrameClearDrawable(windowBackground).setBlurRadius(radius);
        holder.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        holder.blurView.setClipToOutline(true);

        Glide.with(context).load(items.get(position)
                .getImagePath())
                .transform( new RoundedCorners(30))
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, starTxt, timeTxt, categoryTxt;
        ImageView pic;
        BlurView blurView;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.list_titre_Food_holder);
            priceTxt = itemView.findViewById(R.id.list_Price_Food_holder);
            starTxt = itemView.findViewById(R.id.list_Start_Food_holder);
            timeTxt = itemView.findViewById(R.id.list_Time_Food_holder);
            pic = itemView.findViewById(R.id.list_Image_food_holder);
            blurView = itemView.findViewById(R.id.blurview_holder);
            categoryTxt = itemView.findViewById(R.id.list_Titre_Food);
        }
    }

}
