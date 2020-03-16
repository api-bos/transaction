package com.bos.transaction.repository;

import com.bos.transaction.model.entity.Kelurahan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KelurahanRepository extends JpaRepository<Kelurahan, Integer> {
    @Query(value = "select a.kelurahan_name, b.kecamatan_name, c.kota_kab_name, d.provinsi_name\n" +
            "from kelurahan a\n" +
            "left join kecamatan b on a.id_kecamatan = b.id_kecamatan\n" +
            "left join kota_kab c on b.id_kota_kab = c.id_kota_kab\n" +
            "left join provinsi d on c.id_provinsi = d.id_provinsi\n" +
            "where id_kelurahan = :id_kelurahan", nativeQuery = true)
    String getAddressByKelurahanId(@Param("id_kelurahan") int id_kelurahan);
}
