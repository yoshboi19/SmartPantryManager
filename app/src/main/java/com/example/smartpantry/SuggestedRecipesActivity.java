package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView rvRecipes;
    private TextView tvZeroFeedback;
    private boolean showStrictOnly = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        dbHelper = new DatabaseHelper(this);
        rvRecipes = findViewById(R.id.rvSuggestedRecipes);
        tvZeroFeedback = findViewById(R.id.tvZeroMatchFeedback);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        Button btnStrict = findViewById(R.id.btnStrictMode);
        Button btnAlmost = findViewById(R.id.btnAlmostThereMode);

        btnStrict.setOnClickListener(v -> {
            showStrictOnly = true;
            filterAndDisplayRecipes();
        });

        btnAlmost.setOnClickListener(v -> {
            showStrictOnly = false;
            filterAndDisplayRecipes();
        });

        filterAndDisplayRecipes();
    }

    private void filterAndDisplayRecipes() {
        List<Ingredient> pantry = dbHelper.getAllIngredients();
        List<Recipe> allRecipes = dbHelper.getAllRecipes();
        List<Recipe> filtered = new ArrayList<>();

        for (Recipe r : allRecipes) {
            int missing = MatchingUtils.countMissingIngredients(r, pantry);
            r.setMissingCount(missing);
            if (showStrictOnly && missing == 0) {
                filtered.add(r);
            } else if (!showStrictOnly && missing == 1) {
                filtered.add(r);
            }
        }

        if (filtered.isEmpty()) {
            tvZeroFeedback.setVisibility(View.VISIBLE);
            rvRecipes.setVisibility(View.GONE);
            if (showStrictOnly) {
                tvZeroFeedback.setText("No recipes match your pantry yet - add more ingredients!");
            } else {
                tvZeroFeedback.setText("No 'Almost There' recipes missing exactly 1 ingredient right now.");
            }
        } else {
            tvZeroFeedback.setVisibility(View.GONE);
            rvRecipes.setVisibility(View.VISIBLE);
            RecipeAdapter adapter = new RecipeAdapter(filtered, recipe -> {
                Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
                intent.putExtra("NAME", recipe.getName());
                intent.putExtra("INGREDIENTS", recipe.getIngredientsRaw());
                intent.putExtra("STEPS", recipe.getSteps());
                startActivity(intent);
            });
            rvRecipes.setAdapter(adapter);
        }
    }
}
