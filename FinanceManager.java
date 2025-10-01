package com.laiba.BeginnerProjects.PersonalFileTracker;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.*;

/**
 * CORE BUSINESS LOGIC: Manages all financial data and operations
 * COLLECTIONS CONCEPT: Using different collection types for different needs
 */
public class FinanceManager  {
    private static final long serialVersionUID = 1L;
    /**
     * COLLECTIONS FRAMEWORK:
     * ArrayList: For transactions - maintains order, allows duplicates
     * HashMap: For budgets - fast lookup by category+month key
     * ArrayList: For goals - maintains order
     */
     private List<Transaction> transactions;
     private Map<String, Budget> budgets;
     private List<FinancialGoal> financialGoals;

    /**
     * CONSTRUCTOR: Initialize all collections
     */
    public FinanceManager() {
        this.transactions = new ArrayList<>();
        this.budgets = new HashMap<>();
        this.financialGoals = new ArrayList<>();
    }

    // ========== TRANSACTION MANAGEMENT ==========

    /**
     * CREATE OPERATION: Add new transaction
     * EXCEPTION HANDLING: Validate input and throw custom exception
     */
   public void addTransaction(Transaction transaction) throws InvalidTrasactionException {
       if (transaction == null){
           throw  new InvalidTrasactionException("Transaction cannot be null");

       }
       if(transaction.getAmount()<=0){
           throw new InvalidTrasactionException("Amount must be positive");
       }
       if(transaction.getCategory()==null || transaction.getCategory().trim().isEmpty()){
           throw new InvalidTrasactionException("Category cannot be empty");
       }
       transactions.add(transaction);
       if(transaction.getType()==TransactionType.EXPENSE){
           updateBudget(transaction.getCategory(), transaction.getAmount());
       }
       if(transaction.getType()==TransactionType.INCOME){

       }
   }
    /**
     * READ OPERATION: Get all transactions
     * Why return a copy? To prevent external code from modifying our internal list
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    /**
     * READ OPERATION: Get transactions by type
     * STREAMS CONCEPT: Functional-style operations on collections
     */
    public List<Transaction> getTransactionsByType(TransactionType Type){
        return transactions.stream()
                .filter(transaction -> transaction.getType()==Type)
                .collect(Collectors.toList());
    }
/**
 * READ OPERATION: Get transactions by category
 */
    public List<Transaction> getTransactionsByCategory(String category) {
        return transactions.stream()
                .filter(transaction -> transaction.getCategory().equalsIgnoreCase
                        (category)).collect(Collectors.toList());
    }
    /**
     * READ OPERATION: Get transactions by date range
     * DATE HANDLING: Using Java 8 Date API
     */
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(transaction -> {
                    LocalDate transactionDate = ((java.sql.Date) transaction.getDate()).toLocalDate();
                    return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
                })
                .collect(Collectors.toList());
    }
    /**
     * UPDATE OPERATION: We'll implement in UI
     */
    // This would be implemented in the next version

    /**
     * DELETE OPERATION: Remove transaction by ID
     * ITERATOR CONCEPT: Safe way to remove while iterating
     */
    public boolean deleteTransaction(String transactionID){
        Iterator<Transaction> iterator = transactions.iterator();
        while(iterator.hasNext()){
            Transaction transaction = iterator.next();
            if(transaction.getId().equals(transactionID)) {
                iterator.remove();;
                return true;
            }
        }
        return false;
    }
    // ========== BUDGET MANAGEMENT ==========

    /**
     * BUDGET OPERATION: Set monthly budget for category
     */
    public void setBudget(String category,double amount, LocalDate month){
        String key = generateBudgetKey(category, month);
        budgets.put(key, new Budget(category, amount, month ));
    }
    /**
     * BUDGET OPERATION: Get budget for category and month
     */
    public Budget getBudget(String category, LocalDate month){
        String key = generateBudgetKey(category, month);
        return budgets.get(key);
    }
    //get a;ll buidgets
    public List<Budget> getAllBudgets(){
        return new ArrayList<>((budgets.values()));
    }
    /**
     * HELPER METHOD: Update budget when expense is added
     */
    private void updateBudget(String category, double amount){
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        Budget budget = getBudget(category, currentMonth);
        if(budget!=null){
            budget.addspending(amount);
        }
    }
    /**
     * HELPER METHOD: Generate unique key for budget map
     */
    private String generateBudgetKey(String category, LocalDate month){
        return category + "-" + month.getMonthValue() + "-"+ month.getYear();
    }
    //financial goal management
    public void addFinancialGoals(FinancialGoal goal){
        financialGoals.add(goal);
    }
    public List<FinancialGoal> getAllFinancialGoals(){
        return new ArrayList<>(financialGoals);
    }
    public double calculateTotalIncome(){
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    public double calculateTotalExpenses(){
        return transactions.stream()
                .filter(t->t.getType()==TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    public double calculateCurrentBalance(){
        return calculateTotalIncome()-calculateTotalExpenses();
    }
    //monthly summary
    public Map<String, Double> getMonthlyExpenseByCategory(LocalDate month){
        return transactions.stream()
                .filter(t-> t.getType() == TransactionType.EXPENSE)
                .filter(t->{
                    LocalDate transactionDate = ((java.sql.Date) t.getDate()).toLocalDate();
                     return transactionDate.getMonth() == month.getMonth() &&
                             transactionDate.getYear() == month.getYear();
                })
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                        ));
    }
}
