package com.example.diacare;

public class BloodSugar {
    private int sugarLevel;
    private String date;
    private String mealType;

    public BloodSugar(int sugarLevel, String date, String mealType) {
        setSugarLevel(sugarLevel);
        this.date = date;
        this.mealType = mealType;
    }

    public int getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(int sugarLevel) {
        if (sugarLevel < 0) {
            throw new IllegalArgumentException("Blood sugar cannot be less than 0!");
        }
        this.sugarLevel = sugarLevel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}