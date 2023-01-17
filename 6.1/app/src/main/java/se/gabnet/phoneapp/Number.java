package se.gabnet.phoneapp;

import java.util.Date;

public class Number {
    private String name;
    private String number;
    private String date;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String typeStr = type.equals("1") ? "INC" : type.equals("2") ? "OUT" : "MISS";
        if (name == null) {
            return String.format("%s %n%s %n%s %n",number,new Date(Long.parseLong(date)),typeStr);
        } else {
            return String.format("%s %n%s %n%s %n%s %n",name, number,new Date(Long.parseLong(date)),typeStr);

        }
    }
}
