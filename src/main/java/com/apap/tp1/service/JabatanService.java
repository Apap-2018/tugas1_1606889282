package com.apap.tp1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tp1.model.JabatanModel;

public interface JabatanService {
	JabatanModel getJabatanById(Long id);
	
	void addJabatan(JabatanModel jabatan);
	void deleteJabatanById(Long id);
	List<JabatanModel> getListJabatan();
	void changeJabatan (JabatanModel jabatan, Long id);
}