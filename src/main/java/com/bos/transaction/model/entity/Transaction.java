package com.bos.transaction.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_transaction;
    private int id_buyer;
    private String address_detail;
    private String order_time;
    private String total_payment;
    private int status;
}
