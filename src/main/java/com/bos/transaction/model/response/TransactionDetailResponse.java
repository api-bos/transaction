package com.bos.transaction.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailResponse {
    private int id_transaction_detail;
    private int quantity;
    private String sell_price;
    private ProductResponse product;
}
