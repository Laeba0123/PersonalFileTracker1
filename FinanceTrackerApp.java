package com.laiba.BeginnerProjects.PersonalFileTracker;
import com.laiba.BeginnerProjects.PersonalFileTracker.DataPersistenceService;
import com.laiba.BeginnerProjects.PersonalFileTracker.FinanceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

/**
 * MAIN APPLICATION CLASS
 * MODULAR DESIGN: Each major feature is in its own method
 * EASY TO UNDERSTAND: Clear separation of concerns
 */
public class FinanceTrackerApp extends JFrame {
    // Core application components
    private FinanceManager financeManager ;
    private DataPersistenceService dataService ;

    // UI Components organized by module
    private JTabbedPane mainTabs;

    // Dashboard components
    private JLabel balanceLabel, incomeLabel, expenseLabel;
    private JTextArea recentTransactionsArea;

    // Add Transaction components
    private JTextField amountField, descriptionField;
    private JComboBox<TransactionType> typeComboBox;
    private JComboBox<String> categoryComboBox;

    // Transactions components
    private JTable transactionsTable;
    private DefaultTableModel transactionsTableModel;

    // Budgets components
    private JTextArea budgetsDisplayArea;

    // Colors for consistent styling
    private final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private final Color INCOME_COLOR = new Color(34, 139, 34);
    private final Color EXPENSE_COLOR = new Color(220, 20, 60);
    private final Color LIGHT_BG = new Color(240, 248, 255);

    public FinanceTrackerApp() {
        initializeApplication();
        setupUserInterface();
        loadApplicationData();
    }

    /**
     * MODULE 1: APPLICATION INITIALIZATION
     * Purpose: Set up core application components
     */
    private void initializeApplication() {
        // Initialize data services
        dataService = new DataPersistenceService();
        financeManager = dataService.loadData();

        // Basic window setup
        setTitle("💰 Personal Finance Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    /**
     * MODULE 2: MAIN UI SETUP
     * Purpose: Create the main window structure
     */
    private void setupUserInterface() {
        // Use BorderLayout for main organization
        setLayout(new BorderLayout(10, 10));

        // Build each major section
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContentPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    /**
     * MODULE 3: HEADER PANEL
     * Purpose: Show key financial summary at top
     */
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Application title
        JLabel titleLabel = new JLabel("💰 Personal Finance Tracker");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        // Financial summary
        JPanel statsPanel = createStatsPanel();

        header.add(titleLabel, BorderLayout.WEST);
        header.add(statsPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createStatsPanel() {
        JPanel stats = new JPanel(new GridLayout(1, 3, 20, 0));
        stats.setBackground(PRIMARY_COLOR);

        balanceLabel = createStatLabel("Balance: $0.00", Color.WHITE);
        incomeLabel = createStatLabel("Income: $0.00", Color.GREEN);
        expenseLabel = createStatLabel("Expenses: $0.00", new Color(255, 150, 150));

        stats.add(balanceLabel);
        stats.add(incomeLabel);
        stats.add(expenseLabel);

        return stats;
    }

    private JLabel createStatLabel(String text, Color color) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(color);
        return label;
    }

    /**
     * MODULE 4: MAIN CONTENT PANEL
     * Purpose: Tabbed interface for different features
     */
    private JPanel createMainContentPanel() {
        mainTabs = new JTabbedPane();

        // Each tab is a separate module
        mainTabs.addTab("📊 Dashboard", createDashboardTab());
        mainTabs.addTab("➕ Add Transaction", createAddTransactionTab());
        mainTabs.addTab("📋 Transactions", createTransactionsTab());
        mainTabs.addTab("💰 Budgets", createBudgetsTab());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(mainTabs, BorderLayout.CENTER);
        return contentPanel;
    }

    /**
     * MODULE 5: FOOTER PANEL
     * Purpose: Global actions like save data
     */
    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(BorderFactory.createEtchedBorder());

        JButton saveButton = new JButton("💾 Save Data");
        saveButton.addActionListener(e -> saveData());

        footer.add(saveButton);
        return footer;
    }

    // ========== FEATURE MODULES ==========

    /**
     * FEATURE MODULE: DASHBOARD TAB
     * Purpose: Overview of financial health
     */
    private JPanel createDashboardTab() {
        JPanel dashboard = new JPanel(new BorderLayout(10, 10));
        dashboard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Summary cards at top
        dashboard.add(createSummaryCards(), BorderLayout.NORTH);

        // Recent transactions in middle
        recentTransactionsArea = new JTextArea(15, 50);
        recentTransactionsArea.setEditable(false);
        recentTransactionsArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(recentTransactionsArea);

        dashboard.add(scrollPane, BorderLayout.CENTER);

        return dashboard;
    }

    private JPanel createSummaryCards() {
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 15, 15));

        // These will be populated with actual data
        cardsPanel.add(createCard("Current Balance", "$0.00", PRIMARY_COLOR));
        cardsPanel.add(createCard("Total Income", "$0.00", INCOME_COLOR));
        cardsPanel.add(createCard("Total Expenses", "$0.00", EXPENSE_COLOR));
        cardsPanel.add(createCard("Transactions", "0", Color.ORANGE));

        return cardsPanel;
    }

