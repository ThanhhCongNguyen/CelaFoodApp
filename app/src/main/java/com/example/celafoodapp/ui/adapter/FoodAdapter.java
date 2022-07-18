package com.example.celafoodapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.celafoodapp.local.entity.Food;
import com.example.celafoodapp.databinding.ItemFoodBinding;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foods;
    private CallBack callBack;

    public FoodAdapter(CallBack callBack) {
        foods = new ArrayList<>();
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foods.get(position);
        if (food != null) {
            holder.binding.itemFoodName.setText(food.getFoodName());
            holder.binding.itemPriceText.setText(food.getPrice());

            Glide.with(holder.itemView.getContext())
                    .load(food.getImage())
                    .into(holder.binding.itemFoodImage);

            holder.itemView.setOnClickListener(view -> {
                callBack.onItemClick(food);
            });
        }
    }

    @Override
    public int getItemCount() {
        return foods != null ? foods.size() : 0;
    }

    public void setFoods(List<Food> foods) {
        if (foods != null) {
            this.foods = foods;
            notifyDataSetChanged();
        }
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ItemFoodBinding binding;

        public FoodViewHolder(@NonNull ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CallBack {
        void onItemClick(Food food);
    }
}
