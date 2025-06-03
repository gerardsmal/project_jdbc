package com.betacom.jdbc.model;

import java.math.BigDecimal;
import java.util.Date;

public class Dipendente {
	private Integer id;
	private String nome;
	private String cognome;
	private Date dataAssunzione;
	private Integer idUfficio;
	private String telefono;
	private String manzione;
	private BigDecimal stipendio;
	
	public Dipendente(Integer id, String nome, String cognome, Date dataAssunzione, Integer idUfficio, String telefono,
			String manzione, BigDecimal stipendio) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataAssunzione = dataAssunzione;
		this.idUfficio = idUfficio;
		this.telefono = telefono;
		this.manzione = manzione;
		this.stipendio = stipendio;
	}

	public Dipendente() {
		super();
	}

	@Override
	public String toString() {
		return "Dipendente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", dataAssunzione=" + dataAssunzione
				+ ", idUfficio=" + idUfficio + ", telefono=" + telefono + ", manzione=" + manzione + ", stipendio="
				+ stipendio + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public Integer getIdUfficio() {
		return idUfficio;
	}

	public void setIdUfficio(Integer idUfficio) {
		this.idUfficio = idUfficio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getManzione() {
		return manzione;
	}

	public void setManzione(String manzione) {
		this.manzione = manzione;
	}

	public BigDecimal getStipendio() {
		return stipendio;
	}

	public void setStipendio(BigDecimal stipendio) {
		this.stipendio = stipendio;
	}
}
