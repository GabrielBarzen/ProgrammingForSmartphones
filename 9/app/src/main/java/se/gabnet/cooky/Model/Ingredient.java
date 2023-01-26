package se.gabnet.cooky.Model;

public class Ingredient {
    private String text;
    private int amount;
    private boolean weight;
    private int number;

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
