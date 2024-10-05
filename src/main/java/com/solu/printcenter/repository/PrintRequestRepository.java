package com.solu.printcenter.repository;

import com.solu.printcenter.domain.PrintRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrintRequestRepository extends JpaRepository<PrintRequest, Long> {
    // this still contains the default methods from JpaRepository like the one we're using, save
}
