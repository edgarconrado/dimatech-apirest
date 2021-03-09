package com.jacarandalab.dimatech.apirest.models.services;

import java.util.List;

import com.jacarandalab.dimatech.apirest.models.entity.Log;

public interface ILogService {

	public List<Log> findAll();
	
	public Log findById(Long id);
	
	public Log save (Log log);
	
	public void delete(Long id);
}
