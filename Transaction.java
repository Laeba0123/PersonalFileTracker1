package com.laiba.BeginnerProjects.PersonalFileTracker;

import java.util.Date;
/**
 * CORE ENTITY: Represents a single financial transaction
 * OOP CONCEPT: Encapsulation - we keep data private and provide public methods to access it
 */
public class Transaction {
    private String id;
    private double amount;
    private String category;
    private Date date;
    private String Description;
    private TransactionType type;

    public Transaction(String Id,double amount, String category, Date date, String description, TransactionType type) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.Description = description;
        this.id = id;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return Description;
    }

    public String getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setAmount(double amount) {
        if(amount>=0)        this.amount = amount;
        else {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Transaction{id=%s, amount=%.2f, category=%s, type=%s}",
                id, amount, category, type);
    }
}
