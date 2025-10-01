package com.laiba.BeginnerProjects.PersonalFileTracker;

import java.io.Serializable;
import java.time.LocalDate;
//* ENTITY: Represents a savings goal
 //* OOP CONCEPT: More business logic examples

public class FinancialGoal implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String name;
    private double TargetAmount;
    private double SavedAmount;
    private LocalDate targetDate;

    public FinancialGoal(String name, double savedAmount, double targetAmount, LocalDate targetDate) {
        this.name = name;
        SavedAmount = savedAmount;
        TargetAmount = targetAmount;
        this.targetDate = targetDate;
    }

    //calculate progress percenatge
    public double getProgressPercentage(){
        if(TargetAmount==0) return 0;
        return (SavedAmount/TargetAmount)*100;
    }
    //check if goal is achieved
    public boolean isAchieved(){
        return SavedAmount>=TargetAmount;
    }
    //add to davings
    public void addToSavings(double amount){
        if(amount>=0)
            this.SavedAmount+=amount;
    }
    //checkj if goal is overdue
    public boolean isOverdue(){
        return LocalDate.now().isAfter(targetDate) && !isAchieved();
    }
    //getters

    public String getName() {
        return name;
    }

    public double getSavedAmount() {
        return SavedAmount;
    }

    public double getTargetAmount() {
        return TargetAmount;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetAmount(double targetAmount) {
        if (TargetAmount>=0)
        this.TargetAmount = targetAmount;
    }
}
