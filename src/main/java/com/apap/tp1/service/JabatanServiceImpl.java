package com.apap.tp1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tp1.model.JabatanModel;
import com.apap.tp1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	
	@Autowired
	private JabatanDb jabatanDb;

	@Override
	public JabatanModel getJabatanById(Long id) {
		// TODO Auto-generated method stub
		return jabatanDb.getOne(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
	}

	@Override
	public List<JabatanModel> getListJabatan() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}
	
	@Override
	public void changeJabatan(JabatanModel jabatan, Long id) {
		JabatanModel jabatanMdl = jabatanDb.getOne(id);
		jabatanMdl.setNama(jabatan.getNama());
		jabatanMdl.setDeskripsi(jabatan.getDeskripsi());
		jabatanMdl.setGajiPokok(jabatan.getGajiPokok());
		jabatanDb.save(jabatanMdl);
		
	}
	
	@Override
	public void deleteJabatanById(Long id) {
		jabatanDb.deleteById(id);
	}
	
}
