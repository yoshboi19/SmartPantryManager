package com.example.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvIngredients = findViewById(R.id.tvDetailIngredients);
        TextView tvSteps = findViewById(R.id.tvDetailSteps);
        Button btnBack = findViewById(R.id.btnBackToRecipes);

        if (getIntent() != null) {
            tvTitle.setText(getIntent().getStringExtra("NAME"));
            String rawIng = getIntent().getStringExtra("INGREDIENTS");
            tvIngredients.setText(MatchingUtils.formatIngredientsForDisplay(rawIng));
            tvSteps.setText(getIntent().getStringExtra("STEPS"));
        }

        btnBack.setOnClickListener(v -> finish());
    }
}
