package com.bos.transaction.model.dao;

public interface TransactionDao {
    int getId_transaction();
    String getOrder_time();
    String getTotal_payment();
    int getStatus();
    int getId_buyer();
    String getBuyer_name();
    String getPhone();
}
