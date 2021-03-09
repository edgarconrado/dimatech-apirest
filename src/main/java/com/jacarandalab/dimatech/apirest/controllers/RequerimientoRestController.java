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

import com.jacarandalab.dimatech.apirest.models.entity.Requerimiento;
import com.jacarandalab.dimatech.apirest.models.services.IRequerimientoService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class RequerimientoRestController {

	@Autowired
	private IRequerimientoService requerimientoService;
	
	@GetMapping("/v1/requerimientos")
	public List<Requerimiento> index() {
		return requerimientoService.findAll();
	}
	
	
	@GetMapping("/v1/requerimientos/{id}")	
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Requerimiento req = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			req = requerimientoService.findById(id);	
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar la consulta en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}		
				
		if(req == null) {
			response.put("mensaje","El requerimineto ID: ".concat(id.toString()).concat(" no existe en a Base de Datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Requerimiento>(req, HttpStatus.OK); 
	}
	
	@PostMapping("/v1/requerimientos")
	public ResponseEntity<?> create(@RequestBody Requerimiento req) {
		
		Requerimiento reqNew = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			System.out.println(req);
			reqNew = requerimientoService.save(req);	
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar el insert en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El requerimiento se ha creado en la base de datos");
		response.put("req", reqNew);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/v1/requerimientos/{id}")
	public ResponseEntity<?> update(@RequestBody Requerimiento req, @PathVariable Long id) {
		
		Requerimiento reqActual = null;
		Requerimiento reqUpdated = null;
		
		Map<String, Object> response = new HashMap<>();
		
		
		try {
			reqActual = requerimientoService.findById(id);	
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar la consulta en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}		
						
		
		if(reqActual == null) {
			response.put("mensaje","Erro: no se puede editar el requerimiento ID: ".concat(id.toString()).concat(" no existe en a Base de Datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {

			reqActual.setEmailDoctor(req.getEmailDoctor());
			reqActual.setEmailPaciente(req.getEmailPaciente());
			reqActual.setEstudios(req.getEstudios());
			reqActual.setNombreDoctor(req.getNombreDoctor());
			reqActual.setNombrePaciente(req.getNombrePaciente());
			reqActual.setTelefonoDoctor(req.getTelefonoDoctor());
			reqActual.setTelefonoPaciente(req.getTelefonoPaciente());
			
			reqUpdated = requerimientoService.save(reqActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar un update en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
		response.put("mensaje","El requerimiento se ha actualizado en la base de datos");
		response.put("log", reqUpdated);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/v1/requerimientos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Requerimiento req = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			req = requerimientoService.findById(id);	
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar la consulta en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
		
		if(req == null) {
			response.put("mensaje","Erro: no se puede eliminar el requerimiento ID: ".concat(id.toString()).concat(" no existe en a Base de Datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			requerimientoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al eliminar el requerimiento en la Base de Datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El requerimiento se ha eliminado en la base de datos");		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
}

