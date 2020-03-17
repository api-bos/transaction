package com.bos.transaction.repository;

import com.bos.transaction.model.dao.TransactionDao;
import com.bos.transaction.model.entity.Transaction;
import com.bos.transaction.model.response.ResponseDataTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT t.id_transaction AS id_transaction, t.order_time AS order_time, t.total_payment AS total_payment, t.status AS status," +
            "CASE WHEN b.id_buyer IS NULL THEN 0 ELSE b.id_buyer END AS id_buyer, CASE WHEN b.buyer_name IS NULL THEN '' ELSE b.buyer_name END AS buyer_name, CASE WHEN b.phone IS NULL THEN '' ELSE b.phone END AS phone\n" +
            "FROM transaction AS t \n" +
            "LEFT JOIN buyer AS b\n" +
            "ON t.id_buyer = b.id_buyer\n" +
            "WHERE id_seller = :id_seller ORDER BY t.order_time DESC", nativeQuery = true)
    List<TransactionDao> getTransactionBySellerId(@Param("id_seller") int id_seller);
}
