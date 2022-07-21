package com.example.celafoodapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.celafoodapp.databinding.ItemCartBinding;
import com.example.celafoodapp.repository.local.entity.Cart;
import com.example.celafoodapp.repository.local.entity.CartContent;
import com.example.celafoodapp.repository.local.entity.Food;
import com.example.celafoodapp.repository.local.entity.Order;
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
        CartContent cartContent = carts.get(position);
        if (cartContent != null) {
            String userId = cartContent.getUserId();
            String cartId = cartContent.getId();
            int foodId = cartContent.getFoodId();
            String foodName = cartContent.getFoodName();
            String price = cartContent.getPrice();
            String image = cartContent.getImage();
            String descriptionVN = cartContent.getDescriptionVN();
            String descriptionEN = cartContent.getDescriptionEN();
            int amount = cartContent.getAmount();
            Food food = new Food(cartId, foodId, foodName, price, image, descriptionVN, descriptionEN, amount);
            Order order = new Order(userId, foodId, amount);

            Glide.with(holder.itemView)
                    .load(image)
                    .into(holder.binding.imageView);

            holder.binding.foodName.setText(foodName);
            holder.binding.ingredient.setText("Ingredient: " + descriptionEN);
            holder.binding.amount.setText(String.valueOf(amount));
            holder.binding.price.setText(String.valueOf(Utility.pricing(cartContent)));

            holder.binding.minus.setOnClickListener(view -> {
                callback.minus(cartContent.getId(), cartContent.getAmount());
            });

            holder.binding.plus.setOnClickListener(view -> {
                callback.plus(cartContent.getId(), cartContent.getAmount());
            });

            holder.binding.cart.setOnClickListener(view -> {
                callback.checkout(cartContent.getAmount(), Utility.pricing(cartContent), cartContent.getPrice(), order);
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
        void minus(String id, int amount);

        void plus(String id, int amount);

        void checkout(int amount, int totalPrice, String price, Order order);

        void itemOnClick(Food food);

        void deleteItem(Cart cart, int position);
    }

    public void deleteItem(Cart cart, int position) {
        callback.deleteItem(cart, position);
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
            CartContent cartContent = cartAdapter.carts.get(position);
            String cartId = cartContent.getId();
            String userId = cartContent.getUserId();
            int foodId = cartContent.getFoodId();
            int amount = cartContent.getAmount();
            Cart cart = new Cart(cartId, userId, foodId, amount);
            cartAdapter.deleteItem(cart, position);
        }
    }
}