    private JPanel createCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        JLabel valueLabel = new JLabel(value, JLabel.CENTER);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    /**
     * FEATURE MODULE: ADD TRANSACTION TAB
     * Purpose: Form to add new income/expense transactions
     */
    private JPanel createAddTransactionTab() {
        JPanel addTransactionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form title
        JLabel titleLabel = new JLabel("Add New Transaction");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);

        // Form fields
        amountField = new JTextField(20);
        typeComboBox = new JComboBox<>(TransactionType.values());

        String[] categories = {"Food", "Transport", "Entertainment", "Bills",
                "Shopping", "Healthcare", "Salary", "Investment", "Other"};
        categoryComboBox = new JComboBox<>(categories);

        descriptionField = new JTextField(20);
        JButton addButton = new JButton("💾 Add Transaction");

        // Layout setup
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        addTransactionPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        addTransactionPanel.add(new JLabel("Amount ($):"), gbc);
        gbc.gridx = 1;
        addTransactionPanel.add(amountField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        addTransactionPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        addTransactionPanel.add(typeComboBox, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        addTransactionPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        addTransactionPanel.add(categoryComboBox, gbc);

        gbc.gridy = 4; gbc.gridx = 0;
        addTransactionPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        addTransactionPanel.add(descriptionField, gbc);

        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addTransactionPanel.add(addButton, gbc);

        // Event handling
        addButton.addActionListener(e -> addNewTransaction());

        return addTransactionPanel;
    }

    /**
     * ACTION: Add new transaction from form
     */
    private void addNewTransaction() {
        try {
            // Get form data
            double amount = Double.parseDouble(amountField.getText());
            TransactionType type = (TransactionType) typeComboBox.getSelectedItem();
            String category = (String) categoryComboBox.getSelectedItem();
            String description = descriptionField.getText();

            // Validate
            if (amount <= 0) {
                throw new NumberFormatException("Amount must be positive");
            }

            // Create transaction
            Transaction transaction = new Transaction(
                    generateTransactionId(),
                    amount,
                    category,
                    new java.sql.Date(System.currentTimeMillis()),
                    description,
                    type
            );

            // Add to manager
            financeManager.addTransaction(transaction);

            // Reset form
            amountField.setText("");
            descriptionField.setText("");

            // Update UI
            refreshAllDisplays();
            showMessage("Transaction added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            showMessage("Please enter a valid positive amount", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidTrasactionException ex) {
            showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * FEATURE MODULE: TRANSACTIONS TAB
     * Purpose: View and manage all transactions
     */
    private JPanel createTransactionsTab() {
        JPanel transactionsPanel = new JPanel(new BorderLayout(10, 10));
        transactionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Table setup
        String[] columns = {"ID", "Date", "Type", "Category", "Amount", "Description"};
        transactionsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only table
            }
        };

        transactionsTable = new JTable(transactionsTableModel);
        JScrollPane tableScroll = new JScrollPane(transactionsTable);

        // Control buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("🔄 Refresh");
        JButton deleteButton = new JButton("🗑️ Delete Selected");

        refreshButton.addActionListener(e -> refreshTransactionsTable());
        deleteButton.addActionListener(e -> deleteSelectedTransaction());

        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);

        transactionsPanel.add(new JLabel("All Transactions:"), BorderLayout.NORTH);
        transactionsPanel.add(tableScroll, BorderLayout.CENTER);
        transactionsPanel.add(buttonPanel, BorderLayout.SOUTH);

        return transactionsPanel;
    }

    /**
     * ACTION: Refresh transactions table
     */
    private void refreshTransactionsTable() {
        transactionsTableModel.setRowCount(0); // Clear table

        for (Transaction transaction : financeManager.getAllTransactions()) {
            Object[] row = {
                    transaction.getId(),
                    transaction.getDate(),
                    transaction.getType().getDisplayName(),
                    transaction.getCategory(),
                    String.format("$%.2f", transaction.getAmount()),
                    transaction.getDescription()
            };
            transactionsTableModel.addRow(row);
        }
    }

    /**
     * ACTION: Delete selected transaction
     */
    private void deleteSelectedTransaction() {
        int selectedRow = transactionsTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select a transaction to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this transaction?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            String transactionId = (String) transactionsTableModel.getValueAt(selectedRow, 0);
            boolean deleted = financeManager.deleteTransaction(transactionId);

            if (deleted) {
                refreshTransactionsTable();
                refreshAllDisplays();
                showMessage("Transaction deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * FEATURE MODULE: BUDGETS TAB
     * Purpose: Set and view monthly budgets
     */
    private JPanel createBudgetsTab() {
        JPanel budgetsPanel = new JPanel(new BorderLayout(10, 10));
        budgetsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Budget input form
        JPanel inputPanel = createBudgetInputPanel();

        // Budget display area
        budgetsDisplayArea = new JTextArea(15, 50);
        budgetsDisplayArea.setEditable(false);
        budgetsDisplayArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(budgetsDisplayArea);

        budgetsPanel.add(inputPanel, BorderLayout.NORTH);
        budgetsPanel.add(scrollPane, BorderLayout.CENTER);

        return budgetsPanel;
    }

    private JPanel createBudgetInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        String[] categories = {"Food", "Transport", "Entertainment", "Bills", "Shopping", "Healthcare"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        JTextField amountField = new JTextField();
        JButton setBudgetButton = new JButton("💰 Set Budget");

        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryCombo);
        inputPanel.add(new JLabel("Monthly Budget ($):"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel());
        inputPanel.add(setBudgetButton);

        setBudgetButton.addActionListener(e -> {
            try {
                String category = (String) categoryCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

                financeManager.setBudget(category, amount, currentMonth);
                amountField.setText("");
                refreshBudgetsDisplay();
                showMessage("Budget set successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return inputPanel;
    }

    /**
     * ACTION: Refresh budgets display
     */
    private void refreshBudgetsDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("MONTHLY BUDGETS\n");
        sb.append("===============\n\n");

        List<Budget> budgets = financeManager.getAllBudgets();

        if (budgets.isEmpty()) {
            sb.append("No budgets set. Use the form above to set monthly budgets.\n");
        } else {
            for (Budget budget : budgets) {
                sb.append(String.format("Category: %s\n", budget.getCategory()));
                sb.append(String.format("Budget: $%.2f\n", budget.getAllocatedAmount()));
                sb.append(String.format("Spent: $%.2f\n", budget.getSpentAmount()));
                sb.append(String.format("Remaining: $%.2f\n", budget.getRemainingAmount()));
                sb.append("----------------\n");
            }
        }

        budgetsDisplayArea.setText(sb.toString());
    }

    // ========== UTILITY METHODS ==========

    /**
     * Load initial application data
     */
    private void loadApplicationData() {
        refreshAllDisplays();
    }

    /**
     * Refresh all UI displays
     */
    private void refreshAllDisplays() {
        updateHeaderStats();
        refreshTransactionsTable();
        refreshBudgetsDisplay();
        refreshRecentTransactions();
    }

    /**
     * Update header statistics
     */
    private void updateHeaderStats() {
        double balance = financeManager.calculateCurrentBalance();
        double income = financeManager.calculateTotalIncome();
        double expenses = financeManager.calculateTotalExpenses();

        balanceLabel.setText(String.format("Balance: $%.2f", balance));
        incomeLabel.setText(String.format("Income: $%.2f", income));
        expenseLabel.setText(String.format("Expenses: $%.2f", expenses));

        // Color code balance
        balanceLabel.setForeground(balance >= 0 ? INCOME_COLOR : EXPENSE_COLOR);
    }

    /**
     * Refresh recent transactions in dashboard
     */
    private void refreshRecentTransactions() {
        if (recentTransactionsArea == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("Recent Transactions:\n");
        sb.append("====================\n\n");

        List<Transaction> transactions = financeManager.getAllTransactions();
        int count = Math.min(5, transactions.size());

        for (int i = transactions.size() - 1; i >= Math.max(0, transactions.size() - count); i--) {
            Transaction t = transactions.get(i);
            String symbol = t.getType() == TransactionType.INCOME ? "⬆️" : "⬇️";
            sb.append(String.format("%s %s: $%.2f - %s\n",
                    symbol, t.getCategory(), t.getAmount(), t.getDescription()));
        }

        if (transactions.isEmpty()) {
            sb.append("No transactions yet. Add some using the 'Add Transaction' tab!");
        }

        recentTransactionsArea.setText(sb.toString());
    }

    /**
     * Save application data
     */
    private void saveData() {
        dataService.saveData(financeManager);
        showMessage("All data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Generate unique transaction ID
     */
    private String generateTransactionId() {
        return "TXN_" + System.currentTimeMillis();
    }

    /**
     * Show message dialog
     */
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    /**
     * MAIN METHOD: Application entry point
     */
    public static void main(String[] args) {
        // Swing applications must run on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
//            } catch (Exception e) {
//                // Use default look and feel if system L&F fails
//            }

            new FinanceTrackerApp().setVisible(true);
        });
    }
}