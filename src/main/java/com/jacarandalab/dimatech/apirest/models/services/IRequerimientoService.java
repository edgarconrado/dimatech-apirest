package com.jacarandalab.dimatech.apirest.models.services;

import java.util.List;

import com.jacarandalab.dimatech.apirest.models.entity.Requerimiento;

public interface IRequerimientoService {

	public List<Requerimiento> findAll();
	
	public Requerimiento findById(Long id);
	
	public Requerimiento save (Requerimiento requerimiento);
	
	public void delete(Long id);
}
