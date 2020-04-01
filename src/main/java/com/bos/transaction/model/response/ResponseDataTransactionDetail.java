package com.bos.transaction.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataTransactionDetail {
    private int id_transaction;
    private int id_seller;
    private String order_time;
    private String total_payment;
    private String address;
    private String shipping_code;
    private BigInteger shipping_fee;
    private String shipping_agent;
    private int status;
    private BuyerResponse buyer;
    private ArrayList<TransactionDetailResponse> transaction_detail;
}
