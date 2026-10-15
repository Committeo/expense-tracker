# Corporate Bill & Expense Tracker

A console app in Core Java that stores company expenses in MySQL using JDBC.

## Files

| File | What it does |
|---|---|
| `schema.sql` | Creates the database, the `expenses` table and sample data |
| `Expense.java` | Model class: one object = one row of the table |
| `DBConnection.java` | Opens the MySQL connection (`DriverManager`) |
| `ExpenseDAO.java` | All SQL lives here (`PreparedStatement`, `ResultSet`) |
| `Main.java` | Console menu using `Scanner` |

## How to run

1. Install MySQL and Java (JDK 11+).
2. Download the **MySQL Connector/J** jar (e.g. `mysql-connector-j-8.x.jar`) from the MySQL website.
3. Create the database: `mysql -u root -p < schema.sql`
4. Open `DBConnection.java` and put your MySQL password in `PASSWORD`.
5. Compile (put the jar in the same folder):

   Windows:
   ```
   javac -cp ".;mysql-connector-j-8.x.jar" *.java
   java  -cp ".;mysql-connector-j-8.x.jar" Main
   ```
   Mac/Linux:
   ```
   javac -cp ".:mysql-connector-j-8.x.jar" *.java
   java  -cp ".:mysql-connector-j-8.x.jar" Main
   ```

If you use an IDE (IntelliJ / Eclipse), just add the jar to the project libraries and run `Main`.

## Git (for the "Git" on your resume)

```
git init
git add .
git commit -m "Expense tracker with JDBC and MySQL"
git branch -M main
git remote add origin <your-github-repo-url>
git push -u origin main
```

---

