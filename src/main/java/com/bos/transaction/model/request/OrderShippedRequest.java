package com.bos.transaction.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderShippedRequest {
    private int id_transaction;
    private String shipping_code;
    private double shipping_fee;
}
