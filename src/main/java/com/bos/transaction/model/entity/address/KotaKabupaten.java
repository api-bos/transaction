package com.bos.transaction.model.entity.address;

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
@Table(name = "kota_kab")
public class KotaKabupaten {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_kota_kab;
    private int id_provinsi;
    private String kota_kab_name;
}
