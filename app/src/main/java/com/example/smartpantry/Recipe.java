package com.example.smartpantry;

public class Recipe {
    private int id;
    private String name;
    private String ingredientsRaw; // Format: "egg:2:pcs,milk:100:ml"
    private String steps;
    private int missingCount; // 0 = Strict Match, 1 = Almost There

    public Recipe(int id, String name, String ingredientsRaw, String steps) {
        this.id = id;
        this.name = name;
        this.ingredientsRaw = ingredientsRaw;
        this.steps = steps;
        this.missingCount = 0;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getIngredientsRaw() { return ingredientsRaw; }
    public String getSteps() { return steps; }
    public int getMissingCount() { return missingCount; }
    public void setMissingCount(int missingCount) { this.missingCount = missingCount; }
}
