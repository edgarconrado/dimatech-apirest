package com.jacarandalab.dimatech.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacarandalab.dimatech.apirest.models.entity.Log;
import com.jacarandalab.dimatech.apirest.models.services.ILogService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class LogRestController {

	@Autowired
	private ILogService logService;
	
	@GetMapping("/v1/logs")
	public List<Log> index() {
		return logService.findAll();
	}
	
	
	@GetMapping("/v1/logs/{id}")	
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Log log = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			log = logService.findById(id);	
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar la consulta en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}		
				
		if(log == null) {
			response.put("mensaje","El mensaje ID: ".concat(id.toString()).concat(" no existe en a Base de Datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Log>(log, HttpStatus.OK); 
	}
	
	@PostMapping("/v1/logs")
	public ResponseEntity<?> create(@RequestBody Log log) {
		
		Log logNew = null;		
		Map<String, Object> response = new HashMap<>();
		
		try {
			logNew = logService.save(log);	
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar el insert en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El mensaje se ha creado en la base de datos");
		response.put("log", logNew);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/v1/logs/{id}")
	public ResponseEntity<?> update(@RequestBody Log log, @PathVariable Long id) {
		
		Log logActual = null;
		Log logUpdated = null;
		
		Map<String, Object> response = new HashMap<>();
		
		
		try {
			logActual = logService.findById(id);	
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar la consulta en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}		
						
		
		if(logActual == null) {
			response.put("mensaje","Erro: no se puede editar el mensaje ID: ".concat(id.toString()).concat(" no existe en a Base de Datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {

			logActual.setMensaje(log.getMensaje());
			
			logUpdated = logService.save(logActual);

			
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar un update en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
		response.put("mensaje","El mensaje se ha actualizado en la base de datos");
		response.put("log", logUpdated);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/v1/logs/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Log log = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			log = logService.findById(id);	
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar la consulta en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
		
		if(log == null) {
			response.put("mensaje","Erro: no se puede eliminar el mensaje ID: ".concat(id.toString()).concat(" no existe en a Base de Datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			logService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al eliminar el mensaje en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El mensaje se ha eliminado en la base de datos");		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
}
