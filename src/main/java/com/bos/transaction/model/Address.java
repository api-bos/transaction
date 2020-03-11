package com.bos.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String provinsi_name;
    private String kota_kab_name;
    private String kecamatan_name;
    private String kelurahan_name;
}
