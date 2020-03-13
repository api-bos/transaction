package com.bos.transaction.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buyer")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_buyer;
    private String buyer_name;
    private String phone;

    @OneToOne(mappedBy = "buyer")
    private Transaction transaction;
}
