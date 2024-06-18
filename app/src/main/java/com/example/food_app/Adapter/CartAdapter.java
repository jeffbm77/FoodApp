package com.example.food_app.Adapter;

import android.app.Activity;
import android.content.Context;
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
import com.example.food_app.Domain.Foods;
import com.example.food_app.Helper.ChangeNumberItemsListener;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.R;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {

    ArrayList<Foods> ListItemsSelected;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<Foods> listItemsSelected,Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        ListItemsSelected = listItemsSelected;
        this.managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewHolder holder, int position) {



        float radius = 10f;
        View decorView = ((Activity) holder.itemView.getContext()).getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        holder.blurView.setupWith(rootView, new RenderScriptBlur(holder.itemView.getContext())).setFrameClearDrawable(windowBackground).setBlurRadius(radius);
        holder.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        holder.blurView.setClipToOutline(true);


        // recuperation de image de la base de données et affichage dans viewholder_best_food.image_food

        Glide.with(holder.itemView.getContext()).load(ListItemsSelected.get(position)
                 .getImagePath())
                 .transform(new CenterCrop(),
                 new RoundedCorners(30))
                .into(holder.pic);
        holder.titleTxt.setText(ListItemsSelected.get(position).getTitle());
        holder.priceTxt.setText("$" + ListItemsSelected.get(position).getPrice());
        holder.numberItemsTxt.setText(ListItemsSelected.get(position).getNumberInCart() + "+$"+
                (ListItemsSelected.get(position).getNumberInCart()*ListItemsSelected.get(position).getPrice()));

        holder.numberItemsTxt.setText(String.valueOf(ListItemsSelected.get(position).getNumberInCart()));

        holder.btnPlus.setOnClickListener(v ->
            managmentCart.plusNumberItem(ListItemsSelected, position, () -> {
                changeNumberItemsListener.change();
                notifyDataSetChanged();

        }));

        holder.btnMoin.setOnClickListener(v ->
            managmentCart.minusNumberItem(ListItemsSelected, position, () -> {
                changeNumberItemsListener.change();
                notifyDataSetChanged();
        }));

    }

    @Override
    public int getItemCount() {
        return ListItemsSelected.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView titleTxt, priceTxt, numberItemsTxt, totalPriceTxt, btnPlus, btnMoin;
        ImageView pic;
        BlurView blurView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.cart_Titre_Food);
            priceTxt = itemView.findViewById(R.id.cart_Price_Food);
            numberItemsTxt = itemView.findViewById(R.id.cart_Number_Food);
            totalPriceTxt = itemView.findViewById(R.id.cart_Total_Price_Food);
            btnPlus = itemView.findViewById(R.id.cart_Btn_Pus);
            btnMoin = itemView.findViewById(R.id.cart_Btn_Pus);
            pic = itemView.findViewById(R.id.cart_Image_Food);
            blurView = itemView.findViewById(R.id.blurView_cart_holder);
        }
    }
}
