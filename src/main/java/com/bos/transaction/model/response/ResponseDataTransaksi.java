package com.bos.transaction.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataTransaksi {
    private int id_transaction;
    private String buyer_name;
    private String transaction_date;
    private double total_payment;
    private int status;
}
