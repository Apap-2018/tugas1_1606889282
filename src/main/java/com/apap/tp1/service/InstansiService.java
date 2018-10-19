package com.apap.tp1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tp1.model.InstansiModel;
import com.apap.tp1.model.ProvinsiModel;

public interface InstansiService {
	Optional<InstansiModel> getInstansiById(Long id);
	
	List<InstansiModel> getAll();
	
	List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi);

}
