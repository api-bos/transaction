package com.bos.transaction.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_detail")
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_transaction_detail;
    @Column(insertable = false, updatable = false)
    private int id_transaction;
    private int id_product;
    private int quantity;
    private double sell_price;

    @OneToOne(targetEntity = Transaction.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_transaction", referencedColumnName = "id_transaction")
    private Transaction transaction;
}
