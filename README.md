# Smart Pantry Manager

## App Description
**Smart Pantry Manager** is a native Android application written in Java designed to reduce household food waste. It tracks the user's current pantry ingredients (quantities, units, and expiry dates) and runs a **Strict-Matching Engine** against a pre-seeded database of 16 recipes. A recipe is only suggested in the main view if the user possesses 100% of the required ingredients in at least the required quantities. It also handles plural/singular normalization (e.g., `tomato` vs `tomatoes`) and standard metric conversions (`kg` to `g`, `l` to `ml`).

## Database Choice & Justification
* **Database Used:** SQLite (`SQLiteOpenHelper`)
* **Why SQLite was chosen:** Pantry tracking and recipe matching are offline-first tasks that require fast, zero-latency relational queries on the user's mobile device. Using native Android `SQLiteOpenHelper` guarantees full ACID compliance, persistent local storage across app restarts, and zero dependency on external cloud servers or internet connectivity.

## Core Features
1. **Full Pantry CRUD:** Create, Read, Update, and Delete pantry items with form validation.
2. **16 Pre-Seeded Leftover Recipes:** Automatically populated into SQLite on first run.
3. **Strict-Matching Algorithm:** Excludes partial matches from the main suggestions list, with an optional "Almost There (Missing 1)" filter.
4. **5 Distinct Activities:** `MainActivity`, `AddEditIngredientActivity`, `SuggestedRecipesActivity`, `RecipeDetailActivity`, and `SettingsActivity`.

## Setup & Run Instructions
1. Clone this repository:
   ```bash
   git clone [https://github.com/YOUR_USERNAME/SmartPantryManager.git](https://github.com/YOUR_USERNAME/SmartPantryManager.git)