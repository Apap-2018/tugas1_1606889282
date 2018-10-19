package com.apap.tp1.service;

import java.util.Optional;

import com.apap.tp1.model.InstansiModel;
import com.apap.tp1.model.PegawaiModel;

public interface PegawaiService {
	void addPegawai (PegawaiModel pegawai);
	void deletePegawai (PegawaiModel pegawai);
	void updatePegawai (PegawaiModel pegawai);
	Optional<PegawaiModel> getPegawaiDetailById(Long id);
	PegawaiModel getPegawaiDetailByNip(String nip);
	int countGaji(PegawaiModel pegawai);
	PegawaiModel getPegawaiTertua(InstansiModel instansi);
	PegawaiModel getPegawaiTermuda(InstansiModel instansi);

}
