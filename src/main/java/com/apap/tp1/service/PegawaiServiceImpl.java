package com.apap.tp1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tp1.model.InstansiModel;
import com.apap.tp1.model.JabatanModel;
import com.apap.tp1.model.PegawaiModel;
import com.apap.tp1.model.ProvinsiModel;
import com.apap.tp1.repository.PegawaiDb;

@Service
@Transactional

public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}
	
	@Override
	public void deletePegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}
	
	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public Optional<PegawaiModel> getPegawaiDetailById(Long id) {
		return pegawaiDb.findById(id);
	}

	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
		
	}
	
	@Override
	public int countGaji(PegawaiModel pegawai) {
		
		double gaji = 0;
		
		InstansiModel instansi = pegawai.getInstansi();
		ProvinsiModel provinsi = instansi.getProvinsi();
		double persentase = provinsi.getPresentaseTunjangan();
		
		for(JabatanModel jabatan : pegawai.getJabatanList()) {
			if(jabatan.getGajiPokok() > gaji) {
				gaji = jabatan.getGajiPokok();
			
			}
		}
		gaji += persentase/100* gaji;
		
		//gaji += (gajiPokok + (gajiPokok * persentase / 100));
		
		
		return (int)gaji;
		
	}
	
	
	@Override
	public PegawaiModel getPegawaiTertua(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pegawaiTertua = pegawaiDb.findByInstansiOrderByTanggalLahirDesc(instansi);
		return pegawaiTertua.get(0);
	}
	
	@Override
	public PegawaiModel getPegawaiTermuda(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pegawaiTermuda = pegawaiDb.findByInstansiOrderByTanggalLahirDesc(instansi);
		return pegawaiTermuda.get(pegawaiTermuda.size()-1);
	}
	
	
	
	

}
