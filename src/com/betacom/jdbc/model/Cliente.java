package com.betacom.jdbc.model;

public class Cliente {
	private Integer id;
	private String denominazione;
	private String pIva;
	private String indirizzo;
	private String telefono;
	
	public Cliente(Integer id, String denominazione, String pIva, String indirizzo, String telefono) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.pIva = pIva;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
	}

	public Cliente() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getpIva() {
		return pIva;
	}

	public void setpIva(String pIva) {
		this.pIva = pIva;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", denominazione=" + denominazione + ", pIva=" + pIva + ", indirizzo=" + indirizzo
				+ ", telefono=" + telefono + "]";
	}
}
