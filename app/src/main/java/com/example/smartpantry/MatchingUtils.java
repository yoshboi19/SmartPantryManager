package com.example.smartpantry;

import java.util.List;

public class MatchingUtils {

    public static String normalizeName(String raw) {
        if (raw == null) return "";
        String s = raw.trim().toLowerCase();
        if (s.endsWith("ies") && s.length() > 4) {
            return s.substring(0, s.length() - 3) + "y";
        } else if (s.endsWith("oes") && s.length() > 4) {
            return s.substring(0, s.length() - 2);
        } else if (s.endsWith("es") && (s.endsWith("ches") || s.endsWith("shes") || s.endsWith("xes"))) {
            return s.substring(0, s.length() - 2);
        } else if (s.endsWith("s") && !s.endsWith("ss") && s.length() > 3) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    public static double toBaseQuantity(double qty, String unit) {
        if (unit == null) return qty;
        String u = unit.trim().toLowerCase();
        switch (u) {
            case "kg": return qty * 1000.0;
            case "l": return qty * 1000.0;
            case "tbsp": return qty * 15.0;
            case "tsp": return qty * 5.0;
            case "cup":
            case "cups": return qty * 250.0;
            default: return qty; // g, ml, pcs
        }
    }

    public static String getBaseUnitCategory(String unit) {
        if (unit == null) return "pcs";
        String u = unit.trim().toLowerCase();
        if (u.equals("g") || u.equals("kg")) return "weight";
        if (u.equals("ml") || u.equals("l") || u.equals("tbsp") || u.equals("tsp") || u.startsWith("cup")) return "volume";
        return "pcs";
    }

    public static int countMissingIngredients(Recipe recipe, List<Ingredient> pantryList) {
        String raw = recipe.getIngredientsRaw();
        if (raw == null || raw.trim().isEmpty()) return 0;

        String[] reqItems = raw.split(";");
        int missing = 0;

        for (String req : reqItems) {
            String[] parts = req.split(":");
            if (parts.length < 3) continue;

            String reqName = normalizeName(parts[0]);
            double reqQty = Double.parseDouble(parts[1]);
            String reqUnit = parts[2];

            double reqBaseQty = toBaseQuantity(reqQty, reqUnit);
            String reqCategory = getBaseUnitCategory(reqUnit);

            double totalAvailableBaseQty = 0;
            for (Ingredient p : pantryList) {
                if (normalizeName(p.getName()).equals(reqName)) {
                    String pCategory = getBaseUnitCategory(p.getUnit());
                    if (pCategory.equals(reqCategory) || pCategory.equals("pcs") || reqCategory.equals("pcs")) {
                        totalAvailableBaseQty += toBaseQuantity(p.getQuantity(), p.getUnit());
                    }
                }
            }

            if (totalAvailableBaseQty < reqBaseQty) {
                missing++;
            }
        }
        return missing;
    }

    public static String formatIngredientsForDisplay(String raw) {
        StringBuilder sb = new StringBuilder();
        String[] items = raw.split(";");
        for (String item : items) {
            String[] parts = item.split(":");
            if (parts.length == 3) {
                sb.append("• ").append(parts[1]).append(" ").append(parts[2]).append(" ").append(parts[0]).append("\n");
            }
        }
        return sb.toString().trim();
    }
}