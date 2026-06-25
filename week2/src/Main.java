import java.sql.*;

public class Main {
    public static void main(String[] args){
        System.out.println("=== JDBC CRUD Application ===\n");

        // 1. Create table
        createEmployeesTable();

        // 2. Insert employees
        insertEmployee("John Doe", "IT", 75000.00);
        insertEmployee("Jane Smith", "HR", 65000.00);
        insertEmployee("Bob Johnson", "IT", 80000.00);
        insertEmployee("Alice Williams", "Finance", 70000.00);
        insertEmployee("Charlie Brown", "IT", 72000.00);
        insertEmployee("Diana Prince", "HR", 68000.00);

        // 3. Find employee by id
        findEmployeeById(3);

        // 4. List all employees
        listAllEmployees();

        // 5. Update employee salary and department
        updateEmployeeSalaryAndDepartment(2, "Finance", 72000.00);

        // 6. Show updated employee
        findEmployeeById(2);

        // 7. Analytics: count and average salary by department
        getEmployeeAnalyticsByDepartment();

        // 8. Delete employee
        deleteEmployeeById(5);

        // 9. List all employees after deletion
        listAllEmployees();

        System.out.println("\n=== Application completed ===");
    }

    private static void createEmployeesTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS employees (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100) NOT NULL, 
                    department VARCHAR(50) NOT NULL,
                    salary DECIMAL(10,2) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;

        try (Connection connection = JdbcConfig.getConnection();
             Statement statement = connection.createStatement()){

            statement.execute(sql);
            System.out.println("Table 'employees' created or already exists\n");
            //executeUpdate for insert/delete
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertEmployee(String name, String department, double salary){
        String sql = "INSERT INTO employees(name, department, salary) VALUES(?, ?, ?);";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, name);
            statement.setString(2, department);
            statement.setDouble(3, salary);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1){
                System.out.println("Employee " + name + " from " + department + " added successfully");
            }
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void findEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("\nEmployee Found:");
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Department: " + resultSet.getString("department"));
                System.out.println("Salary: $" + resultSet.getDouble("salary"));
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error finding employee: " + e.getMessage());
        }
    }

    private static void listAllEmployees() {
        String sql = "SELECT * FROM employees";

        try (Connection connection = JdbcConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\n=== Employee List ===");

            while (resultSet.next()) {
                System.out.print("ID: " + resultSet.getInt("id") + "; ");
                System.out.print("Name: " + resultSet.getString("name") + "; ");
                System.out.print("Department: " + resultSet.getString("department")  + "; ");
                System.out.println("Salary: $" + resultSet.getDouble("salary"));
            }

        } catch (SQLException e) {
            System.err.println("Error listing employees: " + e.getMessage());
        }
    }

    private static void updateEmployeeSalaryAndDepartment(int id, String department, double salary) {
        String sql =
                "UPDATE employees SET department = ?, salary = ? WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, department);
            statement.setDouble(2, salary);
            statement.setInt(3, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("\nEmployee updated successfully.");
            } else {
                System.out.println("\nEmployee not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }

    private static void getEmployeeAnalyticsByDepartment() {
        String sql = """
            SELECT department,
                   COUNT(*) AS employee_count,
                   AVG(salary) AS avg_salary
            FROM employees
            GROUP BY department
            """;

        try (Connection connection = JdbcConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\n=== Department Analytics ===");

            while (resultSet.next()) {
                System.out.print("Department: " + resultSet.getString("department") + "; ");
                System.out.print("Employees: " + resultSet.getInt("employee_count") + "; ");
                System.out.println("Average Salary: $" + resultSet.getDouble("avg_salary"));
            }

        } catch (SQLException e) {
            System.err.println("Error generating analytics: " + e.getMessage());
        }
    }

    private static void deleteEmployeeById(int id) {
        String sql = "DELETE FROM employees WHERE id=?;";

        try(Connection connection = JdbcConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1){
                System.out.println("Employee with id " + id + " deleted");
            } else {
                System.out.println("Employee with id " + id + " not found");
            }
        } catch(SQLException e){
            System.err.println("Error deleting employee: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
