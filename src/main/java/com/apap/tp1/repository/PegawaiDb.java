package com.apap.tp1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tp1.model.InstansiModel;
import com.apap.tp1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	List<PegawaiModel> findByInstansi(InstansiModel Instansi);
	PegawaiModel findByNip(String Nip);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirDesc(InstansiModel idInstansi);
}
