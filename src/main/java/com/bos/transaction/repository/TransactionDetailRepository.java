package com.bos.transaction.repository;

import com.bos.transaction.model.dao.OfflineTransactionDetailDao;
import com.bos.transaction.model.entity.TransactionDetail;
import com.bos.transaction.model.dao.OnlineTransactionDetailDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Integer> {
    @Query(value = "SELECT td.id_transaction_detail AS id_transaction_detail, td.id_transaction AS id_transaction, " +
            "b.id_buyer AS id_buyer, b.buyer_name AS buyer_name, b.phone AS phone, t.address_detail AS address_detail, t.id_kelurahan AS id_kelurahan, " +
            "t.total_payment AS total_payment, t.order_time AS order_time, " +
            "p.id_product AS id_product, p.product_name AS product_name, td.quantity AS quantity, td.sell_price AS sell_price," +
            "CASE WHEN t.shipping_code IS NULL THEN '' ELSE t.shipping_code END AS shipping_code\n" +
            "FROM transaction_detail AS td\n" +
            "LEFT JOIN transaction AS t ON td.id_transaction = t.id_transaction\n" +
            "LEFT JOIN buyer AS b ON t.id_buyer = b.id_buyer\n" +
            "LEFT JOIN product AS p ON td.id_product = p.id_product\n" +
            "WHERE td.id_transaction = :id_transaction ORDER BY t.order_time DESC", nativeQuery = true)
    List<OnlineTransactionDetailDao> getOnlineTransactionDetail(@Param("id_transaction") int id_transaction);

    @Query(value = "SELECT td.id_transaction_detail AS id_transaction_detail, td.id_transaction AS id_transaction, " +
            "t.total_payment AS total_payment, t.order_time AS order_time, " +
            "p.id_product AS id_product, p.product_name AS product_name, td.quantity AS quantity, td.sell_price AS sell_price " +
            "FROM transaction_detail AS td\n" +
            "LEFT JOIN transaction AS t ON td.id_transaction = t.id_transaction\n" +
            "LEFT JOIN product AS p ON td.id_product = p.id_product\n" +
            "WHERE td.id_transaction = :id_transaction", nativeQuery = true)
    List<OfflineTransactionDetailDao> getOfflineTransactionDetail(@Param("id_transaction") int id_transaction);
}
