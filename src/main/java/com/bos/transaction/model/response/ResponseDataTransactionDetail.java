package com.bos.transaction.model.response;

import com.bos.transaction.model.entity.Buyer;
import com.bos.transaction.model.entity.TransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataTransactionDetail {
    private int id_transaction;
    private String order_time;
    private String total_payment;
    private String address;
    private BuyerResponse buyer;
    private ArrayList<TransactionDetailResponse> transaction_detail;
}
