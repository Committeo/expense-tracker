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

# Interview Cheat Sheet

## The 30-second explanation of your project
"It's a console-based Java application that manages company expense records. I used OOP to separate the code into a model class, a DAO class for database operations and a main class for the menu. It connects to MySQL using JDBC and `PreparedStatement`, and it supports adding, viewing, filtering and deleting expenses, plus reports like total spending and spending per category using `SUM`, `WHERE` and `ORDER BY`."

## JDBC questions

**What is JDBC?** Java Database Connectivity. It's the standard Java API for connecting to and running SQL on a database.

**Steps of JDBC?**
1. Load/register driver (automatic in modern versions with the connector jar)
2. Get a `Connection` using `DriverManager.getConnection(url, user, password)`
3. Create a `PreparedStatement`
4. Execute it
5. Process the `ResultSet`
6. Close resources (I used try-with-resources)

**Statement vs PreparedStatement?** `PreparedStatement` uses `?` placeholders, so user input is treated as data and not as SQL. This **prevents SQL injection**. It's also precompiled, so it's faster when repeated.

**executeQuery vs executeUpdate?** `executeQuery` is for `SELECT` and returns a `ResultSet`. `executeUpdate` is for `INSERT/UPDATE/DELETE` and returns the number of rows affected.

**What is ResultSet?** A table-like object holding the rows returned by a SELECT. `rs.next()` moves to the next row, and `rs.getInt()/getString()/getDouble()` read column values.

**What is the JDBC URL?** `jdbc:mysql://localhost:3306/expense_db` means protocol, host, port and database name.

**Why try-with-resources?** It automatically closes the Connection and PreparedStatement even if an error happens, which avoids resource leaks.

## OOP questions (point to your code)

**Encapsulation?** `Expense` has private fields with public getters/setters.

**Why a DAO class?** It separates database logic from the rest of the program, so Main stays clean and the DB code is easy to change or test.

**Abstraction?** Main doesn't know any SQL. It just calls `dao.addExpense(e)`.

**What is a constructor?** A special method with the class name that runs when you create an object (`new Expense(...)`).

**What does `static` mean in `DBConnection.getConnection()`?** It belongs to the class, so I can call it without creating an object.

## SQL questions

**WHERE?** Filters rows: `WHERE category = 'Travel'`.

**ORDER BY?** Sorts results: `ORDER BY amount DESC` means highest first (`ASC` is the default, lowest first).

**SUM?** An aggregate function that adds up a column: `SELECT SUM(amount) FROM expenses`.

**Other aggregates?** `COUNT`, `AVG`, `MIN`, `MAX`.

**GROUP BY (very likely follow-up!)?** Groups rows and applies an aggregate per group:
```sql
SELECT category, SUM(amount) FROM expenses GROUP BY category;
```
This gives the total per category. It's not in the code, but you can explain it.

**Primary key?** A column that uniquely identifies each row (`id`). `AUTO_INCREMENT` makes MySQL generate it.

**Why DECIMAL for money?** `DECIMAL` is exact. `float/double` can have rounding errors. (In Java I used `double` to keep things simple. Say that a production app would use `BigDecimal`.)

## Git questions

**What is Git?** A version control system that tracks changes to code.

**Basic commands?** `git init`, `git add .`, `git commit -m "msg"`, `git push`, `git status`, `git log`.

## Be honest about limits

If asked "what would you improve?", say:
- Use `BigDecimal` for money
- Add input validation (e.g. no negative amounts)
- Add an update/edit option
- Add a `users` or `departments` table with a foreign key
- Use connection pooling and a config file instead of hardcoding the password

**Resume tip:** your bullet says "secure SQL transactions". If asked, explain that `PreparedStatement` gives the security. If someone asks about `commit`/`rollback`, say JDBC auto-commits by default and you haven't used manual transactions. Or consider changing the word to "secure SQL operations".
