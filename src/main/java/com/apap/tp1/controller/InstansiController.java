package com.apap.tp1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tp1.model.InstansiModel;
import com.apap.tp1.model.ProvinsiModel;
import com.apap.tp1.service.InstansiService;
import com.apap.tp1.service.ProvinsiService;

@Controller
public class InstansiController {
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value="/instansi/getFromProvinsi", method=RequestMethod.GET)
	@ResponseBody
	private List<InstansiModel> getInstansiByProvinsi(@RequestParam (value="provinsiId",required = true) Long provinsiId){
		ProvinsiModel provinsi = provinsiService.getProvinsiById(provinsiId);
		List<InstansiModel> listInstansi = provinsi.getInstansiList();
		return listInstansi;
	}
	
	

}
