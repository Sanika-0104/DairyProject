package com.role.implementation.model;

import javax.persistence.*;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private int id;

    @Column(name = "date")
    private String date;

    private int milkUnits;
    private int pricePerLiter;
    private int amount;
    public Report() {
        super();
    }

    public Report(int id, String date, int milkUnits, int pricePerLiter, int amount) {
        this.id = id;
        this.date = date;
        this.milkUnits = milkUnits;
        this.pricePerLiter = pricePerLiter;
        this.amount = amount;
    }

    public int getMilkUnits() {
        return milkUnits;
    }

    public void setMilkUnits(int milkUnits) {
        this.milkUnits = milkUnits;
    }

    public int getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(int pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}