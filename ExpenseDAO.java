import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * ExpenseDAO.java   (DAO = Data Access Object)
 * ALL database work lives in this class. Main.java never writes SQL.
 *
 * Pattern used in EVERY method (memorize this, it is the heart of JDBC):
 *   1. Get a connection
 *   2. Prepare the SQL with ? placeholders        -> PreparedStatement
 *   3. Fill the ? with real values                -> setString / setDouble / setInt
 *   4. Run it                                     -> executeUpdate() or executeQuery()
 *   5. Read results (for SELECT)                  -> ResultSet
 *
 * try-with-resources "try ( ... )" closes the connection automatically.
 */
public class ExpenseDAO {

    // ---------- 1. INSERT : add a new expense ----------
    public void addExpense(Expense e) {
        String sql = "INSERT INTO expenses (category, description, amount, expense_date) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getCategory());          // 1st ?
            ps.setString(2, e.getDescription());       // 2nd ?
            ps.setDouble(3, e.getAmount());            // 3rd ?
            ps.setDate(4, Date.valueOf(e.getDate()));  // 4th ?  (text "2026-10-10" -> SQL date)

            ps.executeUpdate();   // executeUpdate = for INSERT / UPDATE / DELETE
            System.out.println("Expense added successfully!");

        }    catch (IllegalArgumentException ex) {
            // Date.valueOf() throws this when the date text is not a real YYYY-MM-DD date
            System.out.println("Invalid date. Please use YYYY-MM-DD, e.g. 2026-10-10");
        } catch (SQLException ex) {
            System.out.println("Error adding expense: " + ex.getMessage());
        }
    }

    // ---------- 2. SELECT : view all expenses, newest first (ORDER BY) ----------
    public List<Expense> getAllExpenses() {
        String sql = "SELECT * FROM expenses ORDER BY expense_date DESC";
        return runSelect(sql, null);
    }

    // ---------- 3. SELECT with WHERE : filter by category ----------
    public List<Expense> getByCategory(String category) {
        String sql = "SELECT * FROM expenses WHERE category = ? ORDER BY expense_date DESC";
        return runSelect(sql, category);
    }

    // ---------- 4. SELECT with ORDER BY : highest amount first ----------
    public List<Expense> getSortedByAmount() {
        String sql = "SELECT * FROM expenses ORDER BY amount DESC";
        return runSelect(sql, null);
    }

    // ---------- 5. DELETE : remove an expense by id ----------
    public void deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();   // returns how many rows were affected

            if (rows > 0) {
                System.out.println("Expense deleted.");
            } else {
                System.out.println("No expense found with id " + id);
            }

        } catch (SQLException ex) {
            System.out.println("Error deleting expense: " + ex.getMessage());
        }
    }

    // ---------- 6. SUM : total of all expenses ----------
    public double getTotalExpense() {
        String sql = "SELECT SUM(amount) FROM expenses";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {   // executeQuery = for SELECT

            if (rs.next()) {                       // move to the first (only) row
                return rs.getDouble(1);            // column number 1 = the SUM value
            }

        } catch (SQLException ex) {
            System.out.println("Error calculating total: " + ex.getMessage());
        }
        return 0;
    }

    // ---------- 7. SUM with WHERE : total for one category ----------
    public double getTotalByCategory(String category) {
        String sql = "SELECT SUM(amount) FROM expenses WHERE category = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);   // gives 0 if SUM was NULL (no rows)
            }

        } catch (SQLException ex) {
            System.out.println("Error calculating category total: " + ex.getMessage());
        }
        return 0;
    }

    // ---------- Helper: runs a SELECT and converts rows into Expense objects ----------
    // If "param" is null the query has no ?, otherwise param fills the one ?
    private List<Expense> runSelect(String sql, String param) {
        List<Expense> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (param != null) {
                ps.setString(1, param);
            }

            ResultSet rs = ps.executeQuery();

            // rs.next() moves row by row until there are no more rows
            while (rs.next()) {
                Expense e = new Expense(
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        rs.getString("expense_date")
                );
                list.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Error reading expenses: " + ex.getMessage());
        }
        return list;
    }
}
