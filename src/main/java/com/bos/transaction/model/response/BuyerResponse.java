package com.bos.transaction.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerResponse {
    private int id_buyer;
    private String buyer_name;
    private String phone;
}
