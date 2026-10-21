package com.example.smartpantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Ingredient ingredient);
    }

    private final List<Ingredient> ingredients;
    private final OnItemClickListener listener;

    public PantryAdapter(List<Ingredient> ingredients, OnItemClickListener listener) {
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient item = ingredients.get(position);
        holder.tvName.setText(item.getName());
        String expiryText = (item.getExpiryDate() == null || item.getExpiryDate().isEmpty())
                ? "No expiry set" : "Exp: " + item.getExpiryDate();
        holder.tvDetails.setText("Qty: " + item.getQuantity() + " " + item.getUnit() + "  |  " + expiryText);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetails;
        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvIngredientName);
            tvDetails = itemView.findViewById(R.id.tvIngredientDetails);
        }
    }
}