package com.moringaschool.waterrefillrecords.modules;

import java.sql.Timestamp;
import java.util.Objects;

public class Sales {
    private Timestamp date;
    private int cashSales;
    private int mpesaSales;
    private int litresSold;
    private int emptyBottlesSold;
    private int totalSales;
    private String machineImage;
    private int expectedTotalSales;
    private int balance;
    private int price;
    private int id;

    public Sales(Timestamp date, int cashSales, int mpesaSales, int litresSold, int emptyBottlesSold, String machineImage, int balance) {
        this.date = date;
        this.cashSales = cashSales;
        this.mpesaSales = mpesaSales;
        this.litresSold = litresSold;
        this.emptyBottlesSold = emptyBottlesSold;
        this.balance = balance;
        this.machineImage = machineImage;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getCashSales() {
        return cashSales;
    }

    public void setCashSales(int cashSales) {
        this.cashSales = cashSales;
    }

    public int getMpesaSales() {
        return mpesaSales;
    }

    public void setMpesaSales(int mpesaSales) {
        this.mpesaSales = mpesaSales;
    }

    public int getLitresSold() {
        return litresSold;
    }

    public void setLitresSold(int litresSold) {
        this.litresSold = litresSold;
    }

    public int getEmptyBottlesSold() {
        return emptyBottlesSold;
    }

    public void setEmptyBottlesSold(int emptyBottlesSold) {
        this.emptyBottlesSold = emptyBottlesSold;
    }

    public int getTotalSales() {
        this.totalSales = this.mpesaSales + this.cashSales;
        return totalSales;
    }

    public int getExpectedTotalSales() {
        this.expectedTotalSales = this.litresSold * this.price;
        return expectedTotalSales;
    }

    public String getMachineImage() {
        return machineImage;
    }

    public void setMachineImage(String machineImage) {
        this.machineImage = machineImage;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sales sales = (Sales) o;
        return cashSales == sales.cashSales &&
                mpesaSales == sales.mpesaSales &&
                litresSold == sales.litresSold &&
                emptyBottlesSold == sales.emptyBottlesSold &&
                totalSales == sales.totalSales &&
                expectedTotalSales == sales.expectedTotalSales &&
                balance == sales.balance &&
                price == sales.price &&
                id == sales.id &&
                date.equals(sales.date) &&
                machineImage.equals(sales.machineImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, cashSales, mpesaSales, litresSold, emptyBottlesSold, totalSales, machineImage, expectedTotalSales, balance, price, id);
    }
}
