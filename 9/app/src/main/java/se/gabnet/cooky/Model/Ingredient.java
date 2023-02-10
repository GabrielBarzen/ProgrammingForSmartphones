package se.gabnet.cooky.Model;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private String text;
    private int amount;
    private boolean weight;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isWeight() {
        return weight;
    }

    public void setWeight(boolean weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "text='" + text + '\'' +
                ", amount=" + amount +
                ", weight=" + weight +
                '}';
    }
}
