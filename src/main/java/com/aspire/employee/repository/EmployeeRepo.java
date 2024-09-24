package com.aspire.employee.repository;

import com.aspire.employee.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    public List<Employee> findAllByEmployeeNameStartingWith(String startsWith);
    @Query("SELECT e FROM Employee e WHERE e.employeeId = :id AND e.managerId = 0")
    Optional<Employee> findByIdAndManagerIdEqualsZero(@Param("id") Integer id);


    @Query("SELECT e FROM Employee e WHERE e.stream = :stream AND e.managerId = :managerId")
    Optional<Employee> findByStreamAndManagerIdEquals(@Param("stream") String streamName, @Param("managerId") int managerId);


    List<Employee> findAllByManagerId(Integer employeeId);


    public List<Employee> findAllEmployeesByDesignationAndStreamNameAndAccountName(String designation, String stream, String accountName);


    List<Employee> findAllByManagerId(int employeeId);

    boolean existsByEmployeeIdAndDesignationAndStreamName(int managerId, String manager, String streamName);
}
