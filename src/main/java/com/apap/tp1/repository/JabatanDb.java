package com.apap.tp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tp1.model.JabatanModel;

@Repository
public interface JabatanDb extends JpaRepository<JabatanModel, Long>{
	
}
