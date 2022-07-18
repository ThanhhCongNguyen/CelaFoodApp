package com.example.celafoodapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.celafoodapp.local.entity.CartContent;
import com.example.celafoodapp.local.entity.Food;
import com.example.celafoodapp.databinding.ItemCartBinding;
import com.example.celafoodapp.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartContent> carts;
    private Callback callback;

    public CartAdapter(Callback callback) {
        this.carts = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartContent cart = carts.get(position);
        if (cart != null) {
            int cartId = cart.getId();
            int foodId = cart.getFoodId();
            String foodName = cart.getFoodName();
            String price = cart.getPrice();
            String image = cart.getImage();
            String descriptionVN = cart.getDescriptionVN();
            String descriptionEN = cart.getDescriptionEN();
            int amount = cart.getAmount();
            Food food = new Food(cartId, foodId, foodName, price, image, descriptionVN, descriptionEN, amount);

            Glide.with(holder.itemView)
                    .load(image)
                    .into(holder.binding.imageView);

            holder.binding.foodName.setText(foodName);
            holder.binding.ingredient.setText("Ingredient: " + descriptionEN);
            holder.binding.amount.setText(String.valueOf(amount));
            holder.binding.price.setText(String.valueOf(Utility.pricing(cart)));

            holder.binding.minus.setOnClickListener(view -> {
                callback.minus(cart.getId(), cart.getAmount());
            });

            holder.binding.plus.setOnClickListener(view -> {
                callback.plus(cart.getId(), cart.getAmount());
            });

            holder.binding.cart.setOnClickListener(view -> {
                callback.checkout(cart.getAmount(), Utility.pricing(cart));
            });

            holder.itemView.setOnClickListener(view -> {
                callback.itemOnClick(food);
            });
        }
    }

    @Override
    public int getItemCount() {
        return carts != null ? carts.size() : 0;
    }

    public void setCarts(List<CartContent> carts) {
        if (carts != null) {
            this.carts = carts;
            notifyDataSetChanged();
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ItemCartBinding binding;

        public CartViewHolder(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void minus(int id, int amount);

        void plus(int id, int amount);

        void checkout(int amount, int price);

        void itemOnClick(Food food);
    }

//    private int pricing(CartContent cart) {
//        String temp = ((cart.getPrice().substring(0, cart.getPrice().length() - 1)));
//        String[] temp1 = temp.split(",");
//        return Integer.parseInt(temp1[0].concat(temp1[1])) * cart.getAmount();
//    }

    public void deleteItem(int position) {
        //  int recentDeletedItem = carts.get(position);
        int recentDeletedItemPosition = position;
        carts.remove(position);
        notifyItemRemoved(position);

    }

    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private CartAdapter cartAdapter;

        public SwipeToDeleteCallback(CartAdapter cartAdapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.cartAdapter = cartAdapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            cartAdapter.deleteItem(position);
        }
    }
}
