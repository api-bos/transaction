package com.bos.transaction.repository;

import com.bos.transaction.model.entity.TransactionDetail;
import com.bos.transaction.model.response.ResponseDataTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Integer> {
    @Query("SELECT new com.bos.transaction.model.response.ResponseDataTransactionDetail(t.address_detail, t.total_payment, t.order_time, td.quantity, td.sell_price)\n" +
            "FROM TransactionDetail td\n" +
            "JOIN td.transaction t ON td.id_transaction = t.id_transaction\n" +
            "WHERE td.id_transaction = :id_transaction")
    List<ResponseDataTransactionDetail> getTransactionDetail(@Param("id_transaction") int id_transaciont);
}
