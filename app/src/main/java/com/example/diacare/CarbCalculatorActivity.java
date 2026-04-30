package com.example.diacare;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CarbCalculatorActivity extends AppCompatActivity {

    private TextView textTotalCarbs;
    private double totalCarbs = 0.0;
    private List<FoodItem> consumedFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carb_calculator);

        textTotalCarbs = findViewById(R.id.textTotalCarbs);
        Button buttonAddApple = findViewById(R.id.buttonAddApple);
        Button buttonAddBread = findViewById(R.id.buttonAddBread);
        Button buttonClear = findViewById(R.id.buttonClear);

        consumedFoods = new ArrayList<>();

        buttonAddApple.setOnClickListener(v -> addFood(new FoodItem("1 Serving Apple", 15.0)));
        buttonAddBread.setOnClickListener(v -> addFood(new FoodItem("1 Slice Whole Wheat Bread", 12.5)));
        buttonClear.setOnClickListener(v -> clearCalculator());
    }

    private void addFood(FoodItem food) {
        consumedFoods.add(food);
        totalCarbs += food.getCarbsPerGram();
        textTotalCarbs.setText(getString(R.string.total_carbs_format, totalCarbs));
    }

    private void clearCalculator() {
        consumedFoods.clear();
        totalCarbs = 0.0;
        textTotalCarbs.setText(getString(R.string.total_carbs_format, 0.0));
    }
}