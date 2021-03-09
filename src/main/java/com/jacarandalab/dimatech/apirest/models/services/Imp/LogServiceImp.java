package com.jacarandalab.dimatech.apirest.models.services.Imp;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jacarandalab.dimatech.apirest.models.dao.ILogDao;
import com.jacarandalab.dimatech.apirest.models.entity.Log;
import com.jacarandalab.dimatech.apirest.models.services.ILogService;

@Service
public class LogServiceImp implements ILogService{

	@Autowired
	private ILogDao logDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Log> findAll() {
		return (List<Log>) logDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Log findById(Long id) {		
		return logDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Log save(Log log) {
		return logDao.save(log);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		logDao.deleteById(id);
	}

}
