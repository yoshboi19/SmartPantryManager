package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView rvPantry;
    private TextView tvEmptyPantry, tvPantrySummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        rvPantry = findViewById(R.id.rvPantry);
        tvEmptyPantry = findViewById(R.id.tvEmptyPantry);
        tvPantrySummary = findViewById(R.id.tvPantrySummary);
        rvPantry.setLayoutManager(new LinearLayoutManager(this));

        Button btnAdd = findViewById(R.id.btnAddIngredient);
        Button btnRecipes = findViewById(R.id.btnSuggestedRecipes);
        Button btnSettings = findViewById(R.id.btnSettings);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });

        btnRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {
        List<Ingredient> items = dbHelper.getAllIngredients();
        tvPantrySummary.setText("Total Pantry Items: " + items.size() + " (Tap an item to edit/delete)");
        if (items.isEmpty()) {
            tvEmptyPantry.setVisibility(View.VISIBLE);
            rvPantry.setVisibility(View.GONE);
        } else {
            tvEmptyPantry.setVisibility(View.GONE);
            rvPantry.setVisibility(View.VISIBLE);
            PantryAdapter adapter = new PantryAdapter(items, ingredient -> {
                Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
                intent.putExtra("ID", ingredient.getId());
                intent.putExtra("NAME", ingredient.getName());
                intent.putExtra("QTY", ingredient.getQuantity());
                intent.putExtra("UNIT", ingredient.getUnit());
                intent.putExtra("EXPIRY", ingredient.getExpiryDate());
                startActivity(intent);
            });
            rvPantry.setAdapter(adapter);
        }
    }
}