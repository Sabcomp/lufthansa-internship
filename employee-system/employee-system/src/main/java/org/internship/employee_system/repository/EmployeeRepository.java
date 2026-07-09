package org.internship.employee_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.internship.employee_system.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // find employees by department
    List<Employee> findByDepartment(String department);

    // find employees whose salary is greater than a given amount
//    @Query("Select e FROM Employee e WHERE e.salary > :amount")
//    List<Employee> findBySalaryGreaterThan(@Param("amount") BigDecimal amount);
    List<Employee> findBySalaryGreaterThan(BigDecimal amount);

    // find employees whose last name contains a given text
    List<Employee> findByLastNameContaining(String text);

    // find employees by department whose salary is greater than a given amount
    List<Employee> findByDepartmentAndSalaryGreaterThan(String department, BigDecimal salary);

    // all employees hired after a given date (jpql)
    @Query("Select e from Employee e WHERE e.hireDate > :givenDate")
    List<Employee> findByHireDateAfter(@Param("givenDate") LocalDate date);

    // all employees whose salary greater than given amount (native)
    @Query(value = "SELECT * FROM employee WHERE salary > :amount", nativeQuery = true)
    List<Employee> findBySalaryGreaterThanNative(@Param("amount") BigDecimal amount);

    // retrieve employees by page 1
    Page<Employee> findAll(Pageable pageable);

    // retrieve all employees sorted by salary in descending order
    List<Employee> findAllByOrderBySalaryDesc();
}
