package com.apap.tp1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tp1.model.InstansiModel;
import com.apap.tp1.model.JabatanModel;
import com.apap.tp1.model.PegawaiModel;
import com.apap.tp1.model.ProvinsiModel;
import com.apap.tp1.service.InstansiService;
import com.apap.tp1.service.JabatanService;
import com.apap.tp1.service.PegawaiService;
import com.apap.tp1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	//@Autowired
	//private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		model.addAttribute("listInstansi", instansiService.getAll());
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String viewPegawai (@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		
		model.addAttribute("nip", nip);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listJabatan", pegawai.getJabatanList());
		model.addAttribute("gaji", pegawaiService.countGaji(pegawai));
		return "view-pegawai";
	}
	
	@RequestMapping(value= "/pegawai/tambah", method = RequestMethod.GET)
	private String tambahPegawai(Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getListJabatan(); 
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(listProvinsi.get(0));
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatanList(new ArrayList<JabatanModel>());
		pegawai.getJabatanList().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		model.addAttribute("tanggalLahir", dateFormat.format(date));
		
		return "add-pegawai";
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(
	            dateFormat, false));
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
	public String tambahRow(@ModelAttribute PegawaiModel pegawai,Model model) {
		pegawai.getJabatanList().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tanggalLahir = simpleDateFormat.format(pegawai.getTanggalLahir());
		model.addAttribute("tanggalLahir", tanggalLahir);	
		List<ProvinsiModel> listProv = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		model.addAttribute("listInstansi", listInstansi);
	    model.addAttribute("pegawai", pegawai);
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"deleteRow"}, method = RequestMethod.POST)
	public String hapusRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		pegawai.getJabatanList().remove(rowId.intValue());
		model.addAttribute("pegawai", pegawai);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tanggalLahir = simpleDateFormat.format(pegawai.getTanggalLahir());
		model.addAttribute("tanggalLahir", tanggalLahir);
		List<ProvinsiModel> listProv = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		model.addAttribute("listInstansi", listInstansi);
		System.out.println(rowId); 
	    return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai-sukses";
	}
	
	@RequestMapping(value="/pegawai/muda-tua", method = RequestMethod.GET)
	private String showTermudaTertua(@RequestParam(value="idku") Long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(id);
		PegawaiModel pegawaiTermuda = pegawaiService.getPegawaiTermuda(instansi);
		PegawaiModel pegawaiTertua = pegawaiService.getPegawaiTertua(instansi);
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		return "muda-tua";
	}
	
	@RequestMapping(value = "/pegawai/cari")
	private String cari(@RequestParam(value = "idProvinsi") Optional<Long> idProvinsi,
			@RequestParam(value="idInstansi") Optional<Long> idInstansi,
			@RequestParam(value="idJabatan") Optional<Long> idJabatan,
			Model model) {
		model.addAttribute("listProvinsi", provinsiService.getProvinsi());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		model.addAttribute("listSemuaInstansi", instansiService.getAll());
		
		List<PegawaiModel> listPegawai = null;
		if(idJabatan.isPresent()) {
			System.out.println(idJabatan);
			JabatanModel jabatan = jabatanService.getJabatanById(idJabatan.get());
			listPegawai = jabatan.getPegawaiList();
		} else {
			listPegawai= pegawaiService.getAllPegawai();
		}
		
		ProvinsiModel provinsi = null;
		if (idProvinsi.isPresent()) {
			provinsi = provinsiService.getProvinsiById(idProvinsi.get());
		}
		
		InstansiModel instansi = null;
		if (idInstansi.isPresent()) {
			instansi = instansiService.getInstansiById(idInstansi.get());
		}
		
		List<PegawaiModel> res = null;
		res = pegawaiService.filterPegawai(provinsi, instansi, listPegawai);
		
		model.addAttribute("listPegawai", res);
		return "search-pegawai";
		
	}
	
	
	
}
