package com.bos.transaction.repository;

import com.bos.transaction.model.entity.OfflineTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfflineTransactionRepository extends JpaRepository<OfflineTransaction, Integer> {

}
