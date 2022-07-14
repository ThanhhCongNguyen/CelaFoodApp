package com.example.celafoodapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.celafoodapp.R;
import com.example.celafoodapp.database.entity.CartContent;
import com.example.celafoodapp.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartContent> carts;
    private Callback callback;

    public CartAdapter(Callback callback) {
        carts = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        if (carts != null) {
            CartContent cart = carts.get(position);

            Glide.with(holder.itemView)
                    .load(cart.getImage())
                    .placeholder(R.drawable.loading)
                    .into(holder.imageView);

            holder.foodName.setText(cart.getFoodName());
            holder.ingredient.setText("Ingredient: " + cart.getIngredient());
            holder.amount.setText(String.valueOf(cart.getAmount()));
            holder.price.setText(String.valueOf(Utility.pricing(cart)));

            holder.minus.setOnClickListener(view -> {
                callback.minus(cart.getId(), cart.getAmount());
            });

            holder.plus.setOnClickListener(view -> {
                callback.plus(cart.getId(), cart.getAmount());
            });
        }
    }

    public void setCarts(List<CartContent> carts) {
        if (carts != null) {
            this.carts = carts;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return carts != null ? carts.size() : 0;
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView foodName, ingredient, price, amount, minus, plus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            foodName = itemView.findViewById(R.id.foodName);
            ingredient = itemView.findViewById(R.id.ingredient);
            price = itemView.findViewById(R.id.price);
            amount = itemView.findViewById(R.id.amount);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
        }
    }

    public interface Callback {
        void minus(int id, int amount);

        void plus(int id, int amount);
    }

//    private int pricing(CartContent cart) {
//        String temp = ((cart.getPrice().substring(0, cart.getPrice().length() - 1)));
//        String[] temp1 = temp.split(",");
//        return Integer.parseInt(temp1[0].concat(temp1[1])) * cart.getAmount();
//    }
}
