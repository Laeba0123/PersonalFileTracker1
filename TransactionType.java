package com.laiba.BeginnerProjects.PersonalFileTracker;
/**
 * ENUM CONCEPT: Fixed set of constants
 * Why use enum? Instead of using strings like "INCOME", "EXPENSE",
 * we use enums for type safety and better code organization
 */
public enum TransactionType {
    INCOME("Income"),
    EXPENSE("Expense");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
