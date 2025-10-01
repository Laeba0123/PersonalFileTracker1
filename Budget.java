package com.laiba.BeginnerProjects.PersonalFileTracker;

import java.io.Serializable;
import java.time.LocalDate;

//ENTITY: Represents a monthly budget for a category
public class Budget implements Serializable {
  private static final long SerialVersionUID = 1L;

  private String category;
  private double allocatedAmount;
  private double spentAmount;
  private LocalDate month;

    public Budget(String category,double allocatedAmount, LocalDate month) {
        this.allocatedAmount = allocatedAmount;
        this.category = category;
        this.month = month;
        this.spentAmount = 0.0;
    }

    public double getRemainingAmount(){
        return allocatedAmount-spentAmount;
    }

    public void addspending(double amount){
    if(amount<0){
        throw new IllegalArgumentException("spending amount connot be negative");
    }
    this.spentAmount+=amount;
    }

    public boolean isExceeded(){
        return spentAmount>allocatedAmount;
    }

    public double getSpendingPercentage(){
        if(allocatedAmount==0) return 0;
        return (spentAmount/allocatedAmount)*100;
    }

    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getMonth() {
        return month;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public void setAllocatedAmount(double allocatedAmount) {
        if(allocatedAmount>=0)
        this.allocatedAmount = allocatedAmount;
    }
}

