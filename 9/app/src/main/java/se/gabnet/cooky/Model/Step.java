package se.gabnet.cooky.Model;

import java.io.Serializable;

public class Step implements Serializable {
private String text;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Step{" +
                "text='" + text + '\'' +
                '}';
    }
}
