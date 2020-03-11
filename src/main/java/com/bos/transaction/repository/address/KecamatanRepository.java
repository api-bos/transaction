package com.bos.transaction.repository.address;

import com.bos.transaction.model.entity.address.Kecamatan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KecamatanRepository extends JpaRepository<Kecamatan, Integer> {
}
