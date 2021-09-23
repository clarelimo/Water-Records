package com.moringaschool.waterrefillrecords.modules;

public class Sale {
    private String date;
    private int litresSold;
    private int emptyBottlesSold;
    private int totalSales;
    private int balance;
    private String machineImage;

    public Sale(String date, int litresSold, int emptyBottlesSold, int balance, int totalSales, String machineImage) {
        this.totalSales = totalSales;
        this.litresSold = litresSold;
        this.emptyBottlesSold = emptyBottlesSold;
        this.balance = balance;
        this.date = date;
        this.machineImage = machineImage;
    }

    public Sale(){}
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getMachineImage() {
        return machineImage;
    }

    public void setMachineImage(String machineImage) {
        this.machineImage = machineImage;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}