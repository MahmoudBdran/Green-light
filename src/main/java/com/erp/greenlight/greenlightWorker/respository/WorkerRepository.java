package com.erp.greenlight.greenlightWorker.respository;

import com.erp.greenlight.greenlightWorker.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    //List<Worker> findByNameAndProject(String name, Long projectId);
    List<Worker> findAllByUpdatedAtBetween( LocalDateTime dateFrom,  LocalDateTime dateTo);
   // Worker findAllByIdAndUpdatedAtBetween(Long id, LocalDateTime updatedAt, LocalDateTime updatedAt2);
}
