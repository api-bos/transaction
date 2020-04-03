package com.bos.transaction.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfflineTransactionRequest {
    private int id_seller;
    private double total_payment;
    private ArrayList<ProductPurchased> product;
}
