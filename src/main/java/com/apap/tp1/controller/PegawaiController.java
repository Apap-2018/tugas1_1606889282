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
		List<InstansiModel> listInstansi = instansiService.getAll();
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
	
	@RequestMapping(value="/pegawai/tambah", params={"addJabatan"}, method = RequestMethod.POST)
	public String tambahRow(@ModelAttribute PegawaiModel pegawai,Model model) {
		pegawai.getJabatanList().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tanggalLahir = simpleDateFormat.format(pegawai.getTanggalLahir());
		model.addAttribute("tanggalLahir", tanggalLahir);	
		List<ProvinsiModel> listProv = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		System.out.println(pegawai.getInstansi()==null);
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
	
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		model.addAttribute("listInstansi", listInstansi);
	    model.addAttribute("pegawai", pegawai);
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"deleteJabatan"}, method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	private String cariPegawai(@RequestParam(value = "idProvinsi", required = false) String idProvinsi,
			@RequestParam(value = "idInstansi", required = false) String idInstansi,
			@RequestParam(value = "idJabatan", required = false) String idJabatan, Model model) {
		model.addAttribute("listProvinsi", provinsiService.getProvinsi());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		model.addAttribute("listInstansi", instansiService.getAll());
		
		List<PegawaiModel> listPegawai = pegawaiService.getAllPegawai();
		if ((idProvinsi == null) && (idInstansi == null) && (idJabatan == null )) {
			listPegawai = null;
		}
		
		if ((idProvinsi == null || idProvinsi.equals("")) && (idInstansi == null || idInstansi.equals(""))
				&& (idJabatan == null || idJabatan.equals(""))) {
		} else {
			if (idProvinsi != null && !idProvinsi.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel pegawai : listPegawai) {
					if (((Long) pegawai.getInstansi().getProvinsi().getId()).toString().equals(idProvinsi)) {
						temp.add(pegawai);
					}
				}
				listPegawai = temp;
				model.addAttribute("idProvinsi", idProvinsi);
			} else {
				model.addAttribute("idProvinsi", "");
			}
		}
		
		
		
		if (idInstansi != null && !idInstansi.equals("")) {
			List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
			for (PegawaiModel pegawai : listPegawai) {
				if (((Long) pegawai.getInstansi().getId()).toString().equals(idInstansi)) {
					temp.add(pegawai);
				}
			}
			listPegawai = temp;
			model.addAttribute("idInstansi", idInstansi);
		} else {
			model.addAttribute("idInstansi", "");
		}
		if (idJabatan != null && !idJabatan.equals("")) {
			List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
			for (PegawaiModel pegawai : listPegawai) {
				for (JabatanModel jabatan : pegawai.getJabatanList()) {
					if (((Long) jabatan.getId()).toString().equals(idJabatan)) {
						temp.add(pegawai);
						break;
					}
				}

			}
			listPegawai = temp;
			model.addAttribute("idJabatan", idJabatan);
		} else {
			model.addAttribute("idJabatan", "");
		}
		model.addAttribute("listPegawai", listPegawai);
		return "search-pegawai";
	
		
	}
	
	@RequestMapping(value= "/pegawai/ubah", method = RequestMethod.GET)
	private String ubahPegawai(@RequestParam String nip,Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", pegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getListJabatan(); 
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		model.addAttribute("tanggalLahir", dateFormat.format(date));
		
		return "change-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"addJabatan"}, method = RequestMethod.POST)
	public String tambahRowUbah(@ModelAttribute PegawaiModel pegawai,Model model) {
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
	    return "change-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"deleteJabatan"}, method = RequestMethod.POST)
	public String hapusRowUbah(@ModelAttribute PegawaiModel pegawai, HttpServletRequest req,Model model) {
		Integer rowId = Integer.valueOf(req.getParameter("deleteJabatan"));
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
	    return "change-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	public String ubahPegawai (@ModelAttribute PegawaiModel pegawai, Model model) {
		String oldNip = pegawai.getNip();
		pegawaiService.update(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "change-pegawai-sukses";
		}
	
	
	
	
	
}
