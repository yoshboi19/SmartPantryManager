package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PANTRY = "pantry";
    public static final String COL_P_ID = "id";
    public static final String COL_P_NAME = "name";
    public static final String COL_P_QTY = "quantity";
    public static final String COL_P_UNIT = "unit";
    public static final String COL_P_EXPIRY = "expiry_date";

    // Recipes Table
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_R_ID = "id";
    public static final String COL_R_NAME = "name";
    public static final String COL_R_INGREDIENTS = "ingredients";
    public static final String COL_R_STEPS = "steps";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantry = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_P_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_P_NAME + " TEXT NOT NULL, " +
                COL_P_QTY + " REAL NOT NULL, " +
                COL_P_UNIT + " TEXT NOT NULL, " +
                COL_P_EXPIRY + " TEXT)";

        String createRecipes = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COL_R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_R_NAME + " TEXT NOT NULL, " +
                COL_R_INGREDIENTS + " TEXT NOT NULL, " +
                COL_R_STEPS + " TEXT NOT NULL)";

        db.execSQL(createPantry);
        db.execSQL(createRecipes);

        seedInitialData(db);
    }

    private void seedInitialData(SQLiteDatabase db) {
        insertRecipe(db, "Classic Scrambled Eggs", "Egg:2:pcs;Milk:30:ml;Butter:10:g",
                "1. Crack eggs into a bowl and whisk with milk.\n2. Melt butter in a non-stick pan over medium heat.\n3. Pour in egg mixture and stir gently until softly set.");
        insertRecipe(db, "Garlic Butter Pasta", "Pasta:200:g;Butter:30:g;Garlic:2:pcs",
                "1. Boil pasta in salted water until al dente.\n2. Mince garlic and sauté in melted butter for 2 minutes.\n3. Toss drained pasta in the garlic butter and serve.");
        insertRecipe(db, "Tomato & Onion Omelette", "Egg:2:pcs;Tomato:1:pcs;Onion:1:pcs;Oil:10:ml",
                "1. Dice tomato and onion finely.\n2. Whisk eggs in a bowl.\n3. Sauté onion and tomato in oil until soft, pour eggs over, and cook until set.");
        insertRecipe(db, "Simple Fried Rice", "Rice:200:g;Egg:2:pcs;Onion:1:pcs;Oil:15:ml;Soy Sauce:15:ml",
                "1. Fry chopped onion in oil until translucent.\n2. Push to side, scramble eggs in the pan.\n3. Add cooked rice and soy sauce, stir-fry on high heat for 3 minutes.");
        insertRecipe(db, "Grilled Cheese Toastie", "Bread:2:pcs;Cheese:50:g;Butter:15:g",
                "1. Butter the outside of both bread slices.\n2. Place sliced cheese between the unbuttered sides.\n3. Grill in a pan over medium heat until golden brown on both sides.");
        insertRecipe(db, "Mashed Potatoes", "Potato:400:g;Butter:30:g;Milk:50:ml",
                "1. Peel and cube potatoes, then boil in salted water for 15-20 minutes until tender.\n2. Drain water and mash thoroughly with butter and warm milk.");
        insertRecipe(db, "Homemade Pancakes", "Flour:150:g;Milk:250:ml;Egg:1:pcs;Butter:15:g",
                "1. Whisk flour, milk, and egg into a smooth batter.\n2. Melt a little butter in a hot pan.\n3. Pour ladlefuls of batter, cook until bubbles form, flip and cook until golden.");
        insertRecipe(db, "Tomato Bruschetta Toast", "Bread:2:pcs;Tomato:2:pcs;Garlic:1:pcs;Oil:10:ml",
                "1. Toast the bread slices.\n2. Rub peeled garlic clove over warm toast.\n3. Top with diced tomatoes drizzled with oil.");
        insertRecipe(db, "Cheesy Scrambled Eggs", "Egg:2:pcs;Cheese:40:g;Butter:10:g",
                "1. Beat eggs in a bowl.\n2. Cook in melted butter over low-medium heat.\n3. Fold in grated cheese right before removing from heat.");
        insertRecipe(db, "Crispy Home Fries", "Potato:300:g;Onion:1:pcs;Oil:20:ml",
                "1. Dice potatoes into small cubes and chop onion.\n2. Heat oil in a skillet and pan-fry potatoes for 12 minutes.\n3. Add onions and cook for another 5 minutes until crispy.");
        insertRecipe(db, "Garlic Bread", "Bread:4:pcs;Butter:40:g;Garlic:2:pcs",
                "1. Crush garlic and mix thoroughly with softened butter.\n2. Spread evenly onto bread slices.\n3. Bake or pan-toast until crisp and golden.");
        insertRecipe(db, "Quick Tomato Pasta", "Pasta:200:g;Tomato:3:pcs;Garlic:2:pcs;Onion:1:pcs;Oil:15:ml",
                "1. Boil pasta until tender.\n2. Sauté chopped onion and garlic in oil, then add diced tomatoes and simmer for 10 minutes into a sauce.\n3. Toss pasta with sauce.");
        insertRecipe(db, "Egg Fried Toast (French Toast)", "Bread:2:pcs;Egg:2:pcs;Milk:40:ml;Butter:15:g",
                "1. Beat eggs and milk together in a shallow plate.\n2. Soak bread slices on both sides.\n3. Fry in melted butter until golden brown.");
        insertRecipe(db, "Cheesy Baked Potato", "Potato:2:pcs;Cheese:60:g;Butter:20:g",
                "1. Prick potatoes and microwave or bake until tender inside.\n2. Slice open, fluff insides with butter, and top generously with cheese until melted.");
        insertRecipe(db, "Caramelized Onion Pasta", "Pasta:200:g;Onion:2:pcs;Butter:30:g;Cheese:40:g",
                "1. Slice onions thinly and cook slowly in butter for 15 minutes until golden brown.\n2. Boil pasta, drain, and toss with caramelized onions and cheese.");
        insertRecipe(db, "Simple Garlic Rice", "Rice:200:g;Garlic:3:pcs;Oil:15:ml",
                "1. Finely mince garlic and fry in oil until golden and fragrant.\n2. Toss in cooked rice and stir well for 3 minutes.");

        insertPantryItem(db, "Eggs", 6, "pcs", "2026-11-05");
        insertPantryItem(db, "Milk", 500, "ml", "2026-10-30");
        insertPantryItem(db, "Butter", 200, "g", "2026-12-01");
        insertPantryItem(db, "Bread", 4, "pcs", "2026-10-28");
    }

    private void insertRecipe(SQLiteDatabase db, String name, String ingredients, String steps) {
        ContentValues cv = new ContentValues();
        cv.put(COL_R_NAME, name);
        cv.put(COL_R_INGREDIENTS, ingredients);
        cv.put(COL_R_STEPS, steps);
        db.insert(TABLE_RECIPES, null, cv);
    }

    private void insertPantryItem(SQLiteDatabase db, String name, double qty, String unit, String expiry) {
        ContentValues cv = new ContentValues();
        cv.put(COL_P_NAME, name);
        cv.put(COL_P_QTY, qty);
        cv.put(COL_P_UNIT, unit);
        cv.put(COL_P_EXPIRY, expiry);
        db.insert(TABLE_PANTRY, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    public long addIngredient(String name, double qty, String unit, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_P_NAME, name.trim());
        cv.put(COL_P_QTY, qty);
        cv.put(COL_P_UNIT, unit);
        cv.put(COL_P_EXPIRY, expiry);
        return db.insert(TABLE_PANTRY, null, cv);
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PANTRY + " ORDER BY " + COL_P_NAME + " ASC", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Ingredient(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public int updateIngredient(int id, String name, double qty, String unit, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_P_NAME, name.trim());
        cv.put(COL_P_QTY, qty);
        cv.put(COL_P_UNIT, unit);
        cv.put(COL_P_EXPIRY, expiry);
        return db.update(TABLE_PANTRY, cv, COL_P_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteIngredient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PANTRY, COL_P_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPES, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Recipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}