package com.moringaschool.waterrefillrecords.modules;

import java.util.Objects;

public class Expenses {
    private String description;
    private int amount;
    private int salesId;
    private int id;

    public Expenses(String description, int amount, int salesId) {
        this.description = description;
        this.amount = amount;
        this.salesId = salesId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expenses expenses = (Expenses) o;
        return amount == expenses.amount &&
                salesId == expenses.salesId &&
                id == expenses.id &&
                description.equals(expenses.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, amount, salesId, id);
    }
}
