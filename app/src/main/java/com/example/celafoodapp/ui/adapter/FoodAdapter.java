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
import com.example.celafoodapp.database.entity.Food;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        if (foods != null) {
            Food food = foods.get(position);
            holder.itemFoodName.setText(food.getFoodName());
            holder.itemFoodPrice.setText(food.getPrice());
            Glide.with(holder.itemView.getContext())
                    .load(food.getImage())
                    .placeholder(R.drawable.loading)
                    .into(holder.itemFoodImage);

            holder.itemView.setOnClickListener(view -> {
                callBack.onItemClick(food);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (foods != null) {
            return foods.size();
        }
        return 0;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemFoodImage;
        private TextView itemFoodName, itemFoodPrice;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            itemFoodImage = itemView.findViewById(R.id.itemFoodImage);
            itemFoodName = itemView.findViewById(R.id.itemFoodName);
            itemFoodPrice = itemView.findViewById(R.id.itemPriceText);
        }
    }

    public interface CallBack {
        void onItemClick(Food food);
    }
}
