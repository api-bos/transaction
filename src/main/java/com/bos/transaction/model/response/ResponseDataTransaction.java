package com.bos.transaction.model.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseDataTransaction {
    private int id_transaction;
    private String buyer_name;
    private String order_time;
    private String total_payment;
    private int status;

}
