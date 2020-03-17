package com.bos.transaction.model.dao;

public interface OfflineTransactionDao {
    int getId_transaction();
    String getOrder_time();
    String getTotal_payment();
    int getStatus();
}
