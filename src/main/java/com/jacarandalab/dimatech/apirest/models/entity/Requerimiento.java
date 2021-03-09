package com.jacarandalab.dimatech.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "requerimientos")
public class Requerimiento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre_paciente")
	private String nombrePaciente;

	@Column(name = "email_paciente")
	private String emailPaciente;

	@Column(name = "telefono_paciente")
	private String telefonoPaciente;

	@Column(name = "nombre_doctor")
	private String nombreDoctor;

	@Column(name = "email_doctor")
	private String emailDoctor;

	@Column(name = "telefono_doctor")
	private String telefonoDoctor;

	@Column(name = "estudios")
	private String estudios;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombrePaciente() {
		return nombrePaciente;
	}

	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}

	public String getEmailPaciente() {
		return emailPaciente;
	}

	public void setEmailPaciente(String emailPaciente) {
		this.emailPaciente = emailPaciente;
	}

	public String getTelefonoPaciente() {
		return telefonoPaciente;
	}

	public void setTelefonoPaciente(String telefonoPaciente) {
		this.telefonoPaciente = telefonoPaciente;
	}

	public String getNombreDoctor() {
		return nombreDoctor;
	}

	public void setNombreDoctor(String nombreDoctor) {
		this.nombreDoctor = nombreDoctor;
	}

	public String getEmailDoctor() {
		return emailDoctor;
	}

	public void setEmailDoctor(String emailDoctor) {
		this.emailDoctor = emailDoctor;
	}

	public String getTelefonoDoctor() {
		return telefonoDoctor;
	}

	public void setTelefonoDoctor(String telefonoDoctor) {
		this.telefonoDoctor = telefonoDoctor;
	}

	public String getEstudios() {
		return estudios;
	}

	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
