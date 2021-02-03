package com.prokarma.assignment.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.prokarma.assignment.consumer.domain.ErrorLog;

@Repository
public interface ErrorLogRepository extends CrudRepository<ErrorLog, Long> {

}
