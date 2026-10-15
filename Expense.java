/*
 * Expense.java
 * This is the "model" class. One Expense object = one row in the expenses table.
 * OOP concept shown here: ENCAPSULATION (private fields + public getters/setters).
 */
public class Expense {

    // private = nobody outside this class can touch them directly
    private int id;
    private String category;
    private String description;
    private double amount;
    private String date;        // we keep date as text like "2026-10-10" to keep things simple

    // Constructor: used when we create a new Expense object
    public Expense(int id, String category, String description, double amount, String date) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    // Getters: let other classes READ the values
    public int getId() { return id; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }

    // Setters: let other classes CHANGE the values
    public void setId(int id) { this.id = id; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(String date) { this.date = date; }

    // toString decides how an Expense looks when we print it
    @Override
    public String toString() {
        return id + " | " + date + " | " + category + " | " + description + " | Rs." + amount;
    }
}
