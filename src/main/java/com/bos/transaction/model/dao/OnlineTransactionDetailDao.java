package com.bos.transaction.model.dao;

public interface OnlineTransactionDetailDao {
    int getId_transaction_detail();
    int getId_transaction();
    int getId_buyer();
    String getBuyer_name();
    String getPhone();
    String getAddress_detail();
    int getId_kelurahan();
    String getTotal_payment();
    String getOrder_time();
    int getId_product();
    String getProduct_name();
    int getQuantity();
    String getSell_price();
    String getShipping_code();
    String getShipping_Agent();
    int getStatus();
    int getId_seller();
}
