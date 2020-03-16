package com.bos.transaction.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @Column(name = "id_buyer", insertable = false, updatable = false)
    private int id_buyer;
    private String address_detail;
    private String order_time;
    private String total_payment;
    private int status;

    @OneToOne(targetEntity = Buyer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_buyer", referencedColumnName = "id_buyer")
    private Buyer buyer;

//    @OneToOne

//    private TransactionDetail transaction_detail;
}
