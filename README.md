PersonalFileTracker

A simple Java-based personal finance tracker that helps manage budgets, track transactions, and set financial goals.
The project demonstrates Object-Oriented Programming (OOP) concepts, custom exception handling, and basic data persistence.

✨ Features

Transaction Management
Add, view, and categorize transactions (e.g., income, expenses).

Budget Tracking
Create and monitor budgets to stay on top of spending.

Financial Goals
Define and check progress toward savings or investment goals.

Data Persistence
Save and load data from files (extensible to databases).

Custom Exception Handling
InvalidTransactionException ensures only valid operations are allowed.

🛠️ Tech Stack

Language: Java (JDK 8+)

OOP Concepts: Classes, Enums, Custom Exceptions, Manager/Service separation

Persistence: File-based storage (can be extended to SQLite/MySQL)

Build Tools: (Optional: Maven/Gradle if you add them)

🚀 How to Run

Clone this repository:

git clone https://github.com/your-username/PersonalFileTracker.git
cd PersonalFileTracker


Compile the project:

javac *.java


Run the app:

java FinanceTrackerApp

📂 Project Structure

Budget – Represents budget details.

Transaction – Represents a financial transaction.

TransactionType – Enum for categorizing transactions.

FinanceManager – Handles business logic and calculations.

DataPersistenceService – Saves/loads data.

FinancialGoal – Tracks personal savings/investment goals.

InvalidTransactionException – Custom error handling.

FinanceTrackerApp – Entry point (main class).
