package com.apap.tp1.service;

import java.util.List;

import com.apap.tp1.model.ProvinsiModel;

public interface ProvinsiService {
	List<ProvinsiModel> getProvinsi();
	ProvinsiModel getProvinsiById(Long id);
	
}
