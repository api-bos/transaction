package com.bos.transaction.repository;

import com.bos.transaction.model.entity.Transaction;
import com.bos.transaction.model.response.ResponseDataTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT new com.bos.transaction.model.response.ResponseDataTransaction(t.id_transaction, b.buyer_name, t.order_time, t.total_payment, t.status)\n" +
            "FROM Transaction t \n" +
            "JOIN t.buyer b\n" +
            "ON t.id_buyer = b.id_buyer\n" +
            "WHERE id_seller = :id_seller ORDER BY t.order_time DESC")
    List<ResponseDataTransaction> getResponseDataTransaction(@Param("id_seller") int id_seller);
}
