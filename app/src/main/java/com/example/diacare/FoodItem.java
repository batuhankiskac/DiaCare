package com.example.diacare;

public class FoodItem {
    private String name;
    private double carbsPerGram;

    public FoodItem(String name, double carbsPerGram) {
        this.name = name;
        setCarbsPerGram(carbsPerGram);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCarbsPerGram() {
        return carbsPerGram;
    }

    public void setCarbsPerGram(double carbsPerGram) {
        if (carbsPerGram < 0.0) {
            throw new IllegalArgumentException("Carbohydrate value cannot be negative!");
        }
        this.carbsPerGram = carbsPerGram;
    }

    @Override
    public String toString() {
        return name + " - " + carbsPerGram + "g";
    }
}