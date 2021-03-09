package com.jacarandalab.dimatech.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.jacarandalab.dimatech.apirest.models.entity.Log;

public interface ILogDao extends CrudRepository<Log, Long> {

}
