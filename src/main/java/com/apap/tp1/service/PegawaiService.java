package com.apap.tp1.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apap.tp1.model.InstansiModel;
import com.apap.tp1.model.PegawaiModel;
import com.apap.tp1.model.ProvinsiModel;
import com.sun.istack.internal.Nullable;

public interface PegawaiService {
	void addPegawai (PegawaiModel pegawai);
	void deletePegawai (PegawaiModel pegawai);
	void updatePegawai (PegawaiModel pegawai);
	Optional<PegawaiModel> getPegawaiDetailById(Long id);
	PegawaiModel getPegawaiDetailByNip(String nip);
	int countGaji(PegawaiModel pegawai);
	PegawaiModel getPegawaiTertua(InstansiModel instansi);
	PegawaiModel getPegawaiTermuda(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
	List<PegawaiModel> getAllPegawai();
	void update(PegawaiModel pegawai);
	PegawaiModel findFirstByNipStartingWithOrderByNipDesc(String nipPegawaiWithoutSequence);
}
