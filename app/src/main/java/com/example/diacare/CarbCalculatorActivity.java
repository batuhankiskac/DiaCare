package com.example.diacare;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CarbCalculatorActivity extends AppCompatActivity {

    private static final String TAG = "CarbCalculatorActivity";

    private TextView textTotalCarbs;
    private TextView textSelectedFood;
    private TextView textNoFoods;
    private LinearLayout layoutFoodList;
    private double totalCarbs = 0.0;
    private List<FoodItem> consumedFoods;
    private FoodItem[] availableFoods;
    private FoodItem selectedFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_carb_calculator);

        textTotalCarbs = findViewById(R.id.textTotalCarbs);
        textSelectedFood = findViewById(R.id.textSelectedFood);
        textNoFoods = findViewById(R.id.textNoFoods);
        layoutFoodList = findViewById(R.id.layoutFoodList);

        Button buttonSelectFood = findViewById(R.id.buttonSelectFood);
        Button buttonAddFood = findViewById(R.id.buttonAddFood);
        Button buttonClear = findViewById(R.id.buttonClear);

        consumedFoods = new ArrayList<>();
        selectedFood = null;

        availableFoods = new FoodItem[]{
                new FoodItem("Apple (1 serving)", 15.0),
                new FoodItem("Banana (1 medium)", 27.0),
                new FoodItem("Bread (1 slice)", 12.5),
                new FoodItem("Rice (1 cup cooked)", 28.0),
                new FoodItem("Pasta (1 cup cooked)", 25.0),
                new FoodItem("Potato (1 medium)", 17.0),
                new FoodItem("Milk (1 glass)", 12.0),
                new FoodItem("Yogurt (1 cup)", 6.0),
                new FoodItem("Egg (1 large)", 0.6),
                new FoodItem("Chicken breast (100g)", 0.0),
                new FoodItem("Tomato (1 medium)", 3.9),
                new FoodItem("Cucumber (1 medium)", 3.6),
                new FoodItem("Orange (1 medium)", 12.0),
                new FoodItem("Grapes (1 cup)", 16.0),
                new FoodItem("Cheese (30g)", 0.4),
                new FoodItem("Butter (1 tbsp)", 0.0),
                new FoodItem("Honey (1 tbsp)", 17.0),
                new FoodItem("Sugar (1 tsp)", 4.0),
                new FoodItem("Corn (1 cup)", 19.0),
                new FoodItem("Carrot (1 medium)", 6.0)
        };

        buttonSelectFood.setOnClickListener(v -> showFoodSelectionDialog());
        buttonAddFood.setOnClickListener(v -> addSelectedFood());
        buttonClear.setOnClickListener(v -> clearCalculator());

        updateFoodListVisibility();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void showFoodSelectionDialog() {
        String[] foodNames = new String[availableFoods.length];
        for (int i = 0; i < availableFoods.length; i++) {
            foodNames[i] = availableFoods[i].toString();
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.select_food)
                .setItems(foodNames, (dialog, which) -> {
                    selectedFood = availableFoods[which];
                    textSelectedFood.setText(getString(R.string.selected_food_label, selectedFood.getName()));
                    textSelectedFood.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void addSelectedFood() {
        if (selectedFood == null) {
            Toast.makeText(this, R.string.please_select_food, Toast.LENGTH_SHORT).show();
            return;
        }

        FoodItem foodToAdd = new FoodItem(selectedFood.getName(), selectedFood.getCarbsPerGram());
        addFood(foodToAdd);

        textSelectedFood.setText(R.string.no_food_selected);
        textSelectedFood.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
        selectedFood = null;
    }

    private void addFood(FoodItem food) {
        consumedFoods.add(food);
        totalCarbs += food.getCarbsPerGram();
        textTotalCarbs.setText(getString(R.string.total_carbs_format, totalCarbs));

        addFoodToListView(food);
        updateFoodListVisibility();

        Toast.makeText(this, getString(R.string.food_added, food.getName(), food.getCarbsPerGram()), Toast.LENGTH_SHORT).show();
    }

    private void addFoodToListView(FoodItem food) {
        TextView foodView = new TextView(this);
        foodView.setText("• " + food.toString());
        foodView.setTextSize(16);
        foodView.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        foodView.setPadding(16, 12, 16, 12);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 4);
        foodView.setLayoutParams(params);

        layoutFoodList.addView(foodView);
    }

    private void updateFoodListVisibility() {
        if (consumedFoods.isEmpty()) {
            textNoFoods.setVisibility(View.VISIBLE);
            layoutFoodList.setVisibility(View.GONE);
        } else {
            textNoFoods.setVisibility(View.GONE);
            layoutFoodList.setVisibility(View.VISIBLE);
        }
    }

    private void clearCalculator() {
        consumedFoods.clear();
        totalCarbs = 0.0;
        textTotalCarbs.setText(getString(R.string.total_carbs_format, 0.0));
        layoutFoodList.removeAllViews();
        selectedFood = null;
        textSelectedFood.setText(R.string.no_food_selected);
        textSelectedFood.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
        updateFoodListVisibility();
    }
}
