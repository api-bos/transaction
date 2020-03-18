package com.bos.transaction.model.dao;

public interface OfflineTransactionDetailDao {
    int getId_transaction_detail();
    int getId_transaction();
    String getTotal_payment();
    String getOrder_time();
    int getId_product();
    String getProduct_name();
    int getQuantity();
    String getSell_price();
}
