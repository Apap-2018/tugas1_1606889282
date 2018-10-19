package com.apap.tp1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tp1.model.InstansiModel;
import com.apap.tp1.model.ProvinsiModel;
import com.apap.tp1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	@Autowired
	private InstansiDb instansiDb;
	
	@Override
	public Optional<InstansiModel> getInstansiById(Long id) {
		return instansiDb.findById(id);
	}

	@Override
	public List<InstansiModel> getAll() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

	@Override
	public List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		return instansiDb.findByProvinsi(provinsi);
	}

}
