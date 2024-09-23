package com.aspire.employee.repository;

import com.aspire.employee.models.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamRepo extends JpaRepository<Stream,Integer> {
    boolean existsByStreamName(String streamName);
    Stream findByName(String streamName);
}
