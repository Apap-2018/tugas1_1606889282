package com.apap.tp1.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	@Autowired
	private InstansiService instansiService;
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		InstansiModel instansi = pegawai.getInstansi();
		Date tanggalLahir = pegawai.getTanggalLahir();
		String tahunMasuk = pegawai.getTahunMasuk();
		int pegawaiKe = 1;
		
		List<PegawaiModel> listPegawaiNIPMirip = this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
		if (!listPegawaiNIPMirip.isEmpty()) {
			pegawaiKe = (int) (Long.parseLong(listPegawaiNIPMirip.get(listPegawaiNIPMirip.size()-1).getNip())%100) + 1;
		}
		String kodeInstansi = Long.toString(instansi.getId());
		
		String pattern = "dd-MM-yy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		String tanggalLahirString = simpleDateFormat.format(tanggalLahir).replaceAll("-", "");
		String pegawaiKeString = pegawaiKe/10 == 0 ? ("0" + Integer.toString(pegawaiKe)) : (Integer.toString(pegawaiKe));
		String nip = kodeInstansi + tanggalLahirString + tahunMasuk + pegawaiKeString;
		
		pegawai.setNip(nip);
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
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}

	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
		
	}
	
	@Override
	public int countGaji(PegawaiModel pegawai) {
		
		double gajiTerbesar = 0;
		
		InstansiModel instansi = pegawai.getInstansi();
		ProvinsiModel provinsi = instansi.getProvinsi();
		double tunjangan = (provinsi.getPresentaseTunjangan())/100;
		
		for(JabatanModel jabatan : pegawai.getJabatanList()) {
			double gajiPokok = jabatan.getGajiPokok();
			if(gajiPokok > gajiTerbesar) gajiTerbesar = gajiPokok;
		}
		double gaji = gajiTerbesar + (tunjangan*gajiTerbesar);

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
	
	@Override
	public List<PegawaiModel> getAllPegawai() {
		return pegawaiDb.findAll();
	}
	
	@Override
    public void update(PegawaiModel pegawai) {
        InstansiModel instansi = pegawai.getInstansi();
        Date tanggalLahir = pegawai.getTanggalLahir();
        String tahunMasuk = pegawai.getTahunMasuk();

        pegawai.setNip(this.nipMaker(instansi, tanggalLahir, tahunMasuk));

        pegawaiDb.save(pegawai);
    }
	
	 private String nipMaker(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
	        String nip = "";

	        String kodeInstansi = Long.toString(instansi.getId());
	        nip += kodeInstansi;

	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
	        String tanggalLahirString = simpleDateFormat.format(tanggalLahir).replace("-", "");
	        nip += tanggalLahirString;

	        nip += tahunMasuk;

	        int nomorPegawai = 1;

	        String nipPegawaiWithoutSeq = kodeInstansi + tanggalLahirString + tahunMasuk;
	        PegawaiModel lastPegawaiNipMirip = this.findFirstByNipStartingWithOrderByNipDesc(nipPegawaiWithoutSeq);
	        if (lastPegawaiNipMirip != null){
	            nomorPegawai += Integer.parseInt(lastPegawaiNipMirip.getNip().substring(14));
	        }
	        String stringNomorPegawai = "";
	        if (nomorPegawai / 10 == 0){
	            stringNomorPegawai += "0" + nomorPegawai;
	        } else {
	            stringNomorPegawai += Integer.toString(nomorPegawai);
	        }
	        nip += stringNomorPegawai;

	        return nip;
	    }
	 
	 @Override
	    public PegawaiModel findFirstByNipStartingWithOrderByNipDesc(String nipPegawaiWithoutSequence) {
	        return pegawaiDb.findFirstByNipStartingWithOrderByNipDesc(nipPegawaiWithoutSequence);
	    }
	
	
	
	

}
