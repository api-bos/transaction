package com.bos.transaction.model.response;

import com.bos.transaction.model.response.ProductOrdered;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataTransactionDetail {
    private int id_transaction;
    private String buyer_name;
    private double total_payment;
    private String transaction_date;
    private ProductOrdered product_ordered;
}
