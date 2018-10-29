package com.apap.tp1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tp1.model.JabatanModel;
import com.apap.tp1.repository.JabatanDb;
import com.apap.tp1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String pageAddJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatan(@ModelAttribute JabatanModel jabatan, RedirectAttributes redirectAtt){
		jabatanService.addJabatan(jabatan);
		String message = "Jabatan " + jabatan.getNama() + " berhasil ditambah";
		redirectAtt.addFlashAttribute("message", message);
		return "redirect:/jabatan/tambah";
	}
	
	@RequestMapping(value= "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam(value="idJabatan") Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.GET)
	public String changeJabatan(@RequestParam(value = "idJabatan") Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
		model.addAttribute("jabatan", jabatan);
		return "change-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.POST)
	private String changeJabatanSubmit(@ModelAttribute JabatanModel jabatan, RedirectAttributes redirectAtt) {
		jabatanService.changeJabatan(jabatan, jabatan.getId());
		String message = "Jabatan " + jabatan.getNama() + " berhasil diubah!";
		redirectAtt.addFlashAttribute("message", message);
		redirectAtt.addAttribute("idJabatan", jabatan.getId());
		return "redirect:/jabatan/ubah";
	}
	
	@RequestMapping(value="/jabatan/hapus", method=RequestMethod.POST)
	private String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model,RedirectAttributes redirectAtt) throws Exception{
		JabatanModel jabat = jabatanService.getJabatanById(jabatan.getId());
		String message = "";
		if (jabat.getPegawaiList().size()==0) {
			message = "Jabatan " + jabat.getNama() + " berhasil dihapus!";
			jabatanService.deleteJabatanById(jabatan.getId());
		}
		else {
			message = "Jabatan " + jabat.getNama() + " memiliki pegawai, tidak bisa dihapus!";
		}
		redirectAtt.addFlashAttribute("message", message);
		model.addAttribute("jabatan", jabat.getNama());		
		return "redirect:/";
	}
	
	@RequestMapping(value="/jabatan/viewall", method=RequestMethod.GET)
	public String viewAll(Model model) {
		List<JabatanModel> listAll = jabatanService.getListJabatan();
		model.addAttribute("listAll",listAll);
		return "viewall-jabatan";
	}
	
	
	
	
	
	
}
