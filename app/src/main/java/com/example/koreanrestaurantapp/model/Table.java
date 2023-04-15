package com.example.koreanrestaurantapp.model;

public class Table {

    private String name;
    private String number;

    public Table(String nameTable, String numberTable) {
        this.name = nameTable;
        this.number = numberTable;
    }

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

    public Table() {
    }
}
