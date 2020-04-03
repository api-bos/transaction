package com.bos.transaction.repository;

import com.bos.transaction.model.dao.OfflineTransactionDao;
import com.bos.transaction.model.dao.OnlineTransactioinDao;
import com.bos.transaction.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT t.id_transaction AS id_transaction, t.order_time AS order_time, t.total_payment AS total_payment, t.status AS status," +
            "b.id_buyer AS id_buyer, b.buyer_name AS buyer_name, b.phone AS phone\n" +
            "FROM transaction AS t \n" +
            "JOIN buyer AS b\n" +
            "ON t.id_buyer = b.id_buyer\n" +
            "WHERE id_seller = :id_seller ORDER BY t.order_time DESC", nativeQuery = true)
    List<OnlineTransactioinDao> getOnlineTransactionBySellerId(@Param("id_seller") int id_seller);

    @Query(value = "SELECT t.id_transaction AS id_transaction, t.order_time AS order_time, t.total_payment AS total_payment, t.status AS status," +
            "CASE WHEN b.id_buyer IS NULL THEN 0 ELSE b.id_buyer END AS id_buyer, CASE WHEN b.buyer_name IS NULL THEN '' ELSE b.buyer_name END AS buyer_name, CASE WHEN b.phone IS NULL THEN '' ELSE b.phone END AS phone\n" +
            "FROM transaction AS t \n" +
            "LEFT JOIN buyer AS b\n" +
            "ON t.id_buyer = b.id_buyer\n" +
            "WHERE id_seller = :id_seller AND t.id_buyer IS NULL ORDER BY t.order_time DESC", nativeQuery = true)
    List<OfflineTransactionDao> getOfflineTransactionBySellerId (@Param("id_seller") int id_seller);

    @Transactional
    @Modifying
    @Query(value = "UPDATE transaction SET shipping_code = :shipping_code, shipping_time = :shipping_time, status=2 " +
            "WHERE id_transaction = :id_transaction", nativeQuery = true)
    void updateOrderShipped(@Param("shipping_code") String shipping_code,
                            @Param("shipping_time") Timestamp shipping_time,
                            @Param("id_transaction") int id_transaction);

    @Transactional
    @Modifying
    @Query(value = "UPDATE transaction SET confirmation_time = :confirmation_time, status=3 " +
            "WHERE id_transaction = :id_transaction", nativeQuery = true)
    void updateCompletedTransaction(@Param("confirmation_time") Timestamp confirmation_time,
                                    @Param("id_transaction") int id_transaction);

    @Query(value = "SELECT id_transaction FROM transaction WHERE order_time = :order_time", nativeQuery = true)
    int getTransactionIdByOrderTime(@Param("order_time") Timestamp order_time);
}
