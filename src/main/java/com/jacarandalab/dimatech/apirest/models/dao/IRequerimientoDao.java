package com.jacarandalab.dimatech.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.jacarandalab.dimatech.apirest.models.entity.Requerimiento;

public interface IRequerimientoDao extends CrudRepository<Requerimiento, Long> {

}
