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
//    private String buyer_name;
    private String address_detail;
    private String total_payment;
    private String order_time;
    private int quantity;
    private double sell_price;
}
