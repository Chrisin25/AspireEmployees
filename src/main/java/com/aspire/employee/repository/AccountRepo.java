package com.aspire.employee.repository;

import com.aspire.employee.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account,Integer> {

    boolean existsByAccountName(String accountName);
}
