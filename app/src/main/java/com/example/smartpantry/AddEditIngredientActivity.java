package com.example.smartpantry;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etName, etQty, etExpiry;
    private Spinner spUnit;
    private DatabaseHelper dbHelper;
    private int ingredientId = -1;
    private final String[] units = {"pcs", "g", "kg", "ml", "l", "tbsp", "tsp", "cup"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        dbHelper = new DatabaseHelper(this);
        TextView tvTitle = findViewById(R.id.tvFormTitle);
        etName = findViewById(R.id.etIngredientName);
        etQty = findViewById(R.id.etIngredientQty);
        etExpiry = findViewById(R.id.etExpiryDate);
        spUnit = findViewById(R.id.spUnit);
        Button btnSave = findViewById(R.id.btnSaveIngredient);
        Button btnDelete = findViewById(R.id.btnDeleteIngredient);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        spUnit.setAdapter(unitAdapter);

        if (getIntent() != null && getIntent().hasExtra("ID")) {
            ingredientId = getIntent().getIntExtra("ID", -1);
            tvTitle.setText("Edit Pantry Ingredient");
            etName.setText(getIntent().getStringExtra("NAME"));
            etQty.setText(String.valueOf(getIntent().getDoubleExtra("QTY", 1.0)));
            etExpiry.setText(getIntent().getStringExtra("EXPIRY"));
            String unit = getIntent().getStringExtra("UNIT");
            for (int i = 0; i < units.length; i++) {
                if (units[i].equalsIgnoreCase(unit)) {
                    spUnit.setSelection(i);
                    break;
                }
            }
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnSave.setOnClickListener(v -> saveIngredient());
        btnDelete.setOnClickListener(v -> {
            if (ingredientId != -1) {
                dbHelper.deleteIngredient(ingredientId);
                Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveIngredient() {
        String name = etName.getText().toString().trim();
        String qtyStr = etQty.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();
        String unit = spUnit.getSelectedItem().toString();

        // Input validation required by Section 3.1
        if (name.isEmpty()) {
            etName.setError("Ingredient name is required");
            etName.requestFocus();
            return;
        }
        if (qtyStr.isEmpty()) {
            etQty.setError("Quantity is required");
            etQty.requestFocus();
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
            if (qty <= 0) {
                etQty.setError("Quantity must be greater than 0");
                etQty.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etQty.setError("Enter a valid number");
            return;
        }

        if (ingredientId == -1) {
            dbHelper.addIngredient(name, qty, unit, expiry);
            Toast.makeText(this, "Ingredient added!", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.updateIngredient(ingredientId, name, qty, unit, expiry);
            Toast.makeText(this, "Ingredient updated!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}