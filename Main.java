import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExpenseDAO dao = new ExpenseDAO();   // object that talks to the database

        while (true) {   // keep showing the menu until user chooses Exit
            System.out.println("\n===== Corporate Bill & Expense Tracker =====");
            System.out.println("1. Add expense");
            System.out.println("2. View all expenses");
            System.out.println("3. View expenses by category");
            System.out.println("4. View expenses sorted by amount (highest first)");
            System.out.println("5. Delete expense");
            System.out.println("6. Show total expense");
            System.out.println("7. Show total for a category");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                String category = readText(sc, "Category (e.g. Travel): ");
                String description = readText(sc, "Description: ");
                double amount = readAmount(sc);
                String date = readDate(sc);

                // id is 0 here because MySQL generates the real id (AUTO_INCREMENT)
                Expense e = new Expense(0, category, description, amount, date);
                dao.addExpense(e);

            } else if (choice.equals("2")) {
                printList(dao.getAllExpenses());

            } else if (choice.equals("3")) {
                String category = readText(sc, "Enter category: ");
                printList(dao.getByCategory(category));

            } else if (choice.equals("4")) {
                printList(dao.getSortedByAmount());

            } else if (choice.equals("5")) {
                int id = readId(sc);
                dao.deleteExpense(id);

            } else if (choice.equals("6")) {
                System.out.println("Total expense: Rs." + dao.getTotalExpense());

            } else if (choice.equals("7")) {
                String category = readText(sc, "Enter category: ");
                System.out.println("Total for " + category + ": Rs." + dao.getTotalByCategory(category));

            } else if (choice.equals("8")) {
                System.out.println("Goodbye!");
                break;   // leaves the while loop, program ends

            } else {
                System.out.println("Invalid option, please enter a number from 1 to 8.");
            }
        }

        sc.close();
    }

    // ---------- Input helpers: keep asking until the input is valid ----------

    // Text that must not be empty
    private static String readText(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String text = sc.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("This cannot be empty. Try again.");
        }
    }

    // A positive number, e.g. 580 or 1200.50
    private static double readAmount(Scanner sc) {
        while (true) {
            System.out.print("Amount: ");
            try {
                double amount = Double.parseDouble(sc.nextLine().trim());
                if (amount > 0) {
                    return amount;
                }
                System.out.println("Amount must be greater than 0.");
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number, e.g. 580 or 1200.50");
            }
        }
    }

    // A real date in YYYY-MM-DD format, e.g. 2026-10-10
    private static String readDate(Scanner sc) {
        while (true) {
            System.out.print("Date (YYYY-MM-DD): ");
            String date = sc.nextLine().trim();
            try {
                LocalDate.parse(date);   // throws an exception if the date is not valid
                return date;
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date. Use YYYY-MM-DD, e.g. 2026-10-10");
            }
        }
    }

    // A whole number id
    private static int readId(Scanner sc) {
        while (true) {
            System.out.print("Enter expense id to delete: ");
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a whole number, e.g. 3");
            }
        }
    }

    // Prints every Expense in a list
    private static void printList(List<Expense> list) {
        if (list.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        for (Expense e : list) {
            System.out.println(e);   // uses Expense.toString()
        }
    }
}