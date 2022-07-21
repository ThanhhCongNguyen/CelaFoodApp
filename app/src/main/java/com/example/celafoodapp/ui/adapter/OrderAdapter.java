package com.example.celafoodapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.celafoodapp.databinding.ItemOrderedBinding;
import com.example.celafoodapp.repository.local.entity.Food;
import com.example.celafoodapp.repository.local.entity.OrderContent;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<OrderContent> orderContents;
    private Callback callback;

    public OrderAdapter(Callback callback) {
        this.orderContents = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderedBinding binding = ItemOrderedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderContent orderContent = orderContents.get(position);
        if (orderContent != null) {
            int foodId = orderContent.getFoodId();
            String foodName = orderContent.getFoodName();
            String price = orderContent.getPrice();
            String image = orderContent.getImage();
            String descriptionVN = orderContent.getDescriptionVN();
            String descriptionEN = orderContent.getDescriptionEN();
            Food food = new Food(foodId, foodName, price, image, descriptionVN, descriptionEN, 1);

            holder.binding.foodName.setText(foodName);
            holder.binding.ingredient.setText(descriptionEN);
            holder.binding.price.setText(price);
            holder.binding.amount.setText(String.valueOf(orderContent.getAmount()));

            Glide.with(holder.itemView.getContext())
                    .load(orderContent.getImage())
                    .into(holder.binding.imageView);

            holder.itemView.setOnClickListener(view -> {
                callback.onItemClick(food);
            });

        }
    }

    @Override
    public int getItemCount() {
        return orderContents != null ? orderContents.size() : 0;
    }

    public void setOrderContents(List<OrderContent> orderContents) {
        this.orderContents = orderContents;
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private ItemOrderedBinding binding;

        public OrderViewHolder(@NonNull ItemOrderedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onItemClick(Food food);
    }
}
