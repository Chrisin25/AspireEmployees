package com.aspire.employee.repository;

import com.aspire.employee.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    public List<Employee> findAllByEmployeeNameStartingWith(String startsWith);

    public List<Employee> findAllEmployeesByDesignationAndStreamNameAndAccountName(String designation, String stream, String accountName);

    Optional<Employee> findByStreamAndManagerIdEquals(String streamName, int i);

    List<Employee> findAllByManagerId(int employeeId);

    Optional<Employee> findByIdAndManagerIdEqualsZero(Integer managerId);
}
